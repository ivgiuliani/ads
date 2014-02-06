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

    alias eql? ==

    def has_key?(key)
      if key == @key
        return true
      end

      if key < @key
        (not @left.nil? and @left.has_key?(key))
      else
        (not @right.nil? and @right.has_key?(key))
      end
    end

    alias include? has_key?
    alias key? has_key?
    alias member? has_key?

    def length
      left, right = 0, 0
      left = @left.length unless @left.nil?
      right = @right.length unless @right.nil?
      1 + left + right
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
  end

  def initialize
    @root = nil
  end

  def length
    if @root.nil?
      0
    else
      @root.length
    end
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
    if @root.nil?
      false
    else
      @root.key?(key)
    end
  end
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
end