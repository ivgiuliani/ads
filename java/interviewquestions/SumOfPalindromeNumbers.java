package interviewquestions;

/**
 * Choose a number, reverse its digits and add it to the original. If the sum
 * is not a palindrome (which means, it is not the same number from left to
 * right and right to left), repeat this procedure. E.g.
 *
 *   195 (initial number) + 591 (reverse of initial number) = 786
 *   786 + 687 = 1473
 *   1473 + 3741 = 5214
 *   5214 + 4125 = 9339 (palindrome)
 *
 * In this particular case the palindrome 9339 appeared after the 4th addition.
 * This method leads to palindromes in a few step for almost all of the integers.
 * But there are interesting exceptions. 196 is the first number for which no
 * palindrome has been found. It is not proven though, that there is no
 * such a palindrome.
 */
public class SumOfPalindromeNumbers extends TestCase {
  public static class Result {
    public int iterations;
    public int value;
  }

  public static Result palindromeSum(int number) {
    Result res = new Result();
    int iterations = 0;
    int sum = number;
    String ssum = String.valueOf(sum);
    String reversed = new StringBuilder(ssum).reverse().toString();

    while (!ssum.equals(reversed)) {
      sum += Integer.parseInt(reversed);
      ssum = String.valueOf(sum);
      reversed = new StringBuilder(ssum).reverse().toString();
      iterations++;
    }

    res.value = sum;
    res.iterations = iterations;

    return res;
  }

  public static void main(String[] args) {
    assertEquals(4, palindromeSum(195).iterations);
    assertEquals(9339, palindromeSum(195).value);
    assertEquals(1, palindromeSum(12345).iterations);
    assertEquals(66666, palindromeSum(12345).value);
    assertEquals(9, palindromeSum(44481).iterations);
    assertEquals(144222441, palindromeSum(44481).value);
    assertEquals(1, palindromeSum(25).iterations);
    assertEquals(77, palindromeSum(25).value);
    assertEquals(0, palindromeSum(99).iterations);
    assertEquals(99, palindromeSum(99).value);
  }
}
