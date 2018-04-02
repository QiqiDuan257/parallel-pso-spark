package pso.cores

import breeze.linalg.{DenseMatrix => DM, DenseVector => DV}
import breeze.linalg.{Axis, product, tile, sum}
import breeze.numerics.{constants, cos, pow, sqrt}

object ConFunc {

  def cfGriewank(X: DM[Double]): DV[Double] = {
    sum(pow(X, 2), Axis._1) / 4000.0 - product(cos(X / sqrt(tile(DV.rangeD(1.0, X.cols.toDouble + 1.0).t, 1, X.rows))), Axis._1) + 1.0
  }

  def cfGriewankDV(X: DV[Double]): Double = {
    sum(pow(X, 2)) / 4000.0 - product(cos(X / sqrt(DV.rangeD(1.0, X.length.toDouble + 1.0)))) + 1.0
  }

  def cfRastrigin(X: DM[Double]): DV[Double] = {
    sum(pow(X, 2) - cos(X * 2.0 * constants.Pi) * 10.0 + 10.0, Axis._1)
  }

  def cfRastriginDV(X: DV[Double]): Double = {
    sum(pow(X, 2) - cos(X * 2.0 * constants.Pi) * 10.0 + 10.0)
  }

  def cfRosenbrock(X: DM[Double]): DV[Double] = {
    sum((pow(pow(X(::, 0 until (X.cols - 1)), 2) - X(::, 1 until X.cols), 2) * 100.0) + pow(X(::, 0 until (X.cols - 1)) - 1.0, 2), Axis._1)
  }

  def cfRosenbrockDV(X: DV[Double]): Double = {
    sum((pow(pow(X(0 until (X.length - 1)), 2) - X(1 until X.length), 2) * 100.0) + pow(X(0 until (X.length - 1)) - 1.0, 2))
  }

  def cfSchwefel12(X: DM[Double]): DV[Double] = {
    val y = DV.zeros[Double](X.rows)
    for (funcDim <- 0 until X.cols) {
      y := y + pow(sum(X(::, 0 to funcDim), Axis._1), 2)
    }
    y
  }

  def cfSchwefel12DV(x: DV[Double]): Double = {
    var y = 0.0
    for (funcDim <- 0 until x.length) {
      y += pow(sum(x(0 to funcDim)), 2)
    }
    y
  }

  def cfSphere(X: DM[Double]): DV[Double] = {
    sum(pow(X, 2), Axis._1)
  }

  def cfSphereDV(X: DV[Double]): Double = {
    sum(pow(X, 2))
  }
}
