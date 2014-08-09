"""
You are given an array of both negative and positive integers.
You need to rearrange the array such that positive and negative numbers alternate.
Also, the order should be same as previous array.

eg. -2 3 4 5 -1 -6 7 9 1
result: 3 -2 4 -1 5 -6 7 9 1
"""

import unittest
import itertools


def alternate(arr):
    pos = (i for i in arr if i >= 0)
    neg = (i for i in arr if i < 0)

    res = []
    for p, n in itertools.izip_longest(pos, neg):
        if p is not None:
            res.append(p)
        if n is not None:
            res.append(n)

    return res


class AlternateTest(unittest.TestCase):
    def test_alternate(self):
        self.assertEqual([3, -2, 4, -1, 5, -6, 7, 9, 1],
                         alternate([-2, 3, 4, 5, -1, -6, 7, 9, 1]))
        self.assertEqual([1, 2, 3, 4, 5], alternate([1, 2, 3, 4, 5]))
        self.assertEqual([-1, -2, -3, -4, -5], alternate([-1, -2, -3, -4, -5]))
        self.assertEqual([1, -4, 2, -5, 3, -6], alternate([1, 2, 3, -4, -5, -6]))
