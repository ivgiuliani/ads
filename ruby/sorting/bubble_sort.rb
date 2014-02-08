require 'test/unit'

def bubblesort(values)
  sorted_array = Array.new(values)
  bubblesort!(sorted_array)
end

def bubblesort!(values)
  sorted = false

  until sorted
    sorted = true

    1.upto(values.length - 1).each do |i|
      if values[i - 1] > values[i]
        values[i - 1], values[i] = values[i], values[i - 1]
        sorted = false
      end
    end
  end

  values
end

class BubbleSortTest < Test::Unit::TestCase
  def test_bubble_sort
    v = (1..100).to_a.shuffle!
    vcopy = Array.new(v)
    assert_equal((1..100).to_a, bubblesort(v))
    assert_equal(v, vcopy,
                 'Method has modified array in place')

    assert_equal((1..100).to_a, bubblesort((1..100).to_a))
    assert_equal((1..100).to_a, bubblesort(100.downto(1).to_a))
  end

  def test_bubble_sort_in_place
    assert_equal((1..100).to_a, bubblesort!((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, bubblesort!((1..100).to_a))

    v = 100.downto(1).to_a
    assert_equal((1..100).to_a, bubblesort!(v))
    assert_not_equal(100.downto(1).to_a, v,
                     'Method hasn\'t modified the array in place')
  end
end