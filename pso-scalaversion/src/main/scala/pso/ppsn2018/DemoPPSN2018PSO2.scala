package pso.ppsn2018

import pso.cores._
import pso.PSO

object DemoPPSN2018PSO2 extends App {
  /**
    * Run Time Varying with Function Dimensions.
    */
  println("\n***\nRun Time Varying with Function Dimensions.\n***\n")
  val totalRunTimeStart = System.nanoTime()
  val funcName = Array("cfSphere", "cfRosenbrock", "cfRastrigin", "cfGriewank")
  for (funcInd <- 1 to 4) {
    val runTimeStart = System.nanoTime()
    val conFuncParams = new ConFuncParams(funcName(funcInd - 1), 100000, 10.0)
    val testParams = new TestParams(30, true, 20180308)
    val psoAlgoParams = new PSOAlgoParams(500, 100)
    val optAlgo = new PSO(psoAlgoParams)
    if (funcInd == 1) {
      new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSphere)
    } else if (funcInd == 2) {
      new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrock)
    } else if (funcInd == 3) {
      new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRastrigin)
    } else if (funcInd == 4) {
      new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewank)
    }
    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
    println(f"Total run time: $runTime%7.2e seconds.\n")
  }
  val totalRunTime = (System.nanoTime() - totalRunTimeStart) / 1.0e9 // seconds
  println(f"\nRun Time: $totalRunTime%7.2e seconds.")
}
