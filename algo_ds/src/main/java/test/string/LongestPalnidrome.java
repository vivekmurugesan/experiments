package test.string;

public class LongestPalnidrome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LongestPalnidrome longPal = new LongestPalnidrome();
		longPal.longestPalindromeBF("testvivekkevivtest");
		System.out.println();
		longPal.longestPalindromeDyn("testvivekkevivtest");
		System.out.println();
		longPal.longestPalindromeDyn("testtaabaatt");
	}
	
	/** Brute force algo */
	public void longestPalindromeBF(String str){
		int n = str.length();
		char[] chArr = str.toCharArray();
		int start = 0;
		int maxLength = 1;
		for(int k=1;k<=n;k++){
			for(int i=0;i<n;i++){
				if((i+k) <=n){
					boolean isPal = true;
					for(int j=0;j<k;j++){
						/*if((i+j) > (i+k-j))
							break;*/
						if(chArr[i+j] != chArr[i+k-1-j]){
							isPal = false;
							break;
						}
					}
					if(isPal && k > maxLength){
						start = i;
						maxLength = k;
					}
				}
			}
		}
		printLongestPalindrome(chArr, start, maxLength);
	}
	
	private void printLongestPalindrome(char[] chArr, int start, int len){
		for(int i=start;i<len+start;i++)
			System.out.printf("%c",chArr[i]);
	}
	
	private void longestPalindromeDyn(String str){
		int n = str.length();
		char[] chArr = str.toCharArray();
		int start = 0;
		int maxLength = 1;
		/* Boolean indicating str[i:j] */
		boolean[][] table = new boolean[n][n];
		/* All str of len 1 */
		for(int i=0;i<n;i++)
			table[i][i] = true;
		/* All str of len 2 */
		for(int i=0;i<n-1;i++){
			if(chArr[i] == chArr[i+1]){
				table[i][i+1] = true;
				start = i;
				maxLength=2;
			}
		}
		
		/** Checking for len >= 3 */
		for(int k=3;k<=n;k++){
			/* Starting index */
			for(int i=0;i<=n-k;i++){
				int j=i+k-1;
				if(table[i+1][j-1] && chArr[i] == chArr[j]){
					table[i][j]=true;
					if(k>maxLength){
						start=i;
						maxLength=k;
					}
				}
			}
		}
		
		printLongestPalindrome(chArr, start, maxLength);
	}

}
