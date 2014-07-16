package interviewquestions;

import java.util.*;

/**
 * Write a program to sort a stack in ascending order. You should not make any
 * assumptions about how the stack is implemented.
 * The following are the only functions that
 * should be used to write this program: push | pop | peek | isEmpty.
 */
public class StackSort extends TestCase {
  public static List<Integer> sort(Deque<Integer> stack) {
    List<Integer> sorted = new ArrayList<Integer>();
    Deque<Integer> s2 = new ArrayDeque<Integer>();
    int tmp;

    while (!stack.isEmpty()) {
      tmp = stack.pop();
      while (!s2.isEmpty() && s2.peek() < tmp) {
        stack.push(s2.pop());
      }
      s2.push(tmp);
    }

    for (Integer item : s2) sorted.add(item);
    return sorted;
  }

  public static void main(String[] args) {
    Deque<Integer> stack = new ArrayDeque<Integer>();
    stack.push(3);
    stack.push(4);
    stack.push(1);
    stack.push(6);
    stack.push(10);
    stack.push(32);
    stack.push(90);
    stack.push(45);
    stack.push(1);

    List<Integer> sorted = sort(stack);
    assertEquals(9, sorted.size());
    assertEquals(1, (int) sorted.get(0));
    assertEquals(1, (int) sorted.get(1));
    assertEquals(3, (int) sorted.get(2));
    assertEquals(4, (int) sorted.get(3));
    assertEquals(6, (int) sorted.get(4));
    assertEquals(10, (int) sorted.get(5));
    assertEquals(32, (int) sorted.get(6));
    assertEquals(45, (int) sorted.get(7));
    assertEquals(90, (int) sorted.get(8));

    assertTrue(sort(new ArrayDeque<Integer>()).isEmpty());
  }
}
