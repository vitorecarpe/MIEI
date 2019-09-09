/** mysystem.c
 * Ficheiro implementação da função mysystem, representando uma versão simplificada da função system().
 * Ao contrário da função original não suporta redireccionamento ou composição/encadeamento
 * de programas executáveis.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <wordexp.h>		// used to pass string (char*) to string[] (char**)
#include <stdio.h>
#include "mysystem.h"

/* Structure wordexp_t (partially)
wordexp_t {
	size_t we_wordc = argc;
	char** we_wordv = argv;
}
*/

/* As said before, assuming no commands with pipes requested */
int mysystem(const char* command)
{
	wordexp_t p;
	int status;
	pid_t pid;

	if (!wordexp(command, &p, 0)) {
		pid = fork();

		if (pid < 0) {
			perror("system fork fail. If this was Windows would probably be a blue screen");
			_exit(-1);
		}

		if (pid == 0) {
			// Child
			execvp(p.we_wordv[0], p.we_wordv);
			_exit(-1);		// in case of error
		}

		wordfree(&p);

		waitpid(pid,&status,0);
		if (WIFEXITED(status)) {
			return WEXITSTATUS(status);
		}
	} else {
		perror("command incompatibility, learn to spell maybe");
	}
	return -1;
}
