%{
#include <string.h>
char *eu, *ele;
%}
%%
^EU=.+ eu = strdup(yytext+3);
^ELE=+ ele = strdup(yytext+4);

^[eE][uU]: printf ("%s: ",eu);
^[eE][lL][eE]: printf("%s: ",ele);