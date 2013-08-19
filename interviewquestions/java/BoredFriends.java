package interviewquestions.java;

import java.util.BitSet;

/**
 * John and Brus are bored. They have n+m common friends. The first n of them
 * are bored and other m are not. John chooses the j-th (1-based) friend for a
 * talk. If the friend is not bored, he becomes bored after the talk.
 * Brus does the same with the b-th (1-based) friend. Note that John and Brus
 * can't choose the same friend.
 * You have to find the number of bored friends after the talks.
 *
 * SRM488/DIV2
 */
public class BoredFriends {
  public int find(int n, int m, int j, int b) {
    int bored = n;
    BitSet bs = new BitSet(n + m);
    bs.set(0, n);
    bs.set(j - 1);
    bs.set(b - 1);

    for (int i = n; i < (n + m); i++) {
      if (bs.get(i)) bored++;
    }

    return bored;
  }

  public static void main(String[] args) {
    BoredFriends f = new BoredFriends();

    test(f.find(1, 1, 1, 2) == 2);
    test(f.find(2, 1, 1, 2) == 2);
    test(f.find(1, 2, 3, 2) == 3);
    test(f.find(4, 7, 7, 4) == 5);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}