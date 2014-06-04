import unittest

# Greatest common divisor


def gcd_euclid(a, b):
    assert a >= 0
    assert b >= 0
    while b != 0:
        a, b = b, a % b
    return a


def gcd_euclid_recursive(a, b):
    return a if b == 0 else gcd_euclid_recursive(b, a % b)


def gcd_knuth(a, b):
    assert a >= 0
    assert b >= 0

    if a == b:
        return a
    if a == 0:
        return b
    if b == 0:
        return a

    a_is_even = bool(~a & 1)
    b_is_even = bool(~b & 1)

    if a_is_even and b_is_even:
        return gcd_knuth(a >> 1, b >> 1) << 1
    elif a_is_even:
        return gcd_knuth(a >> 1, b)
    elif b_is_even:
        return gcd_knuth(a, b >> 1)
    elif a >= b:
        return gcd_knuth((a - b) >> 1, b)

    return gcd_knuth((b - a) >> 1, a)


class GCDTest(unittest.TestCase):
    VALUES = (
        # a, b, expected
        (8, 12, 4),
        (2, 4, 2),
        (2, 3, 1),
        (9, 28, 1),
        (54, 24, 6),
        (48, 18, 6),
        (100, 200, 100),
        (100, 0, 100),
        (0, 200, 200),
        (0, 0, 0),
        (40902, 24140, 34),
    )

    def test_gcd_euclid(self):
        for a, b, expected in self.VALUES:
            self.assertEqual(expected, gcd_euclid(a, b))

    def test_gcd_euclid_recursive(self):
        for a, b, expected in self.VALUES:
            self.assertEqual(expected, gcd_euclid_recursive(a, b))

    def test_gcd_knuth(self):
        for a, b, expected in self.VALUES:
            self.assertEqual(expected, gcd_knuth(a, b))
