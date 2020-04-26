package sandbox.test

import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FutureTest {

  val future1 = {
    val r = new Random(0L);

    val x = Future(r.nextInt)

    for{
      a <- x
      b <- x
    }yield (a,b)
  }

  val future2 = {
    val r = new Random(0L)

    for{
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    }yield (a,b)
  }

  def main(args: Array[String]): Unit = {
    val result1 = Await.result(future1, 1.second)
    val result2 = Await.result(future2, 2.second)

    println(result1)
    println(result2)

  }
}
