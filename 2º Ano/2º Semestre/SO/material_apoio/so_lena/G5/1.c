#include <unistd.h>
#include <stdio.h>

int main (int argc, char** argv) {
	int mp[2], n;
	int ret = pipe(mp); // criação de um pipe anónimo
	if (ret == -1) {
		perror("falha na criação do pipe!");
		exit(-1);
	}
	char buf[17];
	int p = fork();
	if (p == -1) {
		perror("falha no fork()!");
		exit(-1);
	} else if (p == 0) { // se sou o filho
		n = read(mp[0], buf, 17);
		// printf("%d, %s", n, buf); // indice 0 é associado à leitura
		write(1, buf, n); // não dá para escrever logo write(1, mp[0], 11) porque mp[0] não é um buffer
	} else { // se sou o pai
		sleep(5);
		write(mp[1], "sending regards!\n", 17); // indice 1 é associado à escrita
	}
}

/* Note agora que a leitura do filho bloqueia enquanto
 * o pai nao realizar a operação de escrita no pipe. 
 */