package pso.cores

import org.scalatest.FunSuite
import breeze.linalg.{DenseMatrix => DM, DenseVector => DV}
import breeze.numerics.pow

class ConFuncTest extends FunSuite {

  test("testCfGriewank") {
    /**
      * Test Correctness Based on Samples
      */
    println(ConFunc.cfGriewank(DM.zeros[Double](3, 7)))
    // 0     0     0
    println(ConFunc.cfGriewankDV(DV.zeros[Double](7)))
    // 0.0

    println(ConFunc.cfGriewank(DM.ones[Double](5, 3)))
    // 0.6566    0.6566    0.6566    0.6566    0.6566
    println(ConFunc.cfGriewankDV(DV.ones[Double](3)))
    // 0.656567738230001

    println(ConFunc.cfGriewank(-DM.ones[Double](7, 3)))
    // 0.6566    0.6566    0.6566    0.6566    0.6566    0.6566    0.6566
    println(ConFunc.cfGriewankDV(-DV.ones[Double](3)))
    // 0.656567738230001

    val X1 = DM((0.0, 0.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0, 1.0, 1.0),
      (-1.0, -1.0, -1.0, -1.0, -1.0),
      (1.0, -1.0, 1.0, -1.0, 1.0),
      (1.0, 2.0, 3.0, 4.0, 5.0),
      (1.0, -2.0, 3.0, -4.0, 5.0),
      (5.0, 4.0, 3.0, 2.0, 0.0),
      (1.1, 1.2, 1.3, 1.4, -1.5))
    println(ConFunc.cfGriewank(X1))
    // 0    0.7289    0.7289    0.7289    1.0172    1.0172    0.9901    0.8708

    for (i <- 0 until X1.rows) {
      print(ConFunc.cfGriewankDV(X1(i, ::).t)); print(" ")
    } // 0.0 0.7289 0.7289 0.7289 1.0172 1.0172 0.9901 0.8708
    println()

    /**
      * Test Time Complexity Based on Samples
      */
    val funDims = pow(10, DV.range(1, 7))
    val testNum = 30
    for (fd <- funDims) {
      val X2 = DM.rand[Double](100, fd)
      val runTimeStart = System.nanoTime()
      for (_ <- 0 until testNum) {
        ConFunc.cfGriewank(X2)
      }
      val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime / testNum}%07.4e\n")
      //      1.00e+01 : 6.5348e-04
      //      1.00e+02 : 1.2359e-03
      //      1.00e+03 : 6.3599e-03
      //      1.00e+04 : 7.0572e-02
      //      1.00e+05 : 9.5322e-01
      //      1.00e+06 : 9.5895e+00 vs. 1.00e+06 : 1.0667e+00 (MATLAB)
    }
  }

  test("testCfRastrigin") {
    /**
      * Test Correctness Based on Samples
      */
    println(ConFunc.cfRastrigin(DM.zeros[Double](3, 7)))
    // 0     0     0
    println(ConFunc.cfRastriginDV(DV.zeros[Double](7)))
    // 0.0

    println(ConFunc.cfRastrigin(DM.ones[Double](5, 3)))
    // 3     3     3     3     3
    println(ConFunc.cfRastriginDV(DV.ones[Double](3)))
    // 3.0

    println(ConFunc.cfRastrigin(-DM.ones[Double](7, 3)))
    // 3     3     3     3     3     3     3
    println(ConFunc.cfRastriginDV(-DV.ones[Double](3)))
    // 3.0

    val X1 = DM((0.0, 0.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0, 1.0, 1.0),
      (-1.0, -1.0, -1.0, -1.0, -1.0),
      (1.0, -1.0, 1.0, -1.0, 1.0),
      (1.0, 2.0, 3.0, 4.0, 5.0),
      (1.0, -2.0, 3.0, -4.0, 5.0),
      (5.0, 4.0, 3.0, 2.0, 0.0),
      (1.1, 1.2, 1.3, 1.4, -1.5))
    println(ConFunc.cfRastrigin(X1))
    // 0    5.0000    5.0000    5.0000   55.0000   55.0000   54.0000   68.5500

    for (i <- 0 until X1.rows) {
      print(ConFunc.cfRastriginDV(X1(i, ::).t)); print(" ")
    } // 0.0 5.0 5.0 5.0 55.0 55.0 54.0 68.55
    println()

    /**
      * Test Time Complexity Based on Samples
      */
    val funDims = pow(10, DV.range(0, 7))
    val testNum = 30
    for (fd <- funDims) {
      val X2 = DM.rand[Double](100, fd)
      val runTimeStart = System.nanoTime()
      for (_ <- 0 until testNum) {
        ConFunc.cfRastrigin(X2)
      }
      val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime / testNum}%07.4e\n")
      //      1.00e+00 : 2.9341e-04
      //      1.00e+01 : 2.7527e-04
      //      1.00e+02 : 1.0256e-03
      //      1.00e+03 : 5.3694e-03
      //      1.00e+04 : 6.0116e-02
      //      1.00e+05 : 7.2294e-01
      //      1.00e+06 : 7.3671e+00 vs. 1.00e+06 : 8.7212e-01 (MATLAB)
    }
  }

  test("CfRosenbrock") {
    /**
      * Test Correctness Based on Samples
      */
    println(ConFunc.cfRosenbrock(DM.zeros[Double](3, 7)))
    // 6     6     6
    println(ConFunc.cfRosenbrockDV(DV.zeros[Double](7)))
    // 6.0

    println(ConFunc.cfRosenbrock(DM.ones[Double](5, 3)))
    // 0     0     0     0     0
    println(ConFunc.cfRosenbrockDV(DV.ones[Double](3)))
    // 0.0

    println(ConFunc.cfRosenbrock(-DM.ones[Double](7, 3)))
    // 808   808   808   808   808   808   808
    println(ConFunc.cfRosenbrockDV(-DV.ones[Double](3)))
    // 808.0

    val X1 = DM((0.0, 0.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0, 1.0, 1.0),
      (-1.0, -1.0, -1.0, -1.0, -1.0),
      (1.0, -1.0, 1.0, -1.0, 1.0),
      (1.0, 2.0, 3.0, 4.0, 5.0),
      (1.0, -2.0, 3.0, -4.0, 5.0),
      (5.0, 4.0, 3.0, 2.0, 0.0))
    println(ConFunc.cfRosenbrock(X1))
    // 4           0        1616         808       14814       30038       67530

    for (i <- 0 until X1.rows) {
      print(ConFunc.cfRosenbrockDV(X1(i, ::).t)); print(" ")
    } // 4.0 0.0 1616.0 808.0 14814.0 30038.0 67530.0
    println()

    /**
      * Test Time Complexity Based on Samples
      */
    val funDims = pow(10, DV.range(0, 7))
    val testNum = 30
    for (fd <- funDims) {
      val X2 = DM.rand[Double](100, fd)
      val runTimeStart = System.nanoTime()
      for (_ <- 0 until testNum) {
        ConFunc.cfRosenbrock(X2)
      }
      val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime / testNum}%07.4e\n")
    }
    //    1.00e+00 : 1.9724e-04
    //    1.00e+01 : 3.8825e-04
    //    1.00e+02 : 1.4672e-03
    //    1.00e+03 : 1.6999e-03
    //    1.00e+04 : 2.4201e-02
    //    1.00e+05 : 3.6345e-01
    //    1.00e+06 : 3.6332e+00 vs. 1.00e+06 : 1.4869e+00 (MATLAB)
  }

  test("testCfSchwefel12") {
    /**
      * Test Correctness Based on Samples
      */
    println(ConFunc.cfSchwefel12(DM.zeros(4, 13)))
    // 0     0     0     0
    println(ConFunc.cfSchwefel12DV(DV.zeros[Double](13)))
    // 0.0

    println(ConFunc.cfSchwefel12(DM.ones(5, 6)))
    // 91    91    91    91    91
    println(ConFunc.cfSchwefel12DV(DV.ones[Double](6)))
    // 91.0

    println(ConFunc.cfSchwefel12(-DM.ones[Double](2, 6)))
    // 91    91
    println(ConFunc.cfSchwefel12DV(-DV.ones[Double](6)))
    // 91.0

    val X1 = DM((0.0, 0.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0, 1.0, 1.0),
      (-1.0, -1.0, -1.0, -1.0, -1.0),
      (1.0, -1.0, 1.0, -1.0, 1.0),
      (1.0, 2.0, 3.0, 4.0, 5.0),
      (1.0, -2.0, 3.0, -4.0, 5.0),
      (5.0, 4.0, 3.0, 2.0, 0.0),
      (-5.0, 4.0, 3.0, 2.0, -1.0))
    println(ConFunc.cfSchwefel12(X1))
    // 0    55    55     3   371    19   642    55

    for (i <- 0 until X1.rows) {
      print(ConFunc.cfSchwefel12DV(X1(i, ::).t)); print(" ")
    } // 0.0 55.0 55.0 3.0 371.0 19.0 642.0 55.0
    println()

    /**
      * Test Time Complexity Based on Samples
      */
    val funDims = pow(10, DV.range(0, 5))
    val testNum = 5
    for (fd <- funDims) {
      val X2 = DM.rand[Double](100, fd)

      val runTimeStart = System.nanoTime()
      for (_ <- 0 until testNum) {
        ConFunc.cfSchwefel12(X2)
      }
      val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime / testNum}%07.4e\n")

      val runTimeStart2 = System.nanoTime()
      for (_ <- 0 until testNum) {
        for (i <- 0 until X2.rows) {
          ConFunc.cfSchwefel12DV(X2(i, ::).t)
        }
      }
      val runTime2 = (System.nanoTime() - runTimeStart2) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime2 / testNum}%07.4e\n")
    }
    //    1.00e+00 : 3.3723e-04
    //    1.00e+01 : 1.2857e-03
    //    1.00e+02 : 5.0343e-03
    //    1.00e+03 : 2.0665e-01
    //    1.00e+04 : 2.7512e+01 vs. 1.00e+04 : 2.0403e+01 (MATLAB)
  }

  test("testCfSphere") {
    /**
      * Test Correctness Based on Samples
      */
    println(ConFunc.cfSphere(DM.zeros(3, 7)))
    // 0     0     0
    println(ConFunc.cfSphereDV(DV.zeros[Double](7)))
    // 0.0

    println(ConFunc.cfSphere(DM.ones(5, 3)))
    // 3     3     3     3     3
    println(ConFunc.cfSphereDV(DV.ones[Double](3)))
    // 3.0

    println(ConFunc.cfSphere(-DM.ones[Double](7, 3)))
    // 3     3     3     3     3     3     3
    println(ConFunc.cfSphereDV(-DV.ones[Double](3)))
    // 3.0

    val X1 = DM((0.0, 0.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0, 1.0, 1.0),
      (-1.0, -1.0, -1.0, -1.0, -1.0),
      (1.0, -1.0, 1.0, -1.0, 1.0),
      (1.0, 2.0, 3.0, 4.0, 5.0),
      (1.0, -2.0, 3.0, -4.0, 5.0),
      (5.0, 4.0, 3.0, 2.0, 0.0),
      (1.1, 1.2, 1.3, 1.4, -1.5))
    println(ConFunc.cfSphere(X1))
    // 0    5.0000    5.0000    5.0000   55.0000   55.0000   54.0000    8.5500

    for (i <- 0 until X1.rows) {
      print(ConFunc.cfSphereDV(X1(i, ::).t)); print(" ")
    } // 0.0 5.0 5.0 5.0 55.0 55.0 54.0 8.55
    println()

    /**
      * Test Time Complexity Based on Samples
      */
    val funDims = pow(10, DV.range(0, 7))
    val testNum = 30
    for (fd <- funDims) {
      val X2 = DM.rand[Double](100, fd)
      val runTimeStart = System.nanoTime()
      for (_ <- 0 until testNum) {
        ConFunc.cfSphere(X2)
      }
      val runTime = (System.nanoTime() - runTimeStart) / 1.0e9 // seconds
      println(f"${fd.toDouble}%07.2e : ${runTime / testNum}%07.4e\n")
    }
    //    1.00e+00 : 2.2150e-04
    //    1.00e+01 : 9.6668e-05
    //    1.00e+02 : 1.8342e-04
    //    1.00e+03 : 5.2158e-04
    //    1.00e+04 : 8.7559e-03
    //    1.00e+05 : 1.9690e-01
    //    1.00e+06 : 1.9419e+00 vs. 1.00e+06 : 3.0678e-01 (MATLAB)
  }

}
