package pso.ppsn2018

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import pso.cores.{ALCPSOSparkAlgoParams, _}
import pso.ALCPSOSpark

object DemoPPSN2018ALCPSOSpark2 extends App {
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
  println("\n***\nRun Time Varying with Function Dimensions.\n***\n")
  val totalRunTimeStart = System.nanoTime()
  val funcName = Array("cfSphere", "cfRosenbrock", "cfRastrigin", "cfGriewank")
  for (funcInd <- 1 to 4) {
    val runTimeStart = System.nanoTime()
    val conFuncParams = new ConFuncParams(funcName(funcInd - 1), 100000, 10.0)
    val testParams = new TestParams(30, true, 20180308)
    val alcpsoSparkAlgoParams = new ALCPSOSparkAlgoParams(500, 100)
    val optAlgo = new ALCPSOSpark(alcpsoSparkAlgoParams, sparkContext)
    if (funcInd == 1) {
      new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSphereDV)
    } else if (funcInd == 2) {
      new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrockDV)
    } else if (funcInd == 3) {
      new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRastriginDV)
    } else if (funcInd == 4) {
      new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewankDV)
    }
    val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
    println(f"Total run time: $runTime%7.2e seconds.\n")
  }
  val totalRunTime = (System.nanoTime() - totalRunTimeStart) / 1.0e9 // seconds
  println(f"\nRun Time: $totalRunTime%7.2e seconds.")
}
