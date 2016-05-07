"""
Given a singly linked list, reverse it in place and return the head
of the new list.
"""

import unittest

class Node(object):
    def __init__(self, value):
        self.value = value
        self.next  = None

    def __repr__(self):
        return "<Node %d>" % self.value


def rev(curr):
    last = None
    while curr is not None:
        next = curr.next
        curr.next = last
        last = curr
        curr = next
    return last


class TestReverse(unittest.TestCase):
    def test_reverse(self):
        l1, l2, l3, l4, l5 = Node(1), Node(2), Node(3), Node(4), Node(5)
        l1.next = l2
        l2.next = l3
        l3.next = l4
        l4.next = l5

        head = rev(l1)
        self.assertEqual(head, l5)
        
        self.assertEqual(head.next, l4)
        self.assertEqual(l4.next, l3)
        self.assertEqual(l3.next, l2)
        self.assertEqual(l2.next, l1)
        self.assertEqual(l1.next, None)

    def test_reverse_onenode(self):
        n = Node(1)
        self.assertEqual(n, rev(n))

    def test_reverse_empty(self):
        self.assertEqual(None, rev(None))


if __name__ == "__main__":
    unittest.main()

