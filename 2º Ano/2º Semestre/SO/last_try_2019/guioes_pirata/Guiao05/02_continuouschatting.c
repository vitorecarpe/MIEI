/**
 * Modificação do exercicio anterior de modo a ler continuamente até um EOF ser lido no descritor do pipe.
 * 
 * Tomar em conta que só acontece quando ambos os processos têm o descritor de excrita no pipe fechado.
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
\t-c2p > for child to parent communication\n"
#define MENU_SIZE 26 + 42 + 42

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
			do {
				n = read(pipe_desc[0],buff,BUFF_SIZE);

				if (write(STDOUT_FILENO, "Parent process sent me this message:\n", 37)) {}
				if (write(STDOUT_FILENO, buff, n)) {}
			} while (n > 0);
			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);
		} else {
			// parent process
			/* parent will be sending the messages:
			 * - closes the output of the pipe;
			 * - redirects or writes to the input */
			close(pipe_desc[0]);
			if (write(STDOUT_FILENO, "Sending messages through the pipe:\n", 35)) {}
			if (write(pipe_desc[1], "One, two, three, testing!\n", 26)) {}
			sleep(1);
			if (write(pipe_desc[1], "This is the second message!\n", 28)) {}
			sleep(1);
			if (write(pipe_desc[1], "Another second passed!\n", 23)) {}
			sleep(1);
			if (write(pipe_desc[1], "Have no idea what to write in these!\n", 37)) {}
			sleep(1);
			if (write(pipe_desc[1], "Ok, you get the point... lot of messages!\n", 42)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);
			/* This is pretty much as saying to the other side of the
			 * pipe that there will be no more writing to it */

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
			sleep(1);
			if (write(pipe_desc[1], "I just copied the other messages!\n", 34)) {}
			sleep(1);
			if (write(pipe_desc[1], "Another second passed!\n", 23)) {}
			sleep(1);
			if (write(pipe_desc[1], "Have no idea what to write in these!\n", 37)) {}
			sleep(1);
			if (write(pipe_desc[1], "Ok, you get the point... lot of messages!\n", 42)) {}

			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[1]);
			/* This is pretty much as saying to the other side of the
			 * pipe that there will be no more writing to it */
		} else {
			// parent process
			/* parent will be receiving the messages:
			 * - closes the input to the pipe;
			 * - redirects or reads from the output */
			close(pipe_desc[1]);
			do {
				n = read(pipe_desc[0],buff,BUFF_SIZE);

				if (n > 0) {
					// Just so that it doesn't print when n = 0, meaning it received EOF
					// -p2c was left without this to show the difference
					if (write(STDOUT_FILENO, "Child process sent me this message:\n", 36)) {}
					if (write(STDOUT_FILENO, buff, n)) {}
				}
			} while (n > 0);
			/* close the pipe on his side after not need it anymore
			 * _exit() or exit() or return also close the descriptors */
			close(pipe_desc[0]);

			// Not mandatory, but waiting on child
			wait(NULL);
		}
	}
	
	exit(EXIT_SUCCESS);
}