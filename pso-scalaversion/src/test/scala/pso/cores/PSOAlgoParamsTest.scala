package pso.cores

import org.scalatest.FunSuite

class PSOAlgoParamsTest extends FunSuite {

  test("Right Setting") {
    val algoParams = new PSOAlgoParams(51, 5)
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.weightRange)
    println(algoParams.weightInterval)
  }

  test("Wrong Setting A") {
    new PSOAlgoParams(0, 7)
  }

  test("Wrong Setting B") {
    new PSOAlgoParams(17, -2)
  }

}
