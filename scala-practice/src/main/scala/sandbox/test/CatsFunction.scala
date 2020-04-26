package sandbox.test

import cats.instances.function._
import cats.syntax.functor._

import cats.Functor

object CatsFunction extends App{

  val func1 = (a: Int) => a+1
  val func2 = (a: Int) => a * 2
  val func3 = (a: Int) => s"${a}!"

  val func4 = func1.map(func2.map(func3))

  println(func4(123))

  def doMath[F[_]](start: F[Int])
                  (implicit functor: Functor[F]): F[Int] =
    start.map(x => x + 1 * 2)

  import cats.instances.option._
  import cats.instances.list._

  val result = doMath(Option(20))

  println(result)

  val result1 = doMath(List(1,2,3))

  println(result1)

  implicit val optionFunctor: Functor[Option] =
    new Functor[Option] {
      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
    }
}
