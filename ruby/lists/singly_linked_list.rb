require 'test/unit'

class SinglyLinkedList
  def initialize
    @head = nil
    @size = 0
  end

  def to_s
    arr, curr = [], @head
    until curr.nil?
      arr << curr.value
      curr = curr.next
    end
    '<' + arr.join(' ') + '>'
  end

  def append(value)
    insert(@size, value)
  end

  def get(index)
    raise ArgumentError, 'Only positive indexing is allowed' if index < 0

    node = get_node(index)
    return nil if node.nil?
    node.value
  end

  def insert(index, value)
    raise ArgumentError, 'Only positive indexing is allowed' if index < 0

    node = Node.new(value)
    prev = get_node(index - 1)

    if prev.nil?
      # we're at the start of the list so we're creating a new head node,
      # however if a head node already exist, we must insert our newly
      # created node before it
      if @head.nil?
        @head = node
      else
        node.next = @head
        @head = node
      end
    else
      next_node = prev.next
      prev.next = node
      node.next = next_node
    end

    @size +=1
  end

  def length
    @size
  end

  private

  class Node
    attr_accessor :value, :next

    def initialize(value)
      @value = value
      @next = nil
    end

    def to_s
      "<Node:#{@value}>"
    end
  end

  def get_node(index)
    return nil if index < 0

    curr = @head
    i = 0
    while !curr.nil? && !curr.next.nil? && i < index
      i += 1
      curr = curr.next
    end
    curr
  end

  def last
    get_node(@size - 1)
  end
end


class SinglyLinkedListTest < Test::Unit::TestCase
  def setup
    @list = SinglyLinkedList.new
  end

  def test_empty_list
    assert_equal(0, @list.length)
    assert_nil(@list.get(0))
    assert_nil(@list.get(123))
  end

  def test_positive_index
    assert_raise(ArgumentError) { @list.get(-1) }
    assert_raise(ArgumentError) { @list.insert(-1, 123) }
  end

  def test_append
    0.upto(100) { |x| @list.append(x) }
    0.upto(100) { |x| assert_equal(x, @list.get(x)) }
  end

  def test_insert
    @list.insert(0, 1000)
    assert_equal(1000, @list.get(0))

    @list.insert(1, 2000)
    assert_equal(2000, @list.get(1))

    @list.insert(1, 3000)
    assert_equal(3000, @list.get(1))
    assert_equal(2000, @list.get(2))

    @list.insert(0, 4000)
    assert_equal(4000, @list.get(0))
    assert_equal(1000, @list.get(1))
    assert_equal(3000, @list.get(2))
    assert_equal(2000, @list.get(3))

    @list.insert(4, 5000)
    assert_equal(4000, @list.get(0))
    assert_equal(1000, @list.get(1))
    assert_equal(3000, @list.get(2))
    assert_equal(2000, @list.get(3))
    assert_equal(5000, @list.get(4))

    @list.insert(3, 6000)
    assert_equal(4000, @list.get(0))
    assert_equal(1000, @list.get(1))
    assert_equal(3000, @list.get(2))
    assert_equal(6000, @list.get(3))
    assert_equal(2000, @list.get(4))
    assert_equal(5000, @list.get(5))

    @list.insert(9999, 7000)
    assert_equal(4000, @list.get(0))
    assert_equal(1000, @list.get(1))
    assert_equal(3000, @list.get(2))
    assert_equal(6000, @list.get(3))
    assert_equal(2000, @list.get(4))
    assert_equal(5000, @list.get(5))
    assert_equal(7000, @list.get(6))
  end

  def test_remove

  end

  def test_length
    assert_equal(0, @list.length)

    @list.append(1234)
    assert_equal(1, @list.length)

    @list.append(1234)
    assert_equal(2, @list.length)

    @list.append(1234)
    assert_equal(3, @list.length)

    @list.append(1234)
    assert_equal(4, @list.length)

    @list.append(1234)
    assert_equal(5, @list.length)

    @list.append(1234)
    assert_equal(6, @list.length)

    @list.append(1234)
    assert_equal(7, @list.length)
  end
end