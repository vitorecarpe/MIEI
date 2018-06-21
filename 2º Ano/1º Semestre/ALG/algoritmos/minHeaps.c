#include "buffer.h"
#include "stdlib.h"

#define parent(i) (i-1)/2
#define left(i) i*2+1
#define right(i) i*2+2

struct buffer {
  int size;
  int used;
  int *values;
}

Buffer init(int size) {
  Buffer b = (Buffer) malloc(sizeof(struct buffer));
  if(b == NULL) return NULL;
  b->size = size;
  b->used = 0;
  b->values = (int *) malloc(sizeof(int) * size);
  if( b->values == NULL) {
    free(b);
    return NULL;
  }
  return b;
}

int valid(Buffer b){
  int i;
  for( i=b->used; i>0; i--){
      if(b->values[i] < b->values[parent(i)]) return 0;
  }
  return 1;
}

void swap(int v[],int i,int j){
  a = v[i];
  v[i] = v[j];
  v[j] = a;
}

void bubbleUP(int v[], int i){
  while(i>0 && v[i]<v[parent(i)]){
      swap(v,i,parent(i));
      i = parent(i);
    }
  }

int add(Buffer b, int v){
  if(b->used == b->size) return 1;
  b->values[b->used] = v;
  bubbleUP(b->values,b->used);
  b->used++;
  return 0;
}

//recursivo
void bubbleDown(int v[],int i,int used){
  while(left(i) < used && v[i] > v[left(i)] || right(i) < used && v[i] > v[right(i)] ){
    if(right(i) < used && v[right(i)] < v[left(i)]){
      swap(v,i,right(i));
      i = right(i);
    } else {
      swap(v,i,left(i));
      i = left(i);
    }
  }

}

//não recursivo
void bubbleDown(int v[],int N){

}

int rem(Buffer b, int *v){
  if(b->used == 0) return 1;
  *v = b->values[0];
  b->used--;
  b->values[0] = b->values[b->used];
  bubbleDown(b->values,0,b->used);
  return 0;
}
