#include <glib.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include "skip.h"

/* Allocates a new skip list */
skiplist *
skip_new() {
  srand(time(NULL));
  skiplist *list = g_new(skiplist, 1);

  list->level = 1;

  /* We just need to allocate enough memory to hold pointers to real
   * items for the various levels so we can start our search from there.
   * There's no need to assign a key/value here since we'll never
   * consider those values
   */
  list->header = g_new(skipitem, 1);
  list->header->next = g_malloc0(MAX_LEVELS * sizeof(skipitem *));

  return list;
}

/* Deallocates a skiplist */
void
skip_free(skiplist *list) {
  skipitem *item = list->header->next[0];
  skipitem *tmp;

  while (item != NULL) {
    tmp = item;
    item = item->next[0];

    skipitem_free(tmp);
  }

  g_free(list->header->next);
  g_free(list->header);
  g_free(list);
}

/* Returns a pointer to the first item in the list */
inline skipitem *
skip_first(skiplist *list) {
  return list->header->next[0];
}

/* Checks if the list is empty or not */
inline int
skip_is_empty(skiplist *list) {
  return skip_first(list) == NULL;
}

/* Return the current skiplist level */
inline int
skip_get_level(skiplist *list) {
  return list->level;
}

/* Set a the key `key` to value `value` */
void
skip_set(skiplist *list, int key, int value) {
  skipitem *item = list->header;
  skipitem *update[MAX_LEVELS];
  int i, level = random_level();

  if (skip_is_empty(list)) {
    for (i = 0; i < list->level; i++) update[i] = list->header;
  } else {
    for (i = list->level - 1; i >= 0; i--) {
      while ((item->next[i] != NULL) && (item->next[i]->key < key)) {
        item = item->next[i];
      }
  
      /* Keep a list of the rightmost pointers to the various levels
       * before the key we're searching for (we need this list to
       * link the newly inserted item)
       */
      update[i] = item;
    }
  
    item = item->next[0];

    /* the key already exists, just update its value */
    if (item && (item->key == key)) {
      item->value = value;
      return;
    }
  }
  
  /* if the new item's level is higher than the current list level
   * just update the level by one
   */
  if (level > list->level) {
    update[list->level] = list->header;
    list->level++;
    level = list->level;
  }

  /* create the item and update the pointers */
  item = skipitem_new(key, value, level);
  for (i = 0; i < level; i++) {
    item->next[i] = update[i]->next[i];
    update[i]->next[i] = item;
  }
}

/* Returns the value of the key `key` or -1 if it doesn't exist */
int
skip_get(skiplist *list, int key) {
  skipitem *item = list->header;
  int i;

  if (skip_is_empty(list)) {
    return -1;
  }

  for (i = list->level - 1; i >= 0; i--) {
    while ((item->next[i] != NULL) && (item->next[i]->key < key)) {
      item = item->next[i];
    }
  }

  item = item->next[0];

  if (item && (item->key == key)) {
    return item->value;
  } else return -1;
}

/* Deletes the item with the key `key` from the skiplist */
void
skip_del(skiplist *list, int key) {
  skipitem *item = list->header;
  skipitem *update[MAX_LEVELS];
  int i;

  if (skip_is_empty(list)) return;

  for (i = list->level - 1; i >= 0; i--) {
    while ((item->next[i] != NULL) && (item->next[i]->key < key)) {
      item = item->next[i];
    }
    update[i] = item;
  }

  item = item->next[0];

  /* delete the item only if the key already exists, otherwise just ignore it */
  if (item && (item->key == key)) {
    for (i = 0; i < list->level; i++) {
      if (update[i]->next[i] != item) break;
      update[i]->next[i] = item->next[i];
    }

    skipitem_free(item);

    while (list->level > 1 && list->header->next[list->level - 1] == NULL) {
      list->level--;
    }
  }
}

/* Allocates a new item of level `level`, returns a pointer
 * to the newly allocated memory area
 */
skipitem *
skipitem_new(int key, int value, int level) {
  skipitem *item = g_new(skipitem, 1);

  item->key = key;
  item->value = value;

  item->next = g_malloc0((level + 1) * sizeof(skipitem *));

  return item;
}

/* Deallocates the item pointed by `item`. Note that items pointed
 * by `next` are not deallocated
 */
void
skipitem_free(skipitem *item) {
  g_free(item->next);
  g_free(item);
}

/* Generate a new random level */
int
random_level() {
  int level = 1;

  while (((double)rand() / (RAND_MAX + 1.0) < PROB) && (level < MAX_LEVELS))
    level++;

  return level;
}

/* Prints all the items in the list */
void
skip_printall(skiplist *list) {
  skipitem *item = list->header->next[0];
  while (item != NULL) {
    printf("key[%d] = %d\n", item->key, item->value);
    item = item->next[0];
  }
}
