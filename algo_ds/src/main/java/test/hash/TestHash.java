package test.hash;

/**
 * 
 * @author vivek
 *
 */
public class TestHash {

	public static void main(String[] args) {
		String one = "Aa";
		String two = "BB";
		
		System.out.println(one.hashCode());
		System.out.println(two.hashCode());
		
		System.out.println(one.hashCode()==two.hashCode());

		int hash=0;
		
		System.out.println();
		for(int i=0;i<one.length();i++){
			hash = hash*31+one.charAt(i);
			System.out.printf("%c\t%d\t",one.charAt(i),hash);
		}
		System.out.println();
		
		System.out.println(hash);
		
		int hash1=0;
		
		System.out.println();
		for(int i=0;i<two.length();i++){
			hash1 = hash1*31+two.charAt(i);
			System.out.printf("%c\t%d\t",two.charAt(i),hash1);
		}
		System.out.println();
		
		int d=0;
		d += 'a';
		System.out.println(d);
		
		int d1=0;
		d1 += 'B';
		System.out.println(d1);
		
		System.out.println(hash1);
	}

}
