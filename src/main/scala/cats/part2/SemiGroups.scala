package cats.part2

import cats.Semigroup

// A type class that can combine two values of the same type

// Useful for general APIs
// Use cases: data structures meant to be combined

// |+| is always associative, a |+| b = b |+| a
object SemiGroups {

  import cats.instances.int._

  val naturalIntSemigroup: Semigroup[Int] = Semigroup[Int]
  val intCombination: Int = naturalIntSemigroup.combine(2, 46) // addition

  import cats.instances.string._

  val naturalStringSemigroup: Semigroup[String] = Semigroup[String]
  val stringCombination: String = naturalStringSemigroup.combine("I love ", "cats") // concatenation

  def reduceInts(list: List[Int]): Int = list.reduce(naturalIntSemigroup.combine)

  def reduceStrings(list: List[String]): String = list.reduce(naturalStringSemigroup.combine)

  // general API : This is the power of semigroups
  def reduceThings[T](list: List[T])(implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)

  // Todo 1: Support a new type
  // hint: use same pattern we used with Eq

  case class Expense(id: Long, amount: Double)

  implicit val expenseSemigroup: Semigroup[Expense] = Semigroup.instance[Expense]{ (expense1, expense2) =>
    val id = Math.max(expense1.id, expense2.id)
    val amount = expense1.amount + expense2.amount
    Expense(id, amount)
  }

  // extension methods from Semigroup - |+|
  import cats.syntax.semigroup._
  val anIntSum: Int = 2 |+| 3 // requires  the presence of an implicit Semigroup[Int]
  val aStringSum: String = "we like " |+| "semigroups"
  val aCombinedExpense: Expense = Expense(4, 80) |+| Expense(56, 46)

  def reduceThings2[T](list: List[T])(implicit semigroup: Semigroup[T]): T = list.reduce(_ |+| _)

  def main(args: Array[String]): Unit = {
    println(intCombination)
    println(stringCombination)

    val numbers = (1 to 10).toList
    println(reduceInts(numbers))

    val strings = List("I'm ", "starting ", "to ", "like ", "semigroups")
    println(reduceStrings(strings))

    // general API
    println(reduceThings(numbers)) // compiler injects the implicit Semigroup[Int]
    println(reduceThings(strings)) // compiler injects the implicit Semigroup[String]

    import cats.instances.option._
    // compiler will produce an implicit Semigroup[Option[Int]]
    // compiler will produce an implicit Semigroup[Option[String]]

    val numberOptions: List[Option[Int]] = numbers.map(Option(_))
    println(reduceThings(numberOptions)) // an Option[Int] containing the sum of all the numbers

    val stringOptions: List[Option[String]] = strings.map(Option(_))
    println(reduceThings(stringOptions))

    val expenseList: List[Expense] = List(Expense(1, 10), Expense(5, 15), Expense(2, 10))
    println(reduceThings(expenseList))
  }

}
