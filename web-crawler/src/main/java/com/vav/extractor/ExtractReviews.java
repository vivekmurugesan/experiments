package com.vav.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractReviews {

	private String fileName = null;
	private String outputFileName = "reviews_";
	private String outputDir = null;
	private String city = null;
	
	public ExtractReviews(String seedFile, String output){
		this.fileName = seedFile;
		this.currentFileName = output+"/" + outputFileName+fileName;
		this.outputDir = output;
	}
	
	// Max file size in KB
		private static final long MAX_FILE_SIZE = 1024;
		
		private int currentFileIndex;
		private String currentFileName = null;
		private File currentFile = null;
		private PrintStream currentStream = null;

		private boolean fileSizeLimitExceeded(){
			long fileSizeKB = this.currentFile.length()/1024;
			if(fileSizeKB >= MAX_FILE_SIZE){
				return true;
			}
			return false;
		}
		
		private void writeToFile(String content) throws FileNotFoundException{
			if(currentFile == null){
				(new File(this.outputDir)).mkdir();
				this.currentFile = new File(currentFileName);
				this.currentStream = new PrintStream(new FileOutputStream(currentFile));
				this.currentStream.printf("%s\t%s\t%s\t%s\t%s\t","ReviewId","RestaurantName","Location","Rating", "UserName");
				this.currentStream.printf("%s\t%s\t%s\t%s\n","UserId", "DateTime", "UserLink", "ReviewTxt");
			}else if(fileSizeLimitExceeded()){
				this.currentFileIndex++;
				this.currentFileName = this.currentFileName.replaceFirst("[0-9]*.tsv", 
						this.currentFileIndex+".tsv");

				this.currentStream.flush();
				this.currentStream.close();
				
				this.currentFile = new File(currentFileName);
				this.currentStream = new PrintStream(new FileOutputStream(currentFile));

			}
			this.currentStream.print(content);
		}

	
	public void init(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));
			

			String line = br.readLine();
			int count=1;
			line = br.readLine();
			while(line != null && !line.isEmpty()){
				String[] tokens = line.split("\t");
				String name = tokens[0];
				city = tokens[3];
				String url = tokens[5];
				System.out.println("Processing restaurant:"+name);
				readReviews(name, url);
				System.out.println("Done with restaurant:"+name+
						"... Done with::"+ count +" so far..");
				Thread.sleep(10);
				count++;
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void readReviews(String restName, String url){
		try {
			Document doc = Jsoup.connect(url).get();
			Elements revEntries = doc.select(".res-review");
			for(Element elm : revEntries){
				Element userElement = elm.child(1).child(0).child(0).child(0).child(1).child(0).child(0); 
				String userName = userElement.text();
				String userLink = userElement.attr("href");
				String userId = userElement.attr("data-entity_id");
				String dateTime = elm.child(1).child(1).child(0).child(0).attr("datetime");
				Element revEntry = elm.child(2);
				String reviewId = revEntry.attr("data-review-id");
				Element hidden = revEntry.child(0).child(0).child(0);
				String className = hidden.attr("class");
				String rating = null;
				String reviewTxt = null;
				if("rev-text hidden".equals(className))
				{
					reviewTxt = hidden.text();
					rating = hidden.child(0).child(0).attr("title");
				}else if("rev-text".equals(className)){
					reviewTxt = hidden.text(); 
					rating = hidden.child(0).child(0).attr("title");
				}
				/*.child.child.(rev-text-hidden | rev-text-sm)*/
				reviewTxt = reviewTxt.replace("<br />", "").replace("\n", "");
				this.writeToFile(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
						reviewId,restName,this.city,rating, userName,
						userId, dateTime, userLink, reviewTxt));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		if(args.length < 2){
			System.err.println(".. Usage:: java ExtractReviews restaurants_<city>.tsv <output>");
			return;
		}
			
		ExtractReviews reviews = new ExtractReviews(args[0], args[1]);
		reviews.init();
	}
	
	public void test(){
		/*ExtractReviews ext = new ExtractReviews("");
		ext.readReviews("absolute-barbecue-t-nagar", "https://www.zomato.com/chennai/absolute-barbecue-t-nagar", System.out);
*/	}
	
}
