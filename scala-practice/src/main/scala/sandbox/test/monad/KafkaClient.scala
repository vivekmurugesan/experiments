package sandbox.test.monad

//import java.util
import java.util.Properties

import org.apache.kafka.clients.admin._
//import org.apache.kafka.common.{KafkaFuture, Node}
//import org.apache.kafka.common.acl.{AccessControlEntry, AclBinding, AclOperation, AclPermissionType}
//import org.apache.kafka.common.config.{ConfigResource, TopicConfig}
//import org.apache.kafka.common.resource.{Resource, ResourceType}

import scala.collection.JavaConverters._

class KafkaClient(val uriVal: String) {

  def createTopic(name: String, partitionCount: Int,
                  replicationFactor: Short): CreateTopicsResult = {
    val props = new Properties()
    props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, uriVal)
    val adminClient = AdminClient.create(props)
    println("creating topic")
    val newTopic = new NewTopic(name, partitionCount, replicationFactor.toShort)
    val result = adminClient.createTopics(List(newTopic).asJavaCollection)
    println("Topic creation done..")
    result
  }

  def listTopics() ={

    val props = new Properties()
    props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, uriVal)
    val adminClient = AdminClient.create(props)
    val result = adminClient.listTopics(new ListTopicsOptions().timeoutMs(10000).listInternal(true))
    println("list topics")
    println(result.listings().get())
    result.listings().get()
  }

  def deleteTopic(name: String) = {
    val props = new Properties()
    props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, uriVal)
    val adminClient = AdminClient.create(props)
    println("deleting topic")
    //val newTopic = new NewTopic(name, partitionCount, replicationFactor.toShort)
    println("Topic deletion done..")
    adminClient.deleteTopics(List(name).asJavaCollection)
  }

  def main(args: Array[String]): Unit = {



    // ACLs
    /*val newAcl = new AclBinding(new Resource(ResourceType.TOPIC, "my-secure-topic"),
      new AccessControlEntry("my-user", "*", AclOperation.WRITE, AclPermissionType.ALLOW))
    adminClient.createAcls(List(newAcl).asJavaCollection) */
    // similarly
    //adminClient.deleteAcls(???)
    //adminClient.describeAcls(???)

    // TOPICS

    /*val configs = Map(TopicConfig.CLEANUP_POLICY_CONFIG -> TopicConfig.CLEANUP_POLICY_COMPACT,
      TopicConfig.COMPRESSION_TYPE_CONFIG -> "gzip") */
    // settings some configs
    //newTopic.configs(configs.asJava)

    // similarly
    //adminClient.deleteTopics(topicNames, options)
    // adminClient.describeTopics(topicNames, options)

    // describe topic configs
    //adminClient.describeConfigs(List(new ConfigResource(ConfigResource.Type.TOPIC, TopicConfig.CLEANUP_POLICY_CONFIG)).asJavaCollection)

  }
}
