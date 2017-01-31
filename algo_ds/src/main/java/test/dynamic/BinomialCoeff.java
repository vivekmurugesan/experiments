package test.dynamic;

/**
 * 
 * @author vivek
 *
 */
public class BinomialCoeff {
	
	long binomCoeff(int n, int m){
		long[][] bc = new long[n+1][n+1];
		for(int i=0;i<=n;i++)bc[i][0]=1;
		for(int i=0;i<=n;i++)bc[i][i]=1;
		
		for(int i=1;i<=n;i++)
			for(int j=1;j<i;j++)
				bc[i][j] = bc[i-1][j-1]+bc[i-1][j];
		
		for(int i=0;i<=n;i++)
			System.out.printf("\t%d",i);
		System.out.println();
		for(int i=0;i<=n;i++){
			for(int j=0;j<=i;j++){
				if(j==0)
					System.out.printf("%d\t", i);
				System.out.printf("%d\t",bc[i][j]);
			}
			System.out.println();
		}
		
		return bc[n][m];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinomialCoeff bc = new BinomialCoeff();
		int n=7,m=4;
		System.out.printf("BC of:%dc%d is:%d", n,m,bc.binomCoeff(n, m));
	}

}
