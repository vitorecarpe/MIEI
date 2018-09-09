%{
char *eu, *ele;
%}
%%
^EU=.+ eu=strdup(yytext+3);
^ELE=.+ ele=strdup(yytext+4);
^(E|e)(U|u): printf("%s: ","eu");
^[Ee][Ll][Ee]: printf("%s: ",ele);
