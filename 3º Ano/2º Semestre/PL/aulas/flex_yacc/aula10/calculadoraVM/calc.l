%{
    #include "y.tab.h"
    #include <math.h>
%}

%%

[ \t\n]         ;
[\n()+\-*/\^?!=]  return *yytext;

[0-9]+   {yylval.num = atoi(yytext);
                     return NUM;}
[a-z]               {yylval.id = *yytext;
                     return ID;}

log     {yylval.f=log; return FUNC;}
sin     {yylval.f=sin; return SIN;}
cos     {yylval.f=cos; return COS;}
exp     {yylval.f=exp; return FUNC;}

print   return PRINT;
repeat  return REPEAT;
while   return WHILE;
"&&"    return AND;
"||"    return OR;
"=="    return EQ;
"!="    return NEQ;
">"     return GT;
"<"     return LT;
">="    return GE;
"<="    return LE;

.       return ERRO;
