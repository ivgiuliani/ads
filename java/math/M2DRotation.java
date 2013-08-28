package math.java;

import java.util.Arrays;

/**
 * Simple 90 degrees rotation of a 2D matrix of arbitrary size
 */
public class M2DRotation {
  public static int[][] rotate(int[][] m) {
    int rows = m.length;
    int columns = m[0].length;

    int[][] rotated = new int[columns][rows];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        rotated[col][rows - row - 1] = m[row][col];
      }
    }

    return rotated;
  }

  public static void print(int[][] matrix) {
    for (int[] row : matrix) {
      System.out.println(Arrays.toString(row));
    }
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {  1,  2,  3,  4 },
      {  5,  6,  7,  8 },
      {  9, 10, 11, 12 },
      { 13, 14, 15, 16 },
    };

    print(matrix);
    System.out.println(" --rotate-- ");
    int[][] rotated = rotate(matrix);
    print(rotated);

    System.out.println("\n ---------- \n");

    int[][] matrix2 = {
        { 1, 2, 3},
        { 3, 4, 5}
    };
    print(matrix2);
    System.out.println(" --rotate-- ");
    int[][] rotated2 = rotate(matrix2);
    print(rotated2);
  }
}