package test.sum.zero;

/**
 * 
 * @author vivek
 *
 */
public class LargestProduct {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			int arr[]={10,2,6,-7,8,-9,8,-7,6,4,1};
			int arrSize = arr.length;
			
			int arrProduct=1;
			for (int i=0;i<arrSize;i++){
				arrProduct*=arr[i];
			}

			int LHSProduct=1;
			int RHSProduct=arrProduct;
			System.out.println("arr prod:" + arrProduct);
			int largestProduct=0;
			int startRange =0, endRange = 0;
			for (int i=0;i<arrSize;i++){
				LHSProduct*=arr[i];
				RHSProduct/=arr[i];
				if(LHSProduct>largestProduct){
					startRange=0;
					endRange=i;
					largestProduct = LHSProduct; 
				}
				if(RHSProduct>largestProduct){
					startRange=i;
					endRange=arrSize-1; 
					largestProduct = RHSProduct;
				}
					
				
			}

			System.out.println("Largest prod:" + largestProduct);
			
			for (int i = startRange;i<=endRange;i++){
				System.out.print( arr[i] + ", ");
			}
			

		
	}

}
