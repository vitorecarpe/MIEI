#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <limits.h>
#include <sys/wait.h>
#include <ctype.h>


/* 5. Escreva um programa que emule o funcionamento
 * do interpretador de comandos na execucão encadeada 
 * de grep -v ˆ# /etc/passwd | cut -f7 -d: | uniq |
 * wc -l.
 */

char** splitPipe(char buf[]) {
	int i = 0;
	char** cmds = (char**) calloc (10, sizeof (char*));
	char* p = strtok(buf, "|");
	while(p != NULL) {
		cmds[i++] = p;
		p = strtok(NULL, "|");
	}
	cmds[i] = NULL;
	return cmds;
}

void split(char* str, char** res, int n) {
	int i, j = 0;
	res[j++] = str;
	for (i = 0; str[i] != '\0'; i++) {
		if (str[i] == ' ') {
			str[i] = '\0';
			if (j == n) {
				res = realloc(res, (n + 5) * sizeof (char*));
				n += 5;
			}
			res[j++] = &(str[i + 1]);
		}
	}
	res[j++] = NULL;
}
void trim(char *str) {
	int i, begin = 0, end = strlen(str) - 1;

	while (isspace(str[begin])) begin++;

	while ((end >= begin) && isspace(str[end])) end--;

	for (i = begin; i <= end; i++)
		str[i - begin] = str[i];

	str[i - begin] = '\0';
}

int main(int argc, char ** argv) {
	int i;
	char cmd[] = "grep -v ˆ# /etc/passwd | cut -f7 -d: | uniq | wc -l";
	char** cmds = splitPipe(cmd);
	for (i = 0; cmds[i] != NULL; i++) {
		trim(cmds[i]);
	}
    for(i = 0; cmds[i+1] != NULL; i++) {
        int pd[2];
        pipe(pd);
        char** argv = malloc(sizeof(char*) * 10);
        char* cmd = cmds[i];
        split(cmd, argv, 10);
        
        if (!fork()) {
        	printf("aqui!");
        	close(pd[0]);
            dup2(pd[1], 1); close(pd[1]);
            cmd = argv[0];
            execvp(cmd, argv);
            printf("lala");
            exit(0);
        }
        // remap output from previous child to input
        close(pd[1]);
        dup2(pd[0], 0); close(pd[0]);
        
    }
    char** argp = malloc(sizeof(char*) * 10);
    char* po = cmds[i];
    split(po, argp, 10);
    char* com = argp[0];
    execvp(com, argp);
    exit(0);
}
/* uniq - report or omit repeated lines
 */