typedef struct nodo *Nodo;

char* getNome(Nodo n);

void coneta_escrita_nodo(Nodo n, char* nome);

void coneta_leitura_nodo(Nodo n, char* nome);

Nodo cria_nodo(char *nome, char* funcao);

void executa_nodos(Nodo n);