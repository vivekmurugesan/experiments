package org.vivek.spark.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.DenseVector;
import org.apache.spark.mllib.regression.LabeledPoint;

import scala.Tuple2;

/**
 * It runs the binary logit model for classifying mnist data. It loads the 
 * data from couple csv files. One containing the test data and the other 
 * one containing the train data.
 * It then converts the data into an RDD containing LabeledPoint instances.
 * And then runs the model train on this RDD.
 * @author vivek
 *
 */
public class SparkLogitWithPCAAttrs {

	public static void main(String[] args) {

		String fileUri = "mnist_pca_lda_train.csv";
		String testFileUri = "mnist_pca_lda_test.csv";
		SparkConf conf = new SparkConf().setAppName("Logit Sample").set("spark.executor.memory", "4g");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> rawTrainData = sc.textFile(fileUri).cache();
		
		JavaRDD<String> rawTestData = sc.textFile(testFileUri).cache();
		
		/************* Training portion  **************************
		 * 1. Parse the lines into LabledPoint<label, features>
		 * 2. Run through in a loop for all 45 combinations of features.
		 * 3. Filter the RDD for the given pair of labels.
		 * 4. Transform the entries into 0 and 1.
		 * 5. Run the logit model for every filtered RDDs.
		 * */
		long startTime = System.currentTimeMillis();
		
		/** Creating LabledPoints from the input. */
		JavaRDD<LabeledPoint> labledPointsRDD = rawTrainData.map(new Function<String, LabeledPoint>() {

			public LabeledPoint call(String line) throws Exception {
				return formLabeledPoint(line);
			}
			
			private LabeledPoint formLabeledPoint(String line){
				String[] tokens = line.split(",");
				double[] result = new double[9];
				for(int j=0,i=tokens.length-9;i<tokens.length;i++,j++){
					result[j] = Double.valueOf(tokens[i]);
				}
				double label = Double.valueOf(tokens[0]);
				LabeledPoint resultPoint = new LabeledPoint(label, new DenseVector(result));
				
				return resultPoint;
			}
		});
		
		LogisticRegressionModel[] model = new LogisticRegressionModel[45];
		
		int k=0;
		for(double i=0;i<=9;i++){
			for(double j=1;j<=9;j++){
				if(j>i){
					final double label1 = i;
					final double label2 = j;
					/** Filtering for lables i and j */
					JavaRDD<LabeledPoint> filteredRDD = labledPointsRDD.filter(new Function<LabeledPoint, Boolean>(){

						public Boolean call(LabeledPoint point) throws Exception {
							boolean result = false;
							if(point.label() == label1 || point.label() == label2)
								result = true;
							return result;
						}
						
					});
					
					/** Mapping input into binary */
					JavaRDD<LabeledPoint> transformedRDD = filteredRDD.map(new Function<LabeledPoint, LabeledPoint>(){

						public LabeledPoint call(LabeledPoint point)
								throws Exception {
							double newLabel = point.label() == label1 ? 0 : 1;
							LabeledPoint result = new LabeledPoint(newLabel, point.features());
							
							return result;
						}
						
					});
					
					model[k] = new LogisticRegressionWithLBFGS()
						      .setNumClasses(2)
						      .run(transformedRDD.rdd());
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(  " Model training time: " + (endTime-startTime) );
		
		/************** End of training portion *****************/ 
		
		startTime = System.currentTimeMillis();
		
		
		/************* Scoring/Testing portion  *******************
		 * 1. Parse the lines into LabledPoint<label, features>
		 * 2. Run through in a loop for all 45 combinations of features.
		 * 3. Filter the RDD for the given pair of labels.
		 * 4. Transform the entries into 0 and 1.
		 * 5. Run the prediction and record prediction and labels.
		 * 6. Print the accuracy metrics.
		 * */
		
		/** Creating LabledPoints from the input. */
		JavaRDD<LabeledPoint> labledTestPointsRDD = rawTestData.map(new Function<String, LabeledPoint>() {

			public LabeledPoint call(String line) throws Exception {
				return formLabeledPoint(line);
			}
			
			private LabeledPoint formLabeledPoint(String line){
				String[] tokens = line.split(",");
				double[] result = new double[9];
				for(int j=0,i=tokens.length-9;i<tokens.length;i++,j++){
					result[j] = Double.valueOf(tokens[i]);
				}
				double label = Double.valueOf(tokens[0]);
				LabeledPoint resultPoint = new LabeledPoint(label, new DenseVector(result));
				
				return resultPoint;
			}
		});
		
		
		k=0;
		double sumAUC = 0;
		for(double i=0;i<=9;i++){
			for(double j=1;j<=9;j++){
				if(j>i){
					final double label1 = i;
					final double label2 = j;
					/** Filtering for labels i and j */
					JavaRDD<LabeledPoint> filteredRDD = labledTestPointsRDD.filter(new Function<LabeledPoint, Boolean>(){

						public Boolean call(LabeledPoint point) throws Exception {
							boolean result = false;
							if(point.label() == label1 || point.label() == label2)
								result = true;
							return result;
						}
						
					});
					
					/** Mapping input into binary */
					JavaRDD<LabeledPoint> transformedRDD = filteredRDD.map(new Function<LabeledPoint, LabeledPoint>(){

						public LabeledPoint call(LabeledPoint point)
								throws Exception {
							double newLabel = point.label() == label1 ? 0 : 1;
							LabeledPoint result = new LabeledPoint(newLabel, point.features());
							
							return result;
						}
						
					});
					
					final LogisticRegressionModel currentModel = model[k];
					
					JavaRDD<Tuple2<Object, Object>> predictionAndLabels = transformedRDD.map(new Function<LabeledPoint, 
							Tuple2<Object, Object>>(){

								private static final long serialVersionUID = 1L;

								public Tuple2<Object, Object> call(
										LabeledPoint inputPoint) throws Exception {
									Double prediction = currentModel.predict(inputPoint.features());
									return new Tuple2<Object, Object>(prediction, inputPoint.label());
								}
						
					});
					
					predictionAndLabels.saveAsTextFile("predictions_pca/predictions_"+label1+"_vs_"+label2);
					
					/*MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
				    double precision = metrics.precision();
				    System.out.printf("Precision for %f, %f is %f \n",i,j, precision);
				    precisionSum += precision;*/
					
					BinaryClassificationMetrics metrics = 
							new BinaryClassificationMetrics(predictionAndLabels.rdd());
					double auROC = metrics.areaUnderROC();
				    
					sumAUC += auROC;
					
				    System.out.printf("AUC for %f, %f is %f \n",i,j, auROC);
				}
			}
		}
		System.out.printf("Average AUC value :: %f \n", (sumAUC/45) );
		
		endTime = System.currentTimeMillis();
		
		System.out.println(" Model scoring time.. :" + (endTime-startTime)  );
		
		/******************* End of scoring/testing portion ****************/
		
	
	}

}
