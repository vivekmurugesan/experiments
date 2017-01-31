package test.min.stack;

import java.util.LinkedList;

/**
 * 
 * @author vivek
 *
 */
public class MinStack<T> {
	private LinkedList<T> stack = null;
	private LinkedList<T> minStack = null;
	
	public MinStack(){
		stack = new LinkedList<T>();
		minStack = new LinkedList<T>();
	}
	
	public void push(T obj){
		if(obj instanceof Comparable){
			stack.addFirst(obj);
			if(minStack.isEmpty())
				minStack.addFirst(obj);
			else{
				if(((Comparable)obj).
						compareTo((Comparable)(minStack.getFirst())) < 0){
					minStack.addFirst(obj);
				}
			}
		}else
			throw new IllegalArgumentException("Object should implement Comparable");
	}
	
	public T top(){
		return stack.getFirst();
	}
	
	public T pop(){
		T obj = stack.removeFirst();
		
		if(obj instanceof Comparable){
			if(!minStack.isEmpty()){
				if(((Comparable)obj).
						compareTo((Comparable)(minStack.getFirst())) == 0){
					minStack.removeFirst();
				}
			}
		}
		
		return obj;
	}
	
	public void printStacks(){
		System.out.println(stack);
		System.out.println(minStack);
	}
	
	public T getMin(){
		return minStack.getFirst();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MinStack<Integer> minStack1 = new MinStack<Integer>();
		minStack1.push(10);minStack1.push(5);minStack1.push(1);minStack1.push(2);minStack1.push(3);minStack1.push(-1);minStack1.push(50);
		minStack1.printStacks();
		System.out.println(minStack1.pop());
		System.out.println(minStack1.pop());
		minStack1.printStacks();
		System.out.println(minStack1.pop());
		System.out.println(minStack1.pop());
		System.out.println(minStack1.pop());
		minStack1.printStacks();
	}

}
