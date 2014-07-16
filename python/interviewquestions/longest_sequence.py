"""
Given a list of distinct numbers, find the longest monotonically increasing
subsequence within that list.

For example:
  S = [2, 4, 3, 5, 1, 7, 6, 9, 8]  ->  [2, 3, 5, 6, 8]
                                   or  [2, 4, 5, 7, 8]
                                   or  [2, 4, 5, 7, 9]

If there's more than one solution, just return one of them.
"""

import unittest


def longest_sequence(lst):
    if not lst:
        return []

    lengths = [0] * len(lst)
    predecessors = [None] * len(lst)
    max_idx = 0

    for idx, item in enumerate(lst):
        # what's the longest subsequence until this point?
        # (whose last item < current item)
        max_length = 1
        lengths[idx] = 1
        predecessors[idx] = None

        for i, length in enumerate(lengths[:idx]):
            if length >= max_length and lst[i] < item:
                max_length = length + 1
                lengths[idx] = max_length
                predecessors[idx] = i

                max_idx = idx

    # proceed backward and rebuild the list
    longest = []
    while max_idx is not None:
        item = lst[max_idx]
        longest.append(item)
        max_idx = predecessors[max_idx]

    return list(reversed(longest))


class LongestSequenceTest(unittest.TestCase):
    def test_sequence_find(self):
        self.assertEqual([], longest_sequence([]))
        self.assertEqual([10], longest_sequence([10]))
        self.assertEqual([2, 4, 5, 7, 8], longest_sequence([2, 4, 3, 5, 1, 7, 6, 9, 8]))
        self.assertEqual([1, 2, 3], longest_sequence([1, 2, 3, 1, 2, 3, 1, 2, 3]))
        self.assertEqual([1, 2, 3], longest_sequence([1, 2, 3]))
        self.assertEqual([10, 20, 30], longest_sequence([10, 5, 4, 20, 3, 2, 30]))
