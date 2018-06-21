#include "graphs.c"


// MINHAS SOLUÇÕES
MatrizAdj initMAdj(){
  MatrizAdj aux;
  for(int i = 0; i < V; i++){
    for(int v = 0; v < V; j++){
      aux[i][j] = -1;
    }
  }
  return aux;
}

ListAdj initLAdj(){
  ListAdj aux = (ListAdj) malloc(sizeof(struct edge))
  for(int i = 0; i < V; i++)
    aux->next = NULL
  return aux
}

void MAtoLA(MatrizAdj ma, ListAdj la){
  struct edge aux;
  for(int i = 0; i < V; i++){
    la[i] = NULL;
    for(int j= V-1; j > 0; j++){
      if( ma[i][j] >= 0){
        aux = (Edge) malloc(sizeof(struct edge));
        aux->custo = ma[i][j];
        aux->dest = j;
        aux->next = la[i]; //esta a adicionar ao contrario
        la[i] = aux;
      }
    }
  }
}

void LAtoMA(MatrizAdj ma, ListAdj la){
  struct edge aux;
  for(int i = 0; i < V; i++){
    for(aux = la[i]; aux; aux = aux->next){
      ma[i][aux->dest] = aux->value;
    }
  }
}
// Funcoes de saber quantos vertices entram num nodo


//melhor caso -  N
//pior caso - N + A
int inLA(ListAdj l,int v){
  struct edge aux;
  int r = 0;
  int flag;
  for(int i = 0; i < V; i++){
    flag = 0
    for(aux = l[i];aux || flag != 0;aux=aux->next){
      if(aux->dest = v){
        r++;
        flag = 1;
      }
    }
  }
  return r;
}

// complexidade igual = N
int inMA(MatrizAdj m, int v){
  int r = 0;
  for(int i = 0; i < V; i++){
    if(m[i][v] >= 0)
      r++;
  }
  return r;
}

// complexidade igual = N

int outMA(MatrizAdj m, int v){
  int r = 0;
  for(int j = 0; j < V; j++){
    if(m[v][j] >= 0)
      r++;
  }
  return r;
}

//melhor caso = 1 (Não tem arestas associadas a esse nó)
//pior caso = min(N,A)
int outLA(ListAdj l,int v){
  int r = 0;
  struct edge aux;
  for(aux = l[v];aux;aux=aux->next)
    r++;
  return r;
}

//complexidade desta funcao é 2N = N
int capMA(MatrizAdj m, int v){}

//melhor caso = N
//pior caso = N + A
int capLA(ListAdj l, int v){}

//complexidade N^2
int maxCapLA(ListAdj l){}

//para melhorar este em vez de invocar a funcao capLA para cada nodo podemos percorrer tudo e ao mesmo tempo ir trabalhar os valores de capacidade num array auxiliar e apresenta-lo no fim - complexidade N + A
int maxCapMA(MatrizAdj m){}

//complexidade N2
int ColorOKMatriz(MatrizAdj m,int color[]){
  for(int i = 0; i < V; i++){
    for(int j = 0; j < V;j++){
      if(m[i][j] >= 0 && i!=j && color[i] == color[j])
        return 1; // falhou
    }
  }
  return 0;
}

int ColorOKList(ListAdj l,int color[]){
  struct edge aux;
  for(int i = 0; i < V; i++){
    for(aux = l[i];aux;aux = aux->next){
      if(color[aux->dest] == color[i])
        return 1; //falhou
    }
  }
  return 0;
}




//SOLUÇÕES DO PROF


// complexidade nesta funcao e n^2 por causa da inicializao do ciclo
void LA2MA(MatrizAdj ma, ListAdj la){
 int i,j;
 Edge aux;
 for(i=0; i < V; i++)
  for(j=0; j < V; j++)
    r[i][j] = -1;
  for(i = 0; i < N ; i++){
    aux = g[i];
    while aux {
      r[i][aux->dest] = aux->peso;
      aux = aux -> prox;
    }
  }
}

/*
  Travessias ESTUDAR
*/

// funcao que ve em largura os nodos a que chega a partir de um nodo inicial
int BSG(ListAdj l,int aux[],int nInicial){
  struct edge nodo;
  for(i = 0; i < V; i++ ) vis[i] == WHITE;

  for(nodo = l[nInicial];nodo;nodo = nodo->next){

  }

}



/*
  Topological Sort
*/

int color[V]; //matriz com as cores do grafo
int t; //variavel para colocar no array de Topological Sort na posicao certa


int topSort_Tarjan(ListAdj l, int aux[]){
  int i,  cycle = 0;

  t = V - 1;
  for(i=0;i < V;i++) vis[i] == WHITE;

  for(i=0;i < V && !cycle; i++)
    if(vis[i] == WHITE) cycle = DF_sol(g,i,n,tsort);

  return cycle; //para no final dizer se tem ciclo à funcao de output . se tiver ciclo não existe
}


//confirmar esta funcao com o codeboard
int DFS_sol(ListAdj l, int i, int sort[]){

  int cycle;
  struct edge P;
  vist[i] = GRAY;

  for(p=l[i]; p && !cycle; p=p->next){

    if(vis[p->dest] == GRAY) cycle = 1;
    else if(vis[p->dest] == WHITE)
      cycle = DFS_sol(g,p->dest,t->sort);
  }

  vis[i] = BLACK
  tsort[t--] = i;

  return cycle;
}


void inDegrees(ListAdj l, int inD[]){
  struct edge aux;
  for(int i = 0; i < V; i++){
    inD[i] = 0;
  }

  for(i = 0 ; i < V; i++){
    for(aux = l[i];aux;aux = aux-> next){
      inD[aux->dest] += 1;
    }
  }
}

int topSort_Kahn(ListAdj l, int aux[]){
  int inD[], first, last, v; //first e last para a queue;
  struct edge node;

  inDegrees(l,inD);

  first = last = 0;
  for(v=0;v < V; v++)
    if(inD[v] == 0) tsort[last++] = v;

  while(first != last){
    v = tsort[first++];
    for(p=g[v];p;)

  /*
    Falta acabar
  */
  }
}

/*
  MST
*/
void mst(ListAdj l, int parent[]){

  int status[V];
  int fringeLink[V];
  int fringeWgt[V];
  struct edge *ptr;
  int x,y;
  int fringeList;
  int edgeCount;
  int stuck;
  int sum;

    /* inicializacao */

  x = 0;
  status[0] = INTREE;
  parent[0] = -1;

  stuck = 0;

  edgeCount = 0;

  fringeList = NIL;

  for (y = 1 ; y < n ; y++) status[y] = UNSEEN;

  while(edgeCount<n-1 && !stuck) {
    ptr = l[x];
    while(ptr){
      y = ptr->dest;
      if(status[y] = FRINGE && ptr->custo < fringeWgt[y]){
        fringeWgt[y] = ptr->custo;
        parent[y] = x;
      }else if(status[ptr->dest] == UNSEEN){
        status[y] = FRINGE;
        fringeLink[y] = fringeList;
        fringeList = y;
        parent[y] = x;
        fringeWgt[y] = ptr->custo;
      }
    ptr = ptr->next;
    }
    if(fringeList == NIL) stuck = 1;
    else {
      int prev = 0;
      x = fringeList;
      for(y=fringeList; fringeLink[y] != NIL; y=fringeLink[y]){
        if(fringeWgt[fringeList[y]] < fringeWgt[x]){
          x = fringeLink[y];
          prev = y;
        }
      }
      if (x == fringeList) fringe

      /* falta acabar

      */

      /* ISTO É MEU

      int aux = -1;
      while(fringeLink[fringeList] != NIL){
        if(aux == -1)
          aux = fringeWgt[fringeList];
        else if(aux > fringeWgt[fringeList]){
          aux = fringeWgt[fringeList];
        }
        fringeList = fringeLink[fringeList];
      }
      */
    edgeCount++;
      }
  }
}

  void printPath(int cam[N][N], int o , int d){
    if(cam[o][d] == -1)
      printf("%d -> %d\n",o,d);
    else{
      printPath(cam,o,cam[o][d]);
      printPath(cam,cam[o][d],d);
    }
  }

