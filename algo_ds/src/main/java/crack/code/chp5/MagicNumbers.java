package crack.code.chp5;

public class MagicNumbers {

	public static void main(String[] args) {
		
		long n = 1;
		
		int index = 0;
		while(n>0) {
			System.out.printf("\n%d\t%d\t%d", index++, n, digitSum(n));
			n *= 2;
		}
		
		System.out.println("\n"+n);
		
		//System.out.println(digitSum(256));
	}
	
	public static long digitSum(long n) {
		if(n<10)
			return n;
		
		long digSum = 0;
		while(n>0) {
			digSum += (n %10);
			n /= 10;
		}
		
		return digitSum(digSum);
	}

}
