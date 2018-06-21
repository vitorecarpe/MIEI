#include "btree.h"
#include "stdlib.h"
#include "stdio.h"

int main(){
  Btree b = (Btree) malloc(sizeof(struct btree));
  b = add(b,7);
  b = add(b,10);
  b = add(b,5);
  b = add(b,6);
  b = add(b,1);
  b = add(b,3);
  b = add(b,12);
  printf("Altura: %d \n",altura(b));
  printTree(b);
  printf("Tamanho: %d \n", size(b));
  printf("Procura nº12: %d\n",search(b,12));
  printf("Procura nº2: %d\n",search(b,2));
  remv(b,7);
  printTree(b);
  b = add(b,7);
  printTree(b);
  printf("Balanceada: %d",balanceada(b));
}
