/**
 * Alteração da função especificada no primeiro exercicio, criando processos após o redirecionamento
 * dos descritores, para a leitura e escrita do texto. Pretendemos provar que os "filhos" copiam os
 * descritores dos "pais". 
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
	pid_t pid;

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
			pid = fork();
			if (pid < 0) {
				perror("fork failed. terminating all processes");
				_exit(EXIT_FAILURE);
			}
			if (pid == 0) {
				// child process
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
				/* write doesn't normally needs it but if no printing is done, do a fflush here too
				 * so that the parent won't close the files before it prints on them */
				// exit(EXIT_SUCCESS);
			}

		}

		if (!strcmp(argv[1],"-printf")) {
			/* using printf e scanf */
			pid = fork();
			if (pid < 0) {
				perror("fork failed. terminating all processes");
				_exit(EXIT_FAILURE);
			}
			if (pid == 0) {
				// child process
				if (printf("We using the scanf and printf pair!\n") > 0) {
					while (n >= 0) {
						n = scanf("%[^\n]%*c", buf);		// reads line to buf
						/* scanf doesn't reads lines with white spaces easily. so had to use a bit of reGex */
						if (n > 0) {
							count = printf("%s\n", buf);
							perror(buf);
						}
					}
					if (count < 0) {
						perror("Something been messed up by the Krakken mah boy!\n");
					}
				} else {
					perror("Shiver me timbers! Boat being gulped by the water even before leaving dry land!\n");
				}
				/* need to force a flush for it to print to the output before the child leaves the process 
				 * and a normal exit / return from the process is a good way for that since auto flushes */
				exit(EXIT_SUCCESS);
			}
		}

		if (!strcmp(argv[1],"-fprintf")) {
			/* using printf e scanf */
			pid = fork();
			if (pid < 0) {
				perror("fork failed. terminating all processes");
				_exit(EXIT_FAILURE);
			}
			if (pid == 0) {
				// child process
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
				/* need to force a flush for it to print to the output before the child leaves the process 
				 * and a normal exit / return from the process is a good way for that since auto flushes */
				exit(EXIT_SUCCESS);
			}
		}

		// parent closes all the files at the end
		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);

		/* no need to get all back to proper situations because we are exiting the program */
		exit(EXIT_SUCCESS);
	}
}