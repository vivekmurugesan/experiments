package binary.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vivek
 *
 */
public class BinaryTree {
	
	private Node root;
	
	public BinaryTree(){
		this.root = null;
	}
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	
	public void construct(int[] data){
		if(data == null)
			return;
		for(int d:data){
			this.root = insert(this.root, d);
		}
	}
	
	public Node insert(Node root, int data){
		if(root == null){
			Node newNode = new Node(data);
			return newNode;
		}
		if(data <= root.getData())
			root.setLeft(insert(root.getLeft(), data));
		else if(data > root.getData())
			root.setRight(insert(root.getRight(), data));
		return root;
	}
	
	/**
	 * Left->Root->Right
	 * @param root
	 */
	public void inOrder(Node root){
		if(root == null)
			return;
		inOrder(root.getLeft());
		System.out.printf("%d\t",root.getData());
		inOrder(root.getRight());
	}
	
	/**
	 * Root->Left->Right
	 * @param root
	 */
	public void preOrder(Node root){
		if(root == null)
			return;
		System.out.printf("%d\t", root.getData());
		preOrder(root.getLeft());
		preOrder(root.getRight());
	}

	/** 
	 * Left->Right->Root
	 * @param root
	 */
	public void postOrder(Node root){
		if(root==null)
			return;
		postOrder(root.getLeft());
		postOrder(root.getRight());
		System.out.printf("%d\t", root.getData());
	}
	
	/** 
	 * f(n) = f(n.left) + f(n.right) +1
	 * f(0) = 0   ---> zero for node null
	 * f(1) = f(0) + f(0) + 1	---> for tree with 1 node.
	 * @param root
	 * @return
	 */
	public int size(Node root){
		if(root == null)
			return 0;
		return size(root.getLeft()) + 1 + size(root.getRight());
	}

	/**
	 * f(n) = max(1+f(n.left), 1+f(n.right))
	 * f(0) = 0
	 * f(1) = max(1+f(0), 1+f(0) = 1
	 * @param root
	 * @return
	 */
	public int getDeapth(Node root){
		if(root == null)
			return 0;
		int leftD = 1+getDeapth(root.getLeft());
		int rightD = 1+getDeapth(root.getRight());
		int maxD = (leftD >= rightD)? leftD:rightD;
		return maxD;
	}
	
	/**
	 * f(0) = true
	 * f(n) = f(n.left) && f(n.right)
	 * @param root
	 * @return
	 */
	public boolean isBalanced(Node root){
		if(root == null) 
			return true;
		boolean balanced = Math.abs(getDeapth(root.getLeft())-getDeapth(root.getRight())) <= 1;
		if(!balanced)
			return false;
		else
			balanced = isBalanced(root.getLeft()) && isBalanced(root.getRight());
		return balanced;
	}
	
	/**
	 * f(n) = f(n.left) + f(n.right)
	 * f(1) = 1  --> where f(1) equals to node with no child.
	 * f(0) = 0, for null
	 * @param root
	 * @return
	 */
	public int getLeafCount(Node root){
		if(root == null)
			return 0;
		if(root.getLeft() == null && root.getRight() == null)
			return 1;
		int leafCount = getLeafCount(root.getLeft()) + getLeafCount(root.getRight());
		return leafCount;
	}
	
	/**
	 * 	Iterate to the left most node and get the value.
	 * @param root
	 */
	public int getMin(Node root){
		while(root.getLeft() != null){
			root = root.getLeft();
		}
		return root.getData();
	}
	
	public int getMax(Node root){
		while(root.getRight() != null)
			root = root.getRight();
		
		return root.getData();
	}
	
	/**
	 * 
	 * @param root
	 * @param sum
	 * @return
	 */
	public boolean checkPathSum(Node root, int sum){
		if(root == null)
			return (sum==0);
		sum -= root.getData();
		return (checkPathSum(root.getLeft(), sum) || 
				checkPathSum(root.getRight(), sum));
	}
	
	public void printPaths(Node root, List<Integer> path, int pathLen){
		if(root == null)
			return;
		path.add(pathLen,root.getData());
		pathLen++;
		/** Its a leaf print the path. */
		if(root.getLeft() == null && root.getRight() == null)
			System.out.println("Path-->"+path.subList(0, pathLen) );
		printPaths(root.getLeft(), path, pathLen);
		printPaths(root.getRight(), path, pathLen);
	}
	
	/**
	 * f(n) = f(n.left) and f(n.right)
	 * f(1) = f(swap(left,right))
	 * Do it like a post order.. left->right->root
	 * 	and swap left and right ptrs when at root.
	 * @param root
	 */
	public void mirror(Node root){
		if(root == null)
			return;
		mirror(root.getLeft());
		mirror(root.getRight());
		Node tmp=null;
		/** Swapping */
		tmp=root.getLeft();
		root.setLeft(root.getRight());
		root.setRight(tmp);
	}
	
	/**
	 * Creates a balanced binary with a given sorted array. 
	 * Use an approach like merge sort by splitting the array by half
	 * every time.
	 * 1. If start > end the recursion with return null
	 * 2. Compute the mid point by: mid = (start + end)/2
	 * 3. Create root node with arr[mid] as the data.
	 * 4. Recurse with calls,
	 * 		root.left = createBalTree(arr, start, mid)
	 * 		root.right = createBalTree(arr, mid, end)  
	 * @param arr
	 * @param start
	 * @param end
	 */
	public Node createBalTree(int[] arr, int start, int end){
		if(start>end)
			return null;
		int mid = (start+end)/2;
		if(mid>=arr.length || mid < 0)
			return null;
		Node root = new Node(arr[mid]);
		root.setLeft(createBalTree(arr, start, mid-1));
		root.setRight(createBalTree(arr, mid+1, end));
		
		return root;
	}
	
	public void constructBalTree(int[] arr){
		this.root = createBalTree(arr, 0, arr.length);
	}
	
	/**
	 * f(n) = abs(f(n.left)-f(n.right)), if(f(n) > 1) return Integer.MAX_VALUE
	 * f(0) = 0
	 * @param root
	 * @return
	 */
	public int isBalancedInPlace(Node root){
		if(root == null)
			return -1;
		
		int leftDepth = isBalancedInPlace(root.getLeft());
		int rightDepth = isBalancedInPlace(root.getRight());
		
		if(leftDepth <= Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		if(rightDepth <= Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		
		int depthDiff = Math.abs(leftDepth-rightDepth);
		
		if(depthDiff>1)
			return Integer.MIN_VALUE;
		else
			return Math.max(leftDepth, rightDepth)+1;
	}
	
	/**
	 * InOrder successor can be seen as the left most child of the 
	 * right subtree of the node. 
	 * 1) If right subtree of node is not NULL, then succ lies in right subtree. 
	 * Do following.
	 * Go to right subtree and return the node with minimum key value in right subtree.
	 * 2) If right sbtree of node is NULL, then succ is one of the ancestors. Do following.
	 * Travel up using the parent pointer until you see a node which is 
	 * left child of it’s parent. 
	 * 				20
	 * 			   /  \
	 * 			  8	  22
	 * 			 / \
	 * 			4   12
	 * 			   /  \
	 * 			  11  14
	 * For example for 4 and 11, it would be 8 and 12 respectively, as the nodes themselves 
	 * qualify the condition (i.e. a node which is the left child of its parent). 
	 * Whereas for 14 it is 20, as 14 and 12 doesen't satisfy the condition 
	 * and it needs to be traversed until 8 to get the condition to be satisfied.
	 * 
	 * For node 8 which has a right subtree, it falls into the case 1 and 11 ends up as the 
	 * in order successor.	
	 * The parent of such a node is the succ.
	 * @param node
	 * @return
	 */
	public Node inOrdSuccessor(Node node){
		if(node == null)
			return null;
		if(node.getRight() != null)
			return leftMostChild(node);
		
		Node q = node;
		Node x = node;
		//Node x = q.getParent();
		
		while(x != null && x.getLeft() != q){
			q = x;
			//x = x.getParent();
		}
		
		return x;
	}
	
	public Node leftMostChild(Node node){
		while(node.getLeft() != null){
			node = node.getLeft();
		}
		
		return node;
	}
	
	public boolean checkBST(Node root){
		return checkBSTProp(root, null, null);
	}
	
	/**
	 * left < root <= right
	 * 1. From root when we traverse towards right, there should be no value
	 * that is less than the current node.
	 * 2. When we traverse towards left, there should be no value that is 
	 * greater than the current node.
	 * @param root
	 * @param min
	 * @param max
	 * @return
	 */
	public boolean checkBSTProp(Node root, Integer min, Integer max){
		if(root == null)
			return true;
		/*if(min == null && max == null)
			return true;*/
		
		if(min != null && root.getData() <= min)
			return false;
		if(max != null && root.getData() > max)
			return false;
		
		boolean leftBST = checkBSTProp(root.getLeft(), min, root.getData());
		boolean rightBST = checkBSTProp(root.getRight(), root.getData(), max);
		
		return leftBST && rightBST;
	}
	
	/**
	 * First common ancestor
	 * 1. Start with root
	 * 2. Iterate until you end up seeing two nodes on the different sides 
	 * (i.e. one on the left subtree and the other one on right subtree)
	 * 3. Check which side of the subtrees the two nodes are.
	 * 4. Return the node at which their side differs.
	 *  
	 * 				 20
	 * 			   /    \
	 * 			  8	     \
	 * 			 / \      \
	 * 			4   12     22
	 * 			   /  \   /  \
	 * 			  11  14  21  27
	 * @return
	 */
	public Node firstCommonAncestor(Node root, Node n1, Node n2){
		Node t = root;
		boolean n1FoundOnLeft = searchNode(t.getLeft(), n1);
		boolean n1FoundOnRight = false;
		if(!n1FoundOnLeft)
			n1FoundOnRight = searchNode(t.getRight(), n1);
		
		boolean n2FoundOnLeft = searchNode(t.getLeft(), n2);
		boolean n2FoundOnRight = false;
		if(!n2FoundOnLeft)
			n2FoundOnRight = searchNode(t.getRight(), n2);
		
		// One of the nodes not found at all 
		if((!n1FoundOnLeft&&!n1FoundOnRight) || (!n2FoundOnLeft&&!n2FoundOnRight))
			return null;
		
		// Loop until both found on the same side.
		while((n1FoundOnLeft && n2FoundOnLeft) || 
				(n1FoundOnRight && n2FoundOnRight)){
			if(t.equals(n1) || t.equals(n2))
				break; // Break and return t
			if(n1FoundOnLeft){
				if(n1.equals(t.getLeft()) || n2.equals(t.getLeft()))
					break;
				t = t.getLeft();
			}else{
				if(n1.equals(t.getRight()) || n2.equals(t.getRight()))
					break;
				t = t.getRight();
			}
			
			n1FoundOnLeft = searchNode(t.getLeft(), n1);
			n1FoundOnRight = !n1FoundOnLeft;
			
			n2FoundOnLeft = searchNode(t.getLeft(), n2);
			n2FoundOnRight = !n2FoundOnRight;

		}
		
		return t;
	}
	
	public boolean searchNode(Node root, Node n){
		if(root == null)
			return false;
		if(root.equals(n))
			return true;
		return searchNode(root.getLeft(),n) || searchNode(root.getRight(),n);
	}
	
	/**
	 * 1. If root is null return 0.
	 * 2. If root is leaf retur 1.
	 * 3. If root.left is not null then set leftD or set it Integer.MAX
	 * 4. If root.right is not null then set rightD or set it to Integer.MAX
	 * 5. return Math.min(leftD,rightD)+1
	 * @param root
	 * @return
	 */
	public int getMinDepth(Node root){
		if(root == null)
			return 0;
		if(root.getLeft()==null && root.getRight() == null)
			return 1;
		int leftD = (root.getLeft() != null)?getMinDepth(root.getLeft()):Integer.MAX_VALUE;
		int rightD = (root.getRight() != null)?getMinDepth(root.getRight()):Integer.MAX_VALUE;
		return Math.min(leftD, rightD)+1;
	}
	
	/**
	 * BST has couple of nodes being swapped, resulting in violation of BST 
	 * property. Need to identify the nodes being swapped and fix the same.
	 * Without changing/moving any other nodes in the tree.
	 * One approach would be to perform a inOrder traversal and identify the
	 * points of violation in sorted order. Fix them by swapping the respective
	 * elements. There would be two cases here.
	 * Sample tree: 4,5,7,10,14,15,17
	 * Case A: Two violations found,
	 * 	4,15,7,10,14,5,17   ---> Pairs {15,7} and {14,5} are the violations.
	 * Swap 1st element of 1st pair and last element of 2nd pair to fix.
	 * Case B: Only one violation found. This will occur when the two adjacent 
	 * elements are swapped. For example,
	 * 	4,15,7,14,10,5,17 ---> Pair {14,10} 
	 * In this case swap these to get BST fixed.
	 * @param root
	 */
	public void recoverBST(Node root){
		
		Node previous = null;
		inOrderFindViolation(root, previous);
		
		if(firstStartPoint != null && lastEndPoint != null){
			int t = firstStartPoint.getData();
			firstStartPoint.setData(lastEndPoint.getData());
			lastEndPoint.setData(t);
		}
			
	}
	
	Node firstStartPoint = null;
	Node lastEndPoint = null;
	
	public void inOrderFindViolation(Node root, Node previous){
		if(root == null)
			return;
		inOrderFindViolation(root.getLeft(), previous);
		if(previous != null){
			if(previous.getData() > root.getData()){
				if(firstStartPoint == null){
					firstStartPoint = previous;
				}
				lastEndPoint = root;
			}
		}
		previous=root;
		inOrderFindViolation(root.getRight(), previous);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/***
		 * 			 5
		 * 		   /   \
		 * 		 3      7 
		 * 		/ \    /  \
		 * 	   1   4  6    9
		 * 	  /	\	  	  / \
		 * 	 0	 2	  	 8   10
		 * 				  	  \
		 * 					   11	
		 */
		int[] data = {5,3,1,2,0,4,7,6,9,8,10,11,13,10};
		BinaryTree bt = new BinaryTree();
		bt.construct(data);
		bt.inOrder(bt.getRoot());
		System.out.println();
		bt.preOrder(bt.getRoot());
		System.out.println();
		bt.postOrder(bt.getRoot());
		System.out.printf("\n.. Size of the tree is:%d", bt.size(bt.getRoot()));
		System.out.printf("\n.. Deapth of tree is:%d", bt.getDeapth(bt.getRoot()));
		System.out.printf("\n.. Leaf count of the tree is:%d", bt.getLeafCount(bt.getRoot()));
		System.out.printf("\n.. Min value:%d",bt.getMin(bt.getRoot()));
		System.out.printf("\n.. Max value:%d", bt.getMax(bt.getRoot()));
		
		/** 
		 * Checking path sum.
		 */
		int pathSum = 42;
		System.out.println("\n Path sum for:"+ pathSum +
				", available:" + bt.checkPathSum(bt.getRoot(), pathSum));
		
		bt.printPaths(bt.getRoot(), new ArrayList<Integer>(), 0);
		
		bt.mirror(bt.getRoot());
		bt.inOrder(bt.getRoot());
		
		System.out.printf("\nConstructing balanced binary tree..\n");
		BinaryTree bt1 = new BinaryTree();
		int[] sortedData = {0,1,2,3,4,5,6,7,8,9,10};
		bt1.constructBalTree(sortedData);
		
		System.out.printf("\n Inorder \n");
		bt1.inOrder(bt1.root);
		
		System.out.printf("\n Preorder \n");
		bt1.preOrder(bt1.root);
		
		int depth1 = bt1.getDeapth(bt1.root);
		System.out.printf("\nDepth of bal tree is:%d\n", depth1);
		
		System.out.printf("\n Tree bt is balanced:%s\n",String.valueOf(bt.isBalanced(bt.getRoot())) );
		
		System.out.printf("\n Tree bt1 is balanced:%s\n",String.valueOf(bt1.isBalanced(bt1.getRoot())) );
		
		System.out.printf("\n Tree bt is balanced:%s\n",String.valueOf(bt.isBalancedInPlace(bt.getRoot())) );
		
		System.out.printf("\n Tree bt1 is balanced:%s\n",String.valueOf(bt1.isBalancedInPlace(bt1.getRoot())) );
		
		bt.setLeftMost(bt.getRoot(), 100);
		bt.inOrder(bt.getRoot());

		System.out.printf("\n Tree bt satisfies BST prop:%s\n",String.valueOf(bt.checkBST(bt.getRoot())) );
		
		System.out.printf("\n Tree bt1 satisfies BST prop:%s\n",String.valueOf(bt1.checkBST(bt1.getRoot())) );
		
		bt1.inOrder(bt1.getRoot());

		Node commonAnc = bt1.firstCommonAncestor(bt1.root, new Node(0), new Node(3));
		System.out.printf("\nFirst common ancestor is:%d\n", commonAnc.getData());
		
		bt1.insert(bt1.getRoot(), 11);
		bt1.insert(bt1.getRoot(), 12);
		bt1.inOrder(bt1.getRoot());
		System.out.println();
		System.out.println("Min depth:"+bt1.getMinDepth(bt1.getRoot()));
		System.out.println();
		System.out.println("Depth:"+bt1.getDeapth(bt1.getRoot()));
		
		System.out.println("order");
		bt1.inOrder(bt1.getRoot());
		int t = bt1.getRoot().getRight().getRight().getData();
		bt1.getRoot().getRight().getRight().setData(bt1.getRoot().getLeft().getLeft().getData());
		bt1.getRoot().getLeft().getLeft().setData(t);
		System.out.printf("\nBroken order\n");
		bt1.inOrder(bt1.getRoot());
		
		System.out.printf("\nAfter fixing\n");
		bt1.recoverBST(bt1.getRoot());
		bt1.inOrder(bt1.getRoot());
	}
	
	
	
	public void setLeftMost(Node root, int data){
		while(root.getLeft() != null)
			root = root.getLeft();
		root.setData(data);
	}

}
