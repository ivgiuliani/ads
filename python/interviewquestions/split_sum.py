"""
Given a list of numbers, find the index of the number that splits
the list in two sublists with equals sum, excluding the index.

Assume there's always such a split.

For example:

[1, 2, 3, 4, 3, 2, 1]: index 3 splits the list into [1, 2, 3] and [3, 2, 1]
which have equal sum.
"""

import unittest


def split(lst):
    for i in range(len(lst)):
        left = sum(lst[:i])
        right = sum(lst[i + 1:])
        if left == right:
            return i
    return None


def split2(lst):
    curr = lst[0]
    right = sum(lst)
    right -= curr
    left = 0

    if left == right:
        return 0

    for i in range(1, len(lst)):
        left += lst[i]
        right -= curr
        curr = lst[i]
        if left == right:
            return i

    return None


class SplitTest(unittest.TestCase):
    def test_split(self):
        self.assertEqual(3, split([1, 2, 3, 4, 3, 2, 1]))
        self.assertEqual(0, split([1]))
        self.assertEqual(3, split([1, 2, 3, 4, 6]))
        self.assertEqual(4, split([9, 4, 6, 1, 0, 20]))
        self.assertEqual(1, split([1, 0, -1, 2]))

    def test_split2(self):
        self.assertEqual(3, split2([1, 2, 3, 4, 3, 2, 1]))
        self.assertEqual(0, split2([1]))
        self.assertEqual(3, split2([1, 2, 3, 4, 6]))
        self.assertEqual(4, split2([9, 4, 6, 1, 0, 20]))
        self.assertEqual(1, split2([1, 0, -1, 2]))
