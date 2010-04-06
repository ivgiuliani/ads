#ifndef SKIP_H
#define SKIP_H

#define PROB 0.5
#define MAX_LEVELS 32

typedef struct _skipitem {
  int key;
  int value;
  struct _skipitem **next;
} skipitem;

typedef struct {
  unsigned int level;
  skipitem *header;
} skiplist;

skiplist *skip_new();
inline skipitem *skip_first(skiplist *);
inline int skip_is_empty(skiplist *);
inline int skip_get_level(skiplist *);
void skip_free(skiplist *);
void skip_set(skiplist *, int, int);
int skip_get(skiplist *, int);
void skip_del(skiplist *, int);
void skip_printall(skiplist *);

skipitem *skipitem_new(int, int, int);
void skipitem_free(skipitem *);

int random_level();

#endif /* SKIP_H */
