package pso.cores

/**
  * Algorithm Parameters for PSO.
  *
  * @param algoFuncEvalMax the maximum of function evaluations
  * @param algoPopSize population size
  */
class PSOAlgoParams(algoFuncEvalMax: Int, algoPopSize: Int)
  extends AlgoParams(algoFuncEvalMax, algoPopSize) {
  override val name = "PSO"

  // inertia weights linearly decreased during optimization
  val weightRange: List[Double] = List(0.9, 0.4)
  val weightInterval: Double = (weightRange.head - weightRange(1)) / (iterMax - 1)
}
