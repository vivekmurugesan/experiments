package test.subseq;

import java.util.Arrays;

/**
 * 
 * @author vivek
 *
 */
public class LongestIncreasingSubSeq {

	public static void main(String[] args) {
		int[] input = {3,4,-1,0,6,1,2,3};
		
		int[] input1 = {3,4,-1,5,8,2,3,12,7,9,10};
		
		System.out.println("Size of longest increasing subseq:" + 
				longestIncreasingSubSeq(input));
		
		System.out.println("Size of longest increasing subseq:" + 
				longestIncreasingSubSeq1(input));
	}
	
	/**
	 * Use array cache to track the max length at position i and array path
	 * to track the parent of max length subseq of given element i.
	 * 1. Initialize cache with values 1 and parent with values -1.
	 * 2. For every element starting i=1, check all elements a[j] where
	 * j<i.
	 * 3. If a[i] > a[j] then set cache[i] = max(cache[i], cache[j]+1).
	 * 4. Accordingly update the parent[i].
	 * 5. At the end of the loop search for the max value of cache and
	 * that is the max length of increasing sub sequence.
	 * 6. Use the path array to traverse through the parent pointers to
	 * construct the subsequence.
	 * @param arr
	 * @return
	 */
	public static int longestIncreasingSubSeq(int[] arr){
		if(arr.length <= 0)
			return -1;
		int[] cache = new int[arr.length];
		int[] path = new int[arr.length];
		Arrays.fill(cache, 1);
		Arrays.fill(path, -1);
		
		for(int i=1;i<arr.length;i++){
			for(int j=0;j<i;j++){
				if(arr[i] >= arr[j]){
					if(cache[j]+1 > cache[i]){
						path[i] = j;
						cache[i] = cache[j]+1;
					}
				}
			}
		}
		
		int max = 0;
		int maxIndex = -1;
		for(int i=0;i<cache.length;i++){
			if(cache[i] > max){
				max = cache[i];
				maxIndex = i;
			}
		}
		
		System.out.println("Values at subseq");
		printSeq(arr,path, maxIndex, max);
		System.out.println();
		
		return max;
	}
	
	public static void printSeq(int[] arr,int[] path, int index, int len){
		if(len<=0)
			return;
		printSeq(arr,path, path[index],len-1);
		System.out.printf("%d\t",arr[index]);
	}
	
	/**
	 * Use couple of arrays of size n, cache to track the ends of subsequences 
	 * length i and path to track the parent of each element.
	 * 1. Initialize both arrays with values -1.
	 * 2. For every element a[i], check if a[i] > a[cache[len]]
	 *    i) Then len++;a[len]=i. Meaning current element can be added to the max
	 *    subsequence.
	 *    ii) Else do a binary search for the position of a[i] using the indices
	 *    available in cache. And replace the value in cache with i. It replaces
	 *    the minimum possible of a[cache[j]] that is greater than a[i]. 
	 *    (This operation ensures that subseq of length n has a better end, i.e.
	 *    we can potential add more values to the subsequence.) 
	 *    iii) Accordingly update the parent pointers to path array.
	 * 3. At the end the value denoted by len is the max length subsequence. 
	 * @param arr
	 * @return
	 */
	public static int longestIncreasingSubSeq1(int[] arr){
		if(arr.length <= 0)
			return -1;
		int[] cache = new int[arr.length+1];//Tracking ends of each subsequences of length i.
		int[] path = new int[arr.length];//Tracking the parent or predecessors of each element
		int length = 1; // Max length subsequence encountered so far.
		Arrays.fill(cache, -1);
		Arrays.fill(path, -1);
		
		/**
		 * If a[i] > a[cache[len]]
		 * then len++;cache[len]=i;
		 * else binary search for the position of a[i] and replace ciel with a[i]
		 */
		cache[1]=0;
		for(int i=1;i<arr.length;i++){
			if(arr[i]>arr[cache[length]]){
				length++;cache[length]=i;
				path[i]=cache[length-1];
			}else{
				int low = 1;
				int high = length;
				int mid = (int) Math.ceil((low+high)/2);
				while(low <= high){
					if(arr[i]<arr[cache[mid]])
						high=mid-1;
					else 
						low = mid+1;
					mid=(low+high)/2;
				}
				int pos = low;
				path[i] = cache[pos-1];
				cache[pos]=i;
			}
		}
		
		System.out.println("Values at subseq");
		printSeq(arr,path, cache[length], length);
		System.out.println();
		
		return length;
	}


}
