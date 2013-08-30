package dictionary

/*
 * Implementation of a dictionary based on binary search trees.
 * Put(), Get() and Remove() are all O(logn) in time.
 *
 * This implementation is not generic, since it only offers a
 * string->string mapping.
 */

import (
	"errors"
	"log"
)

type bstNode struct {
	key    string
	value  string
	parent *bstNode
	left   *bstNode
	right  *bstNode
}

func newBSTNode(key string, value string, parent, left, right *bstNode) *bstNode {
	return &bstNode{
		key:    key,
		value:  value,
		parent: parent,
		left:   left,
		right:  right,
	}
}

// used to compare strings alphabetically rathern than lexicographically
func stringCompare(string1, string2 string) int {
	if len(string1) == len(string2) {
		switch {
		case string1 < string2:
			return -1
		case string1 == string2:
			return 0
		case string1 > string2:
			return 1
		}
	}
	return len(string1) - len(string2)
}

// adds a pair (key, value) to the binary search tree and
// returns the newly added node instance
func (root *bstNode) insert(key, value string) *bstNode {
	var parent *bstNode
	node := root
	parent = nil

	for node != nil {
		if key == node.key {
			node.value = value
			return node
		}

		parent = node
		if stringCompare(key, node.key) < 0 {
			node = node.left
		} else {
			node = node.right
		}
	}

	newNode := newBSTNode(key, value, parent, nil, nil)
	if stringCompare(key, parent.key) < 0 {
		parent.left = newNode
	} else {
		parent.right = newNode
	}
	return newNode
}

// Removes a node from the binary search tree.
// Returns the new root of the tree.
func (root *bstNode) remove(key string) (*bstNode, bool) {
	if root == nil {
		return nil, false
	}
	deleted := false

	cmp := stringCompare(key, root.key)
	switch {
	case cmp < 0:
		_, deleted = root.left.remove(key)
	case cmp > 0:
		_, deleted = root.right.remove(key)
	default: // cmp == 0
		deleted = true
		switch {
		case root.left != nil && root.right != nil:
			// delete a node with two children
			successor := root.right
			for successor.left != nil {
				successor = successor.left
			}

			root.key = successor.key
			root.value = successor.value
			//successor.remove(successor.key)
			root.right.remove(successor.key)
		case root.left != nil:
			// only left child
			root.replaceNode(root.left)
			return root.left, true
		case root.right != nil:
			// only right child
			root.replaceNode(root.right)
			return root.right, true
		default:
			// no children (node is a leaf)
			root.replaceNode(nil)
			return nil, true
		}
	}

	return root, deleted
}

// Replaces the node with the new one, updating the corresponding
// parent pointer
func (node *bstNode) replaceNode(newNode *bstNode) error {
	if node.parent != nil {
		switch {
		case node == node.parent.left:
			node.parent.left = newNode
		case node == node.parent.right:
			node.parent.right = newNode
		default:
			// can't get here
			log.Fatal("node's parent doesn't include node neither as left nor as right child")
		}
	}

	if newNode != nil {
		newNode.parent = node.parent
	}

	return nil
}

func (root *bstNode) find(key string) *bstNode {
	if root == nil {
		return nil
	}

	if stringCompare(key, root.key) == 0 {
		return root
	}

	if stringCompare(key, root.key) < 0 {
		return root.left.find(key)
	}
	return root.right.find(key)
}

type bstIterationFunc func(*bstNode)

func (root *bstNode) inOrder(fnc bstIterationFunc) {
	if root == nil {
		return
	}

	root.left.inOrder(fnc)
	fnc(root)
	root.right.inOrder(fnc)
}

type BSTDictionary struct {
	root *bstNode
	size int
}

// Initializes a new dictionary based on binary search trees.
func NewBSTDictionary() *BSTDictionary {
	return &BSTDictionary{
		root: nil,
		size: 0,
	}
}

// Returns the number of keys currently in the dictionary.
func (dict *BSTDictionary) Size() int {
	return dict.size
}

// Returns a slice of all the keys currently in the dictionary.
func (dict *BSTDictionary) Keys() []string {
	keys := make([]string, dict.Size())
	i := 0

	if dict.root != nil {
		dict.root.inOrder(func(node *bstNode) {
			keys[i] = node.key
			i++
		})
	}

	return keys
}

// Returns a slice of all the values currently in the dictionary.
func (dict *BSTDictionary) Values() []string {
	values := make([]string, dict.Size())
	i := 0

	if dict.root != nil {
		dict.root.inOrder(func(node *bstNode) {
			values[i] = node.value
			i++
		})
	}

	return values
}

// Adds the specified key/value pair into the dictionary.
// If the key already exists in the dictionary, the corresponding
// value will be overwritten.
func (dict *BSTDictionary) Put(key, value string) {
	if dict.root == nil {
		dict.root = newBSTNode(key, value, nil, nil, nil)
		dict.size++
		return
	}

	node := dict.root.find(key)
	if node != nil {
		// overwrite the previous value
		node.value = value
	} else {
		dict.root.insert(key, value)
		dict.size++
	}
}

// Retrieves the current value for the given key in the dictionary.
// If the key doesn't exist, an error is also returned
func (dict *BSTDictionary) Get(key string) (string, error) {
	if dict.root == nil {
		return "", errors.New("missing key")
	}

	node := dict.root.find(key)
	if node == nil {
		return "", errors.New("missing key")
	}

	return node.value, nil
}

// Remove the given key from the dictionary.
// If the key is not present in the dictionary, no actions will
// be performed.
func (dict *BSTDictionary) Remove(key string) {
	if dict.root == nil {
		return
	}

	newRoot, deleted := dict.root.remove(key)
	dict.root = newRoot

	if deleted {
		dict.size--
	}
}
