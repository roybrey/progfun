package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if ((c > r) || (r < 0) || (c < 0)) throw new IllegalArgumentException
    else if ((c == r) || (c == 0)) 1
    else pascal(c, r-1) + pascal(c-1, r-1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def bal(open: Int, chars: List[Char]):Boolean = {
      if (open < 0) false
      else if (chars.isEmpty) open == 0
      else if (chars.head == '(') bal(open+1, chars.tail)
      else if (chars.head == ')') bal(open-1, chars.tail)
      else bal(open, chars.tail)
    }

    bal(0,chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
      if (money == 0) 1
      else if (money < 0 || coins.isEmpty) 0
      else countChange(money - coins.head, coins) +
        countChange(money, coins.tail)
  }


  def countChange2(money: Int, coins: List[Int]): Int = {
    def cg(money: Int, coins: List[Int], result: String): Int = {
      if (money == 0) {println(result); 1  }
      else if (money < 0 || coins.isEmpty) {/*println("|| " + money + " || " + result);*/ 0  }
      else cg(money - coins.head, coins, result.concat(coins.head.toString).concat(",")) +
        cg(money, coins.tail, result)
    }

    cg(money, coins, "")

  }
}