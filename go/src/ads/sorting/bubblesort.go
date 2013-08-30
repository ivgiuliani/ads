package sorting

func bubblesort(array []int) {
	sorted := false
	for !sorted {
		sorted = true
		for i := 1; i < len(array); i++ {
			if array[i] < array[i-1] {
				array[i], array[i-1] = array[i-1], array[i]
				sorted = false
			}
		}
	}
}
