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

public class ZomatoExt {
	
	private static String RESULT_FILE_PREFIX = "restaurants_";
	private static String HEADER = "id\tname\tcost\taddress\tcity\tcountry\turl";
	private static String URL_PREFIX = "https://www.zomato.com/";
	private static String URL_SUFFIX = "/restaurants";
	
	public static int count = 0;
	
	// Max file size in KB
	private static final long MAX_FILE_SIZE = 1024;
	
	private String country = null;
	
	private int currentFileIndex;
	private String currentFileName = null;
	private File currentFile = null;
	private PrintStream currentStream = null;
	
	private String city = null;
	//private String cityUrl = null;
	
	public ZomatoExt(String country){
		this.country = country;
		this.currentFileName = this.country+"/"+this.country+"_restaurants.tsv";
		this.currentFileIndex = 0;
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
			this.currentFile = new File(currentFileName);
			this.currentStream = new PrintStream(new FileOutputStream(currentFile));
			this.currentStream.println(HEADER);
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
		
	public void extractForCity(String city){
		this.city = city;
		StringBuilder buf = new StringBuilder();
		buf.append(URL_PREFIX).append(city).append(URL_SUFFIX);
		String cityUrl = buf.toString();
		extractForURL(city,cityUrl);
	}
	
	public void extractForURL(String city,String url){
		this.city = city;
		url = url+URL_SUFFIX;
		/*String resultFileName = RESULT_FILE_PREFIX + this.city + ".tsv";*/
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			/*PrintStream ps = new PrintStream(new FileOutputStream(resultFileName));*/
			/*ps.println(HEADER);*/
			processPage(doc);
			processSubsequentPages(url, doc);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(".. Failure for url:"+url);
			e.printStackTrace();
			this.count++;
			System.out.println("Failure count:" + this.count);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Usage:: java ZomatoExt seed-cities.txt");
			return;
		}
		ZomatoExt extractor = new ZomatoExt("india");
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(args[0])));
			String line = br.readLine();
			while(line != null && !line.isEmpty()){
				if(line.startsWith("#")){
					line=br.readLine();
					continue;
				}
				extractor.extractForCity(line.trim());
			}
		} catch (FileNotFoundException e) {
			System.err.println("Please ensure the file:" + args[0] +
					" present in the current dir");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void processPage(Document doc){
		String title = doc.title();
		System.out.println(".. Title:" + title);
		processRestaurants(doc.select(".resZS"));
	}
	
	private void processSubsequentPages(String cityUrl,
			Document doc){
		String paginationTxt = 
				doc.select(".search-pagination-top").first().child(0).child(0).text();
		String[] tokens = paginationTxt.split("of");
		String totalPageStr = tokens[1].trim();
		long pageCount = Long.parseLong(totalPageStr);
		for(long i=2;i<=pageCount;i++){
			StringBuilder buf = new StringBuilder();
			String url1 = buf.append(cityUrl).append("?page=").append(i).toString();
			try {
				Document doc1 = Jsoup.connect(url1).get();
				processPage(doc1);
			} catch (Exception e) {
				System.err.println("Error in getting page:"+ url1 +"err:" + e);
				e.printStackTrace();
				this.count++;
				System.out.println("Failure count:" + this.count);
			} 
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	
	private void processRestaurants(Elements restElements){
		int i=1;
		for(Element restaurant : restElements){
			String id = restaurant.attr("data-res_id");
			restaurant = restaurant.child(0);
			String name = restaurant.select(".result-title").text();
			String url = restaurant.select(".result-title").attr("href");
			String address = restaurant.select(".search-result-address").text();
			String cost = getCost(restaurant.select(".search-page-text"));
			/*System.out.printf("%d. Name:%s, address:%s, cost:%s, url:%s\n",i++, 
					name,address, cost, url);*/
			/*ps.printf("%s\t%s\t%s\t%s\n",name, address,cost,url);*/
			try {
				this.writeToFile(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\n",id,name,cost, address,
						this.city,this.country,url));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getCost(Elements elts){
		String result = null;
		for(Element element : elts){
			String text = element.text();
			if(text.startsWith("Cost for 2: Rs.")){
				result = text.replace("Cost for 2: Rs.", "Rs.");
				break;
			}
		}
		return result;
	}

}
