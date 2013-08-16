package interviewquestions.java;

import java.util.ArrayDeque;

/**
 * Implement a Queue class which implements a queue using two stacks
 */
public class QueueWithStacks {
  private ArrayDeque<Integer> s1 = new ArrayDeque<Integer>();
  private ArrayDeque<Integer> s2 = new ArrayDeque<Integer>();

  public void enqueue(int val) {
    while (!s2.isEmpty()) s1.push(s2.pop());
    s1.push(val);
  }

  public int dequeue() {
    while (!s1.isEmpty()) s2.push(s1.pop());
    return s2.pop();
  }

  public static void main(String[] args) {
    QueueWithStacks q = new QueueWithStacks();
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    q.enqueue(6);

    test(q.dequeue() == 1);
    test(q.dequeue() == 2);
    test(q.dequeue() == 3);
    test(q.dequeue() == 4);
    test(q.dequeue() == 5);
    test(q.dequeue() == 6);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
