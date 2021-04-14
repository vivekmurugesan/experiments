package crack.code.chp3;

public class QueWithStacks {

	private Stack dataStack;
	private Stack bufferStack;
	
	public QueWithStacks() {
		this.dataStack = new Stack();
		this.bufferStack = new Stack();
	}
	
	public static void main(String[] args) {
		QueWithStacks queue = new QueWithStacks();
		for(int i=0;i<10;i++)
			queue.insert(i);
		
		while(!queue.isEmpty()) {
			System.out.printf("%d \t", queue.delete());
		}
	}
	
	public boolean isEmpty() {
		return dataStack.isEmpty();
	}
	
	public void insert(int data) {
		dataStack.push(data);
	}
	
	public int delete() {
		// pop until reaching the bottom
		
		if(dataStack.isEmpty())
			return -1;
		
		int result = -1;
		
		while(!dataStack.isEmpty()) {
			result = dataStack.pop();
			if(!dataStack.isEmpty())
				bufferStack.push(result);
		}
		
		while(!bufferStack.isEmpty()) {
			dataStack.push(bufferStack.pop());
		}
		
		return result;
	}

}
