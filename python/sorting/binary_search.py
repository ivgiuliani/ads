import unittest


def binary_search_rec(lst, val, start=None, end=None):
    start = start if start is not None else 0
    end = end if end is not None else len(lst) - 1

    if not lst or start > end:
        return None

    pivot = (start + end) / 2
    if val == lst[pivot]:
        return pivot
    elif val > lst[pivot]:
        return binary_search_rec(lst, val, start=pivot + 1, end=end)
    else:
        return binary_search_rec(lst, val, start=start, end=pivot - 1)


def binary_search_iter(lst, val):
    start, end = 0, len(lst) - 1

    while start <= end:
        pivot = (start + end) / 2
        if val == lst[pivot]:
            return pivot
        elif val > lst[pivot]:
            start, end = pivot + 1, end
        else:
            start, end = start, pivot - 1

    return None


class TestBinarySearch(unittest.TestCase):
    def test_find_value_recursive(self):
        vals = [1, 3, 5, 7, 9, 11, 15, 19]
        self.assertIsNone(binary_search_rec(vals, 21))
        self.assertIsNone(binary_search_rec(vals, 0))
        for idx, item in enumerate(vals):
            self.assertEquals(idx, binary_search_rec(vals, item))

    def test_find_value_iterative(self):
        vals = [1, 3, 5, 7, 9, 11, 15, 19]
        self.assertIsNone(binary_search_iter(vals, 21))
        self.assertIsNone(binary_search_iter(vals, 0))
        for idx, item in enumerate(vals):
            self.assertEquals(idx, binary_search_iter(vals, item))


if __name__ == "__main__":
    unittest.main()
