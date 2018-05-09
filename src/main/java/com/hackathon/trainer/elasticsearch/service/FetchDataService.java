package com.hackathon.trainer.elasticsearch.service;

import java.util.Map;

public interface FetchDataService {
	
	void fetch(String index);

	String get(String index,String documentType,String id);
}
