%{
    #include <stdio.h>
    #include <math.h>
    #include <string.h>
    extern int yylex();
    extern char *yytext;
    extern int yylineno;
    void yyerror(char *);
    char* myStrCat(char *s1, char *mid, char *s2);

%}

%token EMIGRANTE OBRA EVENTO FEZ PARTICIPOU WILD LIGA
%token GRAPH SUBGRAPH LABEL HREF TOOLTIP TEXT ERRO
%union{
    char* string;
}
%type <string> GRAPH SUBGRAPH EMIGRANTE OBRA EVENTO LABEL HREF TOOLTIP TEXT WILD LIGA
%type <string> Comands Comando Nodo AtribsN AtribN Arco AtribsA AtribA

%%
NotDot  : Graph
	    ;

Graph   : GRAPH '{' Comands '}'   {printf("%s{%s}",$1,$3);}
	    ;

Comands : Comands Comando   {$$=myStrCat($1,"",myStrCat("\t",$2,"\n"));}
	    |                   {$$="\n";}
	    ;

Comando : Nodo              {$$=$1;}
    	| Arco              {$$=$1;}
        | LABEL             {$$=$1;}
        | SUBGRAPH '{' Comands '}' 
            {$$=myStrCat("subgraph cluster_",$1+9,myStrCat("{",$3,"\t}"));}
	    ;

Nodo    : EMIGRANTE '[' AtribsN ']' 
            {$$=myStrCat($1," ",myStrCat("[shape=Mrecord penwidth=4",$3,"]"));}
    	| OBRA '[' AtribsN ']'   
            {$$=myStrCat($1," ",myStrCat("[shape=doublecircle",$3,"]"));}
    	| EVENTO '[' AtribsN ']' 
            {$$=myStrCat($1," ",myStrCat("[shape=Msquare",$3,"]"));}
    	| WILD '[' AtribsN ']'   
            {$$=myStrCat($1," ",myStrCat("[shape=circle",$3,"]"));}
	    ;

AtribsN : AtribsN AtribN    {$$=myStrCat($1," ",$2);}
	    |                   {$$="";}
	    ;

AtribN  : LABEL             {$1+=6; $$=myStrCat(myStrCat("label","=",$1)," ",myStrCat("tooltip","=",$1));}
	    | HREF              {$$=myStrCat($1," ","fontcolor=\"blue\"");}
        | TOOLTIP           {$$=$1;}
	    ;

Arco    : EMIGRANTE FEZ OBRA '[' AtribsA ']' 
            {$$=myStrCat(myStrCat($1," -> ",$3)," ",myStrCat("[style=bold",$5,"]"));}
	    | EMIGRANTE PARTICIPOU EVENTO '[' AtribsA ']' 
            {$$=myStrCat(myStrCat($1," -> ",$3)," ",myStrCat("[style=dashed",$5,"]"));}
	    | WILD LIGA WILD '[' AtribsA ']' 
            {$$=myStrCat(myStrCat($1," -> ",$3)," ",myStrCat("[",$5,"]"));}
	    ;

AtribsA : AtribsA AtribA    {$$=myStrCat($1," ",$2);}
	    |                   {$$="";}
	    ;

AtribA  : LABEL             {$$=$1;}
	    ;


%%

char* myStrCat(char *s1, char *mid, char *s2){
    char* s = malloc( sizeof(char)*(strlen(s1)+strlen(mid)+strlen(s2)+1) );
	strcat(s,s1); strcat(s,mid); strcat(s,s2);
    //free(s1);
    //free(s2);
	return s;
}

int main(){
    yyparse();
    return 0;
}
void yyerror(char *erro){
    fprintf(stderr, "%s, %s, %d\n", erro, yytext, yylineno);
}
