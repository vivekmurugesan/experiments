package sandbox.test.monad

object FlatMapTest extends App{

  val result =
  for {
    x <- (1 to 3).toList
    y <- (4 to 5).toList
  }yield (x,y)

  println(result)
}
