#include <stdio.h>
#include <string.h>

int inv(int x, int n){
	int sum=0;
	for(int i=0; i<=n; i++){
		sum+=(1-x)^i;
	}
	return sum;
}

int main(){
	printf("inv(1.5,3) = %f\n",inv(1.5,3));
	return 0;
}


