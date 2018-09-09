%{
    #include "y.tab.h"
%}

%%

[ \t\n]     ;
\(          return '(';
\)          return ')';
.           return ERRO;
