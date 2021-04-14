package crack.code.chp1;

public class RemoveDupChars {

	public static void main(String[] args) {
		
		System.out.println(removeDups("abccdeeef".toCharArray()));
	}
	
	/**
	 * abccdeeef
	 * 
	 * @param input
	 * @return
	 */
	public static String removeDups(char[] input) {
		
		int n = input.length;
		int tail =1;
		
		for(int i=1;i<n;i++) {
			
			int j=0;
			for(;j<i;j++) {
				if(input[j] == input[i])
					break;
			}
			
			if(j==tail) {
				input[tail]=input[i];
				tail++;
			}
		}
		
		input[tail] = 0;
		
		return new String(input);
	}

}
