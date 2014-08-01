"""
Given 2 set of arrays of size N (both sorted and at least one of them non-empty)
find the median of the resulting array of size 2N.

Do not sort the resulting array.
"""

import unittest


def find_median(a1, a2):
    l1, l2 = len(a1), len(a2)
    p1, p2 = 0, 0

    median = (l1 + l2) / 2
    while p1 < l1 and p2 < l2:
        if (p1 + p2) == median:
            return min(a1[p1], a2[p2])

        if a1[p1] < a2[p2]:
            p1 += 1
        else:
            p2 += 1

    for p1 in range(p1, l1):
        if (p1 + p2) == median:
            return a1[p1]

    for p2 in range(p2, l2):
        if (p1 + p2) == median:
            return a2[p2]

    return None


class FindMedianTest(unittest.TestCase):
    def test_find_median(self):
        self.assertEqual(10, find_median([0, 2, 8, 22, 34, 50], [1, 3, 4, 10, 15, 26]))
        self.assertEqual(2, find_median([], [1, 2, 3]))
        self.assertEqual(1, find_median([0], [1]))
        self.assertEqual(3, find_median([0, 1, 2], [3, 4, 5, 6]))
        self.assertEqual(1, find_median([1], [1]))
        self.assertEqual(5, find_median([1, 3, 5, 7], [2, 4, 6, 8]))
        self.assertEqual(5, find_median([5, 6, 7, 8], [1, 2, 3, 4]))
