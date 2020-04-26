package sandbox.test

import cats.Functor
import cats.instances.list._
import cats.instances.option._

object FunctorList extends App{

  val list = List(1,2,3)

  val list2 = Functor[List].map(list)(_ * 2)

  val option1 = Option(123)

  val option2 = Functor[Option].map(option1)(_.toString)

  println(list2)
  println(option2)

  val func = (x: Int) => x + 1

  val liftedFunc = Functor[Option].lift(func)

  println(liftedFunc(Option(1)))
  println(func(1))
}
