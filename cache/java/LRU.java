package cache.java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Simple implementation of an LRU cache that uses a double linked list for
 * eviction scheduling and a hashmap for guaranteeing O(1) get/put
 * @param <K> type of the keys
 * @param <V> type of the values
 */
public class LRU<K,V> {
  private int capacity;

  private class Node {
    public K key;
    public V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private LinkedList<Node> cache = new LinkedList<Node>();
  private Map<K, Node> mapping = new HashMap<K, Node>();

  public LRU(int capacity) {
    this.capacity = capacity;
  }

  public void clear() {
    cache.clear();
    mapping.clear();
  }

  public void put(K key, V value) {
    Node node = null;
    if (!mapping.containsKey(key)) {
      node = new Node(key, value);
      cache.addFirst(node);
    } else {
      // Update the value in the linked list
      for (Node tmp : cache) {
        if (tmp.key.equals(key)) {
          tmp.value = value;
          node = tmp;
          break;
        }
      }
    }

    assert(node != null);
    mapping.put(key, node);

    if (cache.size() > capacity) {
      // this is O(1) because we do not need to traverse the list in order to
      // find the pointers that need to be updated since the java's LinkedList
      // implementation is a double linked list.
      Node removal = cache.getLast();
      cache.remove(removal);
      mapping.remove(removal.key);
    }
  }

  public V get(K item) {
    if (!mapping.containsKey(item)) return null;
    Node node = mapping.get(item);
    cache.remove(node);
    cache.addFirst(node);
    return node.value;
  }

  public static void main(String[] args) {
    LRU<String, Integer> cache = new LRU<String, Integer>(5);
    cache.put("item 1", 1);
    cache.put("item 2", 2);
    cache.put("item 3", 3);
    cache.put("item 4", 4);
    cache.put("item 5", 5);
    cache.put("item 6", 6);
    cache.put("item 7", 7);

    test(cache.get("item 7") == 7);
    test(cache.get("item 6") == 6);
    test(cache.get("item 5") == 5);
    test(cache.get("item 4") == 4);
    test(cache.get("item 3") == 3);
    test(cache.get("item 2") == null);
    test(cache.get("item 1") == null);

    cache.clear();

    cache.put("item 1", 1);
    cache.put("item 2", 2);
    cache.put("item 3", 3);
    cache.put("item 4", 4);
    cache.put("item 5", 5);
    cache.get("item 1");
    cache.put("item 6", 6);
    cache.put("item 7", 7);
    test(cache.get("item 7") == 7);
    test(cache.get("item 6") == 6);
    test(cache.get("item 5") == 5);
    test(cache.get("item 4") == 4);
    test(cache.get("item 3") == null);
    test(cache.get("item 2") == null);
    test(cache.get("item 1") == 1);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
