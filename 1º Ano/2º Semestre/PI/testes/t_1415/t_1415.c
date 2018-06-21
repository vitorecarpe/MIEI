#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct slist {
   int valor;
   struct slist *prox;
} *LInt;
typedef struct nodo {
   int valor;
   struct nodo *esq, *dir;
} *ABin;

int mystrlen (char str []){
int i=0,s=0;
while (str[i]) {s++;i++;};
return s;
}

char *mystrstr (char s1[], char s2[]) {
  int i, j, w;
  for (i=0; s1[i]; i++){
      for (w=0,j=i; s2[w] && s2[w]==s1[j] && s1[j]; j++,w++);
      if (s2[w] == '\0') return &s1[i];
  }
  if (mystrlen(s1)==0 && mystrlen(s2)==0) return s1;
  else return 0;
}

int maxInd (int v[], int n){
	n--;
	int max = v[n];int imax = n;
	while (n >= 0){
		if (v[n] >= max) {max = v[n];imax = n;}
		n--;
	}
	return imax;
}

void concat (LInt *a, LInt b){
	if (*a == NULL) {*a = b; return;}
	while (*a) a = &((*a)->prox);
	*a = b; 
}

LInt newLInt (int x, LInt l){
	LInt new = (LInt) malloc (sizeof (struct slist));
	new -> valor = x;
	new -> prox = l;
	return new;
}

LInt nivelL (ABin a, int n){
	LInt e,d;
	if (a == NULL) return NULL;
	if (n == 1) return newLInt(a->valor,NULL);
	e = nivelL(a->esq,n-1);
	d = nivelL(a->dir,n-1);
	concat(&e,d);
	return e;
}

int elem (int x,int v [],int n){
	n--;
	while (n >= 0){
		if (v[n] == x) return 1;
		n--;
	}
	return 0;
}

void imprimeMaior (){
	int v [100];
	int n = 0;
	int x;
	int maxI;
	scanf("%d",&x);
	while (n < 100 && !elem(x,v,n)){
		v[n] = x;
		scanf("%d",&x);
		n++;
	}
	maxI = maxInd(v,n);
	printf("O maior valor lido é %d e foi o %dº elemento escrito.\n",v[maxI],maxI + 1);
}

typedef struct strlist {
    char *string;
    struct strlist *prox;
} *StrList;

int cpystr (char s [],char t [],int N){
	int i = 0;
	while(i < N && s[i]){
		t[i] = s[i];
		i++;
	}
	if (i != N) t[i] = ' ';
	else t[i] = '\0';
	return i + 1;
}

void unwords (StrList s,char t[],int N){
	N--;
	int i = 0;
	while (s && i < N){
		i += cpystr(s->string,&t[i],N-i);
		s = s->prox;
	}
	t[i] = '\0';
}

StrList newStr(char *t,StrList s){
	StrList new = (StrList) malloc (sizeof (struct strlist));
	new->string = t;
	new->prox = s;
	return new;
}

int words (char t[], StrList *l){
	int i = 0, pal = 0;
	int j;
	while (t[i]){
		while (t[i] == ' ') i++;
		j = i;
		while (t[i] != '\0' && t[i] != ' ') i++;
		t[i] = '\0';i++;
		(*l) = newStr(&t[j],NULL);
		l = &((*l)->prox);
		pal++;
	}
	return pal;
}

int antDif (int v [], int i, int j){
	while (i < j){
		if (v[i] == v[j]) return 0;
		i++;
	}
	return 1;
}

int maxuniqueseq (int v[], int N){
	int tmax = 0, i = 0,j;
	while (i < N){
		for (j = i+1;v[j] && antDif(v,i,j);j++);
		if (tmax < j-i) tmax = j-i;
		i++;
	}
	return tmax;
}

int main (){
	//imprimeMaior();

	char t [20];
	StrList s = newStr("Carne",newStr("Peixe",newStr("omfgwtfisthis",newStr("naoaoaoa",NULL))));
	unwords(s,t,20);
	printf("%s\n",t);
	
	//StrList l;
	//char t [30];
	//char *x = (char *) malloc (26);
	//strcpy(x,"hoje  estamos a cozinhar");
	//int pal = words(x,&l);
	//unwords(l,t,30);
	//printf("%s\n",t);
	
	//int v[10] = {8,2,2,3,4,1,6,5,1,7};
	//printf("%d\n",maxuniqueseq(v,10));

	return 0;
}