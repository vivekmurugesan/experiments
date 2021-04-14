package crack.code.chp2;

public class LinkedList {
	
	private Node head;

	public static void main(String[] args) {
		
		LinkedList list = new LinkedList();
		
		for(int i=0;i<10;i++)
			list.append(i);
		
		list.print();
		
		list.insertAfter(7, 70);
		list.insertAfter(9, 90);
		list.print();
		
		list.insertBefore(2, 20);
		list.insertBefore(0, -1);
		list.insertBefore(90, 95);
		list.print();
		
		list.delete(90);
		list.delete(95);
		list.delete(70);
		list.delete(-1);
		list.delete(20);
		
		list.print();
		
		//list.insertAfter(2, 2);
		list.insertAfter(3, 2);
		list.insertBefore(7, 2);
		list.insertBefore(8, 3);
		list.insertBefore(9, 4);
		
		list.print();
		
		list.deleteDups();
		
		list.print();
		
		int n = 3;
		System.out.printf("\n%d th last element : %d\n", n, list.nthLast(n));
		
		LinkedList list1 = new LinkedList();
		list1.append(3);list1.append(1);list1.append(5);
		
		LinkedList list2 = new LinkedList();
		list2.append(5);list2.append(9);list2.append(2);
		
		LinkedList list3 = add(list1, list2);
		
		list1.print();
		list2.print();
		list3.print();
		
	}
	
	public LinkedList() {
		this.head = null;
	}
	
	public void append(int data) {
		
		if(head == null) {
			head = new Node(data);
			return;
		}
		
		Node t = head;
		while(t.getNext() != null)
			t = t.getNext();
		
		Node temp = new Node(data);
		t.setNext(temp);
	}
	
	public void print() {
		Node t = head;
		
		while(t != null) {
			System.out.printf("%d\t", t.getData());
			t = t.getNext();
		}
		
		System.out.println();
	}
	
	public void insertAfter(int after, int data) {
		
		if(head == null)
			return;
		
		Node t = head;
		Node temp = new Node(data);
		while(t.getNext() != null) {
			if(t.getData() == after) {
				temp.setNext(t.getNext());
				t.setNext(temp);
			}
			
			t = t.getNext();
		}
		
		if(t.getData() == after)
			t.setNext(temp);
	}
	
	public void insertBefore(int before, int data) {
		
		if(head == null)
			return;
		
		Node t = head;
		Node prev = null;
		Node temp = new Node(data);
		
		while(t.getNext() != null) {
			
			if(t.getData() == before) {
				if(prev == null) {
					temp.setNext(head);
					this.head = temp;
					return;
				}
				prev.setNext(temp);
				temp.setNext(t);
				return;
			}
			prev = t;
			t = t.getNext();
		}
		
		if(t.getData() == before) {
			prev.setNext(temp);
			temp.setNext(t);
		}
	}
	
	public void delete(int data) {
		
		if(head == null)
			return;
		
		Node t = head;
		Node prev = null;
		
		while(t != null) {
			
			if(t.getData() == data) {
				if(prev == null) {
					head = head.getNext(); // moving head ahead
					return;
				}
				prev.setNext(t.getNext());
				t.setNext(null); // optional step
			}
			
			prev = t;
			t = t.getNext();
		}
	}
	
	public void deleteDups() {
		
		if(head == null)
			return;
		
		for(Node t = head;t != null ; t = t.getNext()) {
			// For every node compare it with rest of the nodes.
			Node prev = t;
			for(Node u = t.getNext(); u != null;) {
				if(u.getData() == t.getData()) {
					prev.setNext(u.getNext());
					u = prev;
				}
				
				prev = u;
				u = u.getNext();
			}
		}
	}
	
	public int nthLast(int n) {
		if(head == null)
			return -1;
		
		Node t1 = head;
		Node t2 = head;
		int count = 0;
		
		while(t1 != null) {
			
			if(count >= n)
				t2 = t2.getNext();
			
			t1 = t1.getNext();
			count++;
		}
		
		return t2.getData();
	}
	
	public static LinkedList add(LinkedList list1, LinkedList list2) {
		
		if(list1.head == null || list2.head == null)
			return null;
		
		LinkedList result = new LinkedList();
		
		Node t = result.head;
		Node t1 = list1.head;
		Node t2 = list2.head;
		int carry = 0;
		
		while(t1 != null && t2 != null) {
			int sum = t1.getData() + t2.getData() + carry;
			Node temp;
			
			if(sum >= 10) {
				temp = new Node(sum % 10);
				carry = 1;
			}else
				temp = new Node(sum);
			
			if(t == null) 
				result.head = temp;
			else
				t.setNext(temp);
			t = temp;	
			t1 = t1.getNext();
			t2 = t2.getNext();
		}
		
		return result;
	}

}
