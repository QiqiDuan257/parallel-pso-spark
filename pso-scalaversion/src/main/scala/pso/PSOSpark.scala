package pso

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import pso.cores.{ConFuncParams, OptRes, PSOSparkAlgoParams}
import breeze.linalg.{DenseVector => DV}
import breeze.linalg.{argmin, min}

/**
  * Particle Swarm Optimizer Based on Spark.
  *
  * @param algoParams algorithm parameters of PSO
  * @param sparkContext context for Spark
  *
  * REFERENCE:
  *   1. Shi Y, Eberhart R. A Modified Particle Swarm Optimizer.
  *      IEEE World Congress on Computational Intelligence, 1998, 69-73.
  *      http://ieeexplore.ieee.org/document/699146/
  */
class PSOSpark(algoParams: PSOSparkAlgoParams,
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

//    scala.util.Sorting.stableSort(V,
//      (a: (Int, DV[Double]), b: (Int, DV[Double])) => a._1 < b._1)
//    scala.util.Sorting.stableSort(X,
//      (a: (Int, DV[Double]), b: (Int, DV[Double])) => a._1 < b._1)
//    scala.util.Sorting.stableSort(pbX,
//      (a: (Int, DV[Double]), b: (Int, DV[Double])) => a._1 < b._1)

    // initialize globally best X and y
    val optx: DV[Double] = pbX(argmin(pby))._2.copy
    var opty: Double = min(pby)

    funcEvalCVGC(0) = opty

    /** iteratively update the population */
    var weight = algoParams.weightRange.head
    while (funcEvalNum < funcEvalMax) { // synchronously update
      // update and limit the velocities
      for ((k, v) <- V) {
        v := (v * weight) +
          ((DV.rand[Double](funcDim) :* (pbX(k)._2 - X(k)._2)) * 2.0) +
          ((DV.rand[Double](funcDim) :* (optx - X(k)._2)) * 2.0)
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
