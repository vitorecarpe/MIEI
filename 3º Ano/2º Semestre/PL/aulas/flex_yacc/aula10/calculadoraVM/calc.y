%{
#include <stdio.h>
#include <math.h>
extern int yylex();
extern char *yytext;
extern int yylineno;
void yyerror(char *);

int tab[26] = {0};
int contaCiclos = 0;
%}

%token NUM ID ERRO FUNC SIN COS REPEAT INT PRINT 
%token WHILE AND OR EQ NEQ GT LT GE LE 
%union{
    int i;
    int num;
    char id;
    double (*f)(double);
}
%type <i> INT 
%type <id> ID
%type <num> NUM Factor Termo Exp 
%type <f> FUNC SIN COS

%%
Maq : { printf("\tpushn 26\n");
        printf("\tpushn 10\n");
        printf("start\n"); }
        Calc
      { printf("fim: stop"); }

Calc : Calc Comando '\n'
     |
     ;
    
Comando : Escrita
        | Leitura
        | Atrib
        | Repeat
        | While
        ;

While   : WHILE  '(' Cond ')' '{' Calc '}' ;

Cond    : Cond OR Cond2
        | Cond2
        ;

Cond2   : Cond2 AND Cond3
        | Cond3
        ;

Cond3   : '!' Cond
        | '(' Cond ')'
        | CondRel
        ;

CondRel : Exp
        | Exp EQ Exp
        | Exp NEQ Exp
        | Exp GT Exp
        | Exp LT Exp
        | Exp GE Exp
        | Exp LE Exp
        ;

Repeat  : REPEAT '(' Exp ')' 
         { contaCiclos++;
           printf("\tstoreg %d\n", 25+contaCiclos);
           printf("ciclo%d:\n", contaCiclos);
           printf("\tpushg %d\n", 25+contaCiclos);
           printf("\tjz fciclo%d\n", contaCiclos);
         } 
         '{' Calc '}'
         { printf("\tpushg %d\n", 25+contaCiclos); 
           printf("\tpushi 1\n");
           printf("\tsub\n");
           printf("\tstoreg %d\n", 25+contaCiclos);
           printf("\tjump ciclo%d\n", contaCiclos);
           printf("\tfciclo%d\n", contaCiclos);
         }
        ;

Escrita : PRINT Exp {printf("\twritei\n");
                    };

Leitura : '?' ID    {printf("\tread\n");
                     printf("\tatoi\n");
                     printf("\tstoreg %d\n", $2-'a');
                    };

Atrib : ID '=' Exp  {tab[$1-'a'] = $3;
                     printf("\tstoreg %d\n", $1-'a');
                    };

Exp : Exp '+' Termo {$$ = $1+$3; printf("\tadd\n");}
    | Exp '-' Termo {$$ = $1-$3; printf("\tsub\n");}
    | Termo         {$$ = $1;}
    ;

Termo : Termo '*' Factor    {$$ = $1 * $3; printf("\tmul\n");}
      | Termo '/' Factor    {if($3) {
                                $$ = $1/$3;
                                printf("\tdiv\n");
                             }
                             else {
                                 printf("\terr \"Divisao por 0...\"\n");
                                 printf("\tjump fim\n");
                             }
                            }
      | Factor              {$$ = $1;}
      ;

Factor : '(' Exp ')'        {$$ = $2;}
       | NUM                {$$ = $1; printf("\tpushi %d\n", $1);}
       | ID                 {$$ = tab[$1-'a']; printf("\tpushg %d\n", $1-'a');}
       | SIN '(' Exp ')'    {$$ = (*$1)($3); printf("\tfsin\n"); }
       | COS '(' Exp ')'    {$$ = (*$1)($3); printf("\tfcos\n"); }
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
