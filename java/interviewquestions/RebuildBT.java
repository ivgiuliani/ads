package interviewquestions;

/**
 * Given the pre-order and in-order traversing result of a binary tree, write a function
 * to rebuild the tree.
 */
public class RebuildBT extends TestCase {
  public static class Node {
    public int value;
    public Node left;
    public Node right;
  }

  public static Node rebuild(int[] preOrder, int[] inOrder) {
    return rebuild(preOrder, inOrder, 0, inOrder.length, 0, inOrder.length);
  }

  private static Node rebuild(int[] preOrder, int[] inOrder, int preStart, int preEnd, int inStart, int inEnd) {
    if (preStart >= preEnd || preEnd > inOrder.length) return null;

    Node root = new Node();
    root.value = preOrder[preStart];

    // we can't use binary search because this is not a binary *search* tree
    int idx = findValue(inOrder, preOrder[preStart], inStart, inEnd); // O(n)

    root.left = rebuild(preOrder, inOrder, preStart + 1, preStart + 1 + idx, inStart, idx);
    root.right = rebuild(preOrder, inOrder, preStart + idx + 1, preEnd, idx + 1, inEnd);

    return root;
  }

  private static int findValue(int[] items, int value, int start, int end) {
    for (int i = 0; i <= end; i++) {
      if (items[start + i] == value) return i;
    }
    return -1;
  }

  public static void main(String[] args) {
    Node root;
    int[] preOrder, inOrder;

    /*
               7
            /    \
          6        2
         /  \     /  \
        5    4   11   9
     */
    preOrder = new int[] { 7, 6, 5, 4, 2, 11, 9 };
    inOrder = new int[] { 5, 6, 4, 7, 11, 2, 9 };

    root = rebuild(preOrder, inOrder);

    assertEquals(7, root.value);
    assertEquals(6, root.left.value);
    assertEquals(2, root.right.value);
    assertEquals(5, root.left.left.value);
    assertEquals(4, root.left.right.value);
    assertEquals(11, root.right.left.value);
    assertEquals(9, root.right.right.value);

    /*
               7
            /    \
          6        2
                  /  \
                 11   9
     */
    preOrder = new int[] { 7, 6, 2, 11, 9 };
    inOrder = new int[] { 6, 7, 11, 2, 9 };

    root = rebuild(preOrder, inOrder);

    assertEquals(7, root.value);
    assertEquals(6, root.left.value);
    assertEquals(2, root.right.value);
    assertEquals(11, root.right.left.value);
    assertEquals(9, root.right.right.value);


        /*
               7
                 \
                   2
                     \
                      9
     */
    preOrder = new int[] { 7, 2, 9 };
    inOrder = new int[] { 7, 2, 9 };

    root = rebuild(preOrder, inOrder);

    assertEquals(7, root.value);
    assertEquals(2, root.right.value);
    assertEquals(9, root.right.right.value);
    assertNull(root.left);

        /*
               7
                 \
                   2
                 /
                9
     */
    preOrder = new int[] { 7, 2, 9 };
    inOrder = new int[] { 7, 9, 2 };

    root = rebuild(preOrder, inOrder);

    assertEquals(7, root.value);
    assertEquals(2, root.right.value);
    assertEquals(9, root.right.left.value);
    assertNull(root.right.right);
  }
}
