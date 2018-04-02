package pso.cores

/**
  * Algorithm Parameters for ALCPSO.
  *
  * @param algoFuncEvalMax the maximum of function evaluations
  * @param algoPopSize population size
  */
class ALCPSOAlgoParams(algoFuncEvalMax: Int, algoPopSize: Int)
  extends AlgoParams(algoFuncEvalMax, algoPopSize) {
  override val name = "ALCPSO"

  val challengingMax = 2 // the maximum of challenging times on each iteration
  val leaderLifespanMax = 60 // the maximum of the lifespan of the leader

}
