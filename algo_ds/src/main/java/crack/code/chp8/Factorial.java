package crack.code.chp8;

public class Factorial {

	public static void main(String[] args) {
		
		for(int i=0;i<10;i++)
			System.out.printf("Fib of:%d = %d\n", i, fib(i));
	}
	
	public static int fib(int n) {
		if(n==0 || n==1)
			return 1;
		
		return n * fib(n-1);
	}

}
