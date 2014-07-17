"""
Given a roman number, return the actual integer number.

Ex:
MMCM = 2900
CXLIV = 144
IV = 4
"""

import unittest


VALUES = {
    "M": 1000,
    "D": 500,
    "C": 100,
    "L": 50,
    "X": 10,
    "V": 5,
    "I": 1,
}


def roman(string):
    # we assume that input is always valid
    string = string.upper()
    buf = VALUES[string[0]]
    added = False
    roman_sum = 0

    for ch in string[1:]:
        v = VALUES[ch]
        if buf is not None and buf < v:
            added = True
            roman_sum += v - buf
            buf = None
        else:
            added = False
            if buf is not None:
                roman_sum += buf
            buf = v

    if not added:
        roman_sum += buf
    return roman_sum


class RomanTest(unittest.TestCase):
    def test_roman(self):
        self.assertEqual(144, roman("CXLIV"))
        self.assertEqual(1, roman("I"))
        self.assertEqual(5, roman("V"))
        self.assertEqual(10, roman("X"))
        self.assertEqual(4, roman("IV"))
        self.assertEqual(9, roman("IX"))
        self.assertEqual(2900, roman("MMCM"))
        self.assertEqual(2003, roman("MMIII"))
        self.assertEqual(4999, roman("MMMMCMXCIX"))
