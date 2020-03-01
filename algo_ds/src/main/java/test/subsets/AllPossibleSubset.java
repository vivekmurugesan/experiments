package test.subsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class AllPossibleSubset {

	private static int[] productIds = {1,2,3,4,5,6};
	
	private static int[] prices = {5,10,15,20,25,30};
	
	//private static int[] test = {1,2,4,8,16,32,64};
	
	public static void main(String[] args) {
		
		//System.out.println("..." + (5 & 3));
		
		AllPossibleSubset subsets = new AllPossibleSubset();
		subsets.computeAllSubsets(productIds, prices);
	}
	
	public void computeAllSubsets(int[] productIds, int[] prices) {
		int n = productIds.length;
		int subsetSize = (int) Math.pow(2, n);
		
		Map<Integer, List<Integer>> setCache = new HashMap<>();
		Map<Integer, Integer> setSumCache = new HashMap<>();
		
		for(int i=0;i<subsetSize;i++) {
			getSetWithIndexOptimized(productIds, prices, i, setCache, setSumCache);
			
			List<Integer> subset = setCache.get(i);
			int setSum = setSumCache.get(i);
			System.out.println("subset: " + i + "::"+ subset+ " :: sum: " + setSum 
					+ ", id::" + encode(subset) + ".. decode test::" + decode(i, n) );
		}
	}
	
	public int encode(List<Integer> subset) {
		int id = 0;
		for(int n : subset) {
			id += Math.pow(2, n-1);
		}
		return id;
	}
	
	public List<Integer> decode(int comboId, int n){
		List<Integer> productIds = new ArrayList<>();
		for(int i=0;i<n;i++) {
			int mask = (int)Math.pow(2, i);
			if((comboId & mask) == mask)
				productIds.add(i+1);
		}
		
		return productIds;
	}
	
	
	
	public void getSetWithIndex(int[] test, int index,
			Map<Integer, List<Integer>> setCache, Map<Integer, Integer> setSumCache) {
		List<Integer> subset = new ArrayList<>();
		int sum = 0;
		for(int i=0;i<test.length;i++) {
			int mask = (int) Math.pow(2, i);
			//System.out.printf("Index:%d\t Mask:%d\ti:%d\n",index, mask,i);
			if((index & mask) == mask) {
				subset.add(test[i]);
				sum += test[i];
			}
		}
		
		setCache.put(index, subset);
		setSumCache.put(index, sum);
		
	}
	
	public void getSetWithIndexOptimized(int[] productIds, int[] prices, int index,
			Map<Integer, List<Integer>> setCache, Map<Integer, Integer> setSumCache) {
		List<Integer> subset = new ArrayList<>();
		int sum = 0;
		
		if(index >= 2) {
			int mask = (int) (Math.pow(2, log2Floor(index)) - 1);
			int prev = index & mask; // resetting the Msb
			
			subset.addAll(setCache.get(prev));
			
			int newIndex = log2Floor(index);
			subset.add(productIds[newIndex]);
			sum = setSumCache.get(prev) + prices[newIndex];
			//System.out.println(".. hiting the cache.");
			
		}else {
			if(index == 1) {
				subset.add(productIds[0]);
				sum += prices[0];
			}
		}
		
		setCache.put(index, subset);
		setSumCache.put(index, sum);
		
	}
	
	static int log2Floor(int n) {
    	double log2 = Math.log(n) / Math.log(2);
    	int result = (int) Math.floor(log2);
    	
    	return result;
    }

}
