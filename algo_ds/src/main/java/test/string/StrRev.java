package test.string;

/**
 * 
 * @author vivek
 *
 */
public class StrRev {
	
	public void strRev(String str, int index, int length){
		if(index >= length)
			return;
		strRev(str, index+1, length);
		System.out.printf("%c",str.charAt(index));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StrRev rev = new StrRev();
		String str = "HelloWorld";
		rev.strRev(str, 0, str.length());
	}

}
