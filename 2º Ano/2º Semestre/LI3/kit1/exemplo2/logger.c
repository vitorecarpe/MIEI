#include <stdlib.h>
#include <stdio.h>
#include <strings.h>
#include "printer.h"

/*
Implementação log em disco
Definição da struct TCD_Printer e das funções
*/
typedef struct TCD_Printer{
	char* header;
	FILE *fp;

} TCD_Printer;


TAD_Printer init_printer(){

	TAD_Printer printer=malloc(sizeof(TCD_Printer));
	(*printer).header=malloc(sizeof(char)*20);
	strcpy(printer->header,"Logger print: ");

	printer->fp=fopen("log","a");

	return printer;
}

TAD_Printer addto_header(TAD_Printer printer, char* new_header){
	
	strcat(printer->header,new_header);

	return printer;

}

TAD_Printer print(TAD_Printer printer, char* message){

	fprintf(printer->fp,"%s %s\n",printer->header,message);
	return printer;

}


TAD_Printer destroy_printer(TAD_Printer printer){

	fclose(printer->fp);
	free(printer->header);
	free(printer);
	printer=NULL;

	return printer;
}