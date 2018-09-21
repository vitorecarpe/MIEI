%{	/*ESTE FICHEIRO É UM PARSER*/
#include <stdio.h>
#include <math.h>
void yyerror(char *);
int yylex();
float MEM[26];
%}

%union {
	float val;
	char c;
}
%token <val>NUM
%token <c>ID
%type <val>exp termo fator

%%
exps: ID '=' exp	{ MEM[$1 - 'a'] = $3; printf("%f\n",$3);}
	| exps ';' ID '=' exp	{MEM[$3 - 'a'] = $5; printf("%f\n",$5);}
	;	/*como é só 1 caracter não precisamos de aspas mas estamos a dizer que 
		  o símbolo é o código ASCII dele mesmo e que é um símbolo terminal. para dizer isto é que um ; ali em baixo*/

exp: termo	{$$ = $1;} /*basicamente $exp = $termo*/
   |exp '+' termo	{$$ = $1 +$3;}
   |exp '-' termo	{$$ = $1 + $3;}
   ;

termo: fator	{$$ = $1;}
	 | termo '*' fator	{$$ = $1 + $3;}
	 |termo '/' fator	{$$ = $1 + $3;}
	 ;

fator: NUM	{$$ = $1;}
	 | ID 	{$$ = MEM[$1 - 'a'];}
	 ;
%%

#include "lex.yy.c"
void yyerror(char* s) {
	printf("%s\n", s);
}

int main(){
    yyparse();	/* quando encontra uma situação de erro invoca a yyerror*/

    return 0;
}
