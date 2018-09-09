%option noyywrap
	/*ESTE FICHEIRO É UM ANALISADOR LÉXICO*/
%{

%}


%%
"+"	{return '+'; }
"-"	{return '-'; }
[*/;=]	{return yytext[0]; }	/**o + e o - tb podiam estar aqui. só fizemos de 2 maneiras para termos exemplos*/
("+"|"-")?[0-9]+("."[0-9]+)?	{	yylval.val = atof (yytext);
						return NUM;
					} /*um nº real pode começar por + ou -, opcionalmente pode ter um . ou , ou parte decimal */
[a-z]	{yylval.c = yytext[0]; return ID;}
.|\n 	{;}

%%