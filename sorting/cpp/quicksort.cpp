#include <iostream>
#include <algorithm>
#include <cstdlib>
#include <vector>

using namespace std;

template <class T>
void print(const T &data) {
  cout << " " << data;
}

/* Sort a vector of type T
 *
 * Copying a vector around like this (i.e. v = quicksort(...)) is
 * highly inefficient, the right way would be to use pointers or
 * iterators but still this way is more readable and easier to
 * understand.
 *
 * I would never write a thing like this in Real Code.
 */
template <class T>
vector<T> quicksort(vector<T> v, int from_idx, int to_idx) {
  int pivot_idx, less_list_idx;
  int i;

  if ((to_idx - from_idx) > 0) {
    /* partition the array, i.e. move the items less than pivot
     * on its left and those greater or equals on its right
     */
  
    /* using the last item as pivot allow us to keep a O(nlogn)
     * avg time complexity but semplifies the partition process
     */
    pivot_idx = to_idx;
    less_list_idx = from_idx;

    for (i = from_idx; i < to_idx; i++) {
      if (v[i] < v[pivot_idx]) {
        swap(v[i], v[less_list_idx++]);
      }
    }
    swap(v[pivot_idx], v[less_list_idx]);

    v = quicksort(v, from_idx, less_list_idx - 1);
    v = quicksort(v, less_list_idx + 1, to_idx);
  }

  return v;
}

int
main(int argc, char **argv) {
  vector<int> v(8);

  v[0] = 1;
  v[1] = 14;
  v[2] = 82;
  v[3] = 62;
  v[4] = 5;
  v[5] = 75;
  v[6] = 24;
  v[7] = 55;
  
  cout << "Before: ";
  for_each(v.begin(), v.end(), print<int>);
  cout << endl;

  v = quicksort(v, 0, v.size() - 1);

  cout << " After: ";
  for_each(v.begin(), v.end(), print<int>);
  cout << endl;

  return EXIT_SUCCESS;
}
