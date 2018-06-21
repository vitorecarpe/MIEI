typedef struct buffer *Buffer;


Buffer init (void); // inicia e aloca espaco
int empty (Buffer); // testa se esta vazio
int add (Buffer, int); // acrescenta elemento
int next (Buffer, int *);// proximo a sair
int remove (Buffer, int *);// remove proximo
