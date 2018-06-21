#include <unistd.h> 
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include "readln.h"
#include "auxiliar_funcoes.h"

int sum (int* coluna, int inicio, int fim){
	int sum = 0, i;

	for (i = inicio; i < fim; i++){
		sum += coluna[i];
	}
	return sum;
}

int min (int* coluna, int inicio, int fim){
	if (inicio == fim)
		return 0;
	
	else{
		int min = coluna[inicio], i;

		for (i = inicio+1; i < fim; i++){
			if (coluna[i] < min)
				min = coluna[i];	
		}
		return min;
	}
}

int max (int* coluna, int inicio, int fim){
	if (inicio == fim)
		return 0;
	
	else{
		int max = coluna[inicio], i;

		for (i = inicio+1; i < fim; i++){
			if (coluna[i] > max)
				max = coluna[i];	
		}
		return max;
	}
}

int avg (int* coluna, int inicio, int fim){
	int sum = 0, elem = 0, i;
	double med;

	for (i = inicio; i < fim; i++){
		sum += coluna[i];
		elem ++;	
	}

	if (elem != 0)
		med = sum/ elem;
	else 
		med = 0;

	return  (int) med;
}



int go2colum (char* linha, int* colum, int pos, int target){
	int c = 0, coluna = 0;
	char aux [25];
	//printf("%s\n", linha);
	while ((linha[c] != '\0') && (linha[c] != '\n') && (coluna != target)){

		if (linha[c] == ':'){
			coluna++;
		}
		c++;
	}

	if (coluna == target){
		int i = 0;

		while ((linha[c] != '\0') && (linha[c] != '\n') && (linha[c] != ':'))
			aux[i++] = linha[c++];

		aux[i] = '\0';
		colum[pos] = atoi (aux);
		//printf("%d pos: %d\n", colum[pos], pos);
		return 0;
	}

	else 
		return 1;

}

void add_colum(int coluna, char* linha){

	char aux[25];
	int i = 0, c = 0;

	sprintf(aux,"%d",coluna);

	while((linha[i] != '\n') && (linha[i] != '\0'))
		i++;

	linha[i++] = ':';

	while(aux[c] != '\0')
		linha[i++] = aux[c++]; 

}
int posicao (int linha, int n_elementos){

	int res = linha - n_elementos;

	if (res < 0) res = 0;
	return res;
}

void start_func(int* coluna, int tam, int func (int* coluna, int inicio, int fim), int n_elementos){
	int linha = 0, pos_a_passar;
	int res [tam], i;
	
	for (i = 0; i < tam; i++)
		res[i] = coluna[i];



	while (linha < tam){

		pos_a_passar = posicao (linha, n_elementos);

		res[linha] = func (coluna,pos_a_passar, linha);

		linha++;
	}

	for (i = 0; i < tam; i++)
		coluna[i] = res[i];

}


void clean_window (char** matriz, int linhas){
	int i;
	
	for (i = 0; i < linhas; i++){
		free(matriz[i]);
	}

	free(matriz);
}


char** mais_linhas (char** matriz, int linha, int tam){

	matriz = realloc(matriz, sizeof(char*) * (linha+1));

	matriz[linha] = malloc(sizeof (char*)*tam);

	for (int i = 0; i < tam; i++){
		matriz[linha][i] = 0;
	}

	return matriz;

}

int decideFuncao(char* aux){
	if((strcmp(aux, "avg")) == 0){
		return 1;
	}

	if((strcmp(aux, "max")) == 0){
		return 2;
	}

	if((strcmp(aux, "min")) == 0){
		return 3;
	}

	if((strcmp(aux, "sum")) == 0){
		return 4;
	}

	return 0;
}

int soma(int *arr, int n){
	int res = 0;
	for(int i = 0; i < n; i++){
		res += arr[i];
	}
	return res;
}

int maximo(int *arr, int n){
	if(n == 0){
		return 0;
	}

	int res = arr[0];
	for(int i = 1; i < n; i++){

		if(arr[i] > res){
			res = arr[i];
		}
	}

	return res;
}

int minimo(int *arr, int n){
	if(n == 0){
		return 0;
	}

	int res = arr[0];
	for(int i = 1; i < n; i++){

		if(arr[i] < res){
			res = arr[i];
		}
	}

	return res;
}

int media(int *arr, int n){

	int res = soma(arr, n);

	//printf("SOMA: %d\n", res);
	//printf("ELEM: %d\n", n);

	return(res / n);
}

int da_elemento_coluna(char* buf, int n, int c){
	int i, dp = 1;
	for(i = 0; (buf[i] != '\0') && (buf[i] != '\n') && (i < n) && (dp < c); i++){

		if(buf[i] == ':'){
			dp++;
		}
	}

	int res = 0;
	//i--;

	//printf("CHEGUEI\n");

	if(dp == c){
		char* nova = malloc(1);
			int tam = 1;

			for(i; (buf[i] != '\0') && (buf[i] != '\n') && (buf[i] != ':'); i++){
				nova = realloc(nova, tam + 1);
				//printf("%c\n", buf[i]);
				nova[tam - 1] = buf[i];
				tam++;
			}

		nova[tam-1] = '\0';
		//printf("%s\n", nova);

		res = atoi(nova);
		//printf("%d\n", res);

	}

	return res;
}

int executa_funcao(char* buf, int n, int col, int* arr, int num_elem, int elem_atual, int linhas, int funcao){
	
	int res = 0;

	if(linhas != 0){

		int max = (num_elem > linhas ? linhas : num_elem);

		switch(funcao){
			case 1: res = media(arr, max); break;
			case 2: res = maximo(arr, max); break;
			case 3: res = minimo(arr, max); break;
			case 4: res = soma(arr, max); break;
			default: res = 0;
		}
	}

	int a = da_elemento_coluna(buf, n, col);

	if(linhas >= num_elem){

		//printf("ELEMENTO A SAIR: %d\n", elem_atual);

		arr[elem_atual] = a;
	}

	else{
		arr[linhas] = a;
	}

	return res;
}

int main (int argc, char const *argv[]){
	
	if (argc != 4){
		printf("Nº de argumentos errado (!= 3) \n");
		return 1;
	}

	char buffer[PIPE_BUF];
	int n = 0, linhas = 0;

	char** matriz = malloc(sizeof(char**));

	int num_elem = atoi(argv[3]);

	int coluna = atoi(argv[1]);

	int array[num_elem];
	int elem_atual = 0;

	int funcao = decideFuncao(argv[2]);

	/*printf("Coluna: %d\n", coluna);
	printf("Linhas: %d\n", num_elem);
	printf("Func: %d\n", funcao);
	*/
	while ((n = readln(0, buffer, PIPE_BUF - 6)) > 0){

		int ad = executa_funcao(buffer, n, coluna, array, num_elem, elem_atual, linhas, funcao);

		if(linhas >= num_elem){
			elem_atual = (elem_atual + 1) % num_elem;
		}

		char adi[6];
		sprintf(adi, "%d", ad);

		n = acrescenta(buffer, n, adi);
		linhas++;
		write(1, buffer, n);

		
		/*matriz = mais_linhas (matriz, linhas, 25);
		strcpy (matriz[linhas], buffer);
		linhas ++;
		*/
	}

	/*int coluna [linhas];
	int coluna_necess = atoi (argv[1])-1;
	int controlo = 0;
	int i;
	
	/*for (int i = 0; i < linhas; i++){
		printf("Linha %d: %s", i, matriz[i]);
	}

	for (i = 0;((i < linhas) && (controlo =! 0)) ; i++)
		controlo = go2colum (matriz[i], coluna, i, coluna_necess);
	*/

	/* aplicar função desejada pelo utilizador (os resultados serão devolvidos no array coluna)*/
/*
	if (!strcmp(argv[2],"max"))
		start_func (coluna, linhas, max, atoi (argv[3]));
	if (!strcmp(argv[2],"min"))
		start_func (coluna, linhas, min, atoi (argv[3]));
	if (!strcmp(argv[2],"sum"))
		start_func (coluna, linhas, sum, atoi (argv[3]));
	if (!strcmp(argv[2],"avg"))
		start_func (coluna, linhas, avg, atoi (argv[3]));

	//for (i = 0; i < linhas; i++)
	//	printf("%d\n", coluna[i]);

	/*for (i = 0; i < linhas; i++){
		add_colum(coluna[i],matriz[i]);

	}


	for (i = 0; i < linhas; i++){
		printf("Linha %d: %s \n", i, matriz[i]);
	//	printf("%d \n", coluna[i]);

	}
	*/

//	clean_window(matriz, linhas);

	return 0;
}

