#include "buffer.h"
#include "stdlib.h"

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


int add(Buffer b, int v){
  if(b->used == b->size) return 1;
  b->values[b->used] = v;
  b->used++;
  return 0;
}

int rem(Buffer b, int *v){
  if(b->used == 0) return 1;
  int m=0, i;
  for(i=1;i<b->used;i++){
    if(b->values[i]< b->values[m]){
      m = i;
    }
  }
  *v = b->values[m];
  b->used--;
  b->values[m] = b->values[b->used];
  return 0;
}
