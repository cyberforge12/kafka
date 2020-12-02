package com.target.consumer

import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig, Topology}
import org.apache.kafka.streams.kstream.KStream
  import org.apache.kafka.common.serialization.Serdes

import java.util.Properties

object ConsumerApp extends App {

  val props = new Properties()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "consumer-app")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093")
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)

  val builder: StreamsBuilder = new StreamsBuilder()
  val source: KStream[String, String] = builder.stream("myTopic")
  source.foreach((key, value) => println(key + ": " + value))
  source.to("myTopicOut")

  val topology: Topology = builder.build
  println(topology.describe)


  val streams = new KafkaStreams(topology, props)

  streams.start()
}


