%{
    #include "y.tab.h"
%}

TEXT    [a-zA-Z][a-zA-Z0-9\-\_]*
WORD    [a-zA-Z0-9]+

%%

[ \t\r]         ;
[\n]            yylineno++;
[{}\[\]]        return *yytext;

digraph         {yylval.string = strdup(yytext); return GRAPH;}
subgraph_{WORD} {yylval.string = strdup(yytext); return SUBGRAPH;}        

P_{TEXT}        {yylval.string = strdup(yytext); return EMIGRANTE;}
O_{TEXT}        {yylval.string = strdup(yytext); return OBRA;}
E_{TEXT}        {yylval.string = strdup(yytext); return EVENTO;}
W_{TEXT}        {yylval.string = strdup(yytext); return WILD;}
fez             return FEZ;
participou      return PARTICIPOU;
liga            return LIGA;

label=["][^"]*["]       {yylval.string = strdup(yytext); return LABEL;}
href=["][^"]*["]        {yylval.string = strdup(yytext); return HREF;}
tooltip=["][^"]*["]     {yylval.string = strdup(yytext); return TOOLTIP;}


.               return ERRO;
