#define _USE_MATH_DEFINES
#include <math.h>
#include <iostream>
#include <string>
#include <vector>
#include <stdio.h>
#include <stdlib.h>
#include "Ponto.h"
#include "PontoText.h"
#include <fstream>
#include <sstream>

using namespace std;
unsigned int *patches;
float *controlPoints;
int patchesNum;
int controlPointsNum;

// A funcionar para VBOs
void plane(float largura, float comprimento, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");
	if (f != NULL){
	
		vector<Ponto> pontos;
		vector<Ponto> normal;
		vector<PontoText> pontosText;

		for(float i=-(largura/2); i<=(largura/2); i++){
			for(float j=-(comprimento/2); j<=(comprimento/2); j++){
				pontos.push_back(Ponto(j,0.0,i));
				pontos.push_back(Ponto(j,0.0,i+1));
			}
		}
		fprintf(f, "%lu\n", pontos.size()*3); //imprimir nº de pontos no ficheiro.
		for (unsigned int ponto=0; ponto<pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}
	fclose(f);
}

// A funcionar para Triangles
void box(float x, float y, float z, int nDivisoes, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		vector<Ponto> pontos;
		float px, py, pz;

		// face frontal, x e z alteram-se e z mantém os seus valores
		for (px = -x / 2; px < x / 2; px += x / nDivisoes) {
			for (py = y / 2; py > -y / 2; py -= y / nDivisoes) {
				// primeiro
				pontos.push_back(Ponto(px, py, z / 2));
				pontos.push_back(Ponto(px, py - (y / nDivisoes), z / 2));
				pontos.push_back(Ponto(px + (x / nDivisoes), py, z / 2));
				// segundo
				pontos.push_back(Ponto(px, py - (y / nDivisoes), z / 2));
				pontos.push_back(Ponto(px + (x / nDivisoes), py - (y / nDivisoes), z / 2));
				pontos.push_back(Ponto(px + (x / nDivisoes), py, z / 2));
			}
		}
		//face lateral direita, y e z alteram-se e o valor do x mantém-se
		for ( py = y / 2; py > -y / 2; py -= y / nDivisoes) {
			for ( pz = z / 2; pz > -z / 2; pz -= z / nDivisoes) {
				// primeiro 
				pontos.push_back(Ponto(x / 2, py, pz));
				pontos.push_back(Ponto(x / 2, py - (y / nDivisoes), pz));
				pontos.push_back(Ponto(x / 2, py, pz - (z / nDivisoes)));
				// segundo 
				pontos.push_back(Ponto(x / 2, py, pz - (z / nDivisoes)));
				pontos.push_back(Ponto(x / 2, py - (y / nDivisoes), pz));
				pontos.push_back(Ponto(x / 2, py - (y / nDivisoes), pz - (z / nDivisoes)));
			}
		}
		//face de cima, x e z alteram-se e y mantém-se
		for (pz = z / 2; pz > -z / 2; pz -= z / nDivisoes) {
			for (px = -x / 2; px < x / 2; px += x / nDivisoes) {
				//primeiro
				pontos.push_back(Ponto(px, y / 2, pz));
				pontos.push_back(Ponto(px + (x / nDivisoes), y / 2, pz));
				pontos.push_back(Ponto(px, y / 2, pz - (z / nDivisoes)));
				//segundo
				pontos.push_back(Ponto(px, y / 2, pz - (z / nDivisoes)));
				pontos.push_back(Ponto(px + (x / nDivisoes), y / 2, pz));
				pontos.push_back(Ponto(px + (x / nDivisoes), y / 2, pz - (z / nDivisoes)));
			}
		}
		//face lateral esq, z e y alteram-se e o x mantém-se
		for (pz = z / 2; pz > -z / 2; pz -= z / nDivisoes) {
			for (py = y / 2; py > -y / 2; py -= y / nDivisoes) {
				// primeiro 
				pontos.push_back(Ponto(-x / 2, py, pz));
				pontos.push_back(Ponto(-x / 2, py, pz - (z / nDivisoes)));
				pontos.push_back(Ponto(-x / 2, py - (y / nDivisoes), pz));
				// segundo 
				pontos.push_back(Ponto(-x / 2, py, pz - (z / nDivisoes)));
				pontos.push_back(Ponto(-x / 2, py - (y / nDivisoes), pz - (z / nDivisoes)));
				pontos.push_back(Ponto(-x / 2, py - (y / nDivisoes), pz));
			}
		}
		//face de baixo, x e z alteram-se e o y mantém-se
		for (px = -x / 2; px < x / 2; px += x / nDivisoes) {
				for (float pz = -z / 2; pz < z / 2; pz += z / nDivisoes) {
				//primeiro
				pontos.push_back(Ponto(px, -y / 2, pz));
				pontos.push_back(Ponto(px + (x / nDivisoes), -y / 2, pz));
				pontos.push_back(Ponto(px, -y / 2, pz + (z / nDivisoes)));
				//segundo
				pontos.push_back(Ponto(px + (x / nDivisoes), -y / 2, pz));
				pontos.push_back(Ponto(px + (x / nDivisoes), -y / 2, pz + (z / nDivisoes)));
				pontos.push_back(Ponto(px, -y / 2, pz + (z / nDivisoes)));
			}
		}
		//face traseira, x e y alteram-se e z mantém-se
		for (px = -x / 2; px < x / 2; px += x / nDivisoes) {
			for (float py = y / 2; py > -y / 2; py -= y / nDivisoes) {
					// primeiro
					pontos.push_back(Ponto(px, py, -z / 2));
					pontos.push_back(Ponto(px + (x / nDivisoes), py, -z / 2));
					pontos.push_back(Ponto(px, py - (y / nDivisoes), -z / 2));
					// segundo
					pontos.push_back(Ponto(px + (x / nDivisoes), py, -z / 2));
					pontos.push_back(Ponto(px + (x / nDivisoes), py - (y / nDivisoes), -z / 2));
					pontos.push_back(Ponto(px, py - (y / nDivisoes), -z / 2));
				}
			}

		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}
}

// A funcionar para Triangles
void pyramid(float x, float y, float z, int nDivisoes, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		vector<Ponto> pontos;
		float px = x / 2;
		float py = y / 2;
		float pz = z / 2;
		float div;

		//eixo do z
		for (float divZ = -pz; divZ < pz; divZ += z/nDivisoes) {
			//eixo do x
			for (float divX = -px; divX < px; divX += x/nDivisoes) {
				//primeiro triangulo da base
				pontos.push_back(Ponto(divX, 0.0, divZ));
				pontos.push_back(Ponto(divX + (x/nDivisoes), 0.0, divZ));
				pontos.push_back(Ponto(divX + (x / nDivisoes), 0.0, divZ + (z / nDivisoes)));
				//segundo triangulo da base
				pontos.push_back(Ponto(divX + (x / nDivisoes), 0.0, divZ + (z / nDivisoes)));
				pontos.push_back(Ponto(divX, 0.0, divZ + (z/nDivisoes)));
				pontos.push_back(Ponto(divX, 0.0, divZ));
			}
		}
		//face direita
		for (div = -pz; div + (z / nDivisoes) <= pz; div += z / nDivisoes) {
			pontos.push_back(Ponto(px, 0.0, div));
			pontos.push_back(Ponto(0.0, py, 0.0));
			pontos.push_back(Ponto(px, 0.0, div +(z/nDivisoes)));
		}
		//face frente
		for (div = px; div-(x/nDivisoes) >= -px; div -= x / nDivisoes) {
			pontos.push_back(Ponto(div, 0.0, pz));
			pontos.push_back(Ponto(0.0, py, 0.0));
			pontos.push_back(Ponto(div - (x / nDivisoes), 0.0, pz));
		}
		//face detrás
		for (div = -px; div + (x / nDivisoes) <= px; div += x / nDivisoes) {
			pontos.push_back(Ponto(div, 0.0, -pz));
			pontos.push_back(Ponto(0.0, py, 0.0));
			pontos.push_back(Ponto(div + (x / nDivisoes), 0.0,-pz));
		}
		//face esquerda
		for (div = pz; div - (z / nDivisoes) >= -pz; div -= z / nDivisoes) {
			pontos.push_back(Ponto(-px, 0.0, div));
			pontos.push_back(Ponto(0.0, py, 0.0));
			pontos.push_back(Ponto(-px, 0.0, div - (z / nDivisoes)));
		}

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}
}

// A funcionar para VBOs e a gerar normais e pontos de textura
void sphere(float raio, int slices, int stacks, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");
    float x, y, z;

    if (f != NULL) {
        
        vector<Ponto> pontos;
        vector<Ponto> normais;
        vector<PontoText> texturas;
        float alpha = 2 * M_PI / slices;
        float beta = M_PI / stacks;
        float itStacks = 1.0f/((float) stacks);
        float itSlices = 1.0f/((float) slices);
        float cSlices = 0;
        float cStacks = 0;

        //metade superior da esfera
		for (int stack = stacks/2; stack >= 0; stack--){
			for (int slice=0; slice <= slices; slice++) {
				pontos.push_back(Ponto(raio * cosf(stack*beta) * sinf(slice*alpha), raio * sinf(stack*beta), raio * cosf(stack*beta) * cosf(slice*alpha)));
				pontos.push_back(Ponto(raio * cosf((stack - 1)*beta) * sinf(slice*alpha), raio * sinf((stack - 1)*beta), raio * cosf((stack - 1)*beta) * cosf(slice*alpha)));
				
				normais.push_back(Ponto(cosf(stack*beta) * sinf(slice*alpha), sinf(stack*beta), cosf(stack*beta) * cosf(slice*alpha)));
				normais.push_back(Ponto(cosf((stack - 1)*beta) * sinf(slice*alpha), sinf((stack - 1)*beta), cosf((stack - 1)*beta) * cosf(slice*alpha)));
				
				texturas.push_back(PontoText(cSlices, cStacks));
				texturas.push_back(PontoText(cSlices, cStacks - itStacks));

				cSlices = cSlices + itSlices;
			}
			cStacks = cStacks - itStacks;
			cSlices = 0.0;
		}

		cSlices = 0;
		cStacks = 0.5;

		//metade inferior da esfera
		for (int stack = 0; stack <= stacks/2; stack++){
			for (int slice=0; slice <= slices; slice++) {
				pontos.push_back(Ponto(raio * cosf(-stack * beta) * sinf(slice*alpha), raio * sinf(-stack * beta), raio * cosf(-stack * beta) * cosf(slice*alpha)));
				pontos.push_back(Ponto(raio * cosf(-(stack + 1)*beta) * sinf(slice*alpha), raio * sinf(-(stack + 1)*beta), raio * cosf(-(stack + 1)*beta) * cosf(slice*alpha)));

				normais.push_back(Ponto(cosf(-stack * beta) * sinf(slice*alpha), sinf(-stack * beta), cosf(-stack * beta) * cosf(slice*alpha)));
				normais.push_back(Ponto(cosf(-(stack + 1)*beta) * sinf(slice*alpha), sinf(-(stack + 1)*beta), cosf(-(stack + 1)*beta) * cosf(slice*alpha)));

				texturas.push_back(PontoText(cSlices, cStacks));
				texturas.push_back(PontoText(cSlices, cStacks - itStacks));

				cSlices = cSlices + itSlices;
			}
			cStacks = cStacks - itStacks;
			cSlices = 0.0;
		}
		//imprime pontos
		fprintf(f, "%lu\n", pontos.size()*3);
		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
		for (unsigned int normal = 0; normal < normais.size(); normal++) {
			fprintf(f, "%f %f %f\n", normais[normal].getX(), normais[normal].getY(), normais[normal].getZ());
		}
		for (unsigned int textura = 0; textura < texturas.size(); textura++){
			fprintf(f, "%f %f\n", texturas[textura].getX(), texturas[textura].getY());
		}
	}
}

// A funcionar para VBOs e gerar normais e pontos de textura
void cosmos(float raio, int slices, int stacks, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");
    float x, y, z;

    if (f != NULL) {
        
        vector<Ponto> pontos;
        vector<Ponto> normais;
        vector<PontoText> texturas;
        float alpha = 2 * M_PI / slices;
        float beta = M_PI / stacks;
        float itStacks = 1.0f/((float) stacks);
        float itSlices = 1.0f/((float) slices);
        printf("%f %f\n", itSlices, itStacks);
        float cSlices = 0;
        float cStacks = 0;

        //metade superior da esfera
		for (int stack = stacks/2; stack >= 0; stack--){
			for (int slice=0; slice <= slices; slice++) {
				pontos.push_back(Ponto(raio * cosf((stack - 1)*beta) * sinf(slice*alpha), raio * sinf((stack - 1)*beta), raio * cosf((stack - 1)*beta) * cosf(slice*alpha)));
				pontos.push_back(Ponto(raio * cosf(stack*beta) * sinf(slice*alpha), raio * sinf(stack*beta), raio * cosf(stack*beta) * cosf(slice*alpha)));
				
				normais.push_back(Ponto(cosf((stack - 1)*beta) * sinf(slice*alpha), sinf((stack - 1)*beta), cosf((stack - 1)*beta) * cosf(slice*alpha)));
				normais.push_back(Ponto(cosf(stack*beta) * sinf(slice*alpha), sinf(stack*beta), cosf(stack*beta) * cosf(slice*alpha)));
				
				texturas.push_back(PontoText(cSlices, cStacks - itStacks));
				texturas.push_back(PontoText(cSlices, cStacks));
				

				cSlices = cSlices + itSlices;
			}
			cStacks = cStacks - itStacks;
			cSlices = 0.0;
		}

		cSlices = 0;
		cStacks = 0.5;

		//metade inferior da esfera
		for (int stack = 0; stack <= stacks/2; stack++){
			for (int slice=0; slice <= slices; slice++) {
				pontos.push_back(Ponto(raio * cosf(-(stack + 1)*beta) * sinf(slice*alpha), raio * sinf(-(stack + 1)*beta), raio * cosf(-(stack + 1)*beta) * cosf(slice*alpha)));
				pontos.push_back(Ponto(raio * cosf(-stack * beta) * sinf(slice*alpha), raio * sinf(-stack * beta), raio * cosf(-stack * beta) * cosf(slice*alpha)));

				normais.push_back(Ponto(cosf(-stack * beta) * sinf(slice*alpha), sinf(-stack * beta), cosf(-stack * beta) * cosf(slice*alpha)));
				normais.push_back(Ponto(cosf(-(stack + 1)*beta) * sinf(slice*alpha), sinf(-(stack + 1)*beta), cosf(-(stack + 1)*beta) * cosf(slice*alpha)));

				texturas.push_back(PontoText(cSlices, cStacks - itStacks));
				texturas.push_back(PontoText(cSlices, cStacks));
				

				cSlices = cSlices + itSlices;
			}
			cStacks = cStacks - itStacks;
			cSlices = 0.0;
		}
		//imprime pontos
		fprintf(f, "%lu\n", pontos.size()*3);
		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
		for (unsigned int normal = 0; normal < normais.size(); normal++) {
			fprintf(f, "%f %f %f\n", normais[normal].getX(), normais[normal].getY(), normais[normal].getZ());
		}
		for (unsigned int textura = 0; textura < texturas.size(); textura++){
			fprintf(f, "%f %f\n", texturas[textura].getX(), texturas[textura].getY());
		}
	}
}

// A funcionar para Triangles
void circle(float raio, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		vector<Ponto> pontos;
		float x, z;
		//numero de divisões do circulo
		float alpha = 2 * M_PI / slices;

		for (float div = 0; div <= 2 * M_PI + alpha; div += alpha) {
			x = raio * sin(div);
			z = raio * cos(div);
			pontos.push_back(Ponto(x, 0, z));
		}

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

		for (unsigned int ponto = 0; ponto < pontos.size()-1; ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
			fprintf(f, "%f %f %f\n", pontos[ponto + 1].getX(), pontos[ponto + 1].getY(), pontos[ponto + 1].getZ());
			fprintf(f, "0.0 0.0 0.0\n");
		}
	}
}

// A funcionar para Triangles
void cylinder(float raio, float altura, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		// vetor onde os pontos do plano vão ser guardados
		vector<Ponto> pontos1, pontos2,pontos3;
		float x, z;
		float pi = 3.1415926F;
		// numero de divisões do circulo
		float alpha = 2 * pi / slices;

		// circulo centrado na origem
		for (float div = 0; div <= 2 * pi + alpha; div += alpha) {
			x = raio * sin(div);
			z = raio * cos(div);
			pontos1.push_back(Ponto(x, 0, z));
		}
		// circulo topo
		for (float div = 0; div <= 2 * pi + alpha; div += alpha) {
			x = raio * sin(div);
			z = raio * cos(div);
			pontos2.push_back(Ponto(x, altura, z));
		}

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

		// printa o circulo da base
		for (unsigned int ponto = 0; ponto < pontos1.size() - 1; ponto++) {
			fprintf(f, "%f %f %f\n", pontos1[ponto].getX(), pontos1[ponto].getY(), pontos1[ponto].getZ());
			fprintf(f, "0.0 0.0 0.0\n");
			fprintf(f, "%f %f %f\n", pontos1[ponto + 1].getX(), pontos1[ponto + 1].getY(), pontos1[ponto + 1].getZ());
		}
		// printa o circulo do topo
		for (unsigned int ponto = 0; ponto < pontos2.size() - 1; ponto++) {
			fprintf(f, "%f %f %f\n", pontos2[ponto].getX(), pontos2[ponto].getY(), pontos2[ponto].getZ());
			fprintf(f, "%f %f %f\n", pontos2[ponto + 1].getX(), pontos2[ponto + 1].getY(), pontos2[ponto + 1].getZ());
			fprintf(f, "0.0 %f 0.0\n",altura);
		}
		// lados superior do cilindro
		for (unsigned int i = 0; i < pontos2.size()-1; i++) {
			fprintf(f, "%f %f %f\n", pontos2[i].getX(), pontos2[i].getY(), pontos2[i].getZ());
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), pontos1[i].getY(), pontos1[i].getZ());
			fprintf(f, "%f %f %f\n", pontos2[i+1].getX(), pontos2[i+1].getY(), pontos2[i+1].getZ());
		}
		// lados inferiores do cilindro
		for (unsigned int i = 0; i < pontos1.size()-1; i++) {
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), pontos1[i].getY(), pontos1[i].getZ());
			fprintf(f, "%f %f %f\n", pontos1[i + 1].getX(), pontos1[i + 1].getY(), pontos1[i + 1].getZ());
			fprintf(f, "%f %f %f\n", pontos2[i + 1].getX(), pontos2[i + 1].getY(), pontos2[i + 1].getZ());
		}
	}
}

// A funcionar para VBOs
void circumference(float raio, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		// vetor onde os pontos do plano vão ser guardados
		vector<Ponto> pontos;
		float x, z;
		// numero de divisões do circulo
		float alpha = 2 * M_PI / slices;

		// pontos da circunferência
		for (float div = 0; div <= 2 * M_PI + alpha; div += alpha) {
			x = raio * sin(div);
			z = raio * cos(div);
			pontos.push_back(Ponto(x, 0, z));
		}

		fprintf(f, "%lu\n", pontos.size()*3);
		for (unsigned int i = 0; i < pontos.size(); i++) {
			fprintf(f, "%f %f %f\n", pontos[i].getX(), pontos[i].getY(), pontos[i].getZ());
		}
	}
}

// A funcionar para VBOs e a gerar normais e pontos de textura
void anel(float raioint, float raioext, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		// vetor onde os pontos do plano vão ser guardados
		vector<Ponto> pontos1, pontos2;
		vector<Ponto> normais;
		float x, z;
		// numero de divisões do circulo
		float alpha = 2 * M_PI / slices;

		// circunferência interior
		for (float div = 0; div <= 2 * M_PI + alpha; div += alpha) {
			x = raioint * sin(div);
			z = raioint * cos(div);
			pontos1.push_back(Ponto(x, 0, z));
		}
		// circunferência exterior
		for (float div = 0; div <= 2 * M_PI + alpha; div += alpha) {
			x = raioext * sin(div);
			z = raioext * cos(div);
			pontos2.push_back(Ponto(x, 0, z));

		}

		// printa o numeros de pontos
		fprintf(f, "%lu\n", (pontos1.size()+pontos2.size())*6);
		// PONTOS
		// parte de baixo do anel
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), pontos1[i].getY(), pontos1[i].getZ());
			fprintf(f, "%f %f %f\n", pontos2[i].getX(), pontos2[i].getY(), pontos2[i].getZ());
		}
		//parte de cima do anel
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f %f\n", pontos2[i].getX(), pontos2[i].getY(), pontos2[i].getZ());
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), pontos1[i].getY(), pontos1[i].getZ());
		}
		// NORMAIS
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), 1.0f, pontos1[i].getZ());
			fprintf(f, "%f %f %f\n", pontos2[i].getX(), 1.0f, pontos2[i].getZ());
		}
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f %f\n", pontos1[i].getX(), -1.0f, pontos1[i].getZ());
			fprintf(f, "%f %f %f\n", pontos2[i].getX(), -1.0f, pontos2[i].getZ());
		}
		// PONTOS DE TEXTURA
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f\n", 0.1f, 0.5f);
			fprintf(f, "%f %f\n", 0.9f, 0.5f);
		}
		for (unsigned int i = 0; i < pontos1.size(); i++) {
			fprintf(f, "%f %f\n", 0.1f, 0.5f);
			fprintf(f, "%f %f\n", 0.9f, 0.5f);
		}
	}
}

// A funcionar para Triangles
void cone(float raio, float altura,int slices, int stacks, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		// vetor onde os pontos do plano vão ser guardados
		vector<Ponto> pontos;
		int slice, stack;

		float alpha = 2 * M_PI / slices;
		float beta = M_PI / stacks;

		// circulo base
		for (slice = 0; slice <= slices; slice++) {
			pontos.push_back(Ponto(raio * sin(alpha * slice), 0, raio * cos(alpha *slice)));
			pontos.push_back(Ponto(0, 0, 0));
			pontos.push_back(Ponto(raio * sin(alpha * (slice + 1)), 0, raio * cos(alpha *(slice + 1))));

			//lados do cone
			float stackAntiga = 0;
			float raioAntigo = 0;

			for (stack = 0; stack < stacks; stack++) {
				float alturaStack = altura / stacks * stack;
				float novoRaio = (altura - alturaStack) * raio / altura;
			
				//triangulo cima
				pontos.push_back(Ponto(novoRaio * sin(alpha*slice), alturaStack, novoRaio * cos(alpha*slice)));
				pontos.push_back(Ponto(raioAntigo * sin(alpha*slice), stackAntiga, raioAntigo * cos(alpha*slice)));
				pontos.push_back(Ponto(novoRaio * sin(alpha*(slice + 1)), alturaStack, novoRaio * cos(alpha*(slice + 1))));
				// traingulo baixo
				pontos.push_back(Ponto(raioAntigo * sin(alpha*slice), stackAntiga, raioAntigo * cos(alpha*slice)));
				pontos.push_back(Ponto(raioAntigo * sin(alpha*(slice + 1)), stackAntiga, raioAntigo * cos(alpha*(slice + 1))));
				pontos.push_back(Ponto(novoRaio * sin(alpha *(slice+1)), alturaStack, novoRaio * cos(alpha *(slice + 1))));
				
				stackAntiga = alturaStack;
				raioAntigo = novoRaio;
			}
			//ponta do cone
			pontos.push_back(Ponto(0.0, altura, 0.0));
			pontos.push_back(Ponto(raioAntigo * sin(alpha*slice), stackAntiga, raioAntigo * cos(alpha*slice)));
			pontos.push_back(Ponto(raioAntigo * sin(alpha *(slice + 1)), stackAntiga, raioAntigo * cos(alpha *(slice + 1))));
		}

		// printa o numeros de pontos
		for (unsigned int ponto = 0; ponto < pontos.size() - 1; ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}
}

/* BEZIER PATCHES - A funcionar para Triangles e a gerar normais
   e pontos de textura embora erradamente... */
void patchToArrays(string filename) {

	ifstream file(filename.c_str());
	string firstLine, line;
	
	// Colocar valor dos indices de cada patch no array.
	getline(file, firstLine);
	patchesNum = atoi(firstLine.c_str());
	patches = (unsigned int*) malloc(sizeof(unsigned int) * 16 * patchesNum);
	for (int p = 0; p < patchesNum; p++) {
		getline(file, line);
		istringstream indexes(line);
		string indexP;
		for (int i=0; i<16 && getline(indexes, indexP, ','); i++){ //getline(char, streamsize, delimitador)
			patches[p*16+i] = atoi(indexP.c_str());
		}
	}

	// Colocar valor dos pontos de controlo no array.
	getline(file, firstLine);
	controlPointsNum = atoi(firstLine.c_str());
	controlPoints = (float *)malloc(sizeof(float) * 3 * controlPointsNum);
	for (int cp = 0; cp < controlPointsNum; cp++) {
		getline(file, line);
		istringstream indexes(line);
		string indexCP;
		for (int i=0; i<3 && getline(indexes, indexCP, ','); i++){
			controlPoints[cp*3+i] = (float)atof(indexCP.c_str());
		}
	}
}
Ponto pontoBezier(int p, float u, float v) {
	Ponto ponto = Ponto(0.0, 0.0, 0.0);

	// Polinomio de Bernstein
	float bernsteinU[4] = { powf(1-u, 3), 3 * u * powf(1-u, 2), 3 * powf(u, 2) * (1-u), powf(u, 3) };
	float bernsteinV[4] = { powf(1-v, 3), 3 * v * powf(1-v, 2), 3 * powf(v, 2) * (1-v), powf(v, 3) };
	//powf é a potencia (de floats) sendo que o 1º arg é a base e o 2º é o expoente.

	for (int j=0; j<4; j++){
		for (int i=0; i<4; i++) {

			int indexPatch = j*4+i;
			int indexCP = patches[p*16 + indexPatch];
			ponto = Ponto(ponto.getX() + controlPoints[indexCP * 3 + 0] * bernsteinU[j] * bernsteinV[i],
			              ponto.getY() + controlPoints[indexCP * 3 + 1] * bernsteinU[j] * bernsteinV[i],
			              ponto.getZ() + controlPoints[indexCP * 3 + 2] * bernsteinU[j] * bernsteinV[i]);
		}
	}

	return ponto;
}
Ponto pontoBezierNormal(int p, float u, float v) {
	Ponto ponto = Ponto(0.0, 0.0, 0.0);

	for (int j=0; j<4; j++){
		for (int i=0; i<4; i++) {

			int indexPatch = j*4+i;
			int indexCP = patches[p*16 + indexPatch];
			ponto = Ponto(ponto.getX() + controlPoints[indexCP * 3 + 0],
			              ponto.getY() + controlPoints[indexCP * 3 + 1],
			              ponto.getZ() + controlPoints[indexCP * 3 + 2]);
		}
	}
	return ponto;
}
void bezier(string patchFile, string filename, int tesselation) {
	patchToArrays(patchFile);
	vector<Ponto> pontos;
	vector<Ponto> normais;
	vector<PontoText> texturas;
	float inc = 1.0f/tesselation;

	for (int p=0; p<patchesNum; p++) {

		for (int tv = 0; tv<tesselation; tv++) {
			float v = (float) tv/tesselation;
			
			for (int tu = 0; tu < tesselation; tu++) {
				float u = (float) tu/tesselation;

				// triângulo superior
				pontos.push_back(pontoBezier(p, (u+inc), (v+inc)));
				pontos.push_back(pontoBezier(p, u, v));
				pontos.push_back(pontoBezier(p, u, (v+inc)));
				
				// triângulo inferior
				pontos.push_back(pontoBezier(p, (u+inc), (v+inc)));
				pontos.push_back(pontoBezier(p, (u+inc), v));
				pontos.push_back(pontoBezier(p, u, v));
				
				normais.push_back(pontoBezierNormal(p, (u+inc), (v+inc)));
				normais.push_back(pontoBezierNormal(p, u, v));
				normais.push_back(pontoBezierNormal(p, (u+inc), v));

				normais.push_back(pontoBezierNormal(p, (u+inc), (v+inc)));
				normais.push_back(pontoBezierNormal(p, u, (v+inc)));
				normais.push_back(pontoBezierNormal(p, u, v));

				texturas.push_back(PontoText((u+inc), (v+inc)));
				texturas.push_back(PontoText(u, v));
				texturas.push_back(PontoText(u, (v+inc)));

				texturas.push_back(PontoText((u+inc), (v+inc)));
				texturas.push_back(PontoText((u+inc), v));
				texturas.push_back(PontoText(u, v));
			}
		}
	}

	FILE *f;
	f = fopen(filename.c_str(), "w");

	// Printar os pontos no ficheiro .3d
	for (int ponto=0; ponto<pontos.size(); ponto++) {
		fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
	}
	for (int ponto=0; ponto<normais.size(); ponto++) {
		fprintf(f, "%f %f %f\n", normais[ponto].getX(), normais[ponto].getY(), normais[ponto].getZ());
	}
	for (int ponto=0; ponto<texturas.size(); ponto++) {
		fprintf(f, "%f %f\n", texturas[ponto].getX(), texturas[ponto].getY());
	}

	fclose(f);

	free(patches);
	free(controlPoints);
}

int main(int argc, char* argv[]) {
	if (argc < 2) {
		printf("Parametros Inválidos.\nExiting...\n");
		return -1;
	}

	string tipo = argv[1];
	string generated_path = "../../xml_models/";

	// recebe largura do plano(float) e filename
	if (tipo == "plane") { 
		float largura = stof(argv[2]);
		float comprimento = stof(argv[3]);
		string filename = argv[4];
		filename = generated_path + filename;

		plane(largura, comprimento, filename);
		return 0;
	}

	// recebe x,y,z(floats), numero divisões(int) e filename
	if (tipo == "box") { 
		float x = stof(argv[2]);
		float y = stof(argv[3]);
		float z = stof(argv[4]);
		int nDivisoes = stoi(argv[5]);
		string filename = argv[6];
		filename = generated_path + filename;

		box(x, y, z, nDivisoes, filename);
		return 0;
	}

	// recebe x,y,z(floats), numero divisões(int) e filename
	if (tipo == "pyramid") { 
		float x = stof(argv[2]);
		float y = stof(argv[3]);
		float z = stof(argv[4]);
		int nDivisoes = stof(argv[5]);
		string filename = argv[6];
		filename = generated_path + filename;

		pyramid(x, z, y, nDivisoes, filename);
		return 0;
	}

	// recebe os raios do anel (floats), numero divisoes(int) e filename
	if (tipo == "anel") { 
		float raioint = stof(argv[2]);
		float raioext = stof(argv[3]);
		int nDivisoes = stof(argv[4]);
		string filename = argv[5];
		filename = generated_path + filename;

		anel(raioint, raioext, nDivisoes, filename);
		return 0;
	}
	// recebe o raio da circunferência (floats), numero divisoes(int) e filename
	if (tipo == "orbita") { 
		float raio = stof(argv[2]);
		int nDivisoes = stof(argv[3]);
		string filename = argv[4];
		filename = generated_path + filename;

		circumference(raio, nDivisoes, filename);
		return 0;
	}

	// recebe o raio(float), slices e stacks(int)
	if (tipo == "sphere") { 
		float raio = stof(argv[2]);
		int slices = stoi(argv[3]);
		int stacks = stoi(argv[4]);
		string filename = argv[5];
		filename = generated_path + filename;

		//slices e stacks nao podem ser zero porque vamos as usar para dividir
		if (slices > 1 && stacks > 0) {
			sphere(raio, slices, stacks, filename);
			return 0;
		}
		else {
			printf("Parametros Inválidos.\nExiting...\n");
			return -1;
		}
		return 0;
	}

	// recebe o raio(float), slices e stacks(int)
	if (tipo == "cosmos") { 
		float raio = stof(argv[2]);
		int slices = stoi(argv[3]);
		int stacks = stoi(argv[4]);
		string filename = argv[5];
		filename = generated_path + filename;

		//slices e stacks nao podem ser zero porque vamos as usar para dividir
		if (slices > 1 && stacks > 0) {
			cosmos(raio, slices, stacks, filename);
			return 0;
		}
		else {
			printf("Parametros Inválidos.\nExiting...\n");
			return -1;
		}
		return 0;
	}

	// recebe o raio(float) e as slices(int) e filename
	if (tipo == "circle") { 
		float raio = stof(argv[2]);
		int slices = stoi(argv[3]);
		string filename = argv[4];
		filename = generated_path + filename;

		if (slices > 0) {
			circle(raio, slices, filename);
			return 0;
		}
		else {
			printf("Parametros Inválidos.\nExiting...\n");
			return -1;
		}
	}

	// recebe o raio(float) , altura e as slices(int) e filename
	if (tipo == "cylinder") { 
		float raio = stof(argv[2]);
		float altura = stof(argv[3]);
		int slices = stoi(argv[4]);
		string filename = argv[5];
		filename = generated_path + filename;

		if (slices > 0) {
			cylinder(raio, altura, slices, filename);
			return 0;
		}
		else {
			printf("Parametros Inválidos.\nExiting...\n");
			return -1;
		}
	}

	// recebe o raio(float), altura, slices e stacks(int) e filename
	if (tipo == "cone") { 
		float raio = stof(argv[2]);
		float altura = stof(argv[3]);
		int slices = stoi(argv[4]);
		int stacks = stoi(argv[5]);
		string filename = argv[6];
		filename = generated_path + filename;

		//slices e stacks nap podem ser zero porque vamos as usar para dividir
		if (slices > 1 && stacks > 0) {
			cone(raio, altura, slices, stacks, filename);
			return 0;
		}
		else {
			printf("Parametros Inválidos.\nExiting...\n");
			return -1;
		}
		return 0;
	}

	if(tipo == "bezier") {
		string patchFile = argv[2];
		string filename  = argv[3];
		int tesselation  = stoi(argv[4]); 
		patchFile = generated_path + patchFile;
		filename = generated_path + filename;
		
		bezier(patchFile, filename, tesselation);

		return 0;
	}

	// Se chegar a este ponto, é porque não especificou direito o que queria generar
	printf("Parametros Inválidos.\nExiting...\n");
	return -1;
}