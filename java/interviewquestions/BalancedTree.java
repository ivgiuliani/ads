package interviewquestions;

/**
 * Check if a binary tree is balanced. A balanced tree is defined to be a
 * tree such that no two leaf nodes differ in distance from the root by
 * more than one
 */
public class BalancedTree extends TestCase {
  public static class Node {
    public Node left = null;
    public Node right = null;
    public int value;

    public Node(int value) {
      this(value, null, null);
    }
    public Node(int value, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }
  }

  public static boolean isBalanced(Node node) {
    return Math.abs(height(node.left) - height(node.right)) <= 1;
  }

  private static int height(Node node) {
    if (node == null) return 0;
    return Math.max(height(node.left), height(node.right)) + 1;
  }

  public static void main(String[] args) {
    Node balanced = new Node(1,
      new Node(2,
        new Node(4),
        new Node(5)),
      new Node(3,
        new Node(6),
        new Node(7,
          new Node(8),
          new Node(9)))
    );

    Node unbalanced = new Node(1,
        new Node(2,
            new Node(4),
            new Node(5)),
        new Node(3,
            new Node(6),
            new Node(7,
              new Node(8),
              new Node(9,
                new Node(10),
                new Node(11))))
    );

    assertTrue(isBalanced(balanced));
    assertFalse(!isBalanced(unbalanced));
    assertTrue(isBalanced(new Node(0)));
  }
}
