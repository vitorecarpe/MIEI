//RECURSO 2016

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Parte A

//1.
char *strcpy(char *dest, char source[]){
	int i;
	for(i=0; source[i]; i++){
		dest[i]=source[i];
	}
	dest[i]='\0';
	return dest;
}

//2
void strnoV (char t[]){
    int i, j;
    for(i=0,j=0; t[i]; i++){
        if(t[i]!='a' && t[i]!='e' && t[i]!='i' && t[i]!='o' && t[i]!='u' && t[i]!='A' && t[i]!='E' && t[i]!='I' && t[i]!='O' && t[i]!='U'){
            t[j]=t[i];
            j++;
        }
    }
    t[j]='\0';
}

//3
int dumpAbin (ABin a, int v[], int N) {
    int i;
    if (!a || N==0) return 0;
    i = dumpAbin (a->esq,v,N);
    if (i == N-1) {
        v[i] = a->valor;
        return N;
    }
    if (i < N-1) {
        v[i] = a->valor;
        i+=1;
        return i+dumpAbin(a->dir,v+i,N-i);
    }
    if (i == N) return i;
}

//4
int lookupAB(ABin a, int x){
	while(a){
		if(x > a->valor) a=a->dir;
		else if(x < a->valor) a=a->esq;
		else return 1;
	}
	return 0;
}	

//Parte B

typedef struct listaP{	
	char *pal;
	int cont;
	struct listaP *prox;
} Nodo, *Hist;

//1.
int mystrcmp(char *p1, char *p2){
	int i=0;
	while(p1[i]==p2[i] && p2[i] && p1[i]){
		i++;
	}
	return(p1[i]-p2[i]);
}

int inc(Hist *h, char *pal){
	while(mystrcmp((*h)->pal,pal) < 0){
		h=&((*h)->prox);
	}
	if(mystrcmp((*h)->pal,pal) == 0){
		((*h)->cont)++;
		return ((*h)->cont);
	}
	else{
		Hist aux=(*h);
		(*h)=malloc(sizeof(struct listaP));
		(*h)->pal=(pal);
		(*h)->cont=1;
		(*h)->prox=aux;
		return 1;
	}
}

//2.
char *remMaisFreq(Hist *h, int *count){
	Hist *aux=h;
	while(*h){
		if((*h)->cont>(*aux)->cont) (*aux)=(*h);
		h=&((*h)->prox);
	}
	char* freq=(*aux)->pal;
	int con=(*aux)->cont;
	(*aux)=(*aux)->prox;
	*count=con;
	return freq;
}


int main(){
	Hist h = malloc(sizeof(struct listaP));
	h->pal = "a";
	h->cont = 3;
	Hist i = malloc(sizeof(struct listaP));
	h->prox = i;
	i->pal="b";
	i->cont=5;
	Hist j = malloc(sizeof(struct listaP));
	i->prox = j;
	j->pal="f";
	j->cont=1;
	j->prox=NULL;
	//int k;
	//k=inc(&h, "c");
	int count;
	Hist hd=h;
	char* r = remMaisFreq(&hd, &count);
	printf("%d \n", count);
	printf("%s \n\n", r);
	while(hd){
		
		printf("%s ",h->pal);
		printf("%d \n", h->cont);
		h=h->prox;
	}
	return 0;
}
