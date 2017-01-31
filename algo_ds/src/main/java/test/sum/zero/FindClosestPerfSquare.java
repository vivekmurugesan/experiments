package test.sum.zero;

/**
 * 
 * @author vivek
 *
 */

public class FindClosestPerfSquare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n=241;
		int i=1;
		int latestSq=1;
		for(;i*i<n;i++){
			latestSq=i*i;
		}
		int closestSq;
		if(n-latestSq > i*i - n)
			closestSq = i*i;
		else
			closestSq = latestSq;
		System.out.println(closestSq);
	}

}
