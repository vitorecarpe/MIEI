#include <string.h>
int acrescenta(char* buf, int n, char* novo){
	buf[n-1] = ':';
	strcat(buf, novo);
	int res = n + strlen(novo);
	buf[res] = '\n';
	buf[res+1] = '\0';

	return(res+1);
}
