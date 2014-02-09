require 'test/unit'

def insertionsort(values)
  sorted_array = Array.new(values)
  insertionsort!(sorted_array)
end

def insertionsort!(values)
  0.upto(values.length - 1).each do |i|
    i.downto(1).each do |j|
      break if values[j] >= values[j - 1]
      values[j], values[j - 1] = values[j - 1], values[j]
    end
  end

  values
end

class InsertionSortTest < Test::Unit::TestCase
  def test_insertion_sort
    v = (1..100).to_a.shuffle!
    vcopy = Array.new(v)
    assert_equal((1..100).to_a, insertionsort(v))
    assert_equal(v, vcopy,
                 'Method has modified array in place')

    assert_equal((1..100).to_a, insertionsort((1..100).to_a))
    assert_equal((1..100).to_a, insertionsort(100.downto(1).to_a))
    assert_equal([1], insertionsort([1]))
    assert_equal([], insertionsort([]))
  end

  def test_insertion_sort_in_place
    assert_equal((1..100).to_a, insertionsort!((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, insertionsort!((1..100).to_a))

    v = 100.downto(1).to_a
    assert_equal((1..100).to_a, insertionsort!(v))
    assert_not_equal(100.downto(1).to_a, v,
                     'Method hasn\'t modified the array in place')
    assert_equal([1], insertionsort!([1]))
    assert_equal([], insertionsort!([]))
  end
end