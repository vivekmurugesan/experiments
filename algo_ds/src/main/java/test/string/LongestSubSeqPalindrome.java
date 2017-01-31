package test.string;

/**
 * 
 * @author vivek
 *
 */
public class LongestSubSeqPalindrome {

	public static void main(String[] args) {
		String test = "tetsttaabaatttbaxt";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestSubSeqPal(test) );
		
		test = "tetsttaabaatttb";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestSubSeqPal(test) );
		
		test = "axabcaca";
		System.out.printf("Dyn LongestPal SubSeq of:%s is %d\n",
				test, longestSubSeqPal(test) );
	}
	
	public static int longestSubSeqPal(String input){
		
		int n = input.length();
		int[][] table = new int[n][n];
		int maxLenSofar=1;
		int start=0;
		int end=0;
		
		for(int i=0;i<n;i++)
			table[i][i]=1;
		for(int i=0;i<n-1;i++){
			if(input.charAt(i) == input.charAt(i+1)){
				table[i][i+1]=2;
				if(maxLenSofar<2){
					maxLenSofar=2;
					start=i;
					end=i+1;
				}
			}
		}
		
		for(int k=3;k<n;k++){
			for(int i=0;i<n-k;i++){
				int j=i+k-1;
				if(LSP(input, i, j, table)>maxLenSofar){
					maxLenSofar = k;
					start=i;
					end=j;
				}
			}
		}
		
		System.out.println("max len:"+ maxLenSofar);
		System.out.println("start:" + start);
		
		return maxLenSofar;
	}
	
	/**
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @param table
	 */
	public static int LSP(String input, int start, int end,int[][] table){
		if(start==end)
			return 1;
		if(input.charAt(start) == input.charAt(end)){
			if(table[start+1][end-1] > 0){
				table[start][end] = table[start+1][end-1]+2;
				return table[start][end];
			}else{
				table[start+1][end-1] = 
						LSP(input, start+1, end-1, table);
				table[start][end] = table[start+1][end-1]+2;
				return table[start][end];
			}
		}else{
			if(table[start+1][end] <= 0)
				table[start+1][end] = LSP(input, start+1, end, table);
			if(table[start][end-1] <= 0)
				table[start][end-1] = LSP(input, start, end-1, table);
			table[start][end] = 
					Math.max(table[start+1][end], table[start][end-1]);
			return table[start][end];
		}
	}

}
