package interviewquestions.java;

import java.util.*;

/**
 * Given two sorted lists of numbers (respectively of size n and m),
 * return the list of items in common by the two lists.
 * Note that the lists can contain duplicates, in which case return as many
 * pairs as you find.
 * For example:
 *   l1 = 1, 2, 3, 3, 4
 *   l2 = 1, 3, 3, 5
 *   return = 1, 3, 3
 */
public class ListIntersection {
  /*
   * This solution is O(n+m) in time and O(1) in space
   */
  public static List<Integer> common(List<Integer> l1, List<Integer> l2) {
    List<Integer> res = new ArrayList<Integer>();
    if (l1.isEmpty() || l2.isEmpty()) return res;

    Iterator<Integer> i1 = l1.iterator();
    Iterator<Integer> i2 = l2.iterator();

    int val1 = i1.next(), val2 = i2.next();

    while (true) {
      try {
        if (val1 == val2) {
          res.add(val1); // equivalent to .add(val2)
          val1 = i1.next();
          val2 = i2.next();
        } else if (val1 < val2) {
          val1 = i1.next();
        } else if (val2 < val1) {
          val2 = i2.next();
        }
      } catch (NoSuchElementException ex) {
        // we could check for .hasNext() on every .next(), but this leads
        // to cluttered code while this approach (exception handling) is
        // much cleaner although possibly a bit heavier on the memory side
        break;
      }
    }

    return res;
  }

  public static void main(String[] args) {
    test(common(Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5)).equals(Arrays.asList(3)));
    test(common(Arrays.asList(1, 5, 7), Arrays.asList(2, 4, 6)).equals(Arrays.asList()));
    test(common(Arrays.asList(10, 100, 1000), Arrays.asList(1, 10, 100)).equals(Arrays.asList(10, 100)));
    test(common(Arrays.asList(1, 2, 3, 3, 4), Arrays.asList(1, 3, 3, 5)).equals(Arrays.asList(1, 3, 3)));
    test(common(Arrays.asList(1, 2, 3, 3, 4), Arrays.asList(1, 3, 3)).equals(Arrays.asList(1, 3, 3)));
    test(common(Arrays.asList(5, 5, 5, 5, 5), Arrays.asList(5, 5, 5, 5, 5)).equals(Arrays.asList(5, 5, 5, 5, 5)));
    test(common(Arrays.asList(5, 5, 5, 5), Arrays.asList(5, 5, 5, 5, 5)).equals(Arrays.asList(5, 5, 5, 5)));
    test(common(Arrays.asList(5, 5, 5, 5, 5), Arrays.asList(5, 5, 5, 5)).equals(Arrays.asList(5, 5, 5, 5)));
    test(common(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)).equals(
                Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10)));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
