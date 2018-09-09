#define _USE_MATH_DEFINES
#include <math.h>
#include <iostream>
#include <string>
#include <vector>
#include <stdio.h>
#include <stdlib.h>
#include "Ponto.h"

using namespace std;


void plane(float largura, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");

	if (f != NULL){
		// vetor onde os pontos do plano vão ser guardados
		std::vector<Ponto> pontos;
		float lado = largura / 2;

		//primeiro triangulo, virado para cima
		pontos.push_back(Ponto(lado,0.0,lado));
		pontos.push_back(Ponto(lado,0.0,-lado));
		pontos.push_back(Ponto(-lado,0.0,lado));
		//segundo triangulo, virado para cima
		pontos.push_back(Ponto(-lado, 0.0, -lado));
		pontos.push_back(Ponto(-lado, 0.0, lado));
		pontos.push_back(Ponto(lado, 0.0, -lado));

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}

	fclose(f);
}

void box(float x, float y, float z, int nDivisoes, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		std::vector<Ponto> pontos;
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

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

		for (unsigned int ponto = 0; ponto < pontos.size(); ponto++) {
			fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
		}
	}
}

void pyramid(float x, float y, float z, int nDivisoes, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		std::vector<Ponto> pontos;
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

void sphere(float raio, int slices, int stacks, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");

    if (f != NULL) {
        std::vector<Ponto> pontos;

        float alpha = 2 * M_PI / slices;
        float beta = M_PI / stacks;

        for (int slice=0; slice <= slices; slice++) {
            //topo da esfera
            float aux1 = (M_PI/2) - beta;
            pontos.push_back(Ponto(0, raio, 0));
            pontos.push_back(Ponto(raio * cosf(aux1) * sinf(slice*alpha) ,raio * sinf(aux1), raio * cosf(aux1) * cosf(slice*alpha)));
            pontos.push_back(Ponto(raio * cosf(aux1) * sinf((slice + 1)*alpha), raio * sinf(aux1), raio * cosf(aux1) * cosf((slice + 1)*alpha)));
            //base da esfera
            float aux2 = (-M_PI / 2) + beta;
            pontos.push_back(Ponto(0, -raio, 0));
            pontos.push_back(Ponto(raio * cosf(aux2) * sinf((slice + 1)*alpha), raio * sinf(aux2), raio * cosf(aux2) * cosf((slice + 1)*alpha)));
            pontos.push_back(Ponto(raio * cosf(aux2) * sinf(slice*alpha), raio * sinf(aux2), raio * cosf(aux2) * cosf(slice*alpha)));
            
            //lados da esfera
            for (int stack = 0; stack < stacks/2 - 1; stack++) {
                //metade superior
                //triangulo inferior
                pontos.push_back(Ponto(raio * cosf(stack*beta) * sinf(slice*alpha), raio * sinf(stack*beta), raio * cosf(stack*beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf(stack*beta) * sinf((slice + 1)*alpha), raio * sinf(stack*beta), raio * cosf(stack*beta) * cosf((slice + 1)*alpha)));
                pontos.push_back(Ponto(raio * cosf((stack + 1)*beta) * sinf((slice + 1)*alpha), raio * sinf((stack + 1)*beta), raio * cosf((stack + 1)*beta) * cosf((slice + 1)*alpha)));
                //triangulo superior
                pontos.push_back(Ponto(raio * cosf((stack + 1)*beta) * sinf(slice*alpha), raio * sinf((stack + 1)*beta), raio * cosf((stack + 1)*beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf(stack*beta) * sinf(slice*alpha), raio * sinf(stack*beta), raio * cosf(stack*beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf((stack + 1)*beta) * sinf((slice + 1)*alpha), raio * sinf((stack + 1)*beta), raio * cosf((stack + 1)*beta) * cosf((slice + 1)*alpha)));
                
                //metade inferior
                //triangulo inferior
                pontos.push_back(Ponto(raio * cosf(-(stack + 1)*beta) * sinf(slice*alpha), raio * sinf(-(stack + 1)*beta), raio * cosf(-(stack + 1)*beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf(-(stack + 1)*beta) * sinf((slice + 1)*alpha), raio * sinf(-(stack + 1)*beta), raio * cosf(-(stack + 1)*beta) * cosf((slice + 1)*alpha)));
                pontos.push_back(Ponto(raio * cosf(-stack * beta) * sinf((slice + 1)*alpha), raio * sinf(-stack * beta), raio * cosf(-stack * beta) * cosf((slice + 1)*alpha)));
                //triangulo superior
                pontos.push_back(Ponto(raio * cosf(-stack * beta) * sinf(slice*alpha), raio * sinf(-stack * beta), raio * cosf(-stack * beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf(-(stack + 1)*beta) * sinf(slice*alpha), raio * sinf(-(stack + 1)*beta), raio * cosf(-(stack + 1)*beta) * cosf(slice*alpha)));
                pontos.push_back(Ponto(raio * cosf(-stack * beta) * sinf((slice + 1)*alpha), raio * sinf(-stack * beta), raio * cosf(-stack * beta) * cosf((slice + 1)*alpha)));
            }

		// printa o numeros de pontos
		//fprintf(f, "%d\n", pontos.size());

        }
        for (unsigned int ponto = 0; ponto < pontos.size() - 1; ponto++) {
            fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
        }
    }
}

void circle(float raio, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		std::vector<Ponto> pontos;
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

void cylinder(float raio, float altura, int slices, string filename) {
	FILE *f;
	f = fopen(filename.c_str(), "w");

	if (f != NULL) {
		// vetor onde os pontos do plano vão ser guardados
		std::vector<Ponto> pontos1, pontos2,pontos3;
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

void cone(float raio, float altura,int slices, int stacks, string filename) {
    FILE *f;
    f = fopen(filename.c_str(), "w");

    if (f != NULL) {
        // vetor onde os pontos do plano vão ser guardados
        std::vector<Ponto> pontos;
        int slice, stack;

        float alpha = 2 * M_PI / slices;
        float beta = M_PI / stacks;

        // circulo base
        for (slice = 0; slice < slices; slice++) {
            
            pontos.push_back(Ponto(raio * sin(alpha * slice), 0, raio * cos(alpha *slice)));
            pontos.push_back(Ponto(0, 0, 0));
            pontos.push_back(Ponto(raio * sin(alpha * (slice + 1)), 0, raio * cos(alpha *(slice + 1))));
            
            float novoRaio, alturaStack, nextStack, nextRaio;
            //lados do cone
            for (stack = 0; stack < stacks; stack++) {
                alturaStack = (altura / stacks) * stack;
                novoRaio = (altura - alturaStack) * raio / altura;
                nextStack = (altura / stacks) * (stack +1);
                nextRaio = (altura - nextStack) * raio / altura;

            
                //triangulo cima
                pontos.push_back(Ponto(nextRaio * sin(alpha*slice), nextStack, nextRaio * cos(alpha*slice)));
                pontos.push_back(Ponto(novoRaio * sin(alpha*slice), alturaStack, novoRaio * cos(alpha*slice)));
                pontos.push_back(Ponto(nextRaio * sin(alpha*(slice + 1)), nextStack, nextRaio * cos(alpha*(slice + 1))));
                // traingulo baixo
                pontos.push_back(Ponto(novoRaio * sin(alpha*slice), alturaStack, novoRaio * cos(alpha*slice)));
                pontos.push_back(Ponto(novoRaio * sin(alpha*(slice + 1)), alturaStack, novoRaio * cos(alpha*(slice + 1))));
                pontos.push_back(Ponto(nextRaio * sin(alpha *(slice+1)), nextStack, nextRaio * cos(alpha *(slice + 1))));
            
            }
            
            //ponta do cone
            pontos.push_back(Ponto(0.0, altura, 0.0));
            pontos.push_back(Ponto(novoRaio * sin(alpha*slice), alturaStack, novoRaio * cos(alpha*slice)));
            pontos.push_back(Ponto(novoRaio * sin(alpha *(slice + 1)), alturaStack, novoRaio * cos(alpha *(slice + 1))));
            
        }
        
        // printa o numeros de pontos
        //fprintf(f, "%d\n", pontos.size());

        for (unsigned int ponto = 0; ponto < pontos.size() - 1; ponto++) {
            fprintf(f, "%f %f %f\n", pontos[ponto].getX(), pontos[ponto].getY(), pontos[ponto].getZ());
        }
    }
}

int main(int argc, char* argv[]) {
	if (argc < 2) {
		printf("Parametros Inválidos.\nExiting...");
		return -1;
	}

	string tipo = argv[1];
	string generated_path = "../../generated3d/";

	// recebe largura do plano(float) e filename
	if (tipo == "plane") { 
		float largura = std::stof(argv[2]);
		string filename = argv[3];
		filename = generated_path + filename;

		plane(largura, filename);
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
			printf("Parametros Inválidos.\nExiting...");
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
			printf("Parametros Inválidos.\nExiting...");
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
			printf("Parametros Inválidos.\nExiting...");
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
			printf("Parametros Inválidos.\nExiting...");
			return -1;
		}
		return 0;
	}

	// Se chegar a este ponto, é porque não especificou direito o que queria generar
	printf("Parametros Inválidos.\nExiting...");
	return -1;
}