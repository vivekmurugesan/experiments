package sandbox.test.monad

import cats.instances.option._
import cats.instances.list._
import cats.syntax.applicative._

object MonadSyntaxExample extends App{

  val v1 = 1.pure[Option]
  val v2 = 1.pure[List]

  println(v1)
  println(v2)

  val v3 = (1,2,3).pure[List]
  val v4 = (1 to 10).pure[List]

  println(v3)
  println(v4)
}
