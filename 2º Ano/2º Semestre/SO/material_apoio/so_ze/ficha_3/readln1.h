#define MAXELEM 128

static char aux[1024];

static int cont_lin = 0;
static int pos_i = 0;
static int pos_f = 1024;

ssize_t readln (int fildes, char *buf);