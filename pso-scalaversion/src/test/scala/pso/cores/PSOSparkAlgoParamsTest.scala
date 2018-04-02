package pso.cores

import org.scalatest.FunSuite

class PSOSparkAlgoParamsTest extends FunSuite {

  test("Right Setting A") {
    val algoParams = new PSOSparkAlgoParams(51, 5, 7)
    show(algoParams)
  }

  test("Right Settings B") {
    val algoParams = new PSOSparkAlgoParams(100, 20)
    show(algoParams)
  }

  test("Wrong Setting A") {
    new PSOSparkAlgoParams(-3, 2)
  }

  test("Wrong Setting B") {
    new PSOSparkAlgoParams(11, 0)
  }

  test("Wrong Setting C") {
    new PSOSparkAlgoParams(11, 2, 0)
  }

  private def show(algoParams: PSOSparkAlgoParams): Unit = {
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.weightRange)
    println(algoParams.weightInterval)
    println(algoParams.parallelLevel)
  }
}
