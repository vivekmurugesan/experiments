package com.vav.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UserDetailsExtractor {

	private String urlFileName = null;
	private String outputFileName = "user_details_";
	private String outputDir = null;

	// Max file size in KB
	private static final long MAX_FILE_SIZE = 1024;

	private int currentFileIndex;
	private String currentFileName = null;
	private File currentFile = null;
	private PrintStream currentStream = null;

	public UserDetailsExtractor(String outputDir, String urlFileName){
		this.outputDir = outputDir;
		this.urlFileName = urlFileName;
	}
	
	public void init(){
		this.currentFileIndex=0;
		this.currentFileName = this.outputDir+"/"+this.outputFileName+this.currentFileIndex+".tsv";
	}
	
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
			this.currentStream.printf("%s\t%s\t%s\t%s\t%s\n","UserGivenName", "Location", "ReviewCount", "FollowersCount", "BeenThereCount");
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

	public void getAllUserDetails() throws IOException{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(urlFileName)));
			String url = br.readLine();
			int count=1;
			url = br.readLine();
			while(url != null && !url.isEmpty()){
				readUserDetails(url);
				System.out.printf("\nUser details read so far:%d",count++);
				url=br.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}

	}

	public void readUserDetails(String url){
		Connection connection = Jsoup.connect(url);
		try {
			Document doc = connection.get();
			String userGivenName = doc.select(".given-name").text();
			String location = doc.select(".usr-location").text();
			String revCount = doc.select(".user-tab-reviews").text();
			String folCount = doc.select(".user-tab-follows").text();
			String beenThereCount = doc.select(".user-tab-beenthere").text();
			/*String pattern = "(.*)(\\d+)(.*)";
			Pattern r = Pattern.compile(pattern);
			Matcher matcher = r.matcher(revCount);
			if(matcher.find()){
				revCount = matcher.group(2);
			}
	
			matcher = r.matcher(folCount);
			if(matcher.find()){
				folCount = matcher.group(2);
			}
			matcher = r.matcher(beenThereCount);
			if(matcher.find()){
				beenThereCount = matcher.group(2);
			}
*/			System.out.printf("\nname:%s, location:%s,revCount:%s,folCount:%s,beenThere:%s\n",
					userGivenName, location, revCount, folCount, beenThereCount);
			String content = String.format("%s\t%s\t%s\t%s\t%s\n", 
					userGivenName, location, revCount, folCount, beenThereCount);
			this.writeToFile(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String url = "https://www.zomato.com/007Foodie";
		if(args.length<2){
			System.err.println("Usage::java UserDetailsExtractor <output_dir> <url_filename>");
			return;
		}
		UserDetailsExtractor ext = new UserDetailsExtractor(args[0], args[1]);
		ext.init();
		/*ext.readUserDetails(url);*/
		ext.getAllUserDetails();
	}

}
