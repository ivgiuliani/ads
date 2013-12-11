"""
Remove duplicate characters in a given string keeping only the first occurrence.
For example, if the input is "tree traversal" the output will be "tre avsl".
"""

import unittest


def remdup(string):
    """
    Remove duplicates from the string in O(n) time.
    """
    s = set()
    new_string = []
    # we can't update the set within a list comprehension, otherwise that
    # would be a great alternative
    for char in string:
        if char not in s:
            s.add(char)
            new_string.append(char)
    return "".join(new_string)


def remdup_alternative(string):
    """
    Remove duplicates from the string without using additional data structures
    (apart from the output buffer) but in O(n^2) time.
    """
    new_string = []

    for idx, char in enumerate(string):
        found = False
        for i in range(idx):
            if string[i] == char:
                found = True
                continue
        if not found:
            new_string.append(char)

    return "".join(new_string)


class TestDuplicateRemoval(unittest.TestCase):
    def test_duplicate_removal(self):
        self.assertEqual(remdup("stringstring"), "string")
        self.assertEqual(remdup("tree traversal"), "tre avsl")
        self.assertEqual(remdup("no duplicates"), "no duplicates")
        self.assertEqual(remdup("123456123451234"), "123456")
        self.assertEqual(remdup(""), "")

    def test_duplicate_removal_no_datastructures(self):
        self.assertEqual(remdup_alternative("stringstring"), "string")
        self.assertEqual(remdup_alternative("tree traversal"), "tre avsl")
        self.assertEqual(remdup_alternative("no duplicates"), "no duplicates")
        self.assertEqual(remdup_alternative("123456123451234"), "123456")
        self.assertEqual(remdup_alternative(""), "")


if __name__ == "__main__":
    unittest.main()