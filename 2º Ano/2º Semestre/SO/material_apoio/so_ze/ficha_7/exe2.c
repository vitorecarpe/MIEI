#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

int vez = 0;
int numero;
int usados;
int *pid;
char buf[100];

void filhoMorreu(){
	int status;
	int filho = wait(&status);
	int i = 0, r = 0;
	
	if(!(!WIFEXITED(status) && !WIFSIGNALED(status))){
		while((i < numero) && !r){
			r = (pid[i] == filho);

			if(!r) {i++;}
		}

		if(r){

			if(i == vez){
				vez = (vez + 1) % numero;
			}

			pid[i] = -1;
			usados--;
		}
	}
}

void g(){
	if(usados > 0){
		if(pid[vez] != -1) kill(pid[vez], SIGSTOP);
		
		//vez = ((vez + 1) < numero ? (vez + 1) : 0);
		vez = (vez+1) % numero;

		/*sprintf(buf, "Vez: %d! Pid: %d!\n", vez, pid[vez]);

		write(1, buf, strlen(buf));
*/
		while(pid[vez] == -1){
			vez = (vez + 1) % numero;
		}



/*		sprintf(buf, "Vez: %d! Pid: %d!\n", vez, pid[vez]);

		write(1, buf, strlen(buf));
*/
		if(pid[vez] != -1) kill(pid[vez], SIGCONT);
		
		alarm(1);
	}

	else{
		_exit(0);
	}
}

int main(int argc, char **argv){
	int i = 0;
	int f;
	pid = malloc(sizeof(int)*(argc-1));

	for(i = 1; i < argc; i++){

		f = fork();

		if(f == 0){
			//printf("%s\n", argv[i]);

			//printf("Filho %d: pid %d\n", i, getpid());

			kill(getpid(), SIGSTOP);


			execlp(argv[i], argv[i], NULL);

			_exit(0);
		}

		else{
			pid[i-1] = f;
		}

	}


	vez = 0;
	numero = argc-1;
	//printf("Numero: %d\n", numero);
	usados = argc-1;

	/*for(i = 0; i < numero; i++){
		//printf("%d: pid do filho %d\n", i, pid[i]);
	}
*/
	signal(SIGCHLD, filhoMorreu);

	signal(SIGALRM, g);
	
	kill(pid[0], SIGCONT);
	
	alarm(1);

	while(1){
		pause();
		//alarm(1);
	
	}
}