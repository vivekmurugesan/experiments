package test.sum.zero;

/**
 * 
 * @author vivek
 *
 */

public class FindMaxSumSubArray {

		public static void main(String[] args){
			
			//int[] arr = {-2, -3, 4, -1, -2, 1, 5, -3};
			/*int[] arr =  { -1, 6, 2, -20, 13, 17,5,-3};*/
			int[] arr =  { -1, 6, 2,13,17,7, -20, 13, 17,5,-3};
			int maxSumTillHere = 0;
			int maxSumSoFar = 0;
			int start=0;
			int end=0;
			for(int i=0; i< arr.length;i++){
				if(maxSumTillHere == 0)
					start=i;
				maxSumTillHere += arr[i];
				if(maxSumTillHere < 0){
					maxSumTillHere = 0;
				}					
				if(maxSumSoFar < maxSumTillHere){
					maxSumSoFar = maxSumTillHere;
					end=i;
				}
			}
			System.out.println(maxSumSoFar);
			System.out.println("Start and end::" +start+ "--" + end);
		}
}
