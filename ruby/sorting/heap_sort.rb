require 'test/unit'

# essentially a copy of 'tree/heap.rb'
class Heap
  def initialize
    @heap = []
    @count = 0
  end

  def min
    @heap[0]
  end

  def add(val)
    @heap[@count] = val
    bubble_up!(@count)
    @count += 1
  end

  def pop
    return nil if @count == 0

    @count -= 1
    val = @heap[0]
    @heap[0], @heap[@count] = @heap[@count], nil
    bubble_down!(0)

    val
  end

  private

  def parent(index)
    return nil if index == 0
    (index - 1) / 2
  end

  def child_left(index)
    (2 * index) + 1
  end

  def child_right(index)
    (2 * index) + 2
  end

  def bubble_up!(index)
    par_idx = parent(index)
    return if par_idx.nil?

    if @heap[par_idx] > @heap[index]
      @heap[par_idx], @heap[index] = @heap[index], @heap[par_idx]
      bubble_up!(par_idx)
    end
  end

  def bubble_down!(index)
    left, right = child_left(index), child_right(index)
    min_idx = index

    # find out who is the smaller item, if the root, the left or the right child
    min_idx = left if left < @count && @heap[min_idx] > @heap[left]
    min_idx = right if right < @count && @heap[min_idx] > @heap[right]

    # stop bubbling down if the smallest item is the root
    if min_idx != index
      @heap[min_idx], @heap[index] = @heap[index], @heap[min_idx]
      bubble_down!(min_idx)
    end
  end
end

def heapsort(values)
  heap = Heap.new
  values.each { |val| heap.add(val) }
  result = []
  until (val = heap.pop).nil?
    result << val
  end
  result
end


class HeapSortTest < Test::Unit::TestCase
  def test_heap_sort
    assert_equal((1..100).to_a, heapsort((1..100).to_a.shuffle!))
    assert_equal((1..100).to_a, heapsort((1..100).to_a))
    assert_equal((1..100).to_a, heapsort(100.downto(1).to_a))
    assert_equal([1], heapsort([1]))
    assert_equal([], heapsort([]))
  end
end