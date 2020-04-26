package sandbox.test

object KafkaClientTest {

  def main(args: Array[String]): Unit = {
    val kafkaClient = new KafkaClient("localhost:9092")

    kafkaClient.createTopic("test_topic", 3, 1)
    kafkaClient.listTopics()
    kafkaClient.deleteTopic("test_topic")
    kafkaClient.listTopics()
  }
}
