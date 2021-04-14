package algo.part1.week2;

public class InsertionSort {

public static void main(String[] args) {
		
		int[] input = {3,2,1,5,7,8,4,0,9,6};
		
		sort(input);
		for(int x : input)
			System.out.printf("%d\t", x);
	}
	
	/**
	 * At every iteration i find the ith smallest element from 
	 * the array and replace that with the first element of the segment.
	 * @param input
	 */
	public static void sort(int[] input) {
		
		int n = input.length;
		for(int i=1;i<n;i++) {
			for(int j=i-1;j>0;j--) {
				if(input[i]<input[j]) {
					swap(input, i, j);
				}else
					break;
			}
		}
	}
	
	public static void swap(int[] input, int i, int j) {
		int t = input[i];
		input[i] = input[j];
		input[j] = t;
	}
}
