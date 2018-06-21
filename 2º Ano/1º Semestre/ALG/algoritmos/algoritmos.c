#include <stdio.h>
#include <string.h>

#define LEFT   1
#define BAL    0
#define RIGHT -1
typedef struct avl {
	int value;
	int bal;
	struct avl *left, *right;
} *AVL;


void maisProfundoAUX (AVL a, AVL new) {
	if(a==NULL) new=a;
	else {
		maisProfundoAUX (a->left,new);
		maisProfundoAUX (a->right,new);}

}
AVL maisProfundo (AVL a) {
	AVL new;
	if(a==NULL) return NULL;
	else maisProfundoAUX (a,new);

	return new;
}

AVL avl1={1,0,{NULL,NULL}};
//AVL avl2={1,0,{NULL},{1,0,{NULL},{NULL}}};
//AVL avl3={1,0,{1,0,{NULL},{NULL}},{1,0,{NULL},{NULL}}};

int main () {
	maisProfundo(NULL);
	int q;
    scanf("%d",&q);
	printf("%d\n",q);
	return 0;
}


