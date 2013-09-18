package interviewquestions;

/**
 * Given the pre-order and in-order traversing result of a binary tree, write a function
 * to rebuild the tree.
 */
public class RebuildBT {
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

    test(root.value == 7);
    test(root.left.value == 6);
    test(root.right.value == 2);
    test(root.left.left.value == 5);
    test(root.left.right.value == 4);
    test(root.right.left.value == 11);
    test(root.right.right.value == 9);

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

    test(root.value == 7);
    test(root.left.value == 6);
    test(root.right.value == 2);
    test(root.right.left.value == 11);
    test(root.right.right.value == 9);


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

    test(root.value == 7);
    test(root.right.value == 2);
    test(root.right.right.value == 9);
    test(root.left == null);

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

    test(root.value == 7);
    test(root.right.value == 2);
    test(root.right.left.value == 9);
    test(root.right.right == null);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
