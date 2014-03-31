import unittest
import random

random.seed()


class BSTDict(object):
    """"
    Implementation of a dictionary based on binary search trees.
    """

    class BSTNode(object):
        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.parent = None
            self.left, self.right = None, None

        def __str__(self):
            return "<%s:%s>" % (self.key, self.value)

        def __lt__(self, other):
            return self.key < other.key

        def __eq__(self, other):
            return isinstance(other, BSTDict.BSTNode) and self.key == other.key

        def __contains__(self, key):
            if self.key == key:
                return True
            if key < self.key:
                return self.left is not None and key in self.left
            else:
                return self.right is not None and key in self.right

        # for some reason PyCharm warns about a possible type exception about
        # self.left/right being None, but we are actually checking that...
        # noinspection PyTypeChecker
        def __len__(self):
            left = len(self.left) if self.left is not None else 0
            right = len(self.right) if self.right is not None else 0
            return 1 + left + right

        def insert(self, node):
            if node == self:
                # the key must be the same, there's no point in updating that too
                self.value = node.value
            elif node < self:
                if self.left is None:
                    self.left = node
                    self.left.parent = self
                else:
                    self.left.insert(node)
            else:  # node > self
                if self.right is None:
                    self.right = node
                    self.right.parent = self
                else:
                    self.right.insert(node)

        def keys(self):
            left = self.left.keys() if self.left is not None else []
            right = self.right.keys() if self.right is not None else []
            return left + [self.key] + right

        def values(self):
            left = self.left.values() if self.left is not None else []
            right = self.right.values() if self.right is not None else []
            return left + [self.value] + right

        def items(self):
            left = self.left.items() if self.left is not None else []
            right = self.right.items() if self.right is not None else []
            return left + [(self.key, self.value)] + right

        def __successor(self):
            succ = self.right
            while succ.left is not None:
                succ = succ.left
            return succ

        @staticmethod
        def delete(root, key):
            if root is None:
                # nothing to delete
                return None
            if key < root.key:
                BSTDict.BSTNode.delete(root.left, key)
                return root
            elif key > root.key:
                BSTDict.BSTNode.delete(root.right, key)
                return root

            # at this point root == key
            # we can have three cases:
            #   1) the node is a leaf
            #   2) the node has only one child
            #   3) the node has two children
            #
            # the first two cases are straightforward, while in the last one we
            # must relabel the node with the key of its successor, which
            # happens to be the leftmost descendant in the right subtree
            parent = root.parent
            if root.left is not None and root.right is not None:
                # node has two children
                # rather than complicating things by changing pointers, just
                # replace keys and values
                succ = root.__successor()
                root.key, succ.key = succ.key, root.key
                root.value, succ.value = succ.value, root.value
                BSTDict.BSTNode.delete(root.right, succ.key)
            elif root.left is not None:
                # 1 child (the left one)
                root.key = root.left.key
                root.value = root.left.value
                root.left = None
            elif root.right is not None:
                # 1 child (the right one)
                root.key = root.right.key
                root.value = root.right.value
                root.right = None
            else:
                # leaf
                if parent is not None and parent.left == root:
                    parent.left = None
                elif parent is not None and parent.right == root:
                    parent.right = None
                return None

            return root

        def p(self, indent_str=""):
            string = "%s%s:%s (parent:%s)" % (indent_str, self.key, self.value, self.parent)
            print(string)
            if self.left is None:
                print("%sLEFT NULL" % (indent_str + "  "))
            else:
                self.left.p(indent_str + "  ")
            if self.right is None:
                print("%sRIGHT NULL" % (indent_str + "  "))
            else:
                self.right.p(indent_str + "  ")

        def walk(self):
            if self.left is not None:
                for item in self.left.walk():
                    yield item
            yield self
            if self.right is not None:
                for item in self.right.walk():
                    yield item

    def __init__(self):
        self.root = None

    def __getitem__(self, item):
        node = self.root
        while node is not None:
            if node.key == item:
                return node.value
            elif item < node.key:
                node = node.left
            else:  # item > node.key
                node = node.right
        raise KeyError("key not found")

    def __setitem__(self, key, value):
        if self.root is None:
            self.root = BSTDict.BSTNode(key, value)
        else:
            self.root.insert(BSTDict.BSTNode(key, value))

    def __delitem__(self, key):
        if self.root is not None:
            self.root = BSTDict.BSTNode.delete(self.root, key)

    def __contains__(self, key):
        return self.root is not None and key in self.root

    def __len__(self):
        if self.root is None:
            return 0
        return len(self.root)

    def keys(self):
        if self.root is None:
            return []
        else:
            return self.root.keys()

    def values(self):
        if self.root is None:
            return []
        else:
            return self.root.values()

    def items(self):
        if self.root is None:
            return []
        else:
            return self.root.items()

    def iterkeys(self):
        if self.root is None:
            yield
        for node in self.root.walk():
            yield node.key

    def itervalues(self):
        if self.root is None:
            yield
        for node in self.root.walk():
            yield node.value

    def iteritems(self):
        if self.root is None:
            yield
        for node in self.root.walk():
            yield node.key, node.value

    def clear(self):
        self.root = None


class BSTTest(unittest.TestCase):
    def test_empty_dict(self):
        b = BSTDict()
        self.assertEqual(len(b), 0)
        self.assertEqual(len(b.keys()), 0)

    def test_add_items(self):
        b = BSTDict()
        b["item1"] = "value1"
        b["item2"] = "value2"
        b["item3"] = "value3"
        self.assertEqual(len(b), 3)
        self.assertEqual(b["item1"], "value1")
        self.assertEqual(b["item2"], "value2")
        self.assertEqual(b["item3"], "value3")
        b["new item"] = "new value"
        self.assertEqual(b["new item"], "new value")

    def test_large_add(self):
        vals = range(1, 10000)
        random.shuffle(vals)

        b = BSTDict()
        for val in vals:
            b[val] = val

        for val in vals:
            self.assertEqual(b[val], val)

    def test_override(self):
        b = BSTDict()
        b["key"] = "original value"
        self.assertEqual(b["key"], "original value")
        b["key"] = "new value"
        self.assertEqual(b["key"], "new value")

    def test_unexisting_key(self):
        b = BSTDict()
        with self.assertRaises(KeyError):
            # noinspection PyStatementEffect
            b["invalid key"]
            self.fail("should have thrown KeyError")

    def test_delete(self):
        b = BSTDict()
        b["2"] = "value 2"
        b["1"] = "value 1"
        b["7"] = "value 7"
        b["4"] = "value 4"
        b["8"] = "value 8"
        b["3"] = "value 3"
        b["6"] = "value 6"
        b["5"] = "value 5"

        # remove a leaf
        del b["5"]
        self.assertEqual(len(b), 7)
        self.assertFalse("5" in b)

        # another deletion on the same key should be ignored
        del b["5"]
        self.assertEqual(len(b), 7)
        self.assertFalse("5" in b)

        # remove a node with two children
        del b["4"]
        self.assertEqual(len(b), 6)
        self.assertFalse("4" in b)

        # remove a node with one child
        del b["6"]
        self.assertEqual(len(b), 5)
        self.assertFalse("6" in b)

        # remove the root
        del b["2"]
        self.assertEqual(len(b), 4)
        self.assertFalse("2" in b)

        # check that every other key is present
        self.assertEqual(b.keys(), ["1", "3", "7", "8"])

    def test_delete_single_node(self):
        b = BSTDict()
        b["key"] = "value"
        self.assertTrue("key" in b)
        self.assertEqual(len(b), 1)

        del b["key"]

        self.assertFalse("key" in b)
        self.assertEqual(len(b), 0)

    def test_delete_random_nodes(self):
        # this will probably cover any case I didn't think of
        # (or at least most of them)
        vals = range(1, 10000)
        random.shuffle(vals)

        b = BSTDict()
        for val in vals:
            b[val] = val

        for val in vals:
            del b[val]

        for val in vals:
            self.assertFalse(val in b)
        self.assertEqual(len(b), 0)

    def test_delete_twice(self):
        b = BSTDict()
        b["key"] = "value"
        del b["key"]
        try:
            del b["key"]
        except Exception as ex:
            self.fail("exception raised: %s" % ex)

    def test_delete_leaf(self):
        b = BSTDict()
        b[5] = 5
        b[4] = 4
        b[6] = 6

        # each of these operations will delete a leaf
        del b[4]
        self.assertEqual(len(b), 2)
        del b[6]
        self.assertEqual(len(b), 1)
        del b[5]
        self.assertEqual(len(b), 0)

    def test_keys(self):
        b = BSTDict()
        self.assertEqual(b.keys(), [])

        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        self.assertEqual(b.keys(), ["a", "b", "c", "d"])

    def test_keys_ordered(self):
        # this being a BST, keys will always returned alphabetically ordered
        b = BSTDict()
        self.assertEqual(b.keys(), [])

        for item in "hello world":
            b[item] = item

        self.assertEqual(b.keys(), [" ", "d", "e", "h", "l", "o", "r", "w"])

    def test_values(self):
        b = BSTDict()
        self.assertEqual(b.values(), [])

        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        self.assertEqual(b.values(), [1, 2, 3, 4])

    def test_items(self):
        b = BSTDict()
        self.assertEqual(b.items(), [])

        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        self.assertEqual(b.items(), [
            ("a", 1), ("b", 2), ("c", 3), ("d", 4)
        ])

    def test_iterkeys(self):
        b = BSTDict()
        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        expected_list = ["a", "b", "c", "d"]
        for expected, item in zip(expected_list, b.iterkeys()):
            self.assertEqual(expected, item)

        # the generator must reset itself
        for expected, item in zip(expected_list, b.iterkeys()):
            self.assertEqual(expected, item)

    def test_itervalues(self):
        b = BSTDict()
        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        expected_list = [1, 2, 3, 4]
        for expected, item in zip(expected_list, b.itervalues()):
            self.assertEqual(expected, item)

        # the generator must reset itself
        for expected, item in zip(expected_list, b.itervalues()):
            self.assertEqual(expected, item)

    def test_iteritems(self):
        b = BSTDict()
        b["a"], b["b"], b["c"], b["d"] = 1, 2, 3, 4
        expected_list = [("a", 1), ("b", 2), ("c", 3), ("d", 4)]
        for expected, item in zip(expected_list, b.iteritems()):
            self.assertEqual(expected, item)

        # the generator must reset itself
        for expected, item in zip(expected_list, b.iteritems()):
            self.assertEqual(expected, item)

    def test_clear(self):
        b = BSTDict()
        for val in range(1, 100):
            b[val] = val
        for val in range(1, 100):
            self.assertIsNotNone(b[val])
        b.clear()
        for val in range(1, 100):
            with self.assertRaises(KeyError):
                # noinspection PyStatementEffect
                b[val]

    def test_contains(self):
        b = BSTDict()
        b["key"] = "value"
        b["another key"] = "another value"
        b["hello"] = "world"
        self.assertTrue("key" in b)
        self.assertTrue("another key" in b)
        self.assertTrue("hello" in b)
        self.assertFalse("not a key" in b)
        self.assertFalse("invalid" in b)
        self.assertFalse("whatever" in b)

if __name__ == "__main__":
    unittest.main()
