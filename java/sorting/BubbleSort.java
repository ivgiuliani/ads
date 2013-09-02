package sorting;

import java.util.Arrays;

public class BubbleSort {
  public static int[] bubblesort(int[] items) {
    boolean sorted = false;
    int tmp;

    while (!sorted) {
      sorted = true;

      for (int i = 1; i < items.length; i++) {
        if (items[i] < items[i - 1]) {
          tmp = items[i];
          items[i] = items[i - 1];
          items[i - 1] = tmp;
          sorted = false;
        }
      }
    }

    return items;
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(bubblesort(new int[]{1, 2, 3, 4, 5, 6, 7})));
    System.out.println(Arrays.toString(bubblesort(new int[]{5, 1, 6, 3, 4, 2, 7})));
    System.out.println(Arrays.toString(bubblesort(new int[]{7, 6, 5, 4, 3, 2, 1})));
  }
}
