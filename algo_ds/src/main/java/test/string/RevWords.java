package test.string;

/**
 * 
 * @author vivek
 *
 */
public class RevWords {

	public static void main(String[] args) {
		String line = "Chennai is Beautiful";
		System.out.println(revWords(line));
		System.out.println(revWordsInPlace(line));
	}
	
	public static String revWords(String line){
		String[] words = line.split(" ");

		// popping out words in reverse order
		StringBuilder builder = null;


		for(int i=words.length-1;i>=0;i--){
		    if(builder == null){
		        builder = new StringBuilder();
		        builder.append(words[i]);
		    }else
		        builder.append(" ").append(words[i]);
		}
		return (builder != null)?builder.toString():null;

	}
	
	public static String revWordsInPlace(String line){
		char[] chars = line.toCharArray();
		// Reversing in one pass.
		for(int i=0,j=chars.length-1;i<j;i++,j--)
			swap(chars,i,j);
		// Reversing each word in the next pass.
		for(int i=0,j=0;j<chars.length;j++){
			// Looking out for space char or end of string. 
			if(chars[j]==' ' || j==chars.length-1){
				int k=(j==chars.length-1)?j:j-1;
				// Reversing the word
				for(;k>i;k--,i++)
					swap(chars,i,k);
				i=j+1;
			}
		}
		
		return new String(chars);
	}
	
	private static void swap(char[] arr, int i, int j){
		char t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

}
