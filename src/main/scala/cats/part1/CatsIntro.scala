package cats.part1

object CatsIntro extends App {

  // Eq
  val aComparison = 2 == "a string"
  // Note: here we are comparing different types, but still it compiles, so motive here is why to even let code to compile, by leveraging Eq type-class
  println(aComparison)

  // part 1 - type class import

  import cats.Eq

  // part 2 - import Type Class instances for the types you need
  import cats.instances.int._

  // part 3 - use the type class api
  val intEquality = Eq[Int]

  val aTypeSafeComparison = intEquality.eqv(2, 3) // false , but here code wont even compile if int is compared with string

  // part 4 - use extension methods (if applicable)

  import cats.syntax.eq._

  val anotherTypeSafeComp = 2 === 3 // false
  // val anotherTypeSafeComp = 2 === "3" // doesn't compile

  // part 5 - extending the TC operations to composite types, e.g., lists

  import cats.instances.list._

  val aListComparison = List(2) === List(3)

  // part 6 - create a type class instance for a custom type
  case class ToyCar(model: String, price: Double)

  // return true, if price are same
  implicit val toyCarEq: Eq[ToyCar] = Eq.instance[ToyCar] { (car1, car2) =>
    car1.price == car2.price
  }

  val compareTwoToyCars = ToyCar("Ferrari", 29.99) === ToyCar("Lamborghini", 29.99) // we're able to use === here

  // So to summarize, 3 imports are needed
  // import cats.YourTypeClass
  // import cats.instances.yourType._
  // import cats.syntax.yourTypeClass._

  // Note: if you're confused with too many imports, use
  // import cats._
  // import cats.implicits._

}
