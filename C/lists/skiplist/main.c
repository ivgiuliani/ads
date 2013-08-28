#include <assert.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "skip.h"

void
test() {
  int i;
  skiplist *map = skip_new();
  clock_t start, stop;
  double time = 0.0;

  printf("Check inline value get/set\n");
  for (i = 0; i < 1000; i++) {
    skip_set(map, i, i*10);
    assert(skip_get(map, i) == (i * 10));
  }

  printf("Check offline value get\n");
  for (i = 0; i < 1000; i++) {
    assert(skip_get(map, i) == (i * 10));
  }

  printf("Check list value override\n");
  for (i = 0; i < 1000; i++) {
    skip_set(map, i, i);
    assert(skip_get(map, i) == i);
  }

  printf("Check item deletion\n");
  for (i = 0; i < 1000; i += 2) {
    skip_del(map, i);
    assert(skip_get(map, i) == -1);
  }

  printf("Check that only items explicitely deleted have been deleted\n");
  for (i = 1; i < 1000; i += 2) {
    assert(skip_get(map, i) == i);
  }

  skip_free(map);
  map = skip_new();

  printf("Check reverse insertion order\n");
  for (i = 999; i >= 0; i--) {
    skip_set(map, i, i);
    assert(skip_get(map, i) == i);
  }

  printf("Check correct order on reverse insertion\n");
  assert(skip_get(map, 0) == 0);
  for (i = 0; i < 1000; i++) {
    assert(skip_get(map, i) == i);
  }

  skip_free(map);
  map = skip_new();

  #define LARGE_LIST_SIZE 40000000
  printf("Check timing on insertion of %d items: ", LARGE_LIST_SIZE);
  fflush(stdout);
  start = clock();
  for (i = 0; i < LARGE_LIST_SIZE; i++) {
    skip_set(map, i, i);
  }
  stop = clock();
  time = (double)(stop-start)/CLOCKS_PER_SEC;
  printf("%f secs (%d levels)\n", time, skip_get_level(map));

  printf("Lookup time of the middle item in %d items: ", LARGE_LIST_SIZE);
  fflush(stdout);
  start = clock();
  assert(skip_get(map, LARGE_LIST_SIZE/2) == LARGE_LIST_SIZE/2);
  stop = clock();
  time = (double)(stop-start)/CLOCKS_PER_SEC;
  printf("%f secs (%d levels)\n", time, skip_get_level(map));

  printf("Lookup time of the last item in %d items: ", LARGE_LIST_SIZE);
  fflush(stdout);
  start = clock();
  assert(skip_get(map, LARGE_LIST_SIZE-1) == (LARGE_LIST_SIZE-1));
  stop = clock();
  time = (double)(stop-start)/CLOCKS_PER_SEC;
  printf("%f secs (%d levels)\n", time, skip_get_level(map));

  skip_free(map);
}

int
main(int argc, char **argv) {
  test();
  return EXIT_SUCCESS;
}
