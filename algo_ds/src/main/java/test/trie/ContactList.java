package test.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vivek
 *
 */
public class ContactList {
	
	private Node root;
	
	public ContactList(){
		root = new Node('*');
	}

	public static void main(String[] args) {
		ContactList cList = new ContactList();
		String[] names = {"abc", "aac", "bcd", "bdd", "aad", "abcd", "bcdef"};
		for(String name: names){
			cList.add(name);
		}
		
		System.out.println("For a::" + cList.findCount("a", 0, cList.root));
		System.out.println("For abc::" + cList.findCount("abc", 0, cList.root));
		System.out.println("For aa::" + cList.findCount("aa", 0, cList.root));
		System.out.println("For aad::" + cList.findCount("aad", 0, cList.root));
		System.out.println("For bcd::" + cList.findCount("bcd", 0, cList.root));
	}
	
	public void add(String s){
		add(s, 0, this.root);
	}
	
	/**
	 * 1. Recurse until the index reaches the length.
	 * 2. If the child node present then increment the size and recurse
	 * with the next node and next char.
	 * 3. Otherwise create a node add it as a child node.
	 * 4. Increment the size in either case. 
	 * @param s
	 * @param i
	 * @param root
	 */
	private void add(String s, int i, Node root) {
		if(i >= s.length())
			return;
		char c = s.charAt(i);
		Character chObj = Character.valueOf(c);
		Node node = root.getChildren().get(chObj);
		if(node == null){
			node = new Node(Character.valueOf(chObj));
			root.getChildren().put(chObj, node);
		}
		node.setSize(node.getSize()+1);
		add(s, i+1, node);
	}

	/**
	 * 1. Recurse until reaching the string size or finding a mismatch. 
	 * 2. Finding mismatch means encountering a null child node.
	 * 3. Return the size from the node.
	 * @param s
	 * @param index
	 * @param root
	 * @return
	 */
	public int findCount(String s, int index, Node root){
		if(index == s.length())
			return root.getSize();
		
		Character ch = Character.valueOf(s.charAt(index));
		Node node = root.getChildren().get(ch);
		if(node == null)
			return 0;
		return findCount(s, index+1, node);
	}


	class Node {
		private Character nodeChar; 
		private Map<Character, Node> children;
		private boolean isTerminalNode;
		private int size;
		
		public Node(Character nodeChar) {
			super();
			this.nodeChar = nodeChar;
			children = new HashMap<>();
			size=0;
		}
		public Character getNodeChar() {
			return nodeChar;
		}
		public void setNodeChar(Character nodeChar) {
			this.nodeChar = nodeChar;
		}
		public Map<Character, Node> getChildren() {
			return children;
		}
		public void setChildren(Map<Character, Node> children) {
			this.children = children;
		}
		public boolean isTerminalNode() {
			return isTerminalNode;
		}
		public void setTerminalNode(boolean isTerminalNode) {
			this.isTerminalNode = isTerminalNode;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
		
	}

}
