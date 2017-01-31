package test.dynamic;

import test.dynamic.EditDistance.Operation;

/**
 * 
 * @author vivek
 *
 */
public class EditDistance1 {
	
	private static final int MATCH=0;
	private static final int INSERT=1;
	private static final int DELETE=2;
	
	private int match(char a, char b){
		if(a==b)
			return 0;
		return 1;
	}
	
	private int indel(char c){
		return 1;
	}
	
	public int stringCompare(char[] s, char[] t, int i, int j){
		int[] cost = new int[3];
		if(i == 0) return j*indel(' ');
		if(j == 0) return i*indel(' ');
		
		cost[MATCH] = stringCompare(s, t, i-1,j-1)+match(s[i],t[j]);
		cost[INSERT] = stringCompare(s, t, i, j-1) + indel(t[j]);
		cost[DELETE] = stringCompare(s, t, i-1, j) + indel(s[i]);
		
		int lowestCost = cost[MATCH];
		for(int k=1;k<3;k++){
			if(cost[k] < lowestCost)
				lowestCost = cost[k];
		}
		
		return lowestCost;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EditDistance1 editD = new EditDistance1();
		String s = " thou shalt not";
		String t = " you should not";
		
		/*System.out.printf("Edit distance for: %s , %s is:%d\n", s, t, editD.stringCompare(s.toCharArray(), t.toCharArray(), 
				s.length()-1, t.length()-1) );*/
		
		System.out.printf("Edit distance for: %s , %s is:%d\n",s,t,editD.stringCompareDyn(s, t));

	}
	
	private void rowInit(Entry1[][] table, int i){
		table[0][i] = new Entry1(i);
	}
	
	private void colInit(Entry1[][] table, int i){
		table[i][0] = new Entry1(i);
	}
	
	public int stringCompareDyn(String s, String t){
		int[] opt = new int[3];
		int sLen = s.length();
		int tLen = t.length();
		int maxLen = (sLen>=tLen)?sLen:tLen;
		
		Entry1[][] table = new Entry1[maxLen][maxLen];
		
		for(int i=0;i<maxLen;i++){
			rowInit(table, i);
			colInit(table, i);
		}
		
		for(int i=1;i<sLen;i++){
			for(int j=1;j<tLen;j++){
				opt[MATCH] = table[i-1][j-1].getCost()+match(s.charAt(i), t.charAt(j));
				opt[INSERT] = table[i][j-1].getCost()+indel(t.charAt(j));
				opt[DELETE] = table[i-1][j].getCost()+indel(s.charAt(i));
				
				table[i][j] = new Entry1(opt[MATCH]);
				table[i][j].setParent(Operation.MATCH);
				
				if(opt[INSERT] < table[i][j].getCost()){
					table[i][j].setCost(opt[INSERT]);
					table[i][j].setParent(Operation.INSERT);
				}
				if(opt[DELETE] < table[i][j].getCost()){
					table[i][j].setCost(opt[DELETE]);
					table[i][j].setParent(Operation.DELETE);
				}
			}
		}
		
		return table[sLen-1][tLen-1].getCost();
	}

	
	public enum Operation { MATCH, INSERT, DELETE };
	
	class Entry1 {
		int cost;
		Operation parent;
		public Entry1(int cost){
			this.cost = cost;
			parent = null;
		}
		public void setCost(int cost){
			this.cost = cost;
		}
		public int getCost(){
			return this.cost;
		}
		public void setParent(Operation oper){
			this.parent = oper;
		}
		public Operation getParent(){
			return this.parent;
		}
		
	}
}
