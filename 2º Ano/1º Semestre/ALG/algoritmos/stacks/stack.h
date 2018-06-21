typedef struct stack{
  int point;
  int size;
  int *values;
} *Stack;


Stack init(int);
int peek(Stack);
int push(Stack,int);
int pop(Stack);
