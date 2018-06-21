#include "hash.h"
#include "interface.h"

#define SIZE 7001


//================= DEFINIÇÃO DAS ESTRUTURAS =================

// "Caixas" onde serão guardadas as informações sobre artigo.
struct DataObject {
    char* title;
    long id;
    int revid;
    char* revtimestamp;
    char* contributorname;
    int contributorid;
    long tamanho;
    long palavras;
    struct DataObject* next;
};

// "Caixas" onde serão guardadas as informações sobre contribuidores.
struct DataContributor {	
    int id;
    int revid;
    char* contributorname;
    int contributorid;
    struct DataContributor* next;
};

// "Cabide" para as caixas com informações.
struct TCD_istruct {
    object hashTableID[SIZE];
    contributor hashTableRevID[SIZE];
};

// "Caixas" onde se guardam informações de suporte à 9. Titles With Prefix
struct titlesWprefix {
    char* title;
    int id;
    struct titlesWprefix* next;
};

// "Caixas onde se guardam informações de suporte à 4. Top 10 Contributors"
struct top10contributor {
    int contributorid;
    int count;
    int MAX;
    int* revid;
    struct top10contributor* next;
};
