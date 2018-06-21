

//Neste exemplo a struct está no próprio .h e tem já campos definidos.
typedef struct Person{
	char* name;
	int age;
} Person_t;


//API
int get_age(Person_t p);
Person_t change_age1(Person_t p, int new_age);
int change_age2(Person_t *p, int new_age);
