"""
Implement an algorithm to print all valid (e.g., properly opened and
closed) combinations of n-pairs of parentheses.
EXAMPLE:
  input: 3 (e.g., 3 pairs of parentheses)
  output: ()()(), ()(()), (())(), ((()))
"""

import unittest


def gen_parenthesis(count):
    if count == 0:
        return set()
    elif count == 1:
        return {"()"}

    s = set()
    for item in gen_parenthesis(count - 1):
        s.add("(%s)" % item)
        s.add("%s()" % item)
        s.add("()%s" % item)

    return s


class TestGenParenthesis(unittest.TestCase):
    def test_gen_parenthesis_3(self):
        s3 = gen_parenthesis(3)
        self.assertEqual(5, len(s3))
        self.assertIn("()()()", s3)
        self.assertIn("()(())", s3)
        self.assertIn("(())()", s3)
        self.assertIn("(()())", s3)
        self.assertIn("((()))", s3)

    def test_gen_parenthesis_1(self):
        s1 = gen_parenthesis(1)
        self.assertEqual(1, len(s1))
        self.assertIn("()", s1)

    def test_gen_parenthesis_empty(self):
        self.assertEqual(len(gen_parenthesis(0)), 0)
        self.assertEqual(gen_parenthesis(0), set())


if __name__ == "__main__":
    unittest.main()
