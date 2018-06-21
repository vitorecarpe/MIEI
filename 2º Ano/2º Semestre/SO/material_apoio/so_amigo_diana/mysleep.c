#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

void handler(int sig)
{
	printf("Recebi o alarme\n");
}

/*int mysleep(int secs)
{
	int restantes;
	signal(SIGALRM,handler);
	restantes=alarm(secs);
	signal(SIGINT,handler);
	pause();
	return restantes;
}*/

/* A função mysleep é capaz de interceptar os sinais SIGALARM e SIGINT, isto é,
 * intercepta o final de um alarme e intercepta o envio de um CTRL+C
 * */
int mysleep(unsigned segs)
{
	int rest=0;
	signal(SIGALRM,handler);
	signal(SIGINT,handler);
	alarm(segs);
	pause();
	rest = alarm(0);
	return rest;
}

int main()
{
	int res;
	res = mysleep(10);
	printf("Sobraram: %d\n",res);
	signal(SIGINT,handler);
	return 0;
}
