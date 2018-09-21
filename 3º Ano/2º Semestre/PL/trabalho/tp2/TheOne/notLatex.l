%option noyywrap
%option stack
%x image
%s bulletlist numberlist diclist
%s bold italic underline strike formating

%{
FILE *out;
char *s[100];
char *scale;
%}

%%

"%%"              fprintf(out, "\\%%");
"%*"              fprintf(out, "*");
"%/"              fprintf(out, "\/");
"%#"              fprintf(out, "\\#");
"%_"              fprintf(out, "\\_");
"%~"              fprintf(out, "\\textasciitilde");
"%<"              fprintf(out, "\\textless");
"%>"              fprintf(out, "\\textgreater");
"%["              fprintf(out, "[");
"%]"              fprintf(out, "]");
%!.*             {fprintf(out, "%%%s", yytext+=2);}
%note\{.*\}      {fprintf(out, "\\footnote{%s}", yytext+=5);}
%newline         {fprintf(out, "\\\\");}
%newpar          {fprintf(out, "\\par");}
%newpage         {fprintf(out, "\\newpage");}

^######\*.+         fprintf(out, "\\subparagraph*{%s}", yytext+=7);
^#####\*.+          fprintf(out, "\\paragraph*{%s}", yytext+=6);
^####\*.+           fprintf(out, "\\subsubsection*{%s}", yytext+=5);
^###\*.+            fprintf(out, "\\subsection*{%s}", yytext+=4);
^##\*.+             fprintf(out, "\\section*{%s}", yytext+=3);
^#\*.+              fprintf(out, "\\chapter*{%s}", yytext+=2);
^######.+           fprintf(out, "\\subparagraph{%s}", yytext+=6);
^#####.+            fprintf(out, "\\paragraph{%s}", yytext+=5);
^####.+             fprintf(out, "\\subsubsection{%s}", yytext+=4);
^###.+              fprintf(out, "\\subsection{%s}", yytext+=3);
^##.+               fprintf(out, "\\section{%s}", yytext+=2);
^#.+                fprintf(out, "\\chapter{%s}", yytext+=1);

\*                  {fprintf(out, "\\textbf");yy_push_state(formating);}
\/                  {fprintf(out, "\\textit");yy_push_state(formating);}
_                   {fprintf(out, "\\underline");yy_push_state(formating);}
~                   {fprintf(out, "\\sout");yy_push_state(formating);}
<formating>\}       {fprintf(out, "}"); yy_pop_state();}

\>\>\*                  {fprintf(out, "\\begin{itemize}\n\\item ");yy_push_state(bulletlist);}
\>\>[0-9]               {fprintf(out, "\\begin{enumerate}\n\\item ");yy_push_state(numberlist);}
\>\>\[[^%\n]*\]         {fprintf(out, "\\begin{description}\n\\item%s ", yytext+=2);yy_push_state(diclist);}
<bulletlist>\>\*        fprintf(out, "\\item ");
<bulletlist>\<          {fprintf(out, "\\end{itemize}"); yy_pop_state();}
<numberlist>\>[0-9]     fprintf(out, "\\item ");
<numberlist>\<          {fprintf(out, "\\end{enumerate}"); yy_pop_state();}
<diclist>\>\[[^%\n]*\]  fprintf(out, "\\item%s ", ++yytext);
<diclist>\<             {fprintf(out, "\\end{description}"); yy_pop_state();}

\[([0-9]\.)?[0-9]\] {   yytext[yyleng-1]='\0';yytext++;
                        scale=yytext;
                        fprintf(out,"\\begin{figure}[h]\n");
                        fprintf(out,"\t\\centering\n");
                        yy_push_state(image);
                    }
<image>\[.*\]       {   yytext[yyleng-1]='\0';yytext++;
                        fprintf(out,"\t\\includegraphics[scale=%s]{%s}\n",scale,yytext);
                    }
<image>\(.*\)       {   yytext[yyleng-1]='\0';yytext++;
                        fprintf(out,"\t\\caption{%s}\n",yytext);
                        fprintf(out,"\\end{figure}\n");
                        yy_pop_state();
                    }
<image>[.\n]        {fprintf(out,"\\end{figure}\n");yy_pop_state();}


[\n\r\t]            fprintf(out, "%s", yytext);
.	                fprintf(out, "%s", yytext);


%%
void startDoc(){
    fprintf(out, "\n");
    fprintf(out, "\\documentclass{report}\n");
    fprintf(out, "\n");
    fprintf(out, "\\usepackage[utf8]{inputenc}\n");
    fprintf(out, "\\usepackage[portuges]{babel}\n");
    fprintf(out, "\\usepackage{graphicx}\n");
    fprintf(out, "\\graphicspath{{img/}}\n");
    fprintf(out, "\\usepackage[normalem]{ulem}\n");
    fprintf(out, "\\usepackage[left=3cm,top=4cm,right=2cm,bottom=3cm]{geometry}");
    fprintf(out, "\n");
    fprintf(out, "\\title{\\textbf{Mestrado Integrado em Engenharia Informática}\\\\\\textbf{Processamento de Linguagens}\\\\ 2º Trabalho Prático}\n");
    fprintf(out, "\\author{Francisco Oliveira (a78416)\\\\Raul Vilas Boas (a79617)\\\\Vitor Peixoto (a79175)}\n");
    fprintf(out, "\\date{\\today}\n");
    fprintf(out, "\n");
    fprintf(out, "\\begin{document}\n");
    fprintf(out, "\\maketitle\n");
    fprintf(out, "\\tableofcontents\n");
    fprintf(out, "\n");
}
void endDoc(){
    fprintf(out, "\n");
    fprintf(out, "\\end{document}\n");
}

int main(int argc, char *argv[] ) {
    if(argc==1){ /* sem argumentos recebe do stdin e envia para o stdout */
        out=stdout;
        yylex();
    }
    else{ /* com argumentos le o respetivo documento apresentado como argumento e envia output para um ficheiro *.tex */
        for(int i=1; i<argc; i++){ 
            sprintf(s,"./latex/%s.tex",argv[i]);
            out=fopen(s,"w");
            yyin=fopen(argv[i],"r");

            startDoc();
            yylex();
            endDoc();

            fclose(out);
        }
    }
}