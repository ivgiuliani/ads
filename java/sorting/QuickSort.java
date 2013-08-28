package sorting;

import java.util.Arrays;

public class QuickSort {
  /**
   * Recursive implementation of quicksort
   * <p>
   * Note: this updates the array in place
   *
   * @param items array of items to sort
   * @return a sorted array of items
   */
  public static int[] quicksort(int[] items) {
    quicksort(items, 0, items.length - 1);
    return items;
  }

  private static void quicksort(int[] items, int low, int high) {
    if (high < low) return;

    int p = partition(items, low, high);
    quicksort(items, low, p - 1);
    quicksort(items, p + 1, high);
  }

  private static int partition(int[] items, int low, int high) {
    int pivot = items[high]; // high == pivotIdx
    int firstHigh = low;
    int i;

    for (i = low; i < high; i++) {
      if (items[i] < pivot) {
        swap(items, i, firstHigh);
        firstHigh++;
      }
    }
    swap(items, high, firstHigh);

    return high;
  }

  private static void swap(int[] items, int x, int y) {
    int tmp;
    tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(quicksort(new int[]{1, 2, 3, 4, 5, 6, 7})));
    System.out.println(Arrays.toString(quicksort(new int[]{5, 1, 6, 3, 4, 2, 7})));
    System.out.println(Arrays.toString(quicksort(new int[]{7, 6, 5, 4, 3, 2, 1})));
  }
}
