#define OCUPADO o
#define LIVRE l
#define REMOVIDO r

typedef struct set{
  int size;
  char *flag;
  int *value;
}


Set init(int tamanho){
  Set s = (Set)malloc(sizeof(struct set));
  if(!s) return NULL;
  s->size = tamanho;
  s->value = (int*) malloc(sizeof(int)*tamanho);
  s->flag = (char*) malloc(sizeof(char)*tamanho);
  if(!value || !flag){
    free(s->flag);
    free(s->value);
    free(s);
    return NULL;
  }
  for(int i=0;i<tamanho;i++){
    s->flag[i] = LIVRE
  }
  return s;
}

int hash(int valor,int size){
  return valor % size
}

int add(Set s, int valor){
  int a = hash(valor,s->size);
  int saltos = 0;
  while(saltos != s->size ){
    if(s->flag[a] != OCUPADO){
      s->value[a] = valor;
      s->flag[a] = OCUPADO;
      return 0;
    }
    else{
      a =  ++valor % s->size;
      saltos++;
    }
  }
  return 1;
}

//VERSÂO PROFESSOR ALCINO

int add(Set s, int valor){
  int a = hash(valor,s->size);
  int i;
  while(i=0; i<s->size; i++){
    if(s->flag[a] != OCUPADO){
      s->value[a] = valor;
      s->flag[a] = OCUPADO;
      return 0;
    }
    else
      a =  ++a % s->size;
  }
  return 1;
}
/*
 Para a função add.
 Considerando um Array com n posições livres , n posições ocupados , n posiçoes removidas
 Melhor caso - a posição i que é dada pelo hash é uma posição livre
 Pior caso - um cluster de ocupados e começar no inicio no cluster e percorrer tudo até ter a posição livre
*/


//ISTO ESTA ERRADO - versao cardoso
int elem(Set s,int valor){
  int a = hash(valor,s->size);
  int saltos = 0;
  while(saltos != s->size){
    if(s->flag[a] == LIVRE){
      return -1;
    }else if(s->value[a] != valor){ //FALTA COLOCAR QUE ESTÀ ocupado
      saltos++;
      a = ++a ‰ s->size;
    }else
      return s->value[a];
  }
}

//VERSÃO PROFESSOR ALCINO
int elem(Set s,int valor){
  int a = hash(valor,s->size);
  int i;
  while(i=0; i<s->size; i++){
    if(s->flag[a] == OCUPADO && s->value[a] == valor){
      return 0; //EXISTE o elemento
    }else if(s->flag[a] == LIVRE){
      return 1;
    }else{
      a = ++a ‰ s->size;
    }
  }
  return 1;
}

/*
 Para a função elem
 Considerando um Array com n posições livres , n posições ocupados , n posiçoes removidas
 Melhor caso - a posição i que é dada pelo hash é a posição que contêm o elemento ou é uma posição livre
 Pior caso - um cluster de ocupados e removidos e começar no inicio no cluster e percorrer tudo até ter a posição livre
*/


int rem(Set s,int valor){
  int a = hash(valor,s->size);
  int saltos;
  while(saltos != s->size){
    if(s->flag[a] == OCUPADO && s->value[a] == valor){
      s->flag[a] == REMOVIDO;
      return 0;
    } else if(s->flag[a] == LIVRE)
      return 1;
    else {
      saltos++;
      a = ++a % s->size;
    }
  }
  return 1;
}


int clearingHash(Set s, int valor){
  Set aux = init(s->size);
  if(!aux) return 1;
  for(int i = 0; i < s->size; i++){
    if(s->flag[i] == OCUPADO)
      add(aux,s->valor[i]);
  }
  free(s->flag);
  free(s->value);
  s->flag = aux->flag;
  s->value = aux->value;
  free(aux);
  return 0;
}
