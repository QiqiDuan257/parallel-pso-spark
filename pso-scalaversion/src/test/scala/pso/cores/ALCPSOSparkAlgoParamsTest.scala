package pso.cores

import org.scalatest.FunSuite

class ALCPSOSparkAlgoParamsTest extends FunSuite {

  test("Right Setting A") {
    val algoParams = new ALCPSOSparkAlgoParams(803, 45, 91)
    show(algoParams)
  }

  test("Right Setting B") {
    val algoParams = new ALCPSOSparkAlgoParams(20000, 120)
    show(algoParams)
  }

  test("Wrong Setting A") {
    new ALCPSOSparkAlgoParams(0, 85)
  }

  test("Wrong Setting B") {
    new ALCPSOSparkAlgoParams(17, -53)
  }

  test("Wrong Setting C") {
    new ALCPSOSparkAlgoParams(462, 42, 0)
  }

  private def show(algoParams: ALCPSOSparkAlgoParams): Unit = {
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.challengingMax)
    println(algoParams.leaderLifespanMax)
    println(algoParams.parallelLevel)
  }

}
