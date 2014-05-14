package interviewquestions;

import java.util.Iterator;

/**
 * A layman version of junit (just because I don't want to include a dependency)
 */
public abstract class TestCase {
  public static void assertTrue(boolean condition) {
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }

  public static void assertFalse(boolean condition) {
    if (condition) {
      throw new AssertionError("invalid test");
    }
  }

  public static void assertNull(Object o) {
    if (o != null) {
      throw new AssertionError(String.format("%s is not null", o));
    }
  }

  public static void assertNotNull(Object o) {
    if (o == null) {
      throw new AssertionError("object is null");
    }
  }

  public static void assertEquals(int i1, int i2) {
    if (i1 != i2) {
      throw new AssertionError(String.format("%d != %d", i1, i2));
    }
  }

  public static void assertEquals(long i1, long i2) {
    if (i1 != i2) {
      throw new AssertionError(String.format("%d != %d", i1, i2));
    }
  }

  public static void assertEquals(float i1, float i2) {
    if (i1 != i2) {
      throw new AssertionError(String.format("%.5f != %.5f", i1, i2));
    }
  }

  public static void assertEquals(double i1, double i2) {
    if (i1 != i2) {
      throw new AssertionError(String.format("%.5f != %.5f", i1, i2));
    }
  }

  public static <E> void assertEquals(Iterable<E> i1, Iterable<E> i2) {
    Iterator<E> it1 = i1.iterator();
    Iterator<E> it2 = i2.iterator();

    while (it1.hasNext() && it2.hasNext()) {
      E e1 = it1.next();
      E e2 = it2.next();

      assertTrue(e1.equals(e2));
    }

    assertTrue(!it1.hasNext());
    assertTrue(!it2.hasNext());
  }

  public static void assertEquals(String s1, String s2) {
    if (!s1.equals(s2)) {
      throw new AssertionError(String.format("%s != %s", s1, s2));
    }
  }

  public static void assertEquals(Object o1, Object o2) {
    if (o1 != o2) {
      throw new AssertionError("different objects");
    }
  }
}
