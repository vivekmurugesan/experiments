package test.dynamic;

/**
 * 
 * @author vivek
 *
 */
public class FibCached {
	
	private static final long UNKNOWN = -1;
	private long[] cache = null;
	
	public long fibCached(int n){
		if(cache[n] == UNKNOWN)
			cache[n] = fibCached(n-1)+fibCached(n-2);
		return cache[n];
	}

	public void fibCacheInit(int n){
		cache = new long[n+1];
		cache[0] = 0;
		if(n>0)
			cache[1] = 1;
		for(int i=2;i<=n;i++)
			cache[i] = UNKNOWN;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FibCached fibC = new FibCached();
		for(int i=0;i<20;i++){
			fibC.fibCacheInit(i);
			System.out.printf("%d \t", fibC.fibCached(i));
		}
	}

}
