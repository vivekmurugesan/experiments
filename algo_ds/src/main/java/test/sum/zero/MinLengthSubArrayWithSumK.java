package test.sum.zero;

/**
 * 
 * @author vivek
 *
 */
public class MinLengthSubArrayWithSumK {

	public static void main(String[] args) {
		int[] arr = {2,3,1,1,-1,3,4};
		int k=7;
		System.out.printf("min len:%d\t of sum:%d\n",minLenSubArray(arr, k),k);
	}
	
	/**
	 * 1. Iterate through all the elements, with minLen initialized to 
	 * Integer.MAX_VALUE.
	 * 2. For every element at i, start including elements into the sum until 
	 * it becomes >k.
	 * 3. If sum becomes to equal k, then update the minLen.
	 * 
	 */
	public static int minLenSubArray(int[] arr, int k){
		int minLen = Integer.MAX_VALUE;
		int startIndex=-1;
		int endIndex=-1;
		for(int start=0;start<arr.length;start++){
			int sum=0;
			int len=0;
			for(int end=start;end<arr.length;end++){
				sum += arr[end];
				len++;
				if(sum == k && len<minLen){
					minLen = len;
					startIndex=start;
					endIndex=end;
				}
				if(sum>k)
					break;
			}
		}
		
		while(startIndex != -1 && endIndex != -1 &&
				startIndex<=endIndex)
			System.out.printf("%d\t",arr[startIndex++]);
		System.out.println();
		return minLen;
	}

}
