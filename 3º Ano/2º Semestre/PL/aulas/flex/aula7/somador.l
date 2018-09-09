%x soma
%{
    #include <stdlib.h>
    int total=0;
%}

%%
ON  BEGIN soma;

<soma>(\+|-)?[0-9]+     total += atoi(yytext);
<soma>OFF   BEGIN 0;

<*>=    printf("%d\n",total);

<*>.|\n     ;
