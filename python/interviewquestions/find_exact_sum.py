"""
You've built an in-flight entertainment system with on-demand movie streaming.
Users on longer flights like to start a second movie right when their first one
ends, but they complain that the plane usually lands before they can see the
ending. So you're building a feature for choosing two movies whose total
runtimes will equal the exact flight length.

Write a function that takes an integer flight_length (in minutes) and a list
of integers movie_lengths (in minutes) and returns a boolean indicating
whether there are two numbers in movie_lengths whose sum equals flight_length.

When building your function:

    Assume your users will watch exactly two movies
    Don't make your users watch the same movie twice
    Optimize for runtime over memory
"""

"""
We can reword this problem as finding the two numbers in the given list whose
sum is exactly flight_length.
"""

import unittest


def has_valid_movies_combo(l, flight_length):
    # we want to know if there are two items a, b in l such as:
    # a + b = flight_length
    # Given that:
    # b = flight_length - a
    # We prebuild a new list of (flight_length - a) items so all we have to do
    # is iterate again over the list and test that it contains b.
    # (We need a dict that also stores the idx of the item so that we can make
    # sure that the user won't see the same movie twice. Currently we assume
    # that the length of the movies is unique, as not assuming that would
    # introduce unnecessary complications (the dict must be a dict of lists)).
    sub = dict([(flight_length - item, idx) for idx, item in enumerate(l)])
    for idx, item in enumerate(l):
        if item in sub and sub[item] != idx:
            return True
    return False


class TestZeroSum(unittest.TestCase):
    def test_true(self):
        l = [30, 40, 50, 60, 70]
        self.assertTrue(has_valid_movies_combo(l, 80))
        self.assertTrue(has_valid_movies_combo(l, 100))
        self.assertTrue(has_valid_movies_combo(l, 130))
        self.assertTrue(has_valid_movies_combo(l, 90))

    def test_false(self):
        l = [30, 40, 50, 60, 70]
        self.assertFalse(has_valid_movies_combo(l, 10))
        self.assertFalse(has_valid_movies_combo(l, 30))
        self.assertFalse(has_valid_movies_combo(l, 40))
        self.assertFalse(has_valid_movies_combo(l, 14))

    def test_same_movie(self):
        l = [30, 40]
        self.assertFalse(has_valid_movies_combo(l, 60))


if __name__ == "__main__":
    unittest.main()
