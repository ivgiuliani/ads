require 'test/unit'

def selectionsort(values)
  sorted_array = Array.new(values)
  selectionsort!(sorted_array)
end

def selectionsort!(values)
  length = values.length - 1
  (0..length).each do |i|
    _, min_idx = values[i..length].each_with_index.min
    values[i], values[min_idx + i] = values[min_idx + i], values[i]
  end

  values
end

class SelectionSortTest < Test::Unit::TestCase
  def test_selection_sort
    v = (1..100).to_a.shuffle!
    vcopy = Array.new(v)
    assert_equal((1..100).to_a, selectionsort(v))
    assert_equal(v, vcopy,
                 'Method has modified array in place')

    assert_equal((1..100).to_a, selectionsort((1..100).to_a))
    assert_equal((1..100).to_a, selectionsort(100.downto(1).to_a))
    assert_equal([1], selectionsort([1]))
    assert_equal([], selectionsort([]))
  end

  def test_selection_sort_in_place
    assert_equal((1..100).to_a, selectionsort!((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, selectionsort!((1..100).to_a))

    v = 100.downto(1).to_a
    assert_equal((1..100).to_a, selectionsort!(v))
    assert_not_equal(100.downto(1).to_a, v,
                     'Method hasn\'t modified the array in place')
    assert_equal([1], selectionsort!([1]))
    assert_equal([], selectionsort!([]))
  end
end