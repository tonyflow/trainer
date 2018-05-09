package com.hackathon.trainer.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {

	@Bean
	JavaSparkContext sparkContext(){
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("BI Trainer");
		sparkConf.setMaster("local[4]");
		return new JavaSparkContext(sparkConf);
	}
}
