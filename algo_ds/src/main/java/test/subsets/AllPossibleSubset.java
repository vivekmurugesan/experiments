package test.subsets;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vivek
 *
 */
public class AllPossibleSubset {

	private static int[] test = {1,2,3,4};
	
	public static void main(String[] args) {
		
		AllPossibleSubset subsets = new AllPossibleSubset();
		subsets.computeAllSubsets(test);
	}
	
	public void computeAllSubsets(int[] test) {
		int n = test.length;
		int subsetSize = (int) Math.pow(2, n);
		
		for(int i=0;i<subsetSize;i++) {
			List<Integer> subset = getSetWithIndex(test, i);
			System.out.println("subset: " + i + "::"+ subset);
		}
	}
	
	public List<Integer> getSetWithIndex(int[] test, int index) {
		List<Integer> subset = new ArrayList<>();
		for(int i=0;i<test.length;i++) {
			int mask = (int) Math.pow(2, i);
			if((index & mask) == mask)
				subset.add(test[i]);
		}
		
		return subset;
	}

}
