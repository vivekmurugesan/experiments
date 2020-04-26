package sandbox.test.monad

import cats.Monad
import cats.syntax.functor._
import cats.syntax.flatMap._

object MonadSyntaxExample2 extends App{

  def sumOfSquare[F[_]: Monad] (a: F[Int], b: F[Int]): F[Int] =
    a.flatMap(x => b.map(y => x*x + y*y))

  import cats.instances.option._
  import cats.instances.list._

  println(sumOfSquare(Option(2), Option(3)))

  println(sumOfSquare(List(1,2,3), List(4,5,6)))

  // same code rewritten using for comprehension
  def sumOfSquare1[F[_] : Monad] (a: F[Int], b: F[Int]): F[Int] =
    for{
      x <- a
      y <- b
    }yield x*x + y*y

  println(sumOfSquare1(Option(2), Option(3)))

  println(sumOfSquare1(List(1,2,3), List(4,5,6)))

  // println(sumOfSquare1(3, 4)) --> doesn't work as it is not a monad
  // We can make use of Identity function using Id
  // Which transforms an atomic type into a single parameter constructor

  import cats.Id
  println(sumOfSquare1(3 : Id[Int], 4: Id[Int]))

  // As can be seen Id can be used to cast any value to a corresponding Id

  val test = "Test" : Id[String]

  val t = 123: Id[Int]

  val l = List(1,2,3) : Id[List[Int]]

  println(test)
  println(t)
  println(l)

  val a = Monad[Id].pure(3)

  val b = Monad[Id].flatMap(a)(_ + 1)

  println(a)
  println(b)

  import cats.syntax.functor._
  import cats.syntax.flatMap._

  val result =
  for {
    x <- a
    y <- b
  }yield a+b

  println(result)

  println(
    for {
      x <- a
      y <- b
    }yield (x,y)
  )
}
