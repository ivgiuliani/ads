"""
Write an algorithm that brings all nonzero elements to the left of the array,
and returns the number of nonzero elements.

Example input: [1, 0, 2, 0, 0, 3, 4]
Example output: 4
                [1, 2, 3, 4, 0, 0, 0]

- The algorithm should operate in place, i.e. shouldn't create a new array.
- The order of nonzero elements does not matter
"""

import unittest


def nonzero(ar):
    nonzero_ptr, curr_ptr = 0, 0

    while curr_ptr < len(ar):
        if ar[curr_ptr] != 0:
            ar[nonzero_ptr] = ar[curr_ptr]
            nonzero_ptr += 1
        if curr_ptr > nonzero_ptr:
            # only "reset" the array if we are not overlapping the nonzero ptr
            # (i.e.: we found at least a 0)
            ar[curr_ptr] = 0
        curr_ptr += 1

    return nonzero_ptr


class NonZeroTest(unittest.TestCase):
    def test_nonzero(self):
        self.assertEqual(0, nonzero([]))
        v = []
        nonzero(v)
        self.assertEqual([], v)

        self.assertEqual(1, nonzero([1]))
        v = [1]
        nonzero(v)
        self.assertEqual([1], v)

        self.assertEqual(4, nonzero([1, 0, 2, 0, 0, 3, 4]))
        v = [1, 0, 2, 0, 0, 3, 4]
        nonzero(v)
        self.assertEqual([1, 2, 3, 4, 0, 0, 0], v)

        self.assertEqual(5, nonzero([1, 2, 3, 4, 5]))
        v = [1, 2, 3, 4, 5]
        nonzero(v)
        self.assertEqual([1, 2, 3, 4, 5], v)

        self.assertEqual(0, nonzero([0, 0, 0, 0, 0]))
        v = [0, 0, 0, 0, 0]
        nonzero(v)
        self.assertEqual([0, 0, 0, 0, 0], v)
