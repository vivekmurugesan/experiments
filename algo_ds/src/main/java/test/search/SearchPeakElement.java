package test.search;

/**
 * 
 * @author vivek
 *
 */
public class SearchPeakElement {

	public static void main(String[] args) {
		int[] input = {40,20,5,7,8,23,25,19};
		System.out.println(" peak element:" + findPeakElement(input));
	}
	
	/**
	 * Binary search approach is used to find out the peak element.
	 * 1. Start with middle element. 
	 * 2. If it is not a peak element, then proceed in the direction
	 * which is greater than the mid element.
	 * 3. Repeat this step until encountering a peak element.
	 * @param arr
	 * @return
	 */
	public static int findPeakElement(int[] arr){
		int start = 0;
		int end = arr.length-1;
		int mid = (start+end)/2;
		int result = -1;
		
		if(mid-1<0 && arr[mid] > arr[mid+1])
			return arr[mid];
		if(mid+1>=arr.length && arr[mid]>arr[mid-1])
			return arr[mid];
		while(start<=end){
			if(mid-1 > 0 && mid+1 <arr.length && arr[mid]>arr[mid-1] && arr[mid] > arr[mid+1])
				return arr[mid];
			if(mid-1>0 && arr[mid-1]>arr[mid]){
				if(mid-1==0){
					result = arr[mid-1];
					break;
				}
				end=mid-1;
			}
			else if(mid+1<arr.length && arr[mid+1]>arr[mid]){
				if(mid+1==arr.length-1){
					result = arr[mid+1];
					break;
				}
				start=mid+1;
			}
			mid = (start+end)/2;
		}
		
		return result;
	}

}
