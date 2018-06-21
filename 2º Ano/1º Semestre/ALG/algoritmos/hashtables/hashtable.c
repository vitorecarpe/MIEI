#include "stdio.h"
#include "stdlib.h"
#include "hashtable.h"


#define OCUPADO 2
#define LIVRE 0
#define APAGADO 1
#define CAP 7

/*
  OPEN ADRESSING
*/

int hashO(int value,int size){
  return (value % size);
}

HashOpen initHOpen(int size){
  HashOpen res = (HashOpen) malloc(sizeof(struct hashOpen));
  if(!res) return NULL;
  res->values = (int*) malloc(sizeof(int) * size);
  if(!res->values){
    free(res);
    return NULL;
  }
  res->ocupados = (int*) malloc(sizeof(int) * size);
  if(!res->ocupados){
    free(res->values);
    free(res);
    return NULL;
  }else {
    int i;
    for(i=0;i<size;i++)
      res->ocupados[i] = LIVRE;
    res->size = size;
  }
  return res;
}

int add(HashOpen h,int i){
  int l, j = 0;
  l = hashO(i,h->size);
  while(j < h->size){
    if(h->ocupados[l] == OCUPADO) {
      l = hashO(l+1,h->size);
      j++;
    }else {
      h->values[l] = i;
      h->ocupados[l] = OCUPADO;
      return 1;
    }
  }
  return 0;
}

int rmv(HashOpen h,int i){
  int l, j = 0;
  l = hashO(i,h->size);
  while(j < h->size){
    if(h->ocupados[l] == LIVRE)
      return 0;
    else if (h->ocupados[l] == OCUPADO && h->values[l] == i){
      h->ocupados[l] = APAGADO;
      return 1;
    }else {
      l = l+1 % h->size;
      j++;
    }
  }
  return 0;
}

int search(HashOpen h ,int i){
  int l, j = 0;
  l = hashO(i,h->size);
  while(j < h->size){
    if(h->ocupados[l] == LIVRE)
      return 0;
    else if (h->ocupados[l] == OCUPADO && h->values[l] == i){
      return 1;
    }else {
      l = l+1 % h->size;
      j++;
    }
  }
  return 0;
}

void printHash(HashOpen h){
  int i;
  for(i = 0; i < h->size; i++){
    printf(" | %d - %d |\n",h->values[i], h->ocupados[i]);
  }

}


/*
 CLOSED ADDRESING
*/

int hash(int value){
  return (value % CAP);
}

void initHClosed(HashClosed *h){
  int i;
  for(i = 0; i < CAP; i++)
    h[i] = NULL;
}

int addClosed(HashClosed h,int value){
  int l;
  struct node *aux, *p;
  l = hash(value);
  for(aux = h[l]; aux; aux = aux->next){
    p = aux;
    if(aux->valor == value) return 0;
  }
  aux = (Node) malloc(sizeof(struct node));
  if(!aux) return 0;
  aux->valor = value;
  aux->next = NULL;
  p->next = aux;
  return 1;
}

int rmvClosed(HashClosed h,int value){
  int l;
  struct node *p,*aux;
  l = hash(value);
  p = h[l];
  for(aux = h[l]; aux; aux = aux->next){
    if(aux->valor == value){
      p->next = aux->next;
      free(aux);
      return 1;
    }
    p = aux;
  }
  return 0;
}

int searchClosed(HashClosed h,int value){
  int l;
  struct node *aux;
  l = hash(value);
  for(aux = h[l]; aux ; aux = aux->next){
    if(aux->valor == value) return 1;
  }
  return 0;
}

/*
  MAIN
*/

int main(){
  /*
  HashOpen h = initHOpen(7);

  add(h,3);
  add(h,10);
  add(h,1);
  add(h,15);
  add(h,16);
  printf("Search 3: %d \n", search(h,3));
  printf("Search 16: %d \n", search(h,16));
  rmv(h,3);
  printf("Search 3: %d \n", search(h,3));
  printHash(h);
  */

  HashClosed *h;
  initHClosed(h);

}
