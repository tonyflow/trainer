package com.hackathon.trainer.data.ingestion;

import org.apache.spark.mllib.recommendation.ALS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Summoner {

	@Autowired
	ObjectMapper mapper;
	
	
	private void train() {
	}
}
