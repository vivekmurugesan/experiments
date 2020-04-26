package sandbox

import cats.Show
import cats.instances.int._
import cats.instances.string._

object ExampleCatsShow extends App{

  val showInt: Show[Int] = Show[Int]
  val showString: Show[String] = Show[String]

  println(showInt.show(12345))
  println(showString.show("Test"))

  import cats.syntax.all._

  println("Vivek".show)
}
