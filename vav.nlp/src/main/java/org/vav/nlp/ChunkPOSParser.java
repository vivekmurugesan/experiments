package org.vav.nlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.Parser;
import opennlp.tools.util.InvalidFormatException;

public class ChunkPOSParser {
	InputStream modelFile = null;
	ParserModel model = null;
	Parser parser = null;
	
	public ChunkPOSParser(){
		init();
	}
	
	public void init(){
		try {
			this.modelFile = new FileInputStream(
					AttributeSentimentExtractor.RESOURCE_PATH + "en-parser-chunking.bin");
			this.model = new ParserModel(modelFile);
			this.parser = ParserFactory.create(model);
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
	
	public Parse[] getParses(String sentence){
		Parse[] topParses = ParserTool.parseLine(sentence, this.parser, 1);
		return topParses;
	}
}
