package lists

import "errors"

/*
 * Implementation of a singly linked list
 */

type Element struct {
	value interface{}
	next  *Element
}

type LinkedList struct {
	head *Element
}

// NewLinkedList instantiates a new empty linked list.
func NewLinkedList() *LinkedList {
	return &LinkedList{head: nil}
}

// Value returns the value held in the current linked list item.
func (e *Element) Value() interface{} {
	return e.value
}

// Next returns the pointer to the next item in the linked list.
// If the current item is the last item, a pointer to nil is returned.
func (e *Element) Next() *Element {
	return e.next
}

func (lst *LinkedList) lastNode() *Element {
	var head *Element
	for head = lst.head; head != nil && head.next != nil; head = head.next {
	}
	return head
}

// Size returns the number of items currently in the linked list.
func (lst *LinkedList) Size() (i int) {
	for head := lst.head; head != nil; head = head.next {
		i++
	}
	return
}

// IsEmpty returns true if the list doesn't contain any element,
// false otherwise.
func (lst *LinkedList) IsEmpty() bool {
	return lst.head == nil
}

// Append appends the given value to the end of the list.
func (lst *LinkedList) Append(value interface{}) *Element {
	var node *Element = lst.lastNode()
	var newNode *Element = &Element{value: value, next: nil}

	if node == nil {
		// list is empty
		lst.head = newNode
	} else {
		node.next = newNode
	}

	return newNode
}

// Get returns the value in the `index` position of the list.
// If the list holds less than `index` items, an error is returned.
// Index in list are zero-based.
func (lst *LinkedList) Get(index int) (interface{}, error) {
	var node *Element = lst.head
	for i := 0; i < index && node != nil; i++ {
		node = node.next
	}

	if node == nil {
		return nil, errors.New("index out of bounds")
	}

	return node.value, nil
}

// Front returns a pointer to the first item in the list or
// nil if the list is empty.
func (lst *LinkedList) Front() *Element {
	return lst.head
}

// Clear deletes every item contained into the linked list.
func (lst *LinkedList) Clear() {
	lst.head = nil
}

// InsertBefore inserts the given value right before the given item pointer.
// If the item in input is not held inside the linked list, this operation
// does nothing and thus the linked list is returned unchanged.
func (lst *LinkedList) InsertBefore(value interface{}, node *Element) *Element {
	newNode := &Element{value, node}
	head := lst.Front()
	for ; head != nil && head.Next() != node; head = head.Next() {
	}
	if head == nil {
		lst.head = newNode
	} else {
		head.next = newNode
	}
	return newNode
}

// InsertAfter inserts the given value right after the given item pointer.
// If the item in input is not held inside the linked list, this operation
// does nothing and thus the linked list is returned unchanged.
func (lst *LinkedList) InsertAfter(value interface{}, node *Element) *Element {
	newNode := &Element{value, node.next}
	node.next = newNode
	return newNode
}

// Remove removes the given item in the linked list.
// If the item is not held inside the linked list, this operation
// does nothing and thus the linked list is returned unchanged.
func (lst *LinkedList) Remove(node *Element) {
	if lst.Front() == node {
		lst.head = node.next
		return
	}

	head := lst.Front()
	for ; head != nil && head.Next() != node; head = head.Next() {
	}
	head.next = node.next
}
