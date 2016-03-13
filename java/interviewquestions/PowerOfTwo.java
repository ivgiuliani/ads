package interviewquestions;

import java.util.Arrays;

/**
 * Determine if a number is a power of two.
 */
public class PowerOfTwo extends TestCase {
  private static final int[] POWERS_OF_TWO = new int[] {
      0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
      1024, 2048, 4096, 8192, 16384, 32768, 65536
  };

  public static boolean isPowerOfTwo(int number) {
    // If a number is a power of two then its binary representation will have a
    // single 1:
    // 00010000 = 16
    // If we remove 1 from the number then all the digits left of the 1 will
    // stay 0, those right of the 1 will become 1:
    // 00001111 = 15
    // If we AND the numbers together we get 0 because they won't have any single
    // 1 in common. If the number is not a power of two, then at least a 1 will be
    // shared, causing the AND to return (at least) that number.

    return (number & (number - 1)) == 0;
  }

  public static void main(String[] args) {
    for (int i = 0; i < POWERS_OF_TWO[POWERS_OF_TWO.length - 1]; i++) {
      if (Arrays.binarySearch(POWERS_OF_TWO, i) >= 0) {
        assertTrue(isPowerOfTwo(i));
      } else {
        assertFalse(isPowerOfTwo(i));
      }
    }
  }
}
