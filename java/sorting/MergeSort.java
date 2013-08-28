package sorting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MergeSort {
  public static int[] mergesort(int[] items) {
    return mergesort(items, 0, items.length - 1);
  }

  private static int[] mergesort(int[] items, int start, int end) {
    if (start < end) {
      int middle = (start + end) / 2;
      mergesort(items, start, middle);
      mergesort(items, middle + 1, end);
      merge(items, start, middle, end);
    }

    return items;
  }

  private static void merge(int[] items, int start, int middle, int end) {
    int i;
    Queue<Integer> q1 = new LinkedList<Integer>();
    Queue<Integer> q2 = new LinkedList<Integer>();

    for (i = start; i <= middle; i++) q1.add(items[i]);
    for (i = middle + 1; i <= end; i++) q2.add(items[i]);

    i = start;
    while (!(q1.isEmpty() || q2.isEmpty())) {
      if (q1.peek() <= q2.peek())
        items[i] = q1.poll();
      else
        items[i] = q2.poll();

      i++;
    }

    while (!q1.isEmpty()) items[i++] = q1.poll();
    while (!q2.isEmpty()) items[i++] = q2.poll();
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(mergesort(new int[]{1, 2, 3, 4, 5, 6, 7})));
    System.out.println(Arrays.toString(mergesort(new int[]{5, 1, 6, 3, 4, 2, 7})));
    System.out.println(Arrays.toString(mergesort(new int[]{7, 6, 5, 4, 3, 2, 1})));
  }
}
