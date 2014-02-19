import unittest
import random


class Heap(object):
    def __init__(self, heapify=None):
        """
        Instantiates a new heap. If `heapify` is passed as input, it
        must be an array of values that will be "heapified" (a valid
        heap structure will be create out of it and used to initialize
        this whole instance).
        """
        self.heap = []
        self.count = 0
        if heapify:
            self.heap = heapify
            self.count = len(heapify)

            for i in range(len(heapify) / 2, -1, -1):
                self.__bubble_down(i)

    def min(self):
        return self.heap[0] if self.count > 0 else None

    def add(self, val):
        self.heap.append(val)
        self.__bubble_up(self.count)
        self.count += 1

    def pop(self):
        if self.count == 0:
            return None

        self.count -= 1
        val = self.heap[0]
        self.heap[0], self.heap[self.count] = self.heap[self.count], None
        self.__bubble_down(0)
    
        return val

    @staticmethod
    def __parent(index):
        return (index - 1) / 2 if index > 0 else None

    @staticmethod
    def __child_left(index):
        return (2 * index) + 1

    @staticmethod
    def __child_right(index):
        return (2 * index) + 2

    def __bubble_up(self, index):
        par_idx = self.__parent(index)
        if par_idx is None:
            return

        if self.heap[par_idx] > self.heap[index]:
            self.heap[par_idx], self.heap[index] = self.heap[index], self.heap[par_idx]
            self.__bubble_up(par_idx)

    def __bubble_down(self, index):
        left, right = self.__child_left(index), self.__child_right(index)
        min_idx = index

        # find out who is the smaller item, if the root, the left or the right child
        if left < self.count and self.heap[min_idx] > self.heap[left]:
            min_idx = left
        if right < self.count and self.heap[min_idx] > self.heap[right]:
            min_idx = right

        # stop bubbling down if the smallest item is the root
        if min_idx != index:
            self.heap[min_idx], self.heap[index] = self.heap[index], self.heap[min_idx]
            self.__bubble_down(min_idx)


class HeapTest(unittest.TestCase):
    def setUp(self):
        self.heap = Heap()

    def test_empty(self):
        self.assertIsNone(self.heap.min())
        self.heap.pop()
        self.assertIsNone(self.heap.min())

    def test_one_item(self):
        self.assertIsNone(self.heap.min())
        self.heap.add(1)
        self.assertEqual(1, self.heap.min())
        self.assertEqual(1, self.heap.pop())
        self.assertIsNone(self.heap.min())
        self.assertIsNone(self.heap.pop())

    def test_heapsort(self):
        vals = range(1, 1000)
        random.shuffle(vals)

        for v in vals:
            self.heap.add(v)

        for v in range(1, 1000):
            self.assertEqual(v, self.heap.pop())

    def test_duplicates(self):
        for i in range(1000):
            self.heap.add(1816)

        for i in range(1000):
            self.assertEqual(1816, self.heap.pop())

        self.assertIsNone(self.heap.pop())

    def test_reverse(self):
        vals = range(1, 1000)

        for i in reversed(vals):
            self.heap.add(i)
        for i in vals:
            self.assertEqual(i, self.heap.pop())

    def test_heapify(self):
        vals = range(99, -1, -1)
        heap = Heap(vals)
        self.assertEqual(range(0, 100), [heap.pop() for i in range(len(vals))])

        vals = range(1, 1000)
        random.shuffle(vals)
        heap = Heap(vals)
        self.assertEqual(range(1, 1000), [heap.pop() for i in range(len(vals))])

if __name__ == "__main__":
    random.seed()
    unittest.main()