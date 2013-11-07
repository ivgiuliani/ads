package lists;

/**
 * Simple implementation of a doubly linked list.
 * Held values are immutable.
 *
 * @param <T> type of the items to hold
 */
public class DoublyLinkedList<T> {
  private class Node {
    public final T value;
    public Node next = null;
    public Node prev = null;

    public Node(T value) {
      this.value = value;
    }
  }

  private Node head = null;

  public void clear() {
    head = null;
  }

  public void addFirst(T value) {
    Node node = new Node(value);
    node.next = head;
    head.prev = node;
    head = node;
  }

  public void addLast(T value) {
    Node node = new Node(value);
    if (head == null) {
      head = node;
    } else {
      Node last = head;
      while (last.next != null) {
        last = last.next;
      }
      last.next = node;
      node.prev = last;
    }
  }

  /**
   * This is O(N) since we do a full scan of the list, but it might easily be O(1) if
   * we'd store the size of the list in a private field.
   */
  public int size() {
    int count = 0;
    Node node = head;
    while (node != null) {
      count++;
      node = node.next;
    }
    return count;
  }

  public boolean isEmpty() {
    return head == null;
  }

  private Node find(int n) {
    Node ptr = head;
    for (int i = 0; i < n && ptr != null; i++) {
      ptr = ptr.next;
    }
    return ptr;
  }

  public T get(int n) {
    Node node = find(n);
    if (node == null) {
      return null;
    }
    return node.value;
  }

  public void remove(int n) {
    Node node = find(n);
    if (node == null) {
      return;
    }

    if (node.prev == null) {
      // we're removing the head
      head = node.next;
    } else {
      node.prev.next = node.next;
    }
  }

  public static void main(String[] args) {
    DoublyLinkedList<String> dl = new DoublyLinkedList<String>();
    test(dl.isEmpty());

    dl.addLast("item 1");
    dl.addLast("item 2");
    dl.addLast("item 3");
    dl.addLast("item 4");
    test(!dl.isEmpty());
    test(dl.get(0).equals("item 1"));
    test(dl.get(1).equals("item 2"));
    test(dl.get(2).equals("item 3"));
    test(dl.get(3).equals("item 4"));
    test(dl.size() == 4);
    dl.remove(2);
    test(dl.get(0).equals("item 1"));
    test(dl.get(1).equals("item 2"));
    test(dl.get(2).equals("item 4"));
    test(dl.size() == 3);
    dl.addFirst("item 0");
    dl.addFirst("item -1");
    dl.addFirst("item -2");
    test(dl.size() == 6);
    test(dl.get(0).equals("item -2"));
    test(dl.get(1).equals("item -1"));
    test(dl.get(2).equals("item 0"));
    test(dl.get(3).equals("item 1"));
    test(dl.get(4).equals("item 2"));

    dl.remove(0);
    test(dl.get(0).equals("item -1"));

    dl.clear();
    test(dl.size() == 0);
    test(dl.isEmpty());
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
