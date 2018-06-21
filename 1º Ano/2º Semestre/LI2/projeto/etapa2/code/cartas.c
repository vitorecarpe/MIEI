/* * * * * * * * * * * * * * * * * * * * * * * *  >>  PROJETO BIG 2 (Parte 1)  <<   * * * * * * * * * * * * * * * * * * * * * * * *
*																																  *
*                                       		          Grupo 75 -- PL7     												      *
*                                                                       													      *
*         (A78416) Francisco Oliveira                  (A78764) Ricardo Neves                     (A79175) Vitor Peixoto          *
*  																																  *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cartas.h"
#include "aux.h"


/** \brief <b>Função imprime_carta</b>\n
Função que imprime as cartas do baralho do jogador humano, as que estão no baralho e as selecionadas.
@param x A coordenada x da carta.
@param y A coordenada y da carta.
@param e O estado atual.
@param naipe O naipe da carta.
@param valor O valor da carta.*/
void imprime_carta(int x, int y, ESTADO e, int naipe, int valor) {
	char *suit = NAIPES;
	char *rank = VALORES;
	char script[10240];

	if(carta_existe(e.selecao,naipe,valor)){
		e.selecao = rem_carta(e.selecao, naipe, valor);
        e.ncartasselecao-=1;
        sprintf(script, "%s?%s", SCRIPT,estado2str(e));
        printf("<a xlink:href = \"%s\"><image class=\"cardsUp\" style=\"transition:.3s;\" x = \"%d\" y = \"%d\" height = \"88\" width = \"64\" xlink:href = \"http://127.0.0.1/cards/%c%c.svg\" /></a>\n", script, x, y, rank[valor], suit[naipe]);
    }
	else{
		e.selecao = add_carta(e.selecao, naipe, valor);
        e.ncartasselecao+=1;
        sprintf(script, "%s?%s", SCRIPT,estado2str(e));
        printf("<a xlink:href = \"%s\"><image class=\"cardsDown\" style=\"transition:.3s;\" x = \"%d\" y = \"%d\" height = \"88\" width = \"64\" xlink:href = \"http://127.0.0.1/cards/%c%c.svg\" /></a>\n", script, x, y, rank[valor], suit[naipe]);
    }
}

/** \brief <b>Função imprime_carta_bot1</b>\n
Função que imprime as cartas do baralho do bot1 (computador), o da esquerda, viradas ao contrário em diferentes orientações, dependendo do bot.
@param x A coordenada x da carta.
@param y A coordenada y da carta.*/
void imprime_carta_bot1(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"56\" width = \"77\" xlink:href = \"http://127.0.0.1/cards/jogador1.png\" />\n", x, y);
}

/** \brief <b>Função imprime_carta_bot2</b>\n
Função que imprime as cartas do baralho do bot2 (computador), o do meio, viradas ao contrário em diferentes orientações, dependendo do bot.
@param x A coordenada x da carta.
@param y A coordenada y da carta.*/
void imprime_carta_bot2(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"77\" width = \"56\" xlink:href = \"http://127.0.0.1/cards/jogador2.png\" />\n", x, y);
}

/** \brief <b>Função imprime_carta_bot3</b>\n
Função que imprime as cartas do baralho do bot3 (computador), o da esquerda, viradas ao contrário em diferentes orientações, dependendo do bot.
@param x A coordenada x da carta.
@param y A coordenada y da carta.*/
void imprime_carta_bot3(int x,int y){
    printf("<image x = \"%d\" y = \"%d\" height = \"56\" width = \"77\" xlink:href = \"http://127.0.0.1/cards/jogador3.png\" />\n", x, y);
}

/** \brief <b>Função imprime_carta_naoclicavel</b>\n
Função que imprime as cartas que ficam no centro, ou seja, as cartas jogadas, que não podem ser clicáveis.
@param x A coordenada x da carta.
@param y A coordenada y da carta.
@param naipe O naipe da carta.
@param valor O valor da carta.*/
void imprime_carta_naoclicavel(int x,int y,int naipe,int valor){
    char *suit = NAIPES;
    char *rank = VALORES;
    printf("<image x = \"%d\" y = \"%d\" height = \"88\" width = \"64\" xlink:href = \"http://127.0.0.1/cards/%c%c.svg\"/>\n", x, y, rank[valor], suit[naipe]);
}

/** \brief <b>Função combosValores</b>\n
Função que conta o número de cartas do mesmo valor.
@param cartas Recebe uma mão (long long integer).
@return Devolve o array com o número de cartas de cada valor. Ex: A mão 3♦ 3♣ K♥ K♠ 2♥ devolve o array com as posições valores[0]=2;
valores[10]=2; valores[12]=1 e as restantes posições a 0.*/
int *combosValores(MAO cartas){
    int n,v;
    static int valores[13]={0};
    valores[0]=0;
    valores[1]=0;
    valores[2]=0;
    valores[3]=0;
    valores[4]=0;
    valores[5]=0;
    valores[6]=0;
    valores[7]=0;
    valores[8]=0;
    valores[9]=0;
    valores[10]=0;
    valores[11]=0;
    valores[12]=0;

    for(v=0; v<13; v++)
        for(n=0; n<4; n++)
            if(carta_existe(cartas,n,v))
                valores[v]+=1;

    return valores;
}

/** \brief <b>Função combosNaipes</b>\n
Função que conta o número de cartas do mesmo naipe.
@param cartas Recebe uma mão (long long integer)
@return Devolve o array com o número de cartas de cada naipe. Ex: A mão 3♦ 3♣ K♥ 2♥ K♠ devolve o array com as posições naipes[0]=1;
naipes[1]=1; naipes[2]=2; naipes[3]=1.*/
int *combosNaipes(MAO cartas){
    int n,v;
    static int naipes[4]={0};
    naipes[0]=0;
    naipes[1]=0;
    naipes[2]=0;
    naipes[3]=0;

    for(n=0; n<4; n++)
        for(v=0; v<13; v++)
            if(carta_existe(cartas,n,v))
                naipes[n]+=1;

    return naipes;
}

/** \brief <b>Função combosValoresOrdemPokeniana</b>\n
Função que organiza as combinações existentes na sua ordem segundo as regras do Poker (onde o 2 não é a carta com o maior valor. É por outro
lado, a menos forte). 
@param cartas Recebe uma mão (long long integer)
@return Devolve o array com o número de cartas de cada valor segundo as regras do Poker.\n 
<b>Exemplo:</b>\n O array com as posições comboValores[0]=2; comboValores[10]=2; comboValores[12]=1 passará a ser o ter os seguintes valores:
newCombo[2]=2; newCombo[12]=2; newCombo[1]=1*/
int *combosValoresOrdemPokeniana(MAO cartas) {
    static int newCombo[14];
    int *comboValores;
    comboValores=combosValores(cartas);

    newCombo[0]=comboValores[11];
    newCombo[1]=comboValores[12];
    newCombo[2]=comboValores[0];
    newCombo[3]=comboValores[1];
    newCombo[4]=comboValores[2];
    newCombo[5]=comboValores[3];
    newCombo[6]=comboValores[4];
    newCombo[7]=comboValores[5];
    newCombo[8]=comboValores[6];
    newCombo[9]=comboValores[7];
    newCombo[10]=comboValores[8];
    newCombo[11]=comboValores[9];
    newCombo[12]=comboValores[10];
    newCombo[13]=comboValores[11];

    return newCombo;
}

/* 
    BOA MAO PARA TESTAR:
        -> FLUSH + STRAIGHT = 3532216139284560
        -> 4 OF A KIND + FULLHOUSE = 158470612426752
        -> STRAIGHT FLUSH = 279262628232
*/


/** \brief <b>Função jogadaPENTAstraight</b>\n
Função que reconhece um Straight como uma jogada válida.
@param selecao Recebe uma mão da seleção (long long integer).
@return Função que através das cartas selecionadas diz se estamos perante um Straight (Ex: 3♠ 4♠ 5♦ 6♣ 7♥ - cinco cartas com valores seguidos,
independentemente do naipe - ). Esta função recorre à ordenação da função combosValoresOrdemPokeniana da seleção e identifica quando 5 cartas
seguidas estão selecionadas. Caso esta condição se verfique, devolve 1 (return 1) ou seja, a jogada é válida, caso contrário, devolve 0.\n 
<b>Exemplo:</b>\n 3♠ 4♠ 5♦ 6♣ 7♥ na seleção devolve 1.\n 3♠ 4♠ 5♦ 6♣ na seleção devolve 0.\n 4♠ 5♦ 6♣ 7♥ 9♥ na seleção devolve 0.
\note Esta função pode também ser usada para testar se existe um Straight na mão de um bot.*/
int jogadaPENTAstraight(MAO selecao){
    int n,i;
    int *comboVP;
    comboVP=combosValoresOrdemPokeniana(selecao);
    for(n=0,i=0;i<14;i++)
        if(n<5) {    
            if(comboVP[i]>0) n++;
            else n=0;
        }

    if(n==5) return 1;
    else return 0;    
}

/** \brief <b>Função jogadaPENTAflush</b>\n
Função que reconhece um Flush como uma jogada válida.  
@param selecao Recebe uma mão da seleção (long long integer).
@return Função que através das cartas selecionadas diz se estamos perante um Flush (Ex: 2♣ 4♣ 7♣ J♣ A♣ - cinco cartas do mesmo naipe,
independentemente dos seus valores - ). Esta função recorre à ordenação da função combosNaipes da seleção e ao ler cada naipe da seleção,
caso encontre exatamente 5 cartas do mesmo naipe na seleção, devolve 1 (return 1) ou seja, a jogada é válida, caso contrário, devolve 0.\n
<b>Exemplo:</b>\n 2♣ 4♣ 7♣ J♣ A♣ na seleção devolve 1.\n 2♣ 4♣ 7♠ J♣ A♣ na seleção devolve 0.\n 2♣ 4♣ 7♣ J♣ K♣ A♣ na seleção devolve 0.
\note Esta função pode também ser usada para testar se existe um Flush na mão de um bot.*/
int jogadaPENTAflush(MAO selecao){
    int n,i;
    int *comboN;
    comboN=combosNaipes(selecao);
    for(n=0,i=0;i<4;i++)
        if(comboN[i]>=5) n++;

    if(n>=1) return 1;
    else return 0;
}

/** \brief <b>Função jogadaPENTAfullhouse</b>\n
Função que reconhece um Full House como uma jogada válida. 
@param selecao Recebe uma mão da seleção (long long integer).
@return Função que através das cartas selecionadas diz se estamos perante um Full House (Ex: 4♣ 4♠ K♣ K♦ K♥ - uma conjugação de um par e de
um trio - ). Esta função recorre à ordenação da função combosValores da seleção e ao ler cada conjunto de valores, identifica se estão
selecionadas 2 cartas de valor igual e outras 3 de outro qualquer valor igual. Caso esta condição se verifique, devolve o valor de h (return h) ou seja,
a jogada é válida, caso contrário, devolve 0.\n
<b>Exemplo:</b>\n 4♣ 4♠ K♣ K♦ K♥ na seleção devolve o valor de h.\n 3♣ 4♠ K♣ K♦ K♥ na seleção devolve 0.\n 4♣ K♦ K♣ K♥ K♠ na seleção devolve 0.
\note Esta função pode também ser usada para testar se existe um Full House na mão de um bot.*/
int jogadaPENTAfullhouse(MAO selecao){
    int n2,n3,i,h;
    int *comboV;
    comboV=combosValores(selecao);
    for(n3=0,h=0,i=0;i<13;i++)
        if(comboV[i]>=3) {n3++;h=i+3;}

    for(n2=0,i=0;i<13;i++)
        if(comboV[i]>=2) n2++;

    if( n3>1 || (n3==1 && n2>1) ) return h;
    else return 0;    
}

/** \brief <b>Função jogadaPENTA4ofAkind</b>\n
Função que reconhece um Four Of A Kind como uma jogada válida. 
@param selecao Recebe uma mão da seleção (long long integer).
@return Função que através das cartas selecionadas diz se estamos perante um Four Of A Kind (Ex: 4♣ K♦ K♣ K♥ K♠ - uma conjugação de uma
quadra e de uma outra qualquer carta - ). Esta função recorre à ordenação da função combosValores da seleção e ao ler cada conjunto de valores,
identifica se estão selecionadas 4 cartas de valor igual e uma outra de qualquer outro valor. Caso esta condição se verifique, devolve o valor de h
(return h) ou seja, a jogada é válida, caso contrário, devolve 0.\n
<b>Exemplo:</b>\n 4♣ K♦ K♣ K♥ K♠ na seleção devolve o valor de h.\n 4♦ 4♣ K♣ K♥ K♠ na seleção devolve 0.\n 4♣ 5♠ K♦ K♣ K♥ K♠ na seleção devolve 0.
\note Esta função pode também ser usada para testar se existe um Four Of A Kind na mão de um bot.*/
int jogadaPENTA4ofAkind(MAO selecao){
    int n,i,h;
    int *comboV;
    comboV=combosValores(selecao);
    for(n=0,h=0,i=0;i<13;i++)
        if(comboV[i]==4) {n++;h=i+3;}

    if(n>=1) return h;
    else return 0;
}
int jogadaPENTAstraightFlush(MAO selecao){
    int x,n,v,vp;
    int *comboN,*comboVP;
    MAO tempHand=0;
    comboN=combosNaipes(selecao);
    for(x=0,n=0;n<4;n++){
        if(comboN[n]>=5){
            for(v=0;v<13;v++) 
                if(carta_existe(selecao,n,v)) tempHand=add_carta(tempHand,n,v);
            comboVP=combosValoresOrdemPokeniana(tempHand);
            for(vp=0;vp<14;vp++)
                if(x<5) {    
                    if(comboVP[vp]>=1) x++;
                    else x=0;
                }
            x=0;
        }
    }
    if(x==5) return 1;
    else return 0;      
}
/** selecionar
    FUNÇOES RESPONSAVEIS PELA SELACAO DO RESPETIVO DE JOGADA
*/
MAO selecionaPENTAstraight(ESTADO e,int p) {
    int n=0,v=0,vp=0;
    int i=0,x=0;
    MAO selecao=0;
    int *comboVP;
    comboVP=combosValoresOrdemPokeniana(e.mao[p]);

    for(i=0,vp=0;vp<14;vp++)
        if(i<5){    
            if(comboVP[vp]>0){

            switch(vp){
                case 0: v=11;
                break;
                case 1: v=12;
                break;
                case 2: v=0;
                break;
                case 3: v=1;
                break;
                case 4: v=2;
                break;
                case 5: v=3;
                break;
                case 6: v=4;
                break;
                case 7: v=5;
                break;
                case 8: v=6;
                break;
                case 9: v=7;
                break;
                case 10: v=8;
                break;
                case 11: v=9;
                break;
                case 12: v=10;
                break;
                case 13: v=11;
                break;
                default:
                break;
            }

            for(n=0;n<4;n++)
                if(carta_existe(e.mao[p],n,v) && x==0){
                    selecao=add_carta(selecao,n,v); 
                    i++;x++;
                }

            x=0;
        }
        else{ selecao=0;i=0; }
    }

    return selecao;
}
MAO selecionaPENTAflush(ESTADO e,int p) {
    int n=0,v=0;
    int i=0,x=0,f=0;
    MAO selecao=0;
    int *comboNaipes;
    comboNaipes=combosNaipes(e.mao[p]);

    for(n=0;n<4;n++)
        if(comboNaipes[n]>=5 && i==0) {f=n;i++;}

    for(v=0;v<13;v++)
        if(x<5)
            if(carta_existe(e.mao[p],f,v)){
                selecao=add_carta(selecao,f,v);
                x++;
            }

    return selecao;
}
MAO selecionaPENTAfullhouse(ESTADO e,int p) {
    int n=0,v=0;
    int i=0,f=0,f2=0;
    MAO selecao=0;
    int *comboValores;
    comboValores=combosValores(e.mao[p]);

    for(v=0;v<13;v++)
        if(comboValores[v]>=3) f=v;
    for(n=0;n<4;n++)
        if(i<3)
            if(carta_existe(e.mao[p],n,f)){
                selecao=add_carta(selecao,n,f);
                i++;
            }
            
    for(i=0,v=0;v<13;v++)
        if(comboValores[v]>=2 && v!=f) f2=v;
    for(n=0;n<4;n++)
        if(i<2)
            if(carta_existe(e.mao[p],n,f2)){
                selecao=add_carta(selecao,n,f2);
                i++;
            }

    return selecao;
}
MAO selecionaPENTA4ofAkind(ESTADO e,int p) {
    int n=0,v=0;
    int i=0,f=0;
    MAO selecao=0;
    int *comboValores;
    comboValores=combosValores(e.mao[p]);

    for(v=0;v<13;v++)
        if(comboValores[v]==4) f=v;

    for(n=0;n<4;n++){
        selecao=add_carta(selecao,n,f);
    }
    for(v=0;v<13;v++)
        for(n=0;n<4;n++)
            if(carta_existe(e.mao[p],n,v) && i==0 && v!=f){
                selecao=add_carta(selecao,n,v);
                i++; 
            }

    return selecao;
}
MAO selecionaPENTAstraightFlush(ESTADO e,int p) {
    int n=0,v=0,vp=0;
    int i=0,f=0;
    MAO selecao=0;
    MAO tempHand=0;
    int *comboNaipes,*comboVPtemp;
    comboNaipes=combosNaipes(e.mao[p]);

    for(n=0;n<4;n++)
        if(comboNaipes[n]>=5) f=n;

    for(v=0;v<13;v++)
        if(carta_existe(e.mao[p],f,v)) tempHand=add_carta(tempHand,f,v);

    comboVPtemp=combosValoresOrdemPokeniana(tempHand);
    for(vp=0;vp<14;vp++)
        if(i<5){
            if(comboVPtemp[vp]>0){

                switch(vp){
                    case 0: v=11;
                    break;
                    case 1: v=12;
                    break;
                    case 2: v=0;
                    break;
                    case 3: v=1;
                    break;
                    case 4: v=2;
                    break;
                    case 5: v=3;
                    break;
                    case 6: v=4;
                    break;
                    case 7: v=5;
                    break;
                    case 8: v=6;
                    break;
                    case 9: v=7;
                    break;
                    case 10: v=8;
                    break;
                    case 11: v=9;
                    break;
                    case 12: v=10;
                    break;
                    case 13: v=11;
                    break;
                    default:
                    break;
                }

                selecao=add_carta(selecao,f,v);
                i++;
            }
            else{ selecao=0; i=0; }
        }

    return selecao;
}

/* FUNÇOES DE VALIDAÇAO DE JOGADAS
        -> JOGADA VALIDA - APENAS VERIFICA SE A JOGADA É VALIDA INDEPENDENTEMENTE DO CONTEXTO
        -> JOGADA VALIDA MAIOR - VERIFICA A VALIDADE TENDO EM CONSIDERAÇAO MAIS FATORES COMO
            QUAL O ULTIMO JOGADOR, O PODER DA ULTIMA JOGADA, ETC
*/
int jogadaValida(MAO selecao,int ncartas){
    int i,n;
    int *comboValores;
    comboValores=combosValores(selecao);
    switch (ncartas){
        case 1:     
        return 1;
        break;
        case 2:
            for(n=0,i=0;i<13;i++){
                if(comboValores[i]!=0)
                    n+=1;
            }
            if(n==1)
                return 1;
            else
                return 0;
        break;
        case 3:
            for(n=0,i=0;i<13;i++){
                if(comboValores[i]!=0)
                    n+=1;
            }
            if(n==1)
                return 1;
            else
                return 0;
        break;
        case 5:
            n=0;
            if(jogadaPENTAstraight(selecao)==1) n=51;
            if(jogadaPENTAflush(selecao)==1) n=52;
            if(jogadaPENTAfullhouse(selecao)>0) n=53;
            if(jogadaPENTA4ofAkind(selecao)>0) n=54;
            if(jogadaPENTAstraightFlush(selecao)==1) n=55;

            if(n>1)
                return n;
            else
                return 0;
        break;
        default:    
        return 0;
    }
}
int jogadaValidaMaior(MAO selecao,MAO ultimajogada,int ncartasselecao,int ncartascombo,int ncartasPENTAtype) {
    if(ncartasselecao==ncartascombo){
        if(jogadaValida(selecao,ncartasselecao)){
            if(jogadaValida(selecao,ncartasselecao)==1){
                if(selecao>ultimajogada)
                    return 1;
                else
                    return 0;
            }
            else{
                if(jogadaValida(selecao,ncartasselecao)>ncartasPENTAtype)
                    return 1;
                else 
                    return 0;
            }
        }
        else
            return 0;
    }
    else
        return 0;
}


int jogadaSTARTINGstraight(MAO selecao){
    int n,vp;
    int *comboVP;
    comboVP=combosValoresOrdemPokeniana(selecao);
    for(n=0,vp=0;vp<7;vp++)
        if(n<5) {    
            if(comboVP[vp]>0) n++;
            else n=0;
        }

    if(n==5) return 1;
    else return 0;    
}
int jogadaSTARTINGflush(MAO selecao){
    int *comboN;
    comboN=combosNaipes(selecao);
    if(comboN[0]>=5) return 1;
    else return 0;
}
int jogadaSTARTINGfullhouse(MAO selecao){
    int n2,n3,i,h;
    int *comboV;
    comboV=combosValores(selecao);
    for(n3=0,h=0,i=0;i<13;i++)
        if(comboV[i]>=3) {n3++;h=i+3;}

    for(n2=0,i=0;i<13;i++)
        if(comboV[i]>=2) n2++;

    if( (n3>1 || (n3==1 && n2>1)) && comboV[0]>1 ) return h;
    else return 0;    
}
int jogadaSTARTING4ofAkind(MAO selecao){
    int n,v,h;
    int *comboV;
    comboV=combosValores(selecao);
    for(n=0,h=0,v=0;v<13;v++)
        if(comboV[v]==4) {n++;h=v+3;}

    if(n>=1) return h;
    else return 0;
}
int jogadaSTARTINGstraightFlush(MAO selecao){
    int x,v,vp;
    int *comboN,*comboVP;
    MAO tempHand=0;
    comboN=combosNaipes(selecao);
    if(comboN[0]>=5){
        for(v=0;v<13;v++) 
            if(carta_existe(selecao,0,v)) tempHand=add_carta(tempHand,0,v);
        comboVP=combosValoresOrdemPokeniana(tempHand);
        for(x=0,vp=0;vp<7;vp++)
            if(x<5) {    
                if(comboVP[vp]>=1) x++;
                else x=0;
            }
        x=0;
    }
    if(x==5) return 1;
    else return 0;      
}
MAO selecionaSTARTINGstraight(ESTADO e,int p) {
    int n=0,v=0,vp=0;
    int i=0,x=0;
    MAO selecao=0;
    int *comboVP;
    comboVP=combosValoresOrdemPokeniana(e.mao[p]);

    for(i=0,vp=0;vp<14;vp++)
        if(i<5){    
            if(comboVP[vp]>0){

            switch(vp){
                case 0: v=11;
                break;
                case 1: v=12;
                break;
                case 2: v=0;
                break;
                case 3: v=1;
                break;
                case 4: v=2;
                break;
                case 5: v=3;
                break;
                case 6: v=4;
                break;
                case 7: v=5;
                break;
                case 8: v=6;
                break;
                case 9: v=7;
                break;
                case 10: v=8;
                break;
                case 11: v=9;
                break;
                case 12: v=10;
                break;
                case 13: v=11;
                break;
                default:
                break;
            }

            for(n=0;n<4;n++)
                if(carta_existe(e.mao[p],n,v) && x==0){
                    selecao=add_carta(selecao,n,v); 
                    i++;x++;
                }

            x=0;
        }
        else{ selecao=0;i=0; }
    }

    return selecao;
}
MAO selecionaSTARTINGflush(ESTADO e,int p) {
    int v=0;
    int x=0;
    MAO selecao=0;

    for(v=0;v<13;v++)
        if(x<5)
            if(carta_existe(e.mao[p],0,v)){
                selecao=add_carta(selecao,0,v);
                x++;
            }

    return selecao;
}
MAO selecionaSTARTINGfullhouse(ESTADO e,int p) {
    int n=0,v=0;
    int i=0,f=0;
    MAO selecao=0;
    int *comboValores;
    comboValores=combosValores(e.mao[p]);

    if(comboValores[0]==2){
        for(n=0;n<4;n++)
            if(i<2)
                if(carta_existe(e.mao[p],n,0)){
                    selecao=add_carta(selecao,n,0);
                    i++;
                }
        for(i=0,v=1;v<13;v++)
            if(comboValores[v]>=3) f=v;
        for(n=0;n<4;n++)
            if(i<3)
                if(carta_existe(e.mao[p],n,f)){
                    selecao=add_carta(selecao,n,f);
                    i++;
                }
    }
    else{
        for(n=0;n<4;n++)
            if(i<3)
                if(carta_existe(e.mao[p],n,0)){
                    selecao=add_carta(selecao,n,0);
                    i++;
                }
        for(i=0,v=1;v<13;v++)
            if(comboValores[v]>=2) f=v;
        for(n=0;n<4;n++)
            if(i<2)
                if(carta_existe(e.mao[p],n,f)){
                    selecao=add_carta(selecao,n,f);
                    i++;
                }
    }

    return selecao;
}
MAO selecionaSTARTING4ofAkind(ESTADO e,int p) {
    int n=0,v=0;
    int i=0,f=0;
    MAO selecao=0;
    int *comboValores;
    comboValores=combosValores(e.mao[p]);

    for(v=0;v<13;v++)
        if(comboValores[v]==4) f=v;

    for(n=0;n<4;n++){
        selecao=add_carta(selecao,n,f);
    }
    for(v=0;v<13;v++)
        for(n=0;n<4;n++)
            if(carta_existe(e.mao[p],n,v) && i==0 && v!=f){
                selecao=add_carta(selecao,n,v);
                i++; 
            }

    return selecao;
}
MAO selecionaSTARTINGstraightFlush(ESTADO e,int p) {
    int v=0,vp=0;
    int i=0;
    MAO selecao=0;
    MAO tempHand=0;
    int *comboVPtemp;

    for(v=0;v<13;v++)
        if(carta_existe(e.mao[p],0,v)) tempHand=add_carta(tempHand,0,v);

    comboVPtemp=combosValoresOrdemPokeniana(tempHand);
    for(vp=0;vp<14;vp++)
        if(i<5){
            if(comboVPtemp[vp]>0){

                switch(vp){
                    case 0: v=11;
                    break;
                    case 1: v=12;
                    break;
                    case 2: v=0;
                    break;
                    case 3: v=1;
                    break;
                    case 4: v=2;
                    break;
                    case 5: v=3;
                    break;
                    case 6: v=4;
                    break;
                    case 7: v=5;
                    break;
                    case 8: v=6;
                    break;
                    case 9: v=7;
                    break;
                    case 10: v=8;
                    break;
                    case 11: v=9;
                    break;
                    case 12: v=10;
                    break;
                    case 13: v=11;
                    break;
                    default:
                    break;
                }

                selecao=add_carta(selecao,0,v);
                i++;
            }
            else{ selecao=0; i=0; }
        }

    return selecao;
}

ESTADO playWith3(ESTADO e,int p){
    int n=0;
    int i=0,j=0;
    int *comboValores;
    MAO selecao=0;
    comboValores=combosValores(e.mao[p]);

    if(jogadaSTARTINGstraightFlush(e.mao[p])==1 ||
       jogadaSTARTING4ofAkind(e.mao[p])>0 ||
       jogadaSTARTINGfullhouse(e.mao[p])>0 ||
       jogadaSTARTINGflush(e.mao[p])==1 ||
       jogadaSTARTINGstraight(e.mao[p])==1 ) e.ncartascombo=5;
    else{
        if(comboValores[0]>=3) e.ncartascombo=3;
        else{   if(comboValores[0]==2) e.ncartascombo=2;
                else e.ncartascombo=1;
        }
    }

    if(e.ncartascombo==5){
        if(jogadaSTARTINGstraightFlush(e.mao[p])==1){
            selecao=selecionaSTARTINGstraightFlush(e,p);

            e.ultimajogada[p]=selecao;
            e.mao[p]-=selecao;
            e.ncartasPENTAtype=55;
        }
        else{
            if(jogadaSTARTING4ofAkind(e.mao[p])>0){
                selecao=selecionaSTARTING4ofAkind(e,p);

                e.ultimajogada[p]=selecao;
                e.mao[p]-=selecao;
                e.ncartasPENTAtype=54;
            }
            else{
                if(jogadaSTARTINGfullhouse(e.mao[p])>0){
                    selecao=selecionaSTARTINGfullhouse(e,p);

                    e.ultimajogada[p]=selecao;
                    e.mao[p]-=selecao;
                    e.ncartasPENTAtype=53;
                }
                else{
                    if(jogadaSTARTINGflush(e.mao[p])==1){
                        selecao=selecionaSTARTINGflush(e,p);

                        e.ultimajogada[p]=selecao;
                        e.mao[p]-=selecao;
                        e.ncartasPENTAtype=52;
                    }
                    else{
                        if(jogadaSTARTINGstraight(e.mao[p])==1){
                            selecao=selecionaSTARTINGstraight(e,p);

                            e.ultimajogada[p]=selecao;
                            e.mao[p]-=selecao;
                            e.ncartasPENTAtype=51;
                        }
                        else e.selecionar=999;
                    }
                }
            }
        }
    }
    else{
        for(n=0;n<4;n++)
            if(carta_existe(e.mao[p],n,0)){
                if(e.ncartascombo==1 && i==0){
                    i++;
                    selecao=add_carta(selecao,0,0);
                    e.ultimajogada[p]=selecao;
                    e.mao[p]=rem_carta(e.mao[p],0,0);
                }
                else
                    if(comboValores[0]>=e.ncartascombo && i<e.ncartascombo){
                        selecao=add_carta(selecao,n,0);
                        i++;
                        if(i==e.ncartascombo && j==0){
                            j++;
                            e.ultimajogada[p]=selecao;
                            e.mao[p]-=selecao;
                        }
                    }
            }
    }

    return e;
}
/*************************************************** >>  FUNÇÕES "BOTPLAY"  << ****************************************************
*																																  *
*    Funções que são as responsáveis para as ações e reações de cada bot ao desenrolar do jogo e das jogadas dos outros bots      *
*    															                    						                      *
*  									  																							  *
**********************************************************************************************************************************/
ESTADO botPlay1(ESTADO e,int pickFirst) {
    int n=0,v=0,nn,vv;
    int i=0,j=0;
    int *comboValores;
    MAO selecao=0;
    comboValores=combosValores(e.mao[1]);

    if(pickFirst==1){
        e=playWith3(e,1);
    }
    else{
        if(e.ultimojogador==1){
            if(jogadaPENTAstraightFlush(e.mao[1])==1 ||
               jogadaPENTA4ofAkind(e.mao[1])>0 ||
               jogadaPENTAfullhouse(e.mao[1])>0 ||
               jogadaPENTAflush(e.mao[1])==1 ||
               jogadaPENTAstraight(e.mao[1])==1 ) e.ncartascombo=5;
            else{
                for(v=0;v<13;v++){
                    if(comboValores[v]>=3) e.ncartascombo=3;
                    else{   if(comboValores[v]==2 && e.ncartascombo!=3) e.ncartascombo=2;
                            else if(e.ncartascombo!=2 && e.ncartascombo!=3) e.ncartascombo=1;
                    }
                }
            }
        }

        e.ultimajogada[1]=0;
        if(e.ncartascombo==5){
            e.ultimajogada[1]=0;
            if(jogadaPENTAstraightFlush(e.mao[1])==1 && e.ncartasPENTAtype<55){
                selecao=selecionaPENTAstraightFlush(e,1);
                e.ultimajogada[1]=selecao;
                e.mao[1]-=selecao;
                e.ncartasPENTAtype=55;
            }
            else{
                if(jogadaPENTA4ofAkind(e.mao[1])>0 && e.ncartasPENTAtype<54){
                    selecao=selecionaPENTA4ofAkind(e,1);
                    e.ultimajogada[1]=selecao;
                    e.mao[1]-=selecao;
                    e.ncartasPENTAtype=54;
                }
                else{
                    if(jogadaPENTAfullhouse(e.mao[1])>0 && e.ncartasPENTAtype<53){
                        selecao=selecionaPENTAfullhouse(e,1);
                        e.ultimajogada[1]=selecao;
                        e.mao[1]-=selecao;
                        e.ncartasPENTAtype=53;
                    }
                    else{
                        if(jogadaPENTAflush(e.mao[1])==1 && e.ncartasPENTAtype<52){
                            selecao=selecionaPENTAflush(e,1);
                            e.ultimajogada[1]=selecao;
                            e.mao[1]-=selecao;
                            e.ncartasPENTAtype=52;
                        }
                        else{
                            if(jogadaPENTAstraight(e.mao[1])==1 && e.ncartasPENTAtype<51){
                                selecao=selecionaPENTAstraight(e,1);
                                e.ultimajogada[1]=selecao;
                                e.mao[1]-=selecao;
                                e.ncartasPENTAtype=51;
                            }
                            else e.selecionar=999;
                        }
                    }
                }
            }
        }
        else{
            e.ncartasPENTAtype=0;
            for(v = 0; v < 13; v++){
                for(n = 0; n < 4; n++){
                    if(carta_existe(e.mao[1],n,v)){
                        if(e.ncartascombo==1){
                            selecao=add_carta(selecao,n,v);
                            if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo,e.ncartasPENTAtype) && i==0){
                                i++;
                                e.ultimajogada[1]=selecao;
                                e.mao[1]=rem_carta(e.mao[1],n,v);
                            }
                            else selecao=rem_carta(selecao,n,v);
                        }
                        else{
                            if(comboValores[v]>=e.ncartascombo && i<e.ncartascombo){
                                selecao=add_carta(selecao,n,v);
                                i++;
                                if(i==e.ncartascombo){
                                    if(selecao>e.ultimajogada[e.ultimojogador] && j==0){
                                        j++;
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
        }
    }

    if(e.ultimajogada[1]!=0){
        e.ultimojogador=1;
        e.ncartasmao[1]-=e.ncartascombo;
    }
    return e;
}
ESTADO botPlay2(ESTADO e,int pickFirst) {
    int n,v,nn,vv;
    int i=0,j=0;
    MAO selecao=0;
    int* comboValores;
    comboValores=combosValores(e.mao[2]);

    if(pickFirst==1){
        e=playWith3(e,2);
    }
    else{
        if(e.ultimojogador==2){
            if(jogadaPENTAstraightFlush(e.mao[2])==1 ||
               jogadaPENTA4ofAkind(e.mao[2])>0 ||
               jogadaPENTAfullhouse(e.mao[2])>0 ||
               jogadaPENTAflush(e.mao[2])==1 ||
               jogadaPENTAstraight(e.mao[2])==1 ) e.ncartascombo=5;
            else{
                for(v=0;v<13;v++){
                    if(comboValores[v]>=3) e.ncartascombo=3;
                    else{   if(comboValores[v]==2 && e.ncartascombo!=3) e.ncartascombo=2;
                            else if(e.ncartascombo!=2 && e.ncartascombo!=3) e.ncartascombo=1;
                    }
                }
            }
        }

        e.ultimajogada[2]=0;
        if(e.ncartascombo==5){
            if(jogadaPENTAstraightFlush(e.mao[2])==1 && e.ncartasPENTAtype<55){
                selecao=selecionaPENTAstraightFlush(e,2);
                e.ultimajogada[2]=selecao;
                e.mao[2]-=selecao;
                e.ncartasPENTAtype=55;
            }
            else{
                if(jogadaPENTA4ofAkind(e.mao[2])>0 && e.ncartasPENTAtype<54){
                    selecao=selecionaPENTA4ofAkind(e,2);
                    e.ultimajogada[2]=selecao;
                    e.mao[2]-=selecao;
                    e.ncartasPENTAtype=54;
                }
                else{
                    if(jogadaPENTAfullhouse(e.mao[2])>0 && e.ncartasPENTAtype<53){
                        selecao=selecionaPENTAfullhouse(e,2);
                        e.ultimajogada[2]=selecao;
                        e.mao[2]-=selecao;
                        e.ncartasPENTAtype=53;
                    }
                    else{
                        if(jogadaPENTAflush(e.mao[2])==1 && e.ncartasPENTAtype<52){
                            selecao=selecionaPENTAflush(e,2);
                            e.ultimajogada[2]=selecao;
                            e.mao[2]-=selecao;
                            e.ncartasPENTAtype=52;
                        }
                        else{
                            if(jogadaPENTAstraight(e.mao[2])==1 && e.ncartasPENTAtype<51){
                                selecao=selecionaPENTAstraight(e,2);
                                e.ultimajogada[2]=selecao;
                                e.mao[2]-=selecao;
                                e.ncartasPENTAtype=51;
                            }
                            else e.selecionar=999;
                        }
                    }
                }
            }
        }
        else{
            e.ncartasPENTAtype=0;
            for(v = 0; v < 13; v++){
                for(n = 0; n < 4; n++){
                    if(carta_existe(e.mao[2],n,v)){
                        if(e.ncartascombo==1){
                            selecao=add_carta(selecao,n,v);
                            if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo,e.ncartasPENTAtype) && i==0){
                                i++;
                                e.ultimajogada[2]=selecao;
                                e.mao[2]=rem_carta(e.mao[2],n,v);
                            }
                            else selecao=rem_carta(selecao,n,v);
                        }
                        else{
                            if(comboValores[v]>=e.ncartascombo && i<e.ncartascombo){
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
        }
    }

    if(e.ultimajogada[2]!=0){
        e.ultimojogador=2;
        e.ncartasmao[2]-=e.ncartascombo;
    }
    return e;
}
ESTADO botPlay3(ESTADO e,int pickFirst) {
    int n,v,nn,vv;
    int i=0,j=0;
    MAO selecao=0;
    int* comboValores;
    comboValores=combosValores(e.mao[3]);

    if(pickFirst==1){
        e=playWith3(e,3);
    }
    else{
        if(e.ultimojogador==3){
            if(jogadaPENTAstraightFlush(e.mao[3])==1 ||
               jogadaPENTA4ofAkind(e.mao[3])>0 ||
               jogadaPENTAfullhouse(e.mao[3])>0 ||
               jogadaPENTAflush(e.mao[3])==1 ||
               jogadaPENTAstraight(e.mao[3])==1 ) e.ncartascombo=5;
            else{
                for(v=0;v<13;v++){
                    if(comboValores[v]>=3) e.ncartascombo=3;
                    else{   if(comboValores[v]==2 && e.ncartascombo!=3) e.ncartascombo=2;
                            else if(e.ncartascombo!=2 && e.ncartascombo!=3) e.ncartascombo=1;
                    }
                }
            }
        }

        e.ultimajogada[3]=0;
        if(e.ncartascombo==5){
            e.ultimajogada[3]=0;
            if(jogadaPENTAstraightFlush(e.mao[3])==1 && e.ncartasPENTAtype<55){
                selecao=selecionaPENTAstraightFlush(e,3);
                e.ultimajogada[3]=selecao;
                e.mao[3]-=selecao;
                e.ncartasPENTAtype=55;
            }
            else{
                if(jogadaPENTA4ofAkind(e.mao[3])>0 && e.ncartasPENTAtype<54){
                    selecao=selecionaPENTA4ofAkind(e,3);
                    e.ultimajogada[3]=selecao;
                    e.mao[3]-=selecao;
                    e.ncartasPENTAtype=54;
                }
                else{
                    if(jogadaPENTAfullhouse(e.mao[3])>0 && e.ncartasPENTAtype<53){
                        selecao=selecionaPENTAfullhouse(e,3);
                        e.ultimajogada[3]=selecao;
                        e.mao[3]-=selecao;
                        e.ncartasPENTAtype=53;
                    }
                    else{
                        if(jogadaPENTAflush(e.mao[3])==1 && e.ncartasPENTAtype<52){
                            selecao=selecionaPENTAflush(e,3);
                            e.ultimajogada[3]=selecao;
                            e.mao[3]-=selecao;
                            e.ncartasPENTAtype=52;
                        }
                        else{
                            if(jogadaPENTAstraight(e.mao[3])==1 && e.ncartasPENTAtype<51){
                                selecao=selecionaPENTAstraight(e,3);
                                e.ultimajogada[3]=selecao;
                                e.mao[3]-=selecao;
                                e.ncartasPENTAtype=51;
                            }
                            else e.selecionar=999;
                        }
                    }
                }
            }
        }
        else{
            e.ncartasPENTAtype=0;
            for(v = 0; v < 13; v++){
                for(n = 0; n < 4; n++){
                    if(carta_existe(e.mao[3],n,v)){
                        if(e.ncartascombo==1){
                            selecao=add_carta(selecao,n,v);
                            if(jogadaValidaMaior(selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo,e.ncartasPENTAtype) && i==0){
                                i++;
                                e.ultimajogada[3]=selecao;
                                e.mao[3]=rem_carta(e.mao[3],n,v);
                            }
                            else selecao=rem_carta(selecao,n,v);
                        }
                        else{
                            if(comboValores[v]>=e.ncartascombo && i<e.ncartascombo){
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
        }
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
    e=botPlay1(e,0);
    e=botPlay2(e,0);
    e=botPlay3(e,0);
    e.passar=0;
    e.jogar=0;
    return e;
}
ESTADO jogar(ESTADO e) {
    int n,v,X=555;

    e.ultimajogada[0]=0;
    for(v = 0; v < 13; v++) {
        for(n = 0; n < 4; n++){
            if(carta_existe(e.selecao,n,v)){
                e.ultimajogada[0]=add_carta(e.ultimajogada[0],n,v);
                e.selecao=rem_carta(e.selecao,n,v);
                e.mao[0]=rem_carta(e.mao[0],n,v);
            }
            if(carta_existe(e.ultimajogada[0],n,v)){
                imprime_carta_naoclicavel(X,355,n,v);
                X+=25;
            }
        }
    }
    if(e.ultimojogador==0)
        e.ncartascombo=e.ncartasselecao;
    e.ncartasPENTAtype=jogadaValida(e.selecao,e.ncartasselecao);
    e.ncartasmao[0]-=e.ncartasselecao;
    e.ultimojogador=0;
    e.ncartasselecao=0;
    e=botPlay1(e,0);
    e=botPlay2(e,0);
    e=botPlay3(e,0);
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

    if( e.mao[0]==13 && e.mao[1]==13 && e.mao[2]==13 && e.mao[3]==13 ){
        if( carta_existe(e.selecao,0,0) && (jogadaValida(e.selecao,e.ncartasselecao)>=1) ){
            sprintf(script, "%s?%s", SCRIPT,estado2str(e));
            printf("<a xlink:href = \"%s\"><image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_ativo.png\" /></a>\n", script);
        }
        else
            printf("<image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_inativo.png\" />\n");
    }
    else{
        if(e.ultimojogador==0){
            if(jogadaValida(e.selecao,e.ncartasselecao)>=1){
                sprintf(script, "%s?%s", SCRIPT,estado2str(e));
                printf("<a xlink:href = \"%s\"><image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_ativo.png\" /></a>\n", script);
            }
            else
                printf("<image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_inativo.png\" />\n");
        }
        else{
            if(jogadaValidaMaior(e.selecao,e.ultimajogada[e.ultimojogador],e.ncartasselecao,e.ncartascombo,e.ncartasPENTAtype)==1){
                sprintf(script, "%s?%s", SCRIPT,estado2str(e));
                printf("<a xlink:href = \"%s\"><image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_ativo.png\" /></a>\n", script);
            }
            else
                printf("<image x = 498 y = 563 height = \"30\" width = \"172\" xlink:href = \"http://127.0.0.1/botoes/jogar_inativo.png\" />\n");
        }
    }
}
void imprime_passar(ESTADO e) {
    char script[10240];
    e.passar=1;
    e.jogar=0;
    sprintf(script, "%s?%s", SCRIPT,estado2str(e));
    printf("<a xlink:href = \"%s\"><image x = 674 y = 563 height = \"30\" width = \"53\" xlink:href = \"http://127.0.0.1/botoes/passar.png\"  title=\"Passar\" /></a>\n", script);
}
void imprime_restart() {
    printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x = 391 y = 485 height = \"49\" width = \"19\" xlink:href = \"http://127.0.0.1/botoes/restart.png\" title=\"Nova Partida\" /></a>\n");
}
void imprime_BotaoAjuda(ESTADO e) {
    char script[10240];
    int n=0,v=0;
    int i=0,j=0;
    int *comboValores;
    comboValores=combosValores(e.mao[0]);

    
    if( e.mao[0]==13 && e.mao[1]==13 && e.mao[2]==13 && e.mao[3]==13 ){

        if(jogadaSTARTINGstraightFlush(e.mao[0])==1 ||
           jogadaSTARTING4ofAkind(e.mao[0])>0 ||
           jogadaSTARTINGfullhouse(e.mao[0])>0 ||
           jogadaSTARTINGflush(e.mao[0])==1 ||
           jogadaSTARTINGstraight(e.mao[0])==1 ) e.ncartascombo=5;
        else{
            if(comboValores[0]>=3) e.ncartascombo=3;
            else{   if(comboValores[0]==2) e.ncartascombo=2;
                    else e.ncartascombo=1;
            }
        }
        
        
        if(e.ncartascombo==5){
            if(jogadaSTARTINGstraightFlush(e.mao[0])==1 && e.ncartasPENTAtype<55){
                e.selecao=selecionaSTARTINGstraightFlush(e,0);
                if(e.selecao!=0)
                    e.ncartasselecao=5;            
            }
            else{
                if(jogadaSTARTING4ofAkind(e.mao[0])>0 && e.ncartasPENTAtype<54){
                    e.selecao=selecionaSTARTING4ofAkind(e,0);
                    if(e.selecao!=0)
                        e.ncartasselecao=5;
                }
                else{
                    if(jogadaSTARTINGfullhouse(e.mao[0])>0 && e.ncartasPENTAtype<53){
                        e.selecao=selecionaSTARTINGfullhouse(e,0);
                        if(e.selecao!=0)
                        e.ncartasselecao=5;
                    }
                    else{
                        if(jogadaSTARTINGflush(e.mao[0])==1 && e.ncartasPENTAtype<52){
                            e.selecao=selecionaSTARTINGflush(e,0);
                            if(e.selecao!=0)
                                e.ncartasselecao=5;
                        }
                        else{
                            if(jogadaSTARTINGstraight(e.mao[0])==1 && e.ncartasPENTAtype<51){
                                e.selecao=selecionaSTARTINGstraight(e,0);
                                if(e.selecao!=0)
                                    e.ncartasselecao=5;
                            }
                            else e.selecionar=999;
                        }
                    }
                }
            }
        }
        else{
            for(n=0;n<4;n++)
                if(carta_existe(e.mao[0],n,0)){
                    if(e.ncartascombo==1 && i==0){
                        e.selecao=add_carta(e.selecao,0,0);
                        i++;
                    }
                    else
                        if(comboValores[0]>=e.ncartascombo && i<e.ncartascombo){
                            e.selecao=add_carta(e.selecao,n,0);
                            i++;
                        }
                }
        }

    }
    else{
        if(e.ultimojogador==0)
            e.ncartascombo=1;
        /*
        if(e.ultimojogador==1){
            if(jogadaPENTAstraightFlush(e.mao[1])==1 ||
               jogadaPENTA4ofAkind(e.mao[1])>0 ||
               jogadaPENTAfullhouse(e.mao[1])>0 ||
               jogadaPENTAflush(e.mao[1])==1 ||
               jogadaPENTAstraight(e.mao[1])==1 ) e.ncartascombo=5;
            else{
                for(v=0;v<13;v++){
                    if(comboValores[v]>=3) e.ncartascombo=3;
                    else{   if(comboValores[v]==2 && e.ncartascombo!=3) e.ncartascombo=2;
                            else if(e.ncartascombo!=2 && e.ncartascombo!=3) e.ncartascombo=1;
                    }
                }
            }
        }
        */

        e.selecao=0;
        e.ncartasselecao=0;
        e.ultimajogada[0]=0;

        if(e.ncartascombo==5){
            if(jogadaPENTAstraightFlush(e.mao[0])==1 && e.ncartasPENTAtype<55){
                e.selecao=selecionaPENTAstraightFlush(e,0);
                if(e.selecao!=0)
                    e.ncartasselecao=5;            
            }
            else{
                if(jogadaPENTA4ofAkind(e.mao[0])>0 && e.ncartasPENTAtype<54){
                    e.selecao=selecionaPENTA4ofAkind(e,0);
                    if(e.selecao!=0)
                        e.ncartasselecao=5;
                }
                else{
                    if(jogadaPENTAfullhouse(e.mao[0])>0 && e.ncartasPENTAtype<53){
                        e.selecao=selecionaPENTAfullhouse(e,0);
                        if(e.selecao!=0)
                        e.ncartasselecao=5;
                    }
                    else{
                        if(jogadaPENTAflush(e.mao[0])==1 && e.ncartasPENTAtype<52){
                            e.selecao=selecionaPENTAflush(e,0);
                            if(e.selecao!=0)
                                e.ncartasselecao=5;
                        }
                        else{
                            if(jogadaPENTAstraight(e.mao[0])==1 && e.ncartasPENTAtype<51){
                                e.selecao=selecionaPENTAstraight(e,0);
                                if(e.selecao!=0)
                                    e.ncartasselecao=5;
                            }
                            else e.selecionar=999;
                        }
                    }
                }
            }
        }
        else{
            for(v = 0; v < 13; v++){
                for(n = 0; n < 4; n++){
                    if(carta_existe(e.mao[0],n,v)){
                        if(e.ncartascombo==1){
                            e.selecao=add_carta(e.selecao,n,v);
                            e.ncartasselecao++;
                            if(jogadaValidaMaior(e.selecao,e.ultimajogada[e.ultimojogador],1,e.ncartascombo,e.ncartasPENTAtype) && i==0){
                                i++;
                            }
                            else {
                                e.selecao=rem_carta(e.selecao,n,v);
                                e.ncartasselecao--;
                            }
                        }
                        else{
                            if(comboValores[v]>=e.ncartascombo && i<e.ncartascombo && j==0){
                                e.selecao=add_carta(e.selecao,n,v);
                                e.ncartasselecao++;
                                i++;
                                if(i==e.ncartascombo){
                                    if(e.selecao>e.ultimajogada[e.ultimojogador]){
                                        j=1;
                                    }
                                    else { e.selecao=0;e.ncartasselecao=0;i=0;j=0; }
                                }
                            }
                        }
                    }
                }
                if(e.ncartascombo!=e.ncartasselecao) { e.selecao=0;e.ncartasselecao=0;i=0;j=0; }
            }
        }
    }

    /*
    if(e.selecionar==999)
        printf("<svg width=\"629\" height=\"1200\"><text x=\"400\" y=\"400\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">NAO EXISTE JOGADA POSSIBEL</text></svg>\n");
    */

    sprintf(script, "%s?%s", SCRIPT,estado2str(e));
    printf("<a xlink:href = \"%s\"><image x = 440 y = 563 height = \"30\" width = \"53\" xlink:href = \"http://127.0.0.1/botoes/help.png\" title=\"Dica\" /></a>\n", script);
}
void imprime_NaipeValor(ESTADO e) {
    char script[10240];
	if(e.naipevalor==0)
		e.naipevalor=1;
	else
		e.naipevalor=0; 
    sprintf(script, "%s?%s", SCRIPT,estado2str(e));
    printf("<a xlink:href = \"%s\"><image x = 755 y = 483 height= \"24\" width = \"19\" xlink:href = \"http://127.0.0.1/botoes/valor.png\" title=\"Ordenar pelo valor\" /></a>\n", script);
    printf("<a xlink:href = \"%s\"><image x = 755 y = 509 height= \"24\" width = \"19\" xlink:href = \"http://127.0.0.1/botoes/naipe.png\" title=\"Ordenar pelo naipe\" /></a>\n", script);
}
void imprime_caixas_pontuacao(ESTADO e) {
    /*User (pontos e cartas)*/
    printf("<image x = 522 y = 437 height= \"44\" width = \"126\" xlink:href = \"http://127.0.0.1/botoes/user.png\" /></a>\n");
    printf("<svg width=\"1200\" height=\"629\"><text x=\"583\" y=\"461\" font-family=\"sans-serif\" font-size=\"10\" fill=\"lightgrey\">%d</text></svg>\n",e.ncartasPENTAtype); /*pontos user*/
    printf("<svg width=\"1200\" height=\"629\"><text x=\"618\" y=\"461\" font-family=\"sans-serif\" font-size=\"10\" fill=\"lightgrey\">%d</text></svg>\n",e.ncartasmao[0]); /*cartas user*/
	/*Vitor (pontos e cartas)*/
    printf("<image x = 123 y = 150 height= \"44\" width = \"126\" xlink:href = \"http://127.0.0.1/botoes/vitor.png\" /></a>\n");
    printf("<svg width=\"1200\" height=\"629\"><text x=\"140\" y=\"188\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",0); /*pontos vitor*/
    printf("<svg width=\"1200\" height=\"629\"><text x=\"200\" y=\"188\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",e.ncartasmao[1]); /*cartas vitor*/
	/*Francisco (pontos e cartas)*/
    printf("<image x = 523 y = 135 height= \"44\" width = \"126\" xlink:href = \"http://127.0.0.1/botoes/kiko.png\" /></a>\n");
    printf("<svg width=\"629\" height=\"1200\"><text x=\"543\" y=\"173\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",0); /*pontos francisco*/
    printf("<svg width=\"629\" height=\"1200\"><text x=\"601\" y=\"173\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",e.ncartasmao[2]); /*cartas francisco*/
    /*Ricardo (pontos e cartas)*/
    printf("<image x = 915 y = 150 height= \"44\" width = \"126\" xlink:href = \"http://127.0.0.1/botoes/ricardo.png\" /></a>\n");
    printf("<svg width=\"1200\" height=\"629\"><text x=\"935\" y=\"188\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",0); /*pontos ricardo*/
    printf("<svg width=\"1200\" height=\"629\"><text x=\"991\" y=\"188\" font-family=\"sans-serif\" font-size=\"14\" fill=\"lightgrey\">%d</text></svg>\n",e.ncartasmao[3]); /*cartas ricardo*/
	/*Imprime placa onde se encontram os botões "?" (Ajuda), "Jogar" e "->" (Passar). E imprime também a zona onde se encontram as cartas dos jogador.*/
	printf("<image x = 432 y = 523 height= \"76\" width = \"302\" xlink:href = \"http://127.0.0.1/botoes/pad.png\" /></a>\n");
    printf("<image x = 416 y = 465 height= \"88\" width = \"340\" xlink:href = \"http://127.0.0.1/botoes/back.png\" /></a>\n");
}

/** \brief Imprime botoes e svgs
	funçao com o proposito de desenhar todos os botoes e imagens nessessarias ao jogo
*/
void imprime_botoesANDsvgs(ESTADO e) {
	imprime_caixas_pontuacao(e);
    imprime_restart();
    imprime_NaipeValor(e);
    imprime_jogar(e);
    imprime_passar(e);
    imprime_BotaoAjuda(e);
}
/** \brief Imprime o estado
@param path o URL correspondente à pasta que contém todas as cartas
@param ESTADO   O estado atual
*/
void imprime(ESTADO e) {
    int n, v;
    int x, y;
    int X;

    imprime_botoesANDsvgs(e);

    /* Baralho do utilizador (jogador) */
    if (e.naipevalor==0) {
    	for(y = 465, x = 407, v = 0; v < 13; v++) {
    	    for(n = 0; n < 4; n++){
    	        if(carta_existe(e.mao[0], n, v)) {
    	            if(carta_existe(e.selecao, n, v))
    	                imprime_carta(x, y-20, e, n, v);
    	            else
    	                imprime_carta(x, y, e, n, v);
    	            x+=24;
    	        }
    	    }
    	}
	}
	else {
    	for(y = 465, x = 407, n = 0; n < 4; n++) {
    	    for(v = 0; v < 13; v++){
    	        if(carta_existe(e.mao[0], n, v)) {
    	            if(carta_existe(e.selecao, n, v))
    	                imprime_carta(x, y-20, e, n, v);
    	            else
    	                imprime_carta(x, y, e, n, v);
    	            x+=24;
    	        }
    	    }
    	}
	}

    /* CRIAR FUNÇAO DESENHA MAOS DOS BOTS */
    /* Baralho do Bot1 (bot da esquerda) */
    for(y=210,x=120,v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[1], n, v)) {
                /* imprime_carta(x,y,e,n,v); cartas viradas para cima */
                imprime_carta_bot1(x,y); /* cartas ao contrario */
                y += 17;
            }
    }
    /* Baralho do Bot2 (bot do centro) */
    for(y=40,x=462,v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[2], n, v)) {
                /* imprime_carta(x,y,e,n,v); cartas viradas para cima */
                imprime_carta_bot2(x,y);  /* cartas ao contrario */
                x += 17;
            }
    }
    /* Baralho do Bot3 (bot da direita) */
    for(y=210,x=980,v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.mao[3], n, v)) {
                /* imprime_carta(x,y,e,n,v); cartas viradas para cima */
                imprime_carta_bot3(x,y);  /* cartas ao contrario */
                y += 17;
            }
    }

    /* CRIAR FUNÇAO DESENHA JOGADAS DOS BOTS */
    /* Imprime a carta jogada do Bot1*/
    X=375;
    for(v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[1],n,v)){
                imprime_carta_naoclicavel(X,250,n,v);
                X+=25;
            }
    }
    /* Imprime a carta jogada do Bot2*/
    X=555;
    for(v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[2],n,v)){
                imprime_carta_naoclicavel(X,190,n,v);
                X+=25;
            }
    }
    /* Imprime a carta jogada do Bot3*/
    X=730;
    for(v=0;v<13;v++) {
        for(n = 0; n < 4; n++)
            if(carta_existe(e.ultimajogada[3],n,v)){
                imprime_carta_naoclicavel(X,250,n,v);
                X+=25;
            }
    }


    /* MAYBE CRIAR FUNÇAO GG20 QUE VE SE JA ALGUEM GANHOU */
    /* Desenha o ecrã de vitória quando o utilizador venceu o jogo. */
    if(e.ncartasmao[0]==0){
        printf("<image x=70 y=0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/botoes/vitoria.png\" />\n");
        printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x=450 y=542 height = \"55\" width = \"275\" xlink:href = \"http://127.0.0.1/botoes/NovoJogo.png\" /></a>\n");
    }
    /* Desenha o ecrã de derrota quando o utilizador perde o jogo (outro Bot qualquer ganha o jogo). */
    if(e.ncartasmao[1]==0||e.ncartasmao[2]==0||e.ncartasmao[3]==0){
        printf("<image x=70 y=0 height = \"629\" width = \"1035\" xlink:href = \"http://127.0.0.1/botoes/derrota.png\" />\n");
        printf("<a xlink:href = \"http://127.0.0.1/cgi-bin/cartas\"><image x=450 y=542 height = \"55\" width = \"275\" xlink:href = \"http://127.0.0.1/botoes/NovoJogo.png\" /></a>\n");
    }
    /* Desenha as imagens decorativas dos Bots. */
    printf("<image x = 0 y = 215 height = \"175\" width = \"185\" xlink:href = \"http://127.0.0.1/botoes/bot1.png\" /></a>\n");
    printf("<image x = 500 y = 0 height = \"73\" width = \"180\" xlink:href = \"http://127.0.0.1/botoes/bot2.png\" /></a>\n");
    printf("<image x = 1015 y = 215 height = \"185\" width = \"159\" xlink:href = \"http://127.0.0.1/botoes/bot3.png\" /></a>\n");
    printf("</svg>\n");
}
/* ************************************************* >>  FUNÇÃO "DISTRIBUIR"  << **************************************************
*											 																					  *
*    Função responsável por distribuir as cartas aleatoriamente pelos 4 jogadores. 13 a cada um respetivamente.					  *
*  									  		 																					  *
**********************************************************************************************************************************/
ESTADO findTresOuros(ESTADO e){
    int p=0,x=0;

    for(p=0;p<4;p++)
        if(carta_existe(e.mao[p],0,0)) x=p; /* x guarda o numero do jogador que tem o 3de ouros */

    switch (x){
        case 0:
        break;
        case 1: 
            e.ultimojogador=1;
            e=botPlay1(e,1);
            e=botPlay2(e,0);
            e=botPlay3(e,0);
        break;
        case 2:
            e.ultimojogador=2;
            e=botPlay2(e,1);
            e=botPlay3(e,0);
        break;
        case 3:
            e.ultimojogador=3;
            e=botPlay3(e,1);
        break;
        default:
        break;
    }

    return e;
}   

/** \brief Baralha, distribuindo 13 cartas por cada jogador
@return    A mão de uma dos jogadores 
*/ 
ESTADO distribuir (){
    ESTADO e = {{0},{0},{0},{0},0,0,0,0,0,666,0,0,0};
	int n,v,r;
   	for(n = 0; n < 4; n++) {
		for(v = 0; v < 13; v++){
			do r = random() % 4;
            while(e.ncartasmao[r]==13);
            e.mao[r] = add_carta(e.mao[r],n,v);
            e.ncartasmao[r]++;
		}
	}
    e=findTresOuros(e);
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
		imprime(e);
	} 
    else imprime(distribuir());
}