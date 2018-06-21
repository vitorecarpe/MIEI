/*Funções n testadas*/
daniel
ex 1-
int length (LInt l){
	int cont = 0;
	while (l != NULL){cont++;l = l->prox;}
	return cont;
}

void freeL (LInt l){
	while (l!= NULL){
		LInt ant = l;
		l = l->prox;
		free(ant);
	}
}

void imprimeL (LInt l){
	while (l != NULL){
		printf("%d\n",l->valor);
		l = l->prox;
	}
	return;
}

LInt cloneL (LInt l){
	if (l == NULL) return NULL;
	LInt new = newLInt(l->valor,NULL),aux = new;
	l = l->prox
	while (l){
		aux->prox = newLInt(l->valor,NULL);
		aux = aux->prox;
		l = l->prox;
	}
	return new;
}

LInt arrayToList (int v [], int N){
	LInt new = NULL;
	N--;
	while (N >= 0){
		new = newLInt(v[N],new);
		N--;
	}
	return new;
}

/*acho que os testes estão errados*/
LInt rotateL (LInt l){
    LInt aux = l;
    if (l == NULL || l->prox == NULL) return l;
    while (aux->prox) aux = aux->prox;
    aux->prox = l;
    l = l->prox;
    aux = aux->prox;
    aux->prox = NULL;
    return l;
}

LInt parte (LInt l){
	int i = 0;
	if (l == NULL || l->prox == NULL) return NULL;
	LInt new,aux2,aux1 = l;
	new = aux2 = aux1->prox;
	while (aux1->prox != NULL && aux2->prox != NULL){
		aux1->prox = aux2->prox;
		aux1 = aux1->prox;
		aux2->prox = aux1->prox;
		aux2 = aux2->prox;
	}
	return new;
}

int maximo (int x, int y);
	if (x > y) return x;
	else return y;
}

int altura (ABin a){
	if (a == NULL) return 0;
	else return 1 + maximo (altura(a->esq), altura(a->dir));
}

/** ou*/
int altura (ABin a){
	int x,y;
	 if (a == NULL) return 0;
	 x = altura(a->esq);
	 y = altura (a->dir);
	 if (x > y) return 1+x;
	 else return 1+y;
}
/* n está a funcionar*/
 void posorder (ABin a, LInt * l) {
    LInt y;LInt *aux = l;
    if (a == NULL) {(*l) = NULL;return;}
    posorder(a->esq,l);
    posorder(a->dir,&y);
    concat(l,y);
    while ((*aux)->prox) aux = &((*aux)->prox);
    (*aux)->prox = newLInt(a->valor,NULL);
}

/*end of funções n testadas*/
int altura (ABin a){
    int r;
    if (a == NULL) r = 0;
    else if (altura(a->esq) >= altura(a->dir)) r = 1 + altura(a->esq);
    else r = 1 + altura(a->dir);
	return r;
}

void insertOrd (LInt l, int x){
    LInt aux = l,ant = NULL;
    while (aux->valor < x) {ant = aux;aux = aux->prox;}
    if (ant != NULL) ant->prox = newLInt(x,aux);
}

LInt reverseL (LInt l){
    if (l == NULL || l->prox == NULL) return l;
    LInt ant = l,seg;l = l->prox;seg = l->prox;
    ant->prox = NULL;
    while (seg){
        l ->prox = ant;
        ant = l;
        l = seg;
        seg = seg->prox;
    }
    l->prox = ant;
    return l;
}
/*ou*/
LInt reverseL (LInt l){
    LInt ant = NULL,seg = l;
    if (l == NULL) return NULL;
    while (l) {seg = l->prox;l->prox = ant;ant = l;l = seg;}
    return ant;
}

void insertOrd (LInt *l, int x){
    LInt new = newLInt(x,NULL);
    while ((*l) && (*l)->valor < x) l = &((*l)->prox);
    if (new != NULL) new->prox = (*l);
    (*l) = new;
}

int removeOneOrd (LInt *l, int x){
    while ((*l) && (*l)->valor < x) l = &((*l)->prox);
    if ((*l) == NULL || (*l)->valor != x) return 1;
    (*l) = (*l)->prox;
    return 0;
}

void merge (LInt *r, LInt l1, LInt l2){
    while(l1 && l2){
        if (l1->valor < l2->valor) {
            (*r) = newLInt(l1->valor,&((*r)->prox));
            l1 = l1->prox;
             }
        else {
            (*r) = newLInt(l2->valor,&((*r)->prox));
            l2 = l2->prox; }
    }
    if (l1 == NULL) (*r) = l2; else (*r) = l1;
}

void splitMS (LInt l, int x, LInt *mx, LInt *Mx){
    while (l){
        if (l->valor < x) {(*mx) = l;mx = &((*mx)->prox);}
        else {(*Mx) = l; Mx = &((*Mx)->prox);}
        l = l->prox;
    }
    *Mx = NULL;*mx = NULL;
}

int removeAll (LInt *l, int x){
    int cont = 0;
    while (*l){
        if ((*l)->valor == x) {(*l) = (*l)->prox;cont++;}
        else l = &((*l)->prox);
    }
    return cont;
}

/* versão da removeDups se apenas fosse para eliminar as repetiçoes sucessivas*/
int removeDups (LInt *l){
    int cont = 0;
    while ((*l) && (*l)->prox){
        if ((*l)->valor == (*l)->prox->valor) {(*l) = (*l)->prox;cont++;}
        else l = &((*l)->prox);
    }
    return cont;
}

/*verdadeira removeDups*/
int removeDups (LInt *l){
    int cont = 0;LInt *aux;
    while (*l){
        aux = &((*l)->prox);
        while (*aux){
            if ((*l)->valor == (*aux)->valor) {(*aux) = (*aux)->prox;cont++;}
            else aux = &((*aux)->prox);}
        l = &((*l)->prox);
    }
    return cont;
}

int removeMaiorL (LInt *l){
    int m;
    LInt *maior = l;
    while (*l) {
        if ((*l)->valor > (*maior)->valor) maior = l;
        l = &((*l)->prox);
    }
    m = (*maior)->valor;
    *maior = (*maior)->prox;
    return m;
}

int removeMaiorL (LInt *l){
    int m;
    LInt *aux = l;
    while (*l){
        if ((*l)->valor > (*aux)->valor) {aux = l; l = &((*l)->prox);}
        else l = &((*l)->prox);
    }
    if (*aux) {m = (*aux)->valor; (*aux) = (*aux)->prox;return m;}
}

void init (LInt *l){
    while (*l && (*l)->prox) l = &((*l)->prox);
    if (*l != NULL) {free(*l);(*l) = NULL;}
}

void appendL (LInt *l, int x){
    LInt new = newLInt(x,NULL);
    while (*l && (*l)->prox) l = &((*l)->prox);
    if (*l) (*l)->prox = new;
    else (*l) = new;
}

void posorder (ABin a, LInt * l) {
     if (!a) {*l=NULL; return;}
    posorder (a->esq,l);    
    LInt y;
    posorder (a->dir,&y);       
    concatL (l,y); 
    y= newLInt (a->valor,NULL);
    concatL (l,y);
}

void concatL (LInt *a, LInt b){
    if (*a){
        while ((*a)->prox != NULL) a = &((*a)->prox);
        (*a)->prox = b;
    }
    else (*a) = b;
}

LInt cloneRev (LInt l){
    if (l == NULL) return NULL;
    LInt new = newLInt(l->valor,NULL);
    l = l->prox;
    while (l!=NULL){
        new = newLInt(l->valor,new);
        l=l->prox;
    }
    free(l);
    return new;
}

int maximo (LInt l){
    LInt aux = l;
    if (aux == NULL) return -1;
    int max = aux->valor;aux = aux->prox;
    while (aux != NULL){
        if (aux->valor > max) max = aux -> valor;
        aux = aux->prox;
    }
    free(aux);
    return max;
}

/** se se puder destruir a lista*/
int maximo (LInt l){
    if (l == NULL) return -1;
    int max = l->valor;l = l->prox;
    while (l != NULL){
        if (l->valor > max) max = l -> valor;
        l = l->prox;
    }
    return max;
}

int take (int n, LInt *l){
    int i = 0;
    while (*l != NULL && n > 1){
        n--;l = &((*l)->prox);i++;
    }
    if ((*l) == NULL) return i;
    else {free((*l)->prox);(*l)->prox = NULL;return i+1;}
}

/*já está a dar free??*/
int drop (int n, LInt *l){
    int count = 0;
    while ((*l) != NULL && n>0){
        (*l) = (*l)->prox; count++; n--; 
    }
    return count;
}

LInt NForward (LInt l, int N){
    while (N > 0) {l = l->prox;N--;}
    return l;
}

int listToArray (LInt l, int v[], int N){
    int cont = 0;
    while (l != NULL && N > 0){
        v[cont] = l->valor;
        l = l->prox;
        cont++;
        N--;
    }
    return cont;
}

LInt somasAcL (LInt l) {
    if (l == NULL) return NULL;
    LInt auxl = l,new,auxn;
    new = auxn = newLInt(l->valor,NULL);
    auxl = auxl->prox;
    while (auxl != NULL){
        auxn->prox = newLInt(auxl->valor + auxn->valor,NULL);
        auxl = auxl->prox;
        auxn = auxn->prox;
    }
    return new;
}

void remreps (LInt l){
    LInt aux1,aux2;
    if (l == NULL) return;
    for(aux1 = l;aux1 != NULL;aux1 = aux1->prox)
        for(aux2 = aux1;aux2 != NULL && aux2->prox != NULL;){
            if (aux2->prox->valor == aux1->valor) {free(aux2->prox);aux2->prox = aux2->prox->prox;}
            else aux2 = aux2->prox;
        }
}

/*ou com uma função anterior*/ 
void remreps (LInt l){
    LInt aux1,aux2,ant;
    if (l == NULL) return;
    for(aux1 = l;aux1 != NULL;aux1 = aux1->prox)
       for(ant = aux1,aux2 = aux1->prox;aux2 != NULL;){
           if (aux2->valor == aux1->valor) {ant->prox = aux2->prox;free(aux2);aux2 = ant->prox;}
           else {ant = aux2;aux2 = aux2->prox;}
       } 
}

LInt rotateL (LInt l){
    LInt *aux = &l;
    if (l == NULL) return NULL;
    while (*aux) aux = &((*aux)->prox);
    *aux = l;
    l = l->prox;
    (*aux)->prox = NULL;
    return l;
}
/*ou*/
LInt rotateL (LInt l){
    LInt aux,ant; aux = ant = l;
    if (l == NULL) return NULL;
    aux = aux->prox;
    while (aux) {ant = aux;aux = aux->prox;}
    ant->prox = l;
    ant = l;
    l = l->prox;
    ant->prox = NULL;
    return l;
}

LInt parte (LInt l){
	if (l == NULL || l->prox == NULL) return NULL;
	LInt new,aux2,aux1 = l;
	new = aux2 = aux1->prox;
	while (aux1->prox != NULL && aux2->prox != NULL){
		aux1->prox = aux2->prox;
		aux1 = aux1->prox;
		aux2->prox = aux1->prox;
		aux2 = aux2->prox;
	}
	if (aux1) aux1->prox = NULL;
	if (aux2) aux2->prox = NULL;
	return new;
}

int length (LInt l){
    int r = 0;
    while (l) {r++;l = l->prox;}
    return r;
}

LInt parteAmeio (LInt *l){
	int t = length(*l);
	t = t/2;
	if ((*l) == NULL) return NULL;
	LInt save = (*l),aux = (*l);
	while (t > 1){
		(*l) = (*l)->prox;
		aux = aux->prox;
		t--;
	}
	(*l) = (*l)->prox;
	aux->prox = NULL;
	return save;
}

LInt parteAmeio (LInt *l){
    LInt new = *l;LInt *aux = l; 
    int r = lengthL(*l);r /= 2;
    if (r == 0) return NULL;
    while (r > 0){
       aux = &((*aux)->prox);
       *l = (*l)->prox;
       r--;
    }
    *aux = NULL;
    return new;
}

ABin cloneAB (ABin a) {
    ABin new;
    if (a == NULL) return NULL;
    new = (ABin) malloc (sizeof (struct nodo));
    new->valor = a->valor;
    new->esq = cloneAB(a->esq);
    new->dir = cloneAB(a->dir);
    return new;
}

void mirror (ABin *a) {
    ABin aux;
    if (*a == NULL) return;
    mirror (&((*a)->esq));
    mirror (&((*a)->dir));
    aux = ((*a)->dir);
    (*a)->dir = (*a)->esq;
    (*a)->esq = aux;
}

void concat (LInt *a, LInt b){
    if (*a == NULL) {*a = b;return;}
    while ((*a)->prox) a = &((*a)->prox);
    (*a)->prox = b;
}

void inorder (ABin a, LInt *l){
	LInt y;
	if (a == NULL) {(*l) = NULL;return;}
	inorder(a->esq,l);
	inorder(a->dir,&y);
	y = newLInt(a->valor,y);
	concat(l,y);
	return;
}

void preorder (ABin a, LInt * l) {
    LInt y;
    if (a == NULL) {(*l) = NULL;return;}
    preorder(a->esq,l);
    (*l) = newLInt(a->valor,(*l));
    preorder(a->dir,&y);
    concat(l,y);
}

int minimumdepth (int a, int b){
	if (a == -1) return b;
	if (b == -1) return a;
	if (a <= b) return a;
	else return b;
}

int depth (ABin a, int x){
	if (a == NULL) return -1;
	if (a->valor == x) return 1;
	int m = minimumdepth(depth(a->dir),depth(a->esq));
	if (m == -1) return -1;
	else return 1 + m;
}

int iguaisAB (ABin a, ABin b) {
    if (a == NULL && b == NULL) return 1;
    if (a && b && a->valor == b->valor) return iguaisAB(a->esq,b->esq) && iguaisAB(a->dir,b->dir);
    return 0;
}

LInt nivelL (ABin a, int n) {
    if (a == NULL) return NULL;
    if (n == 1) return newLInt(a->valor,NULL);
    LInt y = nivelL(a->esq,n-1);
    LInt aux = nivelL(a->dir,n-1);
    concat(&y,aux);
    return y;
}

int nivelV (ABin a, int n, int v[]) {
    int e,d;
    if (a == NULL) return 0;
    if (n == 1) {v[0] = a->valor;return 1;} 
    e = nivelV(a->esq,n-1,v);
    d = nivelV(a->dir,n-1,v+1);
    return e+d;
}

int freeAB (ABin a) {
    int e,d;
    if (a == NULL) return 0;
    e = freeAB(a->esq);
    d = freeAB(a->dir);
    free(a);
    return 1+e+d;
}

int pruneAB (ABin *a, int l) {
     int e,d,r;
     if (*a == NULL) return 0;
     if (l == 0) {r = freeAB(*a);(*a) = NULL;return r;}
     e = pruneAB(&((*a)->esq),l-1);
     d = pruneAB(&((*a)->dir),l-1);
     return e+d;
 }

int dumpAbin (ABin a, int v[], int N) {
    int i;
    if (a == NULL) return 0;
    if (N == 0) return 0;
    i = dumpAbin (a->esq,v,N);
    if (i == N-1) {v[i] = a->valor;return N;}
    if (i < N-1) {v[i] = a->valor;i+=1;return i+dumpAbin(a->dir,v+i,N-i);}
    if (i == N) return i;
}

int intcabeca (ABin a){
    if (a == NULL) return 0;
    else return a->valor;
}

ABin somasAcA (ABin a) {
    ABin new;
    if (a == NULL) return NULL;
    new = (ABin) malloc (sizeof(struct nodo));
    new->esq = somasAcA(a->esq);
    new->dir = somasAcA(a->dir);
    new->valor = a->valor + intcabeca(new->esq) + intcabeca(new->dir);
    return new;
}

int contaFolhas (ABin a) {
    int e,d;
    if (a == NULL) return 0;
    if (a->esq == NULL && a->dir == NULL) return 1;
    e = contaFolhas (a->esq);
    d = contaFolhas (a->dir);
    return e+d;
}

ABin cloneMirror (ABin a) {
    ABin new;
    if (a == NULL) return NULL;
    new = (ABin) malloc (sizeof (struct nodo));
    new->valor = a->valor;
    new->esq = cloneMirror(a->dir);
    new->dir = cloneMirror(a->esq);
    return new;
}
//recursiva
int addOrd (ABin *a, int x) {
    ABin new;
    new = (ABin) malloc (sizeof(struct nodo));
    new->valor = x;
    new->esq = NULL;
    new->dir = NULL;
    if (*a == NULL) {*a = new;return 0;}
    if ((*a)->valor == x) return 1;
    if ((*a)->valor > x) return addOrd(&((*a)->esq),x);
    if ((*a)->valor < x) return addOrd(&((*a)->dir),x);
}

//não recursiva
int addOrd (ABin *a, int x) {
    ABin new;
    new = (ABin) malloc (sizeof(struct nodo));
    new->valor = x;
    new->esq = NULL;
    new->dir = NULL;
    while (*a){
        if ((*a)->valor == x) return 1;
        if ((*a)->valor > x) a = &((*a)->esq);
        else if ((*a)->valor < x) a = &((*a)->dir);
    }
    (*a) = new;return 0;
}

int lookupAB (ABin a, int x) {
    while(a){
        if (x == a->valor) return 1;
        if (x < a->valor) a = a->esq;
        else if (x > a->valor) a = a->dir;
    }
    return 0;
}

int depthOrd (ABin a, int x) {
    int nivel = 1;
    while(a){
        if (x == a->valor) return nivel;
        if (x < a->valor) a = a->esq;
        else if (x > a->valor) a = a->dir;
        nivel++;
    }
    return -1;
}

int maiorAB (ABin a) {
    if (a == NULL) return -1;
    while (a->dir) a = a->dir;
    return a->valor;
}

void removeMaiorA (ABin *a) {
    if (!(*a)) return;
    while ((*a)->dir) a = &((*a)->dir);
    *a = (*a)->esq;
    return;
}

int quantosMaiores (ABin a, int x) {
    int cont = 0;
    if (a == NULL) return 0;
    if (a->valor < x) return quantosMaiores(a->dir,x);
    if (a->valor > x) return 1+quantosMaiores(a->esq,x)+quantosMaiores(a->dir,x);
    if (a->valor == x) return quantosMaiores (a->dir,x);
}
//ou//
int lengthAB (ABin a){
    int e,d;
    if (a == NULL) return 0;
    else return 1+lengthAB(a->esq)+lengthAB(a->dir);
}

int quantosMaiores (ABin a, int x) {
    int cont = 0;
    if (a == NULL) return 0;
    if (a->valor < x) return quantosMaiores(a->dir,x);
    if (a->valor > x) return 1+quantosMaiores(a->esq,x)+lengthAB(a->dir);
    if (a->valor == x) return lengthAB(a->dir);
}

void listToBTree (LInt l, ABin *a) {
    ABin new;
    if (l == NULL) *a = NULL;
    while(l){
        new = (ABin) malloc (sizeof (struct nodo));
        new->valor = l->valor;
        new->esq = NULL;
        new->dir = NULL;
        *a = new;
        a = &((*a)->dir);
        l = l->prox;
    }
}

int menorqAB (int n, ABin ab){
    if (ab == NULL) return 1;
    if (ab->valor >= n)
        menorqAB (n, ab->esq) && menorqAB (n, ab->dir);
    else return 0;
}

int maiorqAB (int n, ABin ab){
    if (ab == NULL) return 1;
    if (ab->valor <= n)
        maiorqAB (n,ab->esq) && maiorqAB (n,ab->dir);
    else return 0;
}

int deProcura (ABin a) {
   if (a == NULL) return 1;
   int e = deProcura (a->esq);
   int d = deProcura (a->dir);
   if (maiorqAB (a->valor,a->esq) && menorqAB (a->valor,a->dir)) return e && d;
   return 0;
}
