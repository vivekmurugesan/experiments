package test.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author vivek
 *
 */
public class SubstringWithAllChars {

	public static void main(String[] args) {
		String text =  "this is a test string";
		String str = "tist";
		
		System.out.printf("\n Substring containing:%s is ::> %s \n",
				str, subStrSearch1(text, str));
	}
	
	/**
	 * 1. Searching for pattern of length m in the text of length n.
	 * 2. For every position i in the text
	 * 3. Iterate form j=i to n, check if the charAt[j] is in the 
	 * pattern str.
	 * 		i. If it is then remove the char from the pattern str, and decrement 
	 * the number of chars to find by one.
	 * 		ii. Otherwise proceed to next char.
	 * 4. If the remaining chars length becomes zero, then we have found a match.
	 * 5. Length j-i is the length of the substring. Check it against the 
	 * minimum length encountered so far.  
	 * @param text
	 * @param str
	 * @return
	 */
	public static String subStrSearch1(String text, String str){
		int n = text.length();
		int m = str.length();
		Map<Character, Integer> charsMap = getCharMap(str);
		int minLenSofar = Integer.MAX_VALUE;
		int start=0,end=0;
		boolean found = false;
		
		for(int i=0;i<=n-m;i++){
			int remaining = m;
			Map<Character, Integer> clone = new HashMap<>();
			clone.putAll(charsMap);
			for(int j=i;j<n;j++){
				char c = text.charAt(j);
				if(clone.containsKey(c)){
					clone.put(c, clone.get(c)-1);
					if(clone.get(c) <= 0)
						clone.remove(c);
					remaining--;
				}
				if(remaining<=0){
					if(!found)found=true;
					int currentLen = j-i+1;
					if(currentLen < minLenSofar){
						minLenSofar = currentLen;
						start=i;
						end=j;
					}
					break;
				}
			}
		}
		
		if(!found)
			System.out.println("not found");
		System.out.printf("\nMin length substr:%d\n", minLenSofar);
		//TODO to handle end==n-1 case.
		return (found)?text.substring(start, end+1):"";
	}
	
	
	
	public static String subStrSearch(String text, 
			String str){
		int n = text.length();
		String[][] cache = new String[n][n];
		int minLenSofar = Integer.MAX_VALUE;
		int start=0,end=0;
		
		cache[0][0] = returnRemaining(text.charAt(0), str);
		// 1st row init
		for(int i=1;i<n;i++){
			String remaining = returnRemaining(text.charAt(i), cache[0][i-1]);
			
		}
		
		for(int i=0;i<n;i++){
			String remStr = returnRemaining(
					text.charAt(i), str);
			if(remStr.isEmpty()){
				minLenSofar = 1;
				start=i;
				end=i;
			}
			cache[i][i] = remStr;
		}
		
		
		
/*		for(int i=1;i<n;i++){
			String previous = cache[i][i-1];
			String remaining = returnRemaining(text.charAt(i),
					previous);
			if(remaining.isEmpty() && 2<minLenSofar){
				minLenSofar = 2;
				start = i-1;
				end=i;
			}
			cache[i]
		}
*/		
		for(int k=2;k<=n;k++){
			for(int i=0;i<n-k;i++){
				// end of substr to check
				int j = i+k-1;
				//String substr = text.substring(i, j)
				String previous = cache[i][j-1];
				String remaining = str;
				if(previous != null)
					remaining = returnRemaining(text.charAt(j),
						previous);
				else
					System.out.printf("Null found for:%d,%d",i,j-1);
				if(remaining.isEmpty() && k<minLenSofar){
					minLenSofar = k;
					start = i;
					end=j;
				}
				cache[i][j]=remaining;
			}
		}
		
		System.out.printf("Min len:%d\tstart:%d\tend:%d\n", 
				minLenSofar, start,end);
		return text.substring(start, end+1);
	}
	
	public static String returnRemaining(char text, String str){
		String remaining = "";
		if(str.isEmpty())
			return remaining;
		
		if(str.indexOf(text) >= 0){
			char[] chars = str.toCharArray();
			if(chars.length > 1){
				char[] newChars = new char[chars.length-1];
				int index=0;
				for(char c : chars){
					if(c != text)
						newChars[index++]=c;
				}
				remaining = new String(newChars);
			}else
				remaining = "";
		}else
			remaining = str; // Unaffected
		return remaining;
	}

	
	public static String returnRemaining(String text, String str){
		Map<Character, Integer> textMap = getCharMap(text);
		Map<Character, Integer> strMap = getCharMap(str);
		
		StringBuilder builder = new StringBuilder();
		
		// TODO handle when text len is less than str.
		Set<Character> keys = strMap.keySet();
		for(Character c : keys){
			// Found
			if(textMap.containsKey(c)){
				int textCount = textMap.get(c);
				int strCount = strMap.get(c);
				// We have found more than required.
				if(textCount >= strCount)
					strMap.remove(c);
				else{
					strMap.put(c,strCount-textCount);
					char[] arr = new char[strCount-textCount];
					Arrays.fill(arr, c);
					builder.append(arr);
				}
			}
		}
		return (builder.length() > 0)?builder.toString():"";
	}
	
	public static Map<Character, Integer> getCharMap(String str){
		char[] chars = str.toCharArray();
		Map<Character, Integer> map = new HashMap<>();
		for(char c : chars){
			if(!map.containsKey(c))
				map.put(c, 0);
			map.put(c, map.get(c)+1);
		}
		
		return map;
	}
}
