package com.hackathon.trainer.support;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import com.google.common.collect.ImmutableSet;

@Configuration
public class ConversionServiceFactoryBeanConfiguration {

	@Bean
	ConversionService conversionService(){
		ConversionServiceFactoryBean cs = new ConversionServiceFactoryBean();
		return cs.getObject();
	}
	
}
