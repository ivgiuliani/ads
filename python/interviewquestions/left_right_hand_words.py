# You have a list of several million words unsorted.
# Find the largest and the smallest words that can be typed by a single hand
# on a qwerty-style keyboard.
# Following the rules of finger placement, a word can either be typed fully on
# the left-hand side of the keyboard, the right-hand side, or both.
# Find the largest and smallest left-hand word(s),
# and the largest and smallest right-hand word(s).
#
# You are given:
#   - millions of words, unsorted
#   - set of left-hand chars: a,s,d,f, ...

import unittest


LEFT = set("qwerasdfzxcvtgb")
RIGHT = set("uiopjklmnhy")


def search(words):
    smallest_left, smallest_right = words[0], words[0]
    largest_left, largest_right = words[0], words[0]

    def _assign(w, small, large):
        if len(w) < len(small):
            small = w
        elif len(w) > len(large):
            large = w
        return small, large

    for word in words:
        s = set(word)
        if not s.difference(LEFT):
            smallest_left, largest_left = _assign(word,
                                                  smallest_left,
                                                  largest_left)
        elif not s.difference(RIGHT):
            smallest_right, largest_right = _assign(word,
                                                    smallest_right,
                                                    largest_right)

    return smallest_left, smallest_right, largest_left, largest_right


class LeftRightHandWordsTest(unittest.TestCase):
    def test_search(self):
        res = search([
            "qwert",
            "rararararararara",
            "asd",
            "asdlkj",
            "iosakbrknerlk",
            "mmmmmm",
            "llllllllllllllllllll",
            "laskdj",
            "llll",
            "qqqqqqqqqqqqqqqqqqqq",
            "p"])
        smallest_left, smallest_right, largest_left, largest_right = res

        self.assertEquals("asd", smallest_left)
        self.assertEquals("p", smallest_right)
        self.assertEquals("qqqqqqqqqqqqqqqqqqqq", largest_left)
        self.assertEquals("llllllllllllllllllll", largest_right)
