package binary.trees;

public class SetMatrixZero {

	public static void main(String[] args) {

		int[][] input = {
						 {1,2,3,4},
						 {5,0,7,8},
						 {9,10,0,12},
						 {13,14,15,16},
						};
		
		matrixUpdate(input);
		
		for(int i=0;i<input.length;i++) {
			for(int j=0;j<input[0].length;j++) {
				System.out.print(input[i][j] + "   ");
			}
			System.out.println();
		}
	}
	
	public static void matrixUpdate(int[][] mat) {
		
		boolean[] zeroRows = new boolean[mat.length];
		boolean[] zeroCols = new boolean[mat.length];
		
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[0].length;j++) {
				if(mat[i][j] == 0) {
					zeroRows[i] = true;
					zeroCols[j] = true;
				}
			}
		}
		
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[0].length;j++) {
				if(zeroRows[i] || zeroCols[j]) {
					mat[i][j] =0;
				}
			}
		}
	}

}
