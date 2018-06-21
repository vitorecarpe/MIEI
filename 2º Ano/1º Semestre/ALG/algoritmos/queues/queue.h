typedef struct queue{
  int front;
  int rear;
  int size;
  int *values;
} *Queue;


Queue init(int);
int isEmpty(Queue);
int isFull(Queue);
int enqueue(Queue,int);
int dequeue(Queue);
int inFront(Queue);

/*
  Como seria implementado uma queue rotativa
*/
