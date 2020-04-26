package sandbox.test.monad

import sandbox.test.KafkaClient

object FreeMonadKafka extends App{

  // Algebra

  /**
   * Representing grammar using ADT (Algebraic Data Type).
   * ADT types need to be created to represent key-value operations
   */
  sealed trait StoreInterfaceA[A]
  case class Create[T](uri : String, value: T) extends StoreInterfaceA[Unit]
  //case class RetrieveAll() extends KafkaStoreA
  case class Delete[T](uri: String, value: T) extends StoreInterfaceA[Unit]

  // Creating type
  import cats.free.Free
  type StoreInterface[A] = Free[StoreInterfaceA, A]

  // Constructors
  import cats.free.Free.liftF

  // Put does nothing
  def create[T](uri: String, value: T): StoreInterface[Unit] =
    liftF[StoreInterfaceA, Unit](Create[T](uri, value))

  // Get returns an optional value
  /*def get[T](key: String): KVStore[Option[T]] =
    liftF[KVStoreA, Option[T]](Get[T](key)) */

  // Deletes return nothing
  def delete[T](uri: String, value: T): StoreInterface[Unit] =
    liftF[StoreInterfaceA, Unit](Delete[T](uri, value))

  case class KafkaTopic(val name: String, val partitionCount: Int, val replicationFactor: Short){
    override def toString: String =
      s"{topicName: $name, partitionCount: $partitionCount, replicationFactor: $replicationFactor}";
  }

  // Program

  val uriVal = "localhost:9092"

  def program: StoreInterface[Option[KafkaTopic]] =
    for {
      _ <- create(uriVal, KafkaTopic("test_topic1", 3, 1.toShort))
      _ <- create(uriVal, KafkaTopic("test_topic2", 3, 1.toShort))
      _ <- delete(uriVal, KafkaTopic("test_topic1", 3, 1.toShort))
    }()



  // Impure compiler
  import cats.{Id, ~>}

  def impureComiler: StoreInterfaceA ~> Id =
    new (StoreInterfaceA ~> Id) {
      val kafkaClient = new KafkaClient("localhost:9092")

      def apply[A](fa: StoreInterfaceA[A]): Id[A] =
        fa match {
          case Create(uriVal, value) =>
            println(s"Create($uriVal, $value)")
            val topic = value.asInstanceOf[KafkaTopic]
            kafkaClient.createTopic(topic.name, topic.partitionCount, topic.replicationFactor)
            ()
          case Delete(uriVal, value) =>
            println(s"Delete($uriVal, $value)")
            val topic = value.asInstanceOf[KafkaTopic]
            kafkaClient.deleteTopic(topic.name)
            ()
        }
    }


  val result = program.foldMap(impureComiler)



}
