package test.array.search;

import java.util.Random;

/**
 * 
 * @author vivek
 *
 */
public class RSelectOrderStatistic {

	public static void main(String[] args) {
		int[] arr = {3,1,6,-1,5,0,8,7,2};
		
		RSelectOrderStatistic selector = new RSelectOrderStatistic();
		int result = selector.rSelect(arr, 0, arr.length, arr.length, 4);
		System.out.printf("\n\n%d order statistic is:%d\n", 4, result);
	}
	
	/**
	 * Randomized selection (array A, length n, order statistic i)
	 * 0. if(n=1) return A[1]
	 * 1. Choose pivot p from A uniformly at Random
	 * 2. Parition A around p and lets p winds up being at position j
	 * 3. if(i=j) then return p
	 * 4. if(i<j) then RSelect(1st part of Arr, j-1, i)
	 * 5. if(i>j) then RSelect(2nd part of Arr, n-j, i-j)
	 * @param arr
	 * @param n
	 * @param orderStat
	 * @return
	 */
	public int rSelect(int[] arr,int start, int end, int n,
			int orderStat){
		System.out.printf("\n start:%d,end:%d,n:%d,orderstat:%d \n",
				start, end, n, orderStat);
		if(n<=1  || end<=start)
			return arr[start];
		Random rand = new Random();
		int pivIndex = rand.nextInt(n-1)+start;
		/*int pivIndex=0;*/
		System.out.printf("\npivot index chosen:index:%d\tvalue:%d\n",pivIndex,arr[pivIndex]);
		/**
		 * Iterate through all items
		 * 1. if(i<pivIndex) 
		 * 		2. if(a[i] < a[pivIndex]) do nothing and move to next
		 * 		3. else if(a[i] > a[pivIndex]) swap(i,pivIndex); pivIndex=i
		 * 4. else if(i>pivIndex)
		 * 		5. if(a[i] > a[pivIndex]) do nothing and move to next
		 * 		6. if(a[i] < a[pivIndex]) swap(pivIndex, pivIndex+1); swap(i,pivIndex); pivIndex++;
		 * 
		 *  
		 */
		for(int i=start;i<end;i++){
			if(i<pivIndex){
				if(arr[i] > arr[pivIndex]){
					//swap(i,pivIndex) moving pivot element to left.
					int t=arr[i];
					arr[i]=arr[pivIndex];
					arr[pivIndex]=t;
					pivIndex=i;
				}
			}else if(i>pivIndex){
				if(arr[i] < arr[pivIndex]){
					//swap(pivIndex+1, i).. move and create space for lesser element
					int t = arr[pivIndex+1];
					arr[pivIndex+1] = arr[i];
					arr[i] =t;
					
					//swap(pivIndex, pivIndex+1)
					t=arr[pivIndex+1];
					arr[pivIndex+1]=arr[pivIndex]; // Moving pivot element one position to right.
					arr[pivIndex] = t;
					
					//Updating pivIndex
					pivIndex +=1;
				}
			}
		}
		
		System.out.println(pivIndex);
		System.out.println(arr[pivIndex]);
		for(int i=start;i<start+n;i++)
			System.out.printf("%d\t",arr[i]);
		System.out.println();
		
		if(pivIndex == orderStat)
			return arr[pivIndex];
		else if(orderStat < pivIndex){ // order stat is on the left hand side
			return rSelect(arr, start, pivIndex, pivIndex, orderStat); 
		}else{
			return rSelect(arr, pivIndex+1, arr.length, arr.length-(pivIndex+1), orderStat-pivIndex);
		}
		
	}
	
	
}
