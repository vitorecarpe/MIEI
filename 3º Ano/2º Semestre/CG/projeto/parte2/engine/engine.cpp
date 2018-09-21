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
#include <GL/glut.h>
#endif

using namespace tinyxml2;
using namespace std;

class Coordinate {
	public:
		//Coordinate() :empty(true) {}
		bool empty = true;
		float x, y, z, angle;
};
class Color {
	public:
		bool empty = true;
		float r, g, b;
};
class Figure {
	public:
		//???criar class Figura = vector<Coordinate>
		vector<vector<Coordinate> > figuras;
		Coordinate rotate, translate, scale;
		Color color;
		bool orbita=false;

};
class Tree {
	public:
		Figure figure;
		vector<Tree> subtrees;
};

float px = 0;
float py = 0;
float pz = 0;

float dx = 0;
float dy = 0;
float dz = 0;

float alpha = 0;
float beta = M_PI / 4;
float radium = 20.0;

GLenum OPTION = GL_FILL;

Tree tree;

vector<Coordinate> getFigure(string figure) {
	vector<Coordinate> newFig;
	ifstream file;
	file.open(figure);
	while (!file.eof()) {
		Coordinate newC;
		file >> newC.x >> newC.y >> newC.z;
		newFig.push_back(newC);
	}
	return newFig;
}

Tree getGroup(XMLElement* node) {
	Tree t;
	string models_path = "../../xml_models/";

	/* Example scene.xml
		<scene>
			<group>
				<models>
					<model file="box.3d"/>
				</models>
				<group>
					<translate Z="0" Y="0" X="1.0"/>
					<rotate Z="1" Y="0" X="0" angle="-0.2"/>
					<scale Z="0.2" Y="0.2" X="0.2"/>
					<models>
						<model file="sphere.3d"/>
					</models>
				</group>
			</group>
		</scene>
	 */

	XMLElement* child = node->FirstChildElement();
	for (; child != nullptr; child = child->NextSiblingElement()) {
		string tag = child->Value();

		if (strcmp(tag.c_str(), "translate") == 0) {
			t.figure.translate.empty = false;
			t.figure.translate.x = child->DoubleAttribute("X");
			t.figure.translate.y = child->DoubleAttribute("Y");
			t.figure.translate.z = child->DoubleAttribute("Z");
		}
		else if (strcmp(tag.c_str(), "rotate") == 0) {
			t.figure.rotate.empty = false;
			t.figure.rotate.x = child->DoubleAttribute("axisX");
			t.figure.rotate.y = child->DoubleAttribute("axisY");
			t.figure.rotate.z = child->DoubleAttribute("axisZ");
			t.figure.rotate.angle = child->DoubleAttribute("angle");
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
				vector<Coordinate> newFig = getFigure(figureName);
				t.figure.figuras.push_back(newFig);
				if(child->IntAttribute("a")==1) t.figure.orbita=true;

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
		printf("Erro no ficheiro XML\n");
		return;
	}

	XMLElement *pRoot = doc.FirstChildElement("scene");
	if (pRoot == nullptr)
		return;

	tree = getGroup(pRoot);
}



void drawFigures(vector<vector<Coordinate> > figures) {
	vector<vector<Coordinate> >::iterator it_fig;
	for (it_fig = figures.begin(); it_fig != figures.end(); it_fig++) {
      int color = 0;
 

		glBegin(GL_TRIANGLES);
 
		vector<Coordinate>::iterator it_coords;
		for (it_coords = it_fig->begin(); it_coords != it_fig->end(); it_coords++) {

			//descomentar para ver inclinaçao dos astros.
			//if (color % 3 == 0) glColor3f(0.0, 1.0, 0.0);
         	//if (color % 6 == 0) glColor3f(1.0, 0.0, 0.0);
 
			glVertex3f(it_coords->x, it_coords->y, it_coords->z); // vertice(x,y,z)
			color++;
		}
		glEnd();

	}
}

void drawOrbit(vector<vector<Coordinate> > figures) {
	vector<vector<Coordinate> >::iterator it_fig;
	for (it_fig = figures.begin(); it_fig != figures.end(); it_fig++) {
 

		glBegin(GL_LINE_STRIP);
 
		vector<Coordinate>::iterator it_coords;
		for (it_coords = it_fig->begin(); it_coords != it_fig->end(); it_coords++) {
 
			glVertex3f(it_coords->x, it_coords->y, it_coords->z); // vertice(x,y,z)
		}
		glEnd();

	}
}

void drawGroup(Tree t) {
	glPushMatrix();

	if (!t.figure.translate.empty){
        glTranslatef(t.figure.translate.x, t.figure.translate.y, t.figure.translate.z);
	}
   	if (!t.figure.rotate.empty){
        glRotatef(t.figure.rotate.angle, t.figure.rotate.x, t.figure.rotate.y, t.figure.rotate.z);
   	}
   	if (!t.figure.scale.empty){
        glScalef(t.figure.scale.x, t.figure.scale.y, t.figure.scale.z);
   	}
   	if (!t.figure.color.empty){
        glColor3f(t.figure.color.r, t.figure.color.g, t.figure.color.b);
   	}
   	if(t.figure.orbita){
   		drawOrbit(t.figure.figuras);
   	} else{
		drawFigures(t.figure.figuras);
	}
	vector<Tree>::iterator it;
	for (it = t.subtrees.begin(); it != t.subtrees.end(); it++)
		drawGroup(*it);

	glPopMatrix();


}

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

void Mouse_Func(int button, int state, int x, int y) {
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
