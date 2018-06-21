#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

#define PIPE_NAME_MAX 9//pipeR[W]<id> max id 999;
#define PIPE_BUF 1024

typedef struct node{
	int id;
	char* pipeR;
	char* pipeW;
	pid_t pid;
	struct node *next;
} *Node;

typedef struct connection{
	int idO;//id de origem
	int idD;//id de destino
	pid_t pid;
	struct connection* next;
} *Connection;

void newNode (int id, Node* nodes){ // n lhe passo o pid porque só se faz fork dps de criar o nodo
	Node new = (Node) malloc (sizeof (struct node));
	new->id = id;
	new->pipeR = (char*) malloc(PIPE_NAME_MAX * sizeof(char));
	new->pipeW = (char*) malloc(PIPE_NAME_MAX * sizeof(char));
	new->pid = -1;
	sprintf(new->pipeR,"pipeR%d",id);
	sprintf(new->pipeW,"pipeW%d",id);
	new->next = *nodes;
	*nodes = new;
}

char* getpipeR (Node nodes, int idO){
	for(; nodes && nodes -> id != idO; nodes = nodes -> next);
	if (nodes) return (nodes -> pipeR);
	else return NULL;
}

char* getpipeW (Node nodes, int idD){
	for(; nodes && nodes -> id != idD; nodes = nodes -> next);
	if (nodes) return (nodes -> pipeW);
	else return NULL;
}

void newConnection (int idO, int idD , Connection* connections){ // n lhe passo o pid porque só se faz fork dps de criar a connection
	Connection new = (Connection) malloc (sizeof (struct connection));
	new->idO = idO;
	new->idD = idD;
	new->pid = -1;
	new->next = *connections;
	*connections = new;
}

int readln(char* buf, int* r, int* pos, char* line) {
	int i;
	if (*pos == 0) *r = read(0, buf, PIPE_BUF);
	for (i = 0; buf[*pos] != '\n'; (*pos)++, i++) {
		if (*pos == *r) {
			*r = read(0, buf, PIPE_BUF);
			*pos = 0;
		}
		line[i] = buf[*pos];
	}
	if ((*r) == -1) {
		perror("Erro na leitura!");
		_exit(-1);
	}
	(*pos)++; // A próxima vez que vier ler começa na pos a seguir ao \n.
	line[i++] = '\n';
	line[i] = '\0';
	return ((*r) == 0) ? 0 : i;
}

void split(char* str, char** res, int n) {
	int i;
	int j = 0;
	res[j++] = str;
	for (i = 0; str[i]; i++) {
		if (str[i] == ' ') {
			str[i] = '\0';
			if (j == n) {res = realloc(res, (n + 5) * sizeof (char*));n = n+5;}
			res[j++] = &(str[i + 1]);
		}
	}
	res[j] = NULL;
}

void _node (char** args, Node* nodes){
	int id = atoi(args[0]);
	int r,w;
	newNode(id, nodes);//cria um novo nodo e insere-o na posição inicial
	mkfifo((*nodes)->pipeR,0666);
	mkfifo((*nodes)->pipeW,0666);
	pid_t pid = fork();
	if(pid == 0){//se sou o filho
		r = open((*nodes)->pipeR,O_RDONLY);
		if (r == -1){perror("Erro na abertura do pipe de leitura!");_exit(-1);}
		w = open((*nodes)->pipeW,O_WRONLY);
		if (w == -1){perror("Erro na abertura do pipe de escrita!");_exit(-1);}
		dup2(r,0);close(r);
		dup2(w,1);close(w);
		execvp(args[1],args + 1);
		_exit(-1);
	} else {//se sou o pai
		w = open((*nodes)->pipeR,O_WRONLY);
		r = open((*nodes)->pipeW,O_RDONLY);
		if(*nodes && (*nodes)->pid == -1) (*nodes)->pid = pid;
	}
}

void _connect (char** args, Node nodes, Connection* connections){
	int i;
	int idO = atoi(args[0]), idD;

	for (i = 1; args[i]; i++){
		idD = atoi(args[i]);
		newConnection(idO, idD, connections);
		
		pid_t pid = fork();
		if(pid == 0){//se sou o filho
			char* pipeR = getpipeW(nodes,idO);//lidar com n existencia
			char* pipeW = getpipeR(nodes,idD);//lidar com n existencia
			printf("%s\n",pipeR );
			printf("%s\n",pipeW );
			int w = open(pipeW,O_WRONLY);
			int r = open(pipeR,O_RDONLY);
			dup2(r,0);close(r);
			dup2(w,1);close(w);
			execlp("./rdwr","rdwr",NULL);
			_exit(-1);
		} else {//se sou o pai
			if(*connections && (*connections)->pid == -1) (*connections)->pid = pid;
		}
	}
}

void _disconnect (char** args, Connection* con){
	int idO = atoi(args[0]);
	int idD = atoi(args[1]);
	for (; *con && (*con)->idO != idO && (*con)->idD != idD; con = &((*con)->next));
	if (*con){
		kill((*con)->pid, SIGKILL);
		*con = (*con)->next;
	}else{
		perror("Inexistência de Conexão!");
		_exit(-1);
	}
}
/*
int _disconnect (char** args, Connection connections){
	Connection* aux;
	int idO = atoi(args[0]);
	int idD = atoi(args[1]);
	for (aux = &connections; *aux && (*aux)->idO != idO && (*aux)->idD != idD; aux = &((*aux)->next));
	if (*aux){
		kill((*aux)->pid, SIGKILL);
		*aux = (*aux)->next;
	}else{
		perror("Inexistência de Conexão!");
		_exit(-1);
	}
	exit(0);
}*/

void _inject (char** args, Node nodes){
	int id = atoi(args[0]),status;
	char* pipeW = getpipeR(nodes, id);
	if (pipeW){
		if(!fork()){//se sou o filho
			int w = open(pipeW,O_WRONLY);
			dup2(w,1);close(w);
			execvp(args[1],args+1);
			_exit(-1);
		}
	}else{
		wait(&status);
		perror("Nodo Inexistente!");
		_exit(-1);
	}
}

/*
int _inject (char** args, Node nodes){
	int id = atoi(args[0]);
	char* pipeW = getpipeW(nodes, id);
	if (pipeW){
		int w = open(pipeW,O_WRONLY);
		dup2(w,1);close(w);
		if(!fork()){//se sou o filho
			execvp(args[1],args+1);
			_exit(-1);
		}
	}else{
		perror("Nodo Inexistente!");
		_exit(-1);
	}
	exit(0);
}*/

void _controller() {
	Node nodes = NULL;
	Connection connections = NULL;
	int i;
	int nr;//comprimento de cada linha lida
	int r;//quantos caractéres leu no ultimo read dentro do readln
	int pos = 0;//posição onde está a ler no buffer
	char buf [PIPE_BUF];
	char line [PIPE_BUF];
	char** res = (char**) malloc (10 * sizeof (char*));
	while ((nr = readln(buf, &r, &pos, line)) != 0){
		line[nr - 1] = '\0';
		split(line,res,10);
		printf("%s\n",res[0] );
		if (strcmp(res[0], "node") == 0) _node(res + 1, &nodes);
		else if (strcmp(res[0], "connect") == 0) _connect(res + 1, nodes, &connections);
		else if (strcmp(res[0], "disconnect") == 0) _disconnect(res + 1, &connections);
		else if (strcmp(res[0], "inject") == 0) _inject(res + 1, nodes);
	}
}

void main(int argc, char** argv) {
	if (argc != 1) {
		perror ("Erro ao passar os argumentos!");
		_exit(-1); // Eu acho que aqui devia ser 1, mas pronto.
	} else _controller();
	_exit(0);
}
