#ifndef CARTAS_H_
#define CARTAS_H_


/** URL da CGI */
#define SCRIPT		"http://127.0.0.1/cgi-bin/cartas"
#define BARALHO		"http://127.0.0.1/cards"
#define NAIPES		"DCHS"
#define VALORES		"3456789TJQKA2"

#define FORMATO "%lld_%lld_%lld_%lld_%lld_%lld_%lld_%lld_%d_%d_%d_%d_%d_%d_%d_%d_%d_%lld_%d_%d_%d_%d_%d_%d_%d"

typedef long long int MAO;
typedef struct estado
{
	MAO mao[4];
    MAO ultimajogada[4];
    int ncartasmao[4];
    int pontos[4];
    int ultimojogador;
	MAO selecao;
	int ncartasselecao,ncartascombo,ncartasPENTAtype,selecionar;
	int passar,jogar,naipevalor;
} ESTADO;

void imprime_carta(int x, int y, ESTADO e, int naipe, int valor);
void imprime_carta_bot1(int x,int y);
void imprime_carta_bot2(int x,int y);
void imprime_carta_bot3(int x,int y);
void imprime_carta_naoclicavel(int x,int y,int naipe,int valor);

int *combosValores(MAO cartas);
int *combosNaipes(MAO cartas);
int *combosValoresOrdemPokeniana(MAO cartas);

int jogadaPENTAstraight(MAO selecao);
int jogadaPENTAflush(MAO selecao);
int jogadaPENTAfullhouse(MAO selecao);
int jogadaPENTA4ofAkind(MAO selecao);
int jogadaPENTAstraightFlush(MAO selecao);

int jogadaValida(MAO selecao,int ncartas);
int jogadaValidaMaior(MAO selecao,MAO ultimajogada,int ncartasselecao,int ncartascombo,int ncartasPENTAtype);
ESTADO playWith3(ESTADO e,int p);

MAO selecionaPENTAstraight(ESTADO e,int p);
MAO selecionaPENTAflush(ESTADO e,int p);
MAO selecionaPENTAfullhouse(ESTADO e,int p);
MAO selecionaPENTA4ofAkind(ESTADO e,int p);
MAO selecionaPENTAstraightFlush(ESTADO e,int p);

ESTADO botPlay1(ESTADO e,int pickFirst);
ESTADO botPlay2(ESTADO e,int pickFirst);
ESTADO botPlay3(ESTADO e,int pickFirst);

ESTADO passar(ESTADO e);
ESTADO jogar(ESTADO e);

void imprime_jogar(ESTADO e);
void imprime_passar(ESTADO e);
void imprime_restart();
void imprime_BotaoAjuda(ESTADO e);
void imprime_NaipeValor(ESTADO e);
void imprime_caixas_pontuacao(ESTADO e);

void imprime_botoesANDsvgs(ESTADO e);
void imprime_debuging(ESTADO e) ;
void imprime(ESTADO e);

ESTADO distribuir ();
void parse(char *query);


#endif