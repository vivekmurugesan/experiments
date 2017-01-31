package test.sum.zero;

import java.util.Arrays;

/**
 * 
 * @author vivek
 *
 */
public class ThreeSum {

	public static void main(String[] args) {
		int[] arr = {-7, -3, 2, 10, 3, 4, 6, 7,0};
		
		Arrays.sort(arr);
		
		findSumZero(arr);
	}
	
	public static void findSumZero(int[] arr){
		
		for(int i=1;i<arr.length-1;i++){
			int iter=i+1;
			int revIter = arr.length-1;
			
			while(iter < arr.length && revIter >= 0){
				int testSum = arr[iter] + arr[i] + arr[revIter];
				
				if(testSum > 0)
					revIter--;
				else if(testSum < 0)
					iter++;
				else{
					System.out.printf("Sum of: %d %d %d is zero..\n",
							arr[iter], arr[i], arr[revIter]);
					break;
				}
			}
		}
	}

}
