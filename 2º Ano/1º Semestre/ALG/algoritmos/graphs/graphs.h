#define MAX 9

#define WHITE 0
#define GRAY  1
#define BLACK 2

#define INTREE 0
#define ORLA 1
#define UNSEEN 2

#define NIL -1
#define NE -1
#define INF 999


typedef int GraphM[MAX][MAX];

typedef struct edge {
  int dest;
  int cost;
  struct edge *next;
} *Edge;

typedef struct edge *GraphL[MAX];

typedef int COLOR;
typedef COLOR Colors[MAX];

void initGraphM(GraphM);
void initGraphL(GraphL);
void MtoL(GraphM,GraphL);
void LtoM(GraphL,GraphM);


int grauEntradaL(GraphL,int);
int grauSaidaL(GraphL,int);
int grauEntradaM(GraphM,int);
int grauSaidaM(GraphM,int);

void printGraphL(GraphL);
void printGraphM(GraphM);

//perguntas exame
int check_coloring(GraphL,Colors);
