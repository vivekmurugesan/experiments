package test.matrix;

/**
 * 
 * @author vivek
 *
 */
public class MatrixRotate {

	public static void main(String[] args) {
		
		MatrixRotate rotator = new MatrixRotate();
		int[][] mat = rotator.initSquareMatrix(6);
		System.out.println("Original..");
		rotator.printMatrix(mat);
		rotator.rotate(mat);
		System.out.printf("\n\nRotated..\n");
		rotator.printMatrix(mat);
	}
	
	public void printMatrix(int[][] mat){
		for(int i=0;i<mat.length;i++){
			for(int j=0;j<mat[0].length;j++){
				System.out.printf("%d\t", mat[i][j]);
			}
			System.out.println();
		}
	}
	
	public int[][] initSquareMatrix(int d){
		int[][] result = new int[d][d];
		for(int i=0,k=1;i<d;i++){
			for(int j=0;j<d;j++){
				result[i][j] = k++;
			}
		}
		return result;
	}
	
	/**
	 * 1. Rotating layer by layer.
	 * 2. Splitting the layer contents, outer most layer will contain 
	 * n-1 entries. 
	 * 3. Every successive inner layers will contain,
	 * 	n-1 - layer*2 entries. (For example the next inner layer 
	 * will contain n-1-2 and so on).
	 * 4. Making a 4 way move for every entry in the layer will get
	 * the layer rotated.
	 * 5. Repeat the movements until layer<n/2. 
	 * @param matrix
	 */
	public void rotate(int[][] matrix){
		int n = matrix[0].length;
		int size = n;
		int layer = 0;
		
		for(;layer<n/2;layer++){
			size = n -1- layer*2;
			for(int i=layer,j=0;i<layer+size;i++,j++){
				int t = matrix[layer][i];
				matrix[layer][i]=matrix[layer+size-j][layer];
				matrix[layer+size-j][layer] = matrix[layer+size][layer+size-j];
				matrix[layer+size][layer+size-j] = matrix[i][layer+size]; 
				matrix[i][layer+size]=t;
			}
		}
		
	}

}
