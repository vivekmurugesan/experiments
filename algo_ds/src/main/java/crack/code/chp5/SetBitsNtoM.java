package crack.code.chp5;

public class SetBitsNtoM {

	public static void main(String[] args) {
	
		long n = 11;
		printBin(n);
		System.out.println();
		
		n = 2048;
		long m = 21;
		
		n = setBits(n, m, 2, 6);
		
		printBin(n);
		System.out.println();
		printBin(m);
		
	}
	
	public static long setBits(long n, long m, int i, int j) {
		
		int k=0; // to iterate through the bits of m
		while(i<=j) {
			// To check if kth bit is set or reset in m
			long maskM = (long) Math.pow(2, k);
			long maskN = (long) Math.pow(2, i);
			if((m&maskM) == maskM) { // bit is set
				n = n | maskN; // setting the ith bit in n
			}else {
				// to reset 1--> 0  & 0-->0
				if((n&maskN) == maskN)
					n -= maskN;
			}
			i++;
			k++;
		}
		
		return n;
	}
	
	public static void printBin(long n) {
		if(n<=0)
			return;
		long mod = n % 2;
		n /= 2;
		printBin(n);
		System.out.printf("%d ", mod);
	}

}
