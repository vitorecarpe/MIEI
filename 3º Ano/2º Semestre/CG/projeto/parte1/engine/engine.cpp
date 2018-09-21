#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <stdlib.h>
#include "tinyxml2.cpp"
#include "tinyxml2.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

using namespace tinyxml2;
using namespace std;


class Coordinate {
	public:
		float x, y, z;
};

class Figure {
	public:
		std::vector<Coordinate> figura;
		int triangles;
};

float px = 0;
float py = 0;
float pz = 0;

float dx = 0;
float dy = 0;
float dz = 0;

float alpha = M_PI/4;
float beta = M_PI/4;
float radium = 17.0;

GLenum OPTION = GL_FILL;
int axis = 0;

std::vector<Figure> figures;

void getFigures() {
	std::vector<string> figuresToLoad; //vector com os nomes das figuras presentes no ficheiro XML
	string generated_path = "../../generated3d/";
	
	XMLDocument doc;
	XMLError load = doc.LoadFile("../scene.xml"); //abre ficheiro XML
	if (load != XML_SUCCESS) {
		printf("Erro no ficheiro XML\n");
		return;
	}

	/* Example scene.xml
		<scene>
			<model file="sphere.3d"/>
			<model file="box.3d"/>
		</scene>
	 */

	XMLNode *pRoot = doc.FirstChildElement("scene"); 
	if (pRoot == nullptr) return;
	XMLElement *sceneFigures = pRoot->FirstChildElement("model");
	for (; sceneFigures != nullptr; sceneFigures = sceneFigures->NextSiblingElement("model")) {
		string newFigure = generated_path + sceneFigures->Attribute("file");
		figuresToLoad.push_back(newFigure);
	}

	for (auto i: figuresToLoad) {
		ifstream file;
		Figure newFig;
		file.open(i);
		
		//int nTriangles;
		//file >> nTriangles;
		while (!file.eof()) {
			Coordinate newC;
			file >> newC.x >> newC.y >> newC.z;
			newFig.figura.push_back(newC);
		}
				
		figures.push_back(newFig);
	}
}

void changeSize(int w, int h) {
	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width)
	if(h == 0)
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
	gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

void drawFigures() {
	std::vector<Figure>::iterator it_fig;
	
	for (it_fig = figures.begin(); it_fig != figures.end(); it_fig++){
		std::vector<Coordinate> crdnt = it_fig->figura; //coordenadas dos pontos da figura
		std::vector<Coordinate>::iterator it;

		glBegin(GL_TRIANGLES);
		int color;
		glColor3f(0.0, 0.0, 1.0);
		for (it = crdnt.begin(), color = 0; it != crdnt.end(); it++, color++) {
			if (color % 3 == 0) glColor3f(0.0, 1.0, 0.0);
			if (color % 6 == 0) glColor3f(1.0, 0.0, 0.0);

			glVertex3f(it->x, it->y, it->z); // vertice(x,y,z)
		}
		glEnd();
	}
}

void renderScene(void) {
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	px = radium*cosf(beta)*sinf(alpha);
	py = radium*sinf(beta);
	pz = radium*cosf(alpha)*cosf(beta);
	gluLookAt(px, py, pz,
			  dx, dy, dz,
			  0.0f, 1.0f, 0.0f);

	if (axis == 1){
    glPushMatrix();
    glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    glBegin(GL_LINES);
    glColor3f(0.0,0.0,1.0);
    glVertex3f(0,0,0);
    glVertex3f(7,0,0);
    glVertex3f(0,0,0);
    glVertex3f(0,7,0);
    glVertex3f(0,0,0);
    glVertex3f(0,0,7);
    glEnd();
    glPopMatrix();
    }

    glPolygonMode(GL_FRONT_AND_BACK, OPTION);

	getFigures();
	drawFigures();

	// End of frame
	glutSwapBuffers();
}

void processKeys(unsigned char c, int xx, int yy) {
	switch (c) {
		case 'd': dx += 0.1;
			break;
		case 'a': dx -= 0.1;
			break;
		case 'w': dy += 0.1;
			break;
		case 's': dy -= 0.1;
			break;
		case 'q': dz -= 0.1;
			break;
		case 'e': dz += 0.1;
			break;
		case 'z':
            if(axis==0) axis=1;
            else axis=0;
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
		default:
			break;
	}
	glutPostRedisplay();
}

void processSpecialKeys(int key_code, int xx, int yy) {
	switch (key_code) {
		case GLUT_KEY_RIGHT: alpha = alpha + 0.05;
			break;
		case GLUT_KEY_LEFT: alpha = alpha - 0.05;
			break;
		case GLUT_KEY_UP:
			if (beta + 0.05 >= 1.5)
				beta = 1.5;
			else
				beta = beta + 0.05;
			break;
		case GLUT_KEY_DOWN:
			if (beta - 0.05 <= -1.5)
				beta = -1.5;
			else
				beta = beta - 0.05;
			break;
		default:
			break;
	}
	glutPostRedisplay();
}

void Mouse_Func(int button, int state, int x, int y) {
    switch (button) {
        case GLUT_LEFT_BUTTON:
            if (radium - 1 == 0) break;
            else radium --;
            break;
        case GLUT_RIGHT_BUTTON:
            radium ++;
            break;
    }
    glutPostRedisplay();
}



int main(int argc, char **argv) {
	// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(50,50);
	glutInitWindowSize(1200,800);
	glutCreateWindow("CG - Engine");
		
	// Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	
	// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);
	glutMouseFunc(Mouse_Func);

	//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	// enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
