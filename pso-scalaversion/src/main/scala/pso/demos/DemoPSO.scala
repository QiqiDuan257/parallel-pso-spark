package pso.demos

import pso.cores._
import pso.PSO

object DemoPSO extends App {
  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfSphere", 10, 100.0, -100.0, 50.0, -100.0)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(30000, 10)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSphere)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfSphere", 30, 100.0, -100.0, 50.0, -100.0)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(200000, 40)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSphere)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRosenbrock", 10, 2.048)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(30000, 10)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrock)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRosenbrock", 30, 2.048)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(200000, 40)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrock)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfGriewank", 10, 600.0, -600, 200.0, -600.0)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(30000, 10)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewank)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfGriewank", 30, 600.0, -600.0, 200.0, -600.0)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(200000, 40)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewank)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRastrigin", 10, 5.12, -5.12, 2.0, -5.12)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(30000, 10)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRastrigin)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRastrigin", 30, 5.12, -5.12, 2.0, -5.12)
//  val testParams = new TestParams(30, true, 20180308)
//  val psoAlgoParams = new PSOAlgoParams(200000, 40)
//  val optAlgo = new PSO(psoAlgoParams)
//  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfRastrigin)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
  val runTimeStart = System.nanoTime()
  val conFuncParams = new ConFuncParams("cfSchwefel12", 10000, 10.0)
  val testParams = new TestParams(5, true, 20180308)
  val psoAlgoParams = new PSOAlgoParams(500, 100)
  val optAlgo = new PSO(psoAlgoParams)
  new RunAlgo(testParams).run(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12)
  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
  println(f"Total run time: $runTime%7.2e seconds.")
}
