package sandbox.test.monad.free



object FreeMonadTest extends App{

  // Algebra

  /**
   * Representing grammar using ADT (Algebraic Data Type).
   * ADT types need to be created to represent key-value operations
   */
  sealed trait KVStoreA[A]
  case class Put[T](key : String, value: T) extends KVStoreA[Unit]
  case class Get[T](key: String) extends KVStoreA[Option[T]]
  case class Delete[T](key: String) extends KVStoreA[Unit]


  /**
   * 1. Create a type based on Free[_] and KVStoreA[_].
   * 2. Create smart constructors for KVStore[_] using liftF.
   * 3. Build a program out of key-value DSL operations.
   * 4. Build a compiler for programs of DSL operations.
   * 5. Execute our compiled program.
   */


  // 1. Creating type based on Free
  // Free type based on ADT
  import cats.free.Free
  type KVStore[A] = Free[KVStoreA, A]

  // 2. Create smart constructors using liftF
  // Create smart constructors using liftF
  import cats.free.Free.liftF

  // Put returns nothing
  def put[T](key: String, value: T): KVStore[Unit] =
    liftF[KVStoreA, Unit](Put[T](key, value))

  // Get returns an optional value
  def get[T](key: String): KVStore[Option[T]] =
    liftF[KVStoreA, Option[T]](Get[T](key))

  // Deletes return nothing
  def delete[T](key: String): KVStore[Unit] =
    liftF(Delete(key))

  // Updates reuses get and put

  def update[T](key: String, f: T => T): KVStore[Unit] =
    for {
      valOpt <- get[T](key)
      _ <- valOpt.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
    }yield ()

  // 3. Program to implement DSL operations

  def program: KVStore[Option[Int]] =
    for {
      _ <- put("wild-cats", 2)
      _ <- update[Int]("wild-cats", ( _ + 10))
      _ <- put("tame-cats", 5)
      n <- get[Int]("wild-cats")
      _ <- delete("tame-cats")
    }yield n

  // 4. Write a compiler/interpreter

  import cats.{Id, ~>}
  import scala.collection.mutable

  // Error handling for non-existing key or incorrect type is not handled.

  def impureComiler: KVStoreA ~> Id =
    new (KVStoreA ~> Id) {
      val kvStore = mutable.Map.empty[String, Any]

      def apply[A](fa: KVStoreA[A]): Id[A] =
        fa match {
          case Put(key, value) =>
            println(s"Put($key, $value)")
            kvStore(key) = value
            ()
          case Get(key) =>
            println(s"Get($key)")
            kvStore.get(key).map(_.asInstanceOf[A])
          case Delete(key) =>
            println(s"Delete($key)")
            kvStore.remove(key)
            ()
        }
    }


  val result: Option[Int] = program.foldMap(impureComiler)

  // Pure compiler using state monad.

  import cats.data.State

  type KVStoreState[A] = State[Map[String, Any], A]

  val pureCompiler: KVStoreA ~> KVStoreState = new (KVStoreA ~> KVStoreState){
    def apply[A](fa: KVStoreA[A]): KVStoreState[A] =
      fa match {
        case Put(key, value) => State.modify(_.updated(key, value))
        case Get(key) => State.inspect(_.get(key).map(_.asInstanceOf[A]))
        case Delete(key) =>State.modify(_ - key)
      }
  }

  val result1 : (Map[String, Any], Option[Int]) = program.foldMap(pureCompiler).run(Map.empty).value
}
