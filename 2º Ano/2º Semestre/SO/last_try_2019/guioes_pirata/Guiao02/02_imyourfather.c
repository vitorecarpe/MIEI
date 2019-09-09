/**
 * Programa que cria um processo filho. Ambos imprimem os próprios identificadores e dos pais.
 * Em casos normais o pai espera pelo filho executar antes dele executar.
 * O filho fica zombie quando já acabou de executar mas o pai ainda não fez wait().
 * No caso de o pai terminar primeiro, o filho fica orfao.
 * Filho orfao - $ ./basicfork -orphan
 * 
 * @author (Pirata)
 * @version (2018.05)
 */

#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char** argv)
{
	pid_t childpid;		// pid_t is the same as int
	int orphan = 0;

	/* Verifying if we want a zombie child or not */
	if ((argc > 1) && (!strcmp("-orphan",argv[1]))) {
		orphan = 1;
    }

	/* Trying to create a child */
	childpid = fork();

	if (childpid == -1) {
		perror("fork failed to make offspring");
		return -1;
	}

	if (childpid > 0) {
		// Father's working area
		/* wait() allows the father to wait for the son to finnish before proceeding
		 * this allows us to control that the son doesn't become a zombie process
		 */
		if (!orphan) {
			wait(NULL);
        }
		printf("I\'m the father with the process id: %d.\n", getpid());
		printf("I created a son with the pId: %d and I\'m son to the pId: %d.\n",childpid, getppid());
		/* Like before, you can verify the father's getppid() the same as the bash pid with "ps" */
	} else {	// childpid == 0
		// Son's working area
		/* sleep() allow us to stop the process for a time, this will allow us to delay the son
		 * long enough so that the father can finish working first and turn the son to a zombie
		 */
		if (orphan) {
			sleep(1);
        }
		printf("I\'m the son with the process id: %d.\n", getpid());
		/* Son's getpid() is the same as Father's childpid */
		printf("My father has the pId: %d.\n", getppid());
		/* Son's getppid() is the same as Father's getpid()
		 * Or 1 when the son is still working after father finnished */
	}
	return 0;
}
