#include <stdio.h>
#include <stdlib.h>

// Parte 1 - Listas ligadas

typedef struct lligada {
	int valor;
	struct lligada *prox;
} *LInt;

// 1

int length(LInt l) { // 10 em 10
	int c = 0;

	while (l != NULL) {
		c++;
		l = l->prox;
	}

	return c;
}

// 2

void freeL(LInt l) { // não tem teste na codeboard
	LInt temp;

	while (l != NULL) {
		temp = l;
		l = l->prox;
		free(temp);
	}
}

// 3

void imprimeL(LInt l) { // não tem teste na codeboard
	while (l != NULL) {
		printf("%d\n", l->valor);
		l = l->prox;
	}
}

// 4

LInt reverseL(LInt l) { // 10 em 10
	LInt current, current, next;
	current = l;
	current = NULL;

	while (current != NULL) {
		next = current->prox;
		current->prox = current;
		current = current;
		current = next;
	}

	return current;
}

// 5

void insertOrd(LInt *l, int x) { // 10 em 10
	LInt novo = (LInt) malloc(sizeof(LInt));
	novo->valor = x;
	novo->prox = NULL;

	if (*l == NULL) {
		*l = novo;
		return;
	}

	LInt atual = *l;

	while (atual->prox && atual->prox->valor <= x)
		atual = atual->prox;
	
	if (atual->valor <= x) {
		novo->prox = atual->prox;
		atual->prox = novo;
	} else {
		novo->prox = atual;
		*l = novo;
	}
}

// 6

int removeOneOrd(LInt *l, int x) { // 10 em 10
	if (*l == NULL) return 1;

	LInt current = *l;
	LInt prev = NULL;

	while (current->prox && current->valor != x) {
		prev = current;
		current = current->prox;
	}

	if (current->prox == NULL) {
		if (current->valor == x) {
			if (prev) prev->prox = NULL;
			else *l = NULL;
			free(current);
			return 0;
		}
		else return 1;
	} else {
		prev->prox = current->prox;
		free(current);
		return 0;
	}
}

// 7

void insereElem(LInt *l, int x) { // insere sempre no fim da lista
	LInt novo = (LInt) malloc(sizeof(LInt));
	novo->valor = x;
	novo->prox = NULL;

	if (*l == NULL) { *l = novo; return; }	

	LInt atual = *l;

	while (atual->prox)
		atual = atual->prox;

	atual->prox = novo;
}

void merge(LInt *r, LInt l1, LInt l2) { // 10 em 10
	if (l1 == NULL && l2 == NULL) return;

	if (l1 == NULL) { *r = l2; return; }

	if (l2 == NULL) { *r = l1; return; }

	while (l1 && l2) {
		if (l1->valor <= l2->valor) {
			insereElem(r, l1->valor);
			l1 = l1->prox;
		} else {
			insereElem(r, l2->valor);
			l2 = l2->prox;
		}
	}

	if (l1) {
		while (l1) {
			insereElem(r, l1->valor);
			l1 = l1->prox;
		}
	}

	if (l2) {
		while (l2) {
			insereElem(r, l2->valor);
			l2 = l2->prox;
		}		
	}
}

// 8

void splitMS(LInt l, int x, LInt *mx, LInt *Mx) { // 10 em 10
	if (l == NULL) return;

	while (l) {
		if (l->valor < x) insereElem(mx, l->valor);
		else insereElem(Mx, l->valor);
		l = l->prox;
	}
}

// 9

LInt parteAmeio(LInt *l) { // 10 em 10
	int metade = length(*l) / 2;
	int i = 0;

	LInt res = NULL;

	while (*l && i < metade) {
		insereElem(&res, (*l)->valor);
		*l = (*l)->prox;
		i++;
	}

	return res;
}

// 10

int removeAll(LInt *l, int x) { // 10 em 10
	int c = 0;

	if (*l == NULL) return 0;

	LInt current = *l;
	LInt prev = NULL;
	LInt temp;

	while (current) {
		if (current->valor == x) {
			if (prev) prev->prox = current->prox;
			else *l = current->prox;
			temp = current;
			current = current->prox;
			free(temp);
			c++;
		} else {
			prev = current;
			current = current->prox;
		}
	}

	return c;
}

// 11

int removeDups(LInt *l) { // 10 em 10
	if (*l == NULL || (*l)->prox == NULL) return 0;

	LInt current = *l;
	LInt prev = NULL;
	LInt aux, temp;

	while (current) {
		prev = current;
		aux = current->prox;

		while (aux) {
			if (aux->valor == current->valor) {
				prev->prox = aux->prox;
				temp = aux;
				aux = aux->prox;
				free(temp);
			} else {
				prev = aux;
				aux = aux->prox;
			}
		}

		current = current->prox;
	}

	return 0;
}


// 12

int maior_elemento_LInt(LInt *l) {
	int maior = 0;

	LInt atual = *l;

	while (atual) {
		if (atual->valor > maior) maior = atual->valor;
		atual = atual->prox;
	}

	return maior;
}

int removeMaiorL(LInt *l) { // 10 em 10
	if (*l == NULL) return 0;

	int maior = maior_elemento_LInt(l);

	if ((*l)->prox == NULL) { *l = NULL; return maior; }

	LInt current = *l;
	LInt prev = NULL;
	LInt temp;

	while (current) {
		if (current->valor == maior) {
			if (prev) prev->prox = current->prox;
			else *l = current->prox;
			temp = current;
			current = current->prox;
			free(temp);
			return maior;
		} else {
			prev = current;
			current = current->prox;
		}
	}
}

// 13

void init(LInt *l) { // 10 em 10
	if ((*l)->prox == NULL) { *l = NULL; return; }

	LInt current = *l;
	LInt prev = NULL;
	LInt temp;

	while (current->prox) {
		prev = current;
		current = current->prox;
	}

	prev->prox = current->prox;
	temp = current;
	free(temp);
}

// 14

void appendL(LInt *l, int x) { // 10 em 10 (igual à função insereElem())
	LInt novo = (LInt) malloc(sizeof(LInt));
	novo->valor = x;
	novo->prox = NULL;

	if (*l == NULL) { *l = novo; return; }

	LInt atual = *l;

	while (atual->prox)
		atual = atual->prox;

	atual->prox = novo;	
}

// 15

void concatL(LInt *a, LInt b) { // 10 em 10
	if (*a == NULL) { *a = b; return; }

	LInt atual = *a;

	while (atual->prox) atual = atual->prox;

	atual->prox = b;
}

// 16

LInt cloneL(LInt l) { // não tem teste na codeboard
	LInt res = NULL;
	LInt atual = l;

	while (atual->prox) {
		insereElem(&res, atual->valor);
		atual = atual->prox;
	}

	return res;
}

// 17

void insereElem_inicio(LInt *l, int x) {
	LInt novo = (LInt) malloc(sizeof(LInt));
	novo->valor = x;
	novo->prox = NULL;

	if (*l == NULL) { *l = novo; return; }

	novo->prox = *l;
	*l = novo;
}

LInt cloneRev(LInt l) { // 10 em 10
	LInt res;

	if (l == NULL) return NULL;
	
	if (l->prox == NULL) {
		insereElem(&res, l->valor);
		return res;
	}

	LInt atual = l;

	while (atual) {
		insereElem_inicio(&res, atual->valor);
		atual = atual->prox;
	}

	return res;
}

// 18

int maximo(LInt l) { // 10 em 10 (igual à função maior_elemento_LInt())
	int maior = 0;

	LInt atual = l;

	while (atual) {
		if (atual->valor > maior) maior = atual->valor;
		atual = atual->prox;
	}

	return maior;	
}

// 19

int take(int n, LInt *l) { // 10 em 10
	if (length(*l) <= n) return length(*l); 
	
	if (n == 0) { 
		freeL(*l); // dúvida: fazendo freeL(*l), *l fica a NULL?
		*l = NULL;
		return 0;
	}

	int i = 0;
	LInt current = *l;
	LInt temp;
	LInt prev = NULL;

	while (current && i < n) {
		prev = current;
		current = current->prox;
		i++;
	}

	prev->prox = NULL;

	while (current) {
		temp = current;
		current = current->prox;
		free(temp);
	}

	return n;
}

// 20

int drop(int n, LInt *l) { // 10 em 10
	int tam = length(*l);

	if (n == 0) return tam;

	if (tam <= n) {
		freeL(*l); // dúvida: fazendo freeL(*l), *l fica a NULL?
		*l = NULL;
		return tam;
	}

	int i = 0;
	LInt temp;

	while (*l && i < n) {
		temp = *l;
		*l = (*l)->prox;
		free(temp);
		i++;
	}

	return n;
}

// 21

LInt NForward(LInt l, int N) {
	while (l != NULL && N > 0) {
		N--;
		l = l->prox;
	}

	return l;
}

// 22

int listToArray(LInt l, int v[], int N) { // 10 em 10
	int i = 0;
	LInt atual = l;

	while (atual && i < N) {
		v[i] = atual->valor;
		atual = atual->prox;
		i++;
	}

	return i;
}

// 23

LInt arrayToList(int v[], int N) { // 10 em 10
	int i;
	LInt res = NULL;
	
	if (N == 0) return res;

	for (i=0; i < N; i++)
		insereElem(&res, v[i]);

	return res;
}

// 24

LInt somasAcl(LInt l) { // 10 em 10
	int soma = 0;
	LInt atual = l;
	LInt res = NULL;

	while (atual) {
		soma += atual->valor;
		insereElem(&res, soma);
		atual = atual->prox;
	}

	return res;
}

// 25

void remreps(LInt l) { // 10 em 10 (feita pelo Miguel Lobo)
	LInt *e, tmp;

	e = &l;

	while (*e != NULL){
		for (tmp = l ; tmp->valor != (*e)->valor && tmp != *e ; tmp = tmp->prox);

		if (tmp != *e){
			free(*e);
			*e = (*e)->prox;
		}
		else e = &((*e)->prox);
	}
}

// 26

LInt rotateL(LInt l) { // não percebo porque não dá (testes da codeboard errados?)
	if (l == NULL || l->prox == NULL) return l;

	LInt atual = l;

	while (atual->prox) atual = atual->prox;

	int ultimo = atual->valor;
	atual->valor = l->valor;
	l->valor = ultimo;

	return l;
}

// 27

LInt parte(LInt l) { // 10 em 10 (feita pelo Miguel Lobo)
	LInt *n, *e, r;

	n = &r;
	e = &l;

	int par = 0;

	while (*e != NULL) {
		if (par) {
			*n = *e;
			n = &((*n)->prox);

			*e = (*e)->prox;
			par = 0;
		} else {
			e = &((*e)->prox);
			par = 1;
		}
	}

	*n = NULL;
	return r;
}

// Main

int main() {

/*	LInt l = (LInt) malloc(sizeof(LInt));
	LInt k = (LInt) malloc(sizeof(LInt));

	l->valor = 1;
	l->prox = k;
	k->valor = 2;
	k->prox = NULL;

	imprimeL(l);

	LInt l = (LInt) malloc(sizeof(LInt));
	l->valor = 11;
	l->prox = NULL;

	insertOrd(&l, 2);
	imprimeL(l);

	LInt *l = (LInt *) malloc(sizeof(LInt *));
	(*l)->valor = 21;
	(*l)->prox->valor = 22;
	(*l)->prox->prox = NULL;

	insertOrd(l, 22);
*/
	return 0;
}