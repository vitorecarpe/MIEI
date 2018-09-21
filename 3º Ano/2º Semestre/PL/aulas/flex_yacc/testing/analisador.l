%x INT STR
%{
    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>

%}

%%

[\[\],:{}]      {return yytext[0];}
(\+|-)?[0-9]+   {yylval.i=atoi(yytext); return INT;}
\"([^\n"]|\\\")*\"  {yylval.s=strdup(yytext); return STR;}
[\n]            {}
.               {fprintf(stderr,"Caracter Desconheciado");}


