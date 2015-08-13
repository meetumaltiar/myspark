package com.spark.cisco

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkApp extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val lines = sc.textFile("src/main/resources/data.txt")
  val wordCount = lines.map(line => line.toLowerCase).flatMap(line => line.split(" ")).groupBy(word => word).map { case (word, wordList) => (word, wordList.size) }
  wordCount foreach { case (word, count) => println(s" $word -> $count") }
  sc.stop()
}