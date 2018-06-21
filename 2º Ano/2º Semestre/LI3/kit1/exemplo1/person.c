#include <stdio.h>
#include "person.h"

int get_age(Person_t p){
	return p.age;
}

Person_t change_age1(Person_t p, int new_age){

	p.age=new_age;

	return p;
}


int change_age2(Person_t *p, int new_age){

	p->age=new_age;

	return 0;
}
