package org.vivek.spark.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;

/**
 * It runs the PCA on the mnist data. It loads the 
 * data from couple csv files. One containing the test data and the other 
 * one containing the train data.
 * It then converts the data into an RDD containing Vector instances.
 * And then runs learns the PCA weightages from the data and 
 * then performs the respective matrix multiplication for computing the
 * princpal components for each observation.
 * @author vivek
 *
 */
public class SparkPCA {
	
	public static void main(String[] args) {
		
		String fileUri = "train.csv";
		SparkConf conf = new SparkConf().setAppName("PCA Sample").set("spark.executor.memory", "4g");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> rawData = sc.textFile(fileUri).cache();
		long startTime = System.currentTimeMillis();
		JavaRDD<double[]> arrRDD = rawData.map(new Function<String, double[]>() {
			private static final long serialVersionUID = 1L;
			public double[] call(String line) throws Exception {
				return parseLine(line);
			}
			private double[] parseLine(String line){
				String[] tokens = line.split(",");
				double[] result = new double[tokens.length-1];
				for(int i=1;i<tokens.length;i++){
					result[i-1] = Double.valueOf(tokens[i]);
				}
				return result;
			}

		});

		JavaRDD<Vector> vectorRDD = arrRDD.map(new Function<double[], Vector>() {

			private static final long serialVersionUID = 1L;

			public Vector call(double[] arr) throws Exception {
				Vector result = Vectors.dense(arr);
				return result;
			}
		});

		// Create a RowMatrix from JavaRDD<Vector>.
		RowMatrix mat = new RowMatrix(vectorRDD.rdd());

		// Compute the top 3 principal components.
		Matrix pc = mat.computePrincipalComponents(9);
		RowMatrix projected = mat.multiply(pc);
		System.out.println("PCA projected cols:" + projected.numCols());
		System.out.println("PCA projected rows:" + projected.numRows());
		long endTime = System.currentTimeMillis();
		
		MultivariateStatisticalSummary summary = projected.computeColumnSummaryStatistics();
		System.out.println("Variance for the PCA columns:" + summary.variance());
		
		Vector variance = summary.variance();
		for(double val : variance.toArray()){
			System.out.println("SD:" + Math.sqrt(val));
		}
		
		/*summary.variance().foreachActive(new Function2<Object,Object>);*/
		
		System.out.println(".. Time taken for PCA projection:" + (endTime-startTime));
		projected.rows().saveAsTextFile("pca_output");
		
	}

}
