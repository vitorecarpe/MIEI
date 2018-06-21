#include "heaps.h"
#include "stdio.h"
#include "stdlib.h"

Heap init(int size){
  Heap h = (Heap) malloc(sizeof(struct heap));
  h->size = size;
  h->used = 0;
  h->values = (int*) malloc(sizeof(int)* size);
  return h;
}


void swap(int h[],int a,int b){
  int aux = h[a];
  h[a] = h[b];
  h[b] = aux;
}

void BubbleUP(int h[],int i){
  while(i > 0){
    if(h[i] < h[PARENT(i)]){
      swap(h,i,PARENT(i));
      i = PARENT(i);
    }else break;
  }
}

int insertHeap(Heap h,int value){
  if(h->size == h->used) return 0;
  h->values[h->used] = value;
  BubbleUP(h->values,h->used);
  h->used++;
  return 1;
}

void BubbleDown(int h[], int n){
  int i = 0;
  while(LEFT(i) < n){
    if(LEFT(i) < RIGHT(i)){
      if(h[i] > LEFT(i)){
        swap(h,i,LEFT(i));
        i = LEFT(i);
      }
    }else if(h[i] > RIGHT(i)){
        swap(h,i,RIGHT(i));
        i = RIGHT(i);
    }else break;
  }
}

int extractMin(Heap h, int *value){
  if(h->used == 0) return 0;
  *value = h->values[0];
  h->used--;
  h->values[0] = h->values[h->used];
  BubbleDown(h->values, h->used);
  return 1;
}

int minHeapOK(Heap h){
  int i;
  for(i = h->used; i > 0; i--)
    if(h[PAI[i]] > h[i]) return 0;
  return 1;
}

void printHeap(Heap h){
  int i;
  for(i = 0; i < h->used; i++)
    printf("%d ",h->values[i]);
  printf("\n");
}

// MAIN

int main(){
  Heap h = init(10);
  int *valor;
  insertHeap(h,10);
  insertHeap(h,20);
  insertHeap(h,30);
  insertHeap(h,5);
  printf("Heap OK: %d\n",minHeapOK(h));
  printHeap(h);
  extractMin(h,valor);
  printHeap(h);

  extractMin(h,valor);
  printHeap(h);

  extractMin(h,valor);
  printf("valor: %d\n",*valor);

  extractMin(h,valor);
  printf("valor: %d\n",*valor);

}

