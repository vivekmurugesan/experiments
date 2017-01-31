package test.permute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author vivek
 *
 */
public class PermuteStrDyn {

	public static void main(String[] args) {
		
		PermuteStrDyn permuteUtil = new PermuteStrDyn();
		String test = "abcdefg";
		List<String> perms = permuteUtil.permuteStr(test);
		System.out.printf("Number of perms generated for:%s, is:%d\n",test, perms.size());
		perms.forEach(System.out::println);
		System.out.printf("Number of perms generated for:%s, is:%d\n",test, perms.size());
	}

	/**
	 * f(1) = {a} -> {a}
	 * f(2) = {a} + b -> {ab, ba}
	 * f(3) = {ab, ba} + c -> {abc,acb,cab,   bac,bca,cba}
	 * 1. At each iteration, compute the permutations as,
	 * 	a. Iterate through the perms generated from the previous iteration.
	 * 	b. Add the current/new char at all possible positions of all the perms
	 * generated from the previous iteration.
	 * 	c. Set the list of permutations generated as feed for the next 
	 * iteration.
	 * 2. Run this for all chars adding one char per iteration.
	 * @param test
	 * @return
	 */
	public List<String> permuteStr(String test){
		
		char[] chArr = test.toCharArray();
		StringBuilder builder = new StringBuilder();
		builder.append(chArr[0]);
		List<String> list = new ArrayList<>(); 
		list.add(builder.toString());
		for(int i=1;i<chArr.length;i++){
			list = createPerms(list, chArr[i]);
		}
		
		return list;
	}
	
	/**
	 * Given list of strings and a char c, it creates set of possible perms
	 * by adding c to all possible positions of all strings.
	 * @param list
	 * @param c
	 * @return
	 */
	public List<String> createPerms(List<String> list, char c){
		List<String> result = new ArrayList<>();
		
		for(String str: list){
			int n = str.length();
			List<Character> buf = new LinkedList<>();
			for(int i=0;i<n;i++)
				buf.add(str.charAt(i));
			for(int i=0;i<=n;i++){
				result.add(insertCharAt(buf, c, i));
			}
		}
		
		return result;
	}
	
	/**
	 * Given a buf, a char c and a position pos it creates a new string 
	 * and returns the same.
	 * @param buf
	 * @param c
	 * @param pos
	 * @return
	 */
	public String insertCharAt(List<Character> buf, char c, int pos){
		List<Character> bufLocal = new LinkedList<>(buf);
		bufLocal.add(pos, c);
		StringBuilder builder = new StringBuilder();
		bufLocal.forEach(x -> builder.append(x.charValue()));
		return builder.toString();
	}
}
