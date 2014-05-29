package interviewquestions;

/**
 * Given a number, reverse it (without converting it to a string).
 * Example:
 *   639 -> 936
 *   12345 -> 54321
 *   1 -> 1
 */
public class ReverseNumber extends TestCase {
  private static int base(int num) {
    int base = 1;
    while (num > 10) {
      num /= 10;
      base *= 10;
    }
    return base;
  }

  public static int reverse(int num) {
    int orig_base = base(num);
    int reversed = 0;
    int base = 1;

    while (num > 0) {
      reversed += (num / orig_base) * base;
      num = num % orig_base;
      orig_base /= 10;
      base *= 10;
    }
    return reversed;
  }

  public static void main(String[] args) {
    assertEquals(936, reverse(639));
    assertEquals(12345, reverse(54321));
    assertEquals(54321, reverse(12345));
    assertEquals(1, reverse(1));
    assertEquals(0, reverse(0));
    assertEquals(8888, reverse(8888));
  }
}
