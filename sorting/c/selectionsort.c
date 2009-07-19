#include <stdio.h>
#include <stdlib.h>

/*
 * Implementation of the Selection Sort
 */

void selection_sort(int [], int);
void swap(int *, int *);
int find_minimum(int [], int, int);
void print_array(int [], int);

int
main(int argc, char **argv) {
	int array[20] = {
		 9, 71,  2, 84, 12, 23, 99, 26,  1, 76,
		67,  4, 53, 42, 10, 38, 66,  7, 80, 2
	};

	print_array(array, 20);
	selection_sort(array, 20);
	print_array(array, 20);

	return EXIT_SUCCESS;
};

/*
 * Print the array
 */
void
print_array(int array[], int length) {
	int i;
	for (i = 0; i < length; i++)
		printf("%3d ", array[i]);
	printf("\n");
}

/*
 * Sort the (integer) array with selection sort
 */
void
selection_sort(int array[], int length) {
	int i, min;

	for (i = 0; i < length; i++) {
		min = find_minimum(array, i, length);
		swap(&array[min], &array[i]);
	}
}

/*
 * Returns the index of the mininum element of the array within
 * the specified positions
 */
int
find_minimum(int array[], int start_pos, int end_pos) {
	int min = start_pos, pos = start_pos;

	while (pos < end_pos) {
		if (array[pos] < array[min])
			min = pos;
		pos++;
	}

	return min;
}

/*
 * Swap two items
 */
void
swap(int *item1, int *item2) {
	int tmp = *item1;
	*item1 = *item2;
	*item2 = tmp;
}
