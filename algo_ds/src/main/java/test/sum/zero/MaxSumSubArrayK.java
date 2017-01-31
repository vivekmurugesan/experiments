package test.sum.zero;

/**
 * 
 * @author vivek
 *
 */
public class MaxSumSubArrayK {

	public static void main(String[] args) {
		int[] input = {11,-8,16,-7,24,-2,3};
		int k=3;
		
		System.out.println("MaxSum:"+ maxSumSubArrayK(input, k));

	}
	
	/**
	 * 1. Create an initial window of k numbers, with first k numbers
	 * in the array along with computing its sum.
	 * 2. Use a sliding window technique to remove the first number from
	 * the window to accommodate new number at the end.
	 * 3. Recompute the sum accordingly from the prior value.
	 * 4. Track the max sum so far to compare it against the current 
	 * window sum.
	 * @param arr
	 * @param k
	 */
	public static int maxSumSubArrayK(int[] arr, int k){
		if(arr.length < k)
			return Integer.MIN_VALUE;
		int maxSumSoFar=0;
		int currentWindowSum=0;
		int maxSumStartIndex=0;
		for(int i=0;i<k;i++)
			currentWindowSum += arr[i];
		maxSumSoFar = currentWindowSum;
		
		for(int i=k;i<arr.length;i++){
			currentWindowSum -= arr[i-k];
			currentWindowSum += arr[i];
			if(currentWindowSum > maxSumSoFar){
				maxSumSoFar = currentWindowSum;
				maxSumStartIndex = i-k+1;
			}
		}
		
		System.out.println("Max Sum:" + maxSumSoFar);
		for(int i=0;i<k;i++){
			System.out.printf("%d\t",arr[i+maxSumStartIndex]);
		}
		System.out.println();
		return maxSumSoFar;
	}

}
