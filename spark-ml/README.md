#########################################
## Author: Vivek ###############
#########################################

This project contains few sample code for running some machine learning
algorithms using Spark ML.
It is a mavenized project. All regular maven operations can be done for compiling and packaging.

Following is the way it needs to be run,
1. spark-submit --class "org.vivek.spark.ml.SparkDecisionTree" --master local[4] spark-ml-0.0.1-SNAPSHOT.jar > out_dtree.txt
2. spark-submit --class "org.vivek.spark.ml.SparkNaiveBayes" --master local[4] spark-ml-0.0.1-SNAPSHOT.jar > out_nb.txt
3. spark-submit --class "org.vivek.spark.ml.SparkMultinomial" --master local[4] spark-ml-0.0.1-SNAPSHOT.jar > out_mn.txt
4. spark-submit --class "org.vivek.spark.ml.SparkLogit" --master local[4] spark-ml-0.0.1-SNAPSHOT.jar > out_logit.txt

The model csv files are expected to be present in the current directory while running.
Redirecting the output into a file help you looking at the useful information as spark prints lots of information on the console.

Spark needs to be installed and configured before running these commands.
Further details on spark installation can be found at:
https://spark.apache.org/docs/1.0.1/spark-standalone.html

The respective R codes used can also be found in the directory R-Code.
