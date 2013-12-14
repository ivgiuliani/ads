import unittest


def quick_sort(array):
    # This is the shortest quicksort implementation I could come up with
    # in Python. It works because of the order things are evaluated in a
    # logic statement and is equivalent to the following:
    # if array: return <...> else return []
    # I would not ever write code like this in a real project.
    return array and (
        quick_sort([x for x in array[:-1] if x < array[-1]]) +
        [array[-1]] +
        quick_sort([x for x in array[:-1] if x >= array[-1]])
    ) or []


class TestQuickSort(unittest.TestCase):
    def test_quick_sort(self):
        self.assertEqual(range(1, 8), quick_sort([1, 2, 3, 4, 5, 6, 7]))
        self.assertEqual(range(1, 8), quick_sort([5, 1, 6, 3, 4, 2, 7]))
        self.assertEqual(range(1, 8), quick_sort([7, 6, 5, 4, 3, 2, 1]))
        self.assertEqual([], quick_sort([]))
        self.assertEqual([10], quick_sort([10]))

if __name__ == "__main__":
    unittest.main()