%option noyywrap

%%

"+"		{ return '+'; }
"-"		{ return '-'; }
[*/;=()!?]	{ return yytext[0]; }
"=="		{ return EQ; }
"#="		{ return NE; }
[0-9]+("."[0-9]+)?	{
						yylval.val = atof(yytext);
						return NUM;
					}
[a-z]	{ 	
			yylval.c = yytext[0];
			return ID;
		}

\'[^']*\' { 
			yylval.texto = strdup(yytext);
			return STR;
		}

.|\n 	{ ; }

%%
