%x intag



%%

\<\/[a-z][a-z0-9_]*\>   { yytext[yyleng-1] = '\0';
                          printf(")%s\n",yytext+2);
                        }

\<[a-z][a-z0-9_]*       { printf("(%s\n",yytext+1);
                          BEGIN intag;
                        }

<intag>\>               BEGIN 0;
<intag>\/\>             BEGIN 0;

<intag>[a-z][a-z0-9_]*=\"[^"]+\"     printf("A %s\n",yytext);

[^<]+   printf("- %s\n",yytext);
%%