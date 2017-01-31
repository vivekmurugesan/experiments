package test.array.search;

import java.util.LinkedList;

/**
 * Next greater element of i is,
 * arr[j] such that,
 * 	arr[j]>arr[i]
 * 	j>i
 * 	j-i is minimum possible value.
 * @author vivek
 *
 */
public class NextGreaterElement {

	public static void main(String[] args) {
		int[] input = {98, 23, 54, 12, 20, 7, 27 };
		int[] nextGreater = nextGreaterElement(input);
		for(int i=0;i<input.length;i++){
			System.out.printf("Next greater of:%d is %d\n",input[i], 
					(nextGreater[i] != -1)?input[nextGreater[i]]:-1);
		}
	}
	
	/**
	 * Use a stack to maintain the elements in a descending order.
	 * For every element,
	 * 1. If it is less than the top element, then just push the current element.
	 * 2. If it is greater than the top element then pop out the element on top,
	 * 		Set the next_greater(top) as current element.
	 * 3. Repeat step 2 until the current element is greater than top of 
	 * the stack.
	 * 4. Finally once we hit end of the array pop all elements of the stack, 
	 * set their next as null.
	 * @param input
	 * @return
	 */
	public static int[] nextGreaterElement(int[] input){
		// Stack that stores all the greater elements in descending order.
		LinkedList<Integer> stack = new LinkedList<>();
		int[] nextGreater = new int[input.length];
		
		for(int i=0;i<input.length;i++){
			if(stack.isEmpty())
				stack.push(i);
			else{
				if(input[stack.peek()] > input[i])
					stack.push(i);
				else{
					nextGreater[stack.pop()] = i;
					while(!stack.isEmpty() && input[i] > input[stack.peek()]){
						nextGreater[stack.pop()] = i;
					}
					stack.push(i);
				}
			}
		}
		while(!stack.isEmpty()){
			nextGreater[stack.pop()] = -1;
		}
		
		return nextGreater;
	}

}
