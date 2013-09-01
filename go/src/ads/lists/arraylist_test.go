package lists

import (
	"strconv"
	"testing"
)

func TestArrayListEmpty(t *testing.T) {
	lst := NewArrayList()

	if !lst.IsEmpty() {
		t.Error("list is not empty")
	}

	if lst.Size() != 0 {
		t.Errorf("list size is supposed to be 0 but is %d instead", lst.Size())
	}
}

func TestArrayListNewList(t *testing.T) {
	lst := NewArrayList()

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
		t.Errorf("list[0] != 1 (found: %d)", val)
	}

	if val, err := lst.Get(1); err != nil || val != 2 {
		t.Errorf("list[0] != 2 (found: %d)", val)
	}

	if _, err := lst.Get(2); err == nil {
		t.Errorf("accessing list[2] didn't raise any error")
	}
}

func TestArrayListClearList(t *testing.T) {
	lst := NewArrayList()

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

func TestArrayListIteration(t *testing.T) {
	lst := NewArrayList()

	for i := 1; i <= 10; i++ {
		lst.Append(i)
	}
	for i := 1; i <= 10; i++ {
		if val, _ := lst.Get(i - 1); val != i {
			t.Errorf("expected value '%d', found: '%d'", i, val)
		}
	}
}

func TestArrayListRemove(t *testing.T) {
	lst := NewArrayList()

	lst.Append(1)
	lst.Append(2)
	lst.Append(3)
	lst.Append(4)
	lst.Append(5)

	// bear in mind that Remove() removes the item at the given *index*,
	// not the item with that value

	if lst.Size() != 5 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	lst.Remove(3)
	if lst.Size() != 4 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	lst.Remove(1)
	if lst.Size() != 3 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	lst.Remove(2)
	if lst.Size() != 2 {
		t.Fatalf("list contains unexpected number of items: %d", lst.Size())
	}

	if val, _ := lst.Get(0); val != 1 {
		t.Errorf("list[0] != 1")
	}

	if val, _ := lst.Get(1); val != 3 {
		t.Errorf("list[1] != 3")
	}
}

func TestArrayListStrings(t *testing.T) {
	lst := NewArrayList()

	for i := 1; i <= 100; i++ {
		lst.Append("item " + strconv.Itoa(i))
	}

	if lst.Size() != 100 {
		t.Fatalf("lists contains %d items (expected: 100)", lst.Size())
	}

	for i := 1; i <= 100; i++ {
		expected := "item " + strconv.Itoa(i)
		if val, _ := lst.Get(i - 1); val != expected {
			t.Fatalf("unexpected item in list: %s (expected: %s)", val, expected)
		}
	}
}

func TestArrayListInsertAt(t *testing.T) {
	lst := NewArrayList()

	for i := 0; i < 10; i++ {
		lst.Append(i)
	}

	if lst.Size() != 10 {
		t.Fatalf("lists contains %d items (expected: 100)", lst.Size())
	}

	// insert in middle
	lst.InsertAt(5, 99999)
	if lst.Size() != 11 {
		t.Fatalf("lists contains %d items (expected: 101)", lst.Size())
	}
	if val, _ := lst.Get(5); val != 99999 {
		t.Fatalf("expected value (99999) didn't found, found %d instead", val)
	}
	if val, _ := lst.Get(6); val != 5 {
		t.Fatalf("expected value (5) didn't found, found %d instead", val)
	}

	// insert at end
	lst.InsertAt(11, 99999)
	if lst.Size() != 12 {
		t.Fatalf("lists contains %d items (expected: 12)", lst.Size())
	}
	if val, _ := lst.Get(11); val != 99999 {
		t.Fatalf("expected value (99999) didn't found, found %d instead", val)
	}

	// insert at begin
	lst.InsertAt(0, 99999)
	if lst.Size() != 13 {
		t.Fatalf("lists contains %d items (expected: 13)", lst.Size())
	}
	if val, _ := lst.Get(0); val != 99999 {
		t.Fatalf("expected value (99999) didn't found, found %d instead", val)
	}
	if val, _ := lst.Get(1); val != 0 {
		t.Fatalf("expected value (%d) didn't found, found %d instead", 0, val)
	}
}

func TestArrayListGeneric(t *testing.T) {
	lst := NewArrayList()

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

	for i := 0; i < lst.Size(); i++ {
		if val, _ := lst.Get(i); val != values[i] {
			t.Fatalf("expected %s but found %s", values[i], val)
		}
	}
}

func BenchmarkArrayListAppend(b *testing.B) {
	lst := NewArrayList()

	for i := 0; i < b.N; i++ {
		lst.Append(i)
	}
}

func BenchmarkArrayListInsertAtBeginning(b *testing.B) {
	lst := NewArrayList()

	for i := 0; i < b.N; i++ {
		lst.InsertAt(0, i)
	}
}

func BenchmarkArrayListInsertAtAndRemove(b *testing.B) {
	lst := NewArrayList()

	for i := 0; i < b.N; i++ {
		lst.InsertAt(i, i)
	}
	for i := 0; i < b.N; i++ {
		lst.Remove(0)
	}
}

func BenchmarkArrayListAppendAndRemove(b *testing.B) {
	lst := NewArrayList()

	for i := 0; i < b.N; i++ {
		lst.Append(i)
	}
	for i := 0; i < b.N; i++ {
		lst.Remove(0)
	}
}
