package org.vav.nlp;

import java.io.File;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class POSParser {
	private POSModel model = null;
	private POSTaggerME tagger = null;
	
	public POSParser(){
		this.init();
	}
	
	public void init(){
		this.model = new POSModelLoader()	
			.load(new File(
					AttributeSentimentExtractor.RESOURCE_PATH + "en-pos-maxent.bin"));
		this.tagger = new POSTaggerME(model);
	}
	
	public POSSample getPOS(String sentence){
		String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
		String[] tags = tagger.tag(tokens);
		
		POSSample posSample = new POSSample(tokens, tags);
		/*System.out.println("POS:" + posSample);*/
		
		return posSample;
	}
}
