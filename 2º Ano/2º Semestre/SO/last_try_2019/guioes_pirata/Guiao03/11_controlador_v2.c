/**
 * Programa controlador que execute concorrentemente um conjunto de programas
 * especificados como argumento da linha de comandos. O controlador deverá
 * re-executar cada programa enquanto não terminar com código de saída nulo.
 * No fim deverá imprimir o número de vezes que cada programa foi executado.
 * Os programas correm sem nenhum argumento próprio.
 *
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>			// fork(); _exit(); MACROS
#include <sys/wait.h>		// wait()
#include <string.h>			// strlen()
#include <stdio.h>			// sprintf()
#include <stdlib.h>			// malloc

/* WEXITSTATUS(status) only returns values between 0 and 255
 * Or as man page says, 8 least significant bits
 * 
 * Complete version. Result at the end will be correct since there's no point
 * in time where the _exit() returns more then 250 */

int main(int argc, char** argv)
{
	pid_t pids[argc - 1], pid = 1;
	int status, statusc, i, counter, flags_run[argc - 1], counters[argc], ret; 
	char snum[10];

	if (argc < 2) {
		if (write(STDERR_FILENO,"no program to execute",21)) {};
		return -1;
	}

	counters[0] = 0;
	for (i = 0; i < (argc - 1); i++) {
		flags_run[i] = 1;
		counters[i + 1] = 0;
	}

	while (counters[0] < (argc - 1)) {
		counter = 0;
		for (i = 0; (i < (argc - 1)); i++) {
			if (flags_run[i] == 1) {
				pid = fork();

				if (pid < 0) {
					if (write(STDERR_FILENO,"fork failed",11)) {};
					_exit(-1);
				}

				if (pid == 0) {
					// Child
					while (1) {     // infinite cycle
						pid = fork();
						if (pid < 0) {
							if (write(STDERR_FILENO,"childs fork failed",18)) {};
							_exit(-1);
						}
						if (pid == 0) {
							// Grandchild
							execlp(argv[i + 1], argv[i + 1], NULL);
							_exit(-1);
						} else {
							// Child
							wait(&statusc);
							if (WIFEXITED(statusc)) {
								if (WEXITSTATUS(statusc) == 0) {
									counter++;
									return (counter);
								} else {
									counter++;
									if (counter == 250) {
										return (250);
									}
								}
							}
						}
					}
				} else {
					//Parent
					pids[i] = pid;
				}
			}
		}

		for (i = 0; i < (argc - 1); i++) {
			waitpid(pids[i],&status,0);
			if (WIFEXITED(status)) {
				ret = WEXITSTATUS(status);
				if (ret >= 250) {
					counters[i+1] = counters[i+1] + 250;
					flags_run[i] = 1;
				} else {
					counters[i+1] = counters[i+1] + ret;
					counters[0] += 1;
					flags_run[i] = 0;
				}
			}
		}

		if (counters[0] >= (argc - 1)) {
			for (i = 0; i < (argc - 1); i++) {
				sprintf(snum, "%d\n",counters[i + 1]);
				if (write(STDOUT_FILENO, argv[i + 1], strlen(argv[i + 1]))) {};
				if (write(STDOUT_FILENO, "\t\t", 2)) {};
				if (write(STDOUT_FILENO, snum, strlen(snum))) {};
			}
		}
	}
	return 0;
}
