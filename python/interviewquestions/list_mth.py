"""
Given an array of characters, convert it to a linked list and find the
mth-to-last of the list.
"""

import unittest
import itertools


def mth_to_last(lst, mth):
    return lst[::-1][mth - 1]


def mth_to_last_alternative(lst, mth):
    """
    Because the other one was way too easy and leveraged python way more than
    it should in a proper job interview
    """
    # use a second pointer to the list that is m items forward,
    # iterate both lists together and stop as soon as the second list
    # runs out of elements
    for i1, i2 in itertools.izip_longest(lst, lst[mth:]):
        # I guess even this one uses a bit too much python maybe?
        # anyway, izip_longest is needed to pad to the longest of the lists,
        # otherwise (the classic 'zip') will truncate the iteration at the shortest
        # of the two
        if i2 is None:
            return i1
    return None


class TestListMth(unittest.TestCase):
    def test_list_mth(self):
        chars = list("abcdefghijklmnopqrstuvwxyz")
        self.assertEqual(mth_to_last(chars[:4], 4), "a")
        self.assertEqual(mth_to_last(chars[:4], 3), "b")
        self.assertEqual(mth_to_last(chars[:4], 2), "c")
        self.assertEqual(mth_to_last(chars[:4], 1), "d")
        self.assertEqual(mth_to_last(chars, 26), "a")
        self.assertEqual(mth_to_last(chars, 1), "z")

    def test_list_mth_alternative(self):
        chars = list("abcdefghijklmnopqrstuvwxyz")
        self.assertEqual(mth_to_last_alternative(chars[:4], 4), "a")
        self.assertEqual(mth_to_last_alternative(chars[:4], 3), "b")
        self.assertEqual(mth_to_last_alternative(chars[:4], 2), "c")
        self.assertEqual(mth_to_last_alternative(chars[:4], 1), "d")
        self.assertEqual(mth_to_last_alternative(chars, 26), "a")
        self.assertEqual(mth_to_last_alternative(chars, 1), "z")


if __name__ == "__main__":
    unittest.main()
