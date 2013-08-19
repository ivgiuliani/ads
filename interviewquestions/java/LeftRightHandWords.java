package interviewquestions.java;

/**
 * You have a list of several million words unsorted.
 * Find the largest and the smallest words that can be typed by a single hand
 * on a qwerty-style keyboard.
 * Following the rules of finger placement, a word can either be typed fully on
 * the left-hand side of the keyboard, the right-hand side, or both.
 * Find the largest and smallest left-hand word(s),
 * and the largest and smallest right-hand word(s).
 *
 * You are given:
 *   - millions of words, unsorted
 *   - set of left-hand chars: a,s,d,f, ...
 *   - set of right-hand chars: j,k,l, ...
 */
public class LeftRightHandWords {
  public static class Result {
    public String largestLeft;
    public String largestRight;
    public String smallestLeft;
    public String smallestRight;
  }

  public static Result search(String left, String right, String[] words) {
    Result res = new Result();

    long smLeft = Long.MAX_VALUE, smRight = Long.MAX_VALUE;
    long lgLeft = Long.MIN_VALUE, lgRight = Long.MIN_VALUE;

    for (String word : words) {
      if (valid(left, word)) {
        if (word.length() < smLeft) {
          smLeft = word.length();
          res.smallestLeft = word;
        } else if (word.length() > lgLeft) {
          lgLeft = word.length();
          res.largestLeft= word;
        }
      } else if (valid(right, word)) {
        if (word.length() < smRight) {
          smRight = word.length();
          res.smallestRight = word;
        } else if (word.length() > lgRight) {
          lgRight = word.length();
          res.largestRight= word;
        }
      }
    }

    return res;
  }

  public static boolean valid(String side, String word) {
    for (Character ch : word.toCharArray()) {
      if (side.indexOf(ch) < 0) return false;
    }
    return true;
  }

  public static void main(String[] args) {
    final String left = "qwerasdfzxcvtgb";
    final String right = "uiopjklmnhy";

    Result res;

    res = search(left, right, new String[] {
        "qwert",
        "rararararararara",
        "asd",
        "asdlkj",
        "iosakbrknerlk",
        "mmmmmm",
        "llllllllllllllllllll",
        "laskdj",
        "llll",
        "qqqqqqqqqqqqqqqqqqqq",
        "p",
    });

    test(res.smallestLeft.equals("asd"));
    test(res.smallestRight.equals("p"));
    test(res.largestLeft.equals("qqqqqqqqqqqqqqqqqqqq"));
    test(res.largestRight.equals("llllllllllllllllllll"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
