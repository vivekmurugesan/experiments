package crack.code.chp5;

public class PrintDecimalToBin {

	public static void main(String[] args) {
		String str = "3.75";
		decimalToBin(str);
		
		str = "3.55";
		decimalToBin(str);
		
		str = "5.875";
		decimalToBin(str);
		
		
		str = "35.875";
		decimalToBin(str);
	}
	
	public static void decimalToBin(String str) {
		double n = Double.parseDouble(str);
		
		int wholeN = (int) Math.floor(n); 
		double fractionN = n - wholeN;
		System.out.println(wholeN+" . " + fractionN);
		printBin(wholeN);
		System.out.print(". ");
		printFractionBin(fractionN, 1, 10);
		
	}
	
	public static void printBin(int n) {
		if(n<=0)
			return;
		int mod = n % 2;
		n = n/2;
		printBin(n);
		System.out.printf("%d ", mod);
	}
	
	public static void printFractionBin(double f, int count, int max) {
		if(f <= 0 || count>max)
			return;
		double mask = 1/(Math.pow(2, count));
		int val = (f>=mask)?1:0;
		if(f>=mask)
			f -= mask;
		printFractionBin(f, count+1, max);
		System.out.printf("%d ", val);
	}

}
