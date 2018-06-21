#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

/*Exemplo simples para perceber o mecanismo da funcionamento dos sinais
 */

void handler(int sig)
{
	printf("Recebi um CTRL+C\n");
}

void exemplo()
{
	signal(SIGINT,handler);
	pause();
}

int main()
{
	exemplo();
	return 0;
}
