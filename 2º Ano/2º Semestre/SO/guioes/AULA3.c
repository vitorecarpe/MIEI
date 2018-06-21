// SO - AULA 3 - GUIÃO 2
// 8 Mar 2018 
// __% Funcional!

/*
COMO COMPILAR EM C
$ gcc -Wall AULA3.c -o aula
$ ./aula
*/

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

//Ex. 1
/*
int main(){
	printf("pid: %d\nppid: %d\n",getpid(),getppid());
	return 0;
}
*/

//Ex. 2

int main(){
	pid_t pid;
	pid = fork();
	if(pid==0){ //sou o filho
		printf("filho: pid = %d, ppid = %d\n", getpid(), getppid());
		return 0;
	}
	if(pid==-1){ //sou o pai, mas nao criei o filho
		perror("pai: fork");
		return 1;
	}
	printf("pid: %d\nppid: %d\n",getpid(),getppid());
	return 0;
}



/* --- RESOLUÇÃO DO ANO PASSADO ---

No processo pai a variável x vale 1, no processo filho a variável
x vale 0.
A seguir a um fork há sempre um if. Exemplo:

		| x=fork();
		| if(x==0){
		| 	  ...EXECUTADO PELO FILHO (porque x=0)...
		| }
		| else {
		|	  ...EXECUTADO PELO PAI (porque x=1)...
		| }

Filho quando nasce, nasce exatamente igual ao pai, mas a partir
daí é independente do mesmo, tal como uma fotocópia é igual no
momento da cópia, mas a partir daí alterações na cópia(filho)
não afetam a original(pai).


int main(){
	int i,j,ziliao=500000,cont=0;
	for(i=0;i<5;i++){
		x=fork();
		if(x==0){
			for(j=0;j<ziliao;j++){
				if(m[i][j]==k) cont++;
			}
			printf("%d %d\n",i,cont);
			exit(0);
		}
	}
	return 0;
}


Processo é um espaço de memória - espaço de endereçamento do
processo. Este espaço de memória é divido em 4 partes. 1 é o
código. 1 é memória estática onde são guardadas as variáveis
locais. Um grande espaço é da memória dinâmica (malloc).
O outro pequeno bocado é a stack que guarda os endereços de
retorno. Quando é feita uma cópia para um filho tudo é
copiado, sendo a única diferença na stack, onde na variavel
x está 0 no filho e 1 no pai.

Nenhum pai termina o processo antes dos filhos, acontece,
mas é "má cultura".
*/

/*EXERCICIO 1

int main(){
	printf("pid: %d\nppid: %d\n",getpid(),getppid());
}

EXECUÇÃO DO PROGRAMA:

vitor@tardigrade: gcc AULA3.c
vitor@tardigrade: ./a.out
pid: 3112
ppid: 2919
vitor@tardigrade: ps
 PID TTY              TIME CMD
2919 pts/2        00:00:00 bash
3113 pts/2        00:00:00 ps
*/

/*EXERCICIO 2

int main(){
	int x;

	x=fork();
	if(x==0){
		printf("filho1: %d\npai1: %d\n", getpid(),getppid());
		exit(0);
	}

	printf("pai2: %d\npaidopai2: %d\nfilho2: %d\n", getpid(), getppid(),x);
	return 0;
}

EXECUÇÃO DO PROGRAMA:

vitor@tardigrade: gcc AULA3.c
vitor@tardigrade: ./a.out
pai2: 3804
paidopai2: 2919
filho2: 3805
filho1: 3805
pai1: 3804
*/
