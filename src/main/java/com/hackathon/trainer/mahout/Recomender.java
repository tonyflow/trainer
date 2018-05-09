package com.hackathon.trainer.mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

public class Recomender {

	private final static Double THRESHOLD=0.1;
	
	public DataModel getDataModel(String fileName) throws IOException{
		return new FileDataModel(new File(fileName));
		
	};
	
	/**
	 * For finding similar users, we have to compare their interactions.
	 * There are several methods for doing this. One popular method is to compute
	 * the correlation coefficient between their interactions.
	 * @return 
	 */
	public static UserBasedRecommender createRecomender(DataModel dataModel){
		PearsonCorrelationSimilarity pcs;
		try {
			pcs = new PearsonCorrelationSimilarity(dataModel);
			ThresholdUserNeighborhood neighborhood = new ThresholdUserNeighborhood(Recomender.THRESHOLD, pcs, dataModel);
			return new GenericUserBasedRecommender(dataModel, neighborhood, pcs);
		} catch (TasteException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
