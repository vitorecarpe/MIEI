#include <unistd.h> /* copiar o código que está no ficheiro */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h> /* para usar o exit */

/*
 * #define MAX 20 <- substitui MAX por 20
 */

void main() {
	int r;
	while ((r = read(0, stdin, 1)) > 0)
		write(1,stdin, 1);
	if (r == -1) {
		perror("Ocorreu um erro na leitura!");
		exit(-1);
	}
	exit(0);
}

/* CTRL + D is not a signal, it's EOF (End-Of-File). It closes the
 * stdin pipe. If read(STDIN) returns 0, it means stdin closed,
 * which means CTRL + D was hit (assuming there is a keyboard at
 * the other end of the pipe).
 */

/* - return is an instruction of the language that returns from a
 * function call. 
 * - exit is a system call (not a language statement) that terminates
 * the current process.
 * The only case when both do (nearly) the same thing is in the main()
 * function, as a return from main performs an exit().
 * The parameter of exit() is an integer (it's the return status
 * of the process that the launcher process can get; the conventional
 * usage is 0 for success or any other value for an error).
 */

/* #include  <fcntl.h> 
 * int read(int handle, void *buffer, int nbyte);
 * The read() function attempts to read nbytes from the file associated
 * with handle, and places the characters read into buffer. If the file
 * is opened using O_TEXT, it removes carriage returns and detects the
 * end of the file.
 * The function returns the number of bytes read. On end-of-file,
 * 0 is returned, on error it returns -1, setting errno to indicate
 * the type of error that occurred.
 */

/*
 * Standard input - this is the file handle that your process reads
 * to get information from you.
 * Standard output - your process writes normal information to
 * this file handle.
 * Standard error - your process writes error information here.
 */

/*
cat /etc/passwd > /tmp/out     # redirect cat's standard out to /tmp/foo
cat /nonexistant 2> /tmp/err   # redirect cat's standard error to /tmp/error
cat < /etc/passwd              # redirect cat's standard input to /etc/passwd
*/
