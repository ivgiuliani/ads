package dictionary.java;

/**
 * Dict implementation based on (quadratic probing) open addressing.
 *
 * This lacks an implementation of dynamic expansion of the inner array
 * thus the hash table can contain only up to `TABLE_SIZE` (1024) items.
 */
public class OADict {
  private static final int TABLE_SIZE = 1024;

  class ValueHolder {
    String key;
    String value;
    boolean tombstone = false;

    public ValueHolder(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }

  private ValueHolder[] hashtable = new ValueHolder[TABLE_SIZE];

  public void add(String key, String val) {
    final double base_hash = hash(key);
    double hash = base_hash;
    int i = 0;

    while (getForHash(hash) != null && i < TABLE_SIZE) {
      // use quadratic probing for seeking the empty spot
      ValueHolder holder = getForHash(hash);
      if (holder.key.equals(key)) {
        // update existing item (be it a tombstone or an existing "alive" value)
        holder.value = val;
        return;
      }
      hash = base_hash + (int)(Math.pow(i, 2));
      i++;
    }

    hashtable[((int) (hash % TABLE_SIZE))] = new ValueHolder(key, val);
  }

  public void del(String key) {
    ValueHolder holder = find(key);
    if (holder == null) return;
    holder.tombstone = true;
  }

  public boolean contains(String key) {
    return get(key) != null;
  }

  private ValueHolder getForHash(final double hash) {
    return hashtable[((int) (hash % TABLE_SIZE))];
  }

  private ValueHolder find(String key) {
    final double base_hash = hash(key);
    double hash = base_hash;
    int i = 0;

    while (getForHash(hash) != null && i < TABLE_SIZE) {
      ValueHolder holder = getForHash(hash);
      if (holder.key.equals(key)) {
        if (holder.tombstone) return null;
        return holder;
      }

      hash = base_hash + (int)(Math.pow(i, 2));
      i++;
    }

    // value not found
    return null;
  }

  public String get(String key) {
    ValueHolder holder = find(key);
    if (holder == null) return null;
    if (holder.tombstone) return null;
    return holder.value;
  }

  private double hash(String key) {
    int alpha = 127;  // assume all ascii as alphabet
    double hash = 0;
    int idx = 0;
    for (char ch : key.toCharArray()) {
      hash += Math.pow(alpha, idx + 1) * ch;
      idx++;
    }
    return hash;
  }

  public static void main(String[] args) {
    OADict dict = new OADict();
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

    int i;
    for (i = 0; i < TABLE_SIZE; i++) {
      String key = "key" + i;
      dict.add(key, String.valueOf(i));
    }
    for (i = 0; i < TABLE_SIZE; i++) {
      String key = "key" + i;
      test(dict.get(key).equals(String.valueOf(i)));
    }
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
