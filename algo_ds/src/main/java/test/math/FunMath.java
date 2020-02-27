package test.math;

import com.sun.tools.javac.util.Assert;

/**
 * 
 * @author vivek
 *
 */
public class FunMath {

	public static void main(String[] args) {
		
		
		Assert.check(wholeNumberCheck(123));
		Assert.check(!wholeNumberCheck(123.45));
		
		Assert.check(equalsCheck(123.00176, 123.00121, 3));
		Assert.check(!equalsCheck(123.00176, 123.00181, 4));
		
		Assert.check(countDigits(123) == 3);
		Assert.check(countDigits(12345) == 5);
		Assert.check(countDigits(100000) == 6);
		//Assert.check(countDigits(0) == 1);
		
		Assert.check(digitCount(123) == 3);
		Assert.check(digitCount(12345) == 5);
		Assert.check(digitCount(100000) == 6);
		
		Assert.check(log2(32) == 5);
		Assert.check(log2(128) == 7);
		Assert.check(log2(1024) == 10);
		
		//System.out.println("resetting msb::" + resetMsb(15));
		Assert.check(resetMsb(15) == 7);
		Assert.check(resetMsb(18) == 2);
		Assert.check(resetMsb(37) == 5);
		
		Assert.check(resetMsb2(15) == 7);
		Assert.check(resetMsb2(18) == 2);
		Assert.check(resetMsb2(37) == 5);
		
		System.out.println("All the outcomes where as expected.!!");
		
		intBitCount();
		intBitCount2();
		
		longBitCount();
		longBitCount2();
		
		System.out.println(31 << 1);

		System.out.println(31 >> 1);
	}
	
	public static boolean wholeNumberCheck(double n) {
		return Math.floor(n) == n;
	}
	
	public static boolean equalsCheck(double x, double y, int precision) {
		return Math.floor(x * Math.pow(10, precision)) == Math.floor(y * Math.pow(10, precision));
	}
	
	public static int countDigits(int n) {
		int result = (int)(Math.floor(Math.log10(n)) + 1);
		//System.out.printf("%d\t%d\t%f\n", n, result, Math.log10(n));
		
		return result;
	}
	
	public static double log2(double n) {
		//System.out.println(Math.log10(n) / Math.log10(2));
		return Math.log10(n) / Math.log10(2);
	}
	
	public static int resetMsb(int n) {
		return (int)(n & ((int)(Math.pow(2, Math.floor(log2(n)))-1)));
	}
	
	public static int resetMsb2(int n) {
		return (int)(n - (int)(Math.pow(2, Math.floor(log2(n)))));
	}
	
	public static void intBitCount() {
		int n = 1;
		int count = 0;
		while(n > 0) {
			count++;
			n = n << 1;
		}
		
		System.out.println("Bit count for int:: " + count);
	}
	
	public static void intBitCount2() {
		System.out.println(Math.floor(log2(Integer.MAX_VALUE))+1);
	}
	
	public static void longBitCount() {
		long n = 1;
		int count = 0;
		while(n > 0) {
			count++;
			n = n << 1;
		}
		
		System.out.println("Bit count for int:: " + count);
	}
	
	public static void longBitCount2() {
		System.out.println(Math.floor(log2(Long.MAX_VALUE))+1);
	}
	
	public static int digitCount(int n) {
		int input = n;
		int digCount = 0;
		while(n>0) {
			n = n/10;
			digCount++;
		}
		System.out.printf("DigCount:: %d\t%d\n", input, digCount);
		return digCount;
	}

}
