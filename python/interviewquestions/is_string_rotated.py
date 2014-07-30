"""
Write a code to test whether string s2 is obtained by rotating the string s1
to the left by exactly 2 places.

Example:
  S1="amazon" S2="azonam" return true
  S1="quality" S2="lityqua" return false
"""

import unittest


def is_string_rotated(s1, s2, n=2):
    l1, l2 = len(s1), len(s2)
    if l1 != l2:
        return False

    for i in range(l1):
        if s1[i] != s2[(i - n) % l1]:
            return False

    return True

def is_string_rotated2(s1, s2, n=2):
    """Alternative implementation"""
    return s1 == (s2[-n:] + s2[:-n])


class IsStringRotatedTest(unittest.TestCase):
    def test_is_string_rotated(self):
        self.assertTrue(is_string_rotated("amazon", "azonam"))
        self.assertFalse(is_string_rotated("quality", "lityqua"))

    def test_is_string_rotated2(self):
        self.assertTrue(is_string_rotated2("amazon", "azonam"))
        self.assertFalse(is_string_rotated2("quality", "lityqua"))
