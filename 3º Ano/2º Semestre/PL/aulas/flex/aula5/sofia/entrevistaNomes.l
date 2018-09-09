%{
#include <string.h>
char *eu, *ele;
%}
%%
^(E|e)(U|u)=.+ eu=strdup(yytext+3);
^[Ee][Ll][Ee]=.+ ele=strdup(yytext+4);
^(E|e)(U|u): printf("%s: ",eu);
^[Ee][Ll][Ee]: printf("%s: ",ele);
