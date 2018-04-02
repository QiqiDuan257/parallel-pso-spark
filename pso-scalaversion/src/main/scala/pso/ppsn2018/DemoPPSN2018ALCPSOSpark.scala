package pso.ppsn2018

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import pso.cores._
import pso.ALCPSOSpark

object DemoPPSN2018ALCPSOSpark extends App {
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

  /**
    * Run Time Varying with Function Dimensions.
    */
//  println("\n***\nRun Time Varying with Function Dimensions.\n***\n")
//  val totalRunTimeStart = System.nanoTime()
//  val funDims = Array(10, 100, 1000, 10000, 100000)
//  for (funDim <- funDims) {
//    val runTimeStart = System.nanoTime()
//    val conFuncParams = new ConFuncParams("cfSchwefel12", funDim, 10.0)
//    val testParams = new TestParams(30, true, 20180308)
//    val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(500, 100)
//    val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
//    new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12DV)
//    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//    println(f"Total run time: $runTime%7.2e seconds.\n")
//  }
//  val totalRunTime = (System.nanoTime() - totalRunTimeStart) / 1.0e9 // seconds
//  println(f"\nRun Time: $totalRunTime%7.2e seconds.")

  /**
    * Run Time Varying with Number of Function Evaluations
    */
  println("\n***\nRun Time Varying with Number of Function Evaluations.\n***\n")
  val totalRunTimeStart2 = System.nanoTime()
  val funcEvalNums = Array(1000, 2000, 3000, 4000, 5000)
  for (funcEvalNum <- funcEvalNums) {
    val runTimeStart = System.nanoTime()
    val conFuncParams = new ConFuncParams("cfSchwefel12", 100000, 10.0)
    val testParams = new TestParams(30, true, 20180308)
    val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(funcEvalNum, 100)
    val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
    new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12DV)
    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
    println(f"Total run time: $runTime%7.2e seconds.\n")
  }
  val totalRunTime2 = (System.nanoTime() - totalRunTimeStart2) / 1.0e9 // seconds
  println(f"\nRun Time: $totalRunTime2%7.2e seconds.")

  /**
    * close the connection to the Spark clustering environment
    */
  sparkContext.stop()
}
