package com.hackathon.trainer.indico;

import io.indico.Indico;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndicoAnalyzer {

	@Value("${application.indico.key}")
	String indicoKey;
	
	@Bean
	Indico indico(){
		return new Indico(indicoKey);
	}
	
}
