package producerconsumer

import java.util
import java.util.Properties
import java.util.regex.Pattern
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

object Consumer extends App {

  override def main(args: Array[String]): Unit = {
    val prop:Properties = new Properties()
    prop.put("bootstrap.servers","192.168.1.100:9092")
    prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("enable.auto.commit", "true")
    prop.put("auto.commit.interval.ms", "1000")

    val consumer = new KafkaConsumer(prop)

    //val tp1 = new TopicPartition("kafka.learning.sample",1)
    //val tp2 = new TopicPartition("kafka.learning.sample1",1)

    val topics = List("kafka.learning.sample")

    try {
      consumer.subscribe(topics.asJava)
      while (true) {
        val records = consumer.poll(10)
        for (record <- records.asScala) {
          println("Topic: " + record.topic() + ", Key: " + record.key() + ", Value: " + record.value() +
            ", Offset: " + record.offset() + ", Partition: " + record.partition())
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }




  }

}
