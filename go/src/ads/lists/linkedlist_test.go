package lists

import (
	"strconv"
	"testing"
)

func Test_LinkedList_Empty(t *testing.T) {
	var lst LinkedList

	if !lst.IsEmpty() {
		t.Error("list is not empty")
	}

	if lst.Size() != 0 {
		t.Errorf("list size is supposed to be 0 but is %d instead", lst.Size())
	}

	if lst.Front() != nil {
		t.Error("list.Front() != nil")
	}
}

func Test_LinkedList_NewList(t *testing.T) {
	var lst LinkedList

	if lst.Size() != 0 {
		t.Errorf("list size is supposed to be 0 but is %d instead", lst.Size())
	}

	lst.Append(1)
	if lst.Size() != 1 {
		t.Errorf("list size is supposed to be 1 but is %d instead", lst.Size())
	}

	lst.Append(2)
	if lst.Size() != 2 {
		t.Errorf("list size is supposed to be 2 but is %d instead", lst.Size())
	}

	if val, err := lst.Get(0); err != nil || val != 1 {
		t.Error("list[0] != 1")
	}

	if val, err := lst.Get(1); err != nil || val != 2 {
		t.Error("list[1] != 2")
	}

	if _, err := lst.Get(2); err == nil {
		t.Errorf("accessing list[2] didn't raise any error")
	}
}

func Test_LinkedList_ClearList(t *testing.T) {
	var lst LinkedList

	lst.Append(1)
	lst.Append(2)
	lst.Append(3)
	lst.Append(4)
	lst.Append(5)
	if lst.Size() != 5 {
		t.Fatalf("list has unexpected size %d", lst.Size())
	}

	lst.Clear()
	if lst.Size() != 0 {
		t.Fatal("list is not empty after Clear()")
	}
}

func Test_LinkedList_Iteration(t *testing.T) {
	var lst LinkedList
	var i int = 1

	for i <= 10 {
		lst.Append(i)
		i++
	}

	i = 1
	for e := lst.Front(); e != nil; e = e.Next() {
		if e.Value() != i {
			t.Errorf("expected value '%d', found: '%d'", i, e.Value())
		}
		i++
	}
}

func Test_LinkedList_InsertBefore(t *testing.T) {
	var lst LinkedList

	node := lst.Append(1)
	lst.InsertBefore(3, lst.InsertBefore(4, node))

	if lst.Size() != 3 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	if val, _ := lst.Get(0); val != 3 {
		t.Errorf("list[0] != 3")
	}

	if val, _ := lst.Get(1); val != 4 {
		t.Errorf("list[1] != 4")
	}

	if val, _ := lst.Get(2); val != 1 {
		t.Errorf("list[2] != 1")
	}
}

func Test_LinkedList_InsertAfter(t *testing.T) {
	var lst LinkedList

	node := lst.Append(1)
	lst.InsertAfter(3, lst.InsertAfter(4, node))

	if lst.Size() != 3 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	if val, _ := lst.Get(0); val != 1 {
		t.Errorf("list[0] != 1")
	}

	if val, _ := lst.Get(1); val != 4 {
		t.Errorf("list[1] != 4")
	}
	if val, _ := lst.Get(2); val != 3 {
		t.Errorf("list[2] != 3")
	}
}

func Test_LinkedList_Remove(t *testing.T) {
	var lst LinkedList

	node1 := lst.Append(1)
	lst.Append(2)
	node3 := lst.Append(3)
	lst.Append(4)
	node5 := lst.Append(5)

	if lst.Size() != 5 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	lst.Remove(node3)
	if lst.Size() != 4 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	lst.Remove(node1)
	lst.Remove(node5)
	if lst.Size() != 2 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	if val, _ := lst.Get(0); val != 2 {
		t.Errorf("list[0] != 2")
	}

	if val, _ := lst.Get(1); val != 4 {
		t.Errorf("list[1] != 4")
	}
}

func Test_LinkedList_Strings(t *testing.T) {
	lst := NewLinkedList()

	for i := 1; i <= 100; i++ {
		lst.Append("item " + strconv.Itoa(i))
	}

	if lst.Size() != 100 {
		t.Fatalf("lists contains %d items (expected: 100)", lst.Size())
	}

	i := 1
	for head := lst.Front(); head != nil; head = head.Next() {
		expected := "item " + strconv.Itoa(i)
		if head.Value() != expected {
			t.Fatalf("unexpected item in list: %s (expected: %s)", head.Value(), expected)
		}
		i++
	}
}

func Test_LinkedList_Generic(t *testing.T) {
	lst := NewLinkedList()

	values := []interface{}{
		uint8(1),
		uint16(2),
		int64(30000000),
		"hello world",
		complex(17, 4),
		float64(12.2),
		lst,
	}

	for v := range values {
		lst.Append(values[v])
	}

	if lst.Size() != len(values) {
		t.Fatal("expected %d items, found: %d", len(values), lst.Size())
	}

	i := 0
	for head := lst.Front(); head != nil; head = head.Next() {
		if head.Value() != values[i] {
			t.Fatalf("expected %s but found %s", values[i], head.Value())
		}
		i++
	}
	for v := range values {
		lst.Append(values[v])
	}
}
