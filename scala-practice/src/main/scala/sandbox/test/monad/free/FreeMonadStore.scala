package sandbox.test.monad.free

import cats.{Id, ~>}


import sandbox.test.KafkaClient
import sandbox.test.monad.FileClient

object FreeMonadKafka {
  // Algebra
  /**
   * Representing grammar using ADT (Algebraic Data Type).
   * ADT types need to be created to represent key-value operations
   */
  sealed trait StoreInterfaceA[A]
  case class Create[T](uri : String, value: T) extends StoreInterfaceA[Unit]
  //case class RetrieveAll() extends KafkaStoreA
  case class Delete[T](uri: String, value: T) extends StoreInterfaceA[Unit]
  case class GetAll[T](uri: String) extends StoreInterfaceA[Unit]

  // Creating type
  import cats.free.Free
  type StoreInterface[A] = Free[StoreInterfaceA, A]

  // Constructors
  import cats.free.Free.liftF

  // Put does nothing
  def create[T](uri: String, value: T): StoreInterface[Unit] =
    liftF[StoreInterfaceA, Unit](Create[T](uri, value))

  def getAll[T](uri: String): StoreInterface[Unit] =
    liftF[StoreInterfaceA, Unit](GetAll[T](uri))

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

  def program: StoreInterface[Unit] =
    for {
      _ <- create(uriVal, KafkaTopic("test_topic3", 3, 1.toShort))
      _ <- create(uriVal, KafkaTopic("test_topic4", 3, 1.toShort))
      _ <- delete(uriVal, KafkaTopic("test_topic3", 3, 1.toShort))
      list <- getAll(uriVal)
    } yield list



  // Impure compiler
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
          case GetAll(uriVal) =>
            println(s"Getall from: $uriVal")
            kafkaClient.listTopics()
            ()
        }
    }


  val targetDir = "/tmp/test"

  def programFile: StoreInterface[Unit] =
    for {
      _ <- create(targetDir, "testDir1")
      _ <- create(targetDir, "testDir2")
      _ <- delete(targetDir, "testDir1")
      _ <- create(targetDir, "testDir3")
      list <- getAll(targetDir)
    } yield list



  // Impure compiler
  import cats.{Id, ~>}

  def impureComilerFile: StoreInterfaceA ~> Id =
    new (StoreInterfaceA ~> Id) {
      val fileClient = new FileClient(targetDir)

      def apply[A](fa: StoreInterfaceA[A]): Id[A] =
        fa match {
          case Create(targetDir, value) =>
            println(s"File Create($targetDir, $value)")
            val dirName = value.asInstanceOf[String]
            fileClient.create(dirName)
            ()
          case Delete(targetDir, value) =>
            println(s"File Delete($targetDir, $value)")
            val dirName = value.asInstanceOf[String]
            fileClient.delete(dirName)
            ()
          case GetAll(targetDir) =>
            println(s"Getall from: $targetDir")
            val iter = fileClient.list()
            iter.forEachRemaining(println(_))
            ()
        }
    }

  def main(args: Array[String]): Unit = {
    programFile.foldMap(impureComilerFile)
    program.foldMap(impureComiler)
  }


}
