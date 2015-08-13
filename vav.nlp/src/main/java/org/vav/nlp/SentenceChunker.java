package org.vav.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;

public class SentenceChunker {
	
	private POSModel model = null;
	private POSTaggerME tagger = null;
	private InputStream is = null;
	private ChunkerModel cModel = null;
	
	public SentenceChunker(){
		init();
	}
	
	private void init(){
		this.model = new POSModelLoader()
		.load(new File(AttributeSentimentExtractor.RESOURCE_PATH 
				+ "en-pos-maxent.bin"));
		this.tagger = new POSTaggerME(model);
		
		try {
			this.is = new FileInputStream(
					AttributeSentimentExtractor.RESOURCE_PATH + 
					"en-chunker.bin");
			this.cModel = new ChunkerModel(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getChunks(String sentence){
		String[] tokens = WhitespaceTokenizer.INSTANCE
				.tokenize(sentence);
		String[] tags = tagger.tag(tokens);
		ChunkerME chunkerME = new ChunkerME(cModel);
		
		String[] result = chunkerME.chunk(tokens, tags);
		
		return result;
	}
	
}
