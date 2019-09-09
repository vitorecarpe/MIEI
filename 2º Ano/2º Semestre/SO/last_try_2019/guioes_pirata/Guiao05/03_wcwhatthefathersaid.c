/**
 * Programa que executa o comando wc num processo filho. O processo pai deve enviar
 * ao filho atravéx dum pipe anónimo uma sequência de linhas de texto introduzidas pelo
 * utilizador no stdin.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>

#define BUFF_SIZE 512

int main (int argc, char** argv)
{
	char buff[BUFF_SIZE];
	pid_t pid;
	ssize_t n;
	int err, pipe_desc[2];
	/* pipe descriptors is an array with 2 ints:
	 * 		- indice 0 for output;
	 * 		- indice 1 for input; */

	if ((argc != 1) && (argv != NULL)) {
		if (write(STDERR_FILENO, "No need for extra arguments\n", 28)) {}
		_exit(EXIT_FAILURE);
	}

	err = pipe(pipe_desc);
	if (err < 0) {
		if (write(STDERR_FILENO, "This communication pipe went wrong at some point", 48)) {}
		_exit(EXIT_FAILURE);
	}

	pid = fork();
	if (pid < 0) {
		if (write(STDERR_FILENO, "Can't create new family member", 30)) {}
		_exit(EXIT_FAILURE);
	}

	if (pid == 0) {
		// child process
		/* child will be receiving the messages:
		 * - closes the input to the pipe;
		 * - redirects or reads from the output */
		close(pipe_desc[1]);
		err = dup2(pipe_desc[0], STDIN_FILENO);
		if (err < 0) {
			// Error during dup2
			if (write(STDERR_FILENO, "Well, that happened\n", 20)) {}
			_exit(EXIT_FAILURE);
		} else {
			// dup2 succeded so can close the pipe_desc now since it's redirected to stdin
			close(pipe_desc[0]);
		}
		execlp("wc", "wc", NULL);	// exec will close the rest of the descriptors on exit
		_exit(EXIT_FAILURE);
	} else {
		// parent process
		/* parent will be sending the messages:
		 * - closes the output of the pipe;
		 * - redirects or writes to the input */
		close(pipe_desc[0]);

		if (write(STDOUT_FILENO, "Write text to be sent to wc. Use Ctrl+D to finnish\n", 51)) {}
		do {
			n = read(STDIN_FILENO, buff, BUFF_SIZE);

			if (write(pipe_desc[1], buff, n)) {}
		} while (n > 0);
		/* Instead of "n > 0", "n > 1" could be used and it would stop on the first "empty" line
		 * written by the user - it would only have an '\n' */

		/* close the pipe on his side after not need it anymore
		 * _exit() or exit() or return also close the descriptors */
		close(pipe_desc[1]);
		/* This is pretty much as saying to the other side of the
		 * pipe that there will be no more writing to it */

		// Not mandatory, but waiting on child
		wait(NULL);
	}
	
	exit(EXIT_SUCCESS);
}