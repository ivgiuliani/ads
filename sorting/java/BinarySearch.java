package sorting.java;

/**
 * Simple implementation of a binary search on a sorted array
 */
public class BinarySearch {
  /**
   * Performs an iterative binary search on the given array.
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

  /**
   * Performs a recursive binary search on the given array.
   * <p>
   * Assume that the array of items is already sorted.
   *
   * @param items input items
   * @param item item to search for
   * @param <T> type of the values in the array
   * @return the searched item's position or -1 if the item has not been found
   */
  public static <T extends Comparable<T>> int binarysearchRecursive(T[] items, T item) {
    return binarysearchRecursive(items, item, 0, items.length - 1);
  }

  public static <T extends Comparable<T>> int binarysearchRecursive(T[] items, T item, int low, int high) {
    if (low > high) return -1;

    int pivot = (low + high) / 2;
    int comparison = item.compareTo(items[pivot]);

    if (comparison == 0) {
      return pivot;
    }
    else if (comparison < 0) return binarysearchRecursive(items, item, low, pivot - 1);
    else if (comparison > 0) return binarysearchRecursive(items, item, pivot + 1, high);

    // unreachable code
    return -1;
  }

  public static class Range {
    public final int left;
    public final int right;
    public Range(int l, int r) {
      left = l;
      right = r;
    }
  }

  /**
   * Search for the indexes range of an item (the index of the first time it
   * appears and that of the last time).
   *
   * @param items  input array
   * @param item   item to search
   * @param <T>    type of items
   * @return an instance of {@link Range}
   */
  public static <T extends Comparable<T>> Range bsRange(T[] items, T item) {
    int left = bsRangeLeft(items, item, 0, items.length - 1);
    int right = bsRangeRight(items, item, 0, items.length - 1);
    if (left >= items.length || items[left] != item) left = -1;
    if (right >= items.length || items[right] != item) right = -1;
    return new Range(left, right);
  }

  private static <T extends Comparable<T>> int bsRangeLeft(T[] items, T item, int low, int high) {
    if (low > high) return low;

    int pivot = low + (high - low) / 2;
    int comparison = items[pivot].compareTo(item);

    if (comparison < 0) return bsRangeLeft(items, item, pivot + 1, high);
    else return bsRangeLeft(items, item, low, pivot - 1);
  }

  private static <T extends Comparable<T>> int bsRangeRight(T[] items, T item, int low, int high) {
    // if you want the right boundary not to be included in the range (i.e.:
    // return 2-5 as a range for items in position 2, 3 and 4) return `low`
    // instead of `high` here.
    if (low > high) return high;

    int pivot = low + (high - low) / 2;
    int comparison = items[pivot].compareTo(item);

    if (comparison > 0) return bsRangeRight(items, item, low, pivot - 1);
    else return bsRangeRight(items, item, pivot + 1, high);
  }

  public static void main(String[] args) {
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 4) == 3);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 2) == 1);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 8) == -1);
    test(binarysearch(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 0) == -1);

    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, "c") == 2);
    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, "g") == -1);
    test(binarysearch(new String[]{"a", "b", "c", "d", "e", "f"}, " ") == -1);

    test(binarysearchRecursive(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 4) == 3);
    test(binarysearchRecursive(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 2) == 1);
    test(binarysearchRecursive(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 8) == -1);
    test(binarysearchRecursive(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 0) == -1);

    test(binarysearchRecursive(new String[]{"a", "b", "c", "d", "e", "f"}, "c") == 2);
    test(binarysearchRecursive(new String[]{"a", "b", "c", "d", "e", "f"}, "g") == -1);
    test(binarysearchRecursive(new String[]{"a", "b", "c", "d", "e", "f"}, " ") == -1);

    test(bsRange(new Integer[] {1, 2, 3, 3, 3, 3, 4, 5, 6}, 3).left == 2);
    test(bsRange(new Integer[] {1, 2, 3, 3, 3, 3, 4, 5, 6}, 3).right == 5);
    test(bsRange(new Integer[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, 1).left == 0);
    test(bsRange(new Integer[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, 1).right == 8);
    test(bsRange(new Integer[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, 2).left == -1);
    test(bsRange(new Integer[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, 2).right == -1);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}