package test.sort;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * @author vivek
 *
 */
public class TreeMapSorted {
	
	
	public static void main(String[] args) {
		TreeMapSorted sorted = new TreeMapSorted();
		
		sorted.add("token3");sorted.add("token1");sorted.add("token2");
		sorted.add("token3");sorted.add("token2");
		sorted.add("token3");
		
		/*sorted.add("token3");sorted.add("token3");sorted.add("token3");
		sorted.add("token2");sorted.add("token2");
		sorted.add("token1");*/
		
		sorted.iterateTokens();
	}
	
	/*TreeMap<String, Token> set = new TreeMap<String, Token>(new Comparator<Entry<String, Token>>() {

		@Override
		public int compare(Entry<String, Token> o1, Entry<String, Token> o2) {
			Token t1 =o1.getValue();
			Token t2 =o1.getValue();
			return t1.getCount() - t2.getCount();
		}
	});*/
	
	TreeSet<Token> set = new TreeSet<>(new Comparator<Token>() {

		@Override
		public int compare(Token o1, Token o2) {
			/*if(o1.count == o2.count)
				return o2.hashCode() - o1.hashCode();*/
			return (o2.count - o1.count);
		}
	});
	
	public void add(String token){
		Token localToken = new Token(token);
		/*if(set.contains(localToken)){*/
		boolean found = false;
		for(Token cached : set){
			if(cached.equals(localToken)){
				cached.updateCount();
				found = true;
			}
		}
		/*}else*/
		if(!found){
			set.add(localToken);
			localToken.updateCount();
		}
	}
	
	public void iterateTokens(){
		for(Token token : this.set){
			System.out.println(token);
		}
	}
	
	
	class Token {
		private String tokenStr;
		private int count;
		
		public Token(String token){
			this.tokenStr = token;
			this.count = 0;
		}
		
		public void updateCount(){
			this.count += 1;
		}
		
		public int getCount(){
			return this.count;
		}
		
		public String getToken(){
			return this.tokenStr;
		}

		public String toString(){
			return this.tokenStr + "::" + this.count;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			/*result = prime * result + count;*/
			result = prime * result
					+ ((tokenStr == null) ? 0 : tokenStr.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Token other = (Token) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (tokenStr == null) {
				if (other.tokenStr != null)
					return false;
			} else if (!tokenStr.equals(other.tokenStr))
				return false;
			return true;
		}

		private TreeMapSorted getOuterType() {
			return TreeMapSorted.this;
		}
		
		
	}

}
