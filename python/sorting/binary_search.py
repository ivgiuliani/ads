import unittest


def binary_search(lst, val):
    start, end = 0, len(lst) - 1

    while start <= end:
        pivot = (start + end) / 2

        if lst[pivot] == val:
            return pivot
        elif lst[pivot] < val:
            # keep goin in the right branch
            start, end = pivot + 1, end
        else:  # keep going in the left branch
            start, end = start, pivot - 1

    return -1


class TestBinarySearch(unittest.TestCase):
    def test_find_value(self):
        vals = [1, 3, 5, 7, 9, 11, 15, 19]
        self.assertEquals(-1, binary_search(vals, 21))
        self.assertEquals(-1, binary_search(vals, 0))
        for idx, item in enumerate(vals):
            self.assertEquals(idx, binary_search(vals, item))


if __name__ == "__main__":
    unittest.main()
