package lists

/*
 * Implementation of a list based on a dynamically expanded array
 */

import (
	"errors"
)

const (
	START_SIZE = 8
)

type ArrayList struct {
	size   int
	values []interface{}
}

func NewArrayList() *ArrayList {
	arr := ArrayList{}
	arr.Clear()
	return &arr
}

func (lst *ArrayList) expand() {
	// create a new slice twice the size of the original slice
	// and copy all the items over
	newValues := make([]interface{}, len(lst.values)*2)
	copy(newValues, lst.values)
	lst.values = newValues
}

func (lst *ArrayList) shrink() {
	// create a new slice half the size of the original slice
	// and copy all the items over
	newValues := make([]interface{}, len(lst.values)/2)
	copy(newValues, lst.values)
	lst.values = newValues
}

func (lst *ArrayList) Size() int {
	return lst.size
}

func (lst *ArrayList) IsEmpty() bool {
	return lst.Size() == 0
}

func (lst *ArrayList) Clear() {
	lst.size = 0
	lst.values = make([]interface{}, START_SIZE)
}

func (lst *ArrayList) Get(index int) (interface{}, error) {
	if index >= lst.Size() {
		return nil, errors.New("index out of bounds")
	}

	return lst.values[index], nil
}

func (lst *ArrayList) Append(value interface{}) {
	index := lst.Size()
	if index >= len(lst.values) {
		lst.expand()
	}
	lst.values[index] = value
	lst.size++
}

func (lst *ArrayList) Remove(index int) {
	/*
		Interestingly enough, copy() is much slower than the for loop below
		(at least on Go 1.1), benchmarking indeed shows that each Remove() call
		with copy() takes about 260000 ns/ops on my machine, while with the for
		loop we get as low as to 150000 ns/ops.
		I'm not sure why, but I suppose it's because copy() uses a for loop
		internally to create the temporary buffer and another for loop to copy
		it over, which would explain the large increase in timing.

		copy(lst.values[index:], lst.values[index+1:])
	*/
	for i := index; i < lst.Size(); i++ {
		lst.values[i] = lst.values[i+1]
	}
	if index < lst.Size() {
		lst.size--
	}
	if lst.Size() < len(lst.values)/2 {
		lst.shrink()
	}
}

func (lst *ArrayList) InsertAt(index int, value interface{}) {
	if lst.Size() >= len(lst.values) {
		lst.expand()
	}

	/*
		For the same reason as Remove(), copy() is not actually used
		for InsertAt().

		copy(lst.values[index+1:], lst.values[index:])
	*/
	for i := index; i < lst.Size(); i++ {
		lst.values[i+1] = lst.values[i]
	}

	lst.values[index] = value
	lst.size++
}
