#include <stdio.h>
#include <stdlib.h>

#define MAXL 65535

typedef struct intv {
	int ini;
	int fim;
	struct intv * prox;	
} Intv;

struct intv* reserva(struct intv* livres, int n, int *reserva){
	struct intv* aux = livres; struct intv* ant = NULL;
	int r = 0, dif;

	while(aux && !r){
		dif = aux->fim - aux->ini;
		if(dif >= (n-1)){
			r = 1;
			*reserva = aux->ini;
		}
		else {
			ant = aux;
			aux=aux->prox;
		}
	}

	if(!r) *reserva = -1;
	else{
		
		if(dif != (n-1)) aux->ini += n;
		else{
			
			if(ant){
				ant->prox = aux->prox;
				free(aux);
			}
			else{
				livres=livres->prox;
				free(aux);
			}
		}	
	}
	return livres;
}

struct intv* liberta(struct intv* livres, int lugar, int n){
	if(lugar > MAXL) return livres;

	struct intv *aux = livres, *ant=aux;

	while(aux && (aux->ini < lugar)){
		ant = aux;
		aux = aux->prox;
	}

	if(aux == ant){
		
		if(aux){
			int dif = aux->ini - lugar;

			if(dif <= n){
				aux->ini = lugar;
			}
			else{
				livres = (struct intv*) malloc (sizeof(struct intv));
				livres->ini = lugar;
				livres->fim = lugar+n-1;
				livres->prox = aux;
			}
		}
		else{
			livres = (struct intv*) malloc (sizeof(struct intv));
			livres->ini = lugar;
			livres->fim = lugar+n-1;
			livres->prox = aux;
		}
	}
	else{

		if(lugar > (ant->fim +1)){

			if(aux && ((aux->ini -lugar)<=n)) aux->ini = lugar;
			else{
				struct intv *ln = (struct intv*) malloc (sizeof(struct intv));
				ln->ini = lugar;
				ln->fim = lugar+n-1;
				ln->prox = aux;
				ant->prox = ln;
			}
		}
		else{

			if(aux && ((aux->ini -lugar)<=n)){
				ant->fim = aux->fim;
				ant->prox = aux->prox;
				free(aux);
			}
			else ant->fim = lugar+n-1;
		}
	}
	return livres; 
}

int main () {
	struct intv* livres = (struct intv *) malloc (sizeof(struct intv));
	livres->ini = 0;
	livres->fim = 65535;
	livres->prox = NULL;
	int res, n, n2, lug, i=0;


	for(i=0; i<5; i++){	
/*		printf("Quantos lugares quer?\n");
		scanf("%d", &n);
		livres = reserva(livres, n, &res);
		printf("O primeiro lugar é %d!\n", res);

		if(livres) printf("O primeiro lugar livre é %d!\n", (livres->ini));
		else printf("Não existem lugares livres!\n");

		printf("Qual o lugar que quer libertar?\n");
		if(scanf("%d", &lug) == 1){
			printf("Quantos lugares quer libertar?\n");
			if(scanf("%d", &n2) == 1){
				livres = liberta(livres, lug, n2);

				if(livres) printf("O primeiro lugar é %d! O fim deste setor é %d\n", livres->ini, livres->fim);
				else printf("Não existem lugares livres!");
			}
		}
*/
		livres = reserva(livres, (i+1)*100,&res);
		livres = liberta(livres, i*50, 35);
	}
	return 0;
}