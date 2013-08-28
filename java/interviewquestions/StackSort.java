package interviewquestions;

import java.util.*;

/**
 * Write a program to sort a stack in ascending order. You should not make any
 * assumptions about how the stack is implemented.
 * The following are the only functions that
 * should be used to write this program: push | pop | peek | isEmpty.
 */
public class StackSort {
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
    test(sorted.size() == 9);
    test(sorted.get(0) == 1);
    test(sorted.get(1) == 1);
    test(sorted.get(2) == 3);
    test(sorted.get(3) == 4);
    test(sorted.get(4) == 6);
    test(sorted.get(5) == 10);
    test(sorted.get(6) == 32);
    test(sorted.get(7) == 45);
    test(sorted.get(8) == 90);

    test(sort(new ArrayDeque<Integer>()).isEmpty());
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
