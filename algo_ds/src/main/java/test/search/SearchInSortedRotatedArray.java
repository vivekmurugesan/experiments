package test.search;

/**
 * 
 * @author vivek
 *
 */
public class SearchInSortedRotatedArray {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] input = {68, 73, 75, 83, 90, 95, 11, 15, 19, 25, 29, 50,53,55,67};
		int pivIndex = findPivotIndex(input);
		System.out.printf("Pivot index:%d\tval:%d\n", pivIndex, input[pivIndex] );
		int searchVal = 29;
		int resultIndex = search(input, searchVal);
		System.out.printf("Search result index:%d\tval:%d\n", resultIndex, (resultIndex!=-1)?input[resultIndex]:-1 );
	}
	
	/**
	 * 1. Find the pivot element that splits the array into two sorted
	 * subarrays.
	 * 2. Then based on where the element falls into, perform a binary 
	 * search on the respective half to find out the same.
	 * @param arr
	 * @param a
	 * @return
	 */
	public static int search(int[] arr, int a){
		int pivIndex = findPivotIndex(arr);
		int start = 0;
		int end = arr.length-1;
		// Identify the slice to search for
		if(a>=arr[0] && a<=arr[pivIndex-1]){//Use the first half of sorted arr
			start = 0;
			end = pivIndex-1;
		}else if(a>=arr[pivIndex] && a<=arr[arr.length-1]){
			start = pivIndex;
			end = arr.length-1;
		}
		
		int resultIndex=-1;
		
		while(start<=end){
			int mid = (start+end)/2;
			if(arr[mid]==a){
				resultIndex = mid;
				break;
			}
			
			if(arr[mid] < a){
				start = mid+1;
			}else{
				end = mid-1;
			}
		}
		
		if(arr[start] == a)
			resultIndex=start;
		
		return resultIndex;
	}
	
	/**
	 * Finds the pivot element and returns the same
	 * @param arr
	 * @return
	 */
	public static int findPivotIndex(int[] arr){
		int start=0;
		int end=arr.length-1;
		int mid = (start+end)/2;
		int pivotIndex = -1;
		if(arr[start] < arr[end]){
			pivotIndex=start;
			return pivotIndex;
		}
		while(start<=end){
			if(arr[start] < arr[end]){
				pivotIndex=start;
				break;
			}
			if(arr[mid]<arr[mid-1]){
				pivotIndex=mid;
				break;
			}
			if(arr[mid] > arr[start])
				start = mid+1;
			else
				end = mid;
			mid= (start+end)/2;
		}
		
		return pivotIndex;
	}
	

}
