/**
 * Programa que cria um pipe anonimo e um processo filho.
 * Procede a comunicações entre pai e filho em ambos os sentidos, com ou sem delay.
 *
 * Tomar em atenção que o filho recebe os descritores de ficheiros do pai, e ao se
 * executar um fork() após a criação de um pipe(), o pipe fica com ambos os processos
 * com acesso aos seus descritores, precisando todos os processos de "fechar" ambos os
 * lados do pipe para este ser fechado completamente.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

#define BUFF_SIZE 512
#define MENU "Command needs 1 argument:\n\
\t-p2c > for parent to child communication\n\
\t-c2p > for child to parent communication\n\
\t-dp2c > for parent to child with delay\n\
\t-dc2p > for child to parent with delay\n"
#define MENU_SIZE 26 + 42 + 42 + 40 + 40

int main (int argc, char** argv)
{
	char buff[BUFF_SIZE];
	pid_t pid;
	ssize_t n;
	int err, pipe_desc[2];
	/* pipe descriptors is an array with 2 ints:
	 * 		- indice 0 for output;
	 * 		- indice 1 for input; */

	if (argc != 2) {
		if (write(STDOUT_FILENO, MENU, MENU_SIZE)) {}
		_exit(EXIT_FAILURE);
	}

	err = pipe(pipe_desc);
	if (err < 0) {
		if (write(STDERR_FILENO, "this communication pipe went wrong at some point", 48)) {}
		_exit(EXIT_FAILURE);
	}

	// on the case it's communication from parent to child
	if (!strcmp("-p2c",argv[1])) {
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
			n = read(pipe_desc[0],buff,BUFF_SIZE);

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);

			if (write(STDOUT_FILENO, "Parent process sent me this message:\n", 37)) {}
			if (write(STDOUT_FILENO, buff, n)) {}
		} else {
			// parent process
			/* parent will be sending the messages:
			 * - closes the output of the pipe;
			 * - redirects or writes to the input */
			close(pipe_desc[0]);
			if (write(STDOUT_FILENO, "Sending message through the pipe:\n", 34)) {}
			if (write(pipe_desc[1], "One, two, three, testing!\n", 26)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);

			// Not mandatory, but waiting on child
			wait(NULL);
		}
	}

	// on the case it's communication from child to parent
	if (!strcmp("-c2p",argv[1])) {
		pid = fork();
		if (pid < 0) {
			if (write(STDERR_FILENO, "Can't create new family member", 30)) {}
			_exit(EXIT_FAILURE);
		}

		if (pid == 0) {
			// child process
			/* child will be sending the messages:
			 * - closes the output of the pipe;
			 * - redirects or writes to the input */
			close(pipe_desc[0]);
			if (write(STDOUT_FILENO, "Sending message through the pipe:\n", 34)) {}
			if (write(pipe_desc[1], "One, two, three, testing!\n", 26)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);
		} else {
			// parent process
			/* parent will be receiving the messages:
			 * - closes the input to the pipe;
			 * - redirects or reads from the output */
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);

			if (write(STDOUT_FILENO, "Child process sent me this message:\n", 36)) {}
			if (write(STDOUT_FILENO, buff, n)) {}

			// Not mandatory, but waiting on child
			wait(NULL);
		}
	}

	// on the case it's communication from parent to child with delay
	if (!strcmp("-dp2c",argv[1])) {
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
			n = read(pipe_desc[0],buff,BUFF_SIZE);

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);

			if (write(STDOUT_FILENO, "Parent process sent me this message:\n", 37)) {}
			if (write(STDOUT_FILENO, buff, n)) {}
		} else {
			// parent process
			/* parent will be sending the messages:
			 * - closes the output of the pipe;
			 * - redirects or writes to the input */
			close(pipe_desc[0]);
			if (write(STDOUT_FILENO, "Delaying the sending of the message through the pipe:\n", 54)) {}
			sleep(5);
			if (write(pipe_desc[1], "One, two, three, testing!\n", 26)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);

			// Not mandatory, but waiting on child
			wait(NULL);
		}
	}

	// on the case it's communication from child to parent with delay
	if (!strcmp("-dc2p",argv[1])) {
		pid = fork();
		if (pid < 0) {
			if (write(STDERR_FILENO, "Can't create new family member", 30)) {}
			_exit(EXIT_FAILURE);
		}

		if (pid == 0) {
			// child process
			/* child will be sending the messages:
			 * - closes the output of the pipe;
			 * - redirects or writes to the input */
			close(pipe_desc[0]);
			if (write(STDOUT_FILENO, "Delaying the sending of the message through the pipe:\n", 54)) {}
			sleep(5);
			if (write(pipe_desc[1], "One, two, three, testing!\n", 26)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);
		} else {
			// parent process
			/* parent will be receiving the messages:
			 * - closes the input to the pipe;
			 * - redirects or reads from the output */
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);

			if (write(STDOUT_FILENO, "Child process sent me this message:\n", 36)) {}
			if (write(STDOUT_FILENO, buff, n)) {}

			// Not mandatory, but waiting on child
			wait(NULL);
		}
	}
	
	exit(EXIT_SUCCESS);
}