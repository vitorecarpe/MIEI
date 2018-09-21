%{
    #include "y.tab.h"
    #include <math.h>
%}



%%

[ \t]         ;
[\n()+\-*/\^?!=]  return *yytext;

[0-9]+(\.[0-9]+)?   {yylval.num = atof(yytext);
                     return NUM;}
[a-z]               {yylval.id = *yytext;
                     return ID;}

log     {yylval.f=log; return FUNC;}
sin     {yylval.f=sin; return FUNC;}
cos     {yylval.f=cos; return FUNC;}
exp     {yylval.f=exp; return FUNC;}

.                   return ERRO;
