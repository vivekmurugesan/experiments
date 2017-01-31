package test.dynamic.practice;

/**
 * 
 * @author vivek
 *
 */
public class EditDistanceDp {
	
	private static int MATCH=0;
	private static int INSERT=1;
	private static int DELETE=2;
	
	private static int options[] = new int[3];

	public static void main(String[] args) {
		
		String t = " thou shalt not";
		String s = " you should not";
		EditDistanceDp editDist = new EditDistanceDp();
		int distance = editDist.computeDist(s, t);
		System.out.printf("Edit distance for: %s , %s is:%d\n",s,t,distance);
	}
	
	public void setRow(int[][] table, int i){
		table[0][i]=i;
	}
	
	public void setCol(int[][] table, int i){
		table[i][0]=i;
	}
	
	public int match(char a, char b){
		if(a == b)
			return 0;
		return 1;
	}
	
	public int inDel(){
		return 1;
	}
	
	/**
	 * 
	 * @param t
	 * @param s
	 * @return
	 */
	public int computeDist(String t, String s){
		int sLen = t.length();
		int tLen = t.length();
		
		int maxLen = (sLen>=tLen)?sLen:tLen;
		int[][] table = new int[maxLen][maxLen];
		
		for(int i=0;i<maxLen;i++){
			setRow(table,i);
			setCol(table,i);
		}
		
		for(int i=1;i<tLen;i++){
			for(int j=1;j<sLen;j++){
				options[MATCH] = table[i-1][j-1]+match(t.charAt(i),s.charAt(i));
				options[INSERT] = table[i][j-1]+inDel();
				options[DELETE] = table[i-1][j]+inDel();
				
				table[i][j] = options[MATCH];
				if(options[INSERT] < table[i][j])
					table[i][j] = options[INSERT];
				if(options[DELETE] < table[i][j])
					table[i][j] = options[DELETE];
			}
		}
		
		System.out.println("Table");
		for(int i=0;i<maxLen;i++){
			for(int j=0;j<maxLen;j++){
				System.out.printf("%d\t", table[i][j]);
			}
			System.out.println();
		}
		
		return table[tLen-1][sLen-1];
	}

}
