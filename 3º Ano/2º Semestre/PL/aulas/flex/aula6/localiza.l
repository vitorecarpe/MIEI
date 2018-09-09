%{
    int linha=1, coluna=1;
%}

%%
(\+|-)?[0-9]+   {
                    printf("Int(%s,l=%d,c=%d)\n", yytext, linha, coluna);
                    coluna += strlen(yytext);
                }

\n              { linha++; coluna=1; }

.               { coluna++; }
%%