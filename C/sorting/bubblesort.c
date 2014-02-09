#include <stdio.h>
#include <stdlib.h>

/*
 * Implementation of bubble sort
 */

void swap(int *, int *);
void bubblesort(int [], int);
void print_array(int [], int);

int
main(int argc, char **argv) {
	int array[20] = {
		 9, 71,  2, 84, 12, 23, 99, 26,  1, 76,
		67,  4, 53, 42, 10, 38, 66,  7, 80, 2
	};

	print_array(array, 20);
	bubblesort(array, 20);
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
 * Sort the (integer) array with bubblesort sort
 */
void
bubblesort(int array[], int length) {
	int i;
  int sorted = 0;

  while (!sorted) {
    sorted = 1;
    for (i = 1; i < length; i++) {
      if (array[i] < array[i - 1]) {
        swap(&array[i], &array[i - 1]);
        sorted = 0;
      }
    }
  }
}

void
swap(int *item1, int *item2) {
	int tmp = *item1;
	*item1 = *item2;
	*item2 = tmp;
}
