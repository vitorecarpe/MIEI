/************************************************ >>  PROJETO BIG 2 (Parte 1)  << *************************************************
*																																  *
*                                       		          Grupo 75 -- PL7     												      *
*                                                                       													      *
*         (A78416) Vitor Peixoto                      (A78764) Ricardo Neves                      (A79175) Vitor Peixoto          *
*  																																  *
**********************************************************************************************************************************/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

/** URL da CGI */
#define SCRIPT		"http://127.0.0.1/cgi-bin/cartas"
#define BARALHO		"http://127.0.0.1/cards"

#define NAIPES		"DCHS"
#define VALORES		"3456789TJQKA2"

#define FORMATO "%lld_%lld_%lld_%lld_%lld_%lld_%lld_%lld_%d_%d_%d_%d_%d_%d_%d_%d_%d_%lld_%d_%d_%d_%d_%d"

typedef long long int MAO;
typedef struct estado
{
	MAO mao[4];
    MAO ultimajogada[4];
    int ncartasmao[4];
    int pontos[4];
    int ultimojogador;
	MAO selecao;
    int ncartasselecao,ncartascombo;
	int passar,jogar,selecionar;
} ESTADO;

ESTADO str2estado (char* str) {
	ESTADO e;
	sscanf(str, FORMATO,&e.mao[0],&e.mao[1],&e.mao[2],&e.mao[3],
                        &e.ultimajogada[0],&e.ultimajogada[1],&e.ultimajogada[2],&e.ultimajogada[3],
                        &e.ncartasmao[0],&e.ncartasmao[1],&e.ncartasmao[2],&e.ncartasmao[3],
                        &e.pontos[0],&e.pontos[1],&e.pontos[2],&e.pontos[3],
                        &e.ultimojogador,&e.selecao,&e.ncartasselecao,&e.ncartascombo,&e.passar,&e.jogar,&e.selecionar);
	return e;
}
char* estado2str (ESTADO e){
	static char res[10240];
	sprintf(res,FORMATO,e.mao[0],e.mao[1],e.mao[2],e.mao[3],
                        e.ultimajogada[0],e.ultimajogada[1],e.ultimajogada[2],e.ultimajogada[3],
                        e.ncartasmao[0],e.ncartasmao[1],e.ncartasmao[2],e.ncartasmao[3],
                        e.pontos[0],e.pontos[1],e.pontos[2],e.pontos[3],
                        e.ultimojogador,e.selecao,e.ncartasselecao,e.ncartascombo,e.passar,e.jogar,e.selecionar);
	return res;
}
/** \brief Devolve o índice da carta
@param naipe	O naipe da carta (inteiro entre 0 e 3)
@param valor	O valor da carta (inteiro entre 0 e 12)
@return		O índice correspondente à carta
*/
int indice(int naipe, int valor) {
	return naipe + valor*4;
}
/** \brief Adiciona uma carta ao estado
@param ESTADO	O estado atual
@param naipe	O naipe da carta (inteiro entre 0 e 3)
@param valor	O valor da carta (inteiro entre 0 e 12)
@return		O novo estado
*/
long long int add_carta(long long int ESTADO, int naipe, int valor) {
	int idx = indice(naipe, valor);
	return ESTADO | ((long long int) 1 << idx);
}
/** \brief Remove uma carta do estado
@param ESTADO	O estado atual
@param naipe	O naipe da carta (inteiro entre 0 e 3)
@param valor	O valor da carta (inteiro entre 0 e 12)
@return		O novo estado
*/
long long int rem_carta(long long int ESTADO, int naipe, int valor) {
	int idx = indice(naipe, valor);
	return ESTADO & ~((long long int) 1 << idx);
}
/** \brief Verifica se uma carta pertence ao estado
@param ESTADO	O estado atual
@param naipe	O naipe da carta (inteiro entre 0 e 3)
@param valor	O valor da carta (inteiro entre 0 e 12)
@return		1 se a carta existe e 0 caso contrário
*/
int carta_existe(long long int ESTADO, int naipe, int valor) {
	int idx = indice(naipe, valor);
	return (ESTADO >> idx) & 1;
}
/** \brief Imprime o html correspondente a uma carta
@param path	o URL correspondente à pasta que contém todas as cartas
@param x A coordenada x da carta
@param y A coordenada y da carta
@param ESTADO	O estado atual
@param naipe	O naipe da carta (inteiro entre 0 e 3)
@param valor	O valor da carta (inteiro entre 0 e 12)
*/

/*************************************************** >>  FUNÇÕES "IMPRIME"  << ****************************************************
*																																  *
*    -> "imprime_carta" - imprime as cartas do baralho do jogador (utilizador).           										  *
*    -> "imprime_carta_botx" - imprime as cartas do baralho dos bots (computador). Nestas cartas não é visivel o valor e naipe    *
*							   das mesmas, apenas a parte de trás. 																  *
*    -> "imprime_carta_naoclicavel" - imprime as cartas dos bots que serão apenas usadas quando as mesmas são jogadas. Nestas     *
*									  cartas é visível o valor e o naipe das mesmas.                                              *
*  									  																							  *
**********************************************************************************************************************************/

void imprime_carta(int x, int y, ESTADO e, int naipe, int valor) {
	char *suit = NAIPES;
	char *rank = VALORES;
	char script[10240];

	if(carta_existe(e.selecao,naipe,valor)){
		e.selecao = rem_carta(e.selecao, naipe, valor);
        e.ncartasselecao-=1;
    }
	else{
		e.selecao = add_carta(e.selecao, naipe, valor);
        e.ncartasselecao+=1;
    }

	sprintf(script, "%s?%s", SCRIPT,estado2str(e));
	printf("<a xlink:href = \"%s\"><image x = \"%d\" y = \"%d\" height = \"110\" width = \"80\" xlink:href = \"http://127.0.0.1/cards/%c%c.svg\" /></a>\n", script, x, y, rank[valor], suit[naipe]);
}
void imprime_carta_bot1(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"80\" width = \"110\" xlink:href = \"http://127.0.0.1/cards/jogador1.png\" />\n", x, y);
}
void imprime_carta_bot2(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"110\" width = \"80\" xlink:href = \"http://127.0.0.1/cards/jogador2.png\" />\n", x, y);
}
void imprime_carta_bot3(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"80\" width = \"110\" xlink:href = \"http://127.0.0.1/cards/jogador3.png\" />\n", x, y);
}
void imprime_carta_naoclicavel(int x,int y,int naipe,int valor){
    char *suit = NAIPES;
    char *rank = VALORES;
    printf("<image x = \"%d\" y = \"%d\" height = \"110\" width = \"80\" xlink:href = \"http://127.0.0.1/cards/%c%c.svg\" />\n", x, y, rank[valor], suit[naipe]);
}

int *combos(MAO cartas){
    int n,v;
    static int valores[13]={0};

    for(v = 0; v < 13; v++){
        for(n = 0; n < 4; n++){
            if(carta_existe(cartas,n,v))
                valores[v]+=1;        
        }
    }
    return valores;
}
int jogadaValida(MAO selecao,int ncartas){
    int i,n;
    int* combo;
    combo=combos(selecao);
    switch (ncartas){
        case 1:     
        return 1;
        break;
        case 2:
            for(n=0,i=0;i<13;i++){
                if(combo[i]!=0)
                    n+=1;
            }
            if(n==1)
                return 1;
            else
                return 0;
        break;
        case 3:
            for(i=0,n=0;i<13;i++){
                if(combo[i]!=0)
                    n+=1;
            }
            if(n==1)
                return 1;
            else
                return 0;
        break;
        case 5:     
        return 0;
        break;
        default:    
        return 0;
    }
}
int jogadaValidaMaior(MAO selecao,MAO ultimajogada,int ncartasselecao,int ncartascombo) {
    if(jogadaValida(selecao,ncartasselecao)){
        if(ncartasselecao==ncartascombo){
            if(selecao>ultimajogada)
                return 1;
            else
                return 0;
        }
        else
            return 0;
    }
    else
        return 0;
}

/*************************************************** >>  FUNÇÕES "BOTPLAY"  << ****************************************************
*																																  *
*    Funções que são as responsáveis para as ações e reações de cada bot ao desenrolar do jogo e das jogadas dos outros bots      *
*    															                    						                      *
*  									  																							  *
**********************************************************************************************************************************/

ESTADO botPlay1(ESTADO e) {
    int n,v,nn,vv;
    int i=0,j=0;
    MAO selecao=0;
    int* combo;
    combo=combos(e.mao[1]);

    if(e.ultimojogador==1){
        e.ncartascombo=1;
    }

    e.ultimajogada[1]=0;
    for(v = 0; v < 13; v++){
        for(n = 0; n < 4; n++){
            if(carta_existe(e.mao[1],n,v)){

                if(e.ncartascombo==1){
                    selecao=add_carta(selecao,n,v);
                    if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo) && i<1){
                        i++;
                        e.ultimajogada[1]=selecao;
                        e.mao[1]=rem_carta(e.mao[1],n,v);
                    }
                    else
                        selecao=rem_carta(selecao,n,v);
                }
                else{
                    if(combo[v]>=e.ncartascombo && i<e.ncartascombo){
                        selecao=add_carta(selecao,n,v);
                        i++;
                        if(i==e.ncartascombo){

                            if(selecao>e.ultimajogada[e.ultimojogador] && j==0){
                                j=1;
                                e.ultimajogada[1]=selecao;
                                for(vv = 0; vv < 13; vv++)
                                    for(nn = 0; nn < 4; nn++)
                                        if(carta_existe(selecao,nn,vv))
                                            e.mao[1]=rem_carta(e.mao[1],nn,vv);
                            }
                            else { selecao=0;i=0; }

                        }
                    }
                }

            }
        }
        if(e.ncartascombo!=1) { selecao=0;i=0; }
    }
    if(e.ultimajogada[1]!=0){
        e.ultimojogador=1;
        e.ncartasmao[1]-=e.ncartascombo;
    }
    return e;
}
ESTADO botPlay2(ESTADO e) {
    int n,v,nn,vv;
    int i=0,j=0;
    MAO selecao=0;
    int* combo;
    combo=combos(e.mao[2]);

    if(e.ultimojogador==2){
        e.ncartascombo=1;
    }

    e.ultimajogada[2]=0;
    for(v = 0; v < 13; v++){
        for(n = 0; n < 4; n++){
            if(carta_existe(e.mao[2],n,v)){

                if(e.ncartascombo==1){
                    selecao=add_carta(selecao,n,v);
                    if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo) && i==0){
                        i++;
                        e.ultimajogada[2]=selecao;
                        e.mao[2]=rem_carta(e.mao[2],n,v);
                    }
                    else
                        selecao=rem_carta(selecao,n,v);
                }
                else{
                    if(combo[v]>=e.ncartascombo && i<e.ncartascombo){
                        selecao=add_carta(selecao,n,v);
                        i++;
                        if(i==e.ncartascombo){

                            if(selecao>e.ultimajogada[e.ultimojogador] && j==0){
                                j=1;
                                e.ultimajogada[2]=selecao;
                                for(vv = 0; vv < 13; vv++)
                                    for(nn = 0; nn < 4; nn++)
                                        if(carta_existe(selecao,nn,vv))
                                            e.mao[2]=rem_carta(e.mao[2],nn,vv);
                            }
                            else { selecao=0;i=0; }
                        }
                    }
                }

            }
        }
        if(e.ncartascombo!=1) { selecao=0;i=0; }
    }
    if(e.ultimajogada[2]!=0){
        e.ultimojogador=2;
        e.ncartasmao[2]-=e.ncartascombo;
    }
    return e;
}
ESTADO botPlay3(ESTADO e) {
    int n,v,nn,vv;
    int i=0,j=0;
    MAO selecao=0;
    int* combo;
    combo=combos(e.mao[3]);

    if(e.ultimojogador==3){
        e.ncartascombo=1;
    }

    e.ultimajogada[3]=0;
    for(v = 0; v < 13; v++){
        for(n = 0; n < 4; n++){
            if(carta_existe(e.mao[3],n,v)){

                if(e.ncartascombo==1){
                    selecao=add_carta(selecao,n,v);
                    if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo) && i==0){
                        i++;
                        e.ultimajogada[3]=selecao;
                        e.mao[3]=rem_carta(e.mao[3],n,v);
                    }
                    else
                        selecao=rem_carta(selecao,n,v);
                }
                else{
                    if(combo[v]>=e.ncartascombo && i<e.ncartascombo){
                        selecao=add_carta(selecao,n,v);
                        i++;
                        if(i==e.ncartascombo){

                            if(selecao>e.ultimajogada[e.ultimojogador] && j==0){
                                j=1;
                                e.ultimajogada[3]=selecao;
                                for(vv = 0; vv < 13; vv++)
                                    for(nn = 0; nn < 4; nn++)
                                        if(carta_existe(selecao,nn,vv))
                                            e.mao[3]=rem_carta(e.mao[3],nn,vv);
                            }
                            else { selecao=0;i=0; }
                        }
                    }
                }

            }
        }
        if(e.ncartascombo!=1) { selecao=0;i=0; }
    }
    if(e.ultimajogada[3]!=0){
        e.ultimojogador=3;
        e.ncartasmao[3]-=e.ncartascombo;
    }
    return e;
}

/********************************************** >>  FUNÇÕES "PASSAR" E "JOGAR"  << ************************************************
*																																  *
*    Funções responsáveis pela ação de passar a jogada ou jogar a carta selecionada, caso a jogada seja válida de acordo com as   *
*    regras do jogo.																									          *
*  									  																							  *
**********************************************************************************************************************************/

ESTADO passar(ESTADO e) {
    e.ultimajogada[0]=0;
    e.selecao=0;
    e.ncartasselecao=0;
    e=botPlay1(e);
    e=botPlay2(e);
    e=botPlay3(e);
    e.passar=0;
    e.jogar=0;
    return e;
}
ESTADO jogar(ESTADO e) {
    int n,v,X=540;

    e.ultimajogada[0]=0;
    for(v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++){
            if(carta_existe(e.selecao,n,v)){
                e.ultimajogada[0]=add_carta(e.ultimajogada[0],n,v);
                e.selecao=rem_carta(e.selecao,n,v);
                e.mao[0]=rem_carta(e.mao[0],n,v);
            }
            if(carta_existe(e.ultimajogada[0],n,v)){
                imprime_carta_naoclicavel(X,310,n,v);
                X+=25;
            }
        }
    }
    if(e.ultimojogador==0)
        e.ncartascombo=e.ncartasselecao;
    e.ncartasmao[0]-=e.ncartasselecao;
    e.ultimojogador=0;
    e.ncartasselecao=0;
    e=botPlay1(e);
    e=botPlay2(e);
    e=botPlay3(e);
    e.passar=0;
    e.jogar=0;
    return e;
}

/*************************************************** >>  FUNÇÕES "IMPRIME"  << ****************************************************
*																																  *
*    Funções que imprimem os botões do jogo, nomeadamente, o botão "jogar" que pode estar ativado ou desativado dependendo da	  *
*    validade das cartas selecionadas, o botão "passar" e um botão de reiniciar a partida.										  *
*    A função "imprime" é a responsável por desenhar os baralhos de cada jogador, incluíndo bots e o jogador.					  *
*  									  																							  *
**********************************************************************************************************************************/

void imprime_jogar(ESTADO e) {
    char script[10240];
    e.passar=0;
    e.jogar=1;

    if(e.ultimojogador==0){
        if(jogadaValida(e.selecao,e.ncartasselecao)==1){
            sprintf(script, "%s?%s", SCRIPT,estado2str(e));
            printf("<a xlink:href = \"%s\"><image x = 405 y = 551 height = \"45\" width = \"200\" xlink:href = \"http://127.0.0.1/cards/1JogarAtivo.png\" /></a>\n", script);
        }
        else
            printf("<image x = 405 y = 551 height = \"45\" width = \"200\" xlink:href = \"http://127.0.0.1/cards/1JogarInativo.png\" />\n");
    }
    else{
        if(jogadaValidaMaior(e.selecao,e.ultimajogada[e.ultimojogador],e.ncartasselecao,e.ncartascombo)==1){
            sprintf(script, "%s?%s", SCRIPT,estado2str(e));
            printf("<a xlink:href = \"%s\"><image x = 405 y = 551 height = \"45\" width = \"200\" xlink:href = \"http://127.0.0.1/cards/1JogarAtivo.png\" /></a>\n", script);
        }
        else
            printf("<image x = 405 y = 551 height = \"45\" width = \"200\" xlink:href = \"http://127.0.0.1/cards/1JogarInativo.png\" />\n");
    }
}
void imprime_passar(ESTADO e) {
    char script[10240];
    e.passar=1;
    e.jogar=0;
    sprintf(script, "%s?%s", SCRIPT,estado2str(e));
    printf("<a xlink:href = \"%s\"><image x = 583 y = 551 height = \"45\" width = \"200\" xlink:href = \"http://127.0.0.1/cards/1Passar.png\" /></a>\n", script);
}
void imprime_restart() {
    printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x = 1010 y = 42 height = \"35\" width = \"50\" xlink:href = \"http://127.0.0.1/cards/1Restart.png\" /></a>\n");
}
/** \brief Imprime o estado
@param path o URL correspondente à pasta que contém todas as cartas
@param ESTADO   O estado atual
*/

void imprime(ESTADO e) {
    int n, v;
    int x, y;
    int X;

    imprime_jogar(e);
    imprime_passar(e);
    imprime_restart();

    /* Baralho do utilizador (jogador) */
    for(y = 432, x = 395, v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++){
            if(carta_existe(e.mao[0], n, v)) {
                if(carta_existe(e.selecao, n, v))
                    imprime_carta(x, y-20, e, n, v);
                else
                    imprime_carta(x, y, e, n, v);
                x+=25;
            }
        }
    }
    /* Baralho do Bot1 (bot da esquerda) */
    for(y = 120, x = 120, v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[1], n, v)) {
                imprime_carta_bot1(x,y);
                y += 25;
            }
    }
    /* Baralho do Bot2 (bot do centro) */
    for(y = 55, x = 395, v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[2], n, v)) {
                imprime_carta_bot2(x,y);
                x += 25;
            }
    }
    /* Baralho do Bot3 (bot da direita) */
    for(y = 120, x = 945, v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[3], n, v)) {
                imprime_carta_bot3(x,y);
                y += 25;
            }
    }

    /* Imprime a carta jogada do Bot1*/
    X=375;
    for(v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[1],n,v)){
                imprime_carta_naoclicavel(X,250,n,v);
                X+=25;
            }
    }
    /* Imprime a carta jogada do Bot2*/
    X=540;
    for(v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[2],n,v)){
                imprime_carta_naoclicavel(X,190,n,v);
                X+=25;
            }
    }
    /* Imprime a carta jogada do Bot3*/
    X=718;
    for(v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[3],n,v)){
                imprime_carta_naoclicavel(X,250,n,v);
                X+=25;
            }
    }
    
    /* Desenha o ecrã de vitória quando o utilizador venceu o jogo. */
    if(e.ncartasmao[0]==0){
        printf("<image x=70 y=0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/cards/vitoria.png\" />\n");
        printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x=450 y=542 height = \"55\" width = \"275\" xlink:href = \"http://127.0.0.1/cards/NovoJogo.png\" /></a>\n");
    }
    /* Desenha o ecrã de derrota quando o utilizador perde o jogo (outro Bot qualquer ganha o jogo). */
    if(e.ncartasmao[1]==0||e.ncartasmao[2]==0||e.ncartasmao[3]==0){
        printf("<image x=70 y=0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/cards/derrota.png\" />\n");
        printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x=450 y=542 height = \"55\" width = \"275\" xlink:href = \"http://127.0.0.1/cards/NovoJogo.png\" /></a>\n");
    }
    /* Desenha as imagens decorativas dos Bots. */
    printf("<image x = 0 y = 215 height = \"175\" width = \"185\" xlink:href = \"http://127.0.0.1/cards/bot1.png\" /></a>\n");
    printf("<image x = 500 y = 0 height = \"73\" width = \"180\" xlink:href = \"http://127.0.0.1/cards/bot2.png\" /></a>\n");
    printf("<image x = 1015 y = 215 height = \"185\" width = \"159\" xlink:href = \"http://127.0.0.1/cards/bot3.png\" /></a>\n");

    printf("</svg>\n");
}
/** \brief Baralha, distribuindo 13 cartas por cada jogador
@return    A mão de uma dos jogadores 
*/ 
/************************************************** >>  FUNÇÃO "DISTRIBUIR"  << ***************************************************
*											 																					  *
*    Função responsável por distribuir as cartas aleatoriamente pelos 4 jogadores. 13 a cada um respetivamente.					  *
*  									  		 																					  *
**********************************************************************************************************************************/

ESTADO distribuir (){
    ESTADO e = {{0},{0},{0},{0},0,0,0,0,0,0,666};
	int n,v,r;
   	for(n = 0; n < 4; n++) {
		for(v = 0; v < 13; v++){
			do 
            {r = random() % 4;} 
            while(e.ncartasmao[r]==13);
            e.mao[r] = add_carta(e.mao[r],n,v);
            e.ncartasmao[r]++;
		}
	}
	return e;	
}

/** \brief Trata os argumentos da CGI
Esta função recebe a query que é passada à cgi-bin e trata-a.
Neste momento, a query contém o estado que é um inteiro que representa um conjunto de cartas.
Cada carta corresponde a um bit que está a 1 se essa carta está no conjunto e a 0 caso contrário.
Caso não seja passado nada à cgi-bin, ela assume que todas as cartas estão presentes.
@param query A query que é passada à cgi-bin
*/

void parse(char *query) {
	if(query != NULL && strlen(query)!=0) {
		ESTADO e = str2estado(query);
        if(e.jogar) e = jogar(e);
        if(e.passar) e = passar(e);
        /* if(e.selecionar) e.selecionar = 0; */
		imprime(e);
	} 
    else imprime(distribuir());
}
/** \brief Função principal
Função principal do programa que imprime os cabeçalhos necessários e outras imagens e depois
disso invoca a função que vai imprimir o código html para desenhar as cartas. */
int main () {
	printf("Content-Type: text/html; charset=utf-8\n\n");
	printf("<header><title>BIG 2</title></header>\n");
	printf("<body>\n");
	srandom(time(NULL));
    printf("<svg height = \"629\" width = \"1200\">\n");
    printf("<image x = 70 y = 0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/cards/background.png\" /></a>\n");
    printf("<image x = 470 y = 237 height = \"130\" width = \"236\" xlink:href = \"http://127.0.0.1/cards/center.png\" /></a>\n");

/* Ler os valores passados à cgi que estão na variável ambiente e passá-los ao programa */
	parse(getenv("QUERY_STRING"));
	printf("</body>\n");
	return 0;
}