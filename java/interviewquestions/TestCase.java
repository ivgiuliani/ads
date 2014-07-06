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

  public static void assertEquals(char i1, char i2) {
    if (i1 != i2) {
      throw new AssertionError(String.format("%c != %c", i1, i2));
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

      assertEquals(e1, e2);
    }

    assertTrue(!it1.hasNext());
    assertTrue(!it2.hasNext());
  }

  public static void assertEquals(CharSequence s1, CharSequence s2) {
    if (!s1.equals(s2)) {
      throw new AssertionError(String.format("%s != %s", s1, s2));
    }
  }

  public static void assertEquals(int[] o1, int[] o2) {
    if (o1.length == o2.length) {
      for (int i = 0; i < o1.length; i++) {
        if (o1[i] != o2[i]) {
          throw new AssertionError(String.format(
              "different objects at item %d (%d != %d)", i, o1[i], o2[i]));
        }
      }
    } else {
      throw new AssertionError("different objects");
    }
  }

  public static void assertEquals(char[] o1, char[] o2) {
    if (o1.length == o2.length) {
      for (int i = 0; i < o1.length; i++) {
        if (o1[i] != o2[i]) {
          throw new AssertionError(String.format(
              "different objects at item %d (%c != %c)", i, o1[i], o2[i]));
        }
      }
    } else {
      throw new AssertionError("different objects");
    }
  }

  public static void assertEquals(long[] o1, long[] o2) {
    if (o1.length == o2.length) {
      for (int i = 0; i < o1.length; i++) {
        if (o1[i] != o2[i]) {
          throw new AssertionError("different objects at item " + i);
        }
      }
    } else {
      throw new AssertionError("different objects");
    }
  }

  public static void assertEquals(double[] o1, double[] o2) {
    if (o1.length == o2.length) {
      for (int i = 0; i < o1.length; i++) {
        if (o1[i] != o2[i]) {
          throw new AssertionError("different objects at item " + i);
        }
      }
    } else {
      throw new AssertionError("different objects");
    }
  }

  public static <E> void assertEquals(E[] o1, E[] o2) {
    if (o1.length == o2.length) {
      for (int i = 0; i < o1.length; i++) {
        if (!o1[i].equals(o2[i])) {
          throw new AssertionError(String.format(
              "different objects at item %d; expected=%s found=%s",
              i, o1[i], o2[i]
          ));
        }
      }
    } else {
      throw new AssertionError("different objects");
    }
  }

  public static void assertEquals(Object o1, Object o2) {
    if (!o1.equals(o2)) {
      throw new AssertionError("different objects: " + o1.toString() + " != " + o2.toString());
    }
  }
}
