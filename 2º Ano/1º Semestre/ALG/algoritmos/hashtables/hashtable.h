#define OCUPADO 2
#define LIVRE 0
#define APAGADO 1
#define CAP 7

// hash open adressing - array posicoes / ocupados
typedef struct hashOpen{
  int size;
  int *values;
  int *ocupados;
} *HashOpen;


//typedef close adressing array hash com lista ligada de

typedef struct node{
  //int chave; nao faz sentido existe uma chave para
  int valor;
  struct node *next;
} *Node;

typedef struct node *HashClosed[CAP];


/*
  FUNCOES hashOpen
*/
HashOpen initHOpen(int);
int add(HashOpen,int);
int rmv(HashOpen,int);
int search(HashOpen,int);

/*
  FUNCOES hashClosed
*/

void initHClosed(HashClosed*);
int addClosed(HashClosed,int);
int rmvClosed(HashClosed,int);
int searchClosed(HashClosed,int);
