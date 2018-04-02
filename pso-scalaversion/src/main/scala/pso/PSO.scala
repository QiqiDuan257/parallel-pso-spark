package pso

import pso.cores.{ConFuncParams, OptRes, PSOAlgoParams}
import breeze.linalg.{DenseMatrix => DM, DenseVector => DV}
import breeze.linalg.{Transpose, argmin, min, tile}

/**
  * Particle Swarm Optimizer.
  *
  * @param algoParams algorithm parameters of PSO
  *
  * REFERENCE:
  *   1. Shi Y, Eberhart R. A Modified Particle Swarm Optimizer.
  *      IEEE World Congress on Computational Intelligence, 1998, 69-73.
  *      http://ieeexplore.ieee.org/document/699146/
  */
class PSO(algoParams: PSOAlgoParams) {
  /**
    * Optimize the Continuous Function.
    *
    * @param funcParams parameters for the continuous function optimized
    * @param func the continuous function optimized
    */
  def optimize(funcParams: ConFuncParams, func: DM[Double] => DV[Double]): OptRes = {
    val runTimeStart: Long = System.nanoTime()

    val funcDim = funcParams.dim
    val popSize = algoParams.popSize
    val funcEvalMax = algoParams.funcEvalMax

    val funcEvalCVGC: DV[Double] =
      DV.fill[Double](algoParams.iterMax, Double.PositiveInfinity)

    /**
      * initialize the population (i.e., X)
      */
    // initialize search upper and lower bounds during search
    val upperBoundX = tile(funcParams.upperBounds.t, 1, popSize)
    val lowerBoundX = tile(funcParams.lowerBounds.t, 1, popSize)

    // initialize search upper and lower bounds at the initialization stage
    val initUpperBoundX = tile(funcParams.initUpperBounds.t, 1, popSize)
    val initLowerBoundX = tile(funcParams.initLowerBounds.t, 1, popSize)
    val X: DM[Double] = initLowerBoundX +
      (initUpperBoundX - initLowerBoundX) :* DM.rand[Double](popSize, funcDim)

    // initialize velocities (i.e., V)
    val upperBoundV = (upperBoundX - lowerBoundX) * 0.2
    val lowerBoundV = upperBoundV * -1.0
    val V: DM[Double] = lowerBoundV +
      (upperBoundV - lowerBoundV) :* DM.rand[Double](popSize, funcDim)

    // initialize function values (i.e., y)
    var funcEvalRuntimeStart = System.nanoTime()
    var y: DV[Double] = func(X)
    var funcEvalRuntime = (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
    var funcEvalNum = popSize

    // initialize personally best X and y
    val pbX = X.copy
    val pby = y.copy

    // initialize globally best X and y
    val optx: Transpose[DV[Double]] = DV.fill[Double](funcDim, Double.PositiveInfinity).t
    optx := pbX(argmin(pby), ::)
    var opty = min(pby)

    funcEvalCVGC(0) = opty

    /**
      * iteratively update the population
      */
    var weight = algoParams.weightRange.head
    while (funcEvalNum < funcEvalMax) { // synchronously update
      // update and limit the velocities
      V := (V * weight) +
        ((DM.rand[Double](popSize, funcDim) :* (pbX - X)) * 2.0) +
        ((DM.rand[Double](popSize, funcDim) :* (tile(optx, 1, popSize) - X)) * 2.0)
      V(V :< lowerBoundV) := lowerBoundV(V :< lowerBoundV)
      V(V :> upperBoundV) := upperBoundV(V :> upperBoundV)
      weight -= algoParams.weightInterval

      // update, limit, and evaluate the positions
      X += V
      X(X :< lowerBoundX) := lowerBoundX(X :< lowerBoundX)
      X(X :> upperBoundX) := upperBoundX(X :> upperBoundX)
      funcEvalRuntimeStart = System.nanoTime()
      y = func(X)
      funcEvalRuntime += (System.nanoTime() - funcEvalRuntimeStart) / 1.0e9 // seconds
      funcEvalNum += popSize

      // update the individually-best positions and function values
      for (popInd <- 0 until X.rows) {
        if (y(popInd) < pby(popInd)) {
          pby(popInd) = y(popInd)
          pbX(popInd, ::) := X(popInd, ::)
        }
      }

      // update the globally-best positions and function values
      val miny = min(pby)
      if (miny < opty) {
        opty = miny
        optx := pbX(argmin(pby), ::)
      }

      if (funcEvalNum % popSize == 0) { // funcEvalCVGCInterval == popSize
        funcEvalCVGC(funcEvalNum / popSize - 1) = opty
      }
    }

    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds

    new OptRes(opty, optx.t, runTime,
      funcEvalRuntime, funcEvalNum, popSize,
      funcEvalCVGC, funcParams, algoParams)
  }
}
