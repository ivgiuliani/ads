require 'test/unit'

# Implementation of a dictionary backed by binary search trees

class BST
  class BSTNode
    attr_accessor :key, :value, :parent, :left, :right

    def initialize(key, value)
      @key, @value = key, value
      @parent, @left, @right = nil, nil, nil
    end

    def to_s
      '<%s:%s>' % [self.key, self.value]
    end

    def <(other)
      @key < other.key
    end

    def ==(other)
      @key == other.key
    end

    def has_key?(key)
      if key == @key
        return true
      end

      if key < @key
        (!@left.nil? && @left.has_key?(key))
      else
        (!@right.nil? && @right.has_key?(key))
      end
    end

    def length
      left, right = 0, 0
      left = @left.length unless @left.nil?
      right = @right.length unless @right.nil?
      1 + left + right
    end

    def __successor
      # can't be private because we need to access it from the static
      # delete method, but not exactly public since this wouldn't have
      # any use as a public api

      succ = @right
      until succ.left.nil?
        succ = succ.left
      end

      succ
    end

    def insert!(node)
      if node.key == @key
        # same key, update the value
        @value = node.value
      elsif node < self
        # node < self, go to the left tree
        if @left.nil?
          @left = node
          @left.parent = self
        else
          @left.insert!(node)
        end
      else
        # node > self, go the the right tree
        if @right.nil?
          @right = node
          @right.parent = self
        else
          @right.insert!(node)
        end
      end
    end

    # Delete a key from the BST identified by the given root.
    #
    # This method is static because semantically it belongs here, but we
    # need a reference to the actual BST root to delete any key in a given
    # binary search tree.
    #
    # Returns the new tree root or nil if there are no more leafs in the tree.
    #
    # Params:
    # +root+:: the root of the binary search tree
    # +key+:: the key to delete
    def self.delete!(root, key)
      if root.nil?
        return nil
      end

      if key < root.key
        BSTNode.delete!(root.left, key)
        return root
      elsif key > root.key
        BSTNode.delete!(root.right, key)
        return root
      end

      # at this point root == key
      # we can have three cases:
      #   1) the node is a leaf
      #   2) the node has only one child
      #   3) the node has two children
      #
      # the first two cases are straightforward, while in the last one we
      # must relabel the node with the key of its successor, which
      # happens to be the leftmost descendand in the right subtree
      parent = root.parent

      case
        when !root.left.nil? && !root.right.nil?
          # node has two children
          # rather than complicating things by changing pointers, just
          # replace keys and values
          succ = root.__successor
          root.key, succ.key = succ.key, root.key
          root.value, succ.value = succ.key, succ.value
          BSTNode.delete!(root.right, succ.key)
        when !root.left.nil?
          # 1 child (the left one)
          root.key, root.value = root.left.key, root.left.value
          root.left = nil
        when !root.right.nil?
          # 1 child (the right one)
          root.key, root.value = root.right.key, root.right.value
          root.right = nil
        else
          # leaf
          if !parent.nil? && parent.left == root
            parent.left = nil
          elsif !parent.nil? && parent.right == root
            parent.right = nil
          end

          return nil
      end

      root
    end

    def each
      unless @left.nil?
        @left.each { |x| yield(x) }
      end

      yield self

      unless @right.nil?
        @right.each { |x| yield(x) }
      end
    end

    def keys
      left, right = [], []
      left = @left.keys unless @left.nil?
      right = @right.keys unless @right.nil?

      left + [@key] + right
    end

    def values
      left, right = [], []
      left = @left.values unless @left.nil?
      right = @right.values unless @right.nil?

      left + [@value] + right
    end

    alias eql? ==
    alias include? has_key?
    alias key? has_key?
    alias member? has_key?
  end

  def initialize
    @root = nil
  end

  def length
    return 0 if @root.nil?
    @root.length
  end

  def empty?
    length == 0
  end

  def clear!
    @root = nil
  end

  def []=(key, val)
    newnode = BSTNode.new(key, val)
    if @root.nil?
      @root = newnode
    else
      @root.insert!(newnode)
    end
  end

  def [](key)
    node = @root
    until node.nil?
      if node.key == key
        return node.value
      elsif key < node.key
        node = node.left
      else
        node = node.right
      end
    end

    nil
  end

  def key?(key)
    return false if @root.nil?
    @root.key? key
  end

  def delete(key)
    unless @root.nil?
      @root = BSTNode.delete!(@root, key)
    end
  end

  def keys
    return [] if @root.nil?
    @root.keys
  end

  def values
    return [] if @root.nil?
    @root.values
  end

  def each
    return [] if @root.nil?
    @root.each do |node|
      yield node.key, node.value
    end
  end

  def each_key
    return [] if @root.nil?
    each { |key| yield key }
  end

  def each_value
    return [] if @root.nil?
    each { |_, value| yield value }
  end

  def flatten
    arr = []
    each do |key, value|
      arr << key
      arr << value
    end
    arr
  end

  alias each_pair each
  alias include? key?
end

class BSTTest < Test::Unit::TestCase
  def setup
    @bst = BST.new
  end

  def test_empty_dict
    assert_equal(@bst.length, 0)
    assert(@bst.empty?)
  end

  def test_add_items
    @bst['item1'] = 'value1'
    @bst['item2'] = 'value2'
    @bst['item3'] = 'value3'

    assert_equal(@bst.length, 3)
    assert_equal(@bst['item1'], 'value1')
    assert_equal(@bst['item2'], 'value2')
    assert_equal(@bst['item3'], 'value3')

    @bst['new item'] = 'new value'
    assert_equal(@bst.length, 4)
    assert_equal(@bst['new item'], 'new value')
  end

  def test_clear
    @bst['item1'] = 'value1'
    @bst['item2'] = 'value2'
    @bst['item3'] = 'value3'
    @bst.clear!

    assert(@bst.empty?)
  end

  def test_override
    @bst['key'] = 'initial value'
    assert_equal(@bst['key'], 'initial value')
    @bst['key'] = 'overriden value'
    assert_equal(@bst['key'], 'overriden value')
  end

  def test_unexisting_key
    assert_equal(@bst['key'], nil)
  end

  def test_large_add
    values = (1..1000).to_a.shuffle!
    values.each { |x| @bst[x] = x }
    values.each { |x|
      assert_not_nil(@bst[x])
    }
  end

  def test_contains
    @bst['key'] = 'value'
    @bst['another key'] = 'another value'
    @bst['hello'] = 'world'

    assert(@bst.key?('key'))
    assert(!@bst.key?('not a key'))
    assert(!@bst.key?('whatever'))
    assert(@bst.key?('hello'))
  end

  def test_delete
    @bst[2] = 2
    @bst[1] = 1
    @bst[7] = 7
    @bst[4] = 4
    @bst[8] = 8
    @bst[3] = 3
    @bst[6] = 6
    @bst[5] = 5

    # remove a leaf
    @bst.delete(5)
    assert_equal(@bst.length, 7)
    assert(!@bst.key?(5))

    # another deletion on the same key should be ignored
    @bst.delete(5)
    assert_equal(@bst.length, 7)
    assert(!@bst.key?(5))

    # remove a node with two children
    @bst.delete(4)
    assert_equal(@bst.length, 6)
    assert(!@bst.key?(4))

    # remove a node with one child
    @bst.delete(6)
    assert_equal(@bst.length, 5)
    assert(!@bst.key?(6))

    # remove the root
    @bst.delete(2)
    assert_equal(@bst.length, 4)
    assert(!@bst.key?(2))
  end

  def test_delete_single_node
    @bst['key'] = 'value'
    assert(@bst.key?('key'))
    assert(!@bst.empty?)

    @bst.delete('key')
    assert(!@bst.key?('key'))
    assert(@bst.empty?)
  end

  def test_delete_twice
    @bst['key'] = 'value'
    @bst.delete('key')
    @bst.delete('key')
    assert(!@bst.key?('key'))
    assert(@bst.empty?)
  end

  def test_delete_leaf
    @bst[5] = 5
    @bst[4] = 4
    @bst[6] = 6

    # the bst is built so that deleting 4, 6 and 5 in order
    # will delete a leaf every time
    @bst.delete(4)
    assert_equal(@bst.length, 2)

    @bst.delete(6)
    assert_equal(@bst.length, 1)

    @bst.delete(5)
    assert_equal(@bst.length, 0)
  end

  def test_delete_random_nodes
    values = (1..1000).to_a.shuffle!
    values.each { |x| @bst[x] = x }
    assert(!@bst.empty?)

    values.each { |x| @bst.delete(x) }
    assert(@bst.empty?)
  end

  def test_keys
    assert_equal(@bst.keys, [])
    @bst['a'], @bst['b'], @bst['c'], @bst['d'] = 1, 2, 3, 4
    assert_equal(@bst.keys, %w(a b c d))
  end

  def test_values
    assert_equal(@bst.values, [])
    @bst['a'], @bst['b'], @bst['c'], @bst['d'] = 1, 2, 3, 4
    assert_equal(@bst.values, [1, 2, 3, 4])
  end

  def test_keys_ordered
    # this being a BST, keys will always be returned ordered
    'hello world'.split('').each { |ch| @bst[ch] = ch }

    # (remember that duplicates are also deleted)
    assert_equal(@bst.keys, ' dehlorw'.split(''))
  end

  def test_each
    values = (1..1000).to_a
    values.shuffle.each { |x| @bst[x] = x }
    expected_length = values.length

    # check that we iterate on every item too
    i = 0

    @bst.each do |k, v|
      head, *values = values
      assert_equal(k, head)
      assert_equal(k, v)

      i += 1
    end

    assert_equal(expected_length, i)
  end

  def test_each_key
    values = (1..1000).to_a
    values.shuffle.each { |x| @bst[x] = x }

    @bst.each_key do |key|
      assert(values.include?(key))
    end
  end

  def test_each_value
    values = (1..1000).to_a
    values.shuffle.each { |x| @bst[x] = x }

    @bst.each_value do |value|
      assert(values.include?(value))
    end
  end

  def test_flatten
    (1..100).to_a.each do |x|
      @bst[x] = x
    end

    flattened = @bst.flatten
    (1..100).to_a.each do |x|
      key, value = flattened.shift 2
      assert_equal(x, key)
      assert_equal(key, value)
    end
  end
end