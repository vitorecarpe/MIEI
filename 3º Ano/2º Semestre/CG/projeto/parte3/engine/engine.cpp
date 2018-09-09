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

using namespace tinyxml2;
using namespace std;

class Coordinate {
public:
	bool empty = true;
	float x, y, z;
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
class Figure {
public:
	vector<vector<Coordinate> > figuras;
	Coordinate scale;
	Rotate rotate;
	Translate translate;
	/* int buffer - indica se é para desenhar com buffer ou com triangulos
	(1 para triangulos | outro valor superior para buffer, sendo que esse
	valor é o numero de vertices). */
	int buffer; 
	Color color;
};
class Tree {
public:
	Figure figure;
	vector<Tree> subtrees;
};

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


// Funçoes auxiliares (catmull, etc)

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
	// desenhar a curva usando segmentos de reta - GL_LINE_LOOP
	float pos[3], deriv[3];
	glBegin(GL_LINE_LOOP);
	for (float gt = 0; gt <= 1; gt += 0.001) {
		getGlobalCatmullRomPoint(gt, pos, deriv, trans);
		glColor3f(0.15,0.15,0.15);
		glVertex3f(pos[0], pos[1], pos[2]);
	}
	glEnd();
}

void renderCatmullTranslate(Translate trans) {
	float pos[3], deriv[3];
	clocks = glutGet(GLUT_ELAPSED_TIME);
	gt = fmod(clocks, (float) (trans.time * 1000) ) / (trans.time * 1000);
	getGlobalCatmullRomPoint(gt, pos, deriv, trans);
	glTranslatef(pos[0], pos[1], pos[2]);
}

void renderRotate(Rotate rot){
	if(rot.angle != -1)
		glRotatef(rot.angle, rot.x, rot.y, rot.z);
	else if(rot.time != -1){
		clocks = glutGet(GLUT_ELAPSED_TIME);
		float angle = 360 * (fmod(clocks, (float) (rot.time * 1000) ) / (rot.time * 1000));
		glRotatef(angle, rot.x, rot.y, rot.z);
	}
}


// Funçoes de desenho da informaçao carregada

void drawFiguresVBOs(vector<vector<Coordinate> > figures, int nPoints) {
	vector<vector<Coordinate> >::iterator it_fig;
	glEnableClientState(GL_VERTEX_ARRAY);
	GLuint buffers[1];
	glGenBuffers(1, buffers);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);

	float *array = new float[nPoints+5];
	for (it_fig = figures.begin(); it_fig != figures.end(); it_fig++) {
		vector<Coordinate>::iterator it_coords;
		int it = 0;
		for (it_coords = it_fig->begin(); it_coords != it_fig->end(); it_coords++) {
			array[it++] = it_coords->x;
			array[it++] = it_coords->y;
			array[it++] = it_coords->z;
		}
		glBufferData(GL_ARRAY_BUFFER, it*sizeof(float), array, GL_STATIC_DRAW);
		glVertexPointer(3,GL_FLOAT,0,0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, nPoints/3);
	}
	delete[] array;
	glDeleteBuffers(1, buffers);
}

void drawFiguresTriangles(vector<vector<Coordinate> > figures) {
	vector<vector<Coordinate> >::iterator it_fig;
	for (it_fig = figures.begin(); it_fig != figures.end(); it_fig++) {
		glBegin(GL_TRIANGLES);
		vector<Coordinate>::iterator it_coords;
		for (it_coords = it_fig->begin(); it_coords != it_fig->end(); it_coords++)
			glVertex3f(it_coords->x, it_coords->y, it_coords->z);
		glEnd();
	}
}

void drawGroup(Tree t) {
	glPushMatrix();
	if (!t.figure.translate.empty){
		renderCatmullRomCurve(t.figure.translate);
		renderCatmullTranslate(t.figure.translate);
		t.figure.translate.catmullPoints.clear();
		t.figure.translate.catmullPoints.shrink_to_fit();
	}
   	if (!t.figure.rotate.empty){
		renderRotate(t.figure.rotate);
   	}
   	if (!t.figure.scale.empty){
        glScalef(t.figure.scale.x, t.figure.scale.y, t.figure.scale.z);
   	}
   	if (!t.figure.color.empty){
        glColor3f(t.figure.color.r, t.figure.color.g, t.figure.color.b);
   	}
   	if (t.figure.buffer > 1){
		drawFiguresVBOs(t.figure.figuras, t.figure.buffer);
	}
	else if (t.figure.buffer == 1){
		drawFiguresTriangles(t.figure.figuras);
	}
	vector<Tree>::iterator it;
	for (it = t.subtrees.begin(); it != t.subtrees.end(); it++)
		drawGroup(*it);
	
	glPopMatrix();
}


// Funçoes GET que lêem o scene.xml e carregam a informaçao relevante para execuçao

vector<Coordinate> getFigure(string figure, int* i) {
	vector<Coordinate> newFig;
	string line;
	ifstream file;
	file.open(figure);

	// Verifica se é para desenhar com VBO ou triangles e envia valor pelo i para a getGroup.
	int numSpaces, nVertices;
	if (std::getline(file, line)) {
		file.close();
		numSpaces = std::count(line.begin(), line.end(), ' ');
		if (numSpaces == 0) {
			// Criar nova stream para poder voltar atrás e registar o valor da 1ª linha e as coordenadas.
			ifstream fileVBO;
			fileVBO.open(figure);
			fileVBO >> nVertices;
			*i = nVertices;
			/* Guarda os vértices */
			while (!fileVBO.eof()) {
				Coordinate newC;
				fileVBO >> newC.x >> newC.y >> newC.z;
				newFig.push_back(newC);
			}
			fileVBO.close();
			return newFig;
		}
		// Se tiver espaços na 1ª linha é pq são coordenadas, então não é para desenhar com VBO, logo i=1
		else *i = 1;
	}

	// Simplemente regista coordenadas da figura, nos restantes casos (quando é para desenhar com triângulos).
	ifstream fileTriang;
	fileTriang.open(figure);
	while (!fileTriang.eof()) {
		Coordinate newC;
		fileTriang >> newC.x >> newC.y >> newC.z;
		newFig.push_back(newC);
	}
	fileTriang.close();
	return newFig;
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
				int i;
				vector<Coordinate> newFig = getFigure(figureName, &i);
				t.figure.buffer = i;
				t.figure.figuras.push_back(newFig);
			}
		}
		else if (strcmp(tag.c_str(), "group") == 0) {
			t.subtrees.push_back(getGroup(child));
		}
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
	
	gluLookAt(px + dx, py + dy, pz + dz,
                  dx, dy, dz,
                  0.0f, 1.0f, 0.0f);

	//glClearColor(0, 0, 0.1, 1);

	/*
	glBegin(GL_LINE_STRIP); // eixo dos yy
	glColor3f(1, 0, 0);
	glVertex3f(0.0f, 200.0f, 0.0f);
	glVertex3f(0.0f, 0.0f, 0.0f);
	glEnd();
	glBegin(GL_LINE_STRIP); // eixo dos zz
	glColor3f(0, 1, 0);
	glVertex3f(0.0f, 0.0f, 200.0f);
	glVertex3f(0.0f, 0.0f, 0.0f);
	glEnd();
	glBegin(GL_LINE_STRIP); // eixo dos xx
	glColor3f(0, 0, 1);
	glVertex3f(200.0f, 0.0f, 0.0f);
	glVertex3f(0.0f, 0.0f, 0.0f);
	glEnd();
	*/

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

	// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);
	glutMouseFunc(mouse_Func);
	//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
