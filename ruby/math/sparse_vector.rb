require 'test/unit'

class SparseVector
  include Comparable

  # Create a new sparse vector.
  #
  # ==== Attributes
  # * +values+ a list of values to use for this sparse vector starting from
  #            the index 0. The list can be empty, in which case an empty
  #            vector will be created.
  def initialize(*values)
    @vector = {}
    @vector.default = 0
    values.each_with_index do |val, index|
      raise ArgumentError, 'Only numeric types are accepted' unless val.is_a? Numeric
      @vector[index] = val if val != 0
    end
  end

  def to_s
    '(' + @vector.sort.map { |k, v| format('%d:%.3f', k, v) }.join(' ') + ')'
  end

  def <=>(other)
    self.norm2 <=> other.norm2
  end

  def[](index)
    raise ArgumentError, 'Only numeric types are accepted' unless index.is_a? Numeric
    @vector[index]
  end

  def[]=(index, value)
    raise ArgumentError, 'Only numeric types are accepted' unless index.is_a? Numeric
    raise ArgumentError, 'Only numeric types are accepted' unless value.is_a? Numeric
    @vector[index] = value
  end

  # Creates a copy of the current instance.
  def copy
    copy = SparseVector.new
    each { |index, value| copy[index] = value }
    copy
  end

  # Adds either a number or another sparse vector to the current sparse
  # vector. If a number is provided, only non-zero items will be included
  # in the addition.
  def +(other)
    case
      when other.is_a?(Numeric)
        map { |_, v| v + other }
      when other.is_a?(SparseVector)
        sum = copy
        other.each { |index, value| sum[index] += value }
        sum
      else
        raise TypeError, 'Can only add either a numeric value or another sparse vector'
    end
  end

  def -(other)
    self + other.invert
  end

  def *(other)
    case
      when other.is_a?(Numeric)
        map { |_, v| v * other }
      when other.is_a?(SparseVector)
        SparseVector.dot_product(self, other)
      else
        raise TypeError, 'Can only multiply with either a numeric value or another sparse vector'
    end
  end

  # Returns a list of indexes containing non-zero items in the vector.
  # The returned list is sorted in ascending order.
  def indexes
    @vector.keys.sort!
  end

  def each
    @vector.each { |index, value| yield(index, value) }
  end

  # Iterates over every non-zero item and creates a new sparse vector
  # as the result of the application of the given block function
  # on each item
  def map
    new = SparseVector.new
    @vector.each do |index, value|
      new[index] = yield(index, value)
    end

    new
  end

  # Calculates the 2-norm of this vector.
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

  private

  # Dot product among two vectors.
  def self.dot_product(v1, v2)
    sum = 0

    # iterate over the smaller item (the one with the largest number
    # of zeros)
    if v1.length > v2.length
      v1, v2 = v2, v1
    end

    v1.each do |index, value|
      sum += value * v2[index]
    end

    sum
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

  def test_only_numbers_are_accepted
    v = SparseVector.new(0, 1, 2, 3, 4)
    assert_raise(ArgumentError) { v[15] = 'String' }
    assert_raise(ArgumentError) { v['String'] = 123 }
    assert_raise(ArgumentError) { v['String'] = 'Another string' }
    assert_raise(ArgumentError) { v['String'] = 'Another string' }
    assert_raise(ArgumentError) { v[123] = {} }

    assert_raise(ArgumentError) { SparseVector.new(1, 2, 3, '')}
    assert_raise(ArgumentError) { SparseVector.new('string')}
    assert_raise(ArgumentError) { SparseVector.new('string', {}, Object.new)}
    assert_raise(ArgumentError) { SparseVector.new(Object.new)}
    assert_raise(ArgumentError) { SparseVector.new(1, 2, {}, 3)}
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

  def test_product
    v = SparseVector.new(1, 2, 3, 4, 5)
    assert_equal(SparseVector.new(2, 4, 6, 8, 10), v * 2)
    assert_equal(SparseVector.new(10, 20, 30, 40, 50), v * 10)

    assert_raise(TypeError) { v * {} }
    assert_raise(TypeError) { v * Object.new }
    assert_raise(TypeError) { v * 'string' }
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
    v = SparseVector.new(2, 4, 6, 8, 0, 10)
    assert_equal(SparseVector.new(12, 14, 16, 18, 0, 20), v + 10)
    assert_equal(SparseVector.new(102, 104, 106, 108, 0, 110), v + 100)
    assert_equal(v, v + 0)

    assert_raises(TypeError) { v + {} }
    assert_raises(TypeError) { v + 'string' }
    assert_raises(TypeError) { v + Object.new }
  end

  def test_add_sparse_vector
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

  def test_copy
    v = SparseVector.new(10, 100, 1000, 500, 50, 5, 0)
    copy = v.copy
    assert_equal(v, copy)
    assert_not_same(v, copy)
  end

  def test_each
    v = SparseVector.new(10, 100, 1000, 500, 50, 5, 0)
    count, sum = 0, 0
    v.each do |_, value|
      count += 1
      sum += value
    end

    assert_equal(6, count)  # '0' must be skipped
    assert_equal(1665, sum)
  end

  def test_map
    v = SparseVector.new(10, 100, 1000, 500, 50, 5, 0)
    assert_equal(SparseVector.new(20, 200, 2000, 1000, 100, 10),
                 v.map { |_, x| x * 2 })

    v = SparseVector.new(10, 100, 1000, 500, 50, 5, 0)
    assert_equal(SparseVector.new, v.map { 0 })

    v = SparseVector.new(0, 0, 0, 0, 0, 0, 0)
    assert_equal(SparseVector.new, v.map { 1 },
                 '.map() must iterate only over non-zero items')
  end

  def test_indexes
    v = SparseVector.new(10, 100, 1000, 500, 50, 5, 0)
    v[99] = 123
    assert_equal([0, 1, 2, 3, 4, 5, 99], v.indexes)

    v = SparseVector.new
    assert_equal([], v.indexes)

    v = SparseVector.new
    v[91238921] = 98328932
    assert_equal([91238921], v.indexes)
  end

  def test_length
    assert_equal(0, SparseVector.new.length)
    assert_equal(5, SparseVector.new(1, 2, 3, 4, 5).length)
    assert_equal(1, SparseVector.new(0, 0, 0, 0, 5).length)
    assert_equal(1, SparseVector.new(5, 0, 0, 0, 0).length)
  end
end