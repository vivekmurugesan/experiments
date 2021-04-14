package crack.code.chp5;

public class BitCount {

	public static void main(String[] args) {
		
		int count = biCountRequired(14,31);
		System.out.println(count);
	}
	
	public static int biCountRequired(int m, int n) {
		
		int i = (int)Math.floor(log2(m));
		int j = (int)Math.floor(log2(n));
		
		System.out.println(i + "::" + j);
		
		return j-i;
	}
	
	public static double log2(int n) {
		double result = Math.log(n) / Math.log(2);
		
		return result;
	}

}
