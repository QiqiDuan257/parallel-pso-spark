package pso.cores

import breeze.linalg.{DenseVector => DV}

/**
  * Optimization Results Obtained by Optimization Algorithms.
  *
  * @param resOpty the final function value found by the optimization algorithm
  * @param resOpyx the final value found by the optimization algorithm
  * @param resRuntime the total runtime of the optimization algorithm
  * @param resFuncEvalRuntime the total runtime of function evaluations
  * @param resFuncEvalNum the total number of function evaluations
  * @param resFuncEvalCVGCInterval the interval for convergence curve
  * @param resFuncEvalCVGC the convergence curve of function evaluations
  * @param resFuncParams parameters for the continuous function optimized
  * @param resAlgoParams parameters for the optimization algorithm selected
  */
class OptRes(resOpty: Double,
             resOpyx: DV[Double],
             resRuntime: Double,
             resFuncEvalRuntime: Double,
             resFuncEvalNum: Int,
             resFuncEvalCVGCInterval: Int,
             resFuncEvalCVGC: DV[Double],
             resFuncParams: ConFuncParams,
             resAlgoParams: AlgoParams) {

  val opty: Double = resOpty
  val optx: DV[Double] = resOpyx
  val runtime: Double = resRuntime
  val funcEvalRuntime: Double = resFuncEvalRuntime
  val funcEvalNum: Int = resFuncEvalNum
  val funcEvalCVGCInterval: Int = resFuncEvalCVGCInterval
  val funcEvalCVGC: DV[Double] = resFuncEvalCVGC
  val funcParams: ConFuncParams = resFuncParams
  val algoParams: AlgoParams = resAlgoParams
}
