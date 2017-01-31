package test.permute;

import java.util.HashMap;
import java.util.Map;

/**
 * To check if a given string is a permutation of a palindrome.
 * @author vivek
 *
 */
public class CheckPalindromePermute {

	public static void main(String[] args) {
		/*String test = "tact coa"; //--> atco cta*/
		String test = "tact coat"; //--> atco cta
		String test2 = "bbtaaatb";
		
		CheckPalindromePermute palPermute = new CheckPalindromePermute();
		if(palPermute.checkPalPermute(test2))
			System.out.printf("String:%s is a permutation of a palindrome\n",test );
		else
			System.out.printf("String:%s is not a permutation of a palindrome\n",test );
	}
	
	/**
	 * teset <---> ttsee <---> tseet <---> ttees
	 * tesset <---> ttssee 
	 * ttkesksektt <---> tttteesskkk
	 * 1. Count the occurrences of each char, by using hashtable/map.
	 * 2. There should be only one char that has count of odd number, 
	 * rest of them should be even.
	 * 3. Check for this condition by iterating all keys of the map
	 * and if it violates return false, and true otherwise.
	 * @param input
	 * @return
	 */
	public boolean checkPalPermute(String input){
		char[] inputChArr = input.toCharArray();
		Map<Character, Integer> chMap = new HashMap<>();
		for(char ch : inputChArr){
			// To ignore space
			if(ch == ' ')
				continue;
			Character chObj = Character.valueOf(ch);
			if(!chMap.containsKey(chObj))
				chMap.put(chObj, 0);
			chMap.put(chObj, chMap.get(chObj)+1);
		}
		int oddCount = 0;
		boolean result=true;
		for(Character key : chMap.keySet() ){
			int chCount = chMap.get(key);
			if(chCount % 2 != 0){
				oddCount++;
				if(oddCount > 1){
					result = false;
					break;
				}
			}
		}
		
		return result;
	}

}
