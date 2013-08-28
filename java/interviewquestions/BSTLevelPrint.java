package interviewquestions.java;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree of integers, print it in level order.
 * The output will contain space between the numbers in the same
 * level, and new line between different levels.
 * For example, if the tree is:
 *
 *      1
 *     / \
 *    2   3
 *   /   / \
 *  4   5   6
 *
 * The output should be:
 * 1
 * 2 3
 * 4 5 6
 *
 * Also implement a method such that the above tree would be printed
 * in reverse (lowest levels first):
 *
 * 4 5 6
 * 2 3
 * 1
 */
public class BSTLevelPrint {
  public static class Node {
    public Node left;
    public Node right;
    public int value;

    public Node(int value) {this(value, null, null);}
    public Node(int value, Node left, Node right) {
      this.left = left;
      this.right = right;
      this.value = value;
    }
  }

  public static void print(Node root) {
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(root);
    Node tmp;

    int counter = 1;
    int nextLevelCounter = 0;

    while (!queue.isEmpty()) {
      tmp = queue.poll();
      counter--;
      System.out.print(tmp.value + " ");

      if (tmp.left != null) {
        queue.add(tmp.left);
        nextLevelCounter++;
      }
      if (tmp.right != null) {
        queue.add(tmp.right);
        nextLevelCounter++;
      }

      if (counter == 0) {
        System.out.print("\n");
        counter = nextLevelCounter;
        nextLevelCounter = 0;
      }
    }
  }

  public static void printReverse(Node root) {
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(root);

    levelPrint(1, queue);
  }

  private static void levelPrint(int levelCount, Queue<Node> queue) {
    int nextLevelCount = 0;
    Node tmp;
    int current = levelCount;

    if (levelCount == 0) return;
    List<Integer> items = new LinkedList<Integer>();

    while (current > 0) {
      tmp = queue.poll();
      items.add(tmp.value);

      if (tmp.left != null) {
        queue.add(tmp.left);
        nextLevelCount++;
      }

      if (tmp.right != null) {
        queue.add(tmp.right);
        nextLevelCount++;
      }

      current--;
    }

    levelPrint(nextLevelCount, queue);

    for (int i : items) {
      System.out.print(i + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Node root = new Node(1,
        new Node(2, new Node(4), null),
        new Node(3, new Node(5), new Node(6)));

    print(root);

    printReverse(root);
  }
}
