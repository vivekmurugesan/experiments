package binary.trees;

import java.util.Arrays;

/**
 * 
 * @author vivek
 *
 */
public class AllPossibleBSTs {

	public static void main(String[] args) {
		
		int n=4;
		int possibilities = possibilities(n);
		System.out.printf("Number of BSTs possible with n: %d are: %d\n", n, possibilities);
	}
	
	public static int possibilities(int n){
		int[] cache = new int[n];
		Arrays.fill(cache, -1);
		return possibilities(n, cache);
	}
	
	/**
	 * 
	 * numberOfBSTs(n=4) = numOfBSTs_root_as_1 + numOfBSTs_root_as_2
		+ numOfBSTs_root_as_3 + numOfBSTs_root_as_4
					= numberOfBSTs(0) * numberOfBSTs(3) 
					+ numberOfBSTs(1) * numberOfBSTs(2) 
					+ numberOfBSTs(2) * numberOfBSTs(1)
					+ numberOfBSTs(3) * numberOfBSTs(0)  
			= 5 + 2 + 2 + 5 
	 * @param n
	 * @param cache
	 * @return
	 */
	public static int possibilities(int n, int[] cache){
		if(n<0)
			return 0;
		if(n==0 || n==1)
			return 1;
		int possibilities = 0;
		for(int i=0;i<n;i++){
			if(cache[i] == -1)
				cache[i] = possibilities(i, cache);
			if(cache[n-i-1] == -1)
				cache[n-i-1] = possibilities(n-i-1, cache);
			possibilities += cache[i] * cache[n-i-1];
		}
		
		return possibilities;
	}

}
