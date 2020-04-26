package sandbox

object IoMonod extends App{

  def greet() = {
    println("Enter your name:")
    val name = scala.io.StdIn.readLine()
    println(s"Hello $name")
  }
}
