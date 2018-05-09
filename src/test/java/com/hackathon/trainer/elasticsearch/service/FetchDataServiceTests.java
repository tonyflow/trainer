package com.hackathon.trainer.elasticsearch.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hackathon.trainer.base.AbstractTrainerTest;

public class FetchDataServiceTests extends AbstractTrainerTest{

	@Autowired
	FetchDataService service;
	
	/**
	 * Change elastic search url in application yml to fetch production data
	 * @throws Exception
	 */
	@Test
	public void testFeatchAndCreate() throws Exception {
		service.fetch("mtel_user_idx_1_3");
	}
	
	@Test
	public void testGet() throws Exception {
		System.out.println("Result :: "+service.get("user_idx", "user", "4"));;
	}
	
}
