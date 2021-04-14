package crack.code.chp8;

public class Fib {

	public static void main(String[] args) {
	
		for(int i=0;i<40;i++)
			System.out.printf("Fib of: %d = %d\n", i, fib(i));
	}
	
	public static int fib(int n) {
		if(n==0)
			return 0;
		if(n==1)
			return 1;
		
		return fib(n-1)+fib(n-2);
	}

}
