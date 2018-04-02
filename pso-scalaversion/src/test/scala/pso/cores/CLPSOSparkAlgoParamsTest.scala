package pso.cores

import org.scalatest.FunSuite

class CLPSOSparkAlgoParamsTest extends FunSuite {

  test("Right Setting A") {
    val algoParams = new CLPSOSparkAlgoParams(1001, 100, 7)
    show(algoParams)
  }

  test("Right Setting B") {
    val algoParams = new CLPSOSparkAlgoParams(1001, 100)
    show(algoParams)
  }

  test("Wrong Setting A") {
    new CLPSOSparkAlgoParams(-2, 7)
  }

  test("Wrong Setting B") {
    new CLPSOSparkAlgoParams(3, -22)
  }

  test("Wrong Setting C") {
    new CLPSOSparkAlgoParams(1004, 75, -7)
  }

  private def show(algoParams: CLPSOSparkAlgoParams): Unit = {
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.weightRange)
    println(algoParams.weightInterval)
    println(algoParams.learningRate)
    println(algoParams.refreshingGap)
    println(algoParams.parallelLevel)
  }

}
