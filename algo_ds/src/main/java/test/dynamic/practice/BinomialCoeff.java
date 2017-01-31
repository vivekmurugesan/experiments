package test.dynamic.practice;

/**
 * 
 * @author vivek
 *
 */
public class BinomialCoeff {

	public static void main(String[] args) {
		System.out.println("Pascal triangle");
		
		for(int i=0;i<=10;i++){
			for(int k=0;k<=i;k++){
				System.out.printf("%d\t",binCoef(i,k));
			}
			System.out.println();
		}
		
		System.out.println("Pascal triangle DP");
		
		for(int i=0;i<=10;i++){
			for(int k=0;k<=i;k++){
				System.out.printf("%d\t",binCoefDp(i,k));
			}
			System.out.println();
		}
	}
	
	/**
	 * f(0,0) = 1
	 * f(1,0) = 1
	 * f(i,i) = 1
	 * f(i,k | k<i) = f(i-1,k)+f(i-1,k-1)
	 * @param i
	 * @param k
	 * @return
	 */
	public static int binCoef(int i, int k){
		if(i==0 || k==0)
			return 1;
		if(i==k)
			return 1;
		return binCoef(i-1,k)+binCoef(i-1,k-1);
	}
	
	/**
	 * 
	 * @param i
	 * @param k
	 * @return
	 */
	public static int binCoefDp(int i, int k){
		int[][] cache = new int[i+1][i+1];
		
		for(int j=0;j<=i;j++){
			cache[j][0]=1;cache[j][j]=1;
		}
		
		for(int j=2;j<=i;j++){
			for(int x=1;x<j;x++){
				cache[j][x] = cache[j-1][x] + cache[j-1][x-1];
			}
		}
		
		return cache[i][k];	
	}

}
