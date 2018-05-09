package com.hackathon.trainer.spark;


import static org.junit.Assert.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.BinaryOperator;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.descriptive.rank.Median;
import org.apache.commons.math.util.MathUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.configuration.Algo;
import org.apache.spark.mllib.tree.impurity.Entropy;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.util.MLUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import scala.Tuple2;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.hackathon.trainer.base.AbstractTrainerTest;

public class RDDTests extends AbstractTrainerTest{

	@Autowired
	JavaSparkContext sparkContext; 
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testParallelizeCollection() throws Exception {
		List<Integer> list = ImmutableList.of(1,2,3,4,5,6,7);
		JavaRDD<Integer> rdd = sparkContext.parallelize(list);
		System.out.println("First element :: "+rdd.mapToPair(e->new Tuple2<>(e, e+1)).collectAsMap().get(2));
		Broadcast<JavaRDD<Integer>> broadcast = sparkContext.broadcast(rdd);
		sparkContext.parallelize(Arrays.asList(12,13,14)).map(e->broadcast.value().collect().add(e));
	}
	
	@Test
	public void testBroadcast() throws Exception {
		
	}
	
	@Test
	public void testAccumulator() throws Exception {
		
	}
	
	@Test
	public void testSplit() throws Exception {
		String[] res = "I|am|a|dog".split("\\|");
		for(String s : res){
			System.out.println(s);
		}
	}
	
	@Test
	public void testLala() throws Exception {
		JavaRDD<String> parallelize = sparkContext.parallelize(Arrays.asList("this is a test !"));
		JavaRDD<String[]> map = parallelize.map(f->{
			System.out.println("fasdgasfg");
			String[] split = f.split(" ");
			return split;
		});
	}
	
	/**
	 * Tokenize data strings using {StringTokenizer}. Better user .split()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExploreDataset() throws Exception {
		JavaRDD<String> ud = sparkContext.textFile("src/main/resources/u.user");
		JavaRDD<String[]> fields = ud.map(l->l.split("\\|"));
		
//		StringTokenizer tokens = new StringTokenizer(l, "|");
//		tokens.countTokens();
//		ArrayList<String> list = new ArrayList<String>();
//		while (tokens.hasMoreTokens()) {
//			list.add(tokens.nextToken());
//		}
//		return list;
		
		long count = fields.map((f)->f[0]).count();
		long genders = fields.map((f)-> f[2]).distinct().count();
		long occupations = fields.map((f)->f[3]).distinct().count();
		long codes = fields.map((f)->f[4]).distinct().count();
		System.out.println("Number of users :: "+count+" Number of genders :: "+genders+" Number of occupations :: "+occupations+" Number of codes :: "+codes);
		
		
	
	}
	
	@Test
	public void testStatistics() throws Exception {
		JavaRDD<String> ud = sparkContext.textFile("src/main/resources/u.user");
		JavaRDD<String[]> fields = ud.map(l->l.split("\\|"));
		Map<String, Long> occupations = fields.map((f)->f[3]).countByValue();
		for(Map.Entry<String, Long> entry : occupations.entrySet()){
			System.out.println("Key :: "+entry.getKey()+" Value :: "+entry.getValue());
		}
		
		Map<String, Long> genders = fields.map(f->f[2]).countByValue();
		for(Map.Entry<String, Long> entry : genders.entrySet()){
			System.out.println("Key :: "+entry.getKey()+" Value :: "+entry.getValue());
		}
	}
	
	@Test
	public void testExploreMinMax() throws Exception {
		JavaRDD<String> ud = sparkContext.textFile("src/main/resources/u.data");
		JavaRDD<String[]> fields = ud.map(l->l.split("\\t"));
		JavaRDD<Integer> ratings = fields.map(f->Integer.valueOf(f[2]));
		Integer max = ratings.reduce((p,r)->Math.max(p, r));
		Integer min = ratings.reduce((p,r)->Math.min(p, r));
		System.out.println("Max :: "+max);
		System.out.println("Min :: "+min);
		
		//Using apache commons math lib
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		ratings.collect().forEach(c->descriptiveStatistics.addValue(c.doubleValue()));
		double mean = descriptiveStatistics.getMean();
		double standardDeviation = descriptiveStatistics.getStandardDeviation();
		double percentile = descriptiveStatistics.getPercentile(50);
		System.out.println("Mean :: "+mean);
		System.out.println("Standard Deviation :: "+standardDeviation);
		System.out.println("Percentile :: "+percentile);

		//Spark basic statistics
		Vector vector = Vectors.dense(toDoubleArray(ratings.collect()));
	}
	
	
	/**
	 * A proof of concept test for our model
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Test
	public void testPOC() throws Exception {
		JavaRDD<String> ud = sparkContext.textFile("src/main/resources/es-data.dt");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-ddzhh:mm:ss.SSSX");
		JavaRDD<String[]> records = ud.map(l->l.split("\\|"));
		JavaRDD<LabeledPoint> data = records.map(c->{
			
			if (c.equals("")) {
				//enter 0 beacause it is empty
			}else if (formatter.parse(c.toString()) != null) {
				//enter old or new
			}else if (true) {
				
			}
			return new LabeledPoint(Double.valueOf(c[7]), Vectors.dense(new double[]{1,2,3,4}));
		});
		//logistic regression classification
		LogisticRegressionModel logisticRegressionTrainer = LogisticRegressionWithSGD.train(data.rdd(), 10);
		
		// classification
		SVMModel svmTrainer = SVMWithSGD.train(data.rdd(), 5);
		
		//Naive Bayes
		NaiveBayesModel naiveBayesTrainer = NaiveBayes.train(data.rdd());
		
		//decision Tree
		DecisionTreeModel decisionTreeTrainer = DecisionTree.train(data.rdd(), Algo.Classification(), Entropy.instance(), 5);
		
		//predicting
		double logisticPrediction = logisticRegressionTrainer.predict(data.first().features());
		double trueLabel = data.first().label();
		if (trueLabel!=logisticPrediction) {
			System.out.println("Wrong prediction");
		}
		
		
		
	}

	
	
	private double[] toDoubleArray(List<Integer> list) {
		double[] res = new double[list.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = list.get(i);
		}
		return res;
	}

}