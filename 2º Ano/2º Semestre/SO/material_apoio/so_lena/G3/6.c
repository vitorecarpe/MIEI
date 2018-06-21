#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

/* 6. Implemente uma versão simplificada da função
 * system(). Ao contrário da função original, não
 * tente suportar qualquer tipo de redireccionamento,
 * ou composição/encadeamento de programas executáveis.
 * O único argumento deverá ser uma string que
 * especifica um programa executável e uma eventual
 * lista de argumentos. Procure que o comportamento
 * e valor de retorno da sua função sejam compatı́veis
 * com a original.
 */

int _system(char** command) {
	int i;
	char* aux = command[0];
	execvp(aux, command);
	exit(-1);
}


int main(int argc, char** argv) {
	_system(argv + 1);
}


/* The C library function int system(const char *command) passes the
 * command name or program name specified by command to the host
 * environment to be executed by the command processor and returns
 * after the command has been completed.
 * RETURN VALUE The value returned is -1 on error (e.g. fork(2)
 * failed), and the return status of the command otherwise. This
 * latter return status is in the format specified in wait(2).
 * Thus, the exit code of the command will be WEXITSTATUS(status).
 * In case /bin/sh could not be executed, the exit status will be
 * that of a command that does exit(127). If the value of command
 * is NULL, system() returns nonzero if the shell is available,
 * and zero if not.
 */