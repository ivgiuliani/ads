package interviewquestions.java;

/**
 * Given a binary search tree and a number 'number', find the node
 * in the bst whose distance from 'number' is the least.
 * Example:
 *
 *              10
 *         5         15
 *       1   7     12   100
 *
 * Return: 7
 */
public class ClosestNumberBST {
  public static class Node {
    public final int value;
    public final Node left;
    public final Node right;

    public Node(int value) {
      this(value, null, null);
    }

    public Node(int value, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }
  }

  public static Integer find(Node root, int number) {
    return (root == null) ? null : find(root, number, root.value);
  }

  private static int find(Node root, int number, int currentMin) {
    if (root == null) return currentMin;
    int newMin = currentMin;

    if (Math.abs(number - root.value) < Math.abs(number - currentMin)) {
      newMin = root.value;
    }

    if (number < root.value) {
      return find(root.left, number, newMin);
    }
    return find(root.right, number, newMin);
  }

  public static void main(String[] args) {
    Node root = new Node(10,
        new Node(5,
            new Node(1), new Node(7)),
        new Node(15,
            new Node(12), new Node(100)));

    test(find(null, 1) == null);
    test(find(root, 8) == 7);
    test(find(root, 1) == 1);
    test(find(root, 14) == 15);
    test(find(root, Integer.MAX_VALUE) == 100);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
