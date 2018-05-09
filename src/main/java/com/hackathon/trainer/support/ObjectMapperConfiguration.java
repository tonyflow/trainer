package com.hackathon.trainer.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfiguration {

	@Bean
	ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
}
