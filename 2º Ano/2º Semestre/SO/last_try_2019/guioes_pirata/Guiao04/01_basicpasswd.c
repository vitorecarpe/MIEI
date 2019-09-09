/**
 * Comando para redirecionar os descritores standard para outors ficheiros. O input para o ficheiro
 * /etc/passwd, o output para o ficheiro saidas.txt e o error para o ficheiro erros.txt.
 * Usar diversos pares de funções de leitura e escrita para perceber que todas tem o seu descritor redefinido.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <fcntl.h>
#include "readln_v1.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFF_SIZE 1024
#define MENU "redir command flags:\n\t-write\n\t-printf\n\t-fprintf\n"

/** Main function for the basic redirection program 
 * Takes as argument the type of pair of functions we want to use:
 * -write for the readln_v1 and write pair;
 * -printf for the scanf and printf pair;
 * -fprintf for the fgets and fprintf pair;
 */
int main (int argc, char** argv)
{
	char buf[BUFF_SIZE], *bufaux = buf;
	int fd_in = 0, fd_out = 1, fd_err = 2, n = 0, count = 0, err = 0;

	if (argc != 2) {
		/* quickly verifying the number of arguments - printing the menu case of mismatch */
		printf(MENU);
		perror("invalid number of arguments");
		exit(EXIT_FAILURE);
	} else {

		/* opening the file for the input
		 * reads only */
		fd_in = open("/etc/passwd", O_RDONLY);

		/* opening the file for the output
		 * creates it if inexistente
		 * truncates text (deletes older text it might had)
		 * write only */
		fd_out = open("./saida.txt", O_CREAT | O_TRUNC | O_WRONLY, 0640);

		/* opening the file for the errors
		 * creates it if inexistente
		 * appends text (if already existed)
		 * write only */
		fd_err = open("./erros.txt", O_CREAT | O_APPEND | O_WRONLY, 0640);

		/* closes stdin and directs it to fd_in
		 * dup() grabs de lowest free file descriptor so we close the stdin before using it */
		close(STDIN_FILENO);
		err = dup(fd_in);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_in);
		/* closes stdout and directs it to fd_out */
		close(STDOUT_FILENO);
		err = dup(fd_out);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_out);
		/* closes stderr and directs it to fd_err */
		close(STDERR_FILENO);
		err = dup(fd_err);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_err);

		/* Now we can use the file descriptors for the standard in (STDIN_FILENO::int or stdin::File* or 0), for the standard
		 * out (STDOUT_FILENO::int or stdout::File* or 1) or for the standard error (STDERR_FILENO::int or stderr::File* or 2); */

		if (!strcmp(argv[1],"-write")) {
			/* using read() and write() */
			if (write(STDOUT_FILENO, "We using the read and write pair!\n", 34) > 0) {
				while (n >= 0) {
					n = readln_v1(STDIN_FILENO, buf, BUFF_SIZE);	// reads line with max length BUFF_SIZE

					if (n > 0) {
						count = write(STDOUT_FILENO, buf, n);
						count *= write(STDOUT_FILENO, "\n", 1);
						count += write(STDERR_FILENO, buf, n);
						count *= write(STDERR_FILENO, "\n", 1);
					}
				}
				if (count < 0) {
					if (write(fd_err, "Something been messed up by the Krakken mah boy!\n", 49)) {
						// This if is just to clear the warning from return from the write function
					}
				}
			} else {
				if (write(fd_err, "Shiver me timbers! Boat being gulped by the water even before leaving dry land!\n", 80)) {
						// This if is just to clear the warning from return from the write function
				}
			}
		}

		// Since on this case perror is being used for the print to the stderr, it automatically adds a error description after each line it prints
		if (!strcmp(argv[1],"-printf")) {
			/* using printf e scanf */
			if (printf("We using the scanf and printf pair!\n") > 0) {
				while (n >= 0) {
					n = scanf("%[^\n]%*c", buf);		// reads line to buf
					/* scanf doesn't reads lines with white spaces easily. so had to use a bit of reGex */
					if (n > 0) {
						count = printf("%s\n", buf);	// printing to stdout - "saidas.txt" on this case
						perror(buf);					// printing to stderr - "erros.txt" on this case
					}
				}
				if (count < 0) {
					perror("Something been messed up by the Krakken mah boy!\n");
				}
			} else {
				perror("Shiver me timbers! Boat being gulped by the water even before leaving dry land!\n");
			}
		}

		if (!strcmp(argv[1],"-fprintf")) {
			/* using printf e scanf */
			if (fprintf(stdout, "We using the fgets and fprintf pair!\n")) {
				while ((bufaux != NULL) && (n >= 0)) {
					bufaux = fgets(buf, BUFF_SIZE, stdin);		// reads line to buf
					n = strlen(buf);
					if ((bufaux != NULL) && (n > 0)) {
						count = fprintf(stdout, "%s", buf);
						count += fprintf(stderr, "%s", buf);
					}
				}
				if (count < 0) {
					if (fprintf(stderr, "Something been messed up by the Krakken mah boy!\n")) {
						// This if is just to clear the warning from return from the fprintf function
					}
				}
			} else {
				if (fprintf(stderr, "Shiver me timbers! Boat being gulped by the water even before leaving dry land!\n")) {
					// This if is just to clear the warning from return from the fprintf function
				}
			}
		}

		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);

		/* no need to get all back to proper situations because we are exiting the program */
		exit(EXIT_SUCCESS);
	}
}