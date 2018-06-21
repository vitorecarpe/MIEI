#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "cartas.h"


/** \brief Função principal
Função principal do programa que imprime os cabeçalhos necessários e outras imagens e depois
disso invoca a função que vai imprimir o código html para desenhar as cartas. */
int main () {
	printf("Content-Type: text/html; charset=utf-8\n\n");
	printf("<head><header><title>BIG 2</title></header>\n");
	printf("<style>\n");
	printf(".cardsDown:hover{-moz-transform: translate(0px, -10px); transition-duration:.3s;}");
	printf(".cardsUp:hover{-moz-transform: translate(0px, 10px); transition-duration:.3s;}");
    printf("</style>\n");
	printf("</head>");
	printf("<body>\n");
	srandom(time(NULL));
    printf("<svg height = \"629\" width = \"1200\">\n");
    printf("<image x = 70 y = 0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/botoes/background.png\" /></a>\n");
    printf("<image x = 470 y = 250 height = \"130\" width = \"236\" xlink:href = \"http://127.0.0.1/botoes/center.png\" /></a>\n");
/* Ler os valores passados à cgi que estão na variável ambiente e passá-los ao programa */
	parse(getenv("QUERY_STRING"));
	printf("</body>\n");
	
	return 0;
}


/*
		  * LOTS TO DO LIST *
	-> MAYBE JUST MAYBE , INTRODUZIR OS BOTS ´A IDEIA DE TENTAR JOGAR 2 OU 3 CARTAS
		A VER SE GANHAM MAIS UNS JOGUITOS PRA DAR APERTO
	-> TRATAR DE POLIR A AREA DE CONTAGEM DE SCORES PARA MELHOR REFLETIR A REALIDADE
	-> PARTIR O BOTAO DE ORDENAÇAO EM 2 COMO DEVIA SER
	-> 

*/