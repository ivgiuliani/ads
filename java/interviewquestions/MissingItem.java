package interviewquestions.java;

/**
 * There is an array of strictly positive integers. A second array is formed by
 * shuffling the elements of the first array and deleting a random element.
 * Given these two arrays, find which element is missing in the second array.
 */
public class MissingItem {
  public static int missingitem(int[] array1, int[] array2) {
    // either a sum() or a xor() of all the items will lead to the
    // same solution given that all the items are > 0, however
    // the sum can potentially lead to an integer overflow
    int missing = array1[0];
    for (int i = 1; i < array1.length; i++) {
      missing ^= array1[i];
    }
    for (int i = 0; i < array2.length; i++) {
      missing ^= array2[i];
    }
    return missing;
  }

  public static void main(String[] args) {
    test(missingitem(new int[] { 1, 2, 3, 4, 5, 6 },
                     new int[] { 6, 5, 4, 3, 2    }) == 1);
    test(missingitem(new int[] { 1, 2, 3, 4, 5, 6 },
                     new int[] { 1, 4, 3,    5, 2 }) == 6);
    test(missingitem(new int[] { 1, 2, 3, 4, 5, 6 },
                     new int[] { 3, 2, 1,    6, 5 }) == 4);
    test(missingitem(new int[] { 1, 1, 2, 2, 3, 3 },
                     new int[] { 3, 3, 2,    1, 1 }) == 2);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
