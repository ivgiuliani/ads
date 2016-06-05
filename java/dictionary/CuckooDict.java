package dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Cuckoo hashing is a hash table scheme using two hash tables T1 and T2 each with r buckets
 * with independent hash functions h1 and h2 each mapping a universe U to bucket locations
 * {0, ... , r −1}. A key x can be stored in exactly one of the locations T1[h1(x)] and T2[h2(x)].
 * <p>
 * A lookup operation in Cuckoo Hashing examines both locations T1[h1(x)] and T2[h2(x)] and
 * succeeds if the key x is stored in either location.
 *
 * @see <a href="http://cs.stanford.edu/~rishig/courses/ref/l13a.pdf">Cuckoo Hashing</a>
 */
@SuppressWarnings("WeakerAccess")
public class CuckooDict<K, V> {
  private static final Random RANDOM = new Random();

  private static final int THRESHOLD_LOOP = 8;
  private static final int START_SIZE = 2;
  private static final int REHASH_MULTIPLICATION_FACTOR = 2;

  private static class ValueHolder<K1, V1> {
    final K1 key;
    final V1 value;

    ValueHolder(K1 key, V1 value) {
      this.key = key;
      this.value = value;
    }
  }

  @SuppressWarnings("unchecked")
  private ValueHolder<K, V>[] T1 = new ValueHolder[START_SIZE];

  @SuppressWarnings("unchecked")
  private ValueHolder<K, V>[] T2 = new ValueHolder[START_SIZE];

  public CuckooDict() {
  }

  public boolean contains(K key) {
    return get(key) != null;
  }

  public V get(K key) {
    ValueHolder<K, V> v1 = T1[hash1(key)];
    ValueHolder<K, V> v2 = T2[hash2(key)];

    if (v1 == null && v2 == null) {
      return null;
    } else if (v1 != null && v1.key.equals(key)) {
      return v1.value;
    } else if (v2 != null && v2.key.equals(key)) {
      return v2.value;
    }
    return null;
  }

  public void put(K key, V value) {
    ValueHolder<K, V> v;

    while ((v = putSafe(key, value)) != null) {
      rehash();
      put(v.key, v.value);
    }
  }

  /**
   * @return the key we failed to move because of collisions.
   */
  private ValueHolder<K, V> putSafe(K key, V value) {
    int loop = 0;

    while (loop++ < THRESHOLD_LOOP) {
      ValueHolder<K, V> newV = new ValueHolder<>(key, value);
      ValueHolder<K, V> v1 = T1[hash1(key)];
      ValueHolder<K, V> v2 = T2[hash2(key)];

      // Check if we must just update the value first.
      if (v1 != null && v1.key.equals(key)) {
        T1[hash1(key)] = newV;
        return null;
      }
      if (v2 != null && v2.key.equals(key)) {
        T2[hash2(key)] = newV;
        return null;
      }

      if (v1 == null) {
        T1[hash1(key)] = newV;
        return null;
      } else if (v2 == null) {
        T2[hash2(key)] = newV;
        return null;
      } else {
        if (RANDOM.nextBoolean()) {
          // move from T1
          key = v1.key;
          value= v1.value;
          T1[hash1(key)] = newV;
        } else {
          // move from T2
          key = v2.key;
          value= v2.value;
          T2[hash2(key)] = newV;
        }
      }
    }

    return new ValueHolder<>(key, value);
  }

  public void remove(K key) {
    ValueHolder<K, V> v1 = T1[hash1(key)];
    ValueHolder<K, V> v2 = T2[hash2(key)];

    if (v1 != null && v1.key.equals(key)) {
      T1[hash1(key)] = null;
    }

    if (v2 != null && v2.key.equals(key)) {
      T2[hash2(key)] = null;
    }
  }

  private void rehash() {
    int newSize = T1.length;
    do {
      newSize *= REHASH_MULTIPLICATION_FACTOR;
    } while (!rehash(newSize));
  }

  // TODO this is a naive and inefficient rehash, needs a better one.
  @SuppressWarnings("unchecked")
  private boolean rehash(final int newSize) {
    List<ValueHolder<K, V>> lst = new ArrayList<>();
    for (int i = 0; i < T1.length; i++) {
      ValueHolder<K, V> v1 = T1[i];
      ValueHolder<K, V> v2 = T2[i];
      if (v1 != null) {
        lst.add(v1);
      }
      if (v2 != null) {
        lst.add(v2);
      }
    }

    // Save old state as we may need to restore it if the rehash fails.
    ValueHolder<K, V>[] oldT1 = T1;
    ValueHolder<K, V>[] oldT2 = T2;

    T1 = new ValueHolder[newSize];
    T2 = new ValueHolder[newSize];

    for (ValueHolder<K, V> v : lst) {
      if (putSafe(v.key, v.value) != null) {
        // We need to rehash again, so restore old state.
        T1 = oldT1;
        T2 = oldT2;
        return false;
      }
    }

    return true;
  }

  private int hash1(K key) {
    // TODO conversion to unsigned int is not correct
    return (int) (Math.abs(djb2(iToB(key.hashCode()))) % T1.length);
  }

  private int hash2(K key) {
    // TODO conversion to unsigned int is not correct
    return (int) (Math.abs(sdbm(iToB(key.hashCode()))) % T1.length);
  }

  private static byte[] iToB(int value) {
    return new byte[] {
        (byte)(value >>> 24),
        (byte)(value >>> 16),
        (byte)(value >>> 8),
        (byte)value};
  }

  private static long djb2(byte[] bytes) {
    long hash = 5381;
    byte c;

    int i = 0;
    while (i < bytes.length) {
      c = bytes[i++];
      hash = ((hash << 5) + hash) + c;
    }
    return hash;
  }

  private static long sdbm(byte[] bytes) {
    long hash = 0;
    int c;

    int i = 0;
    while (i < bytes.length) {
      c = bytes[i++];
      hash = c + (hash << 6) + (hash << 16) - hash;
    }

    return hash;
  }

  public static void main(String[] args) {
    CuckooDict<String, String> dict = new CuckooDict<>();
    test(dict.get("key") == null);

    dict.put("key1", "value1");
    dict.put("key2", "value2");
    dict.put("key3", "value3");
    dict.put("key4", "value4");
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

    dict.put("key4", "value444");
    test(dict.get("key4").equals("value444"));

    test(dict.contains("key4"));
    dict.remove("key4");
    test(!dict.contains("key4"));

    dict = new CuckooDict<>();
    for (int i = 0; i < 1024; i++) {
      dict.put("key-" + i, "value-" + i);
    }
    for (int i = 0; i < 1024; i++) {
      test(dict.contains("key-" + i));
      test(dict.get("key-" + i).equals("value-" + i));
    }
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
