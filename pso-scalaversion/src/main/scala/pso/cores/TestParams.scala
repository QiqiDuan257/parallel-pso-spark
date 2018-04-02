package pso.cores

import breeze.linalg.{DenseVector => DV}
import java.time.LocalDate
import java.time.format.DateTimeFormatter.BASIC_ISO_DATE

/**
  * Test Parameters.
  *
  * @param testNum the total number of tests (default: 30)
  * @param testIsOutputLog whether output the verbose log on the console (default: true)
  * @param testSeed the random seed to initialize the population (default: current date)
  */
class TestParams(testNum: Int = 30,
                 testIsOutputLog: Boolean = true,
                 testSeed: Int = LocalDate.now().format(BASIC_ISO_DATE).toInt) {

  require(testNum > 0, "the total number of tests must be larger than 0.")
  require(testSeed >= 0, "the random seed must be larger than or equal to 0.")

  val num: Int = testNum
  val isOutputLog: Boolean = testIsOutputLog
  private val seed: Int = testSeed
  /**
    * For reproducibility, it's necessary to set the random seed to initialize the population for each test.
    * It means that when plotting the convergence curve figure, at least the same starting point could
    * be obtained for different optimization algorithms on each test.
    * Note that set different random seeds to initialize the population for different tests.
    */
  val seeds: DV[Int] = DV.range(0, num) * 2 + seed // seeds increasing by step 2 (for simplicity)
}
