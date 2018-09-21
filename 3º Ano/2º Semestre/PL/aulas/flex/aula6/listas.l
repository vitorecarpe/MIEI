%{
#include "tokens.h"
%}

%%
[ \t\n]     ;
\[          return ABRE;
\]          return FECHA;
,           return VIRG;
[0-9]+      return INT;

.           return ERRO;
%%