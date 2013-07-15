package sets.java;

import java.util.HashSet;
import java.util.Set;

public class UnionFind {
  private final int SET_SIZE = 1024;
  private int[] parent = new int[SET_SIZE];
  private int[] size = new int[SET_SIZE];

  /**
   * Initializes the union find data structure.
   */
  public UnionFind() {
    for (int i = 0; i < SET_SIZE; i++) {
      parent[i] = i;
      size[i] = 1;
    }
  }

  public int find(int k) {
    assert(k < SET_SIZE);
    return parent[k] == k ? k : find(parent[k]);
  }

  public boolean sameComponent(int x, int y) {
    assert(x < SET_SIZE);
    assert(y < SET_SIZE);
    return find(x) == find(y);
  }

  public void union(int x, int y) {
    assert(x < SET_SIZE);
    assert(y < SET_SIZE);
    int set1 = find(x);
    int set2 = find(y);

    if (set1 == set2) {
      // same set already
      return;
    }

    // use the larger set as root to keep the height of the tree
    // smaller (using the smaller set as a subtree will have its
    // height to increase of just 1 since we're only adding the new
    // root).
    if (size[set1] > size[set2]) {
      size[set1] = size[set1] + size[set2];
      parent[set2] = set1;
    } else {
      size[set2] = size[set1] + size[set2];
      parent[set1] = set2;
    }
  }

  public static void main(String[] args) {
    Set<Integer> testset = new HashSet<Integer>();
    UnionFind uf = new UnionFind();

    // all sets must be disjoint
    for (int i = 0; i < 10; i++) {
      testset.add(uf.find(i));
    }
    test(testset.size() == 10);
    testset.clear();

    uf.union(1, 2);
    uf.union(1, 3);
    uf.union(3, 5);
    test(uf.sameComponent(1, 2));
    test(uf.sameComponent(1, 3));
    test(uf.sameComponent(3, 5));
    test(uf.sameComponent(1, 5));
    test(uf.sameComponent(2, 5));
    test(!uf.sameComponent(2, 6));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
