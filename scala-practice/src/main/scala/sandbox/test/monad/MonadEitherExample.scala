package sandbox.test.monad

object MonadEitherExample extends App{
  val either1: Either[String, Int] = Right(30)
  val either2: Either[String, Int] = Right(20)

  val one =
  for{
    a <- either1.right
    b <- either2.right
  } yield a + b

  println(one)

  val two =
  for{
    a <- either1
    b <- either2
  }yield a + b

  println(two)

  import cats.syntax.either._

  val a = 3.asRight[String] // Equivalent to val a: Either[String, Int] = Right(3)
  val b = 4.asRight[String]
  val three =
  for {
    x <- a
    y <- b
  }yield x*x + y*y

  println(three)

  def countPositives(nums: List[Int]) =
    nums.foldLeft(0.asRight[String]) {
      (accumulator, num) =>
        if(num>0) {
          accumulator.map(_+1)
        } else{
          Left("Negative stopping")
        }
    }

  println(countPositives(List(1,2,3)))

  println(countPositives(List(1,-2, 3)))

  val e1 = Either.catchOnly[NumberFormatException]("foo".toInt)

  val e2 = Either.catchNonFatal(sys.error("Error"))

  println(e1)
  println(e2)

  val e3 = Either.fromTry(scala.util.Try("foo".toInt))

  println(e3)

  val e4 = Either.fromTry(scala.util.Try(123.toInt))

  println(e4)

  val e5 = Either.fromOption[String, Int](None, "error")

  println(e5)

  val e6 = Either.fromOption[String, Int](Some(6), "error")

  println(e6)

  import cats.syntax.either._

  val v1 = "Error".asLeft[Int].getOrElse(0)

  val v2 = "Error".asLeft[Int].orElse(2.asRight[String])

  println(v1)
  println(v2)

  val x1 = -1.asRight[String].ensure("Must be a non negative value")(_>0)
  val x2 = 90.asRight[String].ensure("Must be a non negative value")(_>0)

  println(x1)
  println(x2)

  val y1 = -1.asLeft[Int].ensure("Must be a non negative value")(_>0)
  val y2 = 90.asLeft[Int].ensure("Must be a non negative value")(_>0)

  println(y1)
  println(y2)

  println(
  "error".asLeft[Int].recover{
    case _: String => -1
  }
  )

  println(
    "error".asLeft[Int].recoverWith{
      case _: String => Right(-1)
    }
  )


  println(
    "foo".asLeft[Int].leftMap(_.reverse)
  )

  println(
    6.asRight[String].bimap(_.reverse, _ * 7)
  )

  println(
    "bar".asLeft[Int].bimap(_.reverse, _ * 7)
  )

  println(
    "bar".asRight[Int].bimap(_ * 7, _.reverse)
  )

  println(123.asRight[String])
  println(123.asRight[String].swap)

  println(
    for {
      a <- 1.asRight[String]
      b <- 0.asRight[String]
      c <- if(b == 0) "DIV0".asLeft
      else (a/b).asRight[String]
    }yield c * 100
  )

  type Result[A] = Either[Throwable, A]

  import java.io.Serializable
  
  sealed trait LoginError extends Product with Serializable

  final case class UserNotFound(userName: String)
  extends LoginError

  final case class PasswordIncorrect(userName: String)
  extends LoginError

  case object UnexpectedError extends LoginError

  case class User(userName: String, password: String)

  type LoginResult = Either[LoginError, User]



}
