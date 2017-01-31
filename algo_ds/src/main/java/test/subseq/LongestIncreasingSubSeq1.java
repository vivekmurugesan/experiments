package test.subseq;

import java.util.Arrays;

/**
 * 
 * @author vivek
 *
 */
public class LongestIncreasingSubSeq1 {

	public static void main(String[] args) {
		int[] input = {3,4,-1,0,6,1,2,3};

		int[] input1 = {3,4,-1,5,8,2,3,12,7,9,10};

		search1(input1);
	}
	
	public static void search1(int[] input){
		
		int n = input.length;
		int[] lengths = new int[n];
		int[] parents = new int[n];
		
		Arrays.fill(lengths, 1);
		Arrays.fill(parents, -1);
		
		for(int i=0; i<n;i++){
			int len=0;
			for(int j=i;j<n;j++){
				if(input[j]>input[i]){
					if(lengths[j] < lengths[i]+1){
						lengths[j] = lengths[i]+1;
						parents[j] = i;
					}
				}
			}
		}
		
		int maxLen = Integer.MIN_VALUE;
		int maxIndex = -1;
		for(int i=0;i<n;i++){
			if(lengths[i] > maxLen){
				maxLen=lengths[i];
				maxIndex = i;
			}
		}
		
		System.out.printf("\n Length of max incr subseq is:%d\n", maxLen);
		print(input, parents, maxIndex, maxLen);
	}
	
	public static void print(int[] input, int[] parents, int index, int len){
		if(len <= 0)
			return;
		print(input, parents, parents[index], len-1);
		System.out.printf("%d\t", input[index]);
	}

}
