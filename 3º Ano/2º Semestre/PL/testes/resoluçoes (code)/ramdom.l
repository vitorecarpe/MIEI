%option noyywrap

%%

(a|b|c)d        printf("!");
dia/DIA         printf("%s",yytext);
.               printf("?");

%%

int main(int argc, char *argv[] ) {
    yylex();
}