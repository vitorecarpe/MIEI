//TESTE 2016

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Parte A

//1.
char *strcat (char s1[], char s2[]){
	int i;
	for(i=0; s1[i]; i++);
	int j;
	for(j=0; s2[j]; j++){
		s1[i]=s2[j];
		i++;
	}
	s1[i]='\0';
	return s1;
}

//2.
int remRep (char texto []) {
    int i, j=0;
    for(i=0; texto[i]; i++){
        if(texto[i]!=texto[i+1]) {texto[j]=texto[i]; j++;}
    }
    texto[j]='\0';
    return j;
}

//3.

//4.
int addOrd (ABin *a, int x) {
    while(*a){
        if(x>(*a)->valor) a=&((*a)->dir);
        else if(x<(*a)->valor) a=&((*a)->esq);
        else if(x==(*a)->valor) return 1;
    }
    (*a)=malloc(sizeof(struct nodo));
    (*a)->valor=x;
    (*a)->esq=NULL;
    (*a)->dir=NULL;
    return 0;
}