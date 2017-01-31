package test.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author vivek
 *
 */
public class AnagramSort {

	public static void main(String[] args) {
		String[] input = {"abcd","abcde","xabcd", "edbca","adbc",
				"aedbc", "abcdx", "ALGORITHM", "LOGARITHM", 
				"ALGORITHMIC", "LOGARITHMIC"};
		
		sortAnagrams(input);
	}
	
	/**
	 * 1. Create a representation of each string like,
	 * 	char1-count_char2-count_char3_count
	 * that can uniquely identify every anagrams.
	 * 2. Create a TreeMap<String, List<String>> and add all the anagrams 
	 * of each other into the same list.
	 * 3. Then iterate through the map in the order of keys and add the elements
	 * of list to result.
	 * This will give the sort we are looking for.  
	 * @param input
	 * @return
	 */
	public static String[] sortAnagrams(String[] input){
		Map<String, List<String>> map = new TreeMap<>();
		
		for(String str : input){
			String parsed = parse(str);
			if(!map.containsKey(parsed)){
				map.put(parsed, new ArrayList<>());
			}
			map.get(parsed).add(str);
		}
		
		System.out.println(map);
		
		List<String> result = new ArrayList<>();
		for(String key : map.keySet()){
			List<String> list = map.get(key);
			result.addAll(list);
		}
		
		System.out.println(result);
		return result.toArray(new String[0]);
	}
	
	/**
	 * Returns a char1-count_char2-count_char3_count representation 
	 * of a string. 
	 * Sorted by the characters, to ensure it put the <char,count>
	 * in a TreeMap and iterate through.
	 * @param str
	 * @return
	 */
	public static String parse(String str){
		char[] chArr = str.toCharArray();
		Map<Character, Integer> chMap = new TreeMap<>();
		for(char c : chArr){
			if(!chMap.containsKey(c))
				chMap.put(c, 0);
			chMap.put(c, chMap.get(c)+1);
		}
		StringBuilder builder = new StringBuilder();
		
		for(char c : chMap.keySet()){
			/*builder.append(c).append('-').append(chMap.get(c)).append('_');*/
			for(int i=0;i<chMap.get(c);i++)
				builder.append(c);
		}
		return builder.toString();
	}
	
}
