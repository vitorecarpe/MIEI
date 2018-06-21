#include <stdio.h>
#include <stdlib.h>

#define MAXL 65535

typedef struct intv {
	int ini;
	int fim;
	struct intv *ant;
	struct intv *prox;	
} Intv;

int reserva(struct intv** livres, int n){
	struct intv** aux = livres; struct intv* ant = NULL;
	int r = 0, dif, p;

	while(*aux && !r){
		dif = (*aux)->fim - (*aux)->ini;
		if(dif >= n){
			r = 1;
			p = (*aux)->ini;
		}
		else {
			aux = &((*aux)->prox);
		}
	}

	if(!r) return -1;
	else{
		
		if(dif != n) (*aux)->ini += n;
		else{
			struct intv *aux2 = *aux;
			*aux = (*aux)->prox;
			(*aux)->ant = aux2 -> ant;
			free(aux2);
		}
		return p;	
	}
	return -1;
}


int main () {
	struct intv* livres = (struct intv *) malloc (sizeof(struct intv));
	livres->ini = 0;
	livres->fim = 65535;
	livres->ant = NULL;
	livres->prox = NULL;
	int res, n, n2, lug;
	
	printf("Quantos lugares quer?\n");
	scanf("%d\n", &n);
	livres = reserva(livres, n, &res);
	printf("O primeiro lugar é %d!\n", res);

	if(livres) printf("O primeiro lugar livre é %d!\n", (livres->ini));
	else printf("Não existem lugares livres!\n");

	printf("Qual o lugar que quer libertar?\n");
	if(scanf("%d\n", &lug) == 1){
		printf("Quantos lugares quer libertar?\n");
		if(scanf("%d\n", &n2) == 1){
			livres = liberta(livres, lug, n2);
	
			if(livres) printf("O primeiro lugar é %d!\n", livres->ini);
			else printf("Não existem lugares livres!");
		}
	}
	return 0;
}