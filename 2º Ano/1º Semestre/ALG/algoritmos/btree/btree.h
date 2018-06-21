typedef struct btree {
  int value;
  struct btree *left, *right;
} *Btree;


Btree add(Btree,int);
int remv(Btree,int);
int altura(Btree);
void printTree(Btree);
int max(int,int);
int size(Btree);
int balanceada(Btree);
