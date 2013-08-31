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

func NewLinkedList() *LinkedList {
	return &LinkedList{head: nil}
}

func (e *Element) Value() interface{} {
	return e.value
}

func (e *Element) Next() *Element {
	return e.next
}

func (lst *LinkedList) lastNode() *Element {
	var head *Element
	for head = lst.head; head != nil && head.next != nil; head = head.next {
	}
	return head
}

func (lst *LinkedList) Size() (i int) {
	for head := lst.head; head != nil; head = head.next {
		i++
	}
	return
}

func (lst *LinkedList) IsEmpty() bool {
	return lst.head == nil
}

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

func (lst *LinkedList) Front() *Element {
	return lst.head
}

func (lst *LinkedList) Clear() {
	lst.head = nil
}

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

func (lst *LinkedList) InsertAfter(value interface{}, node *Element) *Element {
	newNode := &Element{value, node.next}
	node.next = newNode
	return newNode
}

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
