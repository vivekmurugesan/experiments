package org.vav.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class SentenceParser {
	
	InputStream modelFile = null;
	SentenceModel model = null;
	SentenceDetectorME sdetector = null;
	
	public SentenceParser(){
		this.init();
	}
	
	public void init(){
		try {
			this.modelFile = new FileInputStream(
					AttributeSentimentExtractor.RESOURCE_PATH + 
					"en-sent.bin");
			this.model = new SentenceModel(modelFile);
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
		this.sdetector = new SentenceDetectorME(model);
	}
	
	public String[] getSentences(String paragraph){
		String[] sentences = sdetector.sentDetect(paragraph);
		return sentences;
	}
	
	
}
