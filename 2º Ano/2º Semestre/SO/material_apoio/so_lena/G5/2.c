#include <unistd.h>
#include <stdio.h>
#include <limits.h>
#include <sys/wait.h>

/* Modifique o programa anterior de modo a leitura
 * do pipe ser realizada enquanto não for detectada
 * a situacão de end of file no descritor respectivo.
 * Repare que esta situacão acontece apenas quando
 * nenhum processo – neste caso, pai e filho – tem
 * aberto o descritor de escrita do pipe.
 */

int main (int argc, char** argv) {
	int mp[2], n;
	pipe(mp); // criação de um pipe anónimo
	char buf[17];
	int p = fork();
	if (p == 0) { // se sou o filho
		close(mp[0]);
		while ((n = read(0, buf, PIPE_BUF)) > 0) {
			write(mp[1], buf, n); // indice 1 é associado à escrita
		}
		close(mp[1]);
	} else { // se sou o pai
		close (mp[1]);
		while ((n = read(mp[0], buf, PIPE_BUF)) > 0) {
			write(1, buf, n);
		}
		close(mp[0]);
	}
}