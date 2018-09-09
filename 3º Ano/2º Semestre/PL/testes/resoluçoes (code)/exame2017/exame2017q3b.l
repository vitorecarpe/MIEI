%s tipo id value
%{
    #include "y.tab.h"
%}

%%

[ \t\r\n]           ;
[{},=]              return *yytext;

@[a-zA-Z]*          {yylval.string=strdup(yytext); BEGIN id; return NOME;}
<id>[a-zA-Z0-9]*    {yylval.string=strdup(yytext); BEGIN tipo; return ID;}
<tipo>[a-zA-Z]*     {yylval.string=strdup(yytext); BEGIN value; return TIPO;}
<value>\{.*\}       {yylval.string=strdup(yytext); BEGIN tipo; return VALUE;}
<value>\".*\"       {yylval.string=strdup(yytext); BEGIN tipo; return VALUE;}
<value>[^,=]*       {yylval.string=strdup(yytext); BEGIN tipo; return VALUE;}

.                   return ERRO;

%%

