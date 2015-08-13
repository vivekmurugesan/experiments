package org.vav.nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import opennlp.tools.parser.Parse;
import opennlp.tools.postag.POSSample;

public class AttributeSentimentExtractor {
	
	public static final String RESOURCE_PATH = 
			"/home/vivek/cba/capstone/cba_ws/vav.nlp/src/main/resources/";
	
	private static final String NOUN_PREFIX = "NN";
	private static final String ADJECTIVE = "JJ";
	private static final String PRONOUN_PREFIX = "PRP";
	private static final String ADVERB = "RB";
	private static final String VERY = "very";
	
	/*private String paragraph;*/
	private SentenceParser sentenceParser = null;
	private POSParser posParser = null;
	private ChunkPOSParser chunkPOSParser = null;
	private SentenceChunker chunker = null;
	
	public AttributeSentimentExtractor(){
		init();
	}
	
	private void init(){
		this.sentenceParser = new SentenceParser();
		this.posParser = new POSParser();
		this.chunkPOSParser = new ChunkPOSParser();
		this.chunker = new SentenceChunker();
	}
	
	public String[] getSentences(String paragraph){
		return this.sentenceParser.getSentences(paragraph);
	}
	
	public POSSample getPOS(String sentence){
		return this.posParser.getPOS(sentence);
	}
	
	public String[] getChunks(String sentence){
		return this.chunker.getChunks(sentence);
	}
	
	/**
	 * 
	 * @param pos
	 * @return A mapping of <attribute, adjective>
	 */
	public String  getAttribtueAdjMapping(POSSample pos,
			Map<String, List<String>> attrMap, String previousNoun){
		String[] tags = pos.getTags();
		String[] words = pos.getSentence();
		String unattachedNoun = null;
		String unattachedAdj = null;
		String unattachedAdv = null;

		for(int i=0;i<tags.length;i++){
			String tag = tags[i];
			if(tag.startsWith(NOUN_PREFIX)){
				unattachedNoun = words[i];
				if(unattachedAdj != null){
					addAttrMapping(unattachedNoun, unattachedAdj, attrMap);
					previousNoun = unattachedNoun;
					/** Resetting both */
					unattachedNoun = null;
					unattachedAdj = null;
				}
			}else if(tag.equals(ADVERB)){
				if(unattachedAdv != null)
					unattachedAdv = unattachedAdv + " " + words[i];
				else
					unattachedAdv = words[i];
			}else if(tag.equals(ADJECTIVE)){
				if(unattachedAdv != null){
					unattachedAdj = unattachedAdv + " " + words[i];
					unattachedAdv = null;
				}else
					unattachedAdj = words[i];
				if(unattachedNoun != null){
					addAttrMapping(unattachedNoun, unattachedAdj, attrMap);
					previousNoun = unattachedNoun;
					/** Resetting both */
					unattachedNoun = null;
					unattachedAdj = null;
				}
			}else if(tag.startsWith(PRONOUN_PREFIX)){
				if(previousNoun != null)
					unattachedNoun = previousNoun;
				else
					unattachedNoun = words[i];
				if(unattachedAdj != null){
					addAttrMapping(unattachedNoun, unattachedAdj, attrMap);
					previousNoun = unattachedNoun;
					/** Resetting both */
					unattachedNoun = null;
					unattachedAdj = null;
				}
			}
		}
		if(unattachedAdj != null && unattachedNoun != null)
			addAttrMapping(unattachedNoun, unattachedAdj, attrMap);
		else if(unattachedAdv!= null && unattachedNoun != null)
			addAttrMapping(unattachedNoun, unattachedAdv,attrMap);

		return previousNoun;
	}
	
	private void addAttrMapping(String attr, String adj,
			Map<String, List<String>> attrMap){
		List<String> adjList = attrMap.get(attr);
		attr = attr.toLowerCase();
		adj = adj.toLowerCase();
		if(adjList == null){
			adjList = new ArrayList<String>();
			attrMap.put(attr,adjList);
		}
		adjList.add(adj);
	}
	
	public String getAttributeMapping(String text){
		String[] sentences = getSentences(text);
		Map<String, List<String>> attrAdjMap = new HashMap<String, List<String>>();
		String previousNoun	= null;
		for(String sentence : sentences){
			sentence = sentence.replaceAll("[\\-\\+\\.\\^:,!]","");
			POSSample pos = getPOS(sentence);
			System.out.println("Sentence:" + sentence);
			System.out.println("POS:"+pos.toString());
						
			previousNoun = getAttribtueAdjMapping(pos,
					attrAdjMap, previousNoun);
			/*String[] chunks = extractor.getChunks(sentence);
			for(String chunk : chunks){
				chunk = chunk.replaceAll("[\\-\\+\\.\\^:,]","");
				Parse[] topParses = extractor.chunkPOSParser.getParses(chunk);
				for(Parse p : topParses){
					p.show();
					p.getChildren();
					p.getCoveredText();
				}
			}*/
		}
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		Gson gson = builder.create();
		String json = gson.toJson(attrAdjMap);
		System.out.println("Attr Map:"+attrAdjMap);
		System.out.println("Json:" + json);
		return json;
	}

	public static void main(String[] args) {
		String testText = null;
		/*testText = "The ambience is quite unique as that have maintained a pirate theme all over the place. I would just say they have tried to imitate BBQ nation. But the quality of food is not the same. The prawns were really dry. The place is very crowded; if you are in a queue for the buffet, there isn't much of space and you wouldn't really like to struggle in queues in cramped up spaces when you go out for dining. Wouldn't visit this place again.";*/
		/*testText = "Good food.... Decor is to Core.... In good senses... But it was very hot... Good place for team lunches and working on good price point what competition offer all in all must visit venue... For buffet lovers";*/
		/*testText = "Food was good excellent service. It was cheap too.";*/
		/*testText = "Went here for Dinner that too on a Halloween Night which is an added benefit...Its best for its Pirates Ambience and Food is also delicious..The service is good..Ya its bit costly but its worth a visit.Even we demanded  a Pirate Hat from the waiter dressed as pirates as they were very friendly. Live music was Cool.. Great AMBIENCE!!!!!!!";*/
		testText = "Went here for Dinner that too on a Halloween Night which is an added benefit...Its best for its Pirates Ambience and Food is also delicious..The service is good..Ya its bit costly but its worth a visit.Even we demanded  a Pirate Hat from the waiter dressed as pirates as they were very friendly. Live music was Cool.. Great AMBIENCE!!!!!!!";
		
		if(testText != null && !testText.isEmpty()){
			AttributeSentimentExtractor extractor = 
					new AttributeSentimentExtractor();
			extractor.getAttributeMapping(testText);
		}else{
			if(args.length < 2){
				System.err.println("Usage:: java AttributeSentimentExtractor ReviewFileName OutputFileName");
				return;
			}
			AttributeSentimentExtractor extractor = 
					new AttributeSentimentExtractor();

			processReviewFile(extractor, args[0], args[1]);
		}
	}
	
	private static void processReviewFile(AttributeSentimentExtractor ext,
			String reviewFileName, String outputFile){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(reviewFileName)));
			PrintStream ps = new PrintStream(new FileOutputStream(outputFile));
			ps.printf("%s\t%s\t%s\t%s\n", "ReviewId","Rating",
					"ReviewText","Json");
			
			String line = br.readLine();
			
			while(line != null && !line.isEmpty()){
				String[] tokens = line.split("\\t");
				String reviewId = tokens[0];
				String rating = tokens[4];
				String reviewTxt = tokens[9];
				String json = ext.getAttributeMapping(reviewTxt);
				
				writeOutput(ps, reviewId, rating, reviewTxt, json);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeOutput(PrintStream ps, String reviewId, 
			String rating, String reviewTxt, String json){
		ps.printf("%s\t%s\t%s\t%s\n",reviewId, rating, reviewTxt,json);
	}

}
