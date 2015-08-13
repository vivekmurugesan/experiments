package com.vav.extractor;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CountryExtractor {

	private String country = "india";
	private static final String URL_PREFIX = "https://www.zomato.com/";
	private ZomatoExt extractor = null;
	
	public CountryExtractor(String country){
		this.country = country;
	}
	
	public void init(){
		this.extractor = new ZomatoExt(this.country);
		(new File(this.country)).mkdir();
	}
	
	public void extractForCountry(){
		Document doc = null;
		StringBuilder buf = new StringBuilder();
		buf.append(URL_PREFIX).append(this.country);
		try {
			doc = Jsoup.connect(buf.toString()).get();
			Elements elms = doc.select(".country-links");
			for(Element elmt : elms){
				String cityUrl = elmt.attr("href");
				String city = elmt.text();
				extractor.extractForURL(city,cityUrl);
			}
			
			System.out.println("Failure count:" + ZomatoExt.count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String str = "file.tsv";
		String result = str.replaceFirst("[0-9]*.tsv", 
				201+".tsv");
		System.out.println(result);*/
		String country = "india";
		if(args.length >= 1)
			country = args[0];
		
		CountryExtractor ext = new CountryExtractor(country);
		ext.init();
		ext.extractForCountry();
	}

}
