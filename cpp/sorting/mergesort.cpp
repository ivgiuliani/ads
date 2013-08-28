#include <iostream>
#include <algorithm>
#include <cstdlib>
#include <vector>
#include <queue>

using namespace std;

template <class T>
void print(const T &data) {
  cout << " " << data;
}

/* Sort a vector of type T
 *
 * Copying a vector around like this (i.e. v = mergesort(...)) is
 * highly inefficient, the right way would be to use pointers or
 * iterators but still this way is more readable and easier to
 * understand.
 *
 * I would never write a thing like this in Real Code.
 */
template <class T>
vector<T> mergesort(vector<T> v, int from_idx, int to_idx) {
  int i;

  if (from_idx < to_idx) {
    int middle = (from_idx + to_idx) / 2;
    v = mergesort(v, from_idx, middle);
    v = mergesort(v, middle + 1, to_idx);
    
    /* merge the two lists, the former from from_idx to middle
     * and the latter from middle + 1 to to_idx
     */
    queue<T> q1, q2;
    for (i = from_idx; i <= middle; i++) q1.push(v[i]);
    for (i = middle + 1; i <= to_idx; i++) q2.push(v[i]);

    i = from_idx;
    while (!(q1.empty() || q2.empty())) {
      if (q1.front() <= q2.front()) {
        v[i++] = q1.front();
        q1.pop();
      } else {
        v[i++] = q2.front();
        q2.pop();
      }
    }

    while (!q1.empty()) { v[i++] = q1.front(); q1.pop(); };
    while (!q2.empty()) { v[i++] = q2.front(); q2.pop(); };
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

  v = mergesort(v, 0, v.size() - 1);

  cout << " After: ";
  for_each(v.begin(), v.end(), print<int>);
  cout << endl;

  return EXIT_SUCCESS;
}
