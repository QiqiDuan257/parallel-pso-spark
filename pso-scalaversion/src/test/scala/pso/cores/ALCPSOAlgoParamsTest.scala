package pso.cores

import org.scalatest.FunSuite

class ALCPSOAlgoParamsTest extends FunSuite {

  test("Right Setting") {
    val algoParams = new ALCPSOAlgoParams(24, 3)
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.challengingMax)
    println(algoParams.leaderLifespanMax)
  }

  test("Wrong Setting A") {
    new ALCPSOAlgoParams(-7, 3)
  }

  test("Wrong Setting B") {
    new ALCPSOAlgoParams(1, 0)
  }

}
