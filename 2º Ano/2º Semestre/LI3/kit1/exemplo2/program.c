#include <stdio.h>

#include "printer.h"

/*

1 - diferentes implementações mesma interface .h e mesma declaração de struct
2 - compilação múltiplos módulos

*/


int main(int argc, char* argv[]){
	
	
	TAD_Printer printer = init_printer();
	printer = addto_header(printer, "My header :");
	print(printer,"hello world");
	printer = destroy_printer(printer);
	
	return 0;
}