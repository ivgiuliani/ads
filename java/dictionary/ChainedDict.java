package dictionary;

/**
 * Implementation of a chain-based dictionary with single linked lists.
 * <p>
 * This implementation accepts only strings as keys and values and assumes
 * that duplicates keys will never be fed.
 */
public class ChainedDict {
  private final static int TABLE_SIZE = 1024;

  private class LinkedItem {
    private LinkedItem next = null;
    private String key = null;
    private String value = null;

    public LinkedItem(String key, String val) {
      this.key = key;
      this.value = val;
    }

    public void add(String key, String value) {
      LinkedItem item = this;
      while (item.next != null) {
        item = item.next;
      }

      item.next = new LinkedItem(key, value);
    }

    public LinkedItem find(String key) {
      LinkedItem item = this;
      while (item != null) {
        if (item.key.equals(key)) return item;
        item = item.next;
      }
      return null;
    }

    public boolean contains(String key) {
      return find(key) != null;
    }

    /**
     * Deletes an item from the list. Returns the new head of the list.
     * @param head head of the list
     * @param delitem item to delete
     * @return a possibly new head for the given list
     */
    public LinkedItem del(LinkedItem head, LinkedItem delitem) {
      // should be static, but inner classes cannot have static methods
      LinkedItem prev;
      LinkedItem item;
      if (head == delitem) {
        item = head.next;

        // splice out GC references
        head.next = null;
        return item;
      }

      prev = head;
      item = head.next;
      while (item != null) {
        if (item == delitem) {
          prev.next = item.next;
          return head;
        }
        prev = item;
        item = item.next;
      }

      return head;
    }
  }

  private LinkedItem[] hashtable = new LinkedItem[TABLE_SIZE];

  private int hash(String key) {
    int alpha = 127;  // assume all ascii as alphabet
    int hash = 0;
    int idx = 0;
    for (char ch : key.toCharArray()) {
      hash += Math.pow(alpha, idx + 1) * ch;
      idx++;
    }
    return hash;
  }

  private int getBucket(int hash) {
    return hash % TABLE_SIZE;
  }

  public void add(String key, String val) {
    final int hash = this.hash(key);
    final int bucket_idx = getBucket(hash);
    LinkedItem bucket = hashtable[bucket_idx];

    if (bucket == null) {
      bucket = new LinkedItem(key, val);
      hashtable[bucket_idx] = bucket;
      return;
    }

    LinkedItem item = bucket.find(key);
    if (item == null) {
      bucket.add(key, val);
    } else {
      // item exists already, just update its value
      item.value = val;
    }
  }

  public void del(String key) {
    final int hash = this.hash(key);
    final int bucket_idx = getBucket(hash);
    LinkedItem bucket = hashtable[bucket_idx];
    LinkedItem item;

    if (bucket == null) {
      return;
    }

    // we scan the list twice!
    item = bucket.find(key);
    if (item == null) {
      // we don't hold this key
      return;
    }

    hashtable[bucket_idx] = bucket.del(bucket, item);
  }

  public boolean contains(String key) {
    final int hash = this.hash(key);
    final int bucket_idx = getBucket(hash);
    LinkedItem bucket = hashtable[bucket_idx];

    return bucket != null && bucket.contains(key);
  }

  public String get(String key) {
    final int hash = this.hash(key);
    final int bucket_idx = getBucket(hash);
    LinkedItem bucket = hashtable[bucket_idx];

    while (bucket != null) {
      if (bucket.key.equals(key)) {
        return bucket.value;
      }
      bucket = bucket.next;
    }

    return null;
  }

  public static void main(String[] args) {
    ChainedDict dict = new ChainedDict();
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

    dict.add("key4", "value444");
    test(dict.get("key4").equals("value444"));

    test(dict.contains("key4"));
    dict.del("key4");
    test(!dict.contains("key4"));

    // now test everything again, but make sure that there are enough collisions
    // (ideally each bucket should hold the same number of keys [keys/table_size])
    int i;
    for (i = 0; i < ChainedDict.TABLE_SIZE * 10; i++) {
      String key = "key" + i;
      dict.add(key, String.valueOf(i));
    }
    for (i = 0; i < ChainedDict.TABLE_SIZE * 10; i++) {
      String key = "key" + i;
      test(dict.get(key).equals(String.valueOf(i)));
    }
    for (i = 0; i < ChainedDict.TABLE_SIZE * 10; i++) {
      String key = "key" + i;
      dict.del(key);
      test(!dict.contains(key));
    }
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
