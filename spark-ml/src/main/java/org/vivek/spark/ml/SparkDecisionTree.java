package org.vivek.spark.ml;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import scala.Tuple2;

/**
 * It runs the decision tree model for classifying mnist data. It loads the 
 * data from couple csv files. One containing the test data and the other 
 * one containing the train data.
 * It then converts the data into an RDD containing LabeledPoint instances.
 * And then runs the model train on this RDD.
 * 
 * @author vivek
 *
 */
public class SparkDecisionTree {

	public static void main(String[] args) {
		String fileUri = "mnist_pca_lda_train.csv";
		String testFileUri = "mnist_pca_lda_test.csv";
		SparkConf conf = new SparkConf().setAppName("Logit Sample").set("spark.executor.memory", "8g");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> rawTrainData = sc.textFile(fileUri).cache();
		
		JavaRDD<String> rawTestData = sc.textFile(testFileUri).cache();
		
		/************* Training portion  **************************
		 * 1. Parse the lines into LabledPoint<label, features>
		 * */
		long startTime = System.currentTimeMillis();
		
		/** Creating LabledPoints from the input. */
		JavaRDD<LabeledPoint> labledPointsRDD = rawTrainData.map(new Function<String, LabeledPoint>() {

			public LabeledPoint call(String line) throws Exception {
				return formLabeledPoint(line);
			}
			
			private LabeledPoint formLabeledPoint(String line){
				String[] tokens = line.split(",");
				double[] result = new double[tokens.length-19];
				for(int i=1;i<tokens.length-18;i++){
					result[i-1] = Double.valueOf(tokens[i]);
				}
				double label = Double.valueOf(tokens[0]);
				LabeledPoint resultPoint = new LabeledPoint(label, new DenseVector(result));
				
				return resultPoint;
			}
		});
		
		/** Model parameter to be passed. */
		Integer numClasses = 10;
		HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		String impurity = "gini";
		Integer maxDepth = 5;
		Integer maxBins = 20;

		/** Training the model. */
		final DecisionTreeModel model = DecisionTree.trainClassifier(labledPointsRDD, numClasses,
				  categoricalFeaturesInfo, impurity, maxDepth, maxBins);
		
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(  " Model training time: " + (endTime-startTime) );
		
		/************** End of training portion *****************/ 
		
		startTime = System.currentTimeMillis();
		
		
		/************* Scoring/Testing portion  *******************
		 * 1. Parse the lines into LabledPoint<label, features>
		 * 2. Scoring and comparing the labels against prediction.
		 * 3. Then printing the computed accuracies on prediction.
		 * */
		
		/** Creating LabledPoints from the input. */
		JavaRDD<LabeledPoint> labledTestPointsRDD = rawTestData.map(new Function<String, LabeledPoint>() {

			public LabeledPoint call(String line) throws Exception {
				return formLabeledPoint(line);
			}
			
			private LabeledPoint formLabeledPoint(String line){
				String[] tokens = line.split(",");
				double[] result = new double[tokens.length-19];
				for(int i=1;i<tokens.length-18;i++){
					result[i-1] = Double.valueOf(tokens[i]);
				}
				double label = Double.valueOf(tokens[0]);
				LabeledPoint resultPoint = new LabeledPoint(label, new DenseVector(result));
				
				return resultPoint;
			}
		});
		
		
		JavaRDD<Tuple2<Object, Object>> predictionAndLabels = labledTestPointsRDD.map(new Function<LabeledPoint, 
				Tuple2<Object, Object>>(){

					private static final long serialVersionUID = 1L;

					public Tuple2<Object, Object> call(
							LabeledPoint inputPoint) throws Exception {
						Double prediction = model.predict(inputPoint.features());
						return new Tuple2<Object, Object>(prediction, inputPoint.label());
					}
			
		});	
		MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
	    double precision = metrics.precision();
		endTime = System.currentTimeMillis();
		
		System.out.println(" Precision::" + precision);
		
		System.out.println(" Model scoring time.. :" + (endTime-startTime)  );
		
		/******************* End of scoring/testing portion ****************/
		
	}
}
