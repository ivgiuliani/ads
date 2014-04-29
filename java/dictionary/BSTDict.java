package dictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of dictionary using a binary search tree
 */
public class BSTDict {
  private class BSTNode {
    public String key;
    public String value;
    public BSTNode parent;
    public BSTNode left;
    public BSTNode right;

    public BSTNode(BSTNode parent, String key, String value) {
      this.parent = parent;
      this.key = key;
      this.value = value;
    }

    protected BSTNode successor() {
      BSTNode succ = right;
      while (succ.left != null) {
        succ = succ.left;
      }
      return succ;
    }

    String stringify(int indent) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < indent; i++) {
        builder.append(' ');
      }
      builder.append('<');
      builder.append(key);
      builder.append('=');
      builder.append(value);
      builder.append(">\n");

      if (left != null) {
        builder.append(left.stringify(indent + 2));
      }

      if (right!= null) {
        builder.append(right.stringify(indent + 2));
      }

      return builder.toString();
    }
  }

  private BSTNode root = null;

  @Override
  public String toString() {
    if (root == null) {
      return "<>";
    }
    return root.stringify(0);
  }

  public void add(String key, String val) {
    root = add(root, key, val);
  }

  private BSTNode add(BSTNode root, String key, String val) {
    if (root == null) {
      return new BSTNode(null, key, val);
    }

    int comparison = key.compareTo(root.key);
    if (comparison == 0) {
      root.value = val;
    } else if (comparison < 0) {
      if (root.left == null) {
        root.left = new BSTNode(root, key, val);
      } else {
        add(root.left, key, val);
      }
    } else if (comparison > 0) {
      if (root.right == null) {
        root.right = new BSTNode(root, key, val);
      } else {
        add(root.right, key, val);
      }
    }

    return root;
  }

  private static BSTNode delete(BSTNode root, String key) {
    if (root == null) {
      return null;
    }

    int comparison = key.compareTo(root.key);
    if (comparison < 0) {
      delete(root.left, key);
      return root;
    } else if (comparison > 0) {
      delete(root.right, key);
      return root;
    }

    /* now root == key, hence three cases are possible:
     *   1) the node is a leaf
     *   2) the node has only one child
     *   3) the node has two children
     * while the first two cases are straightforward, the last one requires
     * more effort since we must relabel the node with the key of its successor,
     * which happens to be the leftmost descendant in the right sub-tree
     */

    String tmp;
    BSTNode parent = root.parent;

    if (root.left != null && root.right != null) {
      // node has two children
      // rather than complicating things by changing pointers, just replace keys and values
      BSTNode succ = root.successor();

      // swap root.[key/value] with succ.[key/value]
      tmp = root.key;
      root.key = succ.key;
      succ.key = tmp;

      tmp = root.value;
      root.value = succ.value;
      succ.value = tmp;
      delete(root.right, succ.key);
    } else if (root.left != null) {
      // 1 child (the left one)
      root.key = root.left.key;
      root.value = root.left.value;
      root.left = null;
    } else if (root.right != null) {
      // 1 child (the right one)
      root.key = root.right.key;
      root.value = root.right.value;
      root.right = null;
    } else {
      // node is a leaf
      if (parent != null && parent.left != null && parent.left.key.equals(root.key)) {
        parent.left = null;
      } else if (parent != null && parent.right != null && parent.right.key.equals(root.key)) {
        parent.right = null;
      }
      return null;
    }

    return root;
  }

  public void del(String key) {
    root = delete(root, key);
  }

  private static BSTNode min(BSTNode node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  public String get(String key) {
    BSTNode node = find(key);
    if (node != null) return node.value;
    return null; // should throw an exception
  }

  private BSTNode find(String key) {
    BSTNode node = root;

    while (node != null) {
      int comparison = key.compareTo(node.key);

      if (comparison == 0) return node;
      else if (comparison < 0) node = node.left;
      else if (comparison > 0) node = node.right;
    }

    return null; // should throw an exception
  }

  public boolean contains(String key) {
    return find(key) != null;
  }

  public List<String> keys() {
    return keys(root);
  }

  private List<String> keys(BSTNode root) {
    List<String> keys = new LinkedList<String>();

    if (root.left != null) {
      keys.addAll(keys(root.left));
    }
    keys.add(root.key);
    if (root.right != null) {
      keys.addAll(keys(root.right));
    }

    return keys;
  }

  public static void main(String[] args) {
    BSTDict dict = new BSTDict();
    test(dict.get("key") == null);

    dict.add("key1", "value1");
    dict.add("key2", "value2");
    dict.add("key3", "value3");
    dict.add("key4", "value4");

    test(dict.get("key1").equals("value1"));
    test(dict.get("key2").equals("value2"));
    test(dict.get("key3").equals("value3"));
    test(dict.get("key4").equals("value4"));
    test(dict.get("key5") == null);
    test(dict.contains("key1"));
    test(dict.contains("key2"));
    test(dict.contains("key3"));
    test(dict.contains("key4"));
    test(!dict.contains("key5"));

    // keys must be returned in sorted order
    List<String> keys = dict.keys();
    test(keys.get(0).equals("key1"));
    test(keys.get(1).equals("key2"));
    test(keys.get(2).equals("key3"));
    test(keys.get(3).equals("key4"));

    dict.add("key4", "value444");
    test(dict.get("key4").equals("value444"));

    test(dict.contains("key4"));
    dict.del("key4");
    test(dict.contains("key1"));
    test(dict.contains("key2"));
    test(dict.contains("key3"));
    test(!dict.contains("key4"));

    test(dict.contains("key2"));
    dict.del("key2");
    test(dict.contains("key1"));
    test(!dict.contains("key2"));
    test(dict.contains("key3"));
    test(!dict.contains("key4"));

    test(dict.contains("key1"));
    dict.del("key1");
    test(!dict.contains("key1"));
    test(!dict.contains("key2"));
    test(dict.contains("key3"));
    test(!dict.contains("key4"));

    test(dict.contains("key3"));
    dict.del("key3");
    test(!dict.contains("key1"));
    test(!dict.contains("key2"));
    test(!dict.contains("key3"));
    test(!dict.contains("key4"));

    // test delete root
    dict.add("key3", "val3");
    dict.add("key5", "val3");
    dict.add("key1", "val3");
    dict.del("key3");
    test(!dict.contains("key3"));
    test(dict.contains("key5"));
    test(dict.contains("key1"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
