package sorting;

import java.util.Arrays;

/**
 * Implementation of a min-heap
 */
public class Heap {
  private int SIZE = 1024;
  private int[] items = new int[SIZE];
  private int count = 0;

  public void add(int val) {
    items[count] = val;
    bubbleUp(count);
    count++;

    if (count >= SIZE) {
      dynamicExpand();
    }
  }

  private void dynamicExpand() {
    System.out.println(String.format("expansion from %d to %d [#items=%d]", SIZE, SIZE * 2, count));
    items = Arrays.copyOf(items, SIZE * 2);
    SIZE *= 2;
  }

  public int min() {
    return items[0];
  }

  public void pop() {
    if (count == 0) return;

    count--;
    items[0] = items[count];
    bubbleDown(0);
  }

  private int parent(int index) {
    if (index == 0) return -1;
    return (index - 1) / 2;
  }

  private int childLeft(int index) {
    return (2 * index) + 1;
  }

  private int childRight(int index) {
    return (2 * index) + 2;
  }

  private static void swap(int[] items, int x, int y) {
    int tmp;
    tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }

  private void bubbleUp(int index) {
    if (parent(index) == -1) return; // root of the heap
    if (items[parent(index)] > items[index]) {
      swap(items, parent(index), index);
      bubbleUp(parent(index));
    }
  }

  private void bubbleDown(int index) {
    int left = childLeft(index);
    int right = childRight(index);
    int minIndex = index;

    // find out who is the smaller item, if the root, the left or the right child
    if (left < count && items[minIndex] > items[left]) minIndex = left;
    if (right < count && items[minIndex] > items[right]) minIndex = right;

    // stop bubbling down if the smallest item is the root
    if (minIndex != index) {
      swap(items, index, minIndex);
      bubbleDown(minIndex);
    }
  }

  public static int[] heapsort(int[] items) {
    int[] sorted = new int[items.length];
    Heap heap = new Heap();
    for (int item : items) {
      heap.add(item);
    }
    for (int i = 0; i < items.length; i++) {
      sorted[i] = heap.min();
      heap.pop();
    }
    return sorted;
  }

  public static void main(String[] args) {
    Heap heap;

    heap = new Heap();
    heap.add(1); heap.add(2); heap.add(3); heap.add(4); heap.add(5);
    test(heap.min() == 1); heap.pop();
    test(heap.min() == 2); heap.pop();
    test(heap.min() == 3); heap.pop();
    test(heap.min() == 4); heap.pop();
    test(heap.min() == 5); heap.pop();

    heap = new Heap();
    heap.add(5); heap.add(4); heap.add(3); heap.add(2); heap.add(1);
    test(heap.min() == 1); heap.pop();
    test(heap.min() == 2); heap.pop();
    test(heap.min() == 3); heap.pop();
    test(heap.min() == 4); heap.pop();
    test(heap.min() == 5); heap.pop();

    int i;
    heap = new Heap();
    for (i = 10240; i >= 0; i--) {
      heap.add(i);
    }
    for (i = 0; i < 10240; i++) {
      test(heap.min() == i);
      heap.pop();
    }

    System.out.println(Arrays.toString(heapsort(new int[] { 1, 39, 43, 33, 12, 2, 45, 99, 33, 22, 11 })));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
