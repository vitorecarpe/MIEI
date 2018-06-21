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
int length(LInt l){
	int count;
	for(count=0; l; count++) l=l->prox;
	return count;
}

//4
LInt reverseL (LInt l){
    if (l == NULL || l->prox == NULL) return l;
    LInt ant = l;
    LInt nex;
    l = l->prox;
    nex = l->prox;
    ant->prox=NULL;
    while(nex){
        l->prox=ant;
        ant=l;
        l=nex;
        nex=nex->prox;
    }
    l->prox=ant;
    return l;
}

//5
void insertOrd (LInt *l, int x){
    while(*l && (*l)->valor < x){
        l=(&(*l)->prox);
    }
    *l = newLInt(x,*l);
}

//6
int removeOneOrd (LInt *l, int x){
    while(*l && (*l)->valor!=x){
        l = &(*l)->prox;
    }
    if((*l) == NULL) return 1;
    else (*l)=(*l)->prox;
    return 0;
}

//7
void merge (LInt *r, LInt l1, LInt l2){
    while(l1 && l2){
        if((l1)->valor<(l2)->valor){
            *r=newLInt((l1)->valor,*r);
            r = &((*r)->prox);
            l1=l1->prox;
        }
        else{
            *r=newLInt((l2)->valor,*r);
            r = &((*r)->prox);
            l2=l2->prox;
        }
    }
    while(l1){
        *r=newLInt((l1)->valor,*r);
		r = &((*r)->prox);
		l1=l1->prox;
    }
    while(l2){
        *r=newLInt((l2)->valor,*r);
        r = &((*r)->prox);
        l2=l2->prox;
    }
}

//8
void splitMS (LInt l, int x, LInt *mx, LInt *Mx){
    while(l){
        if(l->valor < x){
            *mx = newLInt(l->valor,*mx);
		    mx = &((*mx)->prox);
        }
		else{
		    *Mx = newLInt(l->valor,*Mx);
		    Mx = &((*Mx)->prox);
		}
		l=l->prox;
    }
}

//10
int removeAll (LInt *l, int x){
    int count=0;
    while(*l){
        if((*l)->valor==x){
            (*l)=(*l)->prox;
            count++;
        }
        else{l=&((*l)->prox);}
    }
    return count;
}

//11
int removeDups (LInt *l){
    LInt *aux = (*l)->prox;
    int count=0;
    while(*l){
        aux=&((*l)->prox);
        while(*aux){
            if((*l)->valor == (*aux)->valor){
                (*aux)=(*aux)->prox;
                count++;
            }
            else aux=&((*aux)->prox);
        }
        l=&((*l)->prox);
    }
    return count;
}

//12
int removeMaiorL (LInt *l){
    int maior = (*l)->valor;
    int etc=0;
    LInt *orig = l;
    while(*orig){
        if((*orig)->valor > maior) maior=(*orig)->valor;
        orig=&((*orig)->prox);
    }
    while(*l){
        if((*l)->valor == maior && etc==0) {(*l)=(*l)->prox; etc=1;}
        else l=&((*l)->prox);
    }
    return maior;
}

//13
void init (LInt *l){
    while((*l)->prox){
        l=&((*l)->prox);
    }
    (*l) = NULL;
}

//14
void appendL (LInt *l, int x){
    while(*l){
        l=&((*l)->prox);
    }
    (*l)=newLInt(x,(*l));
}

//15
void concatL (LInt *a, LInt b){
    while(*a){
        a=&((*a)->prox);
    }
    (*a)=b;
}

//17
LInt cloneRev (LInt l){
    if (l == NULL) return NULL;
    LInt new = newLInt(l->valor,NULL);
    l = l->prox;
    while (l){
        new = newLInt(l->valor,new);
        l=l->prox;
    }
    return new;
}

//18
int maximo (LInt l){
    int max=l->valor;
    while(l){
        if(l->valor > max) max = l->valor;
        l = l->prox;
    }
    return max;
}

//19
int take (int n, LInt *l){
    int i=0;
    while((*l) && i<n){
        l=&((*l)->prox);
        i++;
    }
    while(*l){
        free(*l);
        (*l)=(*l)->prox;
    }
    return i;
}

//20
int drop (int n, LInt *l){
    int i=0;
    while((*l) && i<n){
        (*l)=(*l)->prox;
        i++;
    }
    return i;
}

//21
LInt NForward (LInt l, int N){
    int i=0;
    while(l && i<N){
        l=l->prox;
        i++;
    }
    return l;
}

//22
int listToArray (LInt l, int v[], int N){
    int i=0;
    while(l && i<N){
        v[i]=l->valor;
        l=l->prox;
        i++;
    }
    return i;
}

//23
LInt arrayToList (int v[], int N){
    LInt new = NULL;
    N=N-1;
    for(; N>=0; N--)
        new = newLInt(v[N],new);
    return new;
}

//24
LInt somasAcL (LInt l) {
    if(l==NULL) return NULL;
    LInt new = NULL;
    int soma = 0;
    while(l){
        soma+=l->valor;
        new = newLInt(soma,new);
        l=l->prox;
    }
    LInt nova = cloneRev(new);
    return nova;
}

//25
void remreps (LInt l){
    if(l) {
        LInt aux = l->prox;
        while(aux){
            if(l->valor==aux->valor){
                l->prox=aux->prox;
                aux=aux->prox;
                
            }
            else{
                l->prox=aux;
                l=l->prox;
                aux=aux->prox;
            }
        }
        
    }
}

//26
LInt rotateL (LInt l){
    LInt init = l;
    if(!l || !(l->prox)) return l;
    l=l->prox;
    while(l->prox){
        l=l->prox;
    }
    l->prox=init;
    LInt ret=init->prox;
    init->prox=NULL;
    return ret;
}

//28
int max(int x, int y){
	if (x>y) return x;
	else return y;
}

int altura (ABin a){
    int h=0;
    if(a){
        h=1+max(altura(a->esq), altura(a->dir));
    }
    return h;
}

//31
LInt* inorderAux (ABin a, LInt *l) {
    if(a){
        l=inorderAux(a->esq, l);
        *l =newLInt(a->valor, NULL);
        l=&((*l)->prox);
        l=inorderAux(a->dir, l);
    }
    return l;
}

void inorder(ABin a, LInt *l){
    *l=NULL;
    inorderAux(a,l);
}

//32
LInt* preorderAux (ABin a, LInt *l) {
    if(a){
        *l =newLInt(a->valor, NULL); //INSERIR
        l=&((*l)->prox);
        l=preorderAux(a->esq, l);	 //ESQUERDA
        l=preorderAux(a->dir, l);	 //DIREITA
    }
    return l;
}

void preorder(ABin a, LInt *l){
    *l=NULL;
    preorderAux(a,l);
}

//33
LInt* posorderAux (ABin a, LInt *l) {
    if(a){
        l=posorderAux(a->esq, l);
        l=posorderAux(a->dir, l);
        *l =newLInt(a->valor, NULL);
        l=&((*l)->prox);

    }
    return l;
}

void posorder(ABin a, LInt *l){
    *l=NULL;
    posorderAux(a,l);
}

//34
int min(int a, int b){
    if(a<b) return a;
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
int freeAB (ABin a) {
    int r=0;
    if(a){
        free (a);
        r+=1+freeAB(a->esq)+freeAB(a->dir);
    }
    return r;
}

//40
int dumpAbin (ABin a, int v[], int N) {
    int i;
    if (!a || N==0) return 0;
    i = dumpAbin (a->esq,v,N);
    if (i == N-1) {v[i] = a->valor; return N;}
    if (i < N-1) {v[i] = a->valor; i+=1; return i+dumpAbin(a->dir,v+i,N-i);}
    if (i == N) return i;
}

//42
int contaFolhas (ABin a){
    if (a){
	    if (!a->esq && !a->dir) return 1;
	    else return contaFolhas(a->esq) + contaFolhas(a->dir);
    }
}

//43
ABin cloneMirror (ABin a) {
    ABin b=NULL;
    if(a){
        b = malloc(sizeof(struct nodo));
        b->valor=a->valor;
        b->esq=cloneMirror(a->dir);
        b->dir=cloneMirror(a->esq);
    }
    return b;
}

//44
int addOrd (ABin *a, int x) {
    while(*a){
        if(x > (*a)->valor) a=&((*a)->dir);
        else if(x < (*a)->valor) a=&((*a)->esq);
        else if(x == (*a)->valor) return 1;
    }
    (*a)=malloc(sizeof(struct nodo));
    (*a)->valor=x;
    (*a)->esq=NULL;
    (*a)->dir=NULL;
    return 0;
}

//45
int lookupAB (ABin a, int x) {
    while(a){
        if(x > a->valor) a=a->dir;
        else if(x < a->valor) a=a->esq;
        else if(x == a->valor) return 1;
    }
    return 0;
}

//46
int depthOrd (ABin a, int x) {
    int r=1;
    while(a){
        if(x > a->valor) {a=a->dir;r++;}
        else if(x < a->valor) {a=a->esq;r++;}
        else if(x == a->valor) return r;
    }
    return -1;
}

//47
int maiorAB (ABin a) {
    while(a->dir){
        a=a->dir;
    }
    return a->valor;
}

//48
void removeMaiorA (ABin *a) {
    if((*a)->dir){
        removeMaiorA( &((*a)->dir) );
    }
    else (*a)=(*a)->esq;
}

//49
int quantosMaiores (ABin a, int x) {
    int r=0;
    if(a){
        if(a->valor > x) r++;
        r+=quantosMaiores(a->esq,x);
        r+=quantosMaiores(a->dir,x);
    }
    return r;
}