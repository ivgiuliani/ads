require 'test/unit'
require 'set'
require 'benchmark'

# A classic implementation of the union-find collection. This will only
# work for integer values. See below for an interesting "variation" of
# this collection.
class UnionFind
  def initialize
    @size = 0
    @parents = []
    @sizes = []
  end

  def find(k)
    return k if @parents[k].nil?
    @parents[k] == k ? k : find(@parents[k])
  end

  def union(x, y)
    resize!([x, y].max) if [x, y].max > @size
    set1, set2 = find(x), find(y)
    return if set1 == set2 # same set already

    @sizes[set1] = 1 if @sizes[set1].nil?
    @sizes[set2] = 1 if @sizes[set2].nil?

    if @sizes[set1] > @sizes[set2]
      @sizes[set1] += @sizes[set2]
      @parents[set2] = set1
    else
      @sizes[set2] += @sizes[set1]
      @parents[set1] = set2
    end
  end

  def same?(x, y)
    find(x) == find(y)
  end

  private
  def resize!(m)
    return if m < @size
    @size = m
  end

  alias same_component? same?
end


# Dict-based implementation of a union-find. This will accept
# any object but won't be memory constrained.
class UnionFindDict
  def initialize
    @parents = {}
    @sizes = {}
    @sizes.default = 1
  end

  # In this case find() will always return the object with the larger
  # component and not a component #id.
  def find(k)
    return k if @parents[k].nil?
    @parents[k] == k ? k : find(@parents[k])
  end

  def union(x, y)
    set1, set2 = find(x), find(y)
    return if set1 == set2 # same set already

    if @sizes[set1] > @sizes[set2]
      @sizes[set1] += @sizes[set2]
      @parents[set2] = set1
    else
      @sizes[set2] += @sizes[set1]
      @parents[set1] = set2
    end
  end

  def same?(x, y)
    find(x) == find(y)
  end

  alias same_component? same?
end


# Implementation of a probabilistic version of a union-find datastructure.
#
# This collection ensures that if !@same?(x, y) then we are sure that
# x and y belong to different components, however the contrary is not
# guaranteed since there may be collisions in the internal buffer.
#
# This collection however accepts any kind of hashable object such as
# strings and is memory-constrained.
#
# This implementation is optimistical, in the sense that it assumes that there
# won't be collisions among the contained objects. However this won't be
# necessarily true in a real-world application, so a proper tuning of the max
# buffer size is required.
class UnionFindProbabilistic
  # Initializes a new union-find data structure. The max size parameter
  # refers to the internal buffer, and not to the max number of items this
  # collection can hold. Hence proper tuning is required to avoid collisions.
  def initialize(max_size=2048)
    # TODO: I'm fairly sure we can steal some ideas from bloom filters and
    # calculate max_size automatically as a measure of the expected error rate
    @max_size = max_size
    @size = 0
    @parents = []
    @sizes = []
  end

  def find(k)
    _find(k.hash % @max_size)
  end

  def union(x, y)
    _union(x.hash % @max_size, y.hash % @max_size)
  end

  def same?(x, y)
    find(x) == find(y)
  end

  private

  def _find(k)
    return k if @parents[k].nil?
    @parents[k] == k ? k : _find(@parents[k])
  end

  def _union(x, y)
    resize!([x, y].max) if [x, y].max > @size
    set1, set2 = _find(x), _find(y)
    return if set1 == set2 # same set already

    @sizes[set1] = 1 if @sizes[set1].nil?
    @sizes[set2] = 1 if @sizes[set2].nil?

    if @sizes[set1] > @sizes[set2]
      @sizes[set1] += @sizes[set2]
      @parents[set2] = set1
    else
      @sizes[set2] += @sizes[set1]
      @parents[set1] = set2
    end
  end

  def resize!(m)
    return if m < @size
    @size = m
  end

  alias same_component? same?
end


class TestUnionFind < Test::Unit::TestCase
  def setup
    @uf = UnionFind.new
  end

  def test_union_find_empty
    # all sets must be disjoint on an empty union find, hence each
    # .find() will return a different set id
    # (the #set id they belong to though it's not guaranteed to be
    # the same value as the object)
    s = Set.new
    (1..100).each do |i|
      s.add(@uf.find(i))
    end

    assert_equal(s.length, 100)
  end

  def test_union_find
    @uf.union(1, 2)
    @uf.union(1, 3)
    @uf.union(3, 5)

    assert(@uf.same?(1, 2))
    assert(@uf.same?(1, 3))
    assert(@uf.same?(3, 5))
    assert(@uf.same?(1, 5))
    assert(@uf.same?(2, 5))
    assert(!@uf.same?(2, 6))
  end

  def test_union_find_repeat
    @uf.union(1, 2)
    @uf.union(2, 1)

    assert(@uf.same?(1, 2))
    assert(@uf.same?(2, 1))
  end

  def test_union_find_same_item
    assert(@uf.same?(10, 10))
    @uf.union(10, 10)
    assert(@uf.same?(10, 10))
  end
end


class TestDictBackedUnionFind < Test::Unit::TestCase
  def setup
    @uf = UnionFindDict.new
  end

  def test_union_find_empty
    # all sets must be disjoint on an empty union find, hence each
    # .find() will return a different set id
    # (the #set id they belong to though it's not guaranteed to be
    # the same value as the object)
    s = Set.new
    (1..100).each do |i|
      s.add(@uf.find(i))
    end

    assert_equal(s.length, 100)
  end

  def test_union_find
    @uf.union(1, 2)
    @uf.union(1, 3)
    @uf.union(3, 5)

    assert(@uf.same?(1, 2))
    assert(@uf.same?(1, 3))
    assert(@uf.same?(3, 5))
    assert(@uf.same?(1, 5))
    assert(@uf.same?(2, 5))
    assert(!@uf.same?(2, 6))
  end

  def test_union_find_repeat
    @uf.union(1, 2)
    @uf.union(2, 1)

    assert(@uf.same?(1, 2))
    assert(@uf.same?(2, 1))
  end

  def test_union_find_same_item
    assert(@uf.same?(10, 10))
    @uf.union(10, 10)
    assert(@uf.same?(10, 10))
  end

  def test_union_find_strings
    @uf.union('a string', 'another string')
    @uf.union('hello', 'world')

    assert(@uf.same?('a string', 'another string'))
    assert(@uf.same?('hello', 'world'))
    assert(!@uf.same?('hello', 'another string'))
    assert(!@uf.same?('a string', 'world'))
  end

  def test_union_find_objects
    dict1 = { :key1 => :yes, :key2 => :no }
    dict2 = { :key3 => :yes, :key4 => :no }

    @uf.union(dict1, 'string1')
    @uf.union(dict2, 'string2')
    @uf.union('string1', 1000000)

    assert(@uf.same?(dict1, 'string1'))
    assert(@uf.same?(dict2, 'string2'))
    assert(@uf.same?(dict1, 1000000))

    assert(!@uf.same?(dict1, 'string2'))
    assert(!@uf.same?(dict2, 1000000))
  end
end


class TestProbabilisticUnionFind < Test::Unit::TestCase
  def test_union_find_empty
    # all sets must be disjoint on an empty union find, hence each
    # .find() will return a different set id
    # (the #set id they belong to though it's not guaranteed to be
    # the same value as the object)
    uf = UnionFindProbabilistic.new(2048)

    s = Set.new
    (1..100).each do |i|
      s.add(uf.find(i))
    end

    assert_equal(s.length, 100)
  end

  def test_union_find
    uf = UnionFindProbabilistic.new(2048)

    uf.union(1, 2)
    uf.union(1, 3)
    uf.union(3, 5)

    assert(uf.same?(1, 2))
    assert(uf.same?(1, 3))
    assert(uf.same?(3, 5))
    assert(uf.same?(1, 5))
    assert(uf.same?(2, 5))
    assert(!uf.same?(2, 6))
  end

  def test_union_find_repeat
    uf = UnionFindProbabilistic.new(2048)

    uf.union(1, 2)
    uf.union(2, 1)

    assert(uf.same?(1, 2))
    assert(uf.same?(2, 1))
  end

  def test_union_find_same_item
    uf = UnionFindProbabilistic.new(2048)

    assert(uf.same?(10, 10))
    uf.union(10, 10)
    assert(uf.same?(10, 10))
  end

  def test_union_find_strings
    uf = UnionFindProbabilistic.new(2048)

    uf.union('a string', 'another string')
    uf.union('hello', 'world')

    assert(uf.same?('a string', 'another string'))
    assert(uf.same?('hello', 'world'))
    assert(!uf.same?('hello', 'another string'))
    assert(!uf.same?('a string', 'world'))
  end

  def test_union_find_small_buffer
    # the buffer now is small enough that we're sure we're going
    # to have collisions, hence sets now will NOT be disjoint
    uf = UnionFindProbabilistic.new(200)

    s = Set.new
    (1..100).each do |i|
      s.add(uf.find(i))
    end

    assert(s.length < 100)
  end

  def test_union_find_objects
    dict1 = { :key1 => :yes, :key2 => :no }
    dict2 = { :key3 => :yes, :key4 => :no }

    uf = UnionFindProbabilistic.new(2048)
    uf.union(dict1, 'string1')
    uf.union(dict2, 'string2')
    uf.union('string1', 1000000)

    assert(uf.same?(dict1, 'string1'))
    assert(uf.same?(dict2, 'string2'))
    assert(uf.same?(dict1, 1000000))

    assert(!uf.same?(dict1, 'string2'))
    assert(!uf.same?(dict2, 1000000))
  end
end

iterations = 1000
Benchmark.bmbm(20) do |bm|
  # generate 100 random unions out of 1000 unique items
  items = (1..1000).to_a
  unions = 100.times.map { [ items.sample, items.sample] }

  bm.report('classic') do
    iterations.times do
      ufclassic = UnionFind.new
      unions.each { |v1, v2| ufclassic.union(v1, v2) }
      res = unions.map { |v1, v2| ufclassic.find(v1) == ufclassic.find(v2) }
      puts 'ERROR(classic)' unless res.all?
    end
  end

  bm.report('dict-backed') do
    iterations.times do
      ufdict = UnionFindDict.new
      unions.each { |v1, v2| ufdict.union(v1, v2) }
      res = unions.map { |v1, v2| ufdict.find(v1) == ufdict.find(v2) }
      puts 'ERROR(dict-backed)' unless res.all?
    end
  end

  bm.report('probabilistic') do
    iterations.times do
      ufprobabilistic = UnionFindProbabilistic.new(2048)
      unions.each { |v1, v2| ufprobabilistic.union(v1, v2) }
      res = unions.map { |v1, v2| ufprobabilistic.find(v1) == ufprobabilistic.find(v2) }
      puts 'ERROR(probabilistic)' unless res.all?
    end
  end
end