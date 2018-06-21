#define B 0
#define L -1
#define R 1

typedef struct treenode {
  int bf;
  int value;
  struct treenode *left;
  struct treenode *right;
} TreeNode;

typedef TreeNode *Tree;

int altura(Tree);
Tree RotateLeft(Tree);
Tree RotateRight(Tree);
Tree insertTree(Tree,int,int*);
Tree correctLeft(Tree);
Tree correctRight(Tree);
void printTree(Tree);
