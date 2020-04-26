package sandbox.test

import cats.instances.function._
import cats.syntax.functor._

object FunctionFunctor extends App{

  val func1: Int => Double =
    (x: Int) => x.toDouble

  val func2: Double => Double =
    (x: Double) => x * 2

  // Composition options
  val a = (func1 map func2)(1)

  val b = (func1 andThen func2) (1)

  val c = func2(func1(1))

  println(a)
  println(b)
  println(c)

  val func =
    ((x: Int) => x.toDouble).
      map(x => x+1).
      map(x => x *2).
      map(x => s"${x}!")

  println(func(123))
}
