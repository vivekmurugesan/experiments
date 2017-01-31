package test.search;

/**
 * 
 * @author vivek
 *
 */
public class MinMaxSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int arr[] = {8,1,3,7,12,40,-5,100,-11};
		MinMaxSearch search = new MinMaxSearch();
		System.out.println("Condition satisfied: " + search.minSqLtMax(arr));
	}
	
	public boolean minSqLtMax(int[] arr){
		if(arr == null)
			return false;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i : arr ){
			if(i<min)
				min = i;
			if(i>max)
				max=i;
		}
		System.out.printf("Min:%d\tMax:%d\n", min, max);
		boolean result = (min*min < max);
		
		return result;
	}

}
