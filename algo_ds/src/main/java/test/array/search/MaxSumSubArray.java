package test.array.search;

/**
 * Using Kandene's algorithm.
 * @author vivek
 *
 */
public class MaxSumSubArray {

	public static void main(String[] args) {
		int[] arr = {-2, -3, 4, -1, -2, 1, 5, -3};
		MaxSumSubArray computeUtil = new MaxSumSubArray();
		
		System.out.printf("\nMax sum of the contigous subarry:%d\n", computeUtil.maxSumSubArray(arr));
		
		System.out.printf("\nMax sum of the contigous subarry:%d\n", computeUtil.maxSumSubArray(arr, 0, arr.length-1));
	}

	/**
	 * 1. Initialize,
	 * 		maxSumSofar=0;maxSumEndingHere=0;
	 * 2. Iterate through all the elements
	 * 3. maxSumEndingHere += arr[i]
	 * 4. If(maxSumEndingHere<0)
	 * 		maxSumEndingHere=0; 
	 * 5. If(maxSumEndingHere>maxSumSofar)
	 * 		maxSumSofar=maxSumEndingHere
	 * @param arr
	 * @return
	 */
	public int maxSumSubArray(int[] arr){
		int maxSumEndingHere = 0;
		int maxSumSoFar=0;
		
		int start=-1,end=-1; // TODO to track the positions of the subarray.
		
		for(int i=0;i<arr.length;i++){
			maxSumEndingHere += arr[i];
			
			if(maxSumEndingHere < 0)
				maxSumEndingHere=0;
			if(maxSumEndingHere > maxSumSoFar)
				maxSumSoFar=maxSumEndingHere;
		}
			
		return maxSumSoFar;
	}
	
	public int maxCrossingSum(int[] arr, int l, int m, int h){
		
		int leftSum=Integer.MIN_VALUE;
		int sum=0;
		for(int i=l;i<=m;i++){
			sum += arr[i];
			if(sum>leftSum)
				leftSum=sum;
		}
		
		sum=0;
		int rightSum = Integer.MIN_VALUE;
		for(int i=m+1; i<=h;i++){
			sum += arr[i];
			if(sum>rightSum)
				rightSum=sum;
		}
		
		return leftSum+rightSum;
	}
	
	/**
	 * Using Divide and Conquer approach
	 * TODO: this code has defect, and needs to be fixed.
	 * @param arr
	 * @param l
	 * @param h
	 * @return
	 */
	public int maxSumSubArray(int[] arr, int l, int h){
		if(l==h)
			return arr[l];
		
		int m=(l+h)/2;
		
		return max(maxSumSubArray(arr, l,m),
				maxSumSubArray(arr, m+1,h),
				maxCrossingSum(arr, l, m, h));
	}
	
	public int max(int a, int b, int c){
		return Math.max(Math.max(a, b), c);
	}
}
