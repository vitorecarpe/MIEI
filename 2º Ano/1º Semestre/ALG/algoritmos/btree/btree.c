#include "btree.h"
#include "stdlib.h"
#include "stdio.h"

Btree add(Btree b,int value){
  if(!b){ //nodo vazio
    b = (Btree) malloc(sizeof(struct btree));
    b->value = value;
    b->left = NULL;
    b->right = NULL;
  } else if(b->value == 0) {
    b->value = value;
    b->right = NULL;
    b->left = NULL;
  } else if(b->value > value) {
    b->left = add(b->left,value); //estava a colocar o add para sem o apontador para o lado esquerdo - ficava parado no ar.
  } else {
    b->right = add(b->right,value); //same
  }
  return b;
}

int remv(Btree b,int value){
  if(!b) return 0;
  struct btree *aux = b;
  while(b){
    if(b->value == value){
      if(!b->right && !b->left) free(b);
      else if(!b->right && b->left){
        b = b->left;
        aux->left = b->left;
        free(b);
        return 1;
      }else if(b->right && !b->left){
        b = b->right;
        aux->right = b->right;
        free(b);
        return 1;
      }else if(b->right && b->left){
        remvAux(b,b->left,value);
        return 1;
      }
    }
    else if(b->value > value){
      aux = b;
      b = b->left;
    } else {
      aux = b;
      b = b->right;
    }
  }
}

int remvAux(Btree pai, Btree b, int value){
  struct btree *aux = b;
  while(b->right != NULL){
    aux = b;
    b = b->right;
  }
  pai->value = b->value;
  aux->right = NULL;
  free(b);
  return 1;
}

int altura(Btree b){
  if(b == NULL) return 0;
  else return (1 + max(altura(b->left),altura(b->right)));
}

void printTree(Btree b){
  if(b != NULL) {
    printTree(b->left);
    printf(" %d ", b->value);
    printTree(b->right);
  }
}

int size(Btree b){
  if(b == NULL) return 0;
  else return (1 + size(b->left) + size(b->right));
}

int max(int a,int b){
  if(a > b) return a;
  else return b;
}

int search(Btree b, int value){
  if(b == NULL) return 0;
  else if(b->value == value) return 1;
  else if(b->value > value) return search(b->left,value);
  else return search(b->right,value);
}

int balanceada(Btree b){
  int left, right;
  if(b == NULL) return 1;
  left = altura(b->left);
  right = altura(b->right);
  return ((abs(left - right) <= 1)&& balanceada(b->left) && balanceada(b->right));
}
