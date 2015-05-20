package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(-4)
    val s5 = singletonSet(-5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val s8 = singletonSet(8)
    val s9 = singletonSet(9)
    val sn15 = singletonSet(-15)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1) === true, "Singleton-contains")
      assert(contains(s1, 2) === false, "Singleton-doesnt-contain")

    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect") {
    new TestSets {
      val u1 = union(s1, s2)
      val u2 = union(s2, s3)
      val i1 = intersect(u1, u2)
      assert(!contains(i1, 1), "Intersect 1")
      assert(contains(i1, 2), "Intersect 2")
      assert(!contains(i1, 3), "Intersect 3")
    }
  }

  test("diff") {
    new TestSets {
      val u1 = union(union(s1, s2), s3)
      val u2 = union(s2, s3)
      val d1 = diff(u1, u2)
      val d2 = diff(u2, u1)
      assert(contains(d1, 1), "diff 1")
      assert(!contains(d1, 2), "diff 2")
      assert(!contains(d1, 3), "diff 3")

      assert(!contains(d2, 1), "diff2 1")
      assert(!contains(d2, 2), "diff2 2")
      assert(!contains(d2, 3), "diff2 3")
    }
  }

  test("filter") {
    new TestSets {
      val u1 = union(union(s1, s2), s3)
      val f1 = filter(u1, (x: Int) => x == 3 )

      assert(!contains(f1, 1), "filter 1")
      assert(!contains(f1, 2), "filter 2")
      assert(contains(f1, 3), "filter 3")

    }
  }

  test("forall") {
    new TestSets {
      val u1 = union(union(s1, s2), s3)
      val u2 = union(union(union(s1, s2), s3), s4)
      val u3 = union(s4, s5)


      assert(forall(u1, (x: Int) => x > 0 ), "forall-positive 1")
      assert(!forall(u2, (x: Int) => x > 0 ), "forall-positive 2")
      assert(!forall(u3, (x: Int) => x > 0 ), "forall-positive 3")

      assert(!forall(u1, (x: Int) => x < 0 ), "forall-negative 1")
      assert(!forall(u2, (x: Int) => x < 0 ), "forall-negative 2")
      assert(forall(u3, (x: Int) => x < 0 ), "forall-negative 3")

      val u4 = union(s2,s4)
      assert(forall(u4, (x: Int) => x % 2 == 0 ), "forall-even 1")
      assert(!forall(u1, (x: Int) => x % 2 == 0 ), "forall-even 2")

    }
  }

  test("exists") {
    new TestSets {
      val u1 = union(union(s1, s2), s3)
      val u2 = union(union(union(s1, s2), s3), s4)
      val u3 = union(s4, s5)


      assert(exists(u1, (x: Int) => x > 0 ), "exists-positive 1")
      assert(exists(u2, (x: Int) => x > 0 ), "exists-positive 2")
      assert(!exists(u3, (x: Int) => x > 0 ), "exists-positive 3")

      assert(!exists(u1, (x: Int) => x < 0 ), "exists-negative 1")
      assert(exists(u2, (x: Int) => x < 0 ), "exists-negative 2")
      assert(exists(u3, (x: Int) => x < 0 ), "exists-negative 3")

      val u4 = union(s2,s4)
      val u5 = union(s3,s5)
      assert(forall(u4, (x: Int) => x % 2 == 0 ), "exists-even 1")
      assert(!forall(u5, (x: Int) => x % 2 == 0 ), "exists-even 2")

    }
  }


  test("map") {
    new TestSets {
      val u1 = union(union(union(s1, s2), s3), s5)
      val u2 = union(union(union(s3, s6), s9), sn15)
      val m1 = map(u1,(x: Int) => x * 3 )

      printSet(u2)
      printSet(m1)


      assert(forall(m1, (x: Int) => contains(u2,x) ) , "map 1")
      assert(forall(u2, (x: Int) => contains(m1,x) ) , "map 2")
      assert(!forall(u2, (x: Int) => contains(u1,x) ) , "map 3")


    }
  }

}
