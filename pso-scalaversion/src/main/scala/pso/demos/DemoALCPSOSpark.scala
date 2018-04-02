package pso.demos

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import pso.ALCPSOSpark
import pso.cores._

object DemoALCPSOSpark extends App {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  /**
    * connect to the Spark clustering environment
    */
  val sparkConf = new SparkConf()
    .setAppName("PSOSpark")
    .setMaster("spark://dc001.syhlab:7077")
    .setJars(List("D:\\Spark\\Submit_to_Spark_Clustering\\pso-scalaversion.jar"))
  val sparkContext = new SparkContext(sparkConf)

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfSphere", 30, 100.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(200000, 20)
//  val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSphereDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRosenbrock", 30, 10.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(200000, 20)
//  val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrockDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfGriewank", 30, 600.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(200000, 20)
//  val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewankDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRastrigin", 30, 5.12)
//  val testParams = new TestParams(3, true, 20180308)
//  val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(200000, 20)
//  val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRastriginDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
  val runTimeStart = System.nanoTime()
  val conFuncParams = new ConFuncParams("cfSchwefel12", 10000, 10.0)
  val testParams = new TestParams(5, true, 20180308)
  val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(500, 100)
  val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12DV)
  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
  println(f"Total run time: $runTime%7.2e seconds.")

  /**
    * close the connection to the Spark clustering environment
    */
  sparkContext.stop()
}
