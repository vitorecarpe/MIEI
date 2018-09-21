%{
    #include <stdio.h>
%}

%token ERRO INT
%union{
    int vint;
}

%type <vint> INT Conteudo Lista

%%
SeqLista : Lista    {printf("soma = %d\n", $1);}
         | SeqLista Lista   {printf("soma = %d\n", $2);}
         ;

Lista : '[' ']'     {$$=0;}
      | '[' Conteudo ']'    {$$=$2;}
      ;

Conteudo : INT     {$$=$1;}
         | Conteudo ',' INT     {$$=$1+$3;}
         | Lista     {$$=$1;}
         | Conteudo ',' Lista     {$$=$1+$3;}
         ;

%%
int main(){
    yyparse();
    return 0;
}
int yyerror(char *erro){
    printf("%s\n", erro);
    return 0;
}