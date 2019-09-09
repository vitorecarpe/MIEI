/**
 * Interpretador de comandos simples inspirado na bash.
 * Executa comandos especificados numa linha de texto do utilizador.
 * Comandos: <programa> [argumentos]
 * 
 * & - Executa em plano de fundo se a linha acabar com &.
 * exit || Ctrl+D - termina execução do interpretador.
 *
 * Considera os operadores de redirecionamento:
 * < para input
 * > e >> para output
 * 2> e 2>> para erros
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <wordexp.h>
#include <stdlib.h>
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

	int fd_in, fd_out, fd_err, fd_in_filename, fd_out_filename, fd_err_filename, output_flag, append_flag, append_output, append_error;
	int i, aux;

	/* While doesn't read an exit command and while reads something */
	while (!exit_flag) {
		background_flag = 0;
		fd_in = STDIN_FILENO;
		fd_out = STDOUT_FILENO;
		fd_err = STDERR_FILENO;
		fd_in_filename = -1;
		fd_out_filename = -1;
		fd_err_filename = -1;
		append_output = 0;
		append_error = 0;

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

		// checking for the redirection signs, filenames and others
		for (i = 0; i < n; i++) {
			append_flag = 0;
			// for the redirection of the input case
			if (buff[i] == '<') {
				// setting EOF signs and clearing whitespaces
				buff[i] = 0;
				aux = i - 1;
				while ((aux > 0) && (buff[aux] == ' ')) {
					buff[aux] = 0;
					aux--;
				}
				aux = i + 1;
				while ((aux < n) && (buff[aux] == ' ')) {
					buff[aux] = 0;
					aux++;
				}
				// setting the start of the filename for fd_in
				fd_in_filename = aux;
			}
			// for the redirection of the output case
			if (buff[i] == '>') {
				buff[i] = 0;
				// checks if it's the error redir case or not
				if ((i - 2 > 0) && (buff[i - 1] == '2') && (buff[i - 2] == ' ')) {
					buff[i - 1] = 0;
					buff[i - 2] = 0;
					aux = i - 3;
					output_flag = 0;
				} else {
					// it's output redirection
					aux = i - 1;
					output_flag = 1;
				}
				// clearing whitespaces previous to command
				while ((aux > 0) && (buff[aux] == ' ')) {
					buff[aux] = 0;
					aux--;
				}

				// check if it's append or truncate
				if (buff[i + 1] == '>') {
					buff[i + 1] = 0;
					i++;
					append_flag = 1;
				}
				aux = i + 1;
				// clearing whitespaces previous to command
				while ((aux < n) && (buff[aux] == ' ')) {
					buff[aux] = 0;
					aux++;
				}
				// setting the correct redir filename pointed
				if (output_flag) {
					fd_out_filename = aux;
					if (append_flag) {
						append_output = 1;
					}
				} else {
					fd_err_filename = aux;
					if (append_flag) {
						append_error = 1;
					}
				}
			}
		}

		/* If it's an empty line just skips it
		 * wordexp turns the line (char*) into a list of words (char**) */
		if ((n > 0) && (!wordexp(buff, &p, 0))) {
			if (strcmp("exit",p.we_wordv[0])) {
				
				pid = fork();

				if (pid < 0) {
					if (write(STDERR_FILENO,"child down, repeated, we have a child down",42)){};
					_exit(EXIT_FAILURE);
                }
				if (pid == 0) {
					// Child

					// only doing the redirections on the child so the parent won't be affected
					// if something like dup2(0,0) happens, no rediretion is made
					if(fd_in_filename > 0) {
						fd_in = open(&buff[fd_in_filename], O_RDONLY);
						if (fd_in < 0) {
							if (write(STDERR_FILENO, "problems opening reading scroll\n", 32)) {}
						}
						dup2(fd_in, STDIN_FILENO);
						close(fd_in);
					}
					if(fd_out_filename > 0) {
						if (append_output) {
							fd_out = open(&buff[fd_out_filename], O_CREAT | O_APPEND | O_WRONLY, 0640);
						} else {
							fd_out = open(&buff[fd_out_filename], O_CREAT | O_TRUNC | O_WRONLY, 0640);
						}
						if (fd_out < 0) {
							if (write(STDERR_FILENO, "problems opening writing scroll\n", 32)) {}
						}
						dup2(fd_out, STDOUT_FILENO);
						close(fd_out);
					}
					if(fd_err_filename > 0) {
						if (append_error) {
							fd_err = open(&buff[fd_err_filename], O_CREAT | O_APPEND | O_WRONLY, 0640);
						} else {
							fd_err = open(&buff[fd_err_filename], O_CREAT | O_TRUNC | O_WRONLY, 0640);
						}
						if (fd_err < 0) {
							if (write(STDERR_FILENO, "problems opening error dump\n", 28)) {}
						}
						dup2(fd_err, STDERR_FILENO);
						close(fd_err);
					}

					execvp(p.we_wordv[0], p.we_wordv);
					_exit(EXIT_FAILURE);		// in case of error only
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
	exit(EXIT_SUCCESS);
}
