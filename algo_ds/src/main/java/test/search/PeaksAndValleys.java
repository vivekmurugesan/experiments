package test.search;

/**
 * 
 * @author vivek
 *
 */
public class PeaksAndValleys {

	public static void main(String[] args) {
		int[] input = {1,3,9,10,100,1000,100,1,7,13,27,100000, 1000000, 27000,13000};
		System.out.println("Input array:");
		for(int n : input)
			System.out.printf("%d\t", n);
		System.out.println();
		peaksAndValleys(input);
		System.out.println("After rearranging:");
		for(int n : input)
			System.out.printf("%d\t", n);
		System.out.println();
		
	}
	
	/**
	 * For every element b, proceeded by a and followed by c,
	 * swap b with max(a,b,c)
	 * this operation will let the entire array arranged in 
	 * peaks and valleys order. 
	 * @param input
	 */
	public static void peaksAndValleys(int[] input){
		for(int i=1;i<input.length;i+=2){
			int maxIndex = maxIndex(input, i);
			if(maxIndex != i)
				swap(input, i, maxIndex);
		}
	}
	
	/**
	 * Compares the predecessor and successor of the element at index i
	 * and returns the index of max among the 3.
	 * @param i
	 * @return
	 */
	public static int maxIndex(int[] input, int i){
		int a = (i-1<input.length)?input[i-1]:Integer.MIN_VALUE;
		int b = (i<input.length)?input[i]:Integer.MIN_VALUE;
		int c = (i+1<input.length)?input[i+1]:Integer.MIN_VALUE;
		int max = Math.max(Math.max(a, b), c);
		int result=-1;
		if(max==b)
			result = i;
		else if(max==a)
			result = i-1;
		else if(max==c)
			result = i+1;
		
		return result;
	}
	public static void swap(int[] input, int i, int j){
		int t = input[i];
		input[i]=input[j];
		input[j]=t;
	}
}
