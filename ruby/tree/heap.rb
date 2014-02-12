require 'test/unit'

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


class HeapTest < Test::Unit::TestCase
  def setup
    @heap = Heap.new
  end

  def test_empty
    assert_nil(@heap.min)
    @heap.pop
    assert_nil(@heap.min)
  end

  def test_one_item
    assert_nil(@heap.min)
    @heap.add(1)
    assert_equal(1, @heap.min)
    assert_equal(1, @heap.pop)
    assert_nil(@heap.min)
    assert_nil(@heap.pop)
  end

  def test_heapsort
    (1..1000).to_a.shuffle!.each { |x| @heap.add(x) }
    (1..1000).to_a.each do |x|
      assert_equal(x, @heap.pop)
    end
  end

  def test_duplicates
    1000.times { @heap.add(1816) }
    1000.times {  assert_equal(1816, @heap.pop) }
    assert_nil(@heap.pop)
  end

  def test_reverse
    (1..1000).to_a.reverse!.each { |x| @heap.add(x) }
    (1..1000).to_a.each do |x|
      assert_equal(x, @heap.pop)
    end
  end
end