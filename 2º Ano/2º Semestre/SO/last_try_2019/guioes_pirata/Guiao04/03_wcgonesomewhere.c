/**
 * Alteração do primeiro exercicio mas onde seja chamado o comando wc sem argumentos após
 * o redirecionamento dos descritores de entrada e saída. Pretende-se provar que a system call
 * exec() mantém as alterações dos descritores de ficheiros do processo que a chama.
 * Possibilidade de executar com ou sem a criação de processo filho.
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFF_SIZE 1024
#define MENU "redir command flags:\n\t-parent\n\t-child\n"

/** Main function for the basic redirection program 
 * Takes as argument the type of pair of functions we want to use:
 * -write for the readln_v1 and write pair;
 * -printf for the scanf and printf pair;
 * -fprintf for the fgets and fprintf pair;
 */
int main (int argc, char** argv)
{
	int fd_in = 0, fd_out = 1, fd_err = 2, err = 0;
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

		if (!strcmp(argv[1],"-parent")) {
			/* main process does it all */
			execlp("wc","wc",NULL);
			_exit(EXIT_FAILURE);
			/* file descriptors aren't closed */ 
		}

		if (!strcmp(argv[1],"-child")) {
			/* creates a child process and makes it run */
			pid = fork();
			if (pid < 0) {
				perror("no child was made, universe didn't wanted it to happen");
				_exit(EXIT_FAILURE);
		    }
			if (pid == 0) {
				// child process
				execlp("wc","wc",NULL);
				_exit(EXIT_FAILURE);
			}
		}

		/* only the case when execlp is executed with the child process the program arrives here
		 * or in the case that the argument isn't one of the 2 possible choices - nothing happens */
		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);

		/* no need to get all back to proper situations because we are exiting the program */
		exit(EXIT_SUCCESS);
	}
}