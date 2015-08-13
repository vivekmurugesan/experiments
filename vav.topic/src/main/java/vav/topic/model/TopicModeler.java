package vav.topic.model;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.jobcontrol.Job;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.lda.cvb.CVB0Driver;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.utils.vectors.RowIdJob;
import org.apache.mahout.utils.vectors.VectorDumper;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicModeler extends AbstractJob {
    private static final Logger log = LoggerFactory.getLogger(Job.class);
    static int numTopics = 5;
    static double doc_topic_smoothening = 0.0001;
    static double term_topic_smoothening = 0.0001;
    static int maxIter = 10;
    static int iteration_block_size = 10;
    static double convergenceDelta = 0;
    static float testFraction = 0.0f;
    static int numTrainThreads = 4;
    static int numUpdateThreads = 1;
    static int maxItersPerDoc = 10;
    static int numReduceTasks = 10;
    static boolean backfillPerplexity = false;

public static void main(String args[]) throws Exception {
    // String baseFileLocation = args[0];
    String baseFileLocation = "/user/root/topic_model";
    Path output = new Path(baseFileLocation, "output");
    Configuration conf = new Configuration();
    HadoopUtil.delete(conf, output);
    String[] ldaArgs = { "-DbaseFileLocation=" + baseFileLocation };
    // String[] strings =
    // {"-Dmapred.input.dir=VectorFile/tfidf-vectors/part-r-00000"};
    ToolRunner.run(new TopicModeler(), ldaArgs);
    System.out.println("done");
}

public int run(String[] arg0) throws Exception {
    Configuration conf = getConf();
    // String baseFileLocation = "/Users/pin/java";
    String baseFileLocation = conf.get("baseFileLocation");
    Path input = new Path(baseFileLocation, "review_input");
    System.out.println(input.toString());
    String seqFileOutput = "SeqFile";
    String vectorOutFile = "VectorFile";
    String rowIDOutFile = "RowIdOutput";
    String ldaOutputFile = "topicModelOutputPath";
    String dictionaryFileName = vectorOutFile + "/dictionary.file-0";
    String tempLDAModelFile = "modelTempPath";
    String docTopicOutput = "docTopicOutputPath";
    String topicTermVectorDumpPath = "topicTermVectorDump";
    String docTopicVectorDumpPath = "docTopicVectorDump";

    // String topicTermVectorDump = "topicTermVectorDump";

    log.info("Deleting all the previous files.");
    HadoopUtil.delete(conf, new Path(seqFileOutput));
    HadoopUtil.delete(conf, new Path(vectorOutFile));
    HadoopUtil.delete(conf, new Path(rowIDOutFile));
    HadoopUtil.delete(conf, new Path(ldaOutputFile));
    HadoopUtil.delete(conf, new Path(docTopicOutput));
    HadoopUtil.delete(conf, new Path(tempLDAModelFile));
    HadoopUtil.delete(conf, new Path(topicTermVectorDumpPath));
    HadoopUtil.delete(conf, new Path(docTopicVectorDumpPath));

    // S3FileSystem.
    log.info("Step1: convert the directory into seqFile.");
    System.out.println("starting dir to seq job");
    String[] dirToSeqArgs = { "--input", input.toString(), "--output",
            seqFileOutput };
    ToolRunner.run(new SequenceFilesFromDirectory(), dirToSeqArgs);
    System.out.println("finished dir to seq job");

    log.info("Step 2: converting the seq to vector.");
    System.out.println("starting seq To Vector job");
    String[] seqToVectorArgs = { "--input", seqFileOutput, "--output",
            vectorOutFile, "--maxDFPercent", "70", "--maxNGramSize", "1",
            "--namedVector", "--analyzerName",
            "org.apache.lucene.analysis.core.WhitespaceAnalyzer" };
    ToolRunner.run(new SparseVectorsFromSequenceFiles(), seqToVectorArgs);
    System.out.println("finished seq to vector job");

    log.info("Step3: convert SequenceFile<Text, VectorWritable> to  SequenceFile<IntWritable, VectorWritable>");
    System.out.println("starting rowID job");
    String[] rowIdArgs = {
            "-Dmapred.input.dir=" + vectorOutFile
                    + "/tfidf-vectors/part-r-00000",
            "-Dmapred.output.dir=" + rowIDOutFile };
    ToolRunner.run(new RowIdJob(), rowIdArgs);
    System.out.println("finished rowID job");

    log.info("Step4: Run the LDA algo");
    System.out.println("starting caluclulating the number of terms");
    //int numTerms = getNumTerms(new Path(dictionaryFileName));
    System.out.println("finished calculating the number of terms");
    long seed = System.nanoTime() % 10000;
    System.out.println("starting the CVB job");
    new CVB0Driver().run(conf, new Path(rowIDOutFile + "/matrix"), new Path(
            ldaOutputFile), numTopics, 0, doc_topic_smoothening,
            term_topic_smoothening, maxIter, iteration_block_size,
            convergenceDelta, new Path(dictionaryFileName), new Path(
                    docTopicOutput), new Path(tempLDAModelFile), seed,
            testFraction, numTrainThreads, numUpdateThreads,
            maxItersPerDoc, numReduceTasks, backfillPerplexity);
    //String[] runArgs ={};
    System.out.println("finished the cvb job");

    log.info("Step5: vectordump topic-term");

    System.out.println("starting the vector dumper for topic term");
    /*String[] topicTermDumperArg = {"--seqFile", ldaOutputFile+"/part-m-00000",  "--dictionary", 
            dictionaryFileName, "-dt", "sequencefile"  };*/
    
    for(int k=0;k<numTopics;k++){
        System.out.println("Dumping topic \t"+k);
        String partFile="part-m-0000"+k;
        if(k>=10)
            partFile="part-m-000"+k;

        String output="topic"+k;
        String[] topicTermDumperArg = {"-i", ldaOutputFile+"/"+partFile, "-dt", "sequencefile", "-d", 
            dictionaryFileName, "-o",output,  "-c", "csv" };  

        VectorDumper.main(topicTermDumperArg);

    }
    //ToolRunner.run(new Configuration(), new CustomVectorDumper(), topicTermDumperArg);
    //VectorDumper.main(topicTermDumperArg);
    //SequenceFileDumper.main(topicTermDumperArg);
    //String[] topicTermDumperArg = {"--input", ldaOutputFile, "--output", topicTermVectorDumpPath,  "--dictionary", 
    //        dictionaryFileName, "-dt", "sequencefile" ,"--vectorSize", "25" ,"-sort", "testsortVectors" };
    //LDAPrintTopics.main(topicTermDumperArg);
    //String[] topicTermDumperArg = {"-seq"};
    /*VectorDumper.main(topicTermDumperArg);*/
    System.out.println("finisher the vector dumper for topicterm");
    //System.out.println("starting the vector dumper for doctopic dumper");
    //String[] docTopicDumperArg = {"--input", docTopicOutput, "--output", docTopicVectorDumpPath};
    //ToolRunner.run(new Configuration(), new CustomVectorDumper(), docTopicDumperArg);
    //VectorDumper.main(docTopicDumperArg);
    System.out.println("finsiher the vector dumper for doctopic dumper");

    //printLdaResults(ldaOutputFile, numTerms);
    //MongoDumper dumper = new MongoDumper();
    //dumper.writeTopicCollection(topicTermVectorDumpPath.toString());
    return 0;
}
}