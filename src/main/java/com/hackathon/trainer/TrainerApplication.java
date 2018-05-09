package com.hackathon.trainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hackathon.trainer.elasticsearch.configuration.ElasticSearchConfiguration;
import com.hackathon.trainer.elasticsearch.configuration.ElasticSearchConfigurationProperties;
import com.hackathon.trainer.indico.IndicoAnalyzer;
import com.hackathon.trainer.spark.SparkConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ElasticSearchConfigurationProperties.class)
public class TrainerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainerApplication.class, args);
	}
}
