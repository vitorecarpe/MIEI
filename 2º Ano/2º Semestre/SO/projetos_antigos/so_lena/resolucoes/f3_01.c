#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
/*
int execl(const char *path, const char *arg0, ..., NULL);
int execlp(const char *file, const char *arg0, ..., NULL);
int execv(const char *path, char *const argv[]);
int execvp(const char *file, char *const argv[]);

ls -l | wc -l
ls -l | wc -c

./f3_01 | wc -l
./f3_01 | wc -c

Para testar tem de dar igual

*/

int main (int argc, char* argv []){
	//execl("/bin/ls","ls","-l",NULL);
	//execv(argv[1],argv+1); //./f3_01 /bin/ls -l
	execvp(argv[1],argv+1);
	_exit(-1);
}