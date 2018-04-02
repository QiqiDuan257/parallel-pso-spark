package pso.cores

import breeze.linalg.{DenseVector => DV}
import breeze.linalg.all
import breeze.numerics.Inf

/**
  * Continuous Function Parameters.
  *
  * @param funcName function name
  * @param funcDim function dimension
  * @param funcUpperBounds search upper bounds
  * @param funcLowerBounds search lower bounds
  * @param funcInitUpperBounds initial search upper bounds
  * @param funcInitLowerBounds initial search lower bounds
  * @param funcOptx optimal value (i.e., x) for the function
  * @param funcOpty optimal function value (i.e., y) for the function
  */
class ConFuncParams(funcName: String,
                    funcDim: Int,
                    funcUpperBounds: DV[Double],
                    funcLowerBounds: DV[Double],
                    funcInitUpperBounds: DV[Double],
                    funcInitLowerBounds: DV[Double],
                    funcOptx: DV[Double],
                    funcOpty: Double) {
  require(funcDim > 0, "function dimension must be larger than 0.")
  require(handleFuncBounds(funcUpperBounds, funcLowerBounds),
    "search upper bounds must be larger than lower bounds.")
  require(handleFuncBounds(funcInitUpperBounds, funcInitLowerBounds),
    "initial search upper bounds must be larger than initial lower bounds.")

  val name: String = funcName
  val dim: Int = funcDim
  val upperBounds: DV[Double] = funcUpperBounds
  val lowerBounds: DV[Double] = funcLowerBounds
  val initUpperBounds: DV[Double] = funcInitUpperBounds
  val initLowerBounds: DV[Double] = funcInitLowerBounds
  val optx: DV[Double] = funcOptx
  val opty: Double = funcOpty
  val optObj: String = "min" // minimization vs. maximization

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: DV[Double]) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcUpperBounds * -1.0,
    funcUpperBounds,
    funcUpperBounds * -1.0,
    DV.ones[Double](funcDim) * Inf,
    Inf)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: DV[Double],
           funcLowerBounds: DV[Double]) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcLowerBounds,
    funcUpperBounds,
    funcLowerBounds,
    DV.ones[Double](funcDim) * Inf,
    Inf)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: DV[Double],
           funcLowerBounds: DV[Double],
           funcInitUpperBounds: DV[Double]) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcLowerBounds,
    funcInitUpperBounds,
    funcInitUpperBounds * -1.0,
    DV.ones[Double](funcDim) * Inf,
    Inf)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: DV[Double],
           funcLowerBounds: DV[Double],
           funcInitUpperBounds: DV[Double],
           funcInitLowerBounds: DV[Double]) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcLowerBounds,
    funcInitUpperBounds,
    funcInitLowerBounds,
    DV.ones[Double](funcDim) * Inf,
    Inf)

  /**
    * Syntactic Sugar
    */
  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: Double,
           funcLowerBounds: Double,
           funcInitUpperBounds: Double,
           funcInitLowerBounds: Double) = this(funcName,
    funcDim,
    DV.ones[Double](funcDim) * funcUpperBounds,
    DV.ones[Double](funcDim) * funcLowerBounds,
    DV.ones[Double](funcDim) * funcInitUpperBounds,
    DV.ones[Double](funcDim) * funcInitLowerBounds,
    DV.ones[Double](funcDim) * Inf,
    Inf)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: Double) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcUpperBounds * -1.0,
    funcUpperBounds,
    funcUpperBounds * -1.0)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: Double,
           funcLowerBounds: Double) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcLowerBounds,
    funcUpperBounds,
    funcLowerBounds)

  def this(funcName: String,
           funcDim: Int,
           funcUpperBounds: Double,
           funcLowerBounds: Double,
           funcInitUpperBounds: Double) = this(funcName,
    funcDim,
    funcUpperBounds,
    funcLowerBounds,
    funcInitUpperBounds,
    funcInitUpperBounds * -1.0)

  private def handleFuncBounds(funcUpperBounds: DV[Double],
                               funcLowerBounds: DV[Double]): Boolean = {
    if (all(funcUpperBounds :> funcLowerBounds)) true else false
  }
}
