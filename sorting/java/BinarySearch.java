package sorting.java;

/**
 * Simple implementation of a binary search on a sorted array
 */
public class BinarySearch {
  /**
   * Performs a binary search on the given array.
   * <p>
   * Assume that the array of items is already sorted.
   *
   * @param items input items
   * @param item item to search for
   * @param <T> type of the values in the array
   * @return the searched item's position or -1 if the item has not been found
   */
  public static <T extends Comparable<T>> int binarysearch(T[] items, T item) {
    int start = 0, end = items.length - 1;
    int pivot;

    while (start <= end) {
      pivot = (start + end) / 2;
      int comparison = items[pivot].compareTo(item);

      if (comparison == 0) {
        return pivot;
      } else if (comparison < 0) {
        start = pivot + 1;
      } else {
        end = pivot - 1;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 4) == 3);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 2) == 1);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 8) == -1);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 0) == -1);

    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, "c") == 2);
    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, "g") == -1);
    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, " ") == -1);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
