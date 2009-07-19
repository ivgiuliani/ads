#include <stdio.h>
#include <stdlib.h>

/*
 * Implementation of the Insertion Sort
 */

void insertion_sort(int [], int);
void print_array(int [], int);

int
main(int argc, char **argv) {
	int array[20] = {
		 9, 71,  2, 84, 12, 23, 99, 26,  1, 76,
		67,  4, 53, 42, 10, 38, 66,  7, 80, 2
	};

	print_array(array, 20);
	insertion_sort(array, 20);
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
 * Sort the (integer) array with insertion sort
 */
void
insertion_sort(int array[], int length) {
	int i, j, val;

	for (i = 1; i < length; i++) {
		val = array[i];

		j = i - 1;
		/* take the next item into the unsorted part (val) and move it
		 * to the sorted one in the right position by shifting sorted
		 * elements
		 */
		while (j >= 0 && array[j] > val) {
			array[j + 1] = array[j];
			j--;
		}

		array[j + 1] = val;
	}
}
