package interviewquestions;

/**
 * Create a mirror of a binary tree
 *
 * Ex:
 *
 *     4                4
 *  7    15          15   7
 *     19  2       2   19
 */
public class BTMirror extends TestCase {
  public static class Node {
    public int value;
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

  public static void main(String[] args) {
    Node node4 = new Node(4);
    Node node7 = new Node(7);
    Node node15 = new Node(15);
    Node node2 = new Node(2);
    Node node19 = new Node(19);

    node4.left = node7;
    node4.right = node15;
    node15.left = node19;
    node15.right = node2;

    mirror(node4);

    assertEquals(node15, node4.left);
    assertEquals(node7, node4.right);
    assertNull(node7.left);
    assertNull(node7.right);
    assertEquals(node2, node15.left);
    assertEquals(node19, node15.right);
  }
}
