"""
Find the longest sequence of prefix shared by all the words in a string.

Example:
"abcdef abcdxxx abcdabcdef abcyy" => "abc"
"""

import unittest
import itertools


def longest_prefix(string):
    words = string.split()
    prefix = words[0]
    for word in words[1:]:
        l = 0
        for pr, ch in zip(prefix, word):
            if pr != ch:
                break
            l += 1
        prefix = prefix[:min(l, len(prefix))]
    return prefix


def longest_prefix_itertools(string):
    """A slower (because of the need to copy things around) but more
    elegant implementation, based on itertools.
    """
    words = string.split()
    prefix = words[0]
    for word in words[1:]:
        same = lambda v: v[0] == v[1]
        current_prefix = list(itertools.takewhile(same, zip(prefix, word)))
        prefix = prefix[:min(len(current_prefix), len(prefix))]
    return prefix


class LongestPrefixTest(unittest.TestCase):
    def test_longest_prefix(self):
        self.assertEqual("abc", longest_prefix("abcdef abcdxxx abcdabcdef abcyy"))
        self.assertEqual("abcdef", longest_prefix("abcdef abcdefxxx abcdefdabcdef abcdefyy"))
        self.assertEqual("", longest_prefix("abcdef defg hijkl lmnopq"))
        self.assertEqual("", longest_prefix("abcdef defg hijkl lmnopq"))

    def test_longest_prefix_itertools(self):
        self.assertEqual("abc", longest_prefix_itertools("abcdef abcdxxx abcdabcdef abcyy"))
        self.assertEqual("abcdef", longest_prefix_itertools("abcdef abcdefxxx abcdefdabcdef abcdefyy"))
        self.assertEqual("", longest_prefix_itertools("abcdef defg hijkl lmnopq"))
        self.assertEqual("", longest_prefix_itertools("abcdef defg hijkl lmnopq"))
