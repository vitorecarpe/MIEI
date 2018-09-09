%{
    #include <stdio.h>
    extern int yylex();
%}
%token ERRO

%%

P : '(' P ')' P
  |
  ;

%%
int yyerror(char *erro){
    printf("%s\n", erro);
    return 0;
}
int main(){
    yyparse();
    return 0;
}