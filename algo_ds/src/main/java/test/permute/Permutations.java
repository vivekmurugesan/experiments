package test.permute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author vivek
 *
 */
public class Permutations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Permutations perm = new Permutations();
		List<String> result = 
				perm.permutations("abc");
		
		System.out.printf("Number of perms generated:%d\n", result.size());
		result.forEach(System.out::println);
	}
	
	/**
	 * For each char c,
	 * 	1. Strip it off from the string.
	 * 	2. Try adding it to all n-1 positions with remaining chars.
	 *  	test
	 *  	t --> est ==> {test, etst, estt, estt}
	 *  					{}
	 * @param test
	 * @return
	 */
	public List<String> permutations(String test){
		char[] chars = test.toCharArray();
		int n=chars.length;
		
		List<String> result = new ArrayList<>();
		//result.add(test);
		List<Character> buf = new LinkedList<>();
		for(char c : chars) buf.add(c);
		
		for(int i=0;i<n;i++){
			char c = chars[i];
			List<Character> bufWithoutC = new LinkedList<>(buf);
			bufWithoutC.remove(i);
			System.out.println("Trimmed:Buf:" + bufWithoutC);
			// Position to add char c to
			for(int j=i;j<n;j++){
				String perm = insertCharAt(bufWithoutC,c,j);
				result.add(perm);
				//System.out.println(perm);
			}
		}
		
		return result;
	}
	
	public String insertCharAt(List<Character> buf, char c, int pos){
		List<Character> bufLocal = new LinkedList<>(buf);
		bufLocal.add(pos, c);
		StringBuilder builder = new StringBuilder();
		bufLocal.forEach(x -> builder.append(x.charValue()));
		return builder.toString();
	}

}
