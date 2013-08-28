package interviewquestions;

/**
 * Write an algorithm such that if an element in an MxN matrix is 0,
 * its entire row and column is set to 0.
 */
public class MatrixRowColClear {
  public static void clear(int[][] matrix, int rows, int cols) {
    int resetRows[] = new int[rows];
    int resetCols[] = new int[cols];

    // we can't clear the rows/cols directly since that would overwrite
    // any zero we haven't analyzed yet, so we must do a first pass finding
    // where the zeros are, and reset the rows and columns in a second pass

    // first pass: find the zeros
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (matrix[row][col] == 0) {
          resetRows[row] = 1;
          resetCols[col] = 1;
        }
      }
    }

    // second pass: reset the rows/columns
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (resetRows[row] == 1 || resetCols[col] == 1) {
          matrix[row][col] = 0;
        }
      }
    }
  }

  public static void main(String[] args) {
    int[][] matrix = new int[][] {
        { 1, 2, 3, 4},
        { 2, 3, 4, 5},
        { 3, 4, 5, 6},
        { 4, 5, 6, 0}
    };

    clear(matrix, 4, 4);
    test(matrix[0][0] == 1);
    test(matrix[0][1] == 2);
    test(matrix[0][2] == 3);
    test(matrix[0][3] == 0);
    test(matrix[1][0] == 2);
    test(matrix[1][1] == 3);
    test(matrix[1][2] == 4);
    test(matrix[1][3] == 0);
    test(matrix[2][0] == 3);
    test(matrix[2][1] == 4);
    test(matrix[2][2] == 5);
    test(matrix[2][3] == 0);
    test(matrix[3][0] == 0);
    test(matrix[3][1] == 0);
    test(matrix[3][2] == 0);
    test(matrix[3][3] == 0);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
