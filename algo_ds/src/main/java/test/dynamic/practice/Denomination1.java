package test.dynamic.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class Denomination1 {

	public static void main(String[] args) {
		int sum = 80;
		int[] coins = {50,25,20,10};
		System.out.println(denomination(sum, coins));
	}
	
	public static long denomination(int sum, int[] coins){
		Map<String, Long> cache = new HashMap<>();
		long result = denomination(sum, coins, 0, cache);
		System.out.println(cache);
		
		return result;
	}

	private static long denomination(int sum, int[] coins, int i,
			Map<String, Long> cache) {
		if(sum==0)
			return 1;
		if(i >= coins.length)
			return 0;
		String key = sum+"-"+coins[i];
		if(cache.containsKey(key))
			return cache.get(key);
		
		int sumWithCoin = 0;
		long ways = 0;
		while(sumWithCoin <= sum){
			ways += denomination(sum-sumWithCoin, coins, i+1, cache);
			sumWithCoin += coins[i];
		}
		cache.put(key, ways);
		
		return ways;
	}

}
