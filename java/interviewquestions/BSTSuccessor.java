package interviewquestions;

/**
 * Write an algorithm to find the ‘next’ node (i.e., in-order successor) of a given node in
 * a binary search tree where each node has a link to its parent.
 *
 * Ex:
 *            50
 *     30           71
 * 10     40      65   75
 *              60  66
 *
 * succ(50) = 60
 * succ(10) = 30
 * succ(66) = 71
 *
 */
public class BSTSuccessor extends TestCase {
  public static class Node {
    public int value;
    public Node parent;
    public Node left;
    public Node right;

    public Node(int v) {
      value = v;
    }
  }

  /*
   * @NotNull is implicit in JSR133
   * An alternative to the @NotNull check is assert node != null
   * or a classic if (node == null) return null
   */
  public static Node succ(/*@NotNull*/ Node node) {
    if (node.right != null) return leftMost(node.right);
    Node parent;
    while (node.parent != null) {
      parent = node.parent;
      if (parent.left == node)
        return parent;
      node = parent;
    }
    return null;
  }

  private static Node leftMost(Node node) {
    while (node.left != null) node = node.left;
    return node;
  }

  public static void main(String[] args) {
    Node node50 = new Node(50);
    Node node30 = new Node(30);
    Node node10 = new Node(10);
    Node node40 = new Node(40);
    Node node71 = new Node(71);
    Node node65 = new Node(65);
    Node node75 = new Node(75);
    Node node60 = new Node(60);
    Node node66 = new Node(66);

    node50.left = node30;
    node50.right = node71;
    node30.parent = node50;
    node71.parent = node50;
    node30.left = node10;
    node10.parent = node30;
    node30.right = node40;
    node40.parent = node30;
    node71.left = node65;
    node65.parent = node71;
    node71.right = node75;
    node75.parent = node71;
    node65.left = node60;
    node60.parent = node65;
    node65.right = node66;
    node66.parent = node65;

    assertEquals(node60, succ(node50));
    assertEquals(node30, succ(node10));
    assertEquals(node71, succ(node66));
  }
}
