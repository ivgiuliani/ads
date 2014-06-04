package math;

import interviewquestions.TestCase;

/**
 * Greatest common divisor
 */
public class GCD extends TestCase {
  public static int gcd_euclid(int a, int b) {
    assert a >= 0;
    assert b >= 0;

    int tmp;
    while (b != 0) {
      tmp = b;
      b = a % b;
      a = tmp;
    }
    return a;
  }

  // 1-line implementation of GCD
  public static int gcd_euclid_recursive(int a, int b) {
    return b == 0 ? a : gcd_euclid_recursive(b, a % b);
  }

  public static int gcd_knuth(int a, int b) {
    assert a >= 0;
    assert b >= 0;

    if (a == b) return a;
    if (a == 0) return b;
    if (b == 0) return a;

    final boolean aIsEven = (a & 1) == 0;
    final boolean bIsEven = (b & 1) == 0;

    // this is probably the only algorithm I've ever written where it makes
    // sense not to use parenthesis
    if (aIsEven && bIsEven) return gcd_knuth(a >> 1, b >> 1) << 1;
    else if (aIsEven)       return gcd_knuth(a >> 1, b);
    else if (bIsEven)       return gcd_knuth(a, b >> 1);
    else if (a >= b)        return gcd_knuth((a - b) >> 1, b);
    return gcd_knuth((b - a) >> 1, a);
  }

  public static void main(String[] args) {
    assertEquals(4,   gcd_euclid(8, 12));
    assertEquals(2,   gcd_euclid(2, 4));
    assertEquals(1,   gcd_euclid(2, 3));
    assertEquals(1,   gcd_euclid(9, 28));
    assertEquals(6,   gcd_euclid(54, 24));
    assertEquals(6,   gcd_euclid(48, 18));
    assertEquals(100, gcd_euclid(100, 200));
    assertEquals(100, gcd_euclid(100, 0));
    assertEquals(200, gcd_euclid(0, 200));
    assertEquals(0,   gcd_euclid(0, 0));
    assertEquals(34,  gcd_euclid(40902, 24140));

    assertEquals(4,   gcd_euclid_recursive(8, 12));
    assertEquals(2,   gcd_euclid_recursive(2, 4));
    assertEquals(1,   gcd_euclid_recursive(2, 3));
    assertEquals(1,   gcd_euclid_recursive(9, 28));
    assertEquals(6,   gcd_euclid_recursive(54, 24));
    assertEquals(6,   gcd_euclid_recursive(48, 18));
    assertEquals(100, gcd_euclid_recursive(100, 200));
    assertEquals(100, gcd_euclid_recursive(100, 0));
    assertEquals(200, gcd_euclid_recursive(0, 200));
    assertEquals(0,   gcd_euclid_recursive(0, 0));
    assertEquals(34,  gcd_euclid_recursive(40902, 24140));

    assertEquals(4,   gcd_knuth(8, 12));
    assertEquals(2,   gcd_knuth(2, 4));
    assertEquals(1,   gcd_knuth(2, 3));
    assertEquals(1,   gcd_knuth(9, 28));
    assertEquals(6,   gcd_knuth(54, 24));
    assertEquals(6,   gcd_knuth(48, 18));
    assertEquals(100, gcd_knuth(100, 200));
    assertEquals(100, gcd_knuth(100, 0));
    assertEquals(200, gcd_knuth(0, 200));
    assertEquals(0,   gcd_knuth(0, 0));
    assertEquals(34,  gcd_knuth(40902, 24140));
  }
}
