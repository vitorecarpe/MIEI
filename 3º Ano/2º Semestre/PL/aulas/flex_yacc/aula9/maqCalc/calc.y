%{
#include <stdio.h>
#include <math.h>
extern int yylex();
extern char *yytext;
extern int yylineno;
void yyerror(char *);
double tab[26] = {0.0};
%}

%token NUM ID ERRO FUNC
%union{
    double num;
    char id;
    double (*f)(double);
}
%type <id> ID
%type <num> NUM Factor Termo Exp
%type <f> FUNC

%%
Calc : Calc Comando '\n'
     |
     ;
    
Comando : Escrita
        | Leitura
        | Atrib
        ;

Escrita : '!' Exp   {printf(">> %.2lf\n", $2);};

Leitura : '?' ID    {printf("Valor ");
                     scanf("%lf", tab+$2-'a');
                     printf("Guardei o valor %.2lf no registo %c\n", tab[$2-'a'], $2);};

Atrib : ID '=' Exp  {tab[$1-'a'] = $3;
                     printf("-- Guardei o valor %.2lf no registo %c \n", $3, $1);};

Exp : Exp '+' Termo {$$ = $1+$3;}
    | Exp '-' Termo {$$ = $1-$3;}
    | Termo         {$$ = $1;}
    ;

Termo : Termo '*' Factor    {$$ = $1 * $3;}
      | Termo '/' Factor    {if($3) $$ = $1/$3;
                             else printf("erro divisao por 0...\n");}
      | Factor              {$$ = $1;}
      ;

Factor : '(' Exp ')'    {$$ = $2;}
       | NUM            {$$ = $1;}
       | ID             {$$ = tab[$1-'a'];}
       | Factor '^' Factor  {$$ = pow($1,$3);}
       | FUNC '(' Exp ')'   {$$ = (*$1)($3);}
       ;

%%

int main(){
    yyparse();
    return 0;
}
void yyerror(char *erro){
    fprintf(stderr, "%s, %s, %d\n", erro, yytext, yylineno);
}
