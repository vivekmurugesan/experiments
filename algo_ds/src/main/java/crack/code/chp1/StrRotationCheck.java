package crack.code.chp1;

public class StrRotationCheck {

	public static void main(String[] args) {
		
		System.out.printf("Result: %s \t %s :: %s \n", "car", "arc", strRotateChk("car", "arc"));
		
		System.out.printf("Result: %s \t %s :: %s \n", "waterbottle", "erbottlewat", 
				strRotateChk("waterbottle", "erbottlewat"));
	}
	
	public static boolean strRotateChk(String input1, String input2) {
		StringBuilder builder = new StringBuilder();
		builder.append(input2).append(input2);
		
		String concat = builder.toString();
		
		return concat.indexOf(input1) >= 0;
	}

}
