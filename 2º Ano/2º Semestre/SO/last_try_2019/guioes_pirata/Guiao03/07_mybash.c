/**
 * Interpretador de comandos simples inspirado na bash.
 * Executa comandos especificados numa linha de texto do utilizador.
 * Comandos: <programa> [argumentos]
 * 
 * & - Executa em plano de fundo se a linha acabar com &.
 * exit || Ctrl+D - termina execução do interpretador.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <wordexp.h>
#include <sys/wait.h>	// waitpid()
#include <fcntl.h>		// for write and MACROS
#include <string.h>		// for strcmp()
#include "readln_v1.h"
/* Structure wordexp_t (partially)
wordexp_t {
	size_t we_wordc = argc;
	char** we_wordv = argv;
}*/

#define PROMPT_WORD "mybash > "
#define PROMPT_SIZE 9

int main()
{
	char buff[1024];
	wordexp_t p;
	int exit_flag = 0, background_flag = 0, n = 0;
	pid_t pid;

	/* While doesn't read an exit command and while reads something */
	while (!exit_flag) {
		background_flag = 0;

		/* The current directory can be shown using the "getcwd(NULL,size)" function
		 * but for not using it for this case far simplicity or laziness */
		if (write(STDOUT_FILENO,PROMPT_WORD,PROMPT_SIZE)) {};
		n = readln_v1(STDIN_FILENO, buff, 1024);

		/* taking spaces from end of line and
		 * if last word is & changes flag and cuts it */
		while ((n > 0) && (buff[n - 1] == ' ')) {
			n--;
			buff[n] = 0;
		}
		if ((n > 1) && (buff[n - 1] == '&') && (buff[n - 2] == ' ')) {
			background_flag = 1;
			buff[n - 1] = 0;
			buff[n - 2] = 0;
		}

		/* If it's an empty line just skips it
		 * wordexp turns the line (char*) into a list of words (char**) */
		if ((n > 0) && (!wordexp(buff, &p, 0))) {
			if (strcmp("exit",p.we_wordv[0])) {
				pid = fork();

				if (pid < 0) {
					if (write(STDERR_FILENO,"child down, repeated, we have a child down",42)){};
                }
				if (pid == 0) {
					// Child
					execvp(p.we_wordv[0], p.we_wordv);
					_exit(-1);		// in case of error only
				}
				// Only Parent arrives here
				wordfree(&p);

				if (!background_flag){
					/* using waitpid to make sure I'm waiting for the correct one
					 * In case I have a process in bg */
					waitpid(pid,NULL,0);
				}
			} else {	// means "exit" was written
				exit_flag = 1;
            }
		} else {
			if (n < 0) {
				exit_flag = 1;
            }
			if (n > 0) {
				/* if it comes here, means wordexp() failed, probably due to bash symbols */
				if (write(STDERR_FILENO,"so, I\'m a simple bash, don\'t come at me with those hard symbols",63)) {};
			}
		}
	}
	return 0;
}
