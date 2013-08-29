#ifndef HEAP_H
#define HEAP_H

#include <cmath>

using namespace std;

/*
 * Heap implementation.
 * There are no guarantees about the memory content unless explicitely
 * initialized. (we don't initialize the memory in the heap, do it
 * yourself if you need)
 */

namespace ds {
	template <class T>
	class Heap {
		private:
			T *array;
			unsigned int length;
			unsigned int n_ariety;

		public:
			/* Create a 'n_ariety' tree  which will hold at most 'size' nodes */
			Heap(unsigned int size, unsigned int n_ariety) {
				this->array = new T[size];
				this->n_ariety = n_ariety;
			};

			~Heap() {
				delete[] this->array;
			};

			T* at(unsigned int position) {
				return &this->array[position];
			};

			T &operator[] (unsigned int p) { return this->array[p]; };

			/*******************
			 * support methods *
			 *******************/

			/* Obtains the parent of the specified item */
			unsigned int parent(unsigned int pos) {
				return floor((1/(float)this->n_ariety) * (pos - 1));
			};

			/* Obtains the i-th child of the item at position 'pos' */
			unsigned int ith_child(unsigned int pos, unsigned int i) {
				return (this->n_ariety * pos) + i;
			};
	};

} /* namespace:ds */

#endif /* HEAP_H */
