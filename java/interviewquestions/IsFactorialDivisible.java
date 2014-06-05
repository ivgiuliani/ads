package interviewquestions;

import math.GCD;

/**
 * Implement bool isFactorialDivisible(int x, int y)
 * Return true if x! is divisible by y, else return false
 */
public class IsFactorialDivisible extends TestCase {
  public static boolean isFactorialDivisible(int x, int y) {
    // Take the following example:
    // fact(6) == 6 * 5 * 4 * 3 * 2 * 1 == 144
    // 16 = 2 * 2 * 2 * 2
    // fact(6) == (2 * 3) * 5 * (2 * 2) * 3 * 2 * 1
    //            ^^^^^^^
    //            GCD(6, 16)
    // Note the number of 2s in the factorization is equal to that of 16.
    // Essentially this boils down to repeatedly calculate the GCD of y with
    // the "current" x (gcd(4, 16) == gcd(2*2, 16) == 4 == 2*2, so if we
    // divide y by that and y ever reaches 1 we know it's divisible (there's
    // no remainder if y == 1).

    while (x > 0 && y > 1) {
      y /= GCD.gcd_knuth(x, y);
      x--;
    }

    return y == 1;
  }

  public static void main(String[] args) {
    assertTrue(isFactorialDivisible(6, 1));
    assertTrue(isFactorialDivisible(6, 2));
    assertTrue(isFactorialDivisible(6, 3));
    assertTrue(isFactorialDivisible(6, 4));
    assertTrue(isFactorialDivisible(6, 6));
    assertTrue(isFactorialDivisible(6, 16));
    assertTrue(isFactorialDivisible(1, 1));
    assertTrue(isFactorialDivisible(2, 2));
    assertTrue(isFactorialDivisible(10, 3));
    assertTrue(isFactorialDivisible(10, 9));
    assertTrue(isFactorialDivisible(10, 12));
    assertTrue(isFactorialDivisible(10, 14));
    assertTrue(isFactorialDivisible(20, 30));
    assertTrue(isFactorialDivisible(20, 40));
    assertTrue(isFactorialDivisible(20, 45));
    assertTrue(isFactorialDivisible(20, 13));
    assertTrue(isFactorialDivisible(20, 90));
  }
}
