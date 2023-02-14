package cats.part1

object TypeClasses {

  case class Person(name: String, age: Int)

  // part 1 - type class definition
  trait JSONSerializer[T] {
    def toJson(value: T): String
  }

  // part 2 - create implicit type class instances
  implicit object StringSerializer extends JSONSerializer[String] {
    override def toJson(value: String): String = "\"" + value + "\""
  }

  implicit object IntSerializer extends JSONSerializer[Int] {
    override def toJson(value: Int): String = value.toString
  }

  implicit object PersonSerializer extends JSONSerializer[Person] {
    override def toJson(value: Person): String =
      s"""
         |{"name" : ${value.name}, "age" : ${value.age} }
         |""".stripMargin.trim
  }

  // part 3 - offer some API
  def convertListToJSON[T](list: List[T])(implicit serializer: JSONSerializer[T]): String = {
    list.map(serializer.toJson).mkString("[", ",", "]")
  }

  // part 4 - extending the existing types via extension methods
  object JSONSyntax {
    implicit class JSONSerializable[T](value: T)(implicit serializer: JSONSerializer[T]) {
      def toJson: String = serializer.toJson(value)
    }
  }


  def main(args: Array[String]): Unit = {
    println(convertListToJSON(List(Person("person-1", 28), Person("person-2", 45))))
    val bob = Person("bob", 35)
    import JSONSyntax._
    bob.toJson
  }

}
