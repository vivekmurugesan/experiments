package sandbox.test.monad.io

import cats.effect.IO

object IOSample extends App{

  val program: IO[Unit] =
    for{
      _ <- IO(println("Enter your name"))
      n <- IO(scala.io.StdIn.readLine)
      _ <- IO(println(s"Hello $n!!"))
    }yield ()

  program.unsafeRunSync()
}
