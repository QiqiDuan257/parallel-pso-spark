package pso.cores

import scala.math.ceil

/**
  * Algorithm Parameters.
  *
  * @param algoFuncEvalMax the maximum of function evaluations
  * @param algoPopSize population size
  */
class AlgoParams(algoFuncEvalMax: Int, algoPopSize: Int) {
  require(algoFuncEvalMax > 0, "the maximum of function evaluations must be larger than 0.")
  require(algoPopSize > 0, "population size must be larger than 0.")

  val funcEvalMax: Int = algoFuncEvalMax
  val popSize: Int = algoPopSize
  val name: String = "" // algorithm name

  def iterMax: Int = ceil(1.0 * funcEvalMax / popSize).toInt // maximum of iterations
}
