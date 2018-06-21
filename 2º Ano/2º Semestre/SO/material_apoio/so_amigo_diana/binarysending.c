#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

/* Este programa envia um caractér de um processo para o outro recorrendo a 
 * sinais. O caracter é desmembrado em bits e reconstruído no processo filho.
 * Recorre aos sinais SIGUSR1 e SIGUSR2. O caracter, quando totalmente 
 * reconstruido pode ser mostrado apenas no processo filho. É necessário 
 * garantir a sincronização entre processos para não se correr o risco 
 * de perder algum bit.
 */

char global = 0;
int contador=0;

void add(int signo)
{
	contador++;
	if(signo == SIGUSR1)
	{
		global = global << 1;	
	}
	else {
		global = global << 1;
		global = global | 1;
	}
	if(contador==8)
		printf("Caracter transmitido: %c\n",global);
	kill(getppid(),SIGUSR1);
}

void nada(int signo)
{}

void sendbinary(char c)
{
	pid_t pid;
	int i,j;
	//Armadilhar os signais SIGUSR1 e SIGUSR2
	signal(SIGUSR1,add);
	signal(SIGUSR2,add);
	pid = fork();
	switch (pid)
	{
		case -1: perror("fork");
			 exit(EXIT_FAILURE);
		case 0: for(j=0;j<8;j++)
				{
					pause();//Aguardar pelo bit
				}
			//printf("Resultado: %c\n",global);
		default:signal(SIGUSR1,nada); 
			for(i=0;i!=8;i++)
				{
					if(((c & 128) >> 7) == 0)
						kill(pid,SIGUSR1);
					else
						kill(pid,SIGUSR2);
					c = c << 1;
					pause();
				}

	}
}

int main()
{
	sendbinary('a');
	return 0;
}
