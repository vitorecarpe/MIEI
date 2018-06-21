#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

/** Usada por programas cliente, contacta o
 *  servidor e para uma palavra xxxxxx grava
 *  num ficheiro xxxxxx.png a imagem
 *  correspondente devolvida pelo servidor.
 */

ssize_t readln(int fildes, char* buffer, size_t nbytes) {
	int i = 0, r;
	while (i < nbytes && (r = read(fildes, &buffer[i], 1)) > 0 && buffer[i++] != '\n');
	if (r == -1) {
		perror("Erro de leitura!\n");
		exit(-1);
	}
	buffer[i] = '\n';
	return i;
}

void create_captcha_file(const char* palavra) {
	int wr = open("server", O_WRONLY, 0600);
	char pid[PIPE_BUF];
	sprintf(pid, "%d", getpid());

	write(wr, palavra, strlen(palavra));
	write(wr, pid, strlen(pid));

	char fN[strlen(palavra) + 4];
	sprintf(fN, "%s.png", palavra);

	int rd = open(pid, O_RDONLY, 0600);
	int file = open(fN, O_CREAT | O_WRONLY | O_TRUNC, 0600);

	int r = read(rd, ben, sizeof(ben));
	write(file, ben, r);

	exit(0);
}

void main(int argc, char** argv) {
	int rd = open("server", O_RDONLY, 0600);
	char ben[16384], palavra[6], pid[16384];

	int r = read(rd, palavra, 6);
	r = read(rd, pid, 16384);
	pid[r] = '\0';

	int wr = open(pid, O_WRONLY, 0600);
	size_t size = captcha(palavra, ben);
	write(wr, ben, size);

	exit(0);
}
