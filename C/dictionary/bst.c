#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct bst {
  char *key;
  int value;
  struct bst *left;
  struct bst *right;
} BST;

#define BST_MIN(bst) do {     \
  while (bst->left != NULL) { \
    bst = bst->left;          \
  }                           \
} while(0);

#define BST_MAX(bst) do {      \
  while (bst->right != NULL) { \
    bst = bst->right;          \
  }                            \
} while(0);


BST *
bst_make(char *key, int value) {
  BST *bst = malloc(sizeof(BST));
  bst->key = key;
  bst->value = value;
  return bst;
}


void
bst_put(BST *bst, char *key, int value) {
  assert(bst != NULL);
  int cmp = strcmp(bst->key, key);
  
  if (cmp == 0) {
    bst->value = value;
  } else if (cmp < 0) {
    if (bst->left == NULL) {
      bst->left = bst_make(key, value);
    } else {
      bst_put(bst->left, key, value);
    }
  } else { 
    if (bst->right == NULL) {
      bst->right = bst_make(key, value);
    } else {
      bst_put(bst->right, key, value);
    }
  }
}


bool
bst_contains(BST *bst, char *key) {
  if (bst == NULL) {
    return false;
  }

  int cmp = strcmp(bst->key, key);
  if (cmp == 0) {
    return true;
  } else if (cmp < 0) {
    return bst_contains(bst->left, key);
  } else {
    return bst_contains(bst->right, key);
  }
}


int
bst_get(BST *bst, char *key) {
  if (bst == NULL) {
    // Poor return value.
    return 0;
  }

  int cmp = strcmp(bst->key, key);
  if (cmp == 0) {
    return bst->value;
  } else if (cmp < 0) {
    return bst_get(bst->left, key);
  } else {
    return bst_get(bst->right, key);
  }

  return 0;
}


int
main(int argc, char **argv) {
  BST *root = bst_make("root", 10);
  assert(bst_contains(root, "root"));
  assert(!bst_contains(root, "root1"));
  assert(!bst_contains(root, "hello"));
  assert(bst_get(root, "root") == 10);

  bst_put(root, "abcdefghijklmno", 123);
  bst_put(root, "hello", 123);
  bst_put(root, "world", 234);
  bst_put(root, "bst", 345);
  bst_put(root, "lorem", 456);
  bst_put(root, "ipsum", 567);
  bst_put(root, "dolor", 66);
  bst_put(root, "sit", 11);
  bst_put(root, "amet", 22);

  assert(bst_contains(root, "hello"));
  assert(bst_contains(root, "world"));
  assert(bst_contains(root, "bst"));
  assert(!bst_contains(root, "asd"));
  assert(!bst_contains(root, "adsh"));
  assert(!bst_contains(root, "hell"));

  assert(bst_get(root, "hello") == 123);
  assert(bst_get(root, "lorem") == 456);
  assert(bst_get(root, "ipsum") == 567);

  return 0;
}

