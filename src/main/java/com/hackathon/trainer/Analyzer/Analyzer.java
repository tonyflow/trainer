package com.hackathon.trainer.Analyzer;

import java.io.IOException;
import java.util.List;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.utils.IndicoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Analyzer {

	@Autowired
	Indico analyzer;
	
	/**
	 * 
	 * @param data
	 * @return
	 * @throws IndicoException
	 * @throws IOException
	 */
	public Integer TwitterAnalyzer(String[] data) throws IndicoException, IOException{
		return null;
	}
	

	/**
	 * Array of strings input for batch processing
	 * 
	 * @param data
	 * @return
	 * @throws IndicoException
	 * @throws IOException
	 */
	public Integer SentimentAnalyzer(String[] data) throws IndicoException, IOException{
		BatchIndicoResult predict = analyzer.sentiment.predict(data);
		return	 predict.getSentiment().stream().reduce(0, (sum,s)->sum+=s.intValue(), (sum1,sum2)->sum1+sum2);
	}
}
