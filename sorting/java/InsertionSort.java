package sorting.java;

import java.util.Arrays;

/**
 * Simple implementation of insertion sort in java
 */
public class InsertionSort {
  public static <T extends Comparable<T>> T[] insertionsort(T[] items) {
    int size = items.length;
    int i, j;
    T tmp;

    for (i = 1; i < size; i++) {
      j = i;

      while (j > 0 && (items[j].compareTo(items[j - 1]) < 0)) {
        tmp = items[j - 1];
        items[j - 1] = items[j];
        items[j] = tmp;
        j--;
      }
    }

    return items;
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(insertionsort(new Integer[]{1, 2, 3, 4, 5, 6, 7})));
    System.out.println(Arrays.toString(insertionsort(new Integer[]{5, 1, 6, 3, 4, 2, 7})));
    System.out.println(Arrays.toString(insertionsort(new Integer[]{7, 6, 5, 4, 3, 2, 1})));

    System.out.println(Arrays.toString(insertionsort(new String[]{"a", "b", "c", "d", "e"})));
    System.out.println(Arrays.toString(insertionsort(new String[]{"b", "c", "a", "e", "d"})));
    System.out.println(Arrays.toString(insertionsort(new String[]{"e", "d", "c", "b", "a"})));
  }
}