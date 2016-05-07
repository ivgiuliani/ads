package interviewquestions;

/**
 * Determine if two binary trees are identical.
 * Identical trees have the same key value at each position and the same
 * structure.
 */
public class BTSame extends TestCase {
  public static class Node {
    public final int value;
    public Node left;
    public Node right;

    public Node(int value) {
      this.value = value;
    }
  }

  public static void mirror(Node root) {
    if (root == null) return;

    Node tmp = root.left;
    root.left = root.right;
    root.right = tmp;
    mirror(root.left);
    mirror(root.right);
  }

  public static boolean same(Node n1, Node n2) {
    if (n1 == null && n2 == null) {
      return true;
    }

    if (n1 == null || n2 == null) {
      return false;
    }

    if (n1.value == n2.value) {
      return same(n1.left, n1.left) && same(n2.right, n2.right);
    }

    return false;
  }

  public static void main(String[] args) {
    /*
     *     4
     *  7    15
     *     19  2
     */
    Node node_1_4 = new Node(4);
    Node node_1_7 = new Node(7);
    Node node_1_15 = new Node(15);
    Node node_1_2 = new Node(2);
    Node node_1_19 = new Node(19);

    node_1_4.left = node_1_7;
    node_1_4.right = node_1_15;
    node_1_15.left = node_1_19;
    node_1_15.right = node_1_2;

    /*
     *      3
     *   2     9
     * 1   5     10
     */

    Node node_2_3 = new Node(3);
    Node node_2_2 = new Node(2);
    Node node_2_1 = new Node(1);
    Node node_2_5 = new Node(5);
    Node node_2_9 = new Node(9);
    Node node_2_10 = new Node(10);

    node_2_3.left = node_2_2;
    node_2_3.right = node_2_9;
    node_2_2.left = node_2_1;
    node_2_2.right = node_2_5;
    node_2_9.left = node_2_10;

    assertFalse(same(node_1_4, node_2_3));
    assertTrue(same(node_1_4, node_1_4));
    assertTrue(same(node_2_3, node_2_3));
  }
}
