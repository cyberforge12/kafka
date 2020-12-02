package com.target.producer

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.{Properties, UUID}
import scala.io.StdIn

object ProducerApp extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("acks", "all")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val t = new java.util.Timer("Timer")
  val task = new java.util.TimerTask {
    def run() = {
      val uuid: String = UUID.randomUUID().toString
      producer.send(new ProducerRecord[String, String]("myTopic", "uuid", uuid))
//      println(uuid)
    }
  }
  t.schedule(task, 1000L, 1000L)

  while (true) {
    StdIn.readLine() match {
      case "stop" => t.cancel(); sys.exit(0)
      case _ => {println("commands: stop - stop stream and exit")}
    }
  }

}

