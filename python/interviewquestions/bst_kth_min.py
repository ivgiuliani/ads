"""
Find the k-th min of a binary search tree.

Bonus: find the k-th max.
"""

import unittest

class BST(object):
    def __init__(self, value):
        self.value = value
        self.left, self.right = None, None


def build_bst():
    _4 = BST(4)
    _8 = BST(8)
    _10 = BST(10)
    _12 = BST(12)
    _14 = BST(14)
    _20 = BST(20)
    _22 = BST(22)

    #         20
    #      /      \
    #     8        22
    #   /   \
    # 4     12
    #      /  \
    #    10    14

    root = _20
    _20.left = _8
    _20.right = _22
    _8.left = _4
    _8.right = _12
    _12.left = _10
    _12.right = _14

    return root


def find_kth_min(bst, k):
    # An in-order visit on a BST will produce as sorted visit, if keep track
    # (through `curr`) of how many nodes we visited, then the k-th node we
    # visit is the node we're looking for.
    def visit(node, curr=0):
        if not node:
            return None, curr

        v, curr = visit(node.left, curr)
        if v:
            return v, curr

        if curr == k:
            return node.value, curr

        curr += 1

        v, curr = visit(node.right, curr)
        if v:
            return v, curr

        return None, curr

    return visit(bst)[0]


def find_kth_max(bst, k):
    # Same as find_kth_min, but because we're looking for the max now, we visit the right
    # subtree first.
    def visit(node, curr=0):
        if not node:
            return None, curr

        v, curr = visit(node.right, curr)
        if v:
            return v, curr

        if curr == k:
            return node.value, curr

        curr += 1

        v, curr = visit(node.left, curr)
        if v:
            return v, curr

        return None, curr

    return visit(bst)[0]


class TestBst(unittest.TestCase):
    def test_find_kth_min_empty(self):
        self.assertIsNone(find_kth_min(None, 0))
        self.assertIsNone(find_kth_min(None, 1))
        self.assertIsNone(find_kth_min(None, 2))
        self.assertIsNone(find_kth_min(None, 3))

    def test_find_kth_min(self):
        self.assertEquals(4, find_kth_min(build_bst(), 0))
        self.assertEquals(8, find_kth_min(build_bst(), 1))
        self.assertEquals(10, find_kth_min(build_bst(), 2))
        self.assertEquals(12, find_kth_min(build_bst(), 3))

    def test_find_kth_max_empty(self):
        self.assertIsNone(find_kth_max(None, 0))
        self.assertIsNone(find_kth_max(None, 1))
        self.assertIsNone(find_kth_max(None, 2))
        self.assertIsNone(find_kth_max(None, 3))

    def test_find_kth_max(self):
        self.assertEquals(22, find_kth_max(build_bst(), 0))
        self.assertEquals(20, find_kth_max(build_bst(), 1))
        self.assertEquals(14, find_kth_max(build_bst(), 2))
        self.assertEquals(12, find_kth_max(build_bst(), 3))


if __name__ == "__main__":
    unittest.main()

