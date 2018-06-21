#include "stack.h"
#include "stdio.h"

int main(){
  Stack q = init(10);
  printf("%d \n",peek(q));
  push(q,5);
  push(q,3);
  printf("%d \n",peek(q));
  push(q,10);
  push(q,7);
  printf("%d - ",pop(q));
  printf("%d - ",pop(q));
  printf("%d - ",pop(q));
  printf("%d - ",pop(q));
  printf("%d - ",pop(q));
}
