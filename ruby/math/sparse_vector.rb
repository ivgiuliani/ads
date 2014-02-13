require 'test/unit'

class SparseVector
  include Comparable

  def initialize(*values)
    @vector = {}
    @vector.default = 0
    values.each_with_index { |val, index| @vector[index] = val if val != 0 }
  end

  def to_s
    vals = @vector.sort.map do |key, value|
      format('%d:%.3f', key, value)
    end
    '(' + vals.join(' ') + ')'
  end

  def <=>(other)
    self.norm2 <=> other.norm2
  end

  def[](index)
    @vector[index]
  end

  def[]=(index, value)
    @vector[index] = value
  end

  def +(other)
    sum = SparseVector.new
    each { |index, value| sum[index] = value }
    other.each { |index, value| sum[index] += value }
    sum
  end

  def -(other)
    self + other.invert
  end

  def *(other)
    sum = 0

    # iterate over the smaller item (the one with the largest number
    # of zeros)
    v1, v2 = self, other
    if length > other.length
      v1, v2 = v2, v1
    end

    v1.each do |index, value|
      sum += value * v2[index]
    end

    sum
  end

  def each
    @vector.each { |index, value| yield(index, value) }
  end

  def norm2
    Math.sqrt(self * self)
  end

  # Returns the number of non-zero entries in the sparse vector.
  def length
    @vector.size
  end

  # Inverts the sign of very item in the original sparse vector.
  def invert
    inverted = SparseVector.new
    @vector.each { |key, value| inverted[key] = -value }
    inverted
  end

  # Inverts the sign of very item in the original sparse vector in place.
  def invert!
    @vector.each { |key, value| @vector[key] = -value }
    self
  end
end


class TestSparseVector < Test::Unit::TestCase
  def setup
    @empty = SparseVector.new
  end

  def test_missing_index
    v = SparseVector.new(0, 0, 0, 1, 2, 0, 3)

    assert_equal(0, v[1231])
    assert_equal(1, v[3])
    assert_equal(0, v[0])
  end

  def test_mutate_vector
    v = SparseVector.new(0, 1, 2, 3, 4)
    assert_equal(4, v[4])
    v[4] = 400
    assert_equal(400, v[4])
  end

  def test_comparison
    v1 = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    v2 = SparseVector.new(0, 0, 0, 0, 0, 0, 1)

    assert(v1 > v2)
    assert(v2 < v1)
  end

  def test_string_conversion
    assert_equal('()', @empty.to_s)

    v = SparseVector.new(1)
    assert_equal('(0:1.000)', v.to_s)

    v = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    assert_equal('(3:1.000 4:2.000 6:3.000)', v.to_s)

    v = SparseVector.new
    v[2048], v[1816], v[83] = 312231, 213, 9999
    assert_equal('(83:9999.000 1816:213.000 2048:312231.000)', v.to_s)
  end

  def test_dot_product
    v1 = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    v2 = SparseVector.new(0, 0, 0, 0, 0, 0, 1)

    assert_in_delta(3.0, v1 * v2, 0.0001)
    assert_in_delta(3.0, v2 * v1, 0.0001)

    v2[3] = 4
    assert_in_delta(7.0, v1 * v2, 0.0001)
    assert_in_delta(7.0, v2 * v1, 0.0001)

    v2[3] = 0
    assert_in_delta(3.0, v1 * v2, 0.0001)
    assert_in_delta(3.0, v2 * v1, 0.0001)
  end

  def test_norm2
    v1 = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    v2 = SparseVector.new(0, 0, 0, 0, 0, 0, 1)

    assert_in_delta(3.741, v1.norm2, 0.001)
    assert_in_delta(1, v2.norm2, 0.001)

    assert_in_delta(v1.norm2, Math.sqrt(v1 * v1), 0.001)
  end

  def test_add
    v1 = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    v2 = SparseVector.new(0, 0, 0, 0, 0, 0, 1)
    assert_equal(SparseVector.new(0, 0, 0, 1, 2, 0, 4), v1 + v2)

    v1[3] = 1000
    assert_equal(SparseVector.new(0, 0, 0, 1000, 2, 0, 4), v1 + v2)

    assert_equal(SparseVector.new(5, 7, 9), SparseVector.new(1, 2, 3) + SparseVector.new(4, 5, 6))

    v1, v2, res = SparseVector.new, SparseVector.new, SparseVector.new
    v1[10], v1[100], v1[1000] = 10, 100, 1000
    v2[20], v2[200], v2[2000] = 20, 200, 2000
    res[10], res[20], res[100], res[200], res[1000], res[2000] = 10, 20, 100, 200, 1000, 2000
    assert_equal(res, v1 + v2)
  end

  def test_subtract
    v1 = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    v2 = SparseVector.new(0, 0, 0, 0, 0, 0, 1)
    assert_equal(SparseVector.new(0, 0, 0, 1, 2, 0, 2), v1 - v2)
    assert_equal(SparseVector.new(0, 0, 0, -1, -2, 0, -2), v2 - v1)
  end

  def test_invert
    v = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    inverted = SparseVector.new(0, 0, 0, -1, -2, 0, 3)
    assert_equal(inverted, v.invert)
  end

  def test_invert!
    v = SparseVector.new(0, 0, 0, 1, 2, 0, 3)
    inverted = SparseVector.new(0, 0, 0, -1, -2, 0, 3)
    assert_equal(inverted, v.invert!)
    assert_same(v, v.invert!)
  end
end