package test.permute;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string find out if all the chars are unique.
 * @author vivek
 *
 */
public class StrCharUniqueness {

	public static void main(String[] args) {
		
		//String test = "abcdefg";
		String test = "abcadefg";
		StrCharUniqueness uniqTest = new StrCharUniqueness();
		if(uniqTest.testUniqueness(test))
			System.out.printf("String : %s contains only uniq chars\n",test);
		else
			System.out.printf("String : %s contains non uniq chars\n",test);
	}
	
	/**
	 * 1. Iterate through the chars and each char into a hashtable/map.
	 * 2. The moment we encounter a char that is already in the map break
	 * the loop and set flag duplicate found.
	 * 3. At the end of the loop if duplicate found is set to true then 
	 * the string contains non unique chars, only unique chars otherwise.
	 * @param test
	 * @return
	 */
	public boolean testUniqueness(String test){
		char[] chArr = test.toCharArray();
		Map<Character,Integer> chMap = new HashMap<>();
		boolean duplicateFound = false;
		for(char ch : chArr){
			Character chObj = Character.valueOf(ch);
			if(chMap.containsKey(chObj)){
				duplicateFound = true;
				break;
			}
			chMap.put(chObj, 1);
		}
		return !duplicateFound;
	}

}
