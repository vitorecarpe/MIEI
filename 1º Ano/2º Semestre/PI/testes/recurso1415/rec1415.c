#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct slist {
	int valor;
	struct slist *prox;
} *LInt;

int bitsUm (unsigned int n){
	int i = 0;
	while(n > 0){
		i += n%2;
		n /= 2; 
	}
	return i;
}

int limpaEspacos (char t []){
	int cont = 0, i = 0;
	while(t[i] != '\0'){
		if (t[i] != ' ' || t[i+1] != ' ') {t[cont] = t[i];cont++;}
		i++;
	}
	t[cont] = '\0';
	return cont;
}

int dumpL (LInt l, int v[], int N){
	int i = 0;
	while (i < N && l){
		v[i] = l->valor;
		i++;
		l = l->prox;
	}
	v[i] = '\0';
	return i;
}

LInt parte (LInt l){
	LInt new,auxn,auxl;
	if (l == NULL) return NULL;
	new = auxn = l->prox;
	auxl = l;
	while (auxn && auxn->prox){
		auxl->prox = auxn->prox;
		auxl = auxl->prox;
		auxn->prox = auxl->prox;
		auxn = auxn->prox;
	}
	if (auxn != NULL) auxn->prox = NULL;
	if (auxl != NULL) auxl->prox = NULL;
	return new;
}
//funções de teste
LInt newLInt (int x, LInt l){
	LInt new = (LInt) malloc (sizeof (struct slist));
	new -> valor = x;
	new -> prox = l;
	return new;
}

LInt geraLista (){
	LInt new;
	new = newLInt(9,newLInt(8,newLInt(2,newLInt(1,newLInt(7,newLInt(6,newLInt(4,NULL)))))));
	return new;
}
//end of funções de teste
typedef struct nodo {
	char nome [50];
	int numero ;
	int nota ; // >=0, <=20
	struct nodo *esq,*dir;
} *Alunos ;

int fnotas (Alunos a, int notas [21]){
	int e = 0, d = 0;
	if (a == NULL) return 0;
	notas[a->nota]++;
	e = fnotas(a->esq,notas);
	d = fnotas(a->dir,notas);
	return 1 + e + d;
}

int rankNota (int nota, int fnotas[21]){
	int sum = 0;
	nota += 1;
	while (nota <= 20) {sum += fnotas[nota];nota++;}
	return sum + 1;
}

int rankAluno (Alunos a, int numero, int fnotas[21]){
	while (a && a->numero != numero){
		if (numero < a->numero) a = a->esq;
		else a = a->dir;
	}
	if (a == NULL) {printf("Aluno inexistente!\n");return -1;}
	else return rankNota(a->nota,fnotas);
}

typedef struct strlist {
	char *string;
	struct strlist *prox;
} *StrList ;

void newStrList (char nome [], StrList *l){
	StrList new = (StrList) malloc (sizeof (struct strlist));
	strcpy(new -> string,nome);
	new -> prox = NULL;
	(*l) = new;
}

int comNota (Alunos a, int nota, StrList *l){
	int e = 0, d = 0;
	if (a == NULL) return 0;
	if (a->nota == nota) {
		newStrList (a->nome,l);
		l = &((*l)->prox);
	}
	e = comNota(a->esq,nota,l);
	d = comNota(a->dir,nota,l);
	return 1 + e + d;
}

int notasInf (int freq [21],int nota){
	int sum = 0;
	nota--;
	while (nota >= 0) {sum += freq[nota];nota--;}
	return sum;
}

void preencheAux (Alunos a, Alunos t[], int freq[21], int *notasAloc){
	int nInf;
	if (a == NULL) return;
	nInf = notasInf(freq,a->nota);
	t[nInf + notasAloc[a->nota]] = a;
	notasAloc[a->nota]++;
	preencheAux(a->esq,t,freq,notasAloc);
	preencheAux(a->dir,t,freq,notasAloc);
}

void preenche (Alunos a, Alunos t[], int freq[21]){
	int *notasAloc;
	notasAloc = (int *) calloc (21,sizeof(int));
	preencheAux (a,t,freq,notasAloc);
}

void imprime ( Alunos a ) {
	int i ;
	int notas[21] = {0};
	int quantos = fnotas(a,notas);
	Alunos todos[quantos];
	preenche(a,todos,notas);
	for (i = 0;i < quantos;i++)
		printf ( "%d %s %d\n",
			todos[i]->numero,
			todos[i]->nome,
			todos[i]->nota);
}

Alunos newA (char nome [], int numero, int nota, Alunos esq, Alunos dir){
	Alunos new = (Alunos) malloc (sizeof (struct nodo));
	strcpy(new -> nome,nome);
	new -> numero = numero;
	new -> nota = nota;
	new -> esq = esq;
	new -> dir = dir;
	return new; 
}

Alunos geraA (){
	return newA("Daniel",754,20,newA("Ines",700,18,newA("Tiago",300,14,NULL,NULL),newA("Marco",715,12,NULL,NULL)),newA("Silva",780,5,newA("Lipe",760,14,NULL,NULL),newA("Calada",800,12,NULL,NULL)));	
}

int main(){
	LInt l= geraLista();
	int toDump [50];
	int i;
	i=dumpL(l,toDump,5);
	for (;i>0;i--) printf("%d\n",toDump[i-1]);//pus a imprimir ao contrário é mais facil
	Alunos a = geraA();
	int notas[21] = {0};
	int quantos = fnotas(a,notas);
	printf("%d\n",rankAluno(a,715,notas));
	imprime (a);
	return 0;
}