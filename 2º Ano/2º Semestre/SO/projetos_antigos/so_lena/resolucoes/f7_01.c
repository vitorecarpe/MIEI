#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>

int c = 0;
int c1 = 0;

void conta(){
	alarm(1);
	c++;
}
void mostra (){
	printf("\n%d\n",c);
	c1++;
}

void contaCTRLD (){
	printf("O user carregou %d vezes no CTR-C\n",c1);
	_exit(0);
}

int main (int argc, char* argv []){
	signal(SIGALRM,conta);
	signal(SIGINT,mostra);
	signal(SIGQUIT,contaCTRLD);
	alarm(1);// metemos aqui porque se tivesse dentro do ciclo se tivessemos sempre a carregar ctr-C o tempo n avançava
	while(1){
		//alarm(1) n pode estar aqui
		pause(); //o pause liberta o cpu, se n tivesse quando viesse um sinal o programa estava sempre em ciclo infinito a gastar cpu
	}
}