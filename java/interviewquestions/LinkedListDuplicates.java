package interviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Remove duplicates from an unsorted linked list
 */
public class LinkedListDuplicates extends TestCase {
  // we assume linkedlist everywhere here (this is what the question asks anyway)
  // it's easy to do the same with array lists but some things are fairly contrived
  // because you need to shift elements, which unnecessarily complicates the question
  // (you can't use iterator.remove() for example)

  /** Creates a new list without duplicates */
  public static <E> List<E> removeDuplicates1(LinkedList<E> input) {
    Set<E> set = new HashSet<E>();
    List<E> newList = new ArrayList<E>(input.size());
    for (E e : input) {
      if (!set.contains(e)) {
        newList.add(e);
        set.add(e);
      }
    }
    return newList;
  }

  /** Removes duplicates in place */
  public static <E> void removeDuplicates2(LinkedList<E> input) {
    Set<E> set = new HashSet<E>();
    Iterator<E> it = input.iterator();

    while (it.hasNext()) {
      E e = it.next();

      if (set.contains(e)) {
        it.remove();
      } else {
        set.add(e);
      }
    }
  }

  /** Removes items without using an additional buffer */
  public static <E> void removeDuplicates3(LinkedList<E> input) {
    Iterator<E> it = input.iterator();
    Iterator<E> duplicates;

    int items = 0;
    while (it.hasNext()) {
      E e = it.next();

      boolean dup = false;
      int i = 0;
      duplicates = input.iterator();
      while (i < items && !dup) {
        E duplicateCheck = duplicates.next();
        if (e.equals(duplicateCheck)) {
          dup = true;
        }
        i++;
      }

      if (dup) {
        it.remove();
      } else {
        items++;
      }
    }
  }

  static <E> LinkedList<E> list(E ... i) {
    return new LinkedList<E>(Arrays.asList(i));
  }

  public static void main(String[] args) {
    assertEquals(Arrays.asList(1, 2, 3), removeDuplicates1(list(1, 2, 3)));
    assertEquals(Arrays.asList(1, 2, 3), removeDuplicates1(list(1, 2, 3, 1, 2, 3)));
    assertEquals(Arrays.asList(1, 2, 3), removeDuplicates1(list(1, 1, 2, 2, 3, 3)));
    assertEquals(Arrays.asList(), removeDuplicates1(list()));

    LinkedList<Integer> lst;

    lst = list(1, 2, 3);
    removeDuplicates2(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list(1, 2, 3, 1, 2, 3);
    removeDuplicates2(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list(1, 1, 2, 2, 3, 3);
    removeDuplicates2(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list();
    removeDuplicates2(lst);
    assertEquals(new ArrayList<Integer>(), lst);

    lst = list(1, 2, 3);
    removeDuplicates3(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list(1, 2, 3, 1, 2, 3);
    removeDuplicates3(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list(1, 1, 2, 2, 3, 3);
    removeDuplicates3(lst);
    assertEquals(Arrays.asList(1, 2, 3), lst);

    lst = list();
    removeDuplicates3(lst);
    assertEquals(new ArrayList<Integer>(), lst);
  }
}
