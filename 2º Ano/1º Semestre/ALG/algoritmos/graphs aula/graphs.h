#define V 5

#define WHITE 0
#define GRAY 1
#define BLACK -1


#define INTREE 0
#define FRINGE 1
#define UNSEEN 2

#define NIL -1

typedef int MatrizAdj[V][V];

typedef struct edge {
  int dest;
  int custo;
  struct edge *next;
} *Edge;

typedef Edge ListAdj[V];

MatrizAdj initMAdj(int);
ListAdj initLAdj(int);
void MAtoLA(MatrizAdj,ListAdj);
void LAtoMA(MatrizAdj,ListAdj);
