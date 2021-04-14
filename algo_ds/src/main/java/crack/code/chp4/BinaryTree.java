package crack.code.chp4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryTree {

	private Node root;
	
	public static void main(String[] args) {
		
		int[] input = {5,4,6,3,2,1,8,7,9,10};
		BinaryTree bt = new BinaryTree();
		bt.insert(input);
		bt.inOrder();
		
		int[] input1 = {5,4,6,3,2,1,8,7,9,10};
		BinaryTree bt1 = new BinaryTree();
		
		bt1.insertSorted(input1);
		bt1.inOrder();
		bt1.preOrder();
		bt1.postOrder();
		bt1.getDepth();
		
		bt.lotHelper();
		bt1.lotHelper();
		
		bt1.mirror(bt1.root);
		bt1.lotHelper();
		
		bt1.mirror(bt1.root);
		bt1.lotHelper();
		
		bt1.bstPropCheck();
		
		System.out.println("mirrored... ");
		bt1.mirror(bt1.root);
		
		bt1.bstPropCheck();
	}
	
	public BinaryTree() {
		this.root = null;
	}
	
	public void insertSorted(int[] data) {
		Arrays.parallelSort(data);
		this.root = insertSorted(data, 0, data.length-1);
	}
	
	public Node insertSorted(int[] data, int lo, int high) {
		if(lo>high)
			return null;
		
		int mid = (lo+high)/2;
		
		//if(mid<0 || mid >= data.length)
		//	return null;
		
		Node root = new Node(data[mid]);
		root.setLeft(insertSorted(data, lo, mid-1));
		root.setRight(insertSorted(data, mid+1, high));
		
		return root;
	}
	
	public void insert(int[] data) {
		for(int x : data) {
			this.root = insert(root, x);
		}
	}
	
	public Node insert(Node root, int data) {
		
		if(root == null)
			return new Node(data);
		
		if(data <= root.getData())
			root.setLeft(insert(root.getLeft(), data));
		else
			root.setRight(insert(root.getRight(), data));
		
		return root;
	}
	
	public void inOrder() {
		System.out.println("In order");
		inOrder(this.root);
		System.out.println();
	}
	
	/**
	 * Left, Root, Right
	 * @param root
	 */
	public void inOrder(Node root) {
		if(root == null)
			return;
		inOrder(root.getLeft());
		System.out.printf("%d\t", root.getData());
		inOrder(root.getRight());
	}
	
	public void preOrder() {
		System.out.println("Pre order");
		preOrder(this.root);
		System.out.println();
	}
	
	/**
	 * Root, Left, Right
	 * @param root
	 */
	public void preOrder(Node root) {
		if(root == null)
			return;
		
		System.out.printf("%d\t", root.getData());
		preOrder(root.getLeft());
		preOrder(root.getRight());
	}
	
	public void postOrder() {
		System.out.println("Post order");
		postOrder(this.root);
		System.out.println();
	}
	
	/**
	 * Left, Right, Root
	 * @param root
	 */
	public void postOrder(Node root) {
		if(root == null)
			return;
		
		postOrder(root.getLeft());
		postOrder(root.getRight());
		System.out.printf("%d\t", root.getData());
	}
	
	public void getDepth() {
		System.out.println("Depth..::" + getDepth(this.root));
	}
	
	public int getDepth(Node root) {
		if(root == null)
			return 0;
		
		int leftD = getDepth(root.getLeft());
		int rightD = getDepth(root.getRight());
		
		return Math.max(leftD, rightD)+1;
	}
	
	public void lotHelper() {
		List<List<Node>> levelNodes = new ArrayList<>();
		
		lot(root, 0, levelNodes);
		
		System.out.println("Lot...");
		for(List<Node> levelList : levelNodes) {
			for(Node x : levelList)
				System.out.printf("%d\t", x.getData());
			System.out.println();
		}
		
		System.out.println("LOT end");
	}
	
	public void lot(Node root, int level, List<List<Node>> levelNodes) {
		if(root == null)
			return;
		
		if(level >= levelNodes.size()) {
			levelNodes.add(new ArrayList<>());
		}
		
		levelNodes.get(level).add(root);
		
		lot(root.getLeft(), level+1, levelNodes);
		lot(root.getRight(), level+1, levelNodes);
	}
	
	public void mirror(Node root) {
		if(root == null)
			return;
		
		// swap
		Node t = root.getLeft();
		root.setLeft(root.getRight());
		root.setRight(t);
		
		mirror(root.getLeft());
		mirror(root.getRight());
	}
	
	public boolean bstPropCheck() {
		boolean result = bstPropCheck(this.root, null, null);
		
		System.out.println(".. bst prop check::" + result);
		
		return result;
	}
	
	public boolean bstPropCheck(Node root, Integer min, Integer max) {
		if(root == null)
			return true;
		
		if(min != null && root.getData() < min)
			return false;
		
		if(max != null && root.getData() > max)
			return true;
		
		return bstPropCheck(root.getLeft(), min, root.getData()) &&
				bstPropCheck(root.getRight(), root.getData(), max);
	}

}
