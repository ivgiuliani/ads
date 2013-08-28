package interviewquestions.java;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * We have four banknotes with values b1, b2, b3 and b4 (some of the values may be equal).
 * Nick wants to know if it's possible to buy a single lottery ticket without getting any
 * change back. In other words, he wants to pay the exact price of a ticket using any
 * subset of his banknotes.
 * Return true if it is possible or false if it is not.
 */
public class LotteryTicket {
  public boolean buy(int price, int b1, int b2, int b3, int b4) {
    assert(price >= 1 && price <= 4000);
    assert(b1 >= 1 && b1 <= 1000);
    assert(b2 >= 1 && b2 <= 1000);
    assert(b3 >= 1 && b3 <= 1000);
    assert(b4 >= 1 && b4 <= 1000);

    List<Integer> start = new ArrayList<Integer>(Arrays.asList(b1, b2, b3, b4));
    return hasExactPrice(start, price);
  }

  private boolean hasExactPrice(List<Integer> items, int price) {
    if (isExactSum(items, price)) return true;

    List<Integer> tmp;
    for (int i = 0; i < items.size(); i++) {
      tmp = new ArrayList<Integer>(items);
      tmp.remove(i);
      if (hasExactPrice(tmp, price)) return true;
    }

    return false;
  }

  private boolean isExactSum(Collection<Integer> set, int total) {
    int sum = 0;
    for (int item : set) sum += item;
    return sum == total;
  }

  public static void main(String[] args) {
    LotteryTicket lt = new LotteryTicket();

    test(lt.buy(10, 1, 5, 10, 50));
    test(lt.buy(15, 1, 5, 10, 50));
    test(lt.buy(65, 1, 5, 10, 50));
    test(lt.buy(66, 1, 5, 10, 50));
    test(!lt.buy(1000, 999, 998, 997, 996));
    test(lt.buy(20, 5, 5, 5, 5));
    test(!lt.buy(2, 1, 5, 10, 50));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}