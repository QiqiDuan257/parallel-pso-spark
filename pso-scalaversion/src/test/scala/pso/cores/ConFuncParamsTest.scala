package pso.cores

import org.scalatest.FunSuite
import breeze.linalg.{DenseVector => DV}

class ConFuncParamsTest extends FunSuite {

  test("Right Setting A") {
    val conFuncParams = new ConFuncParams("cfSphere",
      3,
      DV.ones[Double](3) * 2.0,
      DV.ones[Double](3) * -2.0,
      DV.ones[Double](3) * 1.5,
      DV.ones[Double](3) * 0.5,
      DV.zeros[Double](3),
      0.0)
    show(conFuncParams)
  }

  test("Right Setting B") {
    val conFuncParams = new ConFuncParams("cfSphere",
      4,
      DV.ones[Double](4) * 1.1)
    show(conFuncParams)
  }

  test("Right Setting C") {
    val conFuncParams = new ConFuncParams("cfSphere",
      5,
      DV.ones[Double](5) * 2.1,
      DV.ones[Double](5) * -3.7)
    show(conFuncParams)
  }

  test("Right Setting D") {
    val conFuncParams = new ConFuncParams("cfSphere",
      6,
      DV.ones[Double](6) * -7.1,
      DV.ones[Double](6) * -10.9,
      DV.ones[Double](6) * 3.2)
    show(conFuncParams)
  }

  test("Right Setting E") {
    val conFuncParams = new ConFuncParams("cfSphere",
      7,
      DV.ones[Double](7) * 0.2,
      DV.ones[Double](7) * -2.1,
      DV.ones[Double](7) * 3.2,
      DV.ones[Double](7) * 1.7)
    show(conFuncParams)
  }

  /**
    * Syntactic Sugar
    */
  test("Right Setting AA") {
    val conFuncParams = new ConFuncParams("cfSphere",
      3,
      2.0,
      -2.0,
      1.5,
      0.5)
    show(conFuncParams)
  }

  test("Right Setting BB") {
    val conFuncParams = new ConFuncParams("cfSphere",
      4,
      1.1)
    show(conFuncParams)
  }

  test("Right Setting CC") {
    val conFuncParams = new ConFuncParams("cfSphere",
      5,
      2.1,
      -3.7)
    show(conFuncParams)
  }

  test("Right Setting DD") {
    val conFuncParams = new ConFuncParams("cfSphere",
      6,
      -7.1,
      -10.9,
      3.2)
    show(conFuncParams)
  }

  test("Wrong Setting A") {
    new ConFuncParams("cfSphere", 0, 100.0)
  }

  test("Wrong Setting B") {
    new ConFuncParams("cfSphere", 1, -100.0)
  }

  test("Wrong Setting C") {
    new ConFuncParams("cfSphere", 2, 100.0, 200.0)
  }

  test("Wrong Setting D") {
    new ConFuncParams("cfSphere", 2, 100.0, -100.0, 0.0)
  }

  test("Wrong Setting E") {
    new ConFuncParams("cfSphere", 2, 100.0, -100.0, 77.1, 77.2)
  }

  private def show(conFuncParams: ConFuncParams): Unit = {
    println(conFuncParams.name)
    println(conFuncParams.dim)
    println(conFuncParams.upperBounds)
    println(conFuncParams.lowerBounds)
    println(conFuncParams.initUpperBounds)
    println(conFuncParams.initLowerBounds)
    println(conFuncParams.optx)
    println(conFuncParams.opty)
  }

}
