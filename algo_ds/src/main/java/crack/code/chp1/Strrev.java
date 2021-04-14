package crack.code.chp1;

public class Strrev {

	public static void main(String[] args) {
		
		System.out.println(strRev("abcd"));
		System.out.println(strRev("abcde"));
	}
	
	public static String strRev(String input) {
		
		char[] chArr = input.toCharArray();
		
		int iter = 0;
		int revIter = chArr.length-1;
		
		while(iter<revIter) {
			char t = chArr[iter];
			chArr[iter] = chArr[revIter];
			chArr[revIter] = t;
			iter++;revIter--;
		}
		
		return new String(chArr);
	}

}
