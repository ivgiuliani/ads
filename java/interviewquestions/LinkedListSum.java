package interviewquestions;

/**
 * You have two numbers represented by a linked list, where each node contains
 * a single digit. The digits are stored in reverse order, such that the 1’s digit
 * is at the head of the list.
 *
 * Write a function that adds the two numbers and returns the sum as a linked
 * list.
 * EXAMPLE
 *   Input: (3 -> 1 -> 5) + (5 -> 9 -> 2) (these are equivalent to 513+295)
 *   Output: 8 -> 0 -> 8
 */
public class LinkedListSum extends TestCase {
  private static class LinkedNode {
    public int value;
    public LinkedNode next = null;

    public LinkedNode(int value) {
      this.value = value;
    }

    public LinkedNode(int value, LinkedNode next) {
      this.value = value;
      this.next = next;
    }
  }

  public static LinkedNode sum(LinkedNode l1, LinkedNode l2) {
    return sum(l1, l2, 0, null);
  }

  private static LinkedNode sum(LinkedNode l1, LinkedNode l2, int carry, LinkedNode result) {
    LinkedNode l1next = null, l2next = null;
    int val = carry;

    if (l1 != null) {
      val += l1.value;
      l1next = l1.next;
    }
    if (l2 != null) {
      val += l2.value;
      l2next = l2.next;
    }

    if (val >= 10) {
      carry = 1;
      val = val - 10;
    }

    result = new LinkedNode(val);
    if (l1next != null || l2next != null) {
      result.next = sum(l1next, l2next, carry, result);
    }

    return result;
  }

  public static void main(String[] args) {
    LinkedNode l1 = new LinkedNode(3, new LinkedNode(1, new LinkedNode(5)));
    LinkedNode l2 = new LinkedNode(5, new LinkedNode(9, new LinkedNode(2)));

    LinkedNode sum = sum(l1, l2);
    assertEquals(8, sum.value);
    assertEquals(0, sum.next.value);
    assertEquals(8, sum.next.next.value);

    l1 = new LinkedNode(1, new LinkedNode(1, new LinkedNode(1)));
    l2 = new LinkedNode(2, new LinkedNode(2, new LinkedNode(2)));
    sum = sum(l1, l2);
    assertEquals(3, sum.value);
    assertEquals(3, sum.next.value);
    assertEquals(3, sum.next.next.value);

    l1 = new LinkedNode(5, new LinkedNode(6, new LinkedNode(3)));
    l2 = new LinkedNode(8, new LinkedNode(4, new LinkedNode(2)));
    sum = sum(l1, l2);
    assertEquals(3, sum.value);
    assertEquals(1, sum.next.value);
    assertEquals(6, sum.next.next.value);

    l1 = new LinkedNode(1, new LinkedNode(1, new LinkedNode(1)));
    l2 = new LinkedNode(9, new LinkedNode(9, new LinkedNode(9)));
    sum = sum(l1, l2);
    assertEquals(0, sum.value);
    assertEquals(1, sum.next.value);
    assertEquals(1, sum.next.next.value);
    assertEquals(1, sum.next.next.value);
  }
}
