/**
 * Programa que executa o comando ls -l.
 * 
 * @author (Pirata)
 * @version (2018.06)
 */

#include <unistd.h>

int main()
{
	/* execl used because we know all the arguments and how many there are
	 * required to always have NULL as last argument */
	execlp("ls","ls","-l",NULL);

	/* code after the exec system calls are never executed
	 * process is terminated the moment exec is called */

	/* after the exec system call, normally only an exit is set up in case of error */
	_exit(-1);		// If the _exit() is executed then means that an error ocurred
}
