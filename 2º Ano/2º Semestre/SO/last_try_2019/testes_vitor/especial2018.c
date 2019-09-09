// EPOCA ESPECIAL 18 JULHO 2018

// GRUPO II

#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h> /* chamadas wait*() e macros relacionadas */
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>

int main(int argc, char** argv){

	pid_t pid;
	int n = 0;
	int i;
	int res;


	for(i=3; i<argc; i++){
		res=-1;
		if(n>atoi(argv[2])){
			wait(NULL);
			n--;
		}

		if(i!=3) sleep(argv[1]);
		n++;

		if((pid=fork())==0){
			while(res==-1)
				res = execlp(argv[1], argv[1], NULL);
		}
	}

	for(i=0; i<n; i++) wait(NULL);

	return 0;
}


int main(int argc, char** argv){

	pid_t pid, pid2;
	int n = 0;
	char bufout[256];
	char buferr[256];
	int procs[argc-1];

	for(int i=1; i<argc; i++){

		if((pid=fork())==0){
			int err[2], out[2];
			pipe(err);
			pipe(out);

			if((pid2=fork())==0){

				procs[i-1] = getpid();
				close(out[0]);
				dup2(out[1],1);
				close(out[1]);

				close(err[0]);
				dup2(err[1],2);
				close(err[1]);

				execlp(argv[i], argv[i], NULL);
			} else{
				close(err[1]);
				close(out[1]);

				while((n=readln(err[0], buferr, 256))>0)
					write(erros, buferr, n);
				close(err[0]);

				while((n=read(out[0], bufout, 1))>0){
					for(j=0; j<n; j++){
						if(bufout[j]  =='f' &&
					   	   bufout[j+1]=='i' &&
					   	   bufout[j+2]=='m' ){
							kill(procs[i-1],SIGKILL);
						}	
					}
				}
				close(out[0]);
			}
		}
	}

	for(int i=1; i<argc; i++){
		wait(NULL);
	}
}





























/*
int main(int argc, char **argv){
	int i, nproc=0;
	int ex;
	pid_t pid;


	for(i=3; i<argc; i++){
		ex=-1;
		if(nproc>atoi(argv[2])){
			wait(NULL);
			nproc--;
		}
		
		if(i!=3) sleep(atoi(argv[1]));

		nproc++;
		if((pid=fork())==0){
			while(ex==-1)
				ex = execlp(argv[i], argv[i], NULL);
			exit(i);
		}
		else{
			//wait(NULL); Um wait aqui quebra a concorrência...
		}
	}
	for(i=3; i<argc; i++){
		wait(NULL);
		nproc--;
	}

	return 0;
}*/

ssize_t readln(int fildes, void *buf, size_t nbyte){
	int n=0;
	ssize_t i=0;
	char c;
	char *buffer = (char *)buf;
	while(i<nbyte && (n=read(fildes,&c,1))>0 && c!='\n')
		buffer[i++]=c;

	(i<nbyte) ? (buffer[i] = '\0') : (buffer[i-1]='\0');

	return i;
}

int main(int argc, char **argv){
	int i;
	int n;
	int j;
	char buf[256];
	char buffer[256];
	int procs[argc-1];
	pid_t pid;
	int erros = open("erros.txt", O_CREAT | O_WRONLY, 0666);

	for(i=1; i<argc; i++){
		sleep(2);

		int perr[2];
		int pout[2];
		pipe(perr);
		pipe(pout);

		if((pid=fork())==0){

			procs[i-1]=getpid();

			close(perr[0]);
			close(pout[0]);

			dup2(perr[1],2);
			close(perr[1]);
			
			dup2(pout[1],1);
			close(pout[1]);

			execlp(argv[i], argv[i], NULL);
		} else{
			close(perr[1]);
			close(pout[1]);

			while((n=readln(perr[0], buf, 256))>0){
				write(erros, buf, n);
			}
			close(perr[0]);

			while((n=read(pout[0], buffer, 1))>0){
				for(j=0; j<n; j++){
					if(buffer[j]  =='f' &&
					   buffer[j+1]=='i' &&
					   buffer[j+2]=='m' ){
						kill(procs[i-1],SIGKILL);
					}	
				}
			}
			close(pout[0]);
		}
	}

	for(i=1; i<argc; i++){
		wait(NULL);
	}

	return 0;
}