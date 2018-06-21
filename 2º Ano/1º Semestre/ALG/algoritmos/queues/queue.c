#include "queue.h"
#include "stdlib.h"


/*
  Implementar queue simples sem rotatividade
*/

Queue init(int size){
  Queue a = (Queue) malloc(sizeof(struct queue));
  if(a == NULL) return NULL;
  a->values = (int*) malloc(sizeof(int) * size);
  if(a->values == NULL){
    free(a);
    return NULL;
  }
  a->front = a->rear = 0;
  a->size = size;
  return a;
}

int isEmpty(Queue q){
  if(q->front > 0 || q->rear > q->front) return 0;
  return 1;
}

int isFull(Queue q){
  if(q->rear == q->size - 1) return 0;
  return 1;
}

int enqueue(Queue q, int value){
  if(q->rear == q->size) return -1;
  else{
    q->values[q->rear] = value;
    q->rear++;
    return 0;
  }
}

int dequeue(Queue q){
  if(q->front == q->rear) return -1;
  else {
    int aux = q->values[q->front];
    q->front++;
    return aux;
  }
}

int inFront(Queue q){
  if(q->front == q->rear) return -1;
  else return q->values[q->front];
}

