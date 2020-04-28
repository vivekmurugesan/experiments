package sandbox.test.monad

import cats.effect.IO

object RefTrans extends App{

  val e = 123
  val v1 = (e, e)
  val v2 = (123, 123)

  println(v1)
  println(v2)

  val e1 = println("hey")

  val v3 = (e1, e1)

  val v4 = ((println("hey"), println("hey")))

  val e2 = IO(println("hey"))

  println("::")
  e2.unsafeRunSync()

  val v5 = (e1, e1)

  val v6 = (IO(println("hey")), IO(println("hey")))
}
