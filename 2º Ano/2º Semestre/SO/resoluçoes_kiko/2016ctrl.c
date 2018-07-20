#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <sys/wait.h>
#include <sys/stat.h>

int ok;
pid_t pid, pidFilho;

void terminate(pid_t pid){
    printf("%d esteve ativo durante %d segundos!\n", pidFilho, ok*3);
    kill(pidFilho, SIGKILL);
    kill(getpid(), SIGKILL);
}

void exists(){

}

int readpipe(int filedes, char *buffer, size_t nbytes){
    int i = 0, r;
    while (i < nbytes && (r = read(filedes, &buffer[i], 1)) > 0);

    if (r == -1){
        perror("FALHA!\n");
        exit(-1);
    }
    buffer[i] = '\0';
    return i;
}

int main(int argc, char const *argv[]){

    for(int i=1; i<argc; i++){
        pid = fork();

        if (pid == 0){ // filho
            int batatas[2];
            pipe(batatas);

            pidFilho = fork();
            if( pidFilho==0 ){ // filho
                close(batatas[0]);
                dup2(batatas[1], STDOUT_FILENO);
                close(batatas[1]);
                // assumo que este programa a executar já
                // trata de enviar os "OK"
                execlp(argv[i], argv[i], NULL);
                exit(-1);
            }
            else{ // pai
                close(batatas[1]);
                char buffer[PIPE_BUF];
                int r;
                printf("me exists");
                while ( r=readpipe(batatas[0], buffer, PIPE_BUF) ){
                    if ( strcmp(buffer,"OK!")!=0 ){
                        printf("KILL TIME");
                        kill(pidFilho, terminate);
                    }
                    ok++;
                    break;
                }
            }
        }
    }
    
    
    return 0;
}

// resolucao da dropbox
// https://www.dropbox.com/sh/akiqghi79eo8ti8/AADkaflV9typBad9MkrF30iNa/2%C2%BA%20Ano/2%C2%BA%20Semestre/Sistemas%20Operativos/Testes%20e%20exames/Resolu%C3%A7%C3%B5es?dl=0&file_subpath=%2FTeste_2015-16_SO_LEI_Resolucao_Grupo_I_e_II%2FTeste_2015-16_SO_MiEI_Resolucao_Grupo_II.c&preview=Teste_2015-16_SO_LEI_Resolucao_Grupo_I_e_II.rar#include <unistd.h>
/*
#include <sys/types.h>
#include <sys/stat.h>

int main(int argc, char *argv[])
{
	int r,i;
	int pn = argc;
	int filhos[pn];
	int fd[pn][2];
	int time[pn][1];
	
	//start the pn process and redirect from stdout do pipe
	for (i=1; i<=pn; i++;)
	{
		r=fork();
		filhos[i] = getpid();
		pipe(fd[i]);
		if (r==0)
		{
			close(fd[i][0]);
			dup2(fd[i][1],1); //redirect stdout to pipe
			close(fd[i][1]);
			execlp(argv[i],argv[i],NULL);
		}
		else
		{
			//how to check every 3s to confirm its printing the "ok"?
			open(fd[i][0]);
			wait(status); //should be a while(1)?
			
			//check if its receiving the "ok" and kill the process if not
			kill(filhos[i],SIGUSR1);
			
			//print the time it took - how to now the time passed?
			printf("Process %s has terminated over %i seconds",argv[i],time[i]);
		}
	}
	
	//when all of them stops
	return 0;
}
*/