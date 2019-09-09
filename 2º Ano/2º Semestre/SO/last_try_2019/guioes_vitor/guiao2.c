#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>

/*
pid_t getpid(void);
pid_t getppid(void);
pid_t fork(void);
void _exit(int status);
pid_t wait(int *status);
pid_t waitpid(pid_t pid, int *status, int options);
int WIFEXITED(int status);
int WEXITSTATUS(int status);  */

//1
/*
int main(int argc, char** argv){
	pid_t pid;
	int status;
	if((pid=fork())==0){
		//FILHO
		printf("FILHO: %d\n", getpid());
		printf("PAI: %d\n", getppid());
	} else {
		//PAI
		wait(&status);
		printf("PAI: %d\n", getpid());
	}
}
*/

//2
/*
int main(int argc, char** argv){
	pid_t pid;
	int status;
	if((pid=fork())==0){
		//FILHO
		printf("PROCESSO FILHO\n");
		printf("FILHO: %d\n", getpid());
		printf("PAI: %d\n\n", getppid());
	} else {
		//PAI
		int spid = wait(&status);
		printf("PROCESSO PAI\n");
		printf("FILHO: %d\n", spid);
		printf("PAI: %d\n", getpid());
		printf("AVÔ: %d\n\n", getppid());
	}
}
*/

//3 SEQUENCIAL
/*
int main(int argc, char** argv){
	pid_t pid;
	int i=0;
	int status=0;
	while(i<10){
		if((pid=fork())==0){
			//FILHO
			printf("#%d\n", i);
			printf("PROCESSO FILHO\n");
			printf("FILHO: %d\n", getpid());
			printf("PAI: %d\n\n", getppid());
			_exit(i);
		} else {
			//PAI
			int spid = wait(&status);
			int id = WEXITSTATUS(status);
			printf("#%d\n", id);
			printf("PROCESSO PAI\n");
			printf("FILHO: %d\n", spid);
			printf("PAI: %d\n", getpid());
			printf("AVÔ: %d\n\n", getppid());
		}
		i++;
	}
}
*/

//4 EM CONCORRÊNCIA
/*
int main(int argc, char** argv){
	pid_t pid;
	int i=0;
	int status=0;
	while(i<10){
		if((pid=fork())==0){
			//FILHO
			printf("#%d\n", i);
			printf("PROCESSO FILHO\n");
			printf("FILHO: %d\n", getpid());
			printf("PAI: %d\n\n", getppid());
			sleep(5);								// ASKIKO - É NECESSARIO USAR O SLEEP OU HA OUTRA MANEIRA?
			_exit(i);
		}
		i++;
	}
	i=0;
	while(i<10){
		int spid = wait(&status);
		int id = WEXITSTATUS(status);
		printf("#%d\n", id);
		printf("PROCESSO PAI\n");
		printf("FILHO: %d\n", spid);
		printf("PAI: %d\n", getpid());
		printf("AVÔ: %d\n\n", getppid());
		i++;
	}
}
*/

//5
/*
int main(int argc, char** argv){
	pid_t pid;
	int i=0;
	int status=0;
	while(i<10 && (pid=fork())==0){
		//FILHO
		printf("#%d\n", i);
		printf("PROCESSO FILHO\n");
		printf("FILHO: %d\n", getpid());
		printf("PAI: %d\n\n", getppid());
		i++;
	}
	i=0;
	while(i<10){
		int spid = wait(&status);
		printf("PROCESSO PAI\n");
		printf("FILHO: %d\n", spid);
		printf("PAI: %d\n", getpid());
		printf("AVÔ: %d\n\n", getppid());
		_exit(i);
		i++;
	}
	return 0;
}
*/





























