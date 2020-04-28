package sandbox.test.monad.io

import cats.Monad
import cats.effect.Sync

object IOSampleTagless extends App{

  trait Console[F[_]] {
    def putStrLn(str: String): F[Unit]
    def readLn: F[String]
  }

  def program[F[_]: Monad] (implicit c: Console[F]): F[Unit] = ???
    /*for{
      _ <- c.putStrLn("Enter your name")
      n <- c.readLn
      _ <- c.putStrLn(s"Hello $n!!")
    }yield () */

  class StdConsole[F[_]: Sync] extends Console[F] {
    override def putStrLn(str: String): F[Unit] = Sync[F].delay(println(str))

    override def readLn: F[String] = Sync[F].delay(scala.io.StdIn.readLine)
  }
}
