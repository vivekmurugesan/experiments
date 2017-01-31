package test.matrix;

/**
 * 
 * @author vivek
 *
 */
public class PrintDiagonals {

	public static void main(String[] args) {
		int[][] matrix = {  {1,2,3,4,5},
							{6,7,8,9,10},
							{11,12,13,14,15},
							{16,17,18,19,20}
						};
		
		printDiagonals(matrix);
	}
	
	/**
	 * Number of diagonals = rowCount+colCount-1
	 * There are two parts to the algorithm.
	 * Part1: prints all the diagonals starting from 1st column of every row.
	 * Part2: prints all the diagonals starting from the elements from the 
	 * last row.
	 * @param matrix
	 */
	public static void printDiagonals(int[][] matrix){
		int rowCount = matrix.length;
		int colCount = matrix[0].length;
		
		// Part 1
		for(int i=0;i<rowCount;i++){
			int row=i,col=0;
			for(int j=0;j<=i;j++){
				System.out.printf("%d\t", matrix[row][col]);
				row -=1;
				col +=1;
			}
			System.out.println();
		}
		
		// Part 2
		for(int i=1;i<colCount;i++){
			int col=i;
			for(int row=rowCount-1;row>=0&&col<colCount;row--){
				System.out.printf("%d\t", matrix[row][col]);
				col += 1;
			}
			System.out.println();
		}
	}

}
