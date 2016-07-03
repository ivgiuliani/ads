package interviewquestions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Implement an iterator that iterates each item of different lists sequentially.
 * For example, given the following three lists:
 *
 * a = 1, 2, 3, 4
 * b = 'a', 'b'
 * c = "hello world"
 *
 * It would return the following sequence:
 * - 1, 'a', "hello world", 2, 'b', 3, 4
 */
public class CyclicIterator extends TestCase {
  private static final List<Integer> l1 = Arrays.asList(1, 2, 3, 4);
  private static final List<Character> l2 = Arrays.asList('a', 'b');
  private static final List<String> l3 = Arrays.asList("hello world");

  private static class CyclicIt implements Iterator<Object> {
    private Iterator<?>[] lists;
    private int current;

    CyclicIt(Iterator<?> ... lists) {
      this.lists = lists;
      current = 0;

      if (!lists[current].hasNext()) {
        if (!updatePosition()) {
          current = -1;
        }
      }
    }

    @Override
    public boolean hasNext() {
      return current != -1 && lists[current].hasNext();
    }

    @Override
    public Object next() {
      if (current == -1) {
        throw new IndexOutOfBoundsException();
      }

      Object v = lists[current].next();
      if (!updatePosition()) {
        current = -1;
      }
      return v;
    }

    private boolean updatePosition() {
      for (int i = 0; i < lists.length; i++) {
        current = (current + 1) % lists.length;
        if (lists[current].hasNext()) {
          return true;
        }
      }

      return false;
    }
  }

  public static void main(String[] args) {
    CyclicIt it = new CyclicIt(l1.iterator(), l2.iterator(), l3.iterator());

    assertTrue(it.hasNext());
    assertTrue(it.hasNext());
    assertTrue(it.hasNext());
    assertTrue(it.hasNext());

    assertEquals(1, it.next());
    assertTrue(it.hasNext());

    assertEquals('a', it.next());
    assertTrue(it.hasNext());

    assertEquals("hello world", it.next());
    assertTrue(it.hasNext());

    assertEquals(2, it.next());
    assertTrue(it.hasNext());

    assertEquals('b', it.next());
    assertTrue(it.hasNext());

    assertEquals(3, it.next());
    assertTrue(it.hasNext());

    assertEquals(4, it.next());
    assertFalse(it.hasNext());

    it = new CyclicIt(Collections.<Integer>emptyIterator(), l2.iterator());
    assertTrue(it.hasNext());

    assertEquals('a', it.next());
    assertTrue(it.hasNext());

    assertEquals('b', it.next());
    assertFalse(it.hasNext());
  }
}
