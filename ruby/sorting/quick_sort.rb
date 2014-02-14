require 'test/unit'

def quicksort(values)
  return [] if values.empty?

  low = values.drop(1).select { |x| x < values[0] }
  high = values.drop(1).select { |x| x >= values[0] }

  quicksort(low) + [ values[0] ] + quicksort(high)
end

class QuickSortTest < Test::Unit::TestCase
  def test_quick_sort
    assert_equal((1..100).to_a, quicksort((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, quicksort((1..100).to_a))
    assert_equal((1..100).to_a, quicksort(100.downto(1).to_a))
    assert_equal([1], quicksort([1]))
    assert_equal([], quicksort([]))
  end
end