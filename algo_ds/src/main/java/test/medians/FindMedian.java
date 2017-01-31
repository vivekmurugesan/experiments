package test.medians;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 
 * @author vivek
 *
 */
public class FindMedian {

	public static void main(String[] args) {
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		int[] input1 = {1,2,3,4,5,6,7,8,9,10,11};
		
		FindMedian finder = new FindMedian();
		double median1 = finder.findMedian(input);
		System.out.println("Median1:" + median1);
		
		finder = new FindMedian();
		double median2 = finder.findMedian(input1);
		System.out.println("Median1:" + median2);
		
	}
	
	PriorityQueue<Integer> higher=new PriorityQueue<Integer>();
	PriorityQueue<Integer> lower=new PriorityQueue<Integer>(new Comparator<Integer>(){

		@Override
		public int compare(Integer o1, Integer o2) {
			return -1 * o1.compareTo(o2);
		}
		
	});
	

	public double findMedian(int[] input){
		
		double median=0;
		for(int i=0;i<input.length;i++){
			addNumber(input[i],lower, higher);
			rebalance(lower,higher);
			median = getMedian(lower,higher);
		}
		
		System.out.printf("Size of higher:%d\tlower:%d\n", 
				higher.size(), lower.size());
		
		return median;
	}


	private double getMedian(PriorityQueue<Integer> lower2,
			PriorityQueue<Integer> higher2) {
		if(lower2.size() == higher2.size())
			return (lower2.peek() + higher2.peek())/2.0;
		PriorityQueue<Integer> bigger = lower2.size() < higher2.size() ? higher2 :lower2;
		return bigger.peek();
	}


	private void rebalance(PriorityQueue<Integer> lower2,
			PriorityQueue<Integer> higher2) {
		PriorityQueue<Integer> bigger = lower2.size() <= higher2.size() ? higher2 :lower2;
		PriorityQueue<Integer> smaller = lower2.size() <= higher2.size() ? lower2 :higher2;
		
		if(bigger.size()-smaller.size() >= 2)
			smaller.add(bigger.poll());
	}


	/**
	 * 
	 * @param i
	 * @param lower2
	 * @param higher2
	 */
	private void addNumber(int i, PriorityQueue<Integer> lower2,
			PriorityQueue<Integer> higher2) {
		if(lower2.isEmpty() || i <= lower2.peek())	
			lower2.add(i);
		else 
			higher2.add(i);
	}
	
	
}
