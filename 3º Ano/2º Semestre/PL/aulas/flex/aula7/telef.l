%x sessao moeda
%{
    #include <stdlib.h>
    #include <string.h>
    float saldo = 0;
%}

%%
<*>[ \t\n]  ;

LEVANTAR  { printf("Cabine: Bom Dia!!! Pode introduzir moedas...\n");
            BEGIN sessao; }

.           ;

<sessao>POUSAR  { printf("Cabine: Saldo devolvido = %f\n", saldo);
                  saldo = 0;
                  BEGIN 0;
                }

<sessao>T=6[04]1[0-9]{6}    printf("Cabine: Chamadas para numeros vermelhos sao BARRADAS");

<sessao>T=00[0-9]{6,}       { if( saldo >= 1.5 ){
                                saldo -= 1.5;
                                printf("Cabine: Saldo Disponivel = %f\n", saldo);
                              }else
                                printf("Cabine: Saldo insuficiente, introduza mais moedas...\n");
                            }

<sessao>T=2[0-9]{8}       { if( saldo >= 0.25 ){
                                saldo -= 0.25;
                                printf("Cabine: Saldo Disponivel = %f\n", saldo);
                              }else
                                printf("Cabine: Saldo insuficiente, introduza mais moedas...\n");
                            }

<sessao>T=800[0-9]{6}       printf("Cabine: Chamada Gratuita. Saldo Disponivel = %f\n", saldo);

<sessao>T=808[0-9]{6}       { if( saldo >= 0.10 ){
                                saldo -= 0.10;
                                printf("Cabine: Saldo Disponivel = %f\n", saldo);
                              }else
                                printf("Cabine: Saldo insuficiente, introduza mais moedas...\n");
                            }

<sessao>MOEDA   BEGIN moeda;


<moeda>[125]0?c     { yytext[yyleng-1] = '\0';
                      saldo += atof(yytext)/100;
                    }
<moeda>(1|2)e       { yytext[yyleng-1] = '\0';
                      saldo += atof(yytext);
                    }
<moeda>[0-9]+(e|c)  printf("Cabine: Moeda invalida: %s\n", yytext);

<moeda>,            ;
<moeda>\.           { printf("Cabine: Saldo disponivel = %f\n", saldo) 
                      BEGIN sessao;
                    }
