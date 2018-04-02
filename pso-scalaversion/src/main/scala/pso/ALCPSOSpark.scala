package pso

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import pso.cores.{ConFuncParams, OptRes, ALCPSOSparkAlgoParams}
import breeze.linalg.{DenseVector => DV}
import breeze.linalg.{argmin, min, sum}

/**
  *
  * @param algoParams algorithm parameters of ALCPSO
  * @param sparkContext context for Spark
  *
  * REFERENCE:
  *   1. Chen W N, Zhang J, Lin Y, et al.
  *      Particle Swarm Optimization with an Aging Leader and Challengers.
  *      IEEE Transactions on Evolutionary Computation (IEEE TEVC), 2013, 17(2): 241-258.
  */
class ALCPSOSpark(algoParams: ALCPSOSparkAlgoParams,
                  sparkContext: SparkContext) {
  /**
    * Optimize the Continuous Function.
    *
    * @param funcParams parameters for the continuous function optimized
    * @param conFunc the continuous function optimized
    */
  def optimize(funcParams: ConFuncParams, conFunc: DV[Double] => Double): OptRes = {
    val runTimeStart: Long = System.nanoTime()
    val funcDim = funcParams.dim
    val popSize = algoParams.popSize
    val funcEvalMax = algoParams.funcEvalMax
    val challengingMax = algoParams.challengingMax
    val leaderLifespanMax = algoParams.leaderLifespanMax

    val funcEvalCVGC: DV[Double] =
      DV.fill[Double](algoParams.iterMax, Double.PositiveInfinity)

    /**
      * initialize the population (i.e., X)
      */
    // initialize positions (i.e., X) and velocities (i.e., V)
    val upperBoundX: DV[Double] = funcParams.upperBounds
    val lowerBoundX: DV[Double] = funcParams.lowerBounds

    val initUpperBoundX: DV[Double] = funcParams.initUpperBounds
    val initLowerBoundX: DV[Double] = funcParams.initLowerBounds

    val upperBoundV: DV[Double] = (upperBoundX - lowerBoundX) * 0.5
    val lowerBoundV: DV[Double] = upperBoundV * -1.0

    var V = Array[(Int, DV[Double])]()
    var X = Array[(Int, DV[Double])]()

    for (k <- 0 until popSize) {
      V :+= (k, lowerBoundV + ((upperBoundV - lowerBoundV) :* DV.rand(funcDim)))
      X :+= (k, initLowerBoundX + ((initUpperBoundX - initLowerBoundX) :* DV.rand(funcDim)))
    }

    // initialize function values (i.e., y)
    val disX: RDD[(Int, DV[Double])] = sparkContext.parallelize(X, algoParams.parallelLevel)
    var funcEvalRuntimeStart = System.nanoTime()
    val disy: Map[Int, Double] = disX.mapValues(conFunc(_)).collect().toMap
    var funcEvalRuntime = (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
    var funcEvalNum = popSize
    val y = DV.fill[Double](popSize, Double.PositiveInfinity)
    for ((k, v) <- disy) {
      y(k) = v
    }

    // initialize personally best X and y
    var pbX = Array[(Int, DV[Double])]()
    for (k <- 0 until popSize) {
      pbX :+= (X(k)._1, X(k)._2.copy)
    }
    val pby: DV[Double] = y.copy

    // initialize globally best X and y
    val optx: DV[Double] = pbX(argmin(pby))._2.copy
    var opty: Double = min(pby)

    funcEvalCVGC(0) = opty

    // initialize the leader
    val leader = optx.copy
    var leadery = opty
    var leaderAge = 0
    var leaderLifespan = leaderLifespanMax

    /** iteratively update the population */
    while (funcEvalNum < funcEvalMax) { // synchronously update
      var indicatorGLP = false // good leading power
      var indicatorFLP = false // fair leading power
      val pbyBak: DV[Double] = pby.copy
      var indicatorPLP = false // poor leading power
      // update and limit the velocities
      for ((k, v) <- V) {
        v := (v * 0.4) +
          ((DV.rand[Double](funcDim) :* (pbX(k)._2 - X(k)._2)) * 2.0) +
          ((DV.rand[Double](funcDim) :* (leader - X(k)._2)) * 2.0)
        v(v :< lowerBoundV) := lowerBoundV(v :< lowerBoundV)
        v(v :> upperBoundV) := upperBoundV(v :> upperBoundV)
      }

      // update, limit, and evaluate the positions
      for ((k, x) <- X) {
        x :+= V(k)._2
        x(x :< lowerBoundX) := lowerBoundX(x :< lowerBoundX)
        x(x :> upperBoundX) := upperBoundX(x :> upperBoundX)
      }

      val disX = sparkContext.parallelize(X, algoParams.parallelLevel)
      funcEvalRuntimeStart = System.nanoTime()
      val disy = disX.mapValues(conFunc(_)).collect().toMap
      funcEvalRuntime += (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
      funcEvalNum += popSize
      for ((k, v) <- disy) {
        y(k) = v
      }

      if (min(y) < leadery) {
        leadery = min(y)
        leader := X(argmin(y))._2
        indicatorPLP = true
      }

      // update the individually-best positions and function values
      for (k <- 0 until popSize) {
        if (y(k) < pby(k)) {
          pby(k) = y(k)
          pbX(k)._2 := X(k)._2
        }
      }

      // update the globally-best positions and function values
      if (min(pby) < opty) {
        opty = min(pby)
        optx := pbX(argmin(pby))._2
        indicatorGLP = true
      }

      if (funcEvalNum % popSize == 0) { // funcEvalCVGCInterval == popSize
        funcEvalCVGC(funcEvalNum / popSize - 1) = opty
      }

      if (sum(pby) < sum(pbyBak)) {
        indicatorFLP = true
      }

      // control the lifespan of the leader
      leaderAge += 1
      if (indicatorGLP) {
        leaderLifespan += 2
      } else if (indicatorFLP) {
        leaderLifespan += 1
      } else if (indicatorPLP) {
      } else { // no leading power
        leaderLifespan -= 1
      }

      if (leaderAge >= leaderLifespan) { // generate and test the challenger
        var flagSame = true // the challenger is the same as the leader
        val challenger = leader.copy
        for (fd <- 0 until funcDim) { // funcDim
          if (math.random < (1.0 / funcDim.toDouble)) {
            challenger(fd) = lowerBoundX(fd) + (upperBoundX(fd) - lowerBoundX(fd)) * math.random
            flagSame = false
          }
        }
        if (flagSame) { // ensure that the challenger is different from the leader
          val rfd = scala.util.Random.nextInt(popSize) // random index of function dimensions
          challenger(rfd) = lowerBoundX(rfd) + (upperBoundX(rfd) - lowerBoundX(rfd)) * math.random
        }
        var XBak = Array[(Int, DV[Double])]()
        for (k <- 0 until popSize) {
          XBak :+= (X(k)._1, X(k)._2.copy)
        }
        var VBak = Array[(Int, DV[Double])]()
        for (k <- 0 until popSize) {
          VBak :+= (V(k)._1, V(k)._2.copy)
        }
        funcEvalRuntimeStart = System.nanoTime()
        var challengery = conFunc(challenger)
        funcEvalRuntime += (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
        funcEvalNum += 1
        if (challengery < opty) {
          opty = challengery
          optx := challenger
        }
        if (funcEvalNum % popSize == 0) { // funcEvalCVGCInterval == popSize
          funcEvalCVGC(funcEvalNum / popSize - 1) = opty
        }
        var flagImprove = false
        var tryNum = 1
        while (!flagImprove && tryNum <= challengingMax) {
          tryNum += 1
          // update and limit the velocities
          for ((k, v) <- V) {
            v := (v * 0.4) +
              ((DV.rand[Double](funcDim) :* (pbX(k)._2 - X(k)._2)) * 2.0) +
              ((DV.rand[Double](funcDim) :* (challenger - X(k)._2)) * 2.0)
            v(v :< lowerBoundV) := lowerBoundV(v :< lowerBoundV)
            v(v :> upperBoundV) := upperBoundV(v :> upperBoundV)
          }

          // update, limit, and evaluate the positions
          for ((k, x) <- X) {
            x :+= V(k)._2
            x(x :< lowerBoundX) := lowerBoundX(x :< lowerBoundX)
            x(x :> upperBoundX) := upperBoundX(x :> upperBoundX)
          }

          val disX = sparkContext.parallelize(X, algoParams.parallelLevel)
          funcEvalRuntimeStart = System.nanoTime()
          val disy = disX.mapValues(conFunc(_)).collect().toMap
          funcEvalRuntime += (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
          funcEvalNum += popSize
          for ((k, v) <- disy) {
            y(k) = v
          }

          // update the individually-best positions and function values
          for (k <- 0 until popSize) {
            if (y(k) < pby(k)) {
              pby(k) = y(k)
              pbX(k)._2 := X(k)._2
              flagImprove = true
            }
          }

          // update the globally-best positions and function values
          if (min(pby) < opty) {
            opty = min(pby)
            optx := pbX(argmin(pby))._2
          }

          if (funcEvalNum % popSize == 0) { // funcEvalCVGCInterval == popSize
            funcEvalCVGC(funcEvalNum / popSize - 1) = opty
          }

          if (min(y) < challengery) {
            challengery = min(y)
            challenger := X(argmin(y))._2
          }

          if (flagImprove) {
            leader := challenger
            leadery = challengery
            leaderAge = 0
            leaderLifespan = leaderLifespanMax
          }
        }
        if (!flagImprove) {
          for (k <- 0 until popSize) {
            X(k)._2 := XBak(k)._2.copy
            V(k)._2 := VBak(k)._2.copy
          }
          leaderAge = leaderLifespan - 1
        }
      }
    }

    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds

    new OptRes(opty, optx, runTime,
      funcEvalRuntime, funcEvalNum, popSize,
      funcEvalCVGC, funcParams, algoParams)
  }
}
