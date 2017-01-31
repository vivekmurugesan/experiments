package test.dynamic;

public class FibDp {
	
	long fibDp(int n){
		long[] f = new long[n+1];
		f[0] = 0;
		if(n>0)
			f[1]=1;
		for(int i=2;i<=n;i++)
			f[i]=f[i-1]+f[i-2];
		
		return f[n];
			
	}
	
	long fibUltimate(int n){
		long back1=1, back2=0, next;
		if(n==0)return 0;
		for(int i=2;i<n;i++){
			next = back1+back2;
			back2=back1;
			back1=next;
		}
		return back1+back2;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FibDp fib = new FibDp();
		for(int i=0;i<20;i++){
			System.out.printf("%d \t", fib.fibUltimate(i));
		}
	}

}
