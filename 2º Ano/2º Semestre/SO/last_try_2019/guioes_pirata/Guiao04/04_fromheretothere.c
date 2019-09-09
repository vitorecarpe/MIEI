/**
 * Programa 'redir' que permita executar um comando, opcionalmente redirecionando a 
 * entrada e/ou a saida:
 * redir [-i <inputfile>] [-o <outputfile>] <command> [arg1] [arg2] [...
 * 
 * @author (Pirata) 
 * @version (2018.02)
 */

#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main (int argc, char** argv)
{
	int fd_in = STDIN_FILENO, fd_out = STDOUT_FILENO, opt_flag = 1, i = 1, err;
	pid_t pid;

	/* Getting all the arguments and checking if there is an input or output file
	 * Assuming that the -o and the -i come as the first pairs of arguments. In case that
	 * doesn't happens it will consider it as arguments of the command we want to run. */
	while (opt_flag && (i < argc)) {
		if (!strcmp("-o",argv[i]) || !strcmp("-i",argv[i])) {
			// opt_flag keeps being True
			if (i + 1 < argc) {
				if (!strcmp("-o",argv[i])) {
					fd_out = open(argv[i + 1], O_CREAT | O_APPEND | O_WRONLY, 0640);
				}
				if (!strcmp("-i",argv[i])) {
					fd_in = open(argv[i + 1], O_RDONLY);
				}
				i += 2;
			} else {
				perror("Ahoy there... where the rest of arguments might be?");
				_exit(EXIT_FAILURE);
			}
		} else {
			opt_flag = 0;
		}
	}

	// At this point we already have the files input and output if any were given

	pid = fork();

	if (pid < 0) {
		perror("no child was made, universe didn't wanted it to happen");
		_exit(EXIT_FAILURE);
    }
	if (pid == 0) {
		// child process
		err = dup2(fd_in, STDIN_FILENO);
		if (err < 0) {
			perror("Can't read, not even with my good eye!");
			_exit(err);
		}
		close(fd_in);

		err = dup2(fd_out, STDOUT_FILENO);
		if (err < 0) {
			perror("Blamey pirates stole my log book, where to write now!");
			_exit(err);
		}
		close(fd_in);

		// Now with the correct redirections, we execute the command
		argv[argc] = NULL;
		execvp(argv[i], argv+i);
		// only comes here if execvp fails
		perror(argv[i]);
		_exit(EXIT_FAILURE);
	} else {
		// parent process
		wait(NULL);
		close(fd_in);
		close(fd_out);
	}
	exit(EXIT_SUCCESS);
}