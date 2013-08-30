package dictionary

import (
	"math/rand"
	"strconv"
	"testing"
	"time"
)

func Test_BSTDictionary_Empty(t *testing.T) {
	bst := NewBSTDictionary()

	if bst.Size() != 0 {
		t.Fatal("dictionary is not empty")
	}

	if len(bst.Keys()) != 0 {
		t.Fatal("dictionary is not empty")
	}

	if len(bst.Values()) != 0 {
		t.Fatal("dictionary is not empty")
	}
}

func Test_BSTDictionary_PutGet(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("key 1", "value 1")
	bst.Put("key 3", "value 3")
	bst.Put("key 2", "value 2")
	bst.Put("key 4", "value 4")
	bst.Put("key 10", "value 10")
	bst.Put("key 5", "value 5")

	for i := 1; i <= 5; i++ {
		key := "key " + strconv.Itoa(i)
		if val, err := bst.Get(key); val != "value "+strconv.Itoa(i) || err != nil {
			t.Errorf(
				"unexpected key or error condition on dict[key %d], value = '%s', error = '%s'",
				i, val, err)
		}
	}

	if val, err := bst.Get("key 10"); val != "value 10" || err != nil {
		t.Errorf(
			"unexpected key or error condition on dict[key 10], value = '%s', error = '%s'",
			val, err)
	}

	bst.Put("key 10", "value 100")
	if val, err := bst.Get("key 10"); val != "value 100" || err != nil {
		t.Errorf(
			"unexpected key or error condition on dict[key 10], value = '%s', error = '%s'",
			val, err)
	}
}

func Test_BSTDictionary_Size(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("key 1", "value 1")
	bst.Put("key 3", "value 3")
	bst.Put("key 2", "value 2")
	bst.Put("key 4", "value 4")
	bst.Put("key 10", "value 10")
	bst.Put("key 5", "value 5")

	if bst.Size() != 6 {
		t.Fatal("dictionary doesn't contain all the added elements")
	}

	// adding a key that exists already shouldn't change the dict's size
	bst.Put("key 10", "value 100")
	if bst.Size() != 6 {
		t.Fatal("dictionary doesn't contain all the added elements")
	}
}

func Test_BSTDictionary_Remove(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("2", "value 2")
	bst.Put("1", "value 1")
	bst.Put("7", "value 7")
	bst.Put("4", "value 4")
	bst.Put("8", "value 8")
	bst.Put("3", "value 3")
	bst.Put("6", "value 6")
	bst.Put("5", "value 5")

	// remove a leaf
	bst.Remove("5")
	if bst.Size() != 7 {
		t.Fatal("dictionary contains removed elements")
	}

	// another call to .Remove() should be ignored
	bst.Remove("5")
	if bst.Size() != 7 {
		t.Fatal("dictionary contains removed elements")
	}

	if val, err := bst.Get("does not exist"); val != "" || err == nil {
		t.Fatalf("Get() on a removed key didn't raise any error (returned: %s)", val)
	}

	// remove a node with two children
	bst.Remove("4")
	if bst.Size() != 6 {
		t.Fatal("dictionary contains removed elements")
	}
	if val, err := bst.Get("4"); val != "" || err == nil {
		t.Fatalf("Get() on a removed key didn't raise any error (returned: %s)", val)
	}

	// remove a node with one child
	bst.Remove("6")
	if bst.Size() != 5 {
		t.Fatal("dictionary contains removed elements")
	}
	if val, err := bst.Get("6"); val != "" || err == nil {
		t.Fatalf("Get() on a removed key didn't raise any error (returned: %s)", val)
	}

	// remove the root
	bst.Remove("2")
	if bst.Size() != 4 {
		t.Fatal("dictionary contains removed elements")
	}
	if val, err := bst.Get("2"); val != "" || err == nil {
		t.Fatalf("Get() on a removed key didn't raise any error (returned: %s)", val)
	}
}

func Test_BSTDictionary_Keys(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("key 1", "value 1")
	bst.Put("key 3", "value 3")
	bst.Put("key 2", "value 2")
	bst.Put("key 4", "value 4")
	bst.Put("key 10", "value 10")
	bst.Put("key 5", "value 5")

	if !stringSlicesEquals(bst.Keys(), []string{
		"key 1",
		"key 2",
		"key 3",
		"key 4",
		"key 5",
		"key 10"}) {
		t.Fatalf("Keys() returned missing keys or not in expected order (found: %s)",
			bst.Keys())
	}
}

func Test_BSTDictionary_Values(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("key 1", "value 1")
	bst.Put("key 3", "value 3")
	bst.Put("key 2", "value 2")
	bst.Put("key 4", "value 4")
	bst.Put("key 10", "value 10")
	bst.Put("key 5", "value 5")

	if !stringSlicesEquals(bst.Values(), []string{
		"value 1",
		"value 2",
		"value 3",
		"value 4",
		"value 5",
		"value 10"}) {
		t.Fatalf("Values() returned missing values or not in expected order (found: %s)",
			bst.Values())
	}
}

func Test_BSTDictionary_ManyItemsPutRemove_Sequential(t *testing.T) {
	bst := NewBSTDictionary()

	for i := 0; i < 1000; i++ {
		val := strconv.Itoa(i)
		bst.Put(val, val)
	}
	for i := 0; i < 1000; i++ {
		bst.Remove(strconv.Itoa(i))
	}
	if bst.Size() != 0 {
		t.Fatalf("dict contains items even after clearing it out (size: %d)", bst.Size())
	}
	for i := 0; i < 1000; i++ {
		if val, err := bst.Get(strconv.Itoa(i)); err == nil {
			t.Fatalf("removed item '%d' is still present in dictionary with value '%s'", i, val)
		}
	}
}

func Test_BSTDictionary_ManyItemsPutRemove_Sequential_ReverseRemoval(t *testing.T) {
	bst := NewBSTDictionary()

	for i := 0; i < 1000; i++ {
		val := strconv.Itoa(i)
		bst.Put(val, val)
	}
	for i := 999; i >= 0; i-- {
		bst.Remove(strconv.Itoa(i))
	}
	if bst.Size() != 0 {
		t.Fatal("dict contains items even after clearing it out")
	}
	for i := 0; i < 1000; i++ {
		if val, err := bst.Get(strconv.Itoa(i)); err == nil {
			t.Fatalf("removed item '%d' is still present in dictionary with value '%s'", i, val)
		}
	}
}

func Test_BSTDictionary_ManyItemsPutRemove_Random(t *testing.T) {
	bst := NewBSTDictionary()
	rand.Seed(time.Now().UTC().UnixNano())

	for i := 0; i < 10000; i++ {
		val := rand.Intn(5000)
		bst.Put(strconv.Itoa(val), strconv.Itoa(val))
	}
	for i := 0; i < 5000; i++ {
		bst.Remove(strconv.Itoa(i))
	}
	if bst.Size() != 0 {
		t.Fatal("dict contains items even after clearing it out")
	}
	for i := 0; i < 5000; i++ {
		if val, err := bst.Get(strconv.Itoa(i)); err == nil {
			t.Fatalf("removed item '%d' is still present in dictionary with value '%s'", i, val)
		}
	}
}

func Test_BSTDictionary_OneItem(t *testing.T) {
	bst := NewBSTDictionary()

	bst.Put("1", "100")
	if bst.Size() != 1 {
		t.Fatalf("dict contains %d items instead of 1", bst.Size())
	}

	if bst.Keys()[0] != "1" {
		t.Fatal("dict.keys != [1]")
	}

	if bst.Values()[0] != "100" {
		t.Fatal("dict.values != [100]")
	}

	if val, _ := bst.Get("1"); val != "100" {
		t.Fatal("dict[1].value != [100]")
	}

	bst.Remove("1")
	if bst.Size() != 0 {
		t.Fatalf("dict contains %d items instead of 0", bst.Size())
	}

	if val, err := bst.Get("1"); err == nil {
		t.Fatalf("error not raised on .Get() of invalid key (val: %s)", val)
	}
}

// helper function to compare two string slices
func stringSlicesEquals(a, b []string) bool {
	if len(a) != len(b) {
		return false
	}
	for i, v := range a {
		if v != b[i] {
			return false
		}
	}
	return true
}
