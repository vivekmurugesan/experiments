package sandbox

object BooleanMonoid extends App{

  trait Semigroup[T] {
    def combine(a: T, b: T): T
  }

  trait Monoid[T] extends Semigroup[T] {
    def empty: T
  }

  implicit val booleanAndMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true
    override def combine(a: Boolean, b: Boolean): Boolean = a && b
  }

  implicit val booleanOrMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(a: Boolean, b: Boolean): Boolean = a || b
  }

}
