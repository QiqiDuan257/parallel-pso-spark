package pso

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import pso.cores.{ConFuncParams, OptRes, CLPSOSparkAlgoParams}
import breeze.linalg.{DenseVector => DV}
import breeze.linalg.{argmin, min}
import breeze.numerics.exp

/**
  *
  * @param algoParams algorithm parameters of CLPSO
  * @param sparkContext context for Spark
  *
  * REFERENCE:
  *   1. Liang J J, Qin A K, Suganthan P N, et al.
  *      Comprehensive Learning Particle Swarm Optimizer for Global Optimization of Multimodal Functions.
  *      IEEE Transactions on Evolutionary Computation (IEEE TEVC), 2006, 10(3): 281-295.
  *      http://ieeexplore.ieee.org/abstract/document/1637688/
  *   2. Matlab Source Code Published by the Authors (Suganthan P N):
  *      http://web.mysites.ntu.edu.sg/epnsugan/PublicSite/Shared%20Documents/Codes/2006-IEEE-TEC-CLPSO.zip
  */
class CLPSOSpark(algoParams: CLPSOSparkAlgoParams,
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
    val refreshingGap = algoParams.refreshingGap
    val learningRate = algoParams.learningRate

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

    val upperBoundV: DV[Double] = (upperBoundX - lowerBoundX) * 0.2
    val lowerBoundV: DV[Double] = upperBoundV * -1.0

    var V = Array[(Int, DV[Double])]()
    var X = Array[(Int, DV[Double])]()

    for (k <- 0 until popSize) { // popInd
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

    // initialize learning probability and exemplar learned
    val learningProb = ((exp((DV.rangeD(1.0, popSize + 1.0) - 1.0) *
      10.0 / (popSize - 1.0)) - 1.0) / (exp(10.0) - 1.0)) * 0.45 + 0.05
    var exemplars = Array[(Int, DV[Double])]()
    for (k <- 0 until popSize) {
      exemplars :+= (pbX(k)._1, pbX(k)._2.copy)
    }
    val refreshingGaps = DV.zeros[Int](popSize)

    /** iteratively update the population */
    var weight = algoParams.weightRange.head
    while (funcEvalNum < funcEvalMax) { // synchronously update
      // update and limit the velocities
      for ((k, v) <- V) {
        // allow the particle to learn from the exemplar until the particle
        // stops improving for a certain number of generations (i.e., refreshingGaps)
        var learnFlag = false // indicate the particle does not learn to other particles
        if (refreshingGaps(k) >= refreshingGap) {
          refreshingGaps(k) = 0
          for (fd <- 0 until funcDim) {
            if (math.random < learningProb(k)) {
              val individuals = scala.util.Random.shuffle((0 until popSize).toList)
              val a = individuals.head
              val b = individuals.tail.head
              if (pby(a) < pby(b)) { // tournament selection
                exemplars(k)._2(fd) = pbX(a)._2(fd)
              } else {
                exemplars(k)._2(fd) = pbX(b)._2(fd)
              }
              learnFlag = true // indicate the particle learns to at least one other particle
            } else {
              exemplars(k)._2(fd) = pbX(k)._2(fd)
            }
          }
          if (!learnFlag) { // make sure to learn to at least other particle for one random dimension
            val randFuncDim = scala.util.Random.nextInt(funcDim)
            val individuals = scala.util.Random.shuffle((0 until popSize).toList)
            var randIndividual = Int.MaxValue
            if (individuals.head == k) {
              randIndividual = individuals.head
            } else {
              randIndividual = individuals.tail.head
            }
            exemplars(k)._2(randFuncDim) = pbX(randIndividual)._2(randFuncDim)
          }
        }

        v := (v * weight) + ((DV.rand[Double](funcDim) :* (exemplars(k)._2 - X(k)._2)) * learningRate)
        v(v :< lowerBoundV) := lowerBoundV(v :< lowerBoundV)
        v(v :> upperBoundV) := upperBoundV(v :> upperBoundV)
      }
      weight -= algoParams.weightInterval

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
          refreshingGaps(k) = 0
        } else {
          refreshingGaps(k) += 1
        }
      }

      // update the globally-best positions and function values
      val miny = min(pby)
      if (miny < opty) {
        opty = miny
        optx := pbX(argmin(pby))._2
      }

      if (funcEvalNum % popSize == 0) { // funcEvalCVGCInterval == popSize
        funcEvalCVGC(funcEvalNum / popSize - 1) = opty
      }
    }

    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds

    new OptRes(opty, optx, runTime,
      funcEvalRuntime, funcEvalNum, popSize,
      funcEvalCVGC, funcParams, algoParams)
  }
}
