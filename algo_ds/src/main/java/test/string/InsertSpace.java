package test.string;

/**
 * 
 * @author vivek
 *
 */
public class InsertSpace {

	public static void main(String[] args) {
		printSpace1("wlrb");
	}
	
	static void printSpace1(String str) {
        // Your code here
        if (str != null && str.length() > 0) {
            printSpace1(str, 1, String.valueOf(str.charAt(0)));
        }

    }

    static void printSpace1(String str, int i, String spaceString) {
        if (i >= str.length()) {
            System.out.print(spaceString.concat("$"));
            return;
        }

        printSpace1(str, i + 1, spaceString+str.charAt(i));
        printSpace1(str, i + 1, spaceString.concat(" ") + str.charAt(i));

    }

	
	static void printSpace(String str)
	    {
	        int n = str.length();
	        for(int i=0;i<n;i++){
	            if(i==0)
	                System.out.print(str+"$");
	            else{
	                for(int start=1;start<n;start++){
	                    // Form all the indices from start of count i
	                	if(n-start<i)
	                		break;
	                    int[] indices = null;
	                    int k=start;
	                    if(i>1){
	                    	indices = new int[i-1];
	                    	for(int j=1;j<i;j++){
	                    		indices[j-1]=k+j;
	                    		k+=j;
	                    	}
	                    }
	                    System.out.print(formatStr(str,start,indices)+"$");
	                }
	            }
	        }
	    }
	    
	    static String formatStr(String str, int start, int[] spaceIndices){
	        char[] chars = str.toCharArray();
	        int spaceCount = (spaceIndices != null)? spaceIndices.length:0;
	        char[] newChars = new char[chars.length+spaceCount+1];
	        for(int i=0,j=0,k=0;i<chars.length;i++,j++){
	            if(i==start || (spaceIndices != null && 
	            		k<spaceIndices.length && i==spaceIndices[k])){
	                newChars[j]=' ';j++; // to accomodate the space char
	                newChars[j]=chars[i];
	                k++; // To look for next char index;
	            }else{
	                newChars[j]=chars[i];
	            }
	        }
	        
	        return new String(newChars);
	    }

}
