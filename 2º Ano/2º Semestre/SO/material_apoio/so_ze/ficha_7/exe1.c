#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

int num_ctrl_c = 0;
int tempo = 0;

void f(){
	num_ctrl_c++;
	printf("Tempo passado: %d segundos\n", tempo);
	//write(1, "Carlos mr. Reese\n", 18); //não se usa o printf pois como ele usa apontadores para o buffer podia chegar um sinal enquanto um printf e ele chamaria outro printf(podia ficar a chamar recursivamente e imprimir coisas estranhas para o ecrã). O write como é uma system call não tem estes problemas de segurança
}

void g(){
	//write(1, "ALARM!\n", 7);
	//alarm(1);
	tempo++;
	alarm(1);
//	_exit(0);
}

void h(){
	printf("Número de Ctrls+C: %d\n", num_ctrl_c);
	_exit(0);
}

int main(){
	int i = 0;

	signal(SIGINT, f);
	//signal(SIGTERM, f);
	signal(SIGQUIT, h);

	signal(SIGALRM, g);
	alarm(1);

	while(1){
		pause();
		//alarm(1);
	
	}
}