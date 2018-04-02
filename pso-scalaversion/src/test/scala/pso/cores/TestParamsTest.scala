package pso.cores

import org.scalatest.FunSuite

class TestParamsTest extends FunSuite {

  test("Right Setting A") {
    val testParams = new TestParams(3, false, 20180101)
    show(testParams)
  }

  test("Right Setting B") {
    val testParams = new TestParams(4)
    show(testParams)
  }

  test("Right Setting C") {
    val testParams = new TestParams(4, false)
    show(testParams)
  }

  test("Right Setting D") {
    val testParams = new TestParams(5, false)
    show(testParams)
  }

  test("Wrong Setting A") {
    new TestParams(0)
  }

  test("Wrong Setting B") {
    new TestParams(1, false, -1)
  }

  private def show(testParams: TestParams): Unit = {
    println(testParams.num)
    println(testParams.isOutputLog)
    println(testParams.seeds)
  }

}
