#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

void f(){
	write(1, "Carlos mr. Reese\n", 18); //não se usa o printf pois como ele usa apontadores para o buffer podia chegar um sinal enquanto um printf e ele chamaria outro printf(podia ficar a chamar recursivamente e imprimir coisas estranhas para o ecrã). O write como é uma system call não tem estes problemas de segurança
}

void g(){
	write(1, "ALARM!\n", 7);
	_exit(0);
}

int main(){
	int i = 0;

	signal(SIGINT, f);
	signal(SIGTERM, f);

	signal(SIGALRM, g);
	alarm(10);

	while(1){

		printf("Estou a perder tempo... %d\n", i);
		i++;
		sleep(1);
	}
}