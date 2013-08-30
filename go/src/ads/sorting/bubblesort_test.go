package sorting

import "testing"

func Test_BubbleSort_AlreadySorted(t *testing.T) {
	p := []int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
	bubblesort(p)

	for i := 0; i < 10; i++ {
		if p[i] != i {
			t.Fatalf("array is not sorted (expected: %d found: %d)", i, p[i])
		}
	}
}

func Test_BubbleSort_ReverseOrder(t *testing.T) {
	p := []int{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
	bubblesort(p)

	for i := 0; i < 10; i++ {
		if p[i] != i {
			t.Fatalf("array is not sorted (expected: %d found: %d)", i, p[i])
		}
	}
}

func Test_BubbleSort_RandomOrder(t *testing.T) {
	p := []int{5, 9, 7, 1, 2, 0, 8, 4, 6, 3}
	bubblesort(p)

	for i := 0; i < 10; i++ {
		if p[i] != i {
			t.Fatalf("array is not sorted (expected: %d found: %d)", i, p[i])
		}
	}
}

func Test_BubbleSort_AllEquals(t *testing.T) {
	p := []int{5, 5, 5, 5, 5, 5, 5, 5, 5, 5}
	bubblesort(p)

	for i := 0; i < 10; i++ {
		if p[i] != 5 {
			t.Fatalf("array is not sorted (expected: 5 found: %d)", p[i])
		}
	}
}
