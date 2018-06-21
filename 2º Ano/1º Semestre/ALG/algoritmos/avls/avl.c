#include "stdio.h"
#include "stdlib.h"
#include "avl.h"



Tree RotateLeft(Tree t){
  Tree aux = t->right;
  t->right = aux->left;
  aux->left = t;
  return aux;
}

Tree RotateRight(Tree t){
  Tree aux = t->left;
  t->left = aux->right;
  aux->right = t;
  return aux;
}

int altura(Tree t){
  int a;
  if(t == NULL)
    return 0;
  if(t->bf == R)
    return 1 + altura(t->right);
  return 1 + altura(t->left);
}

Tree insertTree(Tree t, int value, int *cresceu){
  if(t == NULL){
    Tree aux = (Tree) malloc(sizeof(struct treenode));
    aux->value = value;
    aux->bf = B;
    aux->left = NULL;
    aux->right = NULL;
    *cresceu = 1;
    return aux;
  } else if(t->value < value){
    t->right = insertTree(t->right,value,cresceu);
    if(*cresceu){
      if(t->bf == B) t->bf = R;
      else if(t->bf == L){
        t->bf = B;
        cresceu = 0;
      } else{
        t = correctRight(t);
        cresceu = 0;
      }
     }
  } else{
      t->left = insertTree(t->left,value,cresceu);
      if(*cresceu){
        if(t->bf == B) t->bf = L;
        else if(t->bf == R){
          t->bf = B;
          cresceu = 0;
        }
        else{
          t = correctLeft(t);
          cresceu = 0;
        }
      }
    }
  return t;
}

Tree correctLeft(Tree t){
  Tree aux = t->left;
  if(aux->bf == L){
    t->bf = aux->bf = B;
    t = RotateRight(t);
  } else {
    t->left = RotateLeft(t->left);
    t = RotateRight(t);
    t->bf = aux->bf = B;
  }
  return t;
}

Tree correctRight(Tree t){
  Tree aux = t->right;
  if(aux->bf == R){
    t->bf = aux->bf = B;
    t = RotateLeft(t);
  } else {
    t->right = RotateRight(t->right);
    t = RotateLeft(t);
    t->bf = aux->bf = B;
  }
  return t;
}


void printTree(Tree b){

  /*
  if(b != NULL) {
    printTree(b->left);
    printf(" ( %d - bal: %d ) ", b->value,b->bf);
    printTree(b->right);
  }
  */
  if(b == NULL)
      return;
    printTree(b -> left);
    printf("%d | bal: %d\n",b -> value,b->bf);
    printTree(b ->right);

}


/*
  MAIN
*/

int main(){
  Tree t = NULL;
  int *c;


  printf("\ninteracao 1\n\n");
  t = insertTree(t,4,c);
  printTree(t);

  printf("\ninteracao 2\n\n");
  t = insertTree(t,3,c);
  printTree(t);

  printf("\ninteracao 3\n\n");
  t = insertTree(t,2,c);
  printTree(t);

  printf("\ninteracao 4\n\n");
  t = insertTree(t,1,c);
  printTree(t);

  printf("\ninteracao 5\n\n");
  t = insertTree(t,19,c);
  printTree(t);

  /*
  printf("\ninteracao 5\n\n");
  t = insertTree(t,1,c);
  printTree(t);

  printf("\ninteracao 6\n\n");
  t = insertTree(t,4,c);
  printTree(t);

  printf("\ninteracao 7\n\n");
  t = insertTree(t,5,c);
  printTree(t);

  printf("\ninteracao 8\n\n");
  t = insertTree(t,18,c);
  printTree(t);

  printf("\ninteracao 9\n\n");
  t = insertTree(t,15,c);
  printTree(t);
  */
}
