package sandbox.test.monad.io

import cats.effect.IO

object SimpleIO extends App{

  val ioa = IO {
    println("Hey")
  }

  val program: IO[Unit] =
    for {
      _ <- ioa
      _ <- ioa
    } yield ()

  program.unsafeRunSync()

  def fib(n: Int, a: Long = 0, b: Long = 0): IO[Long] =
    IO(a+b).flatMap { b2 =>
      if(n > 0)
        fib(n-1, b, b2)
      else
        IO.pure(a)
    }

  println(fib(4).unsafeRunSync())

  IO.pure(20).flatMap(n => IO(println(s"Number: $n"))).unsafeRunSync()

  IO.pure(println("test wrong."))
}
