#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdlib.h>

int array[1000000];

/* Este programa con o numero de ocorrências num array que sejam diferentes de 0
 * A carga e distribuída por 10 processos filhos. Cada um deles calcula o 
 * número de "casas" diferentes de 0 e retorna esse valor ao pai. De notar 
 * que este mecanismo de comunicação apenas permite que um processo filho 
 * comunique a ocorrência de um máximo de 254. Cada processo filho pesquisa 
 * 100000 elementos do array.
 */

int main()
{
	int i,j,aux=0,total=0;
	int status;
	for(i=0;i<1000000;i++) array[i]=0;
	array[500]=1;
	array[100000]=1;
	array[200000]=1;
	array[300000]=1;
	array[400000]=1;
	array[500000]=1;
	array[600000]=1;
	array[600001]=6;

	for(i=0;i<10;i++)
   	{
      		switch(fork())
         	{
          		case 0:  for(j=i*100000;j<(i+1)*100000;j++)
                   	 		if(array[j]!=0) aux += array[j];
                   		 exit(aux);
          		case -1: perror("fork"); break;
          		default: wait(&status);
                   		 total = total+WEXITSTATUS(status);
                }
   	}
	
	printf("Total = %d\n",total);
	
	return 0;
}
