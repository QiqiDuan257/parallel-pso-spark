package pso.demos

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import pso.CLPSOSpark
import pso.cores._

object DemoCLPSOSpark extends App {
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
//  val conFuncParams = new ConFuncParams("cfSphere", 10, 100.0, -100.0, 50.0, -100.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(30000, 10)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSphereDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfSphere", 30, 100.0, -100.0, 50.0, -100.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(200000, 40)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSphereDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRosenbrock", 10, 2.048)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(30000, 10)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrockDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRosenbrock", 30, 2.048)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(200000, 40)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRosenbrockDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfGriewank", 10, 600.0, -600.0, 200.0, -600.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(30000, 10)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewankDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfGriewank", 30, 600.0, -600.0, 200.0, -600.0)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(200000, 40)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfGriewankDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRastrigin", 10, 5.12, -5.12, 2.0, -5.12)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(30000, 10)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRastriginDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds

//  val runTimeStart = System.nanoTime()
//  val conFuncParams = new ConFuncParams("cfRastrigin", 30, 5.12, -5.12, 2.0, -5.12)
//  val testParams = new TestParams(3, true, 20180308)
//  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(200000, 40)
//  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
//  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfRastriginDV)
//  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
//  println(f"Total run time: $runTime%7.2e seconds.")

  // ************************************************* //
  val runTimeStart = System.nanoTime()
  val conFuncParams = new ConFuncParams("cfSchwefel12", 10000, 10.0)
  val testParams = new TestParams(5, true, 20180308)
  val clpsoSparkAlgoParams = new CLPSOSparkAlgoParams(500, 100)
  val optAlgo = new CLPSOSpark(clpsoSparkAlgoParams, sparkContext)
  new RunAlgo(testParams).runDV(optAlgo.optimize)(conFuncParams, ConFunc.cfSchwefel12DV)
  val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
  println(f"Total run time: $runTime%7.2e seconds.")

  /**
    * close the connection to the Spark clustering environment
    */
  sparkContext.stop()
}
