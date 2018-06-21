/**@file controller.c
 *
 * \brief Módulo responsável pelo controlador
 *
 * Este programa irá ler informação passada pelo
 * stdin ou por um ficheiro e irá criar uma rede
 * de nodos (stream processing).
 *
 * Cada nodo irá executar um comando sobre o
 * input recebido e irá enviar a sua saída
 * (ou não) para um processo 'connect', que por
 * sua vez irá passar esse resultado para um ou
 * mais nodos.
 */

#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <math.h>
#include <limits.h>
#include "stringProcessing.h"

/**
 * @struct Estrutura que contem toda a informação de um nodo
 */
typedef struct controllerNode{
	int id; ///< id do nodo
	char* cmd; ///< string com o comando a ser executado
	char* args; ///< string com os argumentos do comando
	int* in_ids; ///< ids de todas a ligações ao nó / enviam para o nodo
	int size_in; ///< tamanho de in_ids
	int used_in; ///< quantidade de in_ids
	int* out_ids; ///< ids a quem o nó liga / recebem do nodo
	int size_out; ///< tamanho de out_ids
	int used_out; ///< quantidade de out_ids
	char* pipeIn_name; ///< nome do pipe de entrada
	char* pipeOut_name; ///< nome do pipe de saída
	int f_in; ///< descritor do pipe de entrada
	int f_out; ///< descritor do pipe de saída
	int pid; ///< id do processo que cria o nó
	int connect_pid; ///< id do processo que faz a ligação da saída
}*ControllerNode;

/**
 * @struct Estrutura que contem todos os nodos
 */
typedef struct controllerInfo{
	int size; ///< tamanho da estrutura
	int used; ///< número de posições usadas
	int nnodes; ///< número de nodes até ao momento
	int ninjects; ///< número de injects até ao momento
	ControllerNode node; ///< array de nodos
}*ControllerInfo;

/**
 * Aloca memória num node
 * @param info Estrutura
 * @param i    Indice a alocar memória
 */
void alloc_node(ControllerInfo info, int i){
	info->node[i].id = -1;
	info->node[i].size_in = 5;
	info->node[i].size_out = 5;
	info->node[i].used_in = 0;
	info->node[i].used_out = 0;
	info->node[i].connect_pid = 0;
	info->node[i].in_ids = malloc (sizeof(int) * info->node[i].size_in);
	info->node[i].out_ids = malloc (sizeof(int) * info->node[i].size_out);
}

/**
 * \brief Inicializa um ControllerInfo
 * @param  size Tamanho inicial
 * @return Nova estrutra
 */
ControllerInfo initControllerInfo(int size){
	ControllerInfo info = malloc (sizeof(struct controllerInfo));
	info->size = size;
	info->used = 0;
	info->nnodes = 0;
	info->ninjects = 0;
	info->node = malloc (sizeof(struct controllerNode) * info->size);
	int i;
	for (i=0; i<size; i++)
		alloc_node(info, i);

	return info;
}

/**
 * \brief Realoca memória para a estrutra
 * @param info Estrutura
 */
void reallocControllerInfo(ControllerInfo info){
	int i;
	info->size *= 2;
	info->node = realloc(info->node, sizeof(struct controllerNode) * info->size);
	for (i=info->used; i<info->size; i++)
		alloc_node(info, i);
}

/**
 * \brief Realoca memória para o array de ids de um nodo
 * @param info    Estrutura
 * @param id      ID do nodo
 * @param id_name Se é para alocar a entrada ("in") ou a saída ("out")
 */
void reallocNodeIds(ControllerInfo info, int id, char *id_name){
	int* c;
	int *size;

	if (!strcmp(id_name, "in")){
		c = info->node[id].in_ids;
		info->node[id].size_in *= 2;
		size = &(info->node[id].size_in);
	}
	else {
		c = info->node->out_ids;
		info->node[id].size_out *= 2;
		size = &(info->node[id].size_out);
	}

	c = realloc(c, sizeof(int) * *size);
}

/**
 * \brief Retorna a posição de um ID na estrutura
 * @param  info Estrutura
 * @param  id   ID a procurar
 * @return      Posição do ID (-1 se não existir)
 */
int findID(ControllerInfo info, int id){
	int i;
	for (i=0; i<info->used; i++)
		if (info->node[i].id == id)
			return i;

	return -1;
}

/**
 * \brief Remove um determiado nodo da estrutura
 * @param info Estrutura
 * @param id   ID do nodo a remover
 */
void removeID(ControllerInfo info, int id){
	int i;
	for (i=0; i<info->used-1; i++)
		if (info->node[i].id == id){
			info->node[i] = info->node[i+1];
			id = info->node[i+1].id;
		}
	if (id == info->node[i].id) info->used--;
}

/**
 * \brief Procura se um determinado ID já liga a um nodo
 * @param  info  Estrutura
 * @param  id    ID do nodo
 * @param  value ID do nodo que liga a id
 * @param  c     Se o ID liga à entrada ("in") ou à saída ("out")
 * @return       Já existe um value a ligar ao id (true) ou não (false)
 */
int findValue(ControllerInfo info, int id, int value, char* c){
	int i, idx = findID(info, id);

	if (!strcmp(c, "in")){
		for (i=0; i<info->node[idx].used_in; i++)
			if (info->node[idx].in_ids[i] == value)
				return 1;
	}
	else{
		for (i=0; i<info->node[idx].used_out; i++)
			if (info->node[idx].out_ids[i] == value)
				return 1;
	}
	return 0;
}

/**
 * \brief Adiciona um ID à lista de ids de um nodo
 * @param info  Estrutura
 * @param id    ID do nodo
 * @param value ID do nodo a ligar a id
 * @param c     Se o ID liga à entrada ("in") ou à saída ("out")
 */
void addValue(ControllerInfo info, int id, int value, char* c){
	int idx = findID(info, id);
	if (!strcmp(c, "in")){
		if (!findValue(info, id, value, "in")){
			info->node[idx].in_ids[info->node[idx].used_in] = value;
			info->node[idx].used_in++;
			if (info->node[idx].used_in == info->node[idx].size_in)
				reallocNodeIds(info, idx, "in");
		}
	}
	else{
		if (!findValue(info, id, value, "out")){
			info->node[idx].out_ids[info->node[idx].used_out] = value;
			info->node[idx].used_out++;
			if (info->node[idx].used_out == info->node[idx].size_out)
				reallocNodeIds(info, idx, "out");
		}
	}
}

/**
 * \breif Remove um determinado ID (value) da lista de ligações de outro (ID)
 * @param info  Estrutura
 * @param id    ID do nodo
 * @param value ID a ser removido
 * @param c     Se é para remover uma entrada ("in") ou uma saída ("out")
 */
void removeValue(ControllerInfo info, int id, int value, char* c){
	int i, idx = findID(info, id);

	if (!strcmp(c, "in")){
		for (i=0; i<info->node[idx].used_in-1; i++)
			if (info->node[idx].in_ids[i] == value){
				info->node[idx].in_ids[i] = info->node[idx].in_ids[i+1];
				value = info->node[idx].in_ids[i+1];
			}
		if (value == info->node[idx].in_ids[i])
			info->node[idx].used_in--;
	}
	else{
		for (i=0; i<info->node[idx].used_out-1; i++)
			if (info->node[idx].out_ids[i] == value){
				info->node[idx].out_ids[i] = info->node[idx].out_ids[i+1];
				value = info->node[idx].out_ids[i+1];
			}
		if (value == info->node[idx].out_ids[i])
			info->node[idx].used_out--;
	}
}

/**
 * \brief Remove um id de um array e o respetivo descritor de ficheiro
 * @param out_ids Array com os ids
 * @param fildes  Array com os descritores
 * @param size    Tamanho dos arrays
 * @param id      ID a remover
 */
void removeFildes(int* out_ids, int* fildes, int size, int id){
	int i;
	for (i=0; i<size-1; i++)
		if (id == out_ids[i]){
			fildes[i] = fildes[i+1];
			id = out_ids[i+1];
		}
}

/**
 * Para todos os nodos de entrada de um determinado nodo
 * @param info Estrutura
 * @param id   ID do nodo
 */
void stop_proc(ControllerInfo info, int id){
	int i = 0, idx = findID(info, id);
	for (i=0; i < info->node[idx].used_out; i++)
		kill(info->node[findID(info, info->node[idx].out_ids[i])].pid, SIGSTOP);
}

/**
 * Continua todos os nodos de entrada de um determinado nodo
 * @param info Estrutura
 * @param id   ID do nodo
 */
void cont_proc(ControllerInfo info, int id){
	int i = 0, idx = findID(info, id);
	for (i=0; i < info->node[idx].used_out; i++)
		kill(info->node[findID(info, info->node[idx].out_ids[i])].pid, SIGCONT);
}

/** \brief Nome do pipe de saída */
static char* exec_pipe_out_name;
/** \brief ID do processo que executa o exec */
static int exec_child_pid;
/** Descritor do pipe de saída */
static int exec_f_out = -1;
/**
 * \brief Trata dos sinais recebidos pelo execNode
 * @param sig Sinal
 */
void nodeHandler(int sig){
	switch(sig){
		//Abrir o pipe de saída e começar a escrever para lá
		case SIGUSR1 :{
			exec_f_out = open(exec_pipe_out_name, O_WRONLY);
			if (exec_f_out != -1) 
				dup2(exec_f_out, 1);
			break;
		}
		//Terminar o exec e sair
		case SIGINT :{
			kill(exec_child_pid, SIGINT);
			break;
		}
		//Remover o nodo atual
		case SIGUSR2 :{
			raise(SIGINT);
			break;
		}
	}
}

/**
 * \brief Irá ser responsável por executar um comando de um nodo
 * @param info Estrutura
 * @param id   ID do nodo
 */
void execNode(ControllerInfo info, int id){
	signal(SIGUSR1, nodeHandler);
	signal(SIGINT, nodeHandler);
	signal(SIGUSR2, nodeHandler);
	int pd_out[2], charsRead, x, f_in;
	char buffer[PIPE_BUF];
	pipe(pd_out);
	exec_pipe_out_name = info->node[findID(info, id)].pipeOut_name;
	kill(getppid(), SIGUSR1);
	f_in = open(info->node[findID(info, id)].pipeIn_name, O_RDONLY);

	//exec command
	if ((x = fork()) == 0){
		dup2(pd_out[1], 1);
		close(pd_out[0]);
		dup2(f_in, 0);
		close(f_in);
		_exit(execlp(info->node[findID(info, id)].cmd, info->node[findID(info, id)].cmd, info->node[findID(info, id)].args, NULL));
	}
	else{
		close(pd_out[1]);
		exec_child_pid = x;
		while((charsRead = readline(pd_out[0], buffer, PIPE_BUF)) > 0)
			if (exec_f_out != -1)
				write(1, buffer, charsRead);
		_exit(0);
	}
}

/**
 * \brief Apenas servirá para quando um node é substituido, 
 * recebendo o sinal do novo node dizendo que pode remover o anterior (createNode)
 * ou para o connect sinalizar que recebeu e processou um bit (disconnect)
 * @param sig Sinal
 */
void default_handler(int sig){}

/**
 * \brief Cria um novo nodo (ou substitui um existente)
 * @param info Estrutura
 * @param c    Array com informação do nodo
 */
void createNode(ControllerInfo info, char** c){
	signal(SIGUSR1, default_handler);
	int nodeID = atoi(c[1]), new_connect = 0, old_pid, idx;
	
	idx = findID(info, nodeID);
	if(idx == -1) idx = info->used; 

	info->node[idx].cmd = addCommandPrefix(c[2]);
	info->node[idx].args = strcatWithSpaces(c+3);

	//Se o nodo já existe, irá substituir-lo com o novo comando
	if (idx != info->used){
		new_connect = 1;
		old_pid = info->node[idx].pid;
	}
	//Se o nodo não existe, cria um novo
	else{
		info->node[idx].id = nodeID;
		info->used++;
		info->node[idx].pipeIn_name = fifoName(info->nnodes, "in");
		info->node[idx].pipeOut_name = fifoName(info->nnodes, "out");
		info->nnodes++;
		mkfifo(info->node[idx].pipeIn_name, 0666);
	}

	int pd[2], x;
	pipe(pd);

	//exec node
	if ((x = fork()) == 0) execNode(info, nodeID);

	else{
		pause();
		open(info->node[idx].pipeIn_name, O_WRONLY);
		info->node[findID(info, nodeID)].pid = x;
		if (new_connect){
			kill(x, SIGUSR1);
			kill(old_pid, SIGINT);
		}
	}
}

/**
 * \brief Irá enviar um input para um nodo
 * @param info Estrutura
 * @param c    Array com o comando a executar
 */
void inject(ControllerInfo info, char** c){
	int nodeID = atoi(c[1]);
	char* cmd = addCommandPrefix(c[2]);
	char* args = strcatWithSpaces(c+3);

	if (!fork()){
		int f_node, f_input;
		char* inj_name = malloc(sizeof(char) * 20);
		snprintf(inj_name, 20, "inject_%d", info->ninjects);
		mkfifo(inj_name, 0666);

		//Executar um novo terminal com o programa que irá escrever para o inject
		if (!fork()){
			char *arg = malloc(sizeof(char) * 30);
			snprintf(arg, 30, "./client %s", inj_name);
			_exit(execlp("gnome-terminal", "gnome-terminal", "-e", arg, NULL));
		}
		
		f_node = open(info->node[findID(info, nodeID)].pipeIn_name, O_WRONLY);
		f_input = open(inj_name, O_RDONLY);
		dup2(f_input, 0);
		close(f_input);
		dup2(f_node, 1);
		close(f_node);
		_exit(execlp(cmd, cmd, args, NULL));
	}

	else info->ninjects++;
}

/** \brief Indica se o disconnect foi concluído */
static int disconnect_done = 0;
/** \brief Indica o número de interações já realizadas */
static int disconnect_iterations = 0;
/** \brief Indica o id até ao momento */
static int disconnect_id = 0;
/** \brief Array com os ids a fazer disconnect */
static int* disconnect_ids;
/** \brief Número de elementos em disconnect_ids */
static int disconnect_ids_size = 0;
/** \brief Indica se o connect está a escrever */
static int connect_working = 0;

/**
 * \brief Tratará dos sinais do connect (receberá os bits de disconnect)
 * @param sig Sinal
 */
void connect_handler(int sig){
	if (disconnect_done == 1){
		disconnect_done = 0;
		disconnect_iterations = 0;
		disconnect_id = 0;
	}

	switch(sig){
		//Bit 1
		case SIGUSR1 :{
			disconnect_id += pow(2, disconnect_iterations);
			disconnect_iterations ++;
			break;
		}
		//Bit 0
		case SIGUSR2 :{
			disconnect_iterations++;
			break;
		}
		//Done
		case SIGHUP :{
			disconnect_done = 1;
			disconnect_ids[disconnect_ids_size++] = disconnect_id;
			break;
		}
		//Sair
		case SIGINT :{
			if (connect_working == 0)
				_exit(0);
			else connect_working = -1;
		break;
		}
	}
	//Diz que pode receber um novo sinal
	kill(getppid(), SIGUSR1);
}

/**
 * \brief Irá ligar um nodo a um ou mais nodos
 * @param info Estrutura
 * @param c    Informação com as ligações
 */
void connect(ControllerInfo info, char** c){
	signal(SIGUSR1, connect_handler);
	signal(SIGUSR2, connect_handler);
	signal(SIGHUP, connect_handler);
	signal(SIGINT, connect_handler);
	disconnect_ids = malloc (sizeof(int) * info->used);
	int in = atoi(c[1]), i, x, j, in_idx;
	in_idx = findID(info, in);

	//Cria o pipe de onde irá ler
	mkfifo(info->node[in_idx].pipeOut_name, 0666);

	//Irá percorrer todos os nods de saída
	for (i=2; c[i]; i++){
		int out = atoi(c[i]);
		addValue(info, in, out, "out");
		addValue(info, out, in, "in");

		//Se não existe mais nenhum elemento a adicionar
		if (c[i+1] == NULL){

		char buffer[PIPE_BUF];
		int charsRead = 0;

		x = fork();
		if (!x){
			//Avisa o node para abrir o pipe de saída e abre o pipe para ler o seu output
			kill(info->node[in_idx].pid, SIGUSR1);
			int f_out = open(info->node[in_idx].pipeOut_name, O_RDONLY);
			
			//Abrirá todos os nodos que terá que ligar
			int fildes[info->node[in_idx].used_out];
			for (j=0; j<info->node[in_idx].used_out; j++){
				int f = info->node[in_idx].out_ids[j];
				fildes[j] = open(info->node[findID(info, f)].pipeIn_name, O_WRONLY);
			}

			//Terminará a ligação anterior, caso exista
			if (info->node[in_idx].connect_pid != 0)
				kill(info->node[in_idx].connect_pid, SIGINT);

			//Ler da saída do nodo
			while ((charsRead = readline(f_out, buffer, PIPE_BUF)) > 0){
				connect_working = 1;

				//Remover nodos da saída (disconnect)
				if (disconnect_done){
					for (j=0; j<disconnect_ids_size; j++){
						removeFildes(info->node[in_idx].out_ids, fildes, info->node[in_idx].used_out, disconnect_ids[j]);
						info->node[in_idx].used_out--;
					}
					disconnect_done = 0;
					disconnect_ids_size = 0;
				}

				//Escrever para os nodos que estejam ligados a este
				for(j=0; j<info->node[in_idx].used_out; j++)
					write(fildes[j], buffer, charsRead);
				memset(buffer, 0, charsRead);

				if (connect_working == -1) _exit(0);
				else connect_working = 0;
			}
			_exit(0);
		}
		info->node[in_idx].connect_pid = x;
		}
	}
}

/**
 * \brief Irá remover a ligação entre dois nós
 * @param info Estrutura
 * @param c    Array de strings com os ids
 */
void disconnect(ControllerInfo info, char** c){
	signal(SIGUSR1, default_handler);
	int id1, id2, aux;
	id1 = atoi(c[1]);
	id2 = atoi(c[2]);

	//Verifica se a ligação está trocada (id2 -> id1 em vez de id1 -> id2)
	if (!findValue(info, id1, id2, "out")){
		if (findValue(info, id2, id1, "out")){
			int aux = id1;
			id1 = id2;
			id2 = aux;
		}
		//A ligação não existe
		else return;
	}

	//Enviar os bits do id a desligar
	int id1_connect = info->node[findID(info, id1)].connect_pid;
	for (aux = id2; aux > 0; aux = aux >> 1){
		if (aux % 2) kill(id1_connect, SIGUSR1); //Bit 1
		else kill(id1_connect, SIGUSR2); //Bit 0
		pause(); //Fica à espera para enviar um novo bit
	}
	//Já enviou os bits todos
	kill(id1_connect, SIGHUP);

	removeValue(info, id1, id2, "out");
	removeValue(info, id2, id1, "in");
}

/**
 * \brief Remove um nó da rede
 * @param info Estrutura
 * @param c    Arary de strings com o nodo a remover
 */
void removeNode(ControllerInfo info, char** c){
	int id = atoi(c[1]), i, j, idx;
	idx = findID(info, id);

	if (info->node[idx].id != -1){
		//Remover id da lista de saída dos que enviam
		for (i=0; i<info->node[idx].used_in; i++)
			removeValue(info, info->node[idx].in_ids[i], id, "out");

		//Remover id da lista de entrada dos que recebem
		for (i=0; i<info->node[idx].used_out; i++)
			removeValue(info, info->node[idx].out_ids[i], id, "in");

		//Fazer connects restantes
		for (i=0; i<info->node[idx].used_in; i++)
			for (j=0; j<info->node[idx].used_out; j++){
				char* values = malloc (sizeof(char) * 30);
				snprintf(values, 30, "connect %d %d", info->node[idx].in_ids[i], info->node[idx].out_ids[j]);
				char** c = divideString(values, " ");
				connect(info, c);
			}
		
		//Diz ao node que pode parar o exec
		stop_proc(info, id);
		kill(info->node[findID(info, id)].pid, SIGINT);
		cont_proc(info, id);

		removeID(info, id);
	}
}

/**
 * \brief Termina o programa
 */
void quit(){
	system("pkill -f controller");
}

/**
 * \brief Identifica o comando lido e chama a função adqueada
 * @param buf  Buffer com o input
 * @param info Estrutura
 */
void readCommand(char *buf, ControllerInfo info){
	char **c = divideString(buf, " ");

	//node id cmd args
	if (!strcmp(c[0], "node"))
		createNode(info, c);

	//connect id1 id2
	if (!strcmp(c[0], "connect"))
		connect(info, c);

	//inject id1 cmd args
	if (!strcmp(c[0], "inject"))
		inject(info, c);

	//disconnect id1 id2
	if (!strcmp(c[0], "disconnect"))
		disconnect(info, c);

	//remove id
	if (!strcmp(c[0], "remove"))
		removeNode(info, c);

	//quit
	if (!strcmp(c[0], "quit\n"))
		quit(info, c);

	if (info->used == info->size)
		reallocControllerInfo(info);
}

/**
 * Função main de controller. Irá ler o input e passar a readCommand
 * @param  argc Número de argumentos
 * @param  argv Argumentos
 * @return  --
 */
int main(int argc, char *argv[]){
	int charsRead;
	char buf[PIPE_BUF];
	ControllerInfo info = initControllerInfo(50);

	//read file
	if (argc > 1){
		int f = open(argv[1], O_RDONLY);
		while ((charsRead = readline(f, buf, PIPE_BUF)) > 0){
			readCommand(buf, info);
			memset(buf, 0, charsRead);
		}
		close(f);
	}

	//read stdin
	while ((charsRead = readline(0, buf, PIPE_BUF)) > 0){
			readCommand(buf, info);
			memset(buf, 0, charsRead);
	}
	quit();
}