package vav.topic.model;

import org.apache.mahout.text.SequenceFilesFromDirectory;

public class TopicModelLocal {

	private String textFileInput = "/home/vivek/cba/extractors/ReviewFiles/";
	private String seqFileOuput = "/home/vivek/cba/extractors/TopicModel/SeqFile/";
	
	public TopicModelLocal(){
	}
	
	public void generateSeqFile() throws Exception{
		SequenceFilesFromDirectory seqFileGen = new SequenceFilesFromDirectory();
		String[] args = { "--input", textFileInput, "--output",
				seqFileOuput };
		seqFileGen.run(args);
	}

	public static void main(String[] args) {
		TopicModelLocal topicModel = new TopicModelLocal();
		try {
			topicModel.generateSeqFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
