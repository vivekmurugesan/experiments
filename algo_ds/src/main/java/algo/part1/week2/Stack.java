package algo.part1.week2;

import java.util.NoSuchElementException;

public class Stack<T> {

	private Node<T> top;
	
	public void push(T data) {
		if(top == null)
			top = new Node<T>(data);
		else {
			Node<T> temp = new Node<>(data);
			temp.next = top;
			top = temp;
		}
	}
	
	public T pop() {
		if(top == null)
			throw new NoSuchElementException("Empty stack");
		
		T result = top.data;
		top = top.next;
		
		return result;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	class Node<T>{
		public Node(T data) {
			this.data = data;
		}
		T data;
		Node<T> next;
	}
	
	
	
	
	public static void main(String[] args) {
		int n = 10;
		Stack<Integer> ints = new Stack<>();
		for(int i=0;i<n;i++)
			ints.push(i);
		
		while(!ints.isEmpty()){
			System.out.printf("%d\t", ints.pop());
		}
		
		System.out.println();
		
		n = 100;
		
		while(n>0) {
			ints.push(n % 2);
			n /= 2;
		}
		
		while(!ints.isEmpty())
			System.out.printf("%d ", ints.pop());
		
		System.out.println();
			
	}

}
