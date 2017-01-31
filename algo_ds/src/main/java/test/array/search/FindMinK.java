package test.array.search;

import java.util.LinkedList;

/**
 * 
 * @author vivek
 *
 */
public class FindMinK {

	public static void main(String[] args){
		Integer[] arr = {-1,7,3,1,2,4,0,8,9,6};
		int k=3;
		Integer[] minArr = new Integer[arr.length-k+1];
		Integer[] tmpList = null;
		
		for(int i=0; i<arr.length; i++){
			int min=Integer.MAX_VALUE;
			int minPos=-1;
			if(i==0){
				for(int j=0;j<k;j++){
					if(arr[j] < min){
						min = arr[j];
						minPos = j;
					}
				}
				//tmpList = new LinkedList<Integer>();
				tmpList = new Integer[k-minPos];
				System.arraycopy(arr, minPos, tmpList, 0, k-minPos);
				minArr[i] = min;
				System.out.println("Min of:" + i +"::" + min);
			}else{
				int size = tmpList.length;
				min = tmpList[0];
				for(int j=i+size+1; j<i+k; j++){
					if(arr[j] < min){
						min = arr[j];
						minPos = j;
					}
				}
				tmpList = new Integer[i+k-minPos];
				System.arraycopy(arr, minPos, tmpList, 0, i+k-minPos);
				minArr[i] = min;
				System.out.println("Min of:" + i +"::" + min);
			}
		}
		for(int i=0;i<minArr.length;i++){
			System.out.println("::->" + minArr[i]);
		}
	}
}
