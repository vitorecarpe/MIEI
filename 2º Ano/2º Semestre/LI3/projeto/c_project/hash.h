#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <libxml/xmlmemory.h>
#include <libxml/parser.h>
#include <stdbool.h>
#include "interface.h"

#define SIZE 7001


//Ficheiro hash.c
typedef struct DataObject *object;
typedef struct DataContributor *contributor;
typedef struct titlesWprefix *TWD;
typedef struct top10contributor *T10C;

//Ficheiro load.c
int hashCode (int id);
void insertHashID (TAD_istruct qs, object artigo);
void insertHashRevID (TAD_istruct qs, contributor artigo);
char *mystrdup (const char *s);
object getObject (xmlNodePtr node, xmlDocPtr doc);
contributor getContributor (object info);
void printHashID(TAD_istruct qs);
void printHashRevID(TAD_istruct qs);
void lePrints(TAD_istruct qs);
int parseDoc(xmlDocPtr doc);

//Ficheiro questoes.c
long contaPalavras(char* s);
