package test.string;

public class RunLengthEncoding {

	public static void main(String[] args) {
		
		String input = "aaabbbbccd";
		System.out.printf(" input: %s \t encoding: %s", input, runLenEncoding(input));
	}
	
	public static String runLenEncoding(String input) {
		
		char[] chArray = input.toCharArray();
		
		char prevChar = 0;
		int counter = 0;
		
		StringBuilder sb = new StringBuilder();
		
		for(char c : chArray) {
			if(c == prevChar)
				counter++;
			else {
				if(prevChar != 0) 
					sb.append(counter).append(prevChar);
				// Resetting the counter and prevChar
				prevChar = c;
				counter = 1;
			}
		}
		
		sb.append(counter).append(prevChar);
		
		return sb.toString();
	}

}
