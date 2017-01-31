package test.medians;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 
 * @author vivek
 *
 */
public class FindMedian1 {

	public static void main(String[] args) {
		FindMedian1 finder = new FindMedian1();
		
		for(int i=0;i<10;i++){
			finder.addNumber(i);
			System.out.printf("\n Median:%d \n", finder.findMedian());
		}
		
		System.out.printf("\n Median:%d \n", finder.findMedian());
	}
	
	// Min heap
	private PriorityQueue<Integer> higher = new PriorityQueue<>();
	private PriorityQueue<Integer> lower = new PriorityQueue<Integer>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return -1 * o1.compareTo(o2);
		}
	});
	
	public void addNumber(int n){
		if(lower.isEmpty()){
			lower.add(n);
			return;
		}
		
		if(n <= lower.peek())
			lower.add(n);
		else 
			higher.add(n);
		rebalance();
	}
	
	public void rebalance(){
		PriorityQueue<Integer> larger = (lower.size() > higher.size()) ? lower:higher;
		PriorityQueue<Integer> smaller = (lower.size() < higher.size()) ? lower : higher;
		
		// Need to balance
		if(larger.size()-smaller.size() >= 2){
			smaller.add(larger.poll());
		}
	}
	
	public int findMedian(){
		int result;
		if(lower.size() == higher.size())
			result = (lower.peek()+higher.peek())/2;
		else{
			PriorityQueue<Integer> larger = (lower.size() > higher.size()) ? lower:higher;
			result = larger.peek();
		}
		System.out.println(lower.size() + "__" + higher.size());
		return result;
	}

}
