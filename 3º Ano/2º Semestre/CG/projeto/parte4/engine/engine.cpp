#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <stdlib.h>
#include <stdio.h>
#include "tinyxml2.cpp"
#include "tinyxml2.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
#include <IL/il.h> 

using namespace tinyxml2;
using namespace std;

class Coordinate {
public:
	bool empty = true;
	float x, y, z;
};
class TexCoordinate { 
public: 
   bool empty = true; 
   float x, y; 
}; 
class Color {
public:
	bool empty = true;
	float r, g, b;
};
class Rotate {
public:
	bool empty = true;
	float x, y, z, angle=-1, time=-1;
};
class Translate {
public:
	bool empty = true;
	float time;
	vector<Coordinate> catmullPoints;
	float catmulls[50][3];
};
class Material {
public:
	float *diffuse=NULL, *specular=NULL, *emissive=NULL, *ambient=NULL;
};
class Light {
public:
	string type;
	float pos[4];
	Material color;
	float cons=NULL, quad=NULL, linear=NULL, spotCutOff=NULL, *spotDirection=NULL, spotExponent=NULL;
	unsigned int id;
};
class Figure {
public:
	vector<Coordinate> figuras;
	vector<Coordinate> normais;
	vector<TexCoordinate> texturas;
	Coordinate scale;
	Rotate rotate;
	Translate translate;
	/* int buffer - indica se é para desenhar com buffer ou com triangulos
	(1 para triangulos | outro valor superior para buffer, sendo que esse
	valor é o numero de vertices). */
	int buffer, norm, text; 
	GLuint texture;
	Color color;
	Material material;
};
class Tree {
public:
	Figure figure;
	vector<Tree> subtrees;
	vector<Light> lights;
};

unsigned int nodeN = 0;
float px = 0;
float py = 0;
float pz = 0;

float gt = 0.0;
float dx = 0;
float dy = 0;
float dz = 0;
float clocks = 0;

float alpha = 0;
float beta = M_PI / 4;
float radium = 20.0;

// Modo de visualizaçao (FILL,LINE,POINT)
GLenum OPTION = GL_FILL;
// Arvore de armazenamento das figuras a desenhar
Tree tree;


// Funções auxiliares (catmull, etc)
Translate vectorToArray(Translate trans) {
	//transforma o vetor num array. 
	for (unsigned int it = 0; it < trans.catmullPoints.size(); it++) {
		Coordinate coord = trans.catmullPoints.at(it);
		trans.catmulls[it][0] = coord.x;
		trans.catmulls[it][1] = coord.y;
		trans.catmulls[it][2] = coord.z;
	}
	return trans;
}

void multMatrixVector(float *m, float *v, float *res) {
	for (int j = 0; j < 4; ++j) {
		res[j] = 0;
		for (int k = 0; k < 4; ++k)
			res[j] += v[k] * m[j * 4 + k];
	}
}

void getCatmullRomPoint(float t, float *p0, float *p1, float *p2, float *p3, float *pos, float *deriv) {

	// catmull-rom matrix
	float m[4][4] = {	{-0.5f,  1.5f, -1.5f,  0.5f},
						{ 1.0f, -2.5f,  2.0f, -0.5f},
						{-0.5f,  0.0f,  0.5f,  0.0f},
						{ 0.0f,  1.0f,  0.0f,  0.0f}};
			
	// Compute A = M * P
	float a[3][4];

	float px[4] = { p0[0], p1[0], p2[0], p3[0] };
	float py[4] = { p0[1], p1[1], p2[1], p3[1] };
	float pz[4] = { p0[2], p1[2], p2[2], p3[2] };

	multMatrixVector(*m, px, a[0]);
	multMatrixVector(*m, py, a[1]);
	multMatrixVector(*m, pz, a[2]);

	// Compute pos = T * A
	float tv[4] =  { t*t*t, t*t, t, 1 };
	float tvd[4] = { 3*t*t, 2*t, 1, 0 };

	pos[0] = tv[0]*a[0][0] + tv[1]*a[0][1] + tv[2]*a[0][2] + tv[3]*a[0][3];
	pos[1] = tv[0]*a[1][0] + tv[1]*a[1][1] + tv[2]*a[1][2] + tv[3]*a[1][3];
	pos[2] = tv[0]*a[2][0] + tv[1]*a[2][1] + tv[2]*a[2][2] + tv[3]*a[2][3];
	
	// compute deriv = T' * A
	deriv[0] = tvd[0]*a[0][0] + tvd[1]*a[0][1] + tvd[2]*a[0][2] + tvd[3]*a[0][3];
	deriv[1] = tvd[0]*a[1][0] + tvd[1]*a[1][1] + tvd[2]*a[1][2] + tvd[3]*a[1][3];
	deriv[2] = tvd[0]*a[2][0] + tvd[1]*a[2][1] + tvd[2]*a[2][2] + tvd[3]*a[2][3];
}

void getGlobalCatmullRomPoint(float gt, float *pos, float *deriv, Translate trans) {

	int pointCount = trans.catmullPoints.size();
	float t = gt * pointCount; // this is the real global t
	int index = floor(t);  // which segment
	t = t - index; // where within  the segment.

	// indices store the points
	int indices[4]; 
	indices[0] = (index + pointCount-1)%pointCount;
	indices[1] = (indices[0]+1)%pointCount;
	indices[2] = (indices[1]+1)%pointCount;
	indices[3] = (indices[2]+1)%pointCount;

	getCatmullRomPoint(t, trans.catmulls[indices[0]], trans.catmulls[indices[1]], trans.catmulls[indices[2]], trans.catmulls[indices[3]], pos, deriv);
}

void renderCatmullRomCurve(Translate trans) {
	// desenhar a curva usando segmentos de reta - GL_LINE_STRIP
	float pos[3], deriv[3];
	glBegin(GL_LINE_STRIP);

	for (float gt = 0; gt <= 1; gt += 0.001) {
		getGlobalCatmullRomPoint(gt, pos, deriv, trans);
		glColor3f(0.1,0.1,0.1);
		glVertex3fv(pos);
		glNormal3fv(pos);
	}
	glEnd();
}

void renderCatmullTranslate(Translate trans) {
	float pos[3], deriv[3];
	clocks = glutGet(GLUT_ELAPSED_TIME);
	gt = fmod(clocks, (float) (trans.time * 10000) ) / (trans.time * 10000);
	getGlobalCatmullRomPoint(gt, pos, deriv, trans);
	glTranslatef(pos[0], pos[1], pos[2]);
}

void renderRotate(Rotate rot){
	if(rot.angle != -1)
		glRotatef(rot.angle, rot.x, rot.y, rot.z);
	else if(rot.time != -1){
		clocks = glutGet(GLUT_ELAPSED_TIME);
		float angle = 360 * (fmod(clocks, (float) (rot.time * 10000) ) / (rot.time * 10000));
		glRotatef(angle, rot.x, rot.y, rot.z);
	}
}

void lights(Tree t){
	vector<Light>::iterator it;
	for(it = t.lights.begin(); it != t.lights.end(); it++){
		glLightfv(GL_LIGHT0 + it->id, GL_POSITION, it->pos);

		if(it->color.diffuse)
			glLightfv(GL_LIGHT0 + it->id, GL_DIFFUSE, it->color.diffuse);
		if(it->color.specular)
			glLightfv(GL_LIGHT0 + it->id, GL_SPECULAR, it->color.specular);
		if(it->color.emissive)
			glLightfv(GL_LIGHT0 + it->id, GL_EMISSION, it->color.emissive);
		if(it->color.ambient)
			glLightfv(GL_LIGHT0 + it->id, GL_AMBIENT, it->color.ambient);
	
		if (strcmp(it->type.c_str(), "POINT") == 0) {
			if (it->cons)
				glLightf(GL_LIGHT0 + it->id, GL_CONSTANT_ATTENUATION, it->cons);
			if (it->linear)
				glLightf(GL_LIGHT0 + it->id, GL_LINEAR_ATTENUATION, it->linear);
			if (it->quad)
				glLightf(GL_LIGHT0 + it->id, GL_QUADRATIC_ATTENUATION, it->quad);
		}
		
		if (strcmp(it->type.c_str(), "SPOTLIGHT") == 0) {
			if (it->spotCutOff)
				glLightf(GL_LIGHT0 + it->id, GL_SPOT_CUTOFF, it->spotCutOff);
			if (it->spotExponent)
				glLightfv(GL_LIGHT0 + it->id, GL_SPOT_DIRECTION, it->spotDirection);
			if (it->spotExponent)
				glLightf(GL_LIGHT0 + it->id, GL_SPOT_EXPONENT, it->spotExponent);
		}
	}
}

int loadTexture(string s) {
   unsigned int t, tw, th;
   unsigned char *texData;      
   unsigned int texID;
 
   ilInit();
   ilEnable(IL_ORIGIN_SET);
   ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
   ilGenImages(1,&t);
   ilBindImage(t);
   ilLoadImage((ILstring)s.c_str());
   tw = ilGetInteger(IL_IMAGE_WIDTH);
   th = ilGetInteger(IL_IMAGE_HEIGHT);
   ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
   texData = ilGetData();
 
   glGenTextures(1,&texID);
   
   glBindTexture(GL_TEXTURE_2D,texID);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
 
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
      
   gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGBA, tw, th, GL_RGBA, GL_UNSIGNED_BYTE, texData);
 
   glBindTexture(GL_TEXTURE_2D, 0);
 
   return texID;
}

// Funções de desenho da informação carregada
void drawFiguresVBOs(vector<Coordinate> coords, vector<Coordinate> normals, vector<TexCoordinate> textures, int nPoints, int nNormals) {

	GLuint buffers[3];
	glGenBuffers(3, buffers);

	float *bufVertex   = new float[nPoints+5];
	float *bufNormal   = new float[nNormals*3];
	float *bufTextures = new float[nNormals*3];

	vector<Coordinate>::iterator it_coords;
	int it = 0;
	for (it_coords = coords.begin(); it_coords != coords.end(); it_coords++) {
		bufVertex[it++] = it_coords->x;
		bufVertex[it++] = it_coords->y;
		bufVertex[it++] = it_coords->z;
	}

	vector<Coordinate>::iterator it_normals;
	int i = 0;
	for (it_normals = normals.begin(); it_normals != normals.end(); it_normals++) {
		bufNormal[i++] = it_normals->x;
		bufNormal[i++] = it_normals->y;
		bufNormal[i++] = it_normals->z;
	}

	vector<TexCoordinate>::iterator it_textures;
    int n = 0;
    for (it_textures = textures.begin(); it_textures != textures.end(); it_textures++) {
        bufTextures[n++] = it_textures->x;
        bufTextures[n++] = it_textures->y;
    }

	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glBufferData(GL_ARRAY_BUFFER, it*sizeof(float), bufVertex, GL_STATIC_DRAW);
	glVertexPointer(3,GL_FLOAT,0,0);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
	glBufferData(GL_ARRAY_BUFFER, i*sizeof(float), bufNormal, GL_STATIC_DRAW);
	glNormalPointer(GL_FLOAT,0,0);
	
	glBindBuffer(GL_ARRAY_BUFFER,buffers[2]);
    glBufferData(GL_ARRAY_BUFFER, n*sizeof(float), bufTextures, GL_STATIC_DRAW);
    glTexCoordPointer(2,GL_FLOAT,0,0);

	glDrawArrays(GL_TRIANGLE_STRIP, 0, nPoints/3);
	
	delete[] bufVertex;
	delete[] bufNormal;
	delete[] bufTextures;

	glDeleteBuffers(3, buffers);
}

void drawFiguresTriangles(vector<Coordinate> figures, vector<Coordinate> normais) {
	glBegin(GL_TRIANGLES);
	vector<Coordinate>::iterator it_coords;
	vector<Coordinate>::iterator it_normais;
	for (it_coords = figures.begin(), it_normais = normais.begin(); 
		 it_coords != figures.end() && it_normais != normais.end(); 
		 it_coords++, it_normais++){
		glVertex3f(it_coords->x, it_coords->y, it_coords->z);
		glNormal3f(it_coords->x, it_coords->y, it_coords->z);
	}
	glEnd();
}

void drawGroup(Tree t) {
	glPushMatrix();
	if (!t.figure.translate.empty){
		renderCatmullRomCurve(t.figure.translate);
		renderCatmullTranslate(t.figure.translate);
		t.figure.translate.catmullPoints.clear();
		t.figure.translate.catmullPoints.shrink_to_fit();
	}
	if (!t.figure.rotate.empty)
		renderRotate(t.figure.rotate);
	if (!t.figure.scale.empty)
		glScalef(t.figure.scale.x, t.figure.scale.y, t.figure.scale.z);
	if (!t.figure.color.empty)
		glColor3f(t.figure.color.r, t.figure.color.g, t.figure.color.b);

	if (t.figure.material.ambient)
		glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, t.figure.material.ambient);
	if (t.figure.material.diffuse)
		glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, t.figure.material.diffuse);
	if (t.figure.material.emissive)
		glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, t.figure.material.emissive);
	if (t.figure.material.specular)
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, t.figure.material.specular);
	if (t.figure.buffer > 1){
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,t.figure.texture);
		drawFiguresVBOs(t.figure.figuras, t.figure.normais, t.figure.texturas, t.figure.buffer, t.figure.norm);
		glBindTexture(GL_TEXTURE_2D,0);
		glDisable(GL_TEXTURE_2D);
	}
	else if (t.figure.buffer == 1)
		drawFiguresTriangles(t.figure.figuras, t.figure.normais);
	
	vector<Tree>::iterator it;
	for (it = t.subtrees.begin(); it != t.subtrees.end(); it++)
		drawGroup(*it);

	lights(t);
	glPopMatrix();

	//reiniciar para valores default do glMaterial para nao aplicar aos futuros objetos
	float emission[4] = {0,0,0,1};
	float ambient[4] = {0.2,0.2,0.2,1.0};
	float specular[4] = {0,0,0,1};
	float diffuse[4] = {0.8,0.8,0.8,1.0};
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, emission);
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuse);
}

// Funçoes GET que lêem o scene.xml e carregam a informaçao relevante para execuçao
vector<Coordinate> getFigure(string figure, int *i, vector<Coordinate> *normals, vector<TexCoordinate> *textures) {
	vector<Coordinate> coords;
	string line;
	ifstream file;
	file.open(figure);
	// Verifica se é para desenhar com VBO ou triangles e envia valor pelo i para a getGroup.
	int numSpaces, nVertices, n;
	if (getline(file, line)) {
		file.close();
		numSpaces = count(line.begin(), line.end(), ' ');
		if (numSpaces == 0) {
			// Criar nova stream para poder voltar atrás e registar o valor da 1ª linha e as coordenadas.
			ifstream fileVBO;
			fileVBO.open(figure);
			fileVBO >> nVertices;
			*i = nVertices;
			/* Guarda os vértices */
			n=0;
			//Coordenadas
			while (n < nVertices/3) {
				Coordinate coord;
				fileVBO >> coord.x >> coord.y >> coord.z;
				coords.push_back(coord);
				n++;
			}
			n=0;
			//Normais
			while (n < nVertices/3) {
				Coordinate norm;
				fileVBO >> norm.x >> norm.y >> norm.z;
				(*normals).push_back(norm);
				n++;
			}
			//Pts de textura
			n=0;
			while(n < nVertices/3 && !fileVBO.eof()){
				TexCoordinate tex;
				fileVBO >> tex.x >> tex.y;
				(*textures).push_back(tex);
				n++;
			}
			fileVBO.close();
			return coords;
		}
		// Se tiver espaços na 1ª linha é pq são coordenadas, então não é para desenhar com VBO, logo i=1
		else *i = 1;
	}

	// Simplemente regista coordenadas da figura, nos restantes casos (quando é para desenhar com triângulos).
	ifstream fileGetLines;
	fileGetLines.open(figure);
	int x=0;
	// Conta número de linhas para poder dividir por 3 e saber onde estão as coordenadas, as normais e os pts de textura.
	while (!fileGetLines.eof()) {
		Coordinate coord;
		fileGetLines >> coord.x >> coord.y >> coord.z;
		x++;
	}
	fileGetLines.close();

	ifstream fileTriang;
	fileTriang.open(figure);
	n=0;
	//Coordenadas
	while(n < x/3){
		Coordinate coord;
		fileTriang >> coord.x >> coord.y >> coord.z;
		coords.push_back(coord);
		n++;
	}
	n=0;
	//Normais
	while(n < x/3){
		Coordinate norm;
		fileTriang >> norm.x >> norm.y >> norm.z;
		(*normals).push_back(norm);
		n++;
	}
	n=0;
	//Pts de textura
	while(n < x/3){
		TexCoordinate tex;
		fileTriang >> tex.x >> tex.y;
		(*textures).push_back(tex);
		n++;
	}
	fileTriang.close();
	return coords;
}

Tree getGroup(XMLElement* node) {
	string models_path = "../../xml_models/";
	Tree t;
	t.figure.buffer = 0;

	XMLElement* child = node->FirstChildElement();
	for (; child != nullptr; child = child->NextSiblingElement()) {
		string tag = child->Value();
		if (strcmp(tag.c_str(), "translate") == 0) {
			t.figure.translate.empty = false;
			t.figure.translate.time = child->DoubleAttribute("time");

			XMLElement* translateNode = child->FirstChildElement();
			for (; translateNode != nullptr; translateNode = translateNode->NextSiblingElement()) {
				Coordinate coord;
				coord.x = translateNode->DoubleAttribute("X");
				coord.y = translateNode->DoubleAttribute("Y");
				coord.z = translateNode->DoubleAttribute("Z");
				t.figure.translate.catmullPoints.push_back(coord);
			}
			t.figure.translate = vectorToArray(t.figure.translate);
		}
		else if (strcmp(tag.c_str(), "rotate") == 0) {
			t.figure.rotate.empty = false;
			t.figure.rotate.x = child->DoubleAttribute("axisX");
			t.figure.rotate.y = child->DoubleAttribute("axisY");
			t.figure.rotate.z = child->DoubleAttribute("axisZ");
			if (child->DoubleAttribute("angle")) t.figure.rotate.angle = child->DoubleAttribute("angle");
			else if (child->DoubleAttribute("time"))  t.figure.rotate.time = child->DoubleAttribute("time");
		}
		else if (strcmp(tag.c_str(), "scale") == 0) {
			t.figure.scale.empty = false;
			t.figure.scale.x = child->DoubleAttribute("X");
			t.figure.scale.y = child->DoubleAttribute("Y");
			t.figure.scale.z = child->DoubleAttribute("Z");
		}
		else if (strcmp(tag.c_str(), "color") == 0) {
			t.figure.color.empty = false;
			t.figure.color.r = child->DoubleAttribute("R");
			t.figure.color.g = child->DoubleAttribute("G");
			t.figure.color.b = child->DoubleAttribute("B");
		}
		else if (strcmp(tag.c_str(), "models") == 0) {
			XMLElement* modelsNode = child->FirstChildElement();
			for (; modelsNode != NULL; modelsNode = modelsNode->NextSiblingElement()) {
				string figureName = models_path + modelsNode->Attribute("file");
				
				float white[4] = {1,1,1,1};
				float black[4] = {0,0,0,1};

				if(modelsNode->Attribute("texture")){
					string textureName = models_path + modelsNode->Attribute("texture");
					t.figure.texture = loadTexture(textureName);
				}

				if (modelsNode->Attribute("ambR")) {
					t.figure.material.ambient = (float*)malloc(sizeof(float)*4);
					t.figure.material.ambient[0] = modelsNode->DoubleAttribute("ambR");
					t.figure.material.ambient[1] = modelsNode->DoubleAttribute("ambG");
					t.figure.material.ambient[2] = modelsNode->DoubleAttribute("ambB");
					t.figure.material.ambient[3] = 1.0f;
				}

				if (modelsNode->Attribute("specR")) {
					t.figure.material.specular = (float*)malloc(sizeof(float)*4);
					t.figure.material.specular[0] = modelsNode->DoubleAttribute("specR");
					t.figure.material.specular[1] = modelsNode->DoubleAttribute("specG");
					t.figure.material.specular[2] = modelsNode->DoubleAttribute("specB");
					t.figure.material.specular[3] = 1.0f;
				}

				if (modelsNode->Attribute("diffR")) {
					t.figure.material.diffuse = (float*)malloc(sizeof(float)*4);
					t.figure.material.diffuse[0] = modelsNode->DoubleAttribute("diffR");
					t.figure.material.diffuse[1] = modelsNode->DoubleAttribute("diffG");
					t.figure.material.diffuse[2] = modelsNode->DoubleAttribute("diffB");
					t.figure.material.diffuse[3] = 1.0f;
				}

				if (modelsNode->Attribute("emiR")) {
					t.figure.material.emissive = (float*)malloc(sizeof(float)*4);
					t.figure.material.emissive[0] = modelsNode->DoubleAttribute("emiR");
					t.figure.material.emissive[1] = modelsNode->DoubleAttribute("emiG");
					t.figure.material.emissive[2] = modelsNode->DoubleAttribute("emiB");
					t.figure.material.emissive[3] = 1.0f;
				}

				int nVertex, nNormals;
				vector<TexCoordinate> textures;
				vector<Coordinate> normals;
				vector<Coordinate> coords = getFigure(figureName, &nVertex, &normals, &textures);
				t.figure.buffer = nVertex;
				t.figure.norm = normals.size();
				t.figure.figuras = coords;
				t.figure.texturas = textures;
				t.figure.normais = normals;
			}
		}
		else if (strcmp(tag.c_str(), "lights") == 0) {
			XMLElement* lightsNode = child->FirstChildElement();
			glEnable(GL_LIGHTING);
		    for (; lightsNode && nodeN<8; lightsNode = lightsNode->NextSiblingElement(), nodeN++) {
		    	glEnable(GL_LIGHT0 + nodeN);
				Light l;
				l.id = nodeN;

		    	if(lightsNode->Attribute("type"))
		    		l.type = lightsNode->Attribute("type");

		    	l.pos[0] = lightsNode->DoubleAttribute("X");
		    	l.pos[1] = lightsNode->DoubleAttribute("Y");
		    	l.pos[2] = lightsNode->DoubleAttribute("Z");
		    	l.pos[3] = 1.0f;

		    	if(strcmp(l.type.c_str(), "DIRECTIONAL") == 0) l.pos[3] = 0.0f;

				if(lightsNode->Attribute("diffR")){
		    		l.color.diffuse    = (float*)malloc(sizeof(float) * 4);
		    		l.color.diffuse[0] = lightsNode->DoubleAttribute("diffR");
		    		l.color.diffuse[1] = lightsNode->DoubleAttribute("diffG");
		    		l.color.diffuse[2] = lightsNode->DoubleAttribute("diffG");
		    		l.color.diffuse[3] = 1.0f;
		    	}
		    	if(lightsNode->Attribute("specR")){
		    		l.color.specular    = (float*)malloc(sizeof(float) * 4);
		    		l.color.specular[0] = lightsNode->DoubleAttribute("specR");
		    		l.color.specular[1] = lightsNode->DoubleAttribute("specG");
		    		l.color.specular[2] = lightsNode->DoubleAttribute("specB");
		    		l.color.specular[3] = 1.0f;
		    	}
		    	if(lightsNode->Attribute("emiR")){
		    		l.color.emissive    = (float*)malloc(sizeof(float) * 4);
		    		l.color.emissive[0] = lightsNode->DoubleAttribute("emiR");
		    		l.color.emissive[1] = lightsNode->DoubleAttribute("emiG");
		    		l.color.emissive[2] = lightsNode->DoubleAttribute("emiB");
		    		l.color.emissive[3] = 1.0f;
		    	}
		    	if(lightsNode->Attribute("ambR")){
		    		l.color.ambient    = (float*)malloc(sizeof(float) * 4);
		    		l.color.ambient[0] = lightsNode->DoubleAttribute("ambR");
		    		l.color.ambient[1] = lightsNode->DoubleAttribute("ambG");
		    		l.color.ambient[2] = lightsNode->DoubleAttribute("ambB");
		    		l.color.ambient[3] = 1.0f;
		    	}
		    	if (lightsNode->Attribute("const"))
					l.cons = lightsNode->DoubleAttribute("const");
				if (lightsNode->Attribute("linear"))
					l.linear = lightsNode->DoubleAttribute("linear");
				if (lightsNode->Attribute("quad"))
					l.quad = lightsNode->DoubleAttribute("quad");
				if (lightsNode->Attribute("spotDirX")) {
					l.spotDirection = (float*)malloc(sizeof(float) * 4);
 					l.spotDirection[0] = lightsNode->DoubleAttribute("spotDirX");
					l.spotDirection[1] = lightsNode->DoubleAttribute("spotDirY");
					l.spotDirection[2] = lightsNode->DoubleAttribute("spotDirZ");
				}
				if (lightsNode->Attribute("spotCutOff"))
					l.spotCutOff = lightsNode->DoubleAttribute("spotCutOff");
				if (lightsNode->Attribute("spotExponent"))
					l.spotExponent = lightsNode->DoubleAttribute("spotExponent");

				t.lights.push_back(l);
			}
		}
		else if (strcmp(tag.c_str(), "group") == 0)
			t.subtrees.push_back(getGroup(child));
	}
	return t;
}

void getScene() {
	XMLDocument doc;
	XMLError load = doc.LoadFile("../../xml_models/scene.xml"); //abre ficheiro XML
	if (load != XML_SUCCESS) {
		printf("Erro no ficheiro XML.\n");
		return;
	}
	XMLElement *pRoot = doc.FirstChildElement("scene");
	if (pRoot == nullptr)
		return;
	tree = getGroup(pRoot);
}

// Funçoes responsaveis pela apresentaçao visual
void renderScene(void) {
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glPolygonMode(GL_FRONT_AND_BACK, OPTION);

	// set the camera
	glLoadIdentity();
	px = radium * cosf(beta)*sinf(alpha);
	py = radium * sinf(beta);
	pz = radium * cosf(alpha)*cosf(beta);
	
	gluLookAt(px+dx, py+dy, pz+dz,
			  dx   , dy   , dz,
			  0.0f , 1.0f , 0.0f);
	// Draw the models
	drawGroup(tree);
	// End of frame
	glutSwapBuffers();
}

void changeSize(int w, int h) {
	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width)
	if (h == 0)
		h = 1;

	// compute window's aspect ratio 
	float ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();

	// Set the viewport to be the entire window
	glViewport(0, 0, w, h);
	// Set perspective
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

// Funções de processamento de comandos
void processKeys(unsigned char c, int xx, int yy) {
    switch (c) {

        case 's': dx += 0.01 * (px + sin(alpha)) ; dz += 0.01 * (pz + cos(alpha));
            break;
        case 'w': dx -= 0.01 * (px + sin(alpha)) ; dz -= 0.01 * (pz + cos(alpha));
            break;
        case 'e': dy += 1;
            break;
        case 'q': dy -= 1;
            break;
        case 'd': dx += 0.01 * (pz + cos(alpha)) ; dz -= 0.01 * (px + sin(alpha));
            break;
        case 'a': dx -= 0.01 * (pz + cos(alpha)) ; dz += 0.01 * (px + sin(alpha));
            break;
        case 'S': dx += 0.1 * (px + sin(alpha)) ; dz += 0.1 * (pz + cos(alpha));
            break;
        case 'W': dx -= 0.1 * (px + sin(alpha)) ; dz -= 0.1 * (pz + cos(alpha));
            break;
        case 'E': dy += 3;
            break;
        case 'Q': dy -= 3;
            break;
        case 'D': dx += 0.1 * (pz + cos(alpha)) ; dz -= 0.1 * (px + sin(alpha));
            break;
        case 'A': dx -= 0.1 * (pz + cos(alpha)) ; dz += 0.1 * (px + sin(alpha));
            break;

		case 'i':
			OPTION = GL_FILL;
			break;
		case 'o':
			OPTION = GL_LINE;
			break;
		case 'p':
			OPTION = GL_POINT;
			break;
        case 'I':
            OPTION = GL_FILL;
            break;
        case 'O':
            OPTION = GL_LINE;
            break;
        case 'P':
            OPTION = GL_POINT;
            break;

        case '8': 
                if(beta + 0.05 >= 1.5) beta = 1.5;
                else beta += 0.05;
            break;
        case '2': 
                if(beta - 0.05 <= -1.5) beta = -1.5;
                else beta -= 0.05;
            break;
        case '4': alpha -= 0.05;
            break;
        case '6': alpha += 0.05;
            break;



		default:
			break;
	}
	glutPostRedisplay();
}

void mouse_Func(int button, int state, int x, int y) {
    switch (button) {
        case GLUT_LEFT_BUTTON:
            if (radium - 1 == 0) break;
            else radium = radium - 1;
            break;
        case GLUT_RIGHT_BUTTON:
            radium = radium + 1;
            break;
    }
    glutPostRedisplay();
}

void processSpecialKeys(int key_code, int xx, int yy) {
    switch (key_code) {
        case GLUT_KEY_UP:
            if (radium - 3 == 0) break;
            else radium = radium - 3;
            break;
        case GLUT_KEY_DOWN:
                radium = radium + 3;
            break;
        default:
            break;
    }
    glutPostRedisplay();
}

// Main e inicializações
void initGL() {
	// alguns settings para OpenGL
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);

	glClearColor(0, 0, 0, 0);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
}

int main(int argc, char **argv) {
	// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(50, 50);
	glutInitWindowSize(1200, 800);
	glutCreateWindow("CG - Engine");

	// Required callback registry 
	getScene();
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

	#ifndef __APPLE__
		glewInit();
	#endif
	initGL();

	// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);
	glutMouseFunc(mouse_Func);

	// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
