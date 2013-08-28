package lists;

/**
 * A linked list
 * @param <T> type of items to hold
 */
public class LinkedList<T> {
  private class Node {
    public T value;
    public Node next = null;

    public Node(T value) {
      this.value = value;
    }
  }

  private Node head = null;

  public void clear() {
    head = null;
  }

  public void add(T value) {
    if (head == null) {
      head = new Node(value);
    } else {
      Node node = head;
      while (node.next != null) {
        node = node.next;
      }
      node.next = new Node(value);
    }
  }

  private Node getNode(int idx) {
    Node node = head;
    for (int i = 0; i < idx && node != null; i++) {
      node = node.next;
    }

    if (node == null) {
      // should throw some exception
      return null;
    }

    return node;
  }

  public int size() {
    int i = 0;
    Node node = head;
    while (node != null) {
      node = node.next;
      i++;
    }
    return i;
  }

  public T get(int idx) {
    Node node = getNode(idx);
    if (node == null) {
      // should throw some exception
      return null;
    }

    return node.value;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public void remove(int idx) {
    if (idx == 0) {
      head = head.next;
    } else {
      Node node = getNode(idx - 1);
      node.next = node.next.next;
    }
  }

  public static void main(String[] args) {
    LinkedList<String> ll = new LinkedList<String>();
    test(ll.isEmpty());

    ll.add("item 1");
    ll.add("item 2");
    ll.add("item 3");
    ll.add("item 4");
    test(!ll.isEmpty());
    test(ll.get(0).equals("item 1"));
    test(ll.get(1).equals("item 2"));
    test(ll.get(2).equals("item 3"));
    test(ll.get(3).equals("item 4"));
    test(ll.size() == 4);
    ll.remove(2);
    test(ll.get(0).equals("item 1"));
    test(ll.get(1).equals("item 2"));
    test(ll.get(2).equals("item 4"));
    test(ll.size() == 3);

    ll.remove(0);
    test(ll.get(0).equals("item 2"));

    ll.clear();
    test(ll.size() == 0);
    test(ll.isEmpty());
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
