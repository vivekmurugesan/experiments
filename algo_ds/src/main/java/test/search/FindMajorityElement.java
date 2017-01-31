package test.search;

/**
 * 
 * @author vivek
 *
 */
public class FindMajorityElement {

	public static void main(String[] args) {
		int[] input = {1,4,7,4,4,7,4,4,9,4,3};
		Integer result = findMajorityElement(input);
		System.out.println("Majority element is:"+result);
	}
	
	/**
	 * Majority element is an element that occurs at least n/2 number of times.
	 * The algorithm involves two passes,
	 * Pass1: Finds a candidate for majority element.
	 * 	1. Keep a candidate element and count. Initialize them to
	 *  null and zero respectively.
	 *  2. If the count is 0, then set the current element as candidate and
	 *  count as 1.
	 *  3. If the count is >0 and,
	 *  	i)If the current element is same as candidate then increment 
	 *  the count
	 *  	ii)If the current element is different from candidate then 
	 *  decrement the count
	 * Pass2: At the end of the pass 1 run pass 2.
	 * If the count>0 and candidate is not null, then count the occurrences 
	 * of the candidate if it is >n/2 then return it as the majority. And
	 * null otherwise. 
	 *  
	 * @return
	 */
	public static Integer findMajorityElement(int[] input){
		// Pass 1
		Integer candidate = null;
		int count = 0;
		for(int i=0;i<input.length;i++){
			if(count == 0){
				candidate = input[i];
				count++;
			}else if(candidate == input[i])
				count++;
			else if(candidate != input[i])
				count--;
		}
		
		System.out.println(candidate+"::" + count);
		
		//Pass 2
		int actualCount = 0;
		if(count > 0 && candidate != null){
			for(int i=0;i<input.length;i++){
				if(input[i] == candidate)
					actualCount++;
			}
		}
		
		System.out.println("Actual count::" + actualCount);
		Integer result = (actualCount>input.length/2)?candidate:null;
		return result;
	}
}
