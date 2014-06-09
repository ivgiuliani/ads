package interviewquestions;

import java.util.ArrayDeque;

/**
 * Design a stack which, in addition to push and pop, also has a function
 * min which returns the minimum element.
 * Push, pop and min should all operate in O(1) time.
 */
public class MinStack extends TestCase {
  private ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
  private ArrayDeque<Integer> mins = new ArrayDeque<Integer>();

  public void push(int val) {
    /* Use two stacks to keep track of the min value.
     * Alternatively we could use a single stack but pushing a StackValue
     * custom object that includes also informations on what's the min
     * value up to that point
     */

    stack.push(val);

    if (mins.isEmpty()) {
      mins.push(val);
    } else {
      if (val < mins.peek()) {
        mins.push(val);
      } else {
        mins.push(mins.peek());
      }
    }
  }

  public int pop() {
    mins.pop();
    return stack.pop();
  }

  public int min() {
    return mins.peek();
  }

  public boolean isEmpty() {
    return stack.isEmpty();
  }

  public static void main(String[] args) {
    MinStack stack = new MinStack();
    stack.push(10);
    stack.push(5);
    stack.push(7);
    stack.push(3);
    stack.push(2);
    stack.push(1);

    assertEquals(1, stack.min());
    stack.pop();  // 1
    assertEquals(2, stack.min());
    stack.pop();  // 2
    assertEquals(3, stack.min());
    stack.pop();  // 3
    assertEquals(5, stack.min());
    stack.pop();  // 7
    assertEquals(5, stack.min());
    stack.pop();  // 5
    assertEquals(10, stack.min());
    stack.push(1);
    assertEquals(1, stack.min());
  }
}
