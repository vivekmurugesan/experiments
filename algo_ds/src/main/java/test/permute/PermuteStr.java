package test.permute;

/**
 * a --> {a}
 * ab ---> {ab,ba}
 * abc ---> {ab + c ---> abc,acb,cab } & {ba + c ---> bac,bca,cba}
 * @author vivek
 *
 */
public class PermuteStr {

	public static void main(String[] args) {
		String test = "abcde";
		PermuteStr permute = new PermuteStr();
		String[] result = 
				permute.permute(test.toCharArray(), test.length());
		System.out.println("Number of perms:" + result.length);
		for(String t : result)
			System.out.println(t);
	}

	/**
	 * f(0) ---> 0
	 * f("a") ---> return a
	 * f("ab") ---> return a+b,b+a
	 * @param chArr
	 * @return
	 */
	String[] permute(char[] chArr, int n){
		
		if(n < 1)
			return null;
		if(n == 1){
			String[] retVal = new String[1];
			retVal[0] = (new StringBuilder()).append(chArr[0]).toString();
			return retVal;
		}else
			return addNewChar(permute(chArr, n-1), chArr[n-1],n);
	}
	
	String[] addNewChar(String[] perms, char c, int n){
		/** Iterate through all strings and add the 
		 * new char to all possible positions */
		String[] retVal = new String[perms.length*n];
		for(int i=0,k=0;i<perms.length;i++){
			String str = perms[i];
			char[] arr = str.toCharArray();
/*			System.out.println(str+"::" + arr.length);*/
			for(int j=0;j<n;j++){
				/*
				String t = 
						str.substring(0, j).concat(cStr).concat(str.substring(j));*/
				String t = addCharAtPos(arr, c, j);
				/*System.out.println("t::"+t);*/
				retVal[k++]= t;
			}
		}
		return retVal;
	}
	
	/**
	 * Creates a char arr by inserting the char c at the relevant position.
	 * @param arr
	 * @param c
	 * @param pos
	 * @return
	 */
	String addCharAtPos(char[] arr, char c, int pos){
		char[] result = new char[arr.length+1];
		for(int i=0,k=0;k<result.length;k++){
			if(k==pos)
				result[k] = c;
			else
				result[k]=arr[i++];
		}
		return new String(result);
	}
}
