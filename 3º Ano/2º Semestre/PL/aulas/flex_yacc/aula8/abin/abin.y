%{
    #include <stdio.h>
%}

%token ERRO INT
%union{
    int vint;
}

%type <vint> INT ABin

%%
SeqABin : SeqABin ABin {printf("soma = %d\n", $2);}
        | ABin {printf("soma = %d\n", $1);}
        ;

ABin : '(' INT ABin ABin ')' {$$=$2+$3+$4;}
     | '(' ')' {$$=0;}
     ;
    
%%
int main(){
    yyparse();
    return 0;
}
int yyerror(char *erro){
    printf("%s\n",erro);
    return 0;
}