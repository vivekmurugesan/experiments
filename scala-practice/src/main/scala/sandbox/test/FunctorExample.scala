package sandbox.test

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FunctorExample extends App{

  val result = List(1,2,3).map(n => n+1)
  println(result)

  val result1 = List(1,2,3).map(n => n+1).map(n => n*2).map(n => s"${n}!")

  println(result1)

  val future: Future[String] =
    Future(123).map(n => n+1).map(n=>n*2).map(n => s"${n}!")

  Await.result(future, 1.second)

  println(future)
}
