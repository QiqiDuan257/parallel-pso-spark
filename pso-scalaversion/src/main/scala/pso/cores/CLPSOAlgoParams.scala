package pso.cores

/**
  * Algorithm Parameters for CLPSO.
  *
  * @param algoFuncEvalMax the maximum of function evaluations
  * @param algoPopSize population size
  */
class CLPSOAlgoParams(algoFuncEvalMax: Int, algoPopSize: Int)
  extends AlgoParams(algoFuncEvalMax, algoPopSize) {
  override val name = "CLPSO"

  // inertia weights linearly decreased during optimization
  val weightRange: List[Double] = List(0.9, 0.4)
  val weightInterval: Double = (weightRange.head - weightRange(1)) / (iterMax - 1)
  val refreshingGap: Int = 7 // threshold
  val learningRate: Double = 1.49445
}
