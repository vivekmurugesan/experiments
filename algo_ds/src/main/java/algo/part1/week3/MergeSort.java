package algo.part1.week3;

public class MergeSort {

	public static void main(String[] args) {

		int[] input = {1,3,5,7,9,2,4,6,8,10,12,14};
		
		sort(input);
		
		//merge(input, 0, 4, input.length);
		
		for(int x : input)
			System.out.println(x+"\t");
	}
	
	public static void sort(int[] input) {
		int[] aux = new int[input.length];
		
		for(int i=0;i<input.length;i++)
			aux[i] = input[i];
	}
	
	public static void sort(int[] input, int[] aux, int lo, int hi) {
		
		if(lo<hi)
			return;
		
		int mid = lo + (hi-lo)/2;
		sort(input, aux, lo, mid);
		sort(input, aux, mid+1, hi);
		merge(input, aux, lo, mid, hi);
		
	}
	
	public static void merge(int[] input, int[] aux, int lo, int mid, int high) {
		
		
		int i=lo;
		int j=mid+1;
		for(int k=lo;k<high;k++) {
			if(i>mid)
				input[k] = aux[j++];
			else if(j> high)
				input[k] = aux[i++];
			else if(aux[i]<=aux[j])
				input[k] = aux[i++];
			else
				input[k] = aux[j++];
				
		}
	}

}
