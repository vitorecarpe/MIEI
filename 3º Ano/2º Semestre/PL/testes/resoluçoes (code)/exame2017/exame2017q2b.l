%option noyywrap
%option stack
%x noreturn yesreturn

%{
    FILE *out;
    char* funcao="something";
%}

%%

void[ ].*\(.*\)         {fprintf(out,"%s",yytext); funcao=strdup(yytext+5); BEGIN noreturn;}
<noreturn>"{"           {fprintf(out,"%s\n",yytext);
                         fprintf(out,"Vai iniciar-se a execução da função %s, \n",funcao);}
<noreturn>"}"           {fprintf(out,"Vai terminar a execução da função, %s, (de tipo void) \n",funcao);
                         fprintf(out,"%s",yytext); BEGIN 0;}
<noreturn>.             {fprintf(out,"%s",yytext);}


.*[ ].*\(.*\)           {fprintf(out,"%s",yytext); funcao=strdup(yytext); BEGIN yesreturn;}
<yesreturn>"{"          {fprintf(out,"%s\n",yytext);
                         fprintf(out,"Vai iniciar-se a execução da função, %s \n",funcao);}
<yesreturn>return       {fprintf(out,"Vai terminar a execução da função, %s \n",funcao);
                         fprintf(out,"%s",yytext); BEGIN 0;}
<yesreturn>.            {fprintf(out,"%s",yytext);}



%%

int main(int argc, char *argv[] ) {
    yyin=fopen("exame2017q2b.txt","r");
    out=stdout;
    yylex();
    fclose(out);
}