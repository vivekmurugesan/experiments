package test.dynamic;

/**
 * 
 * @author vivek
 *
 */
public class LongestPal {

	public static void main(String[] args) {
		
		String test = "testtaabaatt";
		System.out.printf("LongestPal of:%s is %s\n", test, longestPal(test) );
		System.out.printf("Dyn LongestPal of:%s is %s\n", test, longestPalDyn(test) );
		System.out.println(checkPal("tesett".toCharArray(),0,6));
		
		test = "tetsttaabaatttbaxt";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestPalDynSubSeq(test) );
		
		test = "tetsttaabaatttb";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestPalDynSubSeq(test) );
		
		test = "axabcaca";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestPalDynSubSeq(test) );
	}
	
	/**
	 * 
	 * @param test
	 * @return
	 */
	public static String longestPal(String test){
		
		char[] chars = test.toCharArray();
		int n = chars.length;
		int start=0;
		int maxLenSofar=0;
		
		// Pals of length k
		for(int k=1;k<n;k++){
			// Starting position i.
			for(int i=0;i<n;i++){
				// Check if str(i to k) is pal
				boolean isPal = checkPal(chars, i,k);
				if(isPal && k>maxLenSofar){
					maxLenSofar=k;
					start=i;
				}
			}
		}
		
		return test.substring(start, start+maxLenSofar);
	}
	
	public static boolean checkPal(char[] chArr, int start, int len){
		if(start+len > chArr.length)
			return false;
		boolean isPal = true;
		for(int i=start,j=start+len-1;i<j;i++,j--){
			if(chArr[i] != chArr[j]){
				isPal = false;
				break;
			}
		}
		
		return isPal;
	}
	
	public static String longestPalDyn(String test){
		char[] chars = test.toCharArray();
		int n = chars.length;
		int start=0;
		int maxLenSofar=0;

		boolean[][] table = new boolean[n][n];
		
		for(int i=0;i<n;i++)
			table[i][i]=true;
		
		for(int i=0;i<n-1;i++){
			if(chars[i] == chars[i+1]){
				table[i][i+1] = true;
				start = i;
				maxLenSofar = 2;
			}
		}
		
		for(int k=3;k<=n;k++){
			for(int i=0;i<=n-k;i++){
				int j=i+k-1;
				if(table[i+1][j-1] && chars[i]==chars[j]){
					table[i][j]=true;
					if(k>maxLenSofar){
						maxLenSofar = k;
						start=i;		
					}
				}
			}
		}
		
		System.out.println(start);
		System.out.println(maxLenSofar);
		
		return test.substring(start, start+maxLenSofar);
	}


	/**
	 * 1. int[][] table is the cache used to store partial results.
	 * 2. Update all the diagonal elements of palindromes of length 1.
	 * 3. Update the palindromes of length 2 by comaring successive
	 * 4. For all lengths starting from k use the recurrence relationship
	 * and the cache to compute the values and update.
	 * 5. Track the max possible length at every step.
	 * 6. Return the max value encountered. 
	 * elements.
	 * @param test
	 * @return
	 */
	public static int longestPalDynSubSeq(String test){
		char[] chars = test.toCharArray();
		int n = chars.length;
		int start=0;
		int maxLenSofar=0;
		int end=0;

		int[][] table = new int[n][n];
		
		for(int i=0;i<n;i++)
			table[i][i]=1;
		
		for(int i=0;i<n-1;i++){
			if(chars[i] == chars[i+1]){
				table[i][i+1] = 2;
				start = i;
				maxLenSofar = 2;
			}
		}
		
		for(int k=3;k<=n;k++){
			for(int i=0;i<=n-k;i++){
				int j=i+k-1;
				int maxLenSubSeq = getMaxLenPalSubSeq(
						chars, i, j, table);
				if(maxLenSubSeq > maxLenSofar){
					maxLenSofar = maxLenSubSeq;
					start = i;
					end=j;
				}
			}
		}
		
		System.out.println("start:" + start);
		System.out.println("maxLenSubseq:" + maxLenSofar);
		
		/*return test.substring(start, end+1);*/
		return maxLenSofar;
	}
	
	/**
	 * If(chars[start] == chars[end])
	 * 		table[start][end] = table[start+1][end-1]+2
	 * else 
	 * 		table[start][end] =
	 * 			Max(table[start+1][end], table[start][end-1]) 
	 *
	 * @return
	 */
	public static int getMaxLenPalSubSeq(char[] chars, 
			int start, int end, int[][] table){
		if(start==end)
			return 1;
		if(chars[start] == chars[end]){
			if(table[start+1][end-1] > 0)
				table[start][end] = table[start+1][end-1]+2;
			else{
				table[start+1][end-1] = 
						getMaxLenPalSubSeq(chars, start+1, end-1, table);
				table[start][end] = table[start+1][end-1] +2; 
			}
		}else{
			if(table[start][end-1] <= 0)
				table[start][end-1] = 
					getMaxLenPalSubSeq(chars, start, end-1, table);
			if(table[start+1][end] <= 0)
				table[start+1][end] = 
					getMaxLenPalSubSeq(chars, start+1, end, table);
			table[start][end] = Math.max(table[start][end-1], 
					table[start+1][end]);
		}
		return table[start][end];	
	}
}


