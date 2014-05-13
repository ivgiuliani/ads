package interviewquestions;

/**
 * Given a sorted (increasing order) array, write an algorithm to create a binary tree
 * with minimal height.
 */
public class ArrayToBalancedBST extends TestCase {
  public static class BSTNode {
    public int value;
    public BSTNode left;
    public BSTNode right;

    public BSTNode(int value) {
      this.value = value;
    }
  }

  public static BSTNode arrayToBST(int[] array) {
    return arrayToBST(array, 0, array.length - 1);
  }

  private static BSTNode arrayToBST(int[] array, int start, int end) {
    if (end - start < 0) return null;

    int middleIdx = start + (end - start + 1) / 2;
    BSTNode root = new BSTNode(array[middleIdx]);

    root.left = arrayToBST(array, start, middleIdx - 1);
    root.right = arrayToBST(array, middleIdx + 1, end);

    return root;
  }

  public static void main(String[] args) {
    int[] array;
    BSTNode node;

    array = new int[] { 1, 3, 5, 7, 9, 11, 12 };
    node = arrayToBST(array);
    assertEquals(node.value, 7);
    assertEquals(node.left.value, 3);
    assertEquals(node.right.value, 11);
    assertEquals(node.left.left.value, 1);
    assertEquals(node.left.right.value, 5);
    assertEquals(node.right.left.value, 9);
    assertEquals(node.right.right.value, 12);

    array = new int[] { 1, 3, 5, 7, 9, 11 };
    node = arrayToBST(array);
    assertEquals(node.value, 7);
    assertEquals(node.left.value, 3);
    assertEquals(node.right.value, 11);
    assertEquals(node.left.left.value, 1);
    assertEquals(node.left.right.value, 5);
    assertEquals(node.right.left.value, 9);
    assertNull(node.right.right);
  }
}
