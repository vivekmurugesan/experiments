package sandbox

import cats.Show

object ExampleCats2 extends App{

  case class Cat(name: String, age: Int, color: String)

  object ShowInstances {
    implicit val catInstance = new Show[Cat] {
      override def show(t: Cat): String = s"${t.name} is a ${t.age} years old ${t.color} cat"
    }
  }

  implicit class ShowSyntax[T](value: T) {
    def show(implicit s: Show[T]): String = s.show(value)
  }

  import ShowInstances._

  println(Cat("Goa",5,"blue").show)
}
