package test.sort;

/**
 * 
 * @author vivek
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		int[] arr = {5,30,1,0,80,7,6,34,9,2,213};
		
		sort(arr, 0, arr.length-1);
		for(int a:arr){
			System.out.printf("%d\t",a);
		}
	}
	
	public static void sort(int[] array, int left, int right){
		if(left >= right)
			return;
		
		int pivot = array[(left+right)/2]; // TODO there are other ways to choose pivot.
		int index = partition(array, left, right, pivot);
		
		sort(array, left, index-1);
		sort(array, index, right);
	}

	/**
	 * To place the pivot in the right position.
	 * @param array
	 * @param left
	 * @param right
	 * @param pivot
	 * @return
	 */
	private static int partition(int[] array, int left, int right, int pivot) {
		
		while(left<=right){
			while(array[left]<pivot)
				left++;
			while(array[right]>pivot)
				right--;
			if(left<=right){
				swap(array, left, right);
				left++;
				right--;
			}
		}
		
		return left;
	}

	private static void swap(int[] array, int left, int right) {
		int t = array[left];
		array[left]=array[right];
		array[right]=t;
	}

}
