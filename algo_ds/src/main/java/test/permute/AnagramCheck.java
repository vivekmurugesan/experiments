package test.permute;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class AnagramCheck {

	public static void main(String[] args){
		
		String first="LOGARITHM";
		String second = "ALGORITHM";
		/*String first = "crecatrova";
		String second = "crtrecaova";*/
		AnagramCheck anagramCheck = new AnagramCheck();
		boolean isAnagram = anagramCheck.isAnagramOf(first, second);
		if(isAnagram)
			System.out.printf("String:%s\tis an Anagram of: %s\n",
					first, second);
		else
			System.out.printf("String:%s\tis not an Anagram of: %s\n",
					first, second);
	}
	
	/**
	 * test <--> sett <--> stet
	 * 1. Tokenize the first string, and create char frequency mapping.
	 * 2. Iterate through the second string, try to form the second string
	 * from the char map of frequency table of first string.
	 * 3. Decrement the freuency by one everytime a char is picked up from the
	 * map.
	 * 4. At the end of the loop ensure that both the strings are equal (it is an
	 * optional step, equality can be checked inside the loop itself).
	 * 5. Ensure that all the frequency entries zero, to make sure that first
	 * string didn't contain no extra chars.
	 * @param first
	 * @param second
	 * @return
	 */
	public boolean isAnagramOf(String first, String second){
		if(first == null || second == null ||
				first.isEmpty() || second.isEmpty()){
			return false;
		}
		if(first.length() != second.length())
			return false;
		
		char[] chArr = first.toCharArray();
		Map<Character,Integer> chMap = new HashMap<>();
		// Forming a HashMap with the characters of first
		for(char ch : chArr){
			Character chObj = Character.valueOf(ch);
			if(!chMap.containsKey(chObj))
				chMap.put(chObj, 0);
			chMap.put(chObj, chMap.get(chObj)+1);
		}
		// Try to form string second from the set of chars from first
		char[] toFormSecond = new char[second.length()];
		char[] secondChArr = second.toCharArray();
		int i=0;
		boolean mismatch = false;
		for(char ch : secondChArr){
			Character chObj = Character.valueOf(ch);
			Integer chCount = chMap.get(chObj);
			if(chCount != null && chCount > 0){
				toFormSecond[i++] = ch;
				chMap.put(chObj, chCount-1);
			}else{
				mismatch = true;
				break;
			}
		}
		boolean result = false;
		if(!mismatch){
			String testSecond = new String(toFormSecond);
			if(testSecond.equals(second))
				return true;
			// TODO Also to ensure that chMap contains zero as the frequency in 
			// entries
		}
		
		return result;
	}
}
