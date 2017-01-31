package rgb.sort;

/**
 * 
 * @author vivek
 *
 */
public class SortRGB {
	
	public void sort1(char[] arr){
		if(arr == null)
			return;
		int nextRIndex = 0;
		for(int i=0;i<arr.length;i++){
			char c = arr[i];
			if(c == 'R' /*&& i != nextRIndex*/){
				// Swapping
				char t = arr[nextRIndex];
				arr[nextRIndex] = arr[i];
				arr[i] = t;
				nextRIndex++;
			}
		}
		int nextGIndex = nextRIndex;
		for(int i=nextGIndex;i<arr.length;i++){
			char c = arr[i];
			if(c == 'G' /*&& i != nextGIndex*/){
				// Swapping
				char t = arr[nextGIndex];
				arr[nextGIndex] = arr[i];
				arr[i] = t;
				nextGIndex++;
			}
		}
		
		int nextBIndex = nextGIndex;
		for(int i=nextBIndex;i<arr.length;i++){
			char c = arr[i];
			if(c == 'B' /*&& i != nextBIndex*/){
				// Swapping
				char t = arr[nextBIndex];
				arr[nextBIndex] = arr[i];
				arr[i] = t;
				nextBIndex++;
			}
		}
	}
	
	public void print(char[] arr){
		if(arr == null)
			return;
		for(char c:arr){
			System.out.printf("%c\t", c);
		}
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char[] arr = {'R','G','G','G','G','B','R','B','B','R','R','G','B'};
		SortRGB rgb = new SortRGB();
		rgb.sort1(arr);
		rgb.print(arr);
	}

}
