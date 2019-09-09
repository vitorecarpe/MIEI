/**
 * Guião 00 de Sistemmas Operativos.
 *
 * @author (PIRATA)
 * @version (2018.02)
 */

#include <stdio.h>
#include <stdlib.h>

#define MAX_SIZE 65536

struct intv {
	int priml;
	int llivres;
	struct intv* next;
};

/* Exercicios 3.1 e 4 */
/** @brief Reserva um determinado número de lugares
 *
 *	Função que reserva um determinado número de lugares seguidos, indicando qual o
 *  primeiro lugar reservado, mesmo que essa área tenha sido previamente usada e
 * 	libertada, tentando reservar numa sequência livre com o minimo de lugares
 *	excedentários. No caso de não conseguir reservar, devolve -1 em vez do primeiro
 *	lugar reservado.
 *
 *	@param livres lista ligada com as informações sobre os lugares livres
 *	@param n número de lugares que pretendemos reservar
 *	@param reservado valor por onde indicar o primeiro lugar reservado
 *
 *	@return a lista ligada com os lugares atualizados após a reserva
 */
struct intv* reserva (struct intv* livres, int n, int* reservado)
{
	struct intv *curr = livres, *best = livres;
	int extras = -1, melhor;

	/* Procura o melhor sitio para alocar os espaços que obedeça a dois pontos:
		- ter espaço suficiente para todos juntos;
		- ter o minimo de espaços extras. */
	melhor = livres->llivres - n;
	while (curr != NULL) {
		extras = curr->llivres - n;
		if ((extras >= 0) && (extras < melhor)) {
			best = curr;
			melhor = extras;
		}
		curr = curr->next;
	}

	/* Se o melhor for menor que 0, então não existe um bloco disponivel.
	Se existe, então o melhor sitio para alocar os espaços está no apontador best. */
	if (melhor < 0) {
		*reservado = (-1);
    } else {
		*reservado = best->priml;
		best->priml += n;
		best->llivres -= n;
	}

	/* Se os lugares livres for 0 no best, remove este pedaço. */
	if (best->llivres == 0) {
		curr = livres;
		while (curr->next != best) {
			curr = curr->next;
        }
		curr->next = best->next;
		free(best);
	}
	return livres;
}

/* recebe a estrutura com o estádio, o primeiro lugar a libertar e o número de lugares a libertar.
 * assumindo que nao se manda libertar lugares que estao livres. */
/** @brief Liberta um determinado número de lugares a partir de uma posição ocupada
 *
 *	Função que liberta um determinado número de lugares seguidos, indicando qual o
 *  primeiro lugar reservado a libertar e o número de lugares a libertar.
 *
 *	@param livres lista ligada com as informações sobre os lugares livres
 *	@param lugar primeiro lugar reservado que pretendemos libertar
 *	@param n número de lugares que pretendemos libertar
 *
 *	@return a lista ligada com os lugares atualizados após a reserva
 */
struct intv* liberta (struct intv* livres, int lugar, int n)
{
	struct intv *curr = livres, *prev = NULL, *novo;

	/* se o número de lugares a reservar for nulo ou negativo, ou se se pretender libertar
	lugares extra-estádio retorna o "estádio" tal como veio */
	if ((n <= 0) || ((lugar + n) > MAX_SIZE)) {
		return livres;
    }

	/* enquanto ainda existirem blocos de lugares e o último lugar livre do actual bloco de
	 * lugares for menor do que o primeiro lugar que se pretende libertar*/
	while ((curr != NULL) && ((curr->priml + curr->llivres - 1) < lugar)) {
		prev = curr;  /* guardar o actual bloco de lugares */
		curr = curr->next;  /* percorrer a lista ligada */
	}

	/* Se o sitio que se quer libertar corresponde ao espaço ocupado entre dois blocos. */
	if ((prev != NULL) && (prev->priml + prev->llivres == lugar) && (curr->priml == lugar + n)) {
		prev->llivres = prev->llivres + n + curr->llivres;
		prev->next = curr->next;
		free(curr);
		return livres;
	}

	/* Se o espaço a libertar se estende até espaços livres a seguir. */
	if ((curr != NULL) && (curr->priml <= lugar + n)) {
		curr->llivres = curr->llivres + n - (lugar + n - curr->priml);
		curr->priml = lugar;
		return livres;
	}

	/* Se o espaço a libertar se estende a partir dos espaços livres anteriores. */
	if ((prev != NULL) && (prev->priml + prev->llivres == lugar)) {
		prev->llivres += n;
		return livres;
	}

	/* criar um novo bloco de lugares */
	novo = (struct intv *) malloc(sizeof(struct intv));
	novo->priml = lugar;
	novo->llivres = n;

	/* caso se trate de uma adição entre dois blocos, ou à cabeça. */
	if (prev != NULL) {
		prev->next = novo;
		novo->next = curr;
	} else {
		novo->next = curr;
		livres = novo;
	}
	return livres;
}

/* Exercicios 3.2 e 4 */
/* Quase a mesma coisa que nas versões anteriores mas com umas alterações na parte de retorno de dados.
 * Agora usa dupla indireção. */
int reserva2(struct intv **livres, int n)
{
	struct intv *curr = *livres, *best = *livres;
	int extras = -1, melhor, reservado;

	melhor = (*livres)->llivres - n;
	while (curr != NULL) {
		extras = curr->llivres - n;
		if ((extras >= 0) && (extras < melhor)) {
			best = curr;
			melhor = extras;
		}
		curr = curr->next;
	}

	if (melhor < 0) {
		reservado = (-1);
    } else {
		reservado = best->priml;
		best->priml += n;
		best->llivres -= n;
	}

	if (best->llivres == 0) {
		curr = *livres;
		while (curr->next != best) {
			curr = curr->next;
        }
		curr->next = best->next;
		free(best);
	}
	return reservado;
}

void liberta2(struct intv **livres, int lugar, int n)
{
	struct intv *curr = *livres, *prev = NULL, *novo;

	if (!((n <= 0) || ((lugar + n) > MAX_SIZE))) {
		while ((curr != NULL) && ((curr->priml + curr->llivres - 1) < lugar)) {
			prev = curr;
			curr = curr->next;
		}

		if ((prev != NULL) && (prev->priml + prev->llivres == lugar) && (curr->priml == lugar + n)) {
			prev->llivres = prev->llivres + n + curr->llivres;
			prev->next = curr->next;
			free(curr);
		} else {
			if ((curr != NULL) && (curr->priml <= lugar + n)) {
				curr->llivres = curr->llivres + n - (lugar + n - curr->priml);
				curr->priml = lugar;
			} else {
				if ((prev != NULL) && (prev->priml + prev->llivres == lugar)) {
					prev->llivres += n;
                } else {
					novo = (struct intv *) malloc(sizeof(struct intv));
					novo->priml = lugar;
					novo->llivres = n;

					if (prev != NULL) {
						prev->next = novo;
						novo->next = curr;
					} else {
						novo->next = curr;
						(*livres) = novo;
					}
				}
			}
		}
	}
}



/* Imprime a estrutura com o estádio */
/*
void print(struct intv *estadio)
{
	struct intv *aux = estadio;

	printf("------------------------------------\n");

	while (aux!=NULL) {
		printf("Lugares livres do %d até ao %d\n", aux->priml, aux->priml + aux->llivres - 1);
		aux = aux->next;
	}

	printf("------------------------------------\n");
}
*/

/* Função Main de teste. */
/*
int main ()
{
	struct intv *estadio = (struct intv *) malloc (sizeof(struct intv));
	int rsv[10];
	int i;

	for (i = 0;i < 10; i++) {
		rsv[i] = -1;
    }

	estadio->priml = 0;
	estadio->llivres = MAX_SIZE;
	estadio->next = NULL;

	print(estadio);
	rsv[0] = reserva2(&estadio,1500);
	rsv[1] = reserva2(&estadio,1800);
	rsv[2] = reserva2(&estadio,1500);
	print(estadio);

	liberta2(&estadio, 15, 700);

	liberta2(&estadio, 900, 700);

	print(estadio);

	liberta2(&estadio,0,15);

	print(estadio);

	rsv[3] = reserva2(&estadio,695);

	print(estadio);

	printf("\n%d - %d - %d - %d\n",rsv[0],rsv[1],rsv[2],rsv[3]);

	return 0;
}
*/
