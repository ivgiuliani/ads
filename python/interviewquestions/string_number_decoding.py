"""
1 represent A, 2 rep B etc and 26 rep Z.
Given a number, find the number of possible decoding for this number.
No need to consider number starts with zero.

1=A   6=F  11=K  16=P  21=U  26=Z
2=B   7=G  12=L  17=Q  22=V
3=C   8=H  13=M  18=R  23=W
4=D   9=I  14=N  19=S  24=X
5=E  10=J  15=O  20=T  25=Y

Examples:
  1234 -> 3 (ABCD, AWD, LCD)
  12345 -> 3 (ABCDE, AWDE, LCDE)
  11111 -> 8 (AAAAA, AAAK, AKAA, AAKA, KAAA, AKK, KAK, KKA)
  12 -> 2 (AB, L)
"""

import unittest
import string

LETTERS = dict([(str(i + 1), string.ascii_uppercase[i]) for i in range(26)])


def decoding(num):
    """Returns all the possible decodings, not just the number."""
    num = str(num)
    splits = []

    if not num:
        return []

    head = LETTERS[num[0]]
    if len(num) == 1:
        return [head]

    for item in decoding(num[1:]):
        splits.append(head + item)

    if len(num) >= 2 and (10 <= int(num[:2]) <= 26):
        head, rest = LETTERS[num[:2]], num[2:]
        for item in decoding(num[2:]):
            splits.append(head + item)
        if not rest:
            splits.append(head)

    return splits


class DecodingTest(unittest.TestCase):
    def test_decoding(self):
        self.assertEqual({"ABCD", "AWD", "LCD"}, set(decoding(1234)))
        self.assertEqual({"ABCDE", "AWDE", "LCDE"}, set(decoding(12345)))
        self.assertEqual({"AAAAA", "AAAK", "AKAA", "AAKA", "KAAA", "AKK", "KAK", "KKA"},
                         set(decoding(11111)))
        self.assertEqual({"AB", "L"}, set(decoding(12)))
