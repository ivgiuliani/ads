package sorting

func insertionsort(array []int) {
	for i := 1; i < len(array); i++ {
		for j := i; j > 0 && array[j] < array[j-1]; j-- {
			array[j-1], array[j] = array[j], array[j-1]
		}
	}
}
