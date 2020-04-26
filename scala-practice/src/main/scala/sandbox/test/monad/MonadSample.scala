package sandbox.test.monad

import cats.Monad
import cats.instances.option._
import cats.instances.list._
import cats.instances.vector._

object MonadSample extends App{

  val opt1 = Monad[Option].pure(3)

  val opt2 = Monad[Option].flatMap(opt1)(a => Some(a+2))

  val opt3 = Monad[Option].map(opt2)(a => 100*a)

  println(opt3)

  val list1 = Monad[List].pure(3)

  val list2 = Monad[List].
    flatMap(List(1,2,3))(a => List(a, a*10))

  val list3 = Monad[List].map(list2)(a=>a+123)

  println(list1)
  println(list2)
  println(list3)


  println(Monad[Option].flatMap(Option(1))(a => Option(a*2)))

  println(Monad[List].flatMap(List(1,2,3))(a => List(a, a*10)))

  println(Monad[Vector].flatMap(Vector(1,2,3))(a => Vector(a, a*10)))

}
