%{
    #include <stdio.h>
    #include <string.h>

    extern int yylex();
    extern char *yytext;
    extern int yylineno;
    void yyerror(char *);
%}

%token NOME ID TIPO VALUE ERRO
%union{
    char* string;
}

%%

Exer    : Registo
        ;

Registo : NOME '{' ID ',' Campos '}'
        ;

Campos  : CampoR CampoF
        |
        ;

CampoR  : CampoR Campo
        |
        ;

Campo   : TIPO '=' VALUE ','
        ;
        
CampoF  : TIPO '=' VALUE
        ;

%%

int main(){
    yyparse();
    return 0;
}
void yyerror(char *erro){
    fprintf(stderr, "%s, %s, %d\n", erro, yytext, yylineno);
}
