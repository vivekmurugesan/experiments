package test.array.search;

/**
 * From a list of numbers in the range of 0 to 1M search for missing numbers.
 * @author vivek
 *
 */
public class SearchMissingNumber {

	public static void main(String[] args) {
		int[] input = {1,3,9,10,100,1000,100,1,7,13,27,100000, 1000000, 27000,13000};
		byte[] bitVector = constructBitVector(input, 1000000);
		for(int n : input){
			if(findIfPresent(bitVector, n))
				System.out.printf("Found:%d\n",n);
			else
				throw new RuntimeException("Value:"+n+"  not found");
				/*System.out.println("Value:"+n+"  not found");*/
		}
		
		int[] negativeCases = {150,4,2,999999,999,26};
		for(int n : negativeCases){
			if(findIfPresent(bitVector, n))
				throw new RuntimeException("Nonexisting value:"+ n + " found");
			else
				System.out.println("Nonexising value:"+ n + " not found");
		}
	}
	
	/**
	 * Lookup into a bit vector is as follows,
	 * 	bytePos = toSearch/8
	 *  bitPos = toSearch%8
	 *  mask = 1<<(bitPos-1)
	 * Return true if(bitVector[bytPos] & mask == mask)
	 * false otherwise
	 * @param bitVector
	 * @param toSearch
	 * @return
	 */
	public static boolean findIfPresent(byte[] bitVector, int toSearch){
		int bytePos = toSearch/8;
		int bitPos = toSearch%8;
		byte mask = (byte) (1<<(bitPos-1));
		boolean found = false;
		if((bitVector[bytePos] & mask) == mask){
			found = true;
		}
		
		return found;
	}
	
	/**
	 * Construct a bit vector to accommodate the max value.
	 * For any value n the position of it in the bit vector is,
	 * 		byte position = n/8
	 * 		bit position = n%8
	 * To set the bit position
	 * 		bv[n/8] | 1<<n%8
	 * @param arr
	 * @param maxVal
	 * @return
	 */
	public static byte[] constructBitVector(int[] arr, int maxVal){
		int thousands = 1;
		while(maxVal > 0){
			thousands++;
			maxVal /= 1000;
		}
		// 1000 --> 2^10
		int tows = thousands * 10;
		
		int size = (int)Math.pow(2,tows)/8;
		byte[] bitVector = new byte[size];
		
		for(int a : arr){
			int bytePos = a/8;
			int bitPos = a%8;
			// set the position
			bitVector[bytePos] |= 1<<(bitPos-1);
		}
		return bitVector;
	}

}
