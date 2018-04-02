package pso.ppsn2018

import pso.cores._
import pso.PSO

object DemoPPSN2018PSO extends App {
  /**
    * Run Time Varying with Function Dimensions.
    */
  println("\n***\nRun Time Varying with Function Dimensions.\n***\n")
  val totalRunTimeStart = System.nanoTime()
  val funDims = Array(10, 100, 1000, 10000, 100000)
  for (funDim <- funDims) {
    val runTimeStart = System.nanoTime()
    val conFuncParams = new ConFuncParams("cfSchwefel12", funDim, 10.0)
    val testParams = new TestParams(3, true, 20180308)
    val psoAlgoParams = new PSOAlgoParams(500, 100)
    val optAlgo = new PSO(psoAlgoParams)
    new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12)
    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
    println(f"Total run time: $runTime%7.2e seconds.\n")
  }
  val totalRunTime = (System.nanoTime() - totalRunTimeStart) / 1.0e9 // seconds
  println(f"\nRun Time: $totalRunTime%7.2e seconds.")

  /**
    * Run Time Varying with Number of Function Evaluations.
    */
  println("\n***\nRun Time Varying with Number of Function Evaluations.\n***\n")
  val totalRunTimeStart2 = System.nanoTime()
  val funcEvalMax = Array(1000, 2000, 3000, 4000, 5000)
  for (funcEvalNum <- funcEvalMax) {
    val runTimeStart = System.nanoTime()
    val conFuncParams = new ConFuncParams("cfSchwefel12", 100000, 10.0)
    val testParams = new TestParams(1, true, 20180308)
    val psoAlgoParams = new PSOAlgoParams(funcEvalNum, 100)
    val optAlgo = new PSO(psoAlgoParams)
    new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12)
    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
    println(f"Total run time: $runTime%7.2e seconds.\n")
  }
  val totalRunTime2 = (System.nanoTime() - totalRunTimeStart2) / 1.0e9 // seconds
  println(f"\nRun Time: $totalRunTime2%7.2e seconds.")
}
