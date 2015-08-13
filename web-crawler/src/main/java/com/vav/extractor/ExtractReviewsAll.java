package com.vav.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExtractReviewsAll {
	
	private String REV_URL_SUFFIX = "/reviews";

	private String fileName = null;
	private String outputFileName = "reviews_";
	private String outputDir = null;
	private String city = null;
	
	private static String postUrl = "https://www.zomato.com/php/social_load_more.php";
	/*private static String contentType = "application/x-www-form-urlencoded; charset=UTF-8";*/
	private static String host = "www.zomato.com";
	
	private static Map<String, String> header;
	
	static {
		header=new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		header.put("Host", "www.zomato.com");
		header.put("Pragma", "no-cache");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Cache-Control", "no-cache");
		header.put("X-NewRelic-ID", "VgcDUF5SGwcEVVNXAwA");
		header.put("Origin", "https://www.zomato.com");
		header.put("Accept", "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	}
	
	/** entity_id=71527&profile_action=reviews-dd&page=2&limit=26 */
	
	public ExtractReviewsAll(String seedFile, String output){
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
				this.currentStream.printf("%s\t%s\t%s\t%s\t%s\t%s\t","ReviewId","RestaurantName","RestaurantId","Location","Rating", "UserName");
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
	
	private int getReviewCount(Elements elms){
		/** Format: <a data-sort="reviews-dd" class="default-section-title everyone empty" href="#">Reviews <span class="grey-text">16</span> </a> */
		int revCount = -1;
		for(Element elm : elms){
			String dataSort = elm.attr("data-sort");
			if(dataSort != null && !dataSort.isEmpty()){
				if(dataSort.equals("reviews-dd")){
					revCount = Integer.parseInt(elm.child(0).text());
					break;
				}
			}
		}
		return revCount;
		
	}
	
	public void readReviews(String restName, String url){
		try {
			url += REV_URL_SUFFIX;
			Connection connection = Jsoup.connect(url);
			Document doc = connection.get();
			Response response = connection.response();
			Map<String, String> cookies = response.cookies();
			/*Document doc = response.parse();*/
	
			int revCount = getReviewCount(doc.select(".default-section-title"));
			Element forRestId = doc.select(".res-review-body").first();
			if(forRestId == null){
				System.err.println("No reviews found for:"+restName);
				return;
			}
				
			String resId = forRestId.attr("data-res_id");
			
			int limit=100;
			for(int page=0,start=0;start<revCount;page++,start+=10){
				doc = getNextReviews(url, revCount, resId, cookies,limit,page);


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
					reviewTxt = reviewTxt.replace("Rated", "").trim();
					this.writeToFile(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
							reviewId,restName,resId,this.city,rating, userName,
							userId, dateTime, userLink, reviewTxt));
				}
				
				Thread.sleep(100);

			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*private String getPostBody(String restId, int limit){
		StringBuilder buf = new StringBuilder();
		buf.append("entity_id=").append(restId);
		buf.append("&profile_action=reviews-dd&page=").append(1);
		buf.append("&limit=").append(10);
		
		return buf.toString();
	}*/
	
	private void updateHeader(Connection connection){
		for(String key : header.keySet()){
			connection.header(key, header.get(key));
		}
	}
	
	private Document getNextReviews(String url, int revCount, String resId, 
			Map<String, String> cookies, int limit, int page){
		Document doc = null;
		try{
			Connection connection = Jsoup.connect(postUrl);
			connection.cookies(cookies);
			connection.header("Referer", url);
			updateHeader(connection);
			/** entity_id=71527&profile_action=reviews-dd&page=2&limit=26 */
			connection.data("entity_id", resId, "profile_action", "reviews-dd",
					"page",String.valueOf(page),"limit",String.valueOf(limit));
			connection.method(Method.POST);
			/*doc = connection.parser(Parser.xmlParser()).post();*/
			Response response = connection.execute();
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(response.body());
			String respStr = json.getAsJsonObject().get("html").getAsString();
			doc = Jsoup.parse(respStr);
			/*connection.response().body();*/
			/*doc = connection.post();*/
			/*doc.getElementById("html");*/
						
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;

	}
	
	
	
	public static void main(String args[]){
		if(args.length < 2){
			System.err.println(".. Usage:: java ExtractReviews restaurants_<city>.tsv <output>");
			return;
		}
			
		ExtractReviewsAll reviews = new ExtractReviewsAll(args[0], args[1]);
		reviews.init();
	}
	
	public void test(){
		/*ExtractReviews ext = new ExtractReviews("");
		ext.readReviews("absolute-barbecue-t-nagar", "https://www.zomato.com/chennai/absolute-barbecue-t-nagar", System.out);
*/	}
	

	/*private String getCookie(Response response) {
		StringBuilder sb = new StringBuilder();
		Map<String, List<String>> headers = response.headers();
		String key="",val="";
		if (headers != null) {
			List<String> cookies = headers.get("Set-Cookie");
			if (cookies != null) {
				for (String cookie : cookies) {
					int semiColon = cookie.indexOf(";");
					String entry = cookie.substring(0, semiColon + 1);
					if(entry.contains("=")){
						 key=entry.substring(0, entry.indexOf("="));
						 val=entry.substring(entry.indexOf("=")+1);
						 if(key.equalsIgnoreCase("zl")){*/
/*							 val="en";
							 entry=key+"="+val+";";
							 
*/						 
						/*	 continue;
						 }
						 
					}
					sb.append(entry);
				}
			}

		}
		sb.append("zl=en");
		return sb.toString();
	}*/
}
