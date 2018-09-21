%option noyywrap

%x tokens
%{
    char* aux;
%}

%%

"%token"            {BEGIN tokens;}
<tokens>[\n]        {BEGIN 0;}
<tokens>[ ]         {}
<tokens>[A-Z]*      {printf("...    {return %s;}\n",yytext);}

"'"[^']"'"          {yytext[yyleng-1]='\0'; yytext++;
                    printf("%s    {return '%s';}\n",yytext,yytext);}

[\n\r\t]            {}
.                   {}

%%

int main(int argc, char *argv[] ) {
    yyin=fopen("q2c.test","r");
    yylex();
}