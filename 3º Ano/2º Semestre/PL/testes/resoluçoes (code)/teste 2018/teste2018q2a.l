%option noyywrap
%x comentario

%{
    int num=0;
%}

%%

"/*"                {printf("%s",yytext); BEGIN comentario;}
<comentario>"*/"    {printf("%s",yytext); BEGIN 0;}
<comentario>.       {printf("%s",yytext);}


[0-9]{2}"..."[0-9]{2}[.]{3}[0-9]{2}\.\.\.[0-9]{2}  {printf("%s",yytext); printf(" #Nº: %d",++num);}
.                   {printf("%s",yytext);}

%%

int main(int argc, char *argv[] ) {
    yyin=fopen("teste2018q2a.txt","r");
    yylex();
    printf("    Nº matriculas -> %d\n",num);
}