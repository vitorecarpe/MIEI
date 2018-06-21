#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <limits.h>

#define PIPE_NAME_MAX 9
#define NNODES 10

typedef int** Matrix;

typedef int* NodeIndiceTable; /* Tabela que para cada nodo contém o indíce da matriz. */
int nNodes = 0;				  /* Número de nodos atuais. */
int tSize = NNODES + 1; 	  /* Máximo identificador que a tabela suporta. */

typedef struct node {
	int id;
	char* pipeR;
	char* pipeW;
	pid_t pid;
	int fdW;				   /* Em que fd está aberto o pipeR para escrita. */
	int fdR;				   /* Em que fd está aberto o pipeW para leitura. */
	struct node *next;
} *Node;


/** \brief Função que cria um array(tabela) onde na posição de cada indíce(id) se
 *         encontra o respetivo indíce do id na matriz.
 *
 * @return						O array em questão
 */


NodeIndiceTable newNodeIndiceTable() {
	int i;
	int* table = (int*) malloc (tSize * sizeof(int));
	for (i = 0; i < tSize; i++)
		table[i] = -1;
	return table;
}


/** \brief Função que aumenta o tamanho do array(tabela) de conversão de ids em
 *		   indíces da matriz.
 *
 * @param	table 		A tabela de conversão.
 * @param	minRealloc 	O realocamento mínimo que é necessário efetuar.
 * @return				A nova tabela.
 */

NodeIndiceTable NodeIndiceTableRealloc(NodeIndiceTable table, int minRealloc) {
	int tNewSize, i;
	if (minRealloc == tSize + 1) {
		tNewSize = 1.5 * tSize;
		table = realloc(table, tNewSize * sizeof (int));
	}
	else { // se minRealloc > tSize + 1
		tNewSize = minRealloc;
		table = realloc(table, tNewSize * sizeof (int));
	}
	for (i = tSize; i < tNewSize; i++)
		table[i] = -1;
	tSize = tNewSize;
	return table;
}


/** \brief Função que cria a matriz das conexões entre nodos e inicializa-a a zeros.
 *
 * @return				A matriz em questão.
 */

Matrix newMatrix () {
	int **mat = (int **)calloc(NNODES , sizeof(int*));
	int i;
	for (i = 0; i < NNODES; i++) mat[i] = (int *)calloc(NNODES , sizeof(int));
	return mat;
}


/** \brief Função que aumenta o tamanho da matriz das conexões entre nodos.
 *
 * @param	mat 		A matriz a realocar.
 * @param	mSize   	O tamanho inicial(atual) da matriz.
 * @return				A nova matriz.
 */

Matrix matrixRealloc (Matrix mat, int* mSize) {
	int mNewSize = 1.5 * (*mSize), i, j;
	mat = realloc(mat , mNewSize * sizeof(int*));
	for (i = 0; i < *mSize; i++)
		mat[i] = realloc(mat[i], mNewSize * sizeof(int));
	for (i = *mSize; i < mNewSize; i++)
		mat[i] = calloc(mNewSize, sizeof(int));
	for (i = 0; i < *mSize; i++)
		for (j = *mSize; j < mNewSize; j++)
			mat[i][j] = 0;
	*mSize = mNewSize;
	return mat;
}


/** \brief Função que cria um novo nodo e o insere na lista dos nodos existentes.
 *
 * @param	id 			O id do nodo a criar.
 * @param	nodes   	O endereço para a lista ligada de nodos já existente.
 */

void newNode (int id, Node* nodes) {
	Node new = (Node) malloc (sizeof (struct node));
	new->id = id;
	new->pipeR = (char*) malloc(PIPE_NAME_MAX * sizeof(char));
	new->pipeW = (char*) malloc(PIPE_NAME_MAX * sizeof(char));
	new->pid = -1;
	new->fdR = -1;
	new->fdW = -1;
	sprintf(new->pipeR, "pipeR%d", id);
	sprintf(new->pipeW, "pipeW%d", id);
	new->next = *nodes;
	*nodes = new;
}


/** \brief Função que retorna o nodo com um certo id.
 *
 * @param	id 			O id do nodo a retornar.
 * @param	nodes   	A lista de nodos existentes.
 * @return				O nodo em questão.
 */

Node retrieveNode (int id, Node nodes) {
	while (nodes && nodes->id != id) nodes = nodes -> next;
	return nodes;
}


/** \brief Função que retorna o endereço do nodo com um certo id.
 *
 * @param	id 			O id do nodo a retornar.
 * @param	nodes   	O endereço para a lista ligada de nodos já existente.
 * @return				O endereço de nodo em questão.
 */

Node* retrieveNodeAdress (int id, Node* nodes) {
	while (*nodes && (*nodes)->id != id) nodes = &((*nodes) -> next);
	return nodes;
}


/** \brief Função que retorna o nome do fifo de leitura do nodo com um certo id.
 *
 * @param	nodes   	A lista de nodos existentes.
 * @param	id  		O id do nodo em questão.
 * @return				O nome atribuido ao fifo de leitura.
 */

char* getpipeR (Node nodes, int id) {
	for (; nodes && nodes -> id != id; nodes = nodes -> next);
	if (nodes) return (nodes -> pipeR);
	else return NULL;
}


/** \brief Função que retorna o nome do fifo de escrita do nodo com um certo id.
 *
 * @param	nodes   	A lista de nodos existentes.
 * @param	id  		O id do nodo em questão.
 * @return				O nome atribuido ao fifo de escrita.
 */

char* getpipeW(Node nodes, int id) {
	for (; nodes && nodes -> id != id; nodes = nodes -> next);
	if (nodes) return (nodes -> pipeW);
	else return NULL;
}


/** \brief Função que retorna uma linha lida pelo descritor de ficheiro fildes.
 *
 * @param	fildes   	O descritor de ficheiro de onde ler.
 * @param	buf  		O buffer para onde vai ser escrito a linha.
 * @param	nbyte  		O número de bytes máximo a ler.
 * @return				O numero de caractéres lido.
 */

int readln(int fildes, char *buf, int nbyte) {
	char c;
	int r;
	int i = 0;
	while (i < nbyte - 1 && (r = read(fildes, &c, 1)) == 1 && c != '\n') buf[i++] = c;
	if (r == -1) {
		perror("Erro na leitura do caracter!");
		_exit(-1);
	}
	if (r != 0) buf[i++] = '\n';
	return i;
}


/** \brief Função que divide uma linha pelos seus espaços.
 *
 * @param	str 	   	A linha a dividir.
 * @param	res  		Array de Strings para adicionar as palavras.
 * @param	n   		O número de inicial de palavras que "res" suporta.
 */

void split(char* str, char** res, int n) {
	int i;
	int j = 0;
	res[j++] = str;
	for (i = 0; str[i]; i++) {
		if (str[i] == ' ') {
			str[i] = '\0';
			if (j == n) {res = realloc(res, (n + 5) * sizeof (char*)); n = n + 5;}
			res[j++] = &(str[i + 1]);
		}
	}
	res[j] = NULL;
}


/** \brief Função altera o nome do comando <cmd> para "./<cmd>".
 *		   caso seja este um dos comandos componentes do trabalho.
 *
 * @param	args 	   	Array de strings que possui o comando.
 */

void stringToProgram(char** args) {
	if (strcmp(args[0], "window") == 0
	        || strcmp(args[0], "filter") == 0
	        || strcmp(args[0], "const") == 0
	        || strcmp(args[0], "spawn") == 0) {
		char* cmd = (char*) malloc (sizeof(args[0]) + 2);
		sprintf(cmd, "./%s", args[0]);
		args[0] = cmd;
	}
}


/** \brief Função responsável por criar um novo nodo (2 fifos e 1 processo).
 *
 * @param	args 	   	Array de Strings com id e informação do processo a criar.
 * @param	nodes  		O endereço para a lista ligada de nodos existente.
 */

void _node(char** args, Node* nodes) {
	int id = atoi(args[0]);
	int r, w;
	stringToProgram(args + 1);
	newNode(id, nodes);
	mkfifo((*nodes)->pipeR, 0666);
	mkfifo((*nodes)->pipeW, 0666);
	pid_t pid = fork();

	if (pid == 0) {
		r = open((*nodes)->pipeR, O_RDONLY);
		if (r == -1) {perror("Erro na abertura do pipe de leitura!"); _exit(-1);}
		w = open((*nodes)->pipeW, O_WRONLY);
		if (w == -1) {perror("Erro na abertura do pipe de escrita!"); _exit(-1);}
		dup2(r, 0); close(r);
		dup2(w, 1); close(w);
		execvp(args[1], args + 1);
		_exit(-1);
	} else {
		w = open((*nodes)->pipeR, O_WRONLY);
		if (w == -1) {
			perror("Erro na abertura do pipe de escrita!");
			_exit(-1);
		}
		r = open((*nodes)->pipeW, O_RDONLY);
		if (r == -1) {
			perror("Erro na abertura do pipe de leitura!");
			_exit(-1);
		}
		(*nodes) -> fdR = r;
		(*nodes) -> fdW = w;
		if (*nodes && (*nodes) -> pid == -1) (*nodes) -> pid = pid;
	}
}


/** \brief Função que procura o nodo a que se refere um dado indíce da matriz.
 *
 * @param	iMatriz   	O indíce da matriz.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 * @return				O id em questão.
 */

int procuraIdIndice(int iMatriz, NodeIndiceTable table) {
	int i;
	for (i = 1; i < tSize; i++)
		if (table[i] == iMatriz) return i;
	return -1;
}


/** \brief Função que devolve o array de argumentos a passar ao processo de conexão.
 *
 * @param	conId   	O array de ids destino da conexão.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 * @return				O Array de strings em questão.
 */

char** arrayToConnectionExec(int* conId, NodeIndiceTable table) {
	int tamanho = 5, i, j = 0, value, lastPid = -1, nCon = 0, id;
	char** argv = (char**) malloc (tamanho * sizeof (char*));
	char* arg = (char*) malloc (5 * sizeof(char));
	strcpy(arg, "rdto");
	argv[j++] = arg;
	for (i = 0; i < nNodes; i++) {
		value = conId[i];
		if (value != 0) {
			nCon++;
			arg = (char*) malloc (5 * sizeof(char));
			id = procuraIdIndice(i, table);
			sprintf(arg, "%d", id);
			argv[j++] = arg;
			if (value != 1) lastPid = value;
		}
		if (j == tamanho - 2) {//para o null e para o lastpid
			argv = realloc(argv, tamanho * 1.5 * sizeof (char*));
			tamanho = tamanho * 1.5;
		}
	}
	arg = (char*) malloc (10 * sizeof(char));
	sprintf(arg, "%d", lastPid);
	argv[j++] = arg;
	argv[j] = NULL;
	if (nCon != 0) return argv;
	else {
		for (j == j - 1; j >= 0; j--) free(argv[j]);
		free(argv);
		return NULL;
	}
}


/** \brief Função responsável por criar uma conexão entre nodos.
 *
 * @param	args 	   	Array de Strings com ids origem e destinos.
 * @param	nodes  		A lista ligada de nodos existente.
 * @param	connections A matriz das conexões existentes.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 */

void _connect (char** args, Node nodes, Matrix connections, NodeIndiceTable table) {
	int i, nCon = 0;
	int idO = atoi(args[0]), idD;

	for (i = 1; args[i]; i++) {
		idD = atoi(args[i]);
		connections[table[idO]][table[idD]] = 1;
	}

	char** argv = arrayToConnectionExec(connections[table[idO]], table);

	pid_t pid = fork();
	if (pid == 0) {
		char* pipeR = getpipeW(nodes, idO);
		int r = open(pipeR, O_RDONLY);
		if (r == -1) {perror("Erro na abertura do pipe de leitura!"); _exit(-1);}
		dup2(r, 0); close(r);
		execvp("./rdto", argv);
		_exit(-1);
	} else {
		for (i = 0; i < nNodes; i++)
			if (connections[table[idO]][i] != 0)
				connections[table[idO]][i] = pid;
	}
}


/** \brief Função responsável por desconectar dois nodos.
 *
 * @param	args 	   	Array de Strings com ids a desconnectar.
 * @param	nodes  		A lista ligada de nodos existente.
 * @param	connections A matriz das conexões existentes.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 */

void _disconnect(char** args, Node nodes, Matrix connections, NodeIndiceTable table) {
	int i, nCon = 0, lastPid;
	int idO = atoi(args[0]), idD;

	idD = atoi(args[1]);
	lastPid = connections[table[idO]][table[idD]];
	connections[table[idO]][table[idD]] = 0;

	char** argv = arrayToConnectionExec(connections[table[idO]], table);

	if (argv != NULL) {
		pid_t pid = fork();
		if (pid == 0) {
			char* pipeR = getpipeW(nodes, idO);
			int r = open(pipeR, O_RDONLY);
			if (r == -1) {perror("Erro na abertura do pipe de leitura!"); _exit(-1);}
			dup2(r, 0); close(r);
			execvp("./rdto", argv);
			_exit(-1);
		} else {
			for (i = 0; i < nNodes; i++)
				if (connections[table[idO]][i] != 0) connections[table[idO]][i] = pid;
		}
	} else kill(lastPid, SIGTERM);
}


/** \brief Função que injeta num nodo o output de um programa passado como argumento.
 *
 * @param	args 	   	Array de Strings com id a injetar e comando a executar.
 * @param	nodes  		O endereço para a lista ligada de nodos existente.
 */

void _inject (char** args, Node nodes) {
	int id = atoi(args[0]), status;
	stringToProgram(args + 1);
	char* pipeW = getpipeR(nodes, id);
	if (pipeW) {
		if (!fork()) {
			int w = open(pipeW, O_WRONLY);
			if (w == -1) {
				perror("Erro na abertura do pipe de leitura!");
				_exit(-1);
			}
			dup2(w, 1); close(w);
			execvp(args[1], args + 1);
			_exit(-1);
		}
		else wait(&status);
	} else {
		perror("Nodo inexistente!");
		_exit(-1);
	}
}


/** \brief Função que altera a função(processo) de um nodo já existente.
 *
 * @param	args 	   	Array de Strings com id do nodo a alterar e novo comando.
 * @param	nodes  		A lista ligada de nodos existente.
 */

void _change(char** args, Node* nodes) {
	int r, w;
	int id = atoi(args[0]);
	stringToProgram(args + 1);
	Node node = retrieveNode (id, *nodes);
	if (node) {
		r = open(node -> pipeR, O_RDONLY);
		if (r == -1) {
			perror("Erro na abertura do pipe de leitura!");
			_exit(-1);
		}
		w = open(node -> pipeW, O_WRONLY);
		if (w == -1) {
			perror("Erro na abertura do pipe de escrita!");
			_exit(-1);
		}
		kill(node -> pid, SIGTERM);
		pid_t pid = fork();
		if (pid == 0) {
			dup2(r, 0); close(r);
			dup2(w, 1); close(w);
			execvp(args[1], args + 1);
			_exit(-1);
		} else {
			close(r);
			close(w);
			node->pid = pid;
		}
	}
}


/** \brief Função que remove todas conexões com origem num certo nodo.
 *
 * @param		conId 		Linha da matriz cujas conexões queremos remover.
 */

void killConnectionsOut(int* conId) {
	int lastPid = -1, i, value;
	for (i = 0; i < nNodes; i++) {
		value = conId[i];
		if (value != 0) {
			if (value != 1) lastPid = value;
			conId[i] = 0;
		}
	}
	if (lastPid != -1) kill(lastPid, SIGTERM);
}


/** \brief Função que remove todas as conexões que tem um dado nodo como destino.
 *
 * @param	id  	    O id do nodo em questão.
 * @param	nodes  		A lista ligada de nodos existente.
 * @param	connections A matriz das conexões existentes.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 */

void disconnectConNode(int id, Node nodes, Matrix connections, NodeIndiceTable table) {
	int i, value, idInd;
	char** argv = (char**) malloc (3 * sizeof (char*)); //3 -{idO idD NULL}
	char* arg = (char*) malloc (5 * sizeof(char));
	sprintf(arg, "%d", id);
	argv[1] = arg;
	argv[2] = NULL;
	for (i = 0; i < nNodes; i++) {
		idInd = table[id];
		value = connections[i][idInd];
		if (value != 0) {
			arg = (char*) malloc (5 * sizeof(char));
			sprintf(arg, "%d", procuraIdIndice(i, table));
			argv[0] = arg;
			_disconnect (argv, nodes, connections, table);
			free(arg);
		}
	}
}


/** \brief Função que remove uma coluna e linha de um certo indice da matiz.
 *
 * @param	indice  	O indice em questão.
 * @param	nodes  		A matriz atual.
 * @param	mSize 		O tamanho atual da matriz.
 */

void removeMatrixRowCol(int indice, Matrix connections, int* mSize) {
	// Remover a coluna.
	int mCurrSize = *mSize;
	int mNewSize = mCurrSize - 1;
	int rowCol = indice, i;
	*mSize = mNewSize;
	for (i = 0; i < mCurrSize; i++) {
		while (rowCol < mNewSize) {
			// Move data to the left.
			connections[i][rowCol] = connections[i][rowCol + 1];
			rowCol++;
		}
		rowCol = indice;
	}
	// Remover a linha.
	rowCol = indice;
	free(connections[rowCol]);
	while (rowCol < mNewSize) {
		// Move data up.
		connections[rowCol] = connections[rowCol + 1];
		rowCol++;
	}
}


/** \brief Função que atualiza os indices da tabela de conversão de ids
 *		   em indíces da matriz após ter sido removido um nodo.
 *
 * @param	id  		O id do nodo removido.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 */

void correctTable(int id, NodeIndiceTable table) {
	int indRemovido = table[id], i;
	table[id] = -1;
	for (i = 0; i < tSize; i++)
		if (table[i] > indRemovido) (table[i])--;
}


/** \brief Função que remove um nodo, atualizando matriz e tabela.
 *
 * @param	args 	   	Array de Strings com o id do nodo a remover.
 * @param	nodes  		O endereço para a lista ligada de nodos existente.
 * @param	connections A matriz das conexões existentes.
 * @param	mSize	    O tamanho atual da matriz.
 * @param	table  		A tabela de conversão de ids em indices da matriz.
 */


void _remove(char** args, Node* nodes, Matrix connections, int* mSize, NodeIndiceTable table) {
	int r, w, id = atoi(args[0]);
	Node* node = retrieveNodeAdress (id, nodes);
	if (*node) {
		killConnectionsOut(connections[table[id]]);
		disconnectConNode(id, *nodes, connections, table);
		removeMatrixRowCol(table[id], connections, mSize);
		correctTable(id, table);
		kill((*node)->pid, SIGTERM);
		remove((*node)->pipeR);
		remove((*node)->pipeW);
		free((*node)->pipeR);
		free((*node)->pipeW);
		*node = (*node)->next;
		nNodes--;
	}
}


/** \brief Função que lê uma linha de um ficheiro ou stdin e
 *	 	   executa um dos comandos do controlador do trabalho.
 *
 * @param	argc 	   	O número de argumentos em "argv".
 * @param	argv 	   	O comando do controlador em questão.
 */

void _controller(int argc, char** argv) {
	int mSize = NNODES;
	Matrix connections = newMatrix();
	Node nodes = NULL;
	NodeIndiceTable table = newNodeIndiceTable();

	int nr, r;
	char line[PIPE_BUF];
	char** res = (char**) malloc (10 * sizeof (char*));
	if (argc) {
		r = open(argv[0], O_RDONLY);
		if (r == -1) {
			perror("Erro na abertura do ficheiro de configuração!");
			argc--;
		}
	}

	while ((argc && (nr = readln(r, line, PIPE_BUF)) > 0) || (nr = readln(0, line, PIPE_BUF)) > 0) {
		line[nr - 1] = '\0';
		split(line, res, 10);
		if (strcmp(res[0], "node") == 0) {
			int id = atoi(res[1]);
			if (id == mSize) connections = matrixRealloc (connections, &mSize);
			if (id >= tSize) table = NodeIndiceTableRealloc (table , id + 1);
			table[id] = nNodes;
			nNodes++;
			_node(res + 1, &nodes);
		}
		else if (strcmp(res[0], "connect") == 0) _connect(res + 1, nodes, connections, table);
		else if (strcmp(res[0], "disconnect") == 0) _disconnect(res + 1, nodes, connections, table);
		else if (strcmp(res[0], "inject") == 0) _inject(res + 1, nodes);
		else if (strcmp(res[0], "remove") == 0) _remove(res + 1, &nodes, connections, &mSize, table);
		else if (strcmp(res[0], "change") == 0) _change(res + 1, &nodes);
		else if (strcmp(res[0], "showSt") == 0) {
			int i, j;
			for (i = 0; i < nNodes; i++) {
				for (j = 0; j < nNodes; j++) {
					printf("%d ", connections[i][j]);
				}
				printf("\n");
			}
		}
	}
}


/** \brief Função principal que invoca o controlador.
 *
 * @param	argc 	   	O número de argumentos em passados ao programa.
 * @param	argv 	   	Argumentos passados ao programa.
 */

void main(int argc, char** argv) {
	int o = open("log.txt", O_WRONLY | O_TRUNC | O_CREAT, 0666);
	dup2(o, 2); close(o);
	if (argc != 1 && argc != 2) {
		perror ("Erro ao passar os argumentos!");
		_exit(-1);
	} else {
		if (argc == 2) _controller(1, argv + 1);
		else _controller (0, NULL);
	}
	_exit(0);
}