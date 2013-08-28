package math.java;

import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of a sparse vector.
 * Indexing is 0-based.
 */
public class SparseVector implements Comparable<SparseVector> {
  private final TreeMap<Integer, Double> vector = new TreeMap<Integer, Double>();

  private SparseVector() {}

  public static SparseVector from(Number... items) {
    SparseVector vector = new SparseVector();

    int i = 0;
    for (Number item : items) {
      vector.set(i, item.doubleValue());
      i++;
    }

    return vector;
  }

  /**
   * Builds a new empty sparse vector
   * @return a new instance of {@link SparseVector}
   */
  public static SparseVector empty() {
    return new SparseVector();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("( ");

    for (Map.Entry<Integer, Double> entry : this.vector.entrySet()) {
      builder.append(String.format("%d:%.3f ", entry.getKey(), entry.getValue()));
    }

    builder.append(')');
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SparseVector that = (SparseVector)o;
    return this.vector.size() == that.vector.size() && this.vector.equals(that.vector);
  }

  @Override
  public int compareTo(SparseVector that) {
    // the comparison is with respect to the 2-norm of the vectors
    return (int)(this.norm2() - that.norm2());
  }

  /**
   * Returns the number of non-zero entries in the sparse vector
   * @return the number of non-zero entries in the sparse vector
   */
  public int nzero() {
    return this.vector.size();
  }

  /**
   * Calculates the 2-norm of this vector
   * @return the 2-norm of the vector
   */
  public double norm2() {
    return Math.sqrt(this.dot(this));
  }

  /**
   * Set the value of the item at the given index in the array
   * Note that the vector index is 0-based.
   * @param idx   position in the array
   * @param value cell's value
   */
  public void set(int idx, double value) {
    if (value == 0.0) {
      this.vector.remove(idx);
    } else {
      this.vector.put(idx, value);
    }
  }

  /**
   * Returns the value of the vector for the given cell index.
   * Note that the vector index is 0-based.
   * @param idx cell index
   * @return    value of the vector at the given index
   */
  public double get(int idx) {
    if (this.vector.containsKey(idx))
      return this.vector.get(idx);
    return 0;
  }

  /**
   * Calculates the dot-product among two vectors.
   * @param that second vector
   * @return     dot-product between this vector and the given vector
   */
  public double dot(SparseVector that) {
    SparseVector v1 = this;
    SparseVector v2 = that;
    double sum = 0;

    // iterate over the vector with the largest number of zeros
    if (this.nzero() > that.nzero()) {
      v1 = that;
      v2 = this;
    }

    for (Map.Entry<Integer, Double> item : v1.vector.entrySet()) {
      sum += item.getValue() * v2.get(item.getKey());
    }

    return sum;
  }

  /**
   * Calculates the dot-product among two vectors.
   * @param vector list of values (in order) for the second vector
   * @return       dot-product between this vector and the given vector
   */
  public double dot(Number... vector) {
    return dot(from(vector));
  }

  /**
   * Calculates the sum-vector of the current vector instance plus
   * the given vector
   * @param that second vector
   * @return     the sum-vector between this vector and the given vector
   */
  public SparseVector add(SparseVector that) {
    SparseVector sum = SparseVector.empty();

    for (int i = 0; i <= Math.max(this.vector.lastKey(), that.vector.lastKey()); i++) {
      sum.set(i, this.get(i) + that.get(i));
    }

    return sum;
  }

  /**
   * Calculates the sum-vector of the current vector instance plus
   * the given vector
   * @param that list of values (in order) for the second vector
   * @return     the sum-vector between this vector and the given vector
   */
  public SparseVector add(Number... that) {
    return add(from(that));
  }

  public static void main(String[] args) {
    SparseVector s1, s2;

    s1 = SparseVector.from(0, 0, 0, 1, 2, 0, 3);
    s2 = SparseVector.from(0, 0, 0, 0, 0, 0, 1);
    test(s1.norm2() > 3.741 && s1.norm2() < 3.742);
    test(s2.norm2() > 0.999999 && s2.norm2() < 1.000001);
    test(s1.compareTo(s2) > 0);
    test(s1.get(0) == 0);
    test(s1.get(2) == 0);
    test(s1.get(4) == 2);
    test(s1.get(6) == 3);
    test(s1.nzero() == 3);
    test(s2.nzero() == 1);
    test(s1.dot(s2) == 3);
    s2.set(3, 4);
    test(s1.dot(s2) == 7);
    test(s2.nzero() == 2);
    s2.set(3, 0);
    test(s1.dot(s2) == 3);
    test(s2.nzero() == 1);
    test(s1.add(s2).equals(SparseVector.from(0, 0, 0, 1, 2, 0, 4)));
    test(s1.add(0, 0, 0, 0, 0, 0, 1).equals(SparseVector.from(0, 0, 0, 1, 2, 0, 4)));

    s1 = SparseVector.from(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 0.f, 3.0f);
    s2 = SparseVector.from(0, 0, 0, 0, 0, 0, 1);
    test(s1.dot(s2) == 3.0f);
    test(s2.dot(s1) == 3);
    test(s1.dot(0, 1, 2, 3) == 3);

    s1 = SparseVector.empty();
    s2 = SparseVector.empty();
    test(s1.dot(s2) == 0);
    test(s2.dot(s1) == 0);
    test(s1.norm2() == 0);
    test(s2.norm2() == 0);
    test(s1.compareTo(s2) == 0);

    test(SparseVector.from(1, 2, 3).dot(3, 4, 5) == 26);
    test(SparseVector.from(1, 2, 3).add(3, 4, 5).equals(SparseVector.from(4, 6, 8)));

    test(SparseVector.empty().toString().equals("( )"));
    test(SparseVector.from(1, 2, 3).toString().equals("( 0:1.000 1:2.000 2:3.000 )"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
