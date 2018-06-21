#include <stdio.h>
#include <stdlib.h>

typedef struct lligada {
    int valor;
    struct lligada *prox;
} *LInt;


LInt newLInt (int v, LInt t){
    LInt new = (LInt) malloc (sizeof (struct lligada));
    if (new!=NULL) {
        new->valor = v;
        new->prox  = t;
    }
    return new;
}

//1

int length (LInt l){
	int tam;
	for (tam = 0;l ; tam++)
		l = l->prox;
	return tam;
}


//2
void freeL (LInt l){
	LInt tmp;
	while (l){
		tmp = l;
		l = l->prox;
		free(tmp);
	}
}


//3
void imprimeL (LInt l){
	while (l){
		printf("%d\n", l->valor);
		l = l->prox;
	}
}


//4
LInt reverseL (LInt l){
	LInt tmp;
	LInt anterior = NULL;
	while (l){
		tmp = l->prox;
		l->prox = anterior;
		anterior = l;
		l = tmp;
	}
	return anterior;
}


//5
void insertOrd (LInt *l, int x){
	while (*l && (*l)->valor < x)
		l = &((*l)->prox);
	*l = newLInt(x, *l);
}


//6
int removeOneOrd (LInt *l, int x){
	while (*l && (*l)->valor < x)
		l = &((*l)->prox);
	if ((*l) && (*l)->valor == x){
		free (*l);
		*l = (*l)->prox;
		return 0;
	}
	else return 1;
}


//7
void merge (LInt *r, LInt a, LInt b){
	while (a && b){
		if (a->valor <= b->valor){
			*r = newLInt(a->valor, *r);
			r = &((*r)->prox);
			a = a->prox;
		}
		else{
			*r = newLInt(b->valor, *r);
			r = &((*r)->prox);
			b = b->prox;
		}
	}
	while (a){
		*r = newLInt(a->valor, *r);
		r = &((*r)->prox);
		a = a->prox;
	}
	while (b){
		*r = newLInt(b->valor, *r);
		r = &((*r)->prox);
		b = b->prox;
	}
}


//8
void splitMS (LInt l, int x, LInt *mx, LInt *Mx){
	while (l){
		if (l->valor < x){
			*mx = newLInt(l->valor,*mx);
			mx = &((*mx)->prox);
		}
		else{
			*Mx = newLInt(l->valor,*Mx);
			Mx = &((*Mx)->prox);
		}
		l = l->prox;
	}
}


//9
LInt parteAmeio (LInt *l){
    LInt *tmp, r = *l; 
    int c = length(*l)/2;
    tmp = l;
    while (c){
        tmp = &((*tmp)->prox);
        *l = (*l)->prox;
        c--;
    }
	if (r == *l) r = NULL;
	else *tmp = NULL;
	return r;
}


//10
int removeAll (LInt *l, int x){
	int c = 0;
	while (*l){
		if (x == (*l)->valor){
			*l = (*l)->prox;
			c++;
		}
		else l = &((*l)->prox);
	}
	return c;
}


//11
int existeValor (int valores[], int x, int n){
	int i;
	for (i=0; i<n && valores[i] != x; i++);
	return i < n;
}

int removeDups (LInt *l){
	LInt tmp = *l;
	int c, n=0;
	for (c=0; tmp; tmp = tmp->prox, c++);
	int valores[c];
	c = 0;
	while (*l){
		if (existeValor (valores, (*l)->valor, n)){
			*l = (*l)->prox;
			c++;
		}
		else{
			valores[n] = (*l)->valor;
			n++;
			l = &((*l)->prox); 
		}
	}
	return c;
}


//12
int removeMaiorL (LInt *l){
	int maior = (*l)->valor;
	LInt tmp = *l;
	for (;tmp; tmp = tmp->prox) // procura o maior
		if (tmp->valor > maior)
			maior = tmp->valor;
	while (*l){
		if ((*l)->valor == maior){
			*l = (*l)->prox;
			return maior;
		}
		else l = &((*l)->prox);
	}
}


//13
void init (LInt *l){
	while((*l)->prox)
		l = &((*l)->prox);
	free(*l);
	*l = NULL;
}

//14
void appendL (LInt *l, int x){
	while (*l)
		l = &((*l)->prox);
	*l = (LInt) malloc (sizeof (struct lligada));
	(*l)->valor = x;
	(*l)->prox = NULL;
}


//15
void concatL (LInt *a, LInt b){
	while (*a)
		a = &((*a)->prox);
	*a = b;
}


//16
LInt cloneL (LInt l){
	LInt nova = NULL, *aux;
	aux = &nova;
	while (l){
		*aux = (LInt) malloc (sizeof(struct lligada));
		(*aux)->valor = l->valor;
		aux = &((*aux)->prox);
		l = l->prox;
	}
	*aux = NULL;
	return nova;
}


//17
LInt cloneRev (LInt l){
	LInt nova, tmp;
	for (nova = NULL; l; l = l->prox){
		tmp = nova;
		nova = (LInt) malloc (sizeof (struct lligada));
		nova->valor = l->valor;
		nova->prox = tmp;
	}
	return nova;
}

//18
int maximo (LInt l){
	int maior = l->valor;
	for (; l; l = l->prox)
		if (l->valor > maior)
			maior = l->valor;
	return maior;
}


//19
int take (int n, LInt *l){
	int c=0;
	while (*l && n){
		l = &((*l)->prox);
		n--; c++;
	}
	while (*l){
		LInt *tmp;
		tmp = l;
		*l = (*l)->prox;
		free (*tmp);
	}
	return c;
}


//20
int drop (int n, LInt *l){
	int c=0;
	while (*l && n){
		free(*l);
		*l = (*l)->prox;
		c++; n--;
	}
	return c;
}


//21
LInt Nforward (LInt l, int N){
	while (N){
		l = l->prox;
		N--;
	}
	return l;
}


//22
int listToArray (LInt l, int v[], int N){
	int c=0;
	while (l && N){
		v[c] = l->valor;
		l = l->prox;
		c++; N--;
	}
	return c;
}

//23
LInt arrayToList (int v[], int N){
	int i;
	LInt *tmp,l;
	tmp=&l;
	for (i=0;i<N;i++){
		*tmp = (LInt) malloc(sizeof(struct lligada));
		(*tmp)->valor = v[i];
		tmp = &((*tmp)->prox);
	}
	*tmp = NULL;
	return l;
}


//24
LInt somasAcL (LInt l){
	LInt novo = NULL, *tmp;
	int soma = 0;
	tmp = &l;
	while (l){
		soma += l->valor;
		*tmp = (LInt) malloc (sizeof(struct lligada));
		(*tmp)->valor = soma;
		(*tmp)->prox = NULL;
		tmp = &((*tmp)->prox);
		l = l->prox;
	}
	return novo;
}


//25
void remreps (LInt l){
	LInt *aux, tmp = l;
	aux = &l;
	while (*aux){
		while(tmp->valor != (*aux)->valor)
			tmp = tmp->prox;
		if (tmp != *aux){
			free(*aux);
			*aux = (*aux)->prox;
		}
		else aux = &((*aux)->prox);
	}
}


//26
LInt rotateL (LInt l){
	if (!l || !l->prox) return l;
	LInt r = l;
	int primeiro = l->valor;
	while (l){
		if (l->prox) l->valor = l->prox->valor;
		else l->valor = primeiro;
		l = l->prox;
	}
	return r;
}


//27
LInt parte (LInt l){
	LInt *auxR, *auxL, r;
	auxR = &r;
	auxL = &l;
	int par = 0;
	while (*auxL){
		if (par){
			*auxR = *auxL;
			auxR = &((*auxR)->prox);
			*auxL = (*auxL)->prox;
		}
		else auxL = &((*auxL)->prox);
		par = !par;
	}
	*auxR = NULL;
	return r;
}

/////////////////////////////////////////

typedef struct nodo{
	int valor;
	struct nodo *esq, *dir;
}*ABin;

ABin newABin (int r, ABin e, ABin d){
	ABin new = (ABin) malloc (sizeof (struct nodo));

	if (new!=NULL){
		new->valor = r;
		new->esq   = e;
		new->dir   = d;
	}
	return new;
}

//28
int max(int x, int y){
	if (x>y) return x;
	else return y;
}

int altura (ABin a){
	int c=0;
	if (!a) return 0;
	else c = 1 + max(altura(a->esq),altura(a->dir));
	return c;
}


//29
ABin cloneAB (ABin a){
	ABin nova = NULL;
	if (a) nova = newABin(a->valor, cloneAB(a->esq), cloneAB(a->dir));
	return nova;
}


//30
void mirror (ABin *a){
	if (*a){
		ABin tmp;
		tmp = (*a)->esq;
		(*a)->esq = (*a)->dir;
		(*a)->dir = tmp;
		mirror (&((*a)->esq));
		mirror (&((*a)->dir));
	}
}


//31
LInt* inorderAux (ABin a, LInt *l){
	if(a){
		l= inorderAux(a->esq,l);
		*l=newLInt(a->valor,NULL);
		l=&((*l)->prox);
		l= inorderAux(a->dir,l);
	}
	return l;
}

void inorder(ABin a, LInt *l){
	*l=NULL;
	inorderAux(a,l);
}

//32
LInt* preorderAux (ABin a, LInt *l){
	if(a){
		*l=newLInt(a->valor,NULL);
		l=&((*l)->prox);
		l= preorderAux(a->esq,l);
		l= preorderAux(a->dir,l);
	}
	return l;
}

void preorder(ABin a, LInt *l){
	*l=NULL;
	preorderAux(a,l);
}


//33
 LInt* posorderAux(ABin a, LInt *l){
     if (a){
         l = posorderAux(a->esq, l);
         l = posorderAux(a->dir, l);
         *l = newLInt (a->valor, *l);
         l = &((*l)->prox);
     }
     return l;
 }
 
 
 void posorder (ABin a, LInt * l) {
    *l = NULL;
    posorderAux(a,l);
}

//34
int min (int a, int b){
    if (a<b) return a;
    else return b;
}
 
int depth (ABin a, int x){
    int e, d;
    if(a){
        if(a->valor == x) return 1;
        e = 1+depth(a->esq,x); // se não existir valor e = 1-1 = 0;
        d = 1+depth(a->dir,x); // se não existir valor d = 1-1 = 0;
        if (e && d) return min(e, d);
        else if (e) return e;
             else if (d) return d;
    }
    return -1;
}

//35
int freeAB (ABin a){
	int c=0;
	if (a){
		c = freeAB(a->esq) + freeAB(a->dir) +1;
		free(a);
	}
	return c;
}


//36
int pruneAB (ABin *a, int l){
	int c = 0;
	if (a){
		if (l == 0){
			c = 1 + pruneAB(&((*a)->esq), 1) + pruneAB(&((*a)->dir), 1);
			*a = NULL;
			free (*a);
		}
		else c = pruneAB(&((*a)->esq), l-1) + pruneAB(&((*a)->dir), l-1);
	}
	return c;
}


//37
int iguaisAB (ABin a, ABin b){
	if (!a && b) return 0;
	if (a && !b) return 0;
	if (!a && !b) return 1;
	if (a && b){
	    if (a->valor == b->valor)
	        return min (iguaisAB (a->esq, b->esq), iguaisAB(a->dir, b->dir));
	   else return 0;
	}
}


//38
LInt nivelL (ABin a, int n){
	LInt l = NULL;
	if (a){
    	if (n == 1) l = newLInt(a->valor, l);
    	else {
    		l = nivelL(a->esq, n-1);
    		concatL (&l, nivelL(a->dir, n-1));
    	}
	}
	return l;
}


//39
int conta_nodos_nivel(ABin a, int n){
	if (a)
		if (n==1) return 1;
		else return conta_nodos_nivel(a->esq, n-1) + conta_nodos_nivel(a->dir, n-1);
}

int nivelVAux(ABin a, int n, int v[], int c){
	if (a){
		if (n==1){
			v[c] = a->valor;
		 	return c-1;
		 }
		else{
			c = nivelVAux(a->esq, n-1, v, c);
			c = nivelVAux(a->dir, n-1, v, c);
		}
	}
	return c;
}

int nivelV (ABin a, int n, int v[]){
	int c = conta_nodos_nivel(a,n);
	nivelVAux(a, n, v, c);
	return c;
}


//40
int dumpAbinAux(ABin a, int v[], int N, int c){
	if (a && N){
		c = dumpAbinAux(a->esq, v, N, c);
		N--; c++;
		v[c] = a->valor;
		c = dumpAbinAux(a->dir, v, N, c);
		N--; c++;
	}
	return c;
}

int dumpAbin (ABin a, int v[], int N){
	int c = dumpAbinAux(a,v,N,0);
	if (c<N) return c;
	else return N;
}


//41
int somasAcAux(ABin a){
    if (a){
        a->valor += somasAcAux(a->esq) + somasAcAux(a->dir);
        return a->valor;
    }
    return 0;
}

ABin somasAcA (ABin a){
    somasAcAux(a);
    return a;
}
//42
int contaFolhas (ABin a){
    if (a){
	    if (!a->esq && !a->dir) return 1;
	    else return contaFolhas(a->esq) + contaFolhas(a->dir);
    }
}


//43
ABin cloneMirror (ABin a){
	ABin b = NULL;
	if (a){
		b = (ABin) malloc (sizeof (struct nodo));
		b->valor = a->valor;
		b->esq = cloneMirror(a->dir);
		b->dir = cloneMirror(a->esq);
	}
	return b;
}


//44
int addOrd (ABin *a, int x){
	while (*a){
		if (x == (*a)->valor) return 1;
		else if (x < (*a)->valor) a = &((*a)->esq);
			 else a = &((*a)->dir);
	}
	*a = (ABin) malloc (sizeof (struct nodo));
	(*a)->valor = x;
	(*a)->esq = NULL;
	(*a)->dir = NULL;
	return 0;
}


//45
int lookupAB (ABin a, int x){
	while (a){
		if (x == a->valor) return 1;
		else if (x < a->valor) a = a->esq;
			else a = a->dir;
	}
	return 0;
}


//46
int depthOrd (ABin a, int x) {
    int conta;
    for (conta = 1; a; conta++){
        if (x == a->valor)
            return conta;
        else if (x < a->valor) a = a->esq;
             else a = a->dir;
    }
    return -1;
}


//47
int maiorAB (ABin a){
    if (a){
		while (a->dir)
			a = a->dir;
		return a->valor;
    }
    else return -1;
}

int menorAB (ABin a){
    if (a){
		while (a->esq)
			a = a->esq;
		return a->valor;
    }
    else return -1;
}

//48
void removeMaiorA (ABin *a){
    while ((*a)->dir)
		a = &((*a)->dir);
	*a = (*a)->esq;
}


//49
int conta_nodos(ABin a){
	if (a) return 1+conta_nodos(a->esq)+conta_nodos(a->dir);
}

int quantosMaiores (ABin a, int x){
	if (a){
		if (x == a->valor) return conta_nodos(a->dir);
		else if (x < a->valor)
			 return 1 + quantosMaiores(a->esq, x) + conta_nodos(a->dir);
			 else return quantosMaiores(a->dir, x); 
	}
	return 0;
}


//50
void listToBTree (LInt l, ABin *a){
	while(l!=NULL){
	    addOrd(a,l->valor);
	    l=l->prox;
	}
}


//51
int eProcura_aux(ABin a, int min, int max){
	if (!a) return 1;
	if (a->valor < min || a->valor > max)
		return 0;
	return eProcura_aux(a->esq, min, max) && eProcura_aux(a->dir, min, max);
}

int eProcura(ABin a){
	if (!a) return 1;
	else return eProcura_aux(a, menorAB(a), maiorAB(a));
}



int main(){
}