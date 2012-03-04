package rationalNumbers;

public class RationalAnalysis {
	public static RationalNumber det(RationalNumber [][] array) throws IllegalArgumentException {
		if (!isSquare(array)) {
			throw new IllegalArgumentException("dimensions must match");
		}
		RationalNumber determinant = new RationalNumber(0,1);
		if (array.length == 2) {
			determinant = array[0][0].multiply(array[1][1]).subtract(
						  array[0][1].multiply(array[1][0]));
		} else {
			for (int i = 0; i<array.length; i++) {
				RationalNumber factor = new RationalNumber(array[i][0]);
				for (int j=0;j<i;j++) { factor = factor.multiply(-1); }
				if (!factor.isZero()) {
					determinant = determinant.add( det(thisSubMatrix(array,i)).multiply(factor) );
				}
			}
		}
		return determinant;
	}
	
	private static RationalNumber [][] thisSubMatrix(RationalNumber [][] array, int rowIndex) {
		int n = array.length;
		RationalNumber [][] newArray = new RationalNumber [n-1][n-1];
		int localRowIndex = 0;
		// Iterate over all the rows.
		for (int i = 0; i < n; i++) {
			if (i != rowIndex) {
				// We are expanding accross the first column of the whole matrix, so we never take j = 0.
				for (int j = 1; j < n; j++) {
					newArray[localRowIndex][j-1] = new RationalNumber(array[i][j].getNumerator(),array[i][j].getDenominator());
				}
				localRowIndex++;
			}
		}
		return newArray;
	}
	
	public static RationalNumber [][] inverse(RationalNumber [][] array) throws IllegalArgumentException{
		if (!isSquare(array)) {
			throw new IllegalArgumentException("Inverse needs a square array.");
		}
		
		// Initialize the inverse to the identity. And copy array to matrixA. It would be bad manners to clobber array.
		// 		Unfortunately I don't know any in place algorithms for computing matrix inverses.
		int n = array.length;
		RationalNumber [][] inverse = new RationalNumber[n][n];
		RationalNumber [][] matrixA = new RationalNumber[n][n];
		for (int i = 0; i < n; i++) {
			for (int j=0; j<n; j++) {
				if (i == j) {
					inverse[i][j] = new RationalNumber(1);
				} else {
					inverse[i][j] = new RationalNumber(0);
				}
				matrixA[i][j] = new RationalNumber(array[i][j]);
			}
		}
		
		// Loop over diagonals to change array into lower triangular form.
		for (int diag = 0; diag < n; diag++) {
			pivot(matrixA,inverse, diag);
			// Put a 1 in the i'th diagonal of matrix A, I.E. multiply matrixA[diag] by (matrixA[diag][diag])^(-1).
			//		Also, do the same operation to the matrix inverse.
			RationalNumber p = matrixA[diag][diag].invert();
			matrixA[diag] = scalarMultiply(matrixA[diag],p);
			inverse[diag] = scalarMultiply(inverse[diag],p);
			// Loop over sub diagonal rows to zero first entries.
			for (int row = diag+1; row < n; row++) {
				RationalNumber [] rowStorage;
				RationalNumber q = matrixA[row][diag].multiply(-1);
				// Set rowStorage[diag] = -matrixA[row][diag], since matrixA[diag][diag] = 1.
				rowStorage =   scalarMultiply(matrixA[diag],q);
				// Now rowStorage[diag] + matrixA[row][diag] = 0.
				matrixA[row] = vectorAddition(matrixA[row],rowStorage);
				// Same operations to inverse.
				rowStorage =   scalarMultiply(inverse[diag],q);
				inverse[row] = vectorAddition(inverse[row],rowStorage);
			}
		}
		
		// Reduce lower triangular form to identity.
		for (int diag = n-1; diag > 0; diag--) {
			for (int row = diag-1; row >= 0; row--) {
				RationalNumber [] rowStorage;
				RationalNumber q = matrixA[row][diag].multiply(-1);
				// Make rowStorage[diag] = -matrix[row][diag], since matrixA[diag][diag] = 1.
				rowStorage = scalarMultiply(matrixA[diag],q);
				// rowstorage[diag] + matrix[row][diag] = 0 now.
				matrixA[row] = vectorAddition(matrixA[row],rowStorage);
				// Must perform the exact same operations to matrix inverse.
				rowStorage = scalarMultiply(inverse[diag],q);
				inverse[row] = vectorAddition(inverse[row],rowStorage);
			}
			// Renormalize the newly diagonalized row.
			RationalNumber q = new RationalNumber(matrixA[diag-1][diag-1]);
			matrixA[diag-1] = scalarMultiply(matrixA[diag-1],q);
			inverse[diag-1] = scalarMultiply(inverse[diag-1],q);
		}
		
		// Cross your fingers and pray to your gods.
		return inverse;
	}
	
	/**
	 * Helper subroutine for matrix inverse function. Returns the two argument arrays pivoted so that the diagonal entry in
	 * column <code>column</code> is non zero.
	 * 
	 * @param matrixA, the <code>RationalNumber</code> array we are trying to invert.
	 * @param inverse, the inverse so far.
	 * @param column, the column index we are pivoting on.
	 * @throws IllegalArgumentException if the matrix is singular, which inverse won't catch.
	 */
	private static void pivot(RationalNumber [][] matrixA, RationalNumber [][] inverse, int column) 
		throws IllegalArgumentException {
		if (matrixA[column][column].getNumerator() == 0) {
			int row = column + 1;
			while ((row < matrixA.length) && (matrixA[row][column].getNumerator() == 0)) {
				row++;
			}
			// Make sure the matrix isn't singular.
			if (row != matrixA.length) {
				RationalNumber [] rowStorage;
				rowStorage = matrixA[row];
				matrixA[row] = matrixA[column];
				matrixA[column] = rowStorage;
				rowStorage = inverse[row];
				inverse[row] = inverse[column];
				inverse[column] = rowStorage;
			} else {
				throw new IllegalArgumentException("Matrix is singular.");
			}
		}
	}
	
	/**
	 * Essentially scalar multiplication, but limited to Rational Numbers. Does not alter inputs.
	 * 
	 * @param row, a <code>RationalNumber</code> vector.
	 * @param x, a <code>RationalNumber</code>.
	 * @return row*x, a new <code>RationalNumber</code> vector.
	 */
	public static RationalNumber [] scalarMultiply(RationalNumber [] row, RationalNumber x) {
		int n = row.length;
		RationalNumber [] newRow = new RationalNumber [n];
		for (int i = 0; i < n; i++) {
			newRow[i] = row[i].multiply(x);
		}
		return newRow;
	}
	
	public static RationalNumber [] vectorAddition(RationalNumber [] x, RationalNumber [] y) 
		throws IllegalArgumentException {
		if (x.length != y.length) {
			throw new IllegalArgumentException("Vector dimensions must match.");
		}
		
		RationalNumber [] z = new RationalNumber [x.length];
		for (int i = 0; i<x.length; i++) {
			z[i] = x[i].add(y[i]);
		}
		return z;
	}
	
	private static boolean isSquare(RationalNumber [][] array) {
		boolean square = true;
		for (int i = 0; i < array.length; i++) {
			square = square && (array.length == array[i].length);
		}
		return square;
	}
}
