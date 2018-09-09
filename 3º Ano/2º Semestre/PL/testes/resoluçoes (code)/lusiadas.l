%option noyywrap

%%


dia{2,3}         printf("BATATAS");


%%

int main(int argc, char *argv[] ) {
    yyin=fopen("lusiadas.txt","r");
    yylex();
}