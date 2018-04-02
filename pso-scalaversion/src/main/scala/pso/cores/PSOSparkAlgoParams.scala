package pso.cores

/**
  * Algorithm Parameters for PSOSpark.
  *
  * @param algoFuncEvalMax the maximum of function evaluations
  * @param algoPopSize population size
  * @param algoParallelLevel parallel level for the population (default: population size)
  */
class PSOSparkAlgoParams(algoFuncEvalMax: Int, algoPopSize: Int, algoParallelLevel: Int)
  extends PSOAlgoParams(algoFuncEvalMax, algoPopSize) {
  require(algoParallelLevel > 0, "parallel level for the population must be larger than 0.")

  override val name = "PSOSpark"

  val parallelLevel: Int = algoParallelLevel

  def this(algoFuncEvalMax: Int,
           algoPopSize: Int) = this(algoFuncEvalMax,
    algoPopSize,
    algoPopSize)
}
