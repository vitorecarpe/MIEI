#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//soma os numeros pares entre x e y
int sp (int x, int y){
	int sum = 0;
	while (x < y){
		if (x%2 == 0) {sum += x;x++;}//mais eficaz um pouco
		x++;
	}
	return sum;
}

int crescente (int a[], int i, int j){
	while (j != i){
		if (a[j] < a[j-1]) return 0;
		j--;
	}
	return 1;
}

int menosFreq (int v[], int N){
	int mF = v[0],nmF = N,i,sum;
	for (i = 0;i < N;i++){
		for (sum = 1;v[i] == v[i+1];i++,sum++);
		if (sum < nmF){nmF = sum;mF = v[i];}
	}
	return mF;
}

void palavra (int N, char sopa[N][N], char f[]){
	int i = 0,l = 0,c = 0;
	while (f[i]){
		if (f[i] == 'D') {f[i] = sopa[l][c];c++;}
		else if (f[i] == 'E') {f[i] = sopa[l][c];c--;}
		else if (f[i] == 'C') {f[i] = sopa[l][c];l--;}
		else {f[i] = sopa[l][c];l++;}
		i++;
	}
	f[i] = sopa[l][c];
}

int menorElem (int v [], int N){
	int mE = v[0], i = 1;
	while (i < N){
		if (v[i] < mE) mE = v[i];
		i++;
	}
	return mE;
}

int nElems (int v [], int N, int x){
	int i = 0, sum = 0;
	while (i < N){
		if (v[i] == x) sum++;
		i++;
	}
	return sum;
}

int mediana (int v[], int N){
	int mE = menorElem (v,N);
	int sum = 0;
	while (sum < (N/2 + N%2)){
		sum += nElems (v,N,mE);
		mE++;
	}
	return mE-1;
}

int main (){
	int cres1 [6] = {2,3,4,12,9,18};
	int mFreq [6] = {2,2,4,12,12,18};
	char s [3][3] = {{'B','A','T'},{'O','T','A'},{'L','O','T'}};
	int medi [8] = {11,15,13,12,14,13,12,14};
	char c [7] = "DDBEC";
	printf("Este valor tem de ser 1: %d\n",crescente(cres1,0,3));
	printf("Este valor tem de ser 0: %d\n",crescente(cres1,0,4));
	printf("Este valor tem de ser 4: %d\n",menosFreq(mFreq,6));
	palavra(3,s,c);
	printf("Esta String tem de ser BATATA: %s\n",c);
	printf("Este valor tem de ser 13: %d\n",mediana(medi,8));
	return 0;
}