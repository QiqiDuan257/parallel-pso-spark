package pso.cores

import breeze.linalg.{DenseMatrix => DM, DenseVector => DV}
import breeze.stats.{mean, stddev}

class RunAlgo(testParams: TestParams) {
  val optResArray: Array[OptRes] = new Array[OptRes](testParams.num)
  private val optys = new DV[Double](testParams.num)
  private val runTimes = new DV[Double](testParams.num)
  private val funcEvalRuntimes = new DV[Double](testParams.num)
  private val funcEvalNums = new DV[Double](testParams.num)

  def run(optimizer: (ConFuncParams, DM[Double] => DV[Double]) => OptRes)
         (funcParams: ConFuncParams, func: DM[Double] => DV[Double]): Unit = {
    var algoName = ""
    var funcEvalMax = Int.MaxValue
    println(f"* funcName: ${funcParams.name}%s + testNum: ${testParams.num}%d + funcDim: ${funcParams.dim}%d")

    for (ti <- 0 until testParams.num) { // testInd
      // run the optimizer and return optimization results
      optResArray(ti) = optimizer(funcParams, func) // functional programming
      optys(ti) = optResArray(ti).opty
      runTimes(ti) = optResArray(ti).runtime
      funcEvalRuntimes(ti) = optResArray(ti).funcEvalRuntime
      funcEvalNums(ti) = optResArray(ti).funcEvalNum.toDouble

      if (ti == 0) { // only print once
        algoName = optResArray(ti).algoParams.name
        funcEvalMax = optResArray(ti).algoParams.funcEvalMax
        println(f"* algoName: $algoName%s + algoFuncEvalMax: $funcEvalMax%d")
      }

      if (testParams.isOutputLog) {
        print(f"test $ti%2d: opty = ${optResArray(ti).opty}%+7.4e || ")
        print(f"runTime = ${optResArray(ti).runtime}%7.2e || ")
        print(f"funcEvalRuntime = ${optResArray(ti).funcEvalRuntime}%7.2e || ")
        print(f"funcEvalNum = ${optResArray(ti).funcEvalNum}%d || ")
        print(f"<- optx [${optResArray(ti).optx(0)}%+7.2e ... ${optResArray(ti).optx(-1)}%+7.2e]\n")
      }
    }

    println("$ ------- >>> Summary <<< ------- $:")
    println(f"* funcName: ${funcParams.name}%s + testNum: ${testParams.num}%d + funcDim: ${funcParams.dim}%d")
    println(f"* algoName: $algoName%s + algoFuncEvalMax: $funcEvalMax%d")
    stat()
  }

  def runDV(optimizer: (ConFuncParams, DV[Double] => Double) => OptRes)
           (funcParams: ConFuncParams, func: DV[Double] => Double): Unit = {
    var algoName = ""
    var funcEvalMax = Int.MaxValue
    println(f"* funcName: ${funcParams.name}%s + testNum: ${testParams.num}%d + funcDim: ${funcParams.dim}%d")

    for (ti <- 0 until testParams.num) { // testInd
      optResArray(ti) = optimizer(funcParams, func) // functional programming
      optys(ti) = optResArray(ti).opty
      runTimes(ti) = optResArray(ti).runtime
      funcEvalRuntimes(ti) = optResArray(ti).funcEvalRuntime
      funcEvalNums(ti) = optResArray(ti).funcEvalNum.toDouble

      if (ti == 0) { // only print once
        algoName = optResArray(ti).algoParams.name
        funcEvalMax = optResArray(ti).algoParams.funcEvalMax
        println(f"* algoName: $algoName%s + algoFuncEvalMax: $funcEvalMax%d")
      }

      if (testParams.isOutputLog) {
        print(f"test $ti%2d: opty = ${optResArray(ti).opty}%+7.4e || ")
        print(f"runTime = ${optResArray(ti).runtime}%7.2e || ")
        print(f"funcEvalRuntime = ${optResArray(ti).funcEvalRuntime}%7.2e || ")
        print(f"funcEvalNum = ${optResArray(ti).funcEvalNum}%d || ")
        print(f"<- optx [${optResArray(ti).optx(0)}%+7.2e ... ${optResArray(ti).optx(-1)}%+7.2e]\n")
      }
    }
    println("$ ------- >>> Summary <<< ------- $:")
    println(f"* funcName: ${funcParams.name}%s + testNum: ${testParams.num}%d + funcDim: ${funcParams.dim}%d")
    println(f"* algoName: $algoName%s + algoFuncEvalMax: $funcEvalMax%d")
    stat()
  }

  // helper function for statistics
  private def stat(): Unit = {
    val funcEvalRatios = (funcEvalRuntimes / runTimes) * 100.0 // percentage
    println(f"opty            --- Mean & Std: ${mean(optys)}%7.2e & ${stddev(optys)}%7.2e")
    println(f"runtime         --- Mean & Std: ${mean(runTimes)}%7.2e & ${stddev(runTimes)}%7.2e")
    println(f"funcEvalRuntime --- Mean & Std: ${mean(funcEvalRuntimes)}%7.2e & ${stddev(funcEvalRuntimes)}%7.2e")
    println(f"funcEvalRatio   --- Mean & Std: ${mean(funcEvalRatios)}%7.2f%% & ${stddev(funcEvalRatios)}%7.2f%%")
    println(f"funcEvalNum     --- Mean & Std: ${mean(funcEvalNums)}%7.2e & ${stddev(funcEvalNums)}%7.2e")
  }
}
