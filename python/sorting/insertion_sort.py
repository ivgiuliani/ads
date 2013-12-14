import unittest


def insertion_sort(array):
    sorted_array, length = array[::], len(array)

    for i in range(length):
        j = i

        while j > 0 and sorted_array[j] < sorted_array[j - 1]:
            sorted_array[j], sorted_array[j - 1] = sorted_array[j - 1], sorted_array[j]
            j -= 1

    return sorted_array


class TestInsertionSort(unittest.TestCase):
    def test_insertion_sort(self):
        self.assertEqual(range(1, 8), insertion_sort([1, 2, 3, 4, 5, 6, 7]))
        self.assertEqual(range(1, 8), insertion_sort([5, 1, 6, 3, 4, 2, 7]))
        self.assertEqual(range(1, 8), insertion_sort([7, 6, 5, 4, 3, 2, 1]))
        self.assertEqual([], insertion_sort([]))
        self.assertEqual([10], insertion_sort([10]))

if __name__ == "__main__":
    unittest.main()