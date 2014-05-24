package interviewquestions;

import java.util.Arrays;

/**
 * Given a number, insert into the correct position in an already
 * sorted array
 */
public class IntoSortedArray extends TestCase {
  public static void add(int[] items, int usedCount, int item) {
    int start = 0, end = usedCount;
    int middle = 0;

    while (start <= end) {
      middle = (start + end) / 2;
      if (items[middle] == item) {
        break;
      } else if (items[middle] < item) {
        start = middle + 1;
      } else {
        // items[middle] > item
        end = middle - 1;
      }
    }

    // shift everything to the right
    for (int i = usedCount; i > middle; i--) {
      swap(items, i, i - 1);
    }
    items[middle] = item;
  }

  private static void swap(int[] items, int x, int y) {
    int tmp;
    tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }

  public static void main(String[] args) {
    int[] items = new int[1024];

    add(items, 0, 100);
    add(items, 1, 50);
    add(items, 2, 150);
    add(items, 3, 1);
    add(items, 4, 2);
    add(items, 5, 2);
    add(items, 6, 99);
    add(items, 7, 98);
    add(items, 8, 1);

    System.out.println(Arrays.toString(Arrays.copyOf(items, 9)));
    assertEquals(1, items[0]);
    assertEquals(1, items[1]);
    assertEquals(2, items[2]);
    assertEquals(2, items[3]);
    assertEquals(50, items[4]);
    assertEquals(98, items[5]);
    assertEquals(99, items[6]);
    assertEquals(100, items[7]);
    assertEquals(150, items[8]);

    items = new int[1024];
    for (int i = 0; i < 100; i++) {
      add(items, i, 100 - i);
    }
    for (int i = 0; i < 100; i++) {
      assertEquals(i + 1, items[i]);
    }
  }
}
