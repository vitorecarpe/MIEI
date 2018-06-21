#include "queue.h"
#include "stdio.h"

int main(){
  Queue q = init(3);
  enqueue(q,5);
  enqueue(q,3);
  enqueue(q,10);
  enqueue(q,7);
  printf("%d - ",dequeue(q));
  printf("%d - ",dequeue(q));
  printf("%d - ",dequeue(q));
  printf("%d - ",dequeue(q));
  printf("%d - ",dequeue(q));
  isEmpty(q);
  isFull(q);
  //printf("%d",inFront(q));
}
