#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>

void terminate(){
	printf("Execucao cancelada, com Ctrl+C\n");
	_exit(0);
}

int executa(char* cmd){
	char *args[10];
	int i=0;

	args[i] = strtok(cmd," \n");
	while(args[i] != NULL) {
		args[++i] = strtok(NULL," \n");
	}
	execvp(args[0],args);
	// only reaches this part if exec fails
	perror(">ERROR: exec() ");
	return -1;
}

int main(int argc, char** argv){
	signal(SIGINT, terminate);

	char c, ant1, ant2;
    int status, comment=0, fail=0;

	char str[50];
	char buffer[4096], pipebuffer[128];
	int lastOutStart=0, lastOutSize=0, lastOutEnd=0;

	int i=0, j=0;
	FILE *file;
	file = fopen(argv[1], "r");

	for(int intchar, ant2=ant1='n'; (intchar=fgetc(file)) != EOF; ant2=ant1, ant1=c){
        c = (char)intchar; // fgetc retorna ints, to fix add cast

		if(comment){
			if( ant2=='<' && ant1=='<' && c=='<' ){ 
				comment=0;
				fgetc(file); // retira o \n apos o <<<
			}
		}
		else{
			buffer[i++] = c; // guarda o caracter atual
			if( ant2=='>' && ant1=='>' && c=='>' ){
				comment=1;
				buffer[i-1]='\0';
				buffer[i-2]='\0';
				buffer[i-3]='\0';
				i-=3;
			}
			if( (ant1 == '$' && c == ' ') || 
				(ant2 == '$' && ant1 == '|' && c == ' ') ){
				fgets(str,50,file); // pega na restante linha
				for(j=0; str[j] != '\0'; j++){
					buffer[i++] = str[j];
				}

				int inputFilho[2];
				pipe(inputFilho);
				int filhoPai[2];
				pipe(filhoPai);
				int childpid = fork();
				if(childpid==0){ // child

					if( ant2 == '$' && ant1 == '|' && c == ' ' ){
						close(inputFilho[1]);				// close write end
						dup2(inputFilho[0], STDIN_FILENO);  // send pipe to stdin
						close(inputFilho[0]);				// no longer needed
					}

					close(filhoPai[0]);    				// close reading end in the child
					dup2(filhoPai[1], STDOUT_FILENO); 	// send stdout to the pipe
					// dup2(filhoPai[1], STDERR_FILENO);   // send stderr to the pipe
					close(filhoPai[1]);    				// this descriptor is no longer needed

					executa(str);
					// only reaches this part if exec fails
					exit(-1);
				}
				else{ // parent

					if( ant2 == '$' && ant1 == '|' && c == ' ' ){
						close(inputFilho[0]); 			// close the read end
						write(inputFilho[1], buffer+lastOutStart, lastOutSize);
						close(inputFilho[1]); 			// no longer needed
					}

					int filho = wait(&status); // espera por filhos que terminem
					// printf("#filho: %d # %d \n", filho, status);
					if(status) // status!=0 indica problemas no ultimo filho
						exit(-1); // returnar -1 para erro, 0 para nao dar erro.

					buffer[i++]='>';
					buffer[i++]='>';
					buffer[i++]='>';
					buffer[i++]='\n';
					lastOutStart=i;
					// printf("START %d \n",lastOutStart);
					close(filhoPai[1]);  // close the write end of the pipe in the parent
					for(int nread=0; (nread=read(filhoPai[0], pipebuffer, sizeof(pipebuffer))) != 0; ){
						for(j=0; j<nread; j++){
							buffer[i++]=pipebuffer[j];
						}
					}
					close(filhoPai[0]);
					lastOutEnd = i;
					lastOutSize = lastOutEnd-lastOutStart;
					// printf("END   %d \n", lastOutEnd);
					// printf("SIZE  %d \n", lastOutSize);
					buffer[i++]='<';
					buffer[i++]='<';
					buffer[i++]='<';
					buffer[i++]='\n';
				}
			}
		}
	}
	fclose(file);
	buffer[i]='\0';
	// puts(buffer);

	// NOTE nao é preciso o if pk eu ja termino a execuçao qd ha erro
	if( !fail ){ // caso tudo corra bem durante a execuçao o ficheiro sera atualizado
		file = fopen(argv[1], "w");
		fputs(buffer,file);
		return 0;
	}

	return -1;
}
