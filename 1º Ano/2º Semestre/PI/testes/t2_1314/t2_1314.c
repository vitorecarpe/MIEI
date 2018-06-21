#include <stdio.h>
#include <stdlib.h>

typedef struct slist {
	int valor;
	struct slist *prox;
} *LInt;

LInt newLInt (int x, LInt l){
	LInt new = (LInt) malloc (sizeof (struct slist));
	new -> valor = x;
	new -> prox = l;
	return new;
}

LInt fromArray (int v [], int N){
	LInt new = NULL;
	N--;
	while (N >= 0){
		new = newLInt(v[N],new);
		N--;
	}
	return new;
}

#define BSize 2

typedef struct larray{
	int valores[BSize];
	struct larray *prox;
} *LArrays;

typedef struct stack{
	int sp;
	LArrays s;
} Stack;

LArrays newLArrays (int x,LArrays s){
	LArrays new = (LArrays) malloc (sizeof (struct larray));
	new->valores[0] = x;
	new->prox = s;
	return new;
}

void push (Stack *st, int x){
	if (st->sp == BSize){
		st->s = newLArrays(x,st->s);
		st->sp = 1;
	}
	else {st->s->valores[st->sp] = x;(st->sp)++;}
}

int pop (Stack *st, int *t){
	if (st->s == NULL) return 1;
	if (st->sp == 1){
		(*t) = st->s->valores[0];
		st->s = st->s->prox;
		st->sp = BSize;
	}
	else{
		(*t) = st->s->valores[(st->sp) - 1];
		(st->sp)--;
	}
	return 0;
}

typedef struct spares {
	int x, y;
	struct spares *prox;
} Par, *LPares;
/*
typedef struct slist {
	int valor;
	struct slist *prox;
} Nodo, *LInt;
*/
LPares newLPares (int x, int y, LPares l){
	LPares new;
	new = (LPares) malloc (sizeof (struct spares));
	new -> x = x;
	new -> y = y;
	new -> prox = l;
	return new;
}

LPares zip (LInt a, LInt b, int *c){
	if (!a || !b) {*c = 0; return NULL;}
	LPares new,aux;
	new = newLPares(a->valor,b->valor,NULL);
	aux = new;
	a = a->prox;
	b = b->prox;
	(*c)++;
	while (a && b){
		aux->prox = newLPares(a->valor,b->valor,NULL);
		aux = aux->prox;
		a = a->prox;
		b = b->prox;
		(*c)++;
	}
	return new;
}

void showLPares (LPares l,int t){
	while (l){
		printf("(%d,%d)\n",l->x,l->y);
		l = l->prox;
	}
	printf("tamanho %d\n",t );
}

typedef struct no {
	int value;
	struct no *esq,*dir,*pai;
} No, *Tree;

void calculaPais(Tree t){
	if (t == NULL) return;
	if (t->esq && t->dir){
		t->esq->pai = t;
		t->dir->pai = t;
		calculaPais(t->esq);
		calculaPais(t->dir);
	}
	else if (t->esq){
		t->esq->pai = t;
		calculaPais(t->esq);
	}
	else if (t->dir){
		t->dir->pai = t;
		calculaPais(t->dir);
	}
	else return;
}

Tree next(Tree t){
	if (t == NULL) return NULL;
	if (t->dir) return t->dir;
	else return NULL;
}

Tree newABin (int x,Tree esq,Tree dir,Tree pai){
	Tree new = (Tree) malloc (sizeof(struct no));
	new->value = x;
	new->esq = esq;
	new->dir = dir;
	new->pai = pai;
	return new;
}

Tree geraABin (){
	Tree new;
	new = newABin(3,newABin(2,NULL,NULL,NULL),newABin(5,NULL,NULL,NULL),NULL);
	return new;
}

void showABin(Tree a){
	if (a == NULL) return;
	printf("%d\n",a->value);
	if (a->pai) printf("%d\n",a->pai->value);
	showABin (a->esq);
	showABin (a->dir);
}

int main (){
	int t = 0;
	LInt a =  newLInt(9,newLInt(8,newLInt(2,newLInt(1,newLInt(7,newLInt(6,newLInt(4,NULL)))))));
	LInt b = newLInt(6,newLInt(4,NULL));
	LPares l = zip(a,b,&t);
	showLPares(l,t);

	Tree tr = geraABin();
	calculaPais(tr);
	showABin(tr);

	tr = next(tr);
	showABin(tr);
}