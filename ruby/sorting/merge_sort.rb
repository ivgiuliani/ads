require 'test/unit'

def merge(left, right)
  l, r, merged = 0, 0, []

  while l < left.length && r < right.length
    if left[l] < right[r]
      merged << left[l]
      l += 1
    else
      merged << right[r]
      r += 1
    end
  end

  while l < left.length;  merged << left[l];  l += 1; end
  while r < right.length; merged << right[r]; r += 1; end

  merged
end

def mergesort(values)
  return values if values.length <= 1
  mid = values.length / 2
  merge(mergesort(values.take(mid)),
        mergesort(values.drop(mid)))
end

class MergeSortTest < Test::Unit::TestCase
  def test_merge_sort
    assert_equal((1..100).to_a, mergesort((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, mergesort((1..100).to_a))
    assert_equal((1..100).to_a, mergesort(100.downto(1).to_a))
    assert_equal([1], mergesort([1]))
    assert_equal([], mergesort([]))
  end
end