package interviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write a function to take the derivative of a polynomial (assume only positive exponents).
 */
public class PolynomialDerivative extends TestCase {

  // Another approach is to use an array of integers where each item in
  // position i corresponds to the coefficient of the x^i, i.e.: [3, 4, 5]
  // would be the representation of 5x^2 + 4x + 3.
  // Another possible approach is to use a hashmap of { exponent -> coefficient }
  // so that the above polynomial would be represented like
  // { 2 -> 5, 1 -> 4, 0 -> 3 } (this helps to save space on sparse polynomials)
  static class PolynomialItem implements Comparable<PolynomialItem> {
    public final int coefficient;
    public final int exponent;

    public PolynomialItem(int coefficient, int exponent) {
      this.coefficient = coefficient;
      this.exponent = exponent;
    }

    @Override
    public int compareTo(PolynomialItem other) {
      return Integer.compare(this.exponent, other.exponent);
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof PolynomialItem)) {
        return false;
      }

      return this.exponent == ((PolynomialItem) other).exponent &&
             this.coefficient == ((PolynomialItem) other).coefficient;
    }

    @Override
    public String toString() {
      return String.format("%dx^%d", coefficient, exponent);
    }
  }

  public static List<PolynomialItem> derivative(Collection<PolynomialItem> polynomial) {
    List<PolynomialItem> derivative = new ArrayList<PolynomialItem>(polynomial.size());

    for (PolynomialItem item : polynomial) {
      if (item.exponent == 0) {
        continue;
      }

      int coeff = item.exponent * item.coefficient;
      int exp = item.exponent - 1;

      derivative.add(new PolynomialItem(coeff, exp));
    }

    Collections.sort(derivative, Collections.reverseOrder());
    return derivative;
  }

  // java8 implementation
  public static List<PolynomialItem> derivative8(Collection<PolynomialItem> polynomial) {
    return polynomial
        .stream()
        .filter(p -> p.exponent > 0)
        .map(p -> new PolynomialItem(p.exponent * p.coefficient, p.exponent - 1))
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    List<PolynomialItem> items;

    // 5x^2 + 4x + 3 -> 10x + 4
    items = Arrays.asList(
        new PolynomialItem(5, 2),
        new PolynomialItem(4, 1),
        new PolynomialItem(3, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(10, 1),
        new PolynomialItem(4, 0)
    ), derivative(items));

    // - 4x^3 - 3x^2 + 2 -> - 12x^2 - 6x
    items = Arrays.asList(
        new PolynomialItem(-4, 3),
        new PolynomialItem(-3, 2),
        new PolynomialItem(2, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(-12, 2),
        new PolynomialItem(-6, 1)
    ), derivative(items));

    // 12x^4 + 10x^3 - 3x^2 + 2x - 2 -> 48x^3 + 30x^2 - 6x + 2
    items = Arrays.asList(
        new PolynomialItem(12, 4),
        new PolynomialItem(10, 3),
        new PolynomialItem(-3, 2),
        new PolynomialItem(2, 1),
        new PolynomialItem(-2, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(48, 3),
        new PolynomialItem(30, 2),
        new PolynomialItem(-6, 1),
        new PolynomialItem(2, 0)
    ), derivative(items));


    // java 8 version


    // 5x^2 + 4x + 3 -> 10x + 4
    items = Arrays.asList(
        new PolynomialItem(5, 2),
        new PolynomialItem(4, 1),
        new PolynomialItem(3, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(10, 1),
        new PolynomialItem(4, 0)
    ), derivative8(items));

    // - 4x^3 - 3x^2 + 2 -> - 12x^2 - 6x
    items = Arrays.asList(
        new PolynomialItem(-4, 3),
        new PolynomialItem(-3, 2),
        new PolynomialItem(2, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(-12, 2),
        new PolynomialItem(-6, 1)
    ), derivative8(items));

    // 12x^4 + 10x^3 - 3x^2 + 2x - 2 -> 48x^3 + 30x^2 - 6x + 2
    items = Arrays.asList(
        new PolynomialItem(12, 4),
        new PolynomialItem(10, 3),
        new PolynomialItem(-3, 2),
        new PolynomialItem(2, 1),
        new PolynomialItem(-2, 0)
    );
    assertEquals(Arrays.asList(
        new PolynomialItem(48, 3),
        new PolynomialItem(30, 2),
        new PolynomialItem(-6, 1),
        new PolynomialItem(2, 0)
    ), derivative8(items));
  }
}
