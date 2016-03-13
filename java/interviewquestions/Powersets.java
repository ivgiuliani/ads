package interviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Return all subsets of a set.
 */
public class Powersets extends TestCase {
  public static <T> Set<Set<T>> powerset(Set<T> set) {
    Set<Set<T>> pset = new HashSet<>();

    // The powerset of a set S is formed by S itself and the powersets we can
    // build by removing one item at the time from S.
    pset.add(set);

    for (T item : set) {
      Set<T> tmp = new HashSet<>(set);
      tmp.remove(item);

      pset.addAll(powerset(tmp));
    }

    return pset;
  }

  public static void main(String[] args) {
    Set<Integer> initial = new HashSet<Integer>(Arrays.asList(1, 2, 3));
    Set<Set<Integer>> powerset = powerset(initial);

    assertEquals(8, powerset.size());
    assertTrue(powerset.contains(new HashSet<Integer>()));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(1))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(2))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(3))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 2))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 3))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(2, 3))));
    assertTrue(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 2, 3))));

    // the powerset is never empty, it always contains at least the empty set
    initial = new HashSet<Integer>();
    assertEquals(1, powerset(initial).size());
    assertTrue(powerset(initial).contains(initial));
  }
}
