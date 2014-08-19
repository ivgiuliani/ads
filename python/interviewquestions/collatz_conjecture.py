"""
Write a program to prove the Collatz conjecture:

- if a number is even, divide it by 2
- if a number is odd, multiply it by 3 and add 1
- repeat until you get to 1
"""

import unittest


def collatz_recursive(n):
    if n == 1:
        return n
    if n % 2 == 0:
        return collatz_recursive(n / 2)
    return collatz_recursive((n * 3) + 1)


def collatz(n):
    while n != 1:
        if n % 2 == 0:
            n /= 2
        else:
            n = (n * 3) + 1
    return n


class CollatzTest(unittest.TestCase):
    def test_collatz_recursive(self):
        self.assertEqual(1, collatz_recursive(100))
        self.assertEqual(1, collatz_recursive(90))
        self.assertEqual(1, collatz_recursive(12345))
        self.assertEqual(1, collatz_recursive(123))
        self.assertEqual(1, collatz_recursive(1))

    def test_collatz(self):
        self.assertEqual(1, collatz(100))
        self.assertEqual(1, collatz(90))
        self.assertEqual(1, collatz(12345))
        self.assertEqual(1, collatz(123))
        self.assertEqual(1, collatz(1))
