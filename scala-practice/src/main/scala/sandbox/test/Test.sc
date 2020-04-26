import cats.Monoid
import cats.instances.string._

Monoid[String].combine("Hi", "There")
Monoid[String].empty

Monoid.apply[String].combine("Hi", "There")
Monoid.apply[String].empty

import cats.Semigroup

Semigroup[String].combine("Hi", "There")

import cats.Monoid
import cats.instances.int._

Monoid[Int].combine(1, 234)

import cats.Monoid
import cats.instances.int._
import cats.instances.option._

val a = Option(32)
val b = Option[Int](10)

Monoid[Option[Int]].combine(a,b)

// |+| is an operator used for combine

import cats.instances.string._
import cats.syntax.semigroup._

val strResult = "Hi" |+| "There" |+| Monoid[String].empty

import cats.instances.int._

val intResult = 1 |+| 23 |+| Monoid[Int].empty

import cats.instances.map._

val a = Map("a" -> 1, "b" -> 2)
val b = Map("b" -> 3, "c" -> 5)

a |+| b

import cats.instances.tuple._

val a = ("Hello", 123)
val b = ("World", 321)

a |+| b