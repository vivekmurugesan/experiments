package crack.code.chp3;

public class Stack {

	private Node top;
	
	public static void main(String[] args) {
		
		Stack stack = new Stack();
		for(int i=9;i>=0;i--)
			stack.push(i);
		
		while(!stack.isEmpty()) {
			System.out.printf("%d \t", stack.pop());
		}
		
		System.out.println();
			
		for(int i=9;i>=0;i--)
			stack.push(i);
		
		Stack sorted = sort(stack);
		
		while(!sorted.isEmpty()) {
			System.out.printf("%d \t", sorted.pop());
		}
	}
	
	public void push(int data) {
		
		Node t = new Node(data);
		
		if(top == null)
			this.top = t;
		else {
			t.setNext(this.top);
			this.top = t;
		}
		
	}
	
	public boolean isEmpty() {
		if(top == null)
			return true;
		else
			return false;
	}
	
	public int pop() {
		
		if(top == null)
			return -1;
		
		int result = top.getData();
		top = top.getNext();
		
		return result;
	}
	
	public int peek() {
		if(top == null)
			return -1;
		
		return top.getData();
	}
	
	public static Stack sort(Stack stack) {
		Stack sorted = new Stack();
		
		while(!stack.isEmpty()) {
			int tmp = stack.pop();
			
			while(!sorted.isEmpty() && sorted.peek() > tmp) {
				stack.push(sorted.pop());
			}
			
			sorted.push(tmp);
		}
		
		return sorted;
	}

}
