package crack.code.chp1;

public class StrUniqueChars {

	public static void main(String[] args) {
		
		String test = "abcde";
		System.out.println("result: %s" + hasUniqChars(test));
		
		test = "abbcd";
		System.out.println("result: %s" + hasUniqChars(test));
		
		test = "abcde";
		System.out.println("result: %s" + hasUniqChars1(test));
		
		test = "abbcd";
		System.out.println("result: %s" + hasUniqChars1(test));
		
		test = "abcde";
		System.out.println("result: %s" + hasUniqChars2(test));
		
		test = "abbcd";
		System.out.println("result: %s" + hasUniqChars2(test));
	}
	
	public static boolean hasUniqChars(String str) {
		
		if(str == null || str.length() <= 0)
			return true;
		
		boolean result = true;
		int n = str.length();
		
		for(int i=0; i<n;i++) {
			for(int j=i+1;j<n;j++) {
				if(str.charAt(i) == str.charAt(j)) {
					result = false;
					return result;
				}
			}
		}
		
		return result;
	}
	
	// With char set
	public static boolean hasUniqChars1(String str) {
		boolean[] charSet = new boolean[256];
		int n = str.length();
		
		for(int i=0;i<n;i++) {
			int val = str.charAt(i);
			if(charSet[val] == true)
				return false;
			charSet[val] = true;
		}
		
		return true;
	}
	
	/**
	 * To handle only 'a'-'z', by using a bit vector formed with 
	 * an integer.
	 * @param str
	 * @return
	 */
	public static boolean hasUniqChars2(String str) {
		int checker = 0;
		int n = str.length();
		
		for(int i=0;i<n;i++) {
			int mask = 1 << (str.charAt(i) - 'a');
			
			if((checker & mask) > 0)
				return false;
			checker |= mask;
		}
		
		return true;
	}

}
