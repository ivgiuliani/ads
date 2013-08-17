package interviewquestions.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Return all subsets of a set.
 */
public class Powersets {
  public static <T> Set<Set<T>> powerset(Set<T> set) {
    Set<Set<T>> pset = new HashSet<Set<T>>();
    Set<T> tmp;

    pset.add(set);
    for (T item : set) {
      tmp = new HashSet<T>(set);
      tmp.remove(item);
      pset.addAll(powerset(tmp));
    }

    return pset;
  }

  public static void main(String[] args) {
    Set<Integer> initial = new HashSet<Integer>(Arrays.asList(1, 2, 3));
    Set<Set<Integer>> powerset = powerset(initial);

    test(powerset.size() == 8);
    test(powerset.contains(new HashSet<Integer>()));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(1))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(2))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(3))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 2))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 3))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(2, 3))));
    test(powerset.contains(new HashSet<Integer>(Arrays.asList(1, 2, 3))));

    // the powerset is never empty, it always contains at least the empty set
    initial = new HashSet<Integer>();
    test(powerset(initial).size() == 1);
    test(powerset(initial).contains(initial));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
