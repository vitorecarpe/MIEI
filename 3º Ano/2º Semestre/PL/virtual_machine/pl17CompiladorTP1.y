%{
int TABID[26];
int eNumber=1;
%}
%union {
	float val;
	char c;
	char* code;
	char* texto;
}
%token <val>NUM
%token <c>ID
%token <texto>STR
%token IF ELSE WHILE NE EQ
%type <code>atrib escrever ler cond ciclo exps econd exp termo fator insts inst

%%

prg: insts					{ printf("pushn 26\n start\n %s stop\n", $1); }
   ;

insts: inst  				{ $$ = $1; }
	 | insts ';' inst 		{ asprintf(&$$, "%s %s", $1, $3); }
	 ;

inst: atrib					{ $$ = $1; }
	| escrever				{ $$ = $1; }
	| ler					{ $$ = $1; }
	| cond					{ $$ = $1; }
	| ciclo					{ $$ = $1; }
	;


atrib: ID '=' exp 		{ asprintf(&$$, "%s storeg %d\n", $3, TABID[$1-'a']);}
	 ;

exp: termo 						{ $$ = $1; }
   | exp '+' termo 				{ asprintf(&$$, "%s %s fadd\n", $1, $3); }
   | exp '-' termo				{ asprintf(&$$, "%s %s fsub\n", $1, $3); }
   ;

termo: fator					{ $$ = $1; }
	 | termo '*' fator			{ asprintf(&$$, "%s %s fmul\n", $1, $3); }
	 | termo '/' fator			{ asprintf(&$$, "%s %s fdiv\n", $1, $3); }
	 ;

fator: NUM						{ asprintf(&$$, "pushf %f\n", $1); }
	 | ID 						{ asprintf(&$$, "pushg %d\n", TABID[$1 - 'a']);}
	 | '(' exp ')'				{ $$ = $2; }
	 ;

escrever: exp '!'				{ asprintf(&$$, "%s writef\n", $1); }
		| STR '!'				{ asprintf(&$$, "pushs %s\n writes\n", $1); }
		;

ler: ID '?'						{ asprintf(&$$, "read\n atof\n storeg %d\n ", TABID[$1 - 'a']); }
   ;

cond : IF '(' cond ')' THEN '{' insts '}' ELSE '{' insts '}' {
            asprintf(&$$, "%s jz ELSE%d\n %s jump ENDIF%d\n ELSE%d: nop\n %s ENDIF%d: nop\n",
                          $3, eNumber, $7, eNumber, eNumber, $11, eNumber);  
            eNumber++; }
	 ;
ciclo: WHILE '(' econd ')' inst {
            asprintf(&$$, "INICIOW%d: nop\n %s jz FIMW%d\n %s jump INICIOW%d\n FIMW%d: nop\n",
                     eNumber, $3, eNumber, $5, eNumber, eNumber);
            eNumber++; }
     ;
     
econd :  exp NE exp      { asprintf(&$$, "%s  %s equal\n not\n", $1, $3); }
      |  exp EQ exp      { asprintf(&$$, "%s  %s equal\n", $1, $3); } // Faltam os outros 4 Opers Relacionais
      |  exp             { $$ = $1; }
      ;

%%

#include "lex.yy.c"

void yyerror(char* s) {
	printf("%s", s);
} 

int main() {
	for(int i = 0; i < 26; i++)
		TABID[i] = i;

	yyparse();

	return 0;
}
