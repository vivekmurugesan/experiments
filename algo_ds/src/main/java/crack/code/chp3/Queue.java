package crack.code.chp3;

public class Queue {

	private Node front;
	private Node rear;
	
	public static void main(String[] args) {
		
		Queue queue = new Queue();
		for(int i=0;i<10;i++)
			queue.insert(i);
		
		while(!queue.isEmpty()) {
			System.out.printf("%d \t", queue.delete());
		}
		
		System.out.println();
	}
	
	public void insert(int data) {
		
		if(front == null && rear == null) {
			Node node = new Node(data);
			front = node;
			rear = node;
			return;
		}
		
		Node node = new Node(data);
		rear.setNext(node);
		rear = rear.getNext();
	}
	
	public int delete() {
		if(front == null && rear == null)
			return -1;
		int result;
		if(front == rear) {
			result = front.getData();
			front = null;
			rear = null;
		}else {
			result = front.getData();
			front = front.getNext();		
		}
			
		return result;
	}
	
	public boolean isEmpty() {
		if(front == null && rear == null)
			return true;
		else
			return false;
	}

}
