package test.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vivek
 *
 */
public class Heap {

	private List<Integer> keys;
	
	public Heap(){
		this.keys = new ArrayList<>();
	}
	
	public void construct(List<Integer> elements){
		for(Integer key: elements){
			this.insert(key);
		}
	}
	
	public void print(){
		System.out.println(this.keys);
	}
	
	/**
	 * Insertion operation with bubble up approach.
	 * @param key
	 */
	public void insert(Integer key){
		if(keys.isEmpty()){
			keys.add(key);
			return;
		}
		
		keys.add(key);
		int position = keys.size();
		int parentIndex = position/2;
		int parent = keys.get(parentIndex-1);
		while(parent > key){
			// Swap with parent
			keys.set(position-1, parent);
			keys.set(parentIndex-1, key);
			position = parentIndex;
			parentIndex = position/2;
			if(parentIndex<=0)
				break; // No more traversal required.
			parent = keys.get(parentIndex-1);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer extractMin(){
		if(keys.isEmpty())
			return null;
		
		int result = keys.get(0);
		if(keys.size() == 1){
			keys.remove(0);
			return result;
		}
		
		keys.set(0, keys.get(keys.size()-1));
		keys.remove(keys.size()-1);
		if(keys.size() <= 1)
			return result;
		
		// Bubble down operation.
		int index=1;
		int lChildIndex = 2*index;
		int rChildIndex = 2*index+1;
		/** Iterate when at least one child is present. */
		while(lChildIndex<=keys.size() || rChildIndex<=keys.size()){
			/** Fetching the values of current root and children */
			int currentVal = keys.get(index-1);
			int lChild = keys.get(lChildIndex-1); // When only one child present then it should be the left one
			int rChild = Integer.MAX_VALUE; // Right would be optional here.
			if(rChildIndex <= keys.size())
				rChild = keys.get(rChildIndex -1);
			
			if(currentVal < lChild && currentVal < rChild)
				break;// Heap prop satisfied with root less than both children
			
			// To be bubbled down swap values and set index accordingly.
			// Bubble down towards left
			if(lChild <= rChild){
				keys.set(lChildIndex-1, currentVal);
				keys.set(index-1, lChild);
				index=lChildIndex;
			}else{ // Bubble down towards right
				keys.set(rChildIndex-1, currentVal);
				keys.set(index-1, rChild);
				index=rChildIndex;
			}
			
			
			lChildIndex=2*index;
			rChildIndex=2*index+1;
		}
		
		return result;
	}
	
	/**
	 * Sort the heap contents and return.
	 * An application of min heap.
	 * @return
	 */
	public List<Integer> sort(){
		List<Integer> result = new ArrayList<>();
		
		Integer min = this.extractMin();
		System.out.println();
		while(min != null){
			result.add(min);
			min = this.extractMin();
			/*heap.print();*/
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
		int[] input = {4,1,9,3,7,6,5,0,2,8,4,3,10,11};
		List<Integer> list =  new ArrayList<>();
		for(int key : input)list.add(key);
		
		Heap heap =  new Heap();
		heap.construct(list);
		
		heap.print();
		
		System.out.println("Sorted values");
		
		System.out.println(heap.sort());
		
		/*Integer min = heap.extractMin();
		System.out.println();
		do{
			System.out.printf("%d\t",min);
			min = heap.extractMin();
		}while(min != null);
		System.out.println();
		heap.print();*/
		
		
	}

}
