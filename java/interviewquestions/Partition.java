package interviewquestions;

import java.util.Arrays;

public class Partition extends TestCase {
  /**
   * Rebuilds `array` so that items smaller than `array[k]` will be left of `array[k]`,
   * and items larger than `array[k]` will be right of `array[k]`.
   *
   * @return the new index of k
   */
  private static int partition(int[] array, int k) {
    final int item = array[k];

    swap(array, k, array.length - 1);
    k = array.length - 1;

    for (int i = 0; i < k; i++) {
      while (array[i] > item && k > i) {
        swap(array, i, k);
        swap(array, i, k - 1);
        k--;
      }
    }
    
    return k;
  }

  private static void swap(int[] array, int i, int j) {
    final int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  private static int[] makeArray() {
    return new int[] { 1, 84, 7, 55, 12, 95, 247, 28, 4, 3 };
  }

  public static void main(String[] args) {
    int array[];

    array = makeArray();
    System.out.println(Arrays.toString(array));

    array = makeArray();
    assertEquals(6, partition(array, 3)); // 55
    System.out.println(Arrays.toString(array));

    array = makeArray();
    assertEquals(4, partition(array, 4)); // 12
    System.out.println(Arrays.toString(array));

    array = makeArray();
    assertEquals(0, partition(array, 0)); // 1
    System.out.println(Arrays.toString(array));

    array = makeArray();
    assertEquals(1, partition(array, 9)); // 3
    System.out.println(Arrays.toString(array));
  }
}

