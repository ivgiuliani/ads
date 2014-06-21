package interviewquestions;

import java.util.ArrayDeque;

/**
 * Implement a Queue class which implements a queue using two stacks
 */
public class QueueWithStacks extends TestCase {
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

    assertEquals(1, q.dequeue());
    assertEquals(2, q.dequeue());
    assertEquals(3, q.dequeue());
    assertEquals(4, q.dequeue());
    assertEquals(5, q.dequeue());
    assertEquals(6, q.dequeue());
  }
}
