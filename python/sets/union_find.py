import unittest


class UnionFind(object):
    """
    A classic implementation of the union-find collection. This will only
    work for integer values. See below for an interesting "variation" of
    this collection.
    """
    def __init__(self):
        self.parents, self.sizes = [], []

    def find(self, k):
        self.__resize(k)
        if self.parents[k] is None:
            return k
        if self.parents[k] == k:
            return k
        return self.find(self.parents[k])

    def union(self, x, y):
        self.__resize(max(x, y))
        set1, set2 = self.find(x), self.find(y)
        if set1 == set2:
            # already in the same component
            return

        self.sizes[set1] = self.sizes[set1] or 1
        self.sizes[set2] = self.sizes[set2] or 1

        if self.sizes[set1] > self.sizes[set2]:
            self.sizes[set1] += self.sizes[set2]
            self.parents[set2] = set1
        else:
            self.sizes[set2] += self.sizes[set1]
            self.parents[set1] = set2

    def is_same_component(self, x, y):
        return self.find(x) == self.find(y)

    def __resize(self, m):
        if m < len(self.parents):
            # ignore the resize if we have enough space
            return
        else:
            l = len(self.parents)
            self.parents.extend([None] * (m + 1 - l))
            self.sizes.extend([0] * (m + 1 - l))


class UnionFindTest(unittest.TestCase):
    def setUp(self):
        self.uf = UnionFind()

    def test_union_find_empty(self):
        # all sets must be disjoint on an empty union find, hence each
        # .find() will return a different set id
        # (the set id they belong to though it's not guaranteed to be
        # the same value as the object)
        s = set()
        for i in range(100):
            s.add(self.uf.find(i))
        self.assertEqual(100, len(s))

    def test_union_find(self):
        self.uf.union(1, 2)
        self.uf.union(1, 3)
        self.uf.union(3, 5)

        self.assertTrue(self.uf.is_same_component(1, 2))
        self.assertTrue(self.uf.is_same_component(1, 3))
        self.assertTrue(self.uf.is_same_component(3, 5))
        self.assertTrue(self.uf.is_same_component(1, 5))
        self.assertTrue(self.uf.is_same_component(2, 5))
        self.assertFalse(self.uf.is_same_component(2, 6))

    def test_union_find_repeat(self):
        self.uf.union(1, 2)
        self.uf.union(2, 1)

        self.assertTrue(self.uf.is_same_component(1, 2))
        self.assertTrue(self.uf.is_same_component(2, 1))

    def test_union_find_same_item(self):
        self.assertTrue(self.uf.is_same_component(10, 10))
        self.uf.union(10, 10)
        self.assertTrue(self.uf.is_same_component(10, 10))

