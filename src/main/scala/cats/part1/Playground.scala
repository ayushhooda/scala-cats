package cats.part1

import cats.Eval

object Playground {

  val meaningOfLife: Eval[Int] = Eval.later {
    println("Learning Cats: computing abstractions and the meaning of life...")
    42
  }

  def main(args: Array[String]): Unit = {
    println(meaningOfLife)
  }

  // advanced stuff - Higher Kinded Types
  trait HigherKindedType[F[_]]

  trait SequenceChecker[F[_]] {
    def isSequential: Boolean
  }

  val listChecker: SequenceChecker[List] = new SequenceChecker[List] {
    override def isSequential: Boolean = true
  }


}
