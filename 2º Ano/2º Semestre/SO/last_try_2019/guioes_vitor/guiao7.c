#include <signal.h>
#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <time.h>


/*
typedef void (*sighandler_t)(int);
sighandler_t signal(int signum, sighandler_t handler);
int kill(pid_t pid, int sig);
unsigned int alarm(unsigned int seconds);
int pause(void);*/


//1

int ctrlc = 0;
int tempo = 0;

void ctrlc_handler(int signum){
	printf("%d seconds\n", tempo);
	ctrlc++;
}

void ctrlq_handler(int signum){
	printf("Ctrl+C %d times\n", ctrlc);
	_exit(0);
}

void alarm_handler(int signum){
	tempo++;
	alarm(1);
}

int main(int argc, char** argv){
	signal(SIGINT, ctrlc_handler);
    signal(SIGQUIT, ctrlq_handler);
    signal(SIGALRM, alarm_handler);
    alarm(1);

	while(1){
		pause();
	}
	return 0;
}

//2







