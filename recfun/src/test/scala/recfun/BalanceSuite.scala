package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  test("balance: no brackets") {
    assert(balance("helloes".toList))
  }

  test("balance: open close open close") {
    assert(balance("(dsg)(sadfd)".toList))
  }

  test("balance: one open") {
    assert(!balance("(".toList))
  }

  test("balance: empty string") {
    assert(balance("".toList))
  }

  test("balance: all open") {
    assert(!balance("((((((((".toList))
  }
}
