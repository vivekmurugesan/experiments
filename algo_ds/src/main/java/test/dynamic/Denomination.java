package test.dynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class Denomination {

	public static void main(String[] args) {
		/*int money = 75;
		int[] coins = {50, 25, 20, 10, 5};*/
		int money = 80;
		int[] coins = {50,25, 20,10};
		
		System.out.println("Number of ways:" + denomination(money, coins));
	}
	
	public static long denomination(int money, int[] coins){
		Map<String, Long> cache = new HashMap<>();
		long result = denomination(money, coins, 0, cache);
		System.out.println(cache);
		return result;
	}
	
	/**
	 * 1. For every given possibility of coin_i,
	 * 	i) Use it one or more times: and recurse with money-coin_i as target
	 * 	ii) Keep adding coin_i more times until moneyWithCoin<=money 
	 * 2. Use Map<money_coin_i, ways> as a cache to avoid duplicate computation.
	 * 
	 */
	public static long denomination(int money, int [] coins, int index,
			Map<String, Long> cache){
		if(money == 0)
			return 1;
		
		if(index >= coins.length)
			return 0;
		
		int moneyWithCoin = 0;
		long ways = 0;
		String key = money+"-"+coins[index];
		if(cache.containsKey(key))
			return cache.get(key);
		while(moneyWithCoin <= money){
			ways += denomination(money-moneyWithCoin, coins, index+1,
					cache);
			moneyWithCoin += coins[index];
		}
		
		cache.put(money+"-"+coins[index], ways);
		
		return ways;
	}

}
