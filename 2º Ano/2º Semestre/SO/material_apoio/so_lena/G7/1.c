#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>


/* Using SIGINT, SIGQUIT, and SIGALRM, write a program
 * that counts the time in seconds since it started
 * and prints the time elapsed whenever the user
 * presses Ctrl-C. If the user presses Ctrl-D your
 * program should indicate how many times the user has
 * pressed Ctr-C and exit.
 */

// SIGINT <- interrupt from keyboard || CONTROL-C
// SIGQUIT <- quit from keyboard || CONTROL-\
// SIGALRM <- timer signal from alarm(2)

/* unsigned int alarm(unsigned int seconds);
 * DESCRIPTION
 * alarm()  arranges  for  a  SIGALRM signal to be
 * delivered to the calling process in seconds
 * seconds.
 * If seconds is zero, any pending alarm is canceled.
 * In any event any previously set alarm() is
 * canceled.
 */


int cs = 0;
int seg = 0;

void contac() {
	printf("%d\n", seg);
	cs++;
}
void contaseg() {
	alarm(1);
	printf("lala\n");
	seg++;
}

void terminate() {
	printf("O user carregou %d vezes no control-c\n", cs);
	_exit(0);
}


void main() {
	signal(SIGALRM, contaseg);
	signal(SIGQUIT, terminate);
	signal(SIGINT, contac);
	alarm(1); // Metemos aqui porque se tivesse dentro
			  // do ciclo, se tivessemos sempre a
			  // carregar CTRL-C o tempo não avançava.
	while (1) {
		pause(); // o pause() liberta o CPU, se
				 // não tivesse quando viesse um
				 // sinal o programa estava sempre
				 // em ciclo infinito a gastar CPU
	}
}

/* pause() causes the calling process (or thread)
 * to sleep until a signal is delivered that
 * either terminates the process or causes the
 * invocation of a signal-catching function.
 */