package interviewquestions;

import java.util.Iterator;

/**
 * A layman version of junit (just because I don't want to include a dependency)
 */
public abstract class TestCase {
  public static void assertTrue(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
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
}
