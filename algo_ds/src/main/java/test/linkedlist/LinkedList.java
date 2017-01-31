package test.linkedlist;

/**
 * 
 * @author vivek
 *
 */
public class LinkedList {
	
	public Node head;

	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		for(int i=0;i<10;i++)
			list.append(i);
		list.printAll();
		System.out.printf("Size of the list:%d\n", list.size(list.head));
		System.out.printf(".. Element at %d position from the last element is: %d\n"
				,4,list.findKthFromLast(list.head, new Index(), 4).data);
		System.out.printf(".. Element at %d position from the last element is: %d\n"
				,4,list.findKthFromLastIter(list.head, 4).data);
		
		LinkedList list1 = new LinkedList();
		list1.append(7);list1.append(1);list1.append(6);
		LinkedList list2 = new LinkedList();
		list2.append(5);list2.append(9);list2.append(2);
		list.digitSumRevLists(list1.head, list2.head).printAll();
		
		/*Node revHead = list.reverse(list.head,0);
		LinkedList reversed = new LinkedList();
		reversed.head = revHead;*/
		LinkedList reversed = list.reverseAndClone(list.head);
		reversed.printAll();
		list.printAll();
		
		if(list.isPalindrome(list)){
			System.out.println("Given list :: ");
			list.printAll();
			System.out.println(" is a palindrome");
		}else{
			System.out.println("Given list :: ");
			list.printAll();
			System.out.println(" is not a palindrome");
		}
		
		LinkedList palList = new LinkedList();
		
		for(int i=0;i<10;i++)
			palList.append(i);
		for(int i=8;i>=0;i--)
			palList.append(i);
		
		if(palList.isPalindrome(palList)){
			System.out.println("Given list :: ");
			palList.printAll();
			System.out.println(" is a palindrome");
		}else{
			System.out.println("Given list :: ");
			palList.printAll();
			System.out.println(" is not a palindrome");
		}
	}
	
	public LinkedList(){
	}
	
	public void append(int data){
		if(this.head == null){
			this.head = new Node(data, null);
		}else{
			Node node = new Node(data, null);
			Node t = head;
			while(t.next != null)
				t = t.next;
			t.next = node;
		}
	}
	
	public void printAll(){
		Node t = head;
		while(t != null){
			System.out.printf("%d\t",t.data);
			t=t.next;
		}
		System.out.println();
	}
	
	public int size(Node head){
		if(head.next == null)
			return 1;
		return size(head.next)+1;
	}
	
	public static class Index {
		public int idx;
	}
	
	/**
	 * 1. Use the index object count the position from the last element.
	 * 2. With the f(n) setting it to 1 and f(n-1) setting it to 2 and so on.
	 * 3. When the idx value becomes k, set it as the return value.
	 * 4. All other calls of recursion just returns the return value from 
	 * the recursion tree.
	 * @param head
	 * @param idx
	 * @param k
	 * @return
	 */
	public Node findKthFromLast(Node head, Index idx,int k){
		if(head.next == null){
			idx.idx = 1;
			return null;
		}
		Node node = findKthFromLast(head.next, idx, k);
		idx.idx += 1;
		if(idx.idx == k)
			node = head;
		return node;
	}
	
	/**
	 * 1. Keep two pointers t1,t2.
	 * 2. Let t2 flow through k positions
	 * 3. And then start both of them run through the list with same pace.
	 * 4. When t2 flows out of the last node, t1 will point to the kth item 
	 * from last element. 
	 * @param head
	 * @param k
	 * @return
	 */
	public Node findKthFromLastIter(Node head, int k){
		Node t1=head;
		Node t2=head;
		for(int i=0;i<k;i++){
			if(t2==null)break;
			t2=t2.next;
		}
		if(t2==null)
			return null;
		for(int i=0;t2 != null;i++){
			if(t1==null)
				break;
			t1=t1.next;
			t2=t2.next;
		}
		return t1;
	}
	
	/**
	 * 617 + 592 --> 912
	 * @param head1
	 * @param head2
	 * @return
	 */
	public LinkedList digitSumRevLists(Node head1, Node head2){
		int n1 = digitSumListR(head1);
		int n2 = digitSumListR(head2);
		return numberToList(n1+n2);
	}
	
	public LinkedList numberToList(int n){
		Node head = null;
		Node tail=null;
		while(n>0){
			Node t = new Node(n%10,null);
			if(head == null){
				head = t;
				tail = t;
			}else{
				tail.next = t;
				tail=t;
			}
			n /= 10;
		}
		LinkedList result = new LinkedList();
		result.head = head;
		return result;
	}
	
	/**
	 * f(0) = 0
	 * f(1) = 1 --> Tail node return the digit
	 * f(n) = f(n-1)*10 + digit(n)
	 * @param head
	 * @return
	 */
	public int digitSumListR(Node head){
		if(head.next == null)
			return head.data;
		return digitSumListR(head.next)*10 + head.data;
	}
	
	public boolean isPalindrome(LinkedList list){
		LinkedList reversed = list.reverseAndClone(list.head);
		if(isEqual(list,reversed))
			return true;
		else
			return false;
	}
	
	public boolean isEqual(LinkedList list1, LinkedList list2){
		Node head1 = list1.head;
		Node head2 = list2.head;
		
		boolean mismatch = false;
		while(head1 != null && head2 != null){
			if(head1.data != head2.data){
				mismatch=true;
				break;
			}
		}
		if(mismatch || head1 != null || head2 != null)
			return false;
		else
			return true;
	}
	
	/**
	 * Does in place reversal of a list.
	 * @param head
	 * @param i
	 * @return
	 */
	public Node reverse(Node head, int i){
		if(head.next == null)
			return head;
		Node result = null;
		result=reverse(head.next,i+1);
		head.next.next = head;
		if(i==0)
			head.next = null;
		return result;
	}
	
	/**
	 * 1. Iterate through the list with head pointer.
	 * 2. Create a head to point the reversed list.
	 * 3. Inside the loop create a new node with data from head.
	 * 4. Make the next of new node point to revHead.
	 * 5. Move the revHead to point to the newly created node.
	 * @param head
	 * @return
	 */
	public LinkedList reverseAndClone(Node head){
		Node revHead = null;
		while(head != null){
			Node temp = new Node(head.data,null);
			temp.next = revHead;
			revHead = temp;
			head = head.next;
		}
		LinkedList result = new LinkedList();
		result.head = revHead;
		
		return result;
	}
}
