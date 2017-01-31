package test.sum.zero;

import java.util.Arrays;

/**
 * 
 * @author vivek
 *
 */
public class FindSumIsZeroFromThreeArrayElements {

	public static void main(String[] args) {
		int [] array = new int[] {-7, -3, 2, 10, 3, 4, 6, 7,0};
		Arrays.sort(array);
		System.out.println("Sorted Array Elements : ");
		for (int i = 0 ; i < array.length ; i++) {
			System.out.print(array[i] + " ,");	
		}
		System.out.println("\n\nSum Of Zero from three array elements : ");
		isSumZero(array);
	}

	/**
	 * 1. Sort the elements of array.
	 * 2. For every element i track the sum with other possible elements.
	 * 3. User to pointers iter and reviter initialized with i+1 and len-1
	 * respectively.
	 * 4. Use sum = a[iter]+a[i]+a[reviter] to check against zero.
	 * 5. If sum > 0
	 * 	i)Then Update reviter to reviter-- to search with smaller elements.
	 *  ii) Else Update iter to iter++ to search with larger elements.
	 * 6. Iterate until sum becomes zero.
	 * @param array
	 */
	public static void isSumZero(int[] array){
		for (int i = 0; i < array.length; i++){
			int iter = i + 1;
			int reviter = array.length - 1;
			int tmp = 0;
			while ( reviter >= 0 && iter < array.length){
				tmp = array[iter] + array[reviter] + array[i];
				if( tmp > 0){
					reviter--;
				}
				else if( tmp < 0) {
					iter++;
				}
				else {
					System.out.println(array[i] + ", "+ array[iter] +", "+ array[reviter]  );
					break;
				}
			}
		}
	}
}
