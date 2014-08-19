"""
Implement atoi() in Python (given a string, return a number).
Assume all the strings are always valid.
"""

import unittest


def atoi(string):
    l = len(string)
    t = 0
    v = 10 ** (l - 1)
    for ch in string:
        t += v * int(ch)
        v /= 10
    return t


def atoi2(string):
    l, t = len(string), 0
    for idx, ch in enumerate(string):
        t += int(ch) * (10 ** (l - idx - 1))
    return t


def atoi3(string):
    l = len(string)
    return sum([
        int(ch) * (10 ** (l - idx - 1))
        for idx, ch in enumerate(string)
    ])


class AtoITest(unittest.TestCase):
    def test_atoi(self):
        self.assertEqual(12345, atoi("12345"))
        self.assertEqual(1234, atoi("1234"))
        self.assertEqual(123, atoi("123"))
        self.assertEqual(12, atoi("12"))
        self.assertEqual(1, atoi("1"))
        self.assertEqual(0, atoi("0"))

    def test_atoi2(self):
        self.assertEqual(12345, atoi2("12345"))
        self.assertEqual(1234, atoi2("1234"))
        self.assertEqual(123, atoi2("123"))
        self.assertEqual(12, atoi2("12"))
        self.assertEqual(1, atoi2("1"))
        self.assertEqual(0, atoi2("0"))

    def test_atoi3(self):
        self.assertEqual(12345, atoi3("12345"))
        self.assertEqual(1234, atoi3("1234"))
        self.assertEqual(123, atoi3("123"))
        self.assertEqual(12, atoi3("12"))
        self.assertEqual(1, atoi3("1"))
        self.assertEqual(0, atoi3("0"))

