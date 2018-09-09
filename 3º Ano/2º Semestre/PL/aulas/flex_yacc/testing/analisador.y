
%token INT STR P
%union {int i; char *s;}
%type <i>INT
%type <s>STR
%type <s>P

%%

J: '[' Js ']'
 | '[' ']'
 | INT
 | STR
 | '{' Ps '}'
 | '{' '}'
;

Js: J
  | Js ';' J
;

Ps: P
  | P ',' Ps
;

%%

#include "lex.yy.c"

int main(int argc, char* argv[]){
    yyparse();
}

int yyerror(){
    printf("ERRO");
}