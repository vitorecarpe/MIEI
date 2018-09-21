%{
    #include "y.tab.h"
%}

%%
[ \t\n]     ;
\(          return '(';
\)          return ')';

(\+\-)?[0-9]+       { yylval.vint=atoi(yytext);
                      return INT; }

.           return ERRO;
