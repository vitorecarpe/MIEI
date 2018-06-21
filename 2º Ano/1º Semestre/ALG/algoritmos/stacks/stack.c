#include "stack.h"
#include "stdlib.h"


/*
  Implementar queue simples sem rotatividade
*/

Stack init(int size){
  Stack a = (Stack) malloc(sizeof(struct stack));
  if(a == NULL) return NULL;
  a->values = (int*) malloc(sizeof(int) * size);
  if(a->values == NULL){
    free(a);
    return NULL;
  }
  a->point = 0;
  a->size = size;
  return a;
}

int push(Stack s, int value){
  if(s->point == s->size -1) return -1;
  s->values[s->point] = value;
  s->point++;
  return 0;
}

int pop(Stack s){
  if(s->point == 0) return -1;
  int ret = s->values[--s->point];
  return ret;
}

int peek(Stack s){
  if(s->point == 0) return -1;
  return s->values[s->point-1];
}
