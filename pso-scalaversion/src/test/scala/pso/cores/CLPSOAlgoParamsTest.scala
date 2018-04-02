package pso.cores

import org.scalatest.FunSuite

class CLPSOAlgoParamsTest extends FunSuite {

  test("Right Setting") {
    val algoParams = new CLPSOAlgoParams(201, 5)
    println(algoParams.funcEvalMax)
    println(algoParams.popSize)
    println(algoParams.iterMax)
    println(algoParams.name)
    println(algoParams.weightRange)
    println(algoParams.weightInterval)
    println(algoParams.learningRate)
    println(algoParams.refreshingGap)
  }

  test("Wrong Setting A") {
    new CLPSOAlgoParams(0, 7)
  }

  test("Wrong Setting B") {
    new CLPSOAlgoParams(17, -2)
  }

}
