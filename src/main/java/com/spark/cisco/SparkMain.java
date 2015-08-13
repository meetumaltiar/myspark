package com.spark.cisco;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkMain {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("My App");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> lines = sc.textFile("src/main/resources/data.txt");

		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
					@Override
					public Iterable<String> call(String s) {
						return Arrays.asList(s.split(" "));
					}
				});

		JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
					@Override
					public Tuple2<String, Integer> call(String s) {
						return new Tuple2<String, Integer>(s, 1);
					}
				});

		JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
					}
				});

		List<Tuple2<String, Integer>> output = counts.collect();

		for (Tuple2<?, ?> tuple : output) {
			System.out.println(tuple._1() + "-> " + tuple._2());
		}
		sc.close();
	}
}