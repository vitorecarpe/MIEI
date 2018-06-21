#include <stdlib.h>
#include <stdio.h>
#include <strings.h>
#include "printer.h"

/*
escrita para stdout
Definição da struct TCD_Printer e das funções
*/

typedef struct TCD_Printer{
	char* header;
} TCD_Printer;


TAD_Printer init_printer(){

	TAD_Printer printer=malloc(sizeof(TCD_Printer));
	(*printer).header=malloc(sizeof(char)*50);
	strcpy(printer->header,"Stdout print: ");

	return printer;
}

TAD_Printer addto_header(TAD_Printer printer, char* new_header){
	
	strcat(printer->header,new_header);

	return printer;

}

TAD_Printer print(TAD_Printer printer,char* message){

	printf("%s %s\n",printer->header,message);

	return printer;

}


TAD_Printer destroy_printer(TAD_Printer printer){

	free(printer->header);
	free(printer);
	printer=NULL;

	return printer;
}