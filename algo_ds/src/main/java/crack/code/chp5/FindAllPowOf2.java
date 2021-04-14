package crack.code.chp5;

public class FindAllPowOf2 {

	public static void main(String[] args) {
		
		printAllPowOf2(1, 256);
	}
	
	public static void printAllPowOf2(int start, int end) {
		
		while(start<=end) {
			
			if((start & (start-1)) == 0)
				System.out.println(start);
			start++;
		}
	}

}
