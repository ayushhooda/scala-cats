package cats.part2

// A type class with same properties as semigroup plus capability to provide empty values

// A Monoid is an semigroup with an identity operation

// Use cases: data structures meant to be combined, with a starting value
// Useful for general APIs
object Monoids {

  import cats.Monoid

  val intMonoid: Monoid[Int] = Monoid[Int]
  val combineInt: Int = intMonoid.combine(23, 999) // 1024
  val zero: Int = intMonoid.empty // 0

  import cats.instances.string._ // bring the implicit Monoid[String] in scope
  val emptyString: String = Monoid[String].empty // ""
  val combineString: String = Monoid[String].combine("I understand ", "monoids")

  import cats.instances.option._  // construct an implicit Monoid[Option[Int]]
  val emptyOption: Option[Int] = Monoid[Option[Int]].empty // None
  val combineOption: Option[Int] = Monoid[Option[Int]].combine(Option(2), Option.empty[Int]) // Some(2)

  // extension methods for Monoids - |+|
  import cats.syntax.monoid._ // either this one or cats.syntax.semigroup._ because monoid extends semigroup under the hood
  val combinedOptionFancy: Option[Int] = Option(3) |+| Option(7)

  // Todo 1: implement reduceByFold
  def combineFold[T](list: List[T])(implicit monoid: Monoid[T]): T = {
    list.foldLeft(monoid.empty)(_ |+| _)
  } // note: this is not possible in semigroups due to absence of empty method

  // Todo 2: combine a list of phonebooks as Map[String, Int]
  val phoneBooks: List[Map[String, Int]] = List(
    Map(
      "Alice" -> 235,
      "Bob" -> 647
    ),
    Map(
      "Charlie" -> 372,
      "Daniel" -> 889
    ),
    Map(
      "Tina" -> 123
    )
  )

  import cats.instances.map._ // import Monoid[Map[String, Int]]

  // Todo 3: shopping cart and online stores with Monoids
  case class ShoppingCart(items: List[String], total: Double)

  implicit val shoppingCartMonoid: Monoid[ShoppingCart] = Monoid.instance[ShoppingCart] (
    ShoppingCart(List(), 0.0), // identity
    (cart1, cart2) => ShoppingCart(cart1.items ++ cart2.items, cart1.total + cart2.total)
  )

  def checkout(shoppingCarts: List[ShoppingCart]): ShoppingCart = combineFold(shoppingCarts)


  def main(args: Array[String]): Unit = {
    println(combineFold[Int](List(1,2,3)))
    println(combineFold[String](List("I ", "like ", "monoids")))
    println(combineFold[Map[String, Int]](phoneBooks))
    println(checkout(List(ShoppingCart(List("phone", "laptop"), 200), ShoppingCart(List("tablet"), 100))))
  }

}
