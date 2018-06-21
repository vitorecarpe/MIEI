#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int mystrlen (char s []){
	int i = 0;
	while (s[i]) i++;
	return i;
}

int palindrome (char s[]){
	int eP = 0,dP = mystrlen(s) - 1;
	while (eP < dP){
		if (s[eP] != s[dP]) return 0;
		eP++;dP--;
	}
	return 1;
}

char *strlchr (char *s, char c){
	char *pos = NULL;int i = 0;
	while(s[i]){
		if (s[i] == c) pos = &s[i];
		i++;
	}
	return pos;
}

void ordena (int nums [], int notas [], int N){
	int i,j,save;
	for (i = 0;i < N;i++){
		for (j = i;j > 0 && notas[j] > notas[j-1];j--){
			save = notas[j];
			notas[j] = notas[j-1];
			notas[j-1] = save;
			save = nums[j];
			nums[j] = nums[j-1];
			nums[j-1] = save;
		}
	}
}

typedef struct slist {
	int valor;
	struct slist *prox;
} *LInt;

int dumpL (LInt l, int v[], int N){
	int i = 0;
	while (l && i < N){
		v[i] = l->valor;
		l = l->prox;
		i++;
	}
	return i;
}

LInt newLInt (int x, LInt l){
	LInt new = (LInt) malloc (sizeof (struct slist));
	new->valor = x;
	new->prox = l;
	return new;
}

LInt somas (LInt l){
	LInt new,aux;
	if (l == NULL) return NULL;
	new = aux = newLInt(l->valor,NULL);
	l = l->prox;
	while (l){
		aux->prox = newLInt(l->valor + aux->valor,NULL);
		l = l->prox;
		aux = aux->prox;
	}
	return new;
}

void remreps (LInt l){
	LInt aux;LInt toFree;
	if (l == NULL) return;
	aux = l->prox;
	while (aux){
		if (aux->valor != l->valor) {l->prox = aux;l = l->prox;aux = aux->prox;}
		else {toFree = aux;aux = aux->prox;free(toFree);}
	}
	l->prox = NULL;
}

typedef struct sbin{
	int valor;
	struct sbin *esq, *dir;
} *Abin;

int contaFolhas (Abin a){
	int e, d;
	if (a == NULL) return 0;
	if (a->esq == NULL && a->dir == NULL) return 1;
	e = contaFolhas(a->esq);
	d = contaFolhas(a->dir);
	return e + d;
}

int valLinhas(int board[][3], int N){
	int l, c;
	int result;
	for (l = 0;l < N;l++){
		for (result = 0,c = 0;c < N;c++) result += board[l][c];
		if (result > 1) return 0;
	}
	return 1;
}

int valColunas(int board[][3], int N){
	int l, c;
	int result;
	for (c = 0;c < N;c++){
		for (result = 0,l = 0;l < N;l++) result += board[l][c];
		if (result > 1) return 0;
	}
	return 1;	
}
//diagonais crescentes
int valSoma(int board[][3],int N){
	int l, c, cAux, result;
	for (c = 0;c < N;c++){
		for (result = 0,cAux = c,l = 0;cAux >= 0;cAux--,l++) result += board[l][cAux];
		if (result > 1) return 0;
	}
	for (c = 1;c < N;c++){
		for (result = 0,cAux = c,l = N-1; cAux < N;cAux++,l--) result += board[l][cAux];
		if (result > 1) return 0;
	}
	return 1;
}
//diagonais decrescentes
int valDif(int board[][3],int N){
	int c, l, lAux, result;
	for (l = 0;l < N;l++){
		for (result = 0,lAux = l,c = 0;lAux < N;lAux++,c++) result += board[lAux][c];
		if (result > 1) return 0;
	}
	for (l = N-1;l >= 0;l--){
		for (result = 0,lAux = l,c = N-1; lAux >= 0;lAux--,c--) result += board[lAux][c];
		if (result > 1) return 0;
	}
	return 1;
}

int validSol (int board[][3],int N){
	return (valLinhas(board,N) && valColunas(board,N) && valSoma(board,N) && valDif(board,N));
}

int main (){
	int i = 0;
	int notas [5] = {14,17,11,9,18};
	int numeros [5] = {1348,178,12343,352,767};
	ordena(numeros,notas,5);
	for (i = 0;i < 5;i++) printf("%d %d\n",numeros[i],notas[i]);

	printf("%c\n",*(strlchr("lnfij",'i')));

	int board [3] [3] = {{1,0,0},{0,0,1},{0,0,0}};
	printf("%d\n",validSol(board,3));
	return 0;
}