package interviewquestions;

/**
 * Given an array of characters, convert it to a linked list and find the
 * mth-to-last of the list.
 */
public class ListMth extends TestCase {
  public static class LinkedList {
    public static class Node {
      public Node next;
      public int value;
    }

    public Node head = null;
    public boolean isEmpty() { return head == null; }
  }

  public static LinkedList.Node mthToLast(char[] arr, int mth) {
    LinkedList lst = convertToList(arr);

    // use two pointers, one that starts at the first item and one
    // that starts m items later so that when the latter ends up
    // being null, the former is the mth-to-last item
    LinkedList.Node mthToLast = lst.head;
    LinkedList.Node advanced = lst.head;

    int i = 0;
    while (i < mth && advanced != null) {
      advanced = advanced.next;
      i++;
    }

    if (i < mth) return null;

    while (advanced != null) {
      advanced = advanced.next;
      mthToLast = mthToLast.next;
    }

    return mthToLast;
  }

  private static LinkedList convertToList(char[] arr) {
    LinkedList lst = new LinkedList();
    LinkedList.Node tmp;
    int i;

    if (lst.isEmpty()) {
      i = 1;
      tmp = new LinkedList.Node();
      tmp.value = arr[0];
      lst.head = tmp;
    } else {
      i = 0;
      tmp = lst.head;
    }

    while (i < arr.length) {
      tmp.next = new LinkedList.Node();
      tmp.next.value = arr[i];
      tmp = tmp.next;
      i++;
    }

    return lst;
  }

  public static void main(String[] args) {
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd' }, 4).value, 'a');
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd' }, 3).value, 'b');
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd' }, 2).value, 'c');
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd' }, 1).value, 'd');
    assertEquals(mthToLast(new char[] { 'e', 'f', 'g', 'h' }, 2).value, 'g');
    assertNull(mthToLast(new char[] { 'e', 'f', 'g', 'h' }, 5));
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd',
                                        'e', 'f', 'g', 'h',
                                        'i', 'j', 'k', 'l',
                                        'm', 'n', 'o', 'p',
                                        'q', 'r', 's', 't',
                                        'u', 'v', 'w', 'x',
                                        'y', 'z' }, 26).value, 'a');
    assertEquals(mthToLast(new char[] { 'a', 'b', 'c', 'd',
                                        'e', 'f', 'g', 'h',
                                        'i', 'j', 'k', 'l',
                                        'm', 'n', 'o', 'p',
                                        'q', 'r', 's', 't',
                                        'u', 'v', 'w', 'x',
                                        'y', 'z' }, 1).value, 'z');
  }
}
