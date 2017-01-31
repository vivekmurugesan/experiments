package test.subseq;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author vivek
 *
 */
public class SubSeqSearch {

	private static String input = "887 778 916 794 336 387 493 650 422 363 28 691 60 764 927 541 427 173 737 212 369 568 430 783 531 863 124 68 136 930 803 23 59 70 168 394 457 12 43 230 374 422 920 785 538 199 325 316 371 414 527 92 981 957 874 863 171 997 282 306 926 85 328 337 506 847 730 314 858 125 896 583 546 815 368 435 365 44 751 88 809 277 179 789";
	
	public static void main(String[] args) {
		String[] tokens = input.split(" ");
		int[] arr = new int[tokens.length];
		int i=0;
		for(String token:tokens){
			arr[i++]=Integer.parseInt(token);
		}
		
		find3Numbers(arr, arr.length);
		
		find3Numbers1(arr, arr.length);
	}
	
	public static void findIf3NumbersSeq(int[] arr){
		int min = arr[0];
		int max = arr[arr.length-1];
		for(int i=0,j=arr.length-1;i<j;){
			
		}
	}
	
	public static void find3Numbers(int[] a, int n)
    {
        int result =0;
        Set<Integer> indexSet = new HashSet<>();
        for(int i=0;i<n;i++){
        	for(int j=i+1;j<n;j++){
        		if(a[j] > a[i]){
        			if(indexSet.contains(i) || indexSet.contains(j))
        				continue;
        			for(int k=j+1;k<n;k++){
        				if(a[k]>a[j] && !indexSet.contains(k) &&
        						!indexSet.contains(i) && !indexSet.contains(j)){
        					result++;   
        					indexSet.add(i);indexSet.add(j);indexSet.add(k);
        				}
        			}
        		}
        	}
        }
        System.out.printf("%d",result);
    }
	
	/**
	 * 1. Iterate through the numbers
	 * 	i. Track the min number encountered so far, if the current number is 
	 * min then set the minIdex of i as -1 otherwise, set the lesser[i] as 
	 * minIndex.
	 *  ii. Similarly start from end of the array, to track the maxIndex
	 * and set the greater[i] accordingly.
	 * 2. Use these arrays lesser and greater, for every element i if both 
	 * lesser[i] and greater[i] not equal to -1 then we have a sequence of 
	 * a,b,c where a<b<c.
	 * 
	 */
	public static int find3Numbers1(int[] a, int n)
    {
		int[] lesser = new int[n];
		int[] greater = new int[n];
		
		int result = 0;
		
		int minIndex = 0;
		int maxIndex = n-1;
		
		lesser[0] = -1;
		
		for(int i=1;i<n;i++){
			if(a[i] < a[minIndex]){
				minIndex = i;
				lesser[i] = -1;
			}else
				lesser[i] = minIndex;
		}
		
		greater[n-1] = -1;
		for(int i=n-2;i>0;i--){
			if(a[i] > a[maxIndex]){
				maxIndex = i;
				greater[i] = -1;
			}else
				greater[i] = maxIndex;
		}
		
		System.out.println();
		for(int i=1;i<n-1;i++){
			if(lesser[i] != -1 && greater[i] != -1){
				result++;
				System.out.printf("%d<%d<%d\n",a[lesser[i]], a[i], a[greater[i]]);
			}
		}
		
		System.out.println("Count:"+ result);
		return result;
    }
	
	

}
