package crack.code.chp3;

public class MinStack {

	private Stack dataStack;
	private Stack minStack;
	
	public static void main(String[] args) {
		
		MinStack minStack = new MinStack();
		
		for(int i=0;i<10;i++) {
			minStack.push(i);
			System.out.println("Min.. ::" + minStack.min());
		}
			
		
		while(!minStack.isEmpty()) {
			System.out.printf("%d \t", minStack.pop());
			System.out.printf("%d \t", minStack.min());
			System.out.println();
		}
		
		
	}
	
	public MinStack() {
		this.dataStack = new Stack();
		this.minStack = new Stack();
	}
	
	public boolean isEmpty() {
		return dataStack.isEmpty();
	}
	
	public void push(int data) {
		if(dataStack.isEmpty()) {
			dataStack.push(data);
			minStack.push(data);
			return;
		}
		
		if(data <= minStack.peek())
			minStack.push(data);
		dataStack.push(data);
	}
	
	public int pop() {
		if(dataStack.isEmpty())
			return -1;
		
		int result = dataStack.pop();
		if(result == minStack.peek())
			minStack.pop();
		
		return result;
	}
	
	public int min() {
		
		return minStack.peek();
	}

}
