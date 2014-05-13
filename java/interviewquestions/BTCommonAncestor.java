package interviewquestions;

/**
 * Design an algorithm and write code to find the first common ancestor of two
 * nodes in a binary tree. Avoid storing additional nodes in a data structure.
 * NOTE: This is not necessarily a binary search tree
 *
 * Ex:
 *              50
 *           /      \
 *       30           51
 *      /   \        /   \
 *    70      25   45    43
 *   /  \         / \
 *  31  29       33  67
 *
 * commonAncestor(31, 25) = 30
 * commonAncestor(29, 25) = 30
 * commonAncestor(31, 67) = 50
 * commonAncestor(45, 51) = 51
 */
public class BTCommonAncestor extends TestCase {
  public static class Node {
    public int value;
    public Node left;
    public Node right;

    public Node(int v) {
      value = v;
    }
  }

  /*
   * This is amortized O(n log n) since the commonAncestor call is O(logn) if the
   * tree is balanced and the call to count() is O(n).
   */
  public static Node commonAncestor(Node root, Node p, Node q) {
    if (p == root || q == root) return root;
    int countLeft = count(root.left, p, q);

    switch (countLeft) {
      case 0:
        return commonAncestor(root.right, p, q);
      case 1:
        return root;
      case 2:
        return commonAncestor(root.left, p, q);
    }
    return null; // java will otherwise complain
  }

  private static int count(Node root, Node p, Node q) {
    int c = 0;
    if (root == null) return 0;
    if (root == p || root == q) c++;
    return c + count(root.left, p, q) + count(root.right, p, q);
  }

  public static void main(String[] args) {
    Node node50 = new Node(50);
    Node node30 = new Node(30);
    Node node51 = new Node(51);
    Node node70 = new Node(70);
    Node node25 = new Node(25);
    Node node45 = new Node(45);
    Node node43 = new Node(43);
    Node node31 = new Node(31);
    Node node29 = new Node(29);
    Node node33 = new Node(33);
    Node node67 = new Node(67);

    node50.left = node30;
    node50.right = node51;
    node30.left = node70;
    node30.right = node25;
    node70.left = node31;
    node70.right = node29;
    node51.left = node45;
    node51.right = node43;
    node45.left = node33;
    node45.right = node67;

    assertEquals(node30, commonAncestor(node50, node31, node25));
    assertEquals(node30, commonAncestor(node50, node29, node25));
    assertEquals(node50, commonAncestor(node50, node31, node67));
    assertEquals(node51, commonAncestor(node50, node45, node51));
  }
}
