#include "hash.h"
#include "interface.h"
#include "hash.c"


//=============== FUNÇÕES AUXILIARES DE INSERÇÃO NA HASH ===============

// Função que recebe o id do artigo e ao dividir pelo tamanho da tabela hash vai retornar o indíce onde as informações vão ser/estao guardadas
int hashCode (int id) {
    return id % SIZE;
}
// Insere "caixas" na hashTableID
void insertHashID (TAD_istruct qs, object artigo) {
    int hashcode=hashCode(artigo->id);
    artigo->next=qs->hashTableID[hashcode];
    qs->hashTableID[hashcode]=artigo;
}
// Insere "caixas" na hashTableRevID
void insertHashRevID (TAD_istruct qs, contributor artigo) {
    int hashcode=hashCode(artigo->contributorid);
    artigo->next=qs->hashTableRevID[hashcode];
    qs->hashTableRevID[hashcode]=artigo;
}

// A nossa strdup que nao cria warnings irritantes
char *mystrdup (const char *s) {
	char *d = malloc (strlen (s) + 1);
	if (d == NULL) return NULL;
	strcpy (d,s);
	return d;
}


//============================= FUNÇÕES GET ============================

// Carrega todos os dados de uma pagina para uma "caixa"
object getObject (xmlNodePtr node, xmlDocPtr doc) {
	object artigo = (object) malloc (sizeof(struct DataObject));

	node = node->children;

	// Titulo do artigo
	while (xmlStrcmp(node->name, (const xmlChar*)"title")) node=node->next;
	artigo->title = ((char*) xmlNodeListGetString(doc, node->children, 1));
	// Id do artigo
	while (xmlStrcmp(node->name, (const xmlChar*)"id")) node=node->next;
	artigo->id = atol((const char *) node->children->content);
	// Entrar no revision
	while (xmlStrcmp(node->name, (const xmlChar*)"revision")) node=node->next;
	
	node = node->children;

		//Id da revisão
		node = node->next;
		artigo->revid = atoi((const char*) node->children->content);
		//Timestamp da revisão
		while (xmlStrcmp(node->name, (const xmlChar *)"timestamp")) node = node->next;
		artigo->revtimestamp = ((char*) xmlNodeListGetString(doc, node->children, 1));

		xmlNodePtr nodeaux = node;

			// Entrar na tag do contributor
			while (xmlStrcmp(node->name, (const xmlChar*)"contributor")) node=node->next;
			node = node->children;
			// Ip do contributor caso não tenha username e id
			if (!strcmp((char*) node->next->name, "ip")) {
				artigo->contributorname = NULL;
				artigo->contributorid = 0;
			} else {
			// Username do contributor
			while (xmlStrcmp(node->name, (const xmlChar *)"username")) node=node->next;
			artigo->contributorname = (char*) xmlNodeListGetString(doc, node -> children, 1);
			//Id do contributor
			while (xmlStrcmp(node->name, (const xmlChar *)"id")) node=node->next;
			artigo->contributorid = atoi((const char*) node->children->content);
		}
		//Texto do artigo
		while (xmlStrcmp(nodeaux -> name,(const xmlChar *)"text") || !nodeaux->children) nodeaux = nodeaux->next;
		artigo->tamanho = strlen((char*) nodeaux->children->content);
		artigo->palavras = contaPalavras((char*) nodeaux->children->content);

		//Imprimir ID e Tamanho de cada revisão. TESTING ONLY! (Para a Questao 6 - top 2 largest articles)
		//printf("%d\n",artigo->id);
		//printf("%ld\n\n", artigo->palavras);

	return artigo;
}

// Carrega todos os dados de uma pagina para uma "caixa"
contributor getContributor (object caixa) {
	contributor artigo = (contributor) malloc(sizeof(struct DataContributor));
	artigo->id = caixa->id;
	artigo->revid = caixa->revid;
	artigo->contributorname = caixa->contributorname;
	artigo->contributorid = caixa->contributorid;
	artigo->next=NULL;
	return artigo;
}


//============= FUNÇÕES PARA IMPRIMIR HASH (DEBUGGING MODE) ============

// Imprime a tabela de hash dos artigos
void printHashID(TAD_istruct qs){
	int j;
	object auxID;
	for(j=0;j<SIZE;j++) {
		auxID = qs->hashTableID[j];
		printf("\n\n~~~~~~------> %d <------~~~~~~\n\n",j);
		while(auxID) {
			printf("Key: %d | ID: %ld | Title: %s \nID da revisão: %d | Timestamp: %s | Username: %s | ID do contributor: %d \n", hashCode(auxID->id), auxID->id, auxID->title, auxID->revid, auxID->revtimestamp, auxID->contributorname, auxID->contributorid);
			auxID = auxID->next;
		}
	}
}

//Imprime a tabela de hash das revisões
void printHashRevID(TAD_istruct qs){
	int k;
	contributor auxRevID;
	for(k=0;k<100;k++) {
		auxRevID=qs->hashTableRevID[k];
		printf("\n~~~~~~------> %d <------~~~~~~\n",k);
		while(auxRevID) {
			printf("Key: %d | ID: %d | ID da revisão: %d | ID do contributor: %d \n", hashCode(auxRevID->contributorid), auxRevID->id, auxRevID->revid, auxRevID->contributorid);
			auxRevID=auxRevID->next;
		}
	}
}

//Executa as duas funções
void lePrints(TAD_istruct qs){
	printHashID(qs);
	printHashRevID(qs);
}


//================ FUNÇÕES INIT, LOAD e CLEAN ================

//Verifica se o backup a carregar é valido
int parseDoc(xmlDocPtr doc) {
	xmlNodePtr cur = xmlDocGetRootElement(doc);

	if (doc == NULL ) {
		fprintf(stderr,"  Document not parsed successfully\n");
		xmlFreeDoc(doc);
		return -1;
	}
	if (cur == NULL) {
		fprintf(stderr,"  Empty document\n");
		xmlFreeDoc(doc);
		return -1;
	}
	if (xmlStrcmp(cur->name, (const xmlChar *) "mediawiki")) {
		fprintf(stderr,"  Document of the wrong type (root node is not 'mediawiki')\n");
		xmlFreeDoc(doc);
		return -1;
	}

	printf("  Parsed successfully\n");
	return 0;
}

//Aloca espaço e inicializa a NULL toda a tabela hash
TAD_istruct init () {
	int i;
	TAD_istruct qs = malloc (sizeof(struct TCD_istruct)); /* qs é a struct/hashTableID e está se alocar espaço para ela*/
	for(i=0;i<SIZE;i++){
		qs->hashTableID[i]=NULL;
		qs->hashTableRevID[i]=NULL;
	}
	return qs;
}

//Carrega toda a informação para a hash
TAD_istruct load(TAD_istruct qs, int nsnaps, char* snaps_paths[]) {
    char * docname;
    xmlDocPtr doc;
    xmlNodePtr nodo;

    int i;
    object test1;
    contributor test2;

    for(i=0;i<nsnaps;i++) {
        printf("Snapshot no. %d\n", i+1);
        docname = snaps_paths[i];
        doc=xmlParseFile(docname);
        parseDoc(doc);
        nodo=xmlDocGetRootElement(doc); //mediawiki
        nodo=nodo->xmlChildrenNode; //descer de mediawiki para siteinfo
        nodo=nodo->next; //avançar de siteinfo para a primeira page

        while(nodo) {
            if(!xmlStrcmp(nodo->name,(const xmlChar *)"page")) {
                test1=getObject(nodo, doc);
                insertHashID(qs, test1);
                test2=getContributor(test1);
                insertHashRevID(qs,test2);
            }
        	nodo=nodo->next;
        }
        xmlFreeDoc(doc);
        printf("  Loaded successfully\n");
    }

    printf("Load completed\n\n");
    //lePrints(qs);

    return qs;
}

// Liberta o espaço tanto da hash como das "caixas"
TAD_istruct clean(TAD_istruct qs){
    int i;
    object atm1,next1;
    contributor atm2,next2;
    for(i=0;i<SIZE;i++){
		for(atm1=qs->hashTableID[i]; atm1; atm1=next1){
        	next1=atm1->next;
		    free(atm1->title);
			free(atm1->revtimestamp);
			free(atm1->contributorname);
			free(atm1);
		}
		for(atm2=qs->hashTableRevID[i];atm2; atm2=next2){
          	next2=atm2->next;
		    free(atm2);
		}
    }
    free(qs);
    return qs;
}
