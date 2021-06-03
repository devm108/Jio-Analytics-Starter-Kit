package producerconsumer

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer extends App {
  val props:Properties = new Properties()
  props.put("bootstrap.servers","192.168.1.128:9092")
  props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks" , "all")

  val producer = new KafkaProducer[String, String](props)
  val topic = "kafka.learning.sample"

  try {
    for (i <- 0 to 15) {
      val record = new ProducerRecord[String, String](topic, i.toString, "This is my first API" + i)
      val metadata = producer.send(record)
      printf(s"sent record(key=%s value=%s) " +
        "meta(partition=%d, offset=%d)\n",
        record.key(), record.value(), metadata.get().partition(),
        metadata.get().offset())
    }
  } catch {
    case e:Exception => e.printStackTrace()

  } finally {
    producer.close()
  }



}
