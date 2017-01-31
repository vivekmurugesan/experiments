package test.dynamic.practice;

/**
 * 
 * @author vivek
 *
 */
public class FibCached {
	
	public static int UNKNOWN = -1;
	public static void main(String[] args) {
		for(int i=0;i<=10;i++)
			System.out.printf("%d\t", fib(i));
		
		System.out.println();
		System.out.println("Fib cached");
		for(int i=0;i<=10;i++)
			System.out.printf("%d\t", fibCached1(i));
		
		System.out.println();
		System.out.println("Fib cached2");
		for(int i=0;i<=10;i++)
			System.out.printf("%d\t", fibCached2(i));
	}
	
	public static int fib(int n){
		if(n==0 || n==1)
			return n;
		return fib(n-1)+fib(n-2);
	}
	
	public static int fibCached1(int n){
		if(n==0)
			return 0;
		int[] cache = new int[n+1];
		cache[0] = 0;
		cache[1] = 1;
		
		for(int i=2;i<=n;i++){
			cache[i] = UNKNOWN; 
		}
		
		for(int i=2;i<=n;i++){
			if(cache[i] == UNKNOWN)
				cache[i] = cache[i-1] + cache[i-2];
		}
		
		return cache[n];
	}
	
	public static int fibCached2(int n){
		int prev1 = 0;
		int prev2 = 1;
		if(n==0 || n==1) return n;
		int result=0;
		for(int i=2;i<=n;i++){
			result = prev1+prev2;
			prev1=prev2;
			prev2=result;
		}
		
		return result;
	}

}
