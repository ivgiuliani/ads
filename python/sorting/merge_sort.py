import unittest


def merge_sort(array):
    if len(array) <= 1:
        return array

    mid = len(array) / 2
    merge1, merge2 = merge_sort(array[:mid]), merge_sort(array[mid:])

    def merge(left, right):
        l, r = 0, 0
        while l < len(left) and r < len(right):
            if left[l] < right[r]:
                yield left[l]
                l += 1
            else:
                yield right[r]
                r += 1
        while l != len(left):
            yield left[l]
            l += 1
        while r != len(right):
            yield right[r]
            r += 1

    return list(merge(merge1, merge2))


class TestMergeSort(unittest.TestCase):
    def test_merge_sort(self):
        self.assertEqual(range(1, 8), merge_sort([1, 2, 3, 4, 5, 6, 7]))
        self.assertEqual(range(1, 8), merge_sort([5, 1, 6, 3, 4, 2, 7]))
        self.assertEqual(range(1, 8), merge_sort([7, 6, 5, 4, 3, 2, 1]))
        self.assertEqual([], merge_sort([]))
        self.assertEqual([10], merge_sort([10]))

if __name__ == "__main__":
    unittest.main()