package sandbox.test.monad.free

import java.util.concurrent.TimeUnit

import cats.{Id, ~>}
import cats.free.Free
import sandbox.test.monad.{FileClient, KafkaClient}

object FreeMonadStore {
  // Algebra
  /**
   * Representing grammar using ADT (Algebraic Data Type).
   * ADT types need to be created to represent key-value operations
   */
  sealed trait StoreActionA[A]
  case class Create[T](uri : String, value: T) extends StoreActionA[Option[T]]
  //case class RetrieveAll() extends KafkaStoreA
  case class Delete[T](uri: String, value: T) extends StoreActionA[Option[T]]
  case class GetAll[T](uri: String) extends StoreActionA[Unit]

  // Creating type
  type StoreAction[A] = Free[StoreActionA, A]

  // Constructors
  import cats.free.Free.liftF

  // Put does nothing
  def create[T](uri: String, value: T): StoreAction[Option[T]] =
    liftF[StoreActionA, Option[T]](Create[T](uri, value))

  def getAll[T](uri: String): StoreAction[Unit] =
    liftF[StoreActionA, Unit](GetAll[Unit](uri))

  // Get returns an optional value
  /*def get[T](key: String): KVStore[Option[T]] =
    liftF[KVStoreA, Option[T]](Get[T](key)) */

  // Deletes return nothing
  def delete[T](uri: String, value: T): StoreAction[Option[T]] =
    liftF[StoreActionA, Option[T]](Delete[T](uri, value))

  case class KafkaTopic(val name: String, val partitionCount: Int, val replicationFactor: Short){
    override def toString: String =
      s"{topicName: $name, partitionCount: $partitionCount, replicationFactor: $replicationFactor}";
  }

  // Program

  val uriVal = "localhost:9092"

  def program: StoreAction[Unit] =
    for {
      _ <- create(uriVal, KafkaTopic("test_topic3", 3, 1.toShort))
      _ <- create(uriVal, KafkaTopic("test_topic4", 3, 1.toShort))
      list <- getAll(uriVal)
      _ <- delete(uriVal, KafkaTopic("test_topic3", 3, 1.toShort))
      _ <- delete(uriVal, KafkaTopic("test_topic4", 3, 1.toShort))
      list <- getAll(uriVal)
    } yield ()



  // Impure compiler
  def impureComiler: StoreActionA ~> Id =
    new (StoreActionA ~> Id) {
      val kafkaClient = new KafkaClient("localhost:9092")

      def apply[A](fa: StoreActionA[A]): Id[A] =
        fa match {
          case Create(uriVal, value) =>
            println(s"Create($uriVal, $value)")
            val topic = value.asInstanceOf[KafkaTopic]
            val kafkaF = kafkaClient.createTopic(topic.name, topic.partitionCount, topic.replicationFactor)
            /*
              This is not the best practice.
              TODO: To rewrite with IO
             */
            while(!kafkaF.all().isDone)
              kafkaF.all().get(1, TimeUnit.SECONDS)
            println("done")
            Some(topic).asInstanceOf[A]
          case Delete(uriVal, value) =>
            println(s"Delete($uriVal, $value)")
            val topic = value.asInstanceOf[KafkaTopic]
            val kafkaF = kafkaClient.deleteTopic(topic.name)
            /*
              This is not the best practice.
              TODO: To rewrite with IO
             */
            while(!kafkaF.all().isDone)
              kafkaF.all().get(1, TimeUnit.SECONDS)
            println("done")
            Some(topic).asInstanceOf[A]
          case GetAll(uriVal) =>
            println(s"Getall from: $uriVal")
            kafkaClient.listTopics().iterator().forEachRemaining(println)
            ()
        }
    }


  val targetDir = "/tmp/test"

  def programFile: StoreAction[Unit] =
    for {
      _ <- create(targetDir, "testDir1")
      _ <- create(targetDir, "testDir2")
      _ <- delete(targetDir, "testDir1")
      _ <- create(targetDir, "testDir3")
      list <- getAll(targetDir)
    } yield ()



  // Impure compiler
  import cats.{Id, ~>}

  def impureComilerFile: StoreActionA ~> Id =
    new (StoreActionA ~> Id) {
      val fileClient = new FileClient(targetDir)

      def apply[A](fa: StoreActionA[A]): Id[A] =
        fa match {
          case Create(targetDir, value) =>
            println(s"File Create($targetDir, $value)")
            val dirName = value.asInstanceOf[String]
            val result = fileClient.create(dirName)
            Some(result).asInstanceOf[A]
          case Delete(targetDir, value) =>
            println(s"File Delete($targetDir, $value)")
            val dirName = value.asInstanceOf[String]
            val result = fileClient.delete(dirName)
            Some(result.toString).asInstanceOf[A]
          case GetAll(targetDir) =>
            println(s"Getall from: $targetDir")
            val iter = fileClient.list()
            iter.forEachRemaining(println)
            ()
        }
    }

  def main(args: Array[String]): Unit = {
    programFile.foldMap(impureComilerFile)
    program.foldMap(impureComiler)
  }


}
