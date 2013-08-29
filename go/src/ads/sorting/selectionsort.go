package sorting

func selectionsort(array []int) {
	for i := range array {
		min := i
		for j := range array[i:] {
			if array[i+j] < array[min] {
				min = i + j
			}
		}

		array[i], array[min] = array[min], array[i]
	}
}
