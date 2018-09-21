#ifdef __APPLE__

#include <GLUT/glut.h> 
#else
#include <GL/glut.h>
#endif
 
#define _USE_MATH_DEFINES
#include <math.h>

float ang = 0.0f;
  
float tx = 0.0f;
float ty = 0.0f;
float tz = 0.0f;
 
float px = 5.0;
float py = 5.0;
float pz = 5.0;
 
float dx = 0.0;
float dy = 0.0;
float dz = 0.0;


void changeSize(int w, int h) { 
   // Prevent a divide by zero, when window is too short
   // (you cant make a window with zero width).
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
 
 
 
void drawCylinder(float radius, float height, int slices) {
 
   glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
 
   glBegin(GL_TRIANGLES);
 
   for(int i=0; i<slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      float npx = radius * sin((i+1)*angle);
 
      float npz = radius * cos((i+1)*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,height,pz);
 
      glVertex3d(npx,height,npz);
 
      glVertex3d(0.0,height,0.0);
 
   }
 
   for(int i=0; i<slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      float npx = radius * sin((i+1)*angle);
 
      float npz = radius * cos((i+1)*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,0.0,pz);
 
      glVertex3d(0.0,0.0,0.0);
 
      glVertex3d(npx,0.0,npz);
 
      
 
   }
 
   for(int i=0; i<slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      float npx = radius * sin((i+1)*angle);
 
      float npz = radius * cos((i+1)*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,0,pz);
 
      glVertex3d(npx,0,npz);
 
      glVertex3d(npx,height,npz);
 
   }
 
   for(int i=0; i<slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      float npx = radius * sin((i+1)*angle);
 
      float npz = radius * cos((i+1)*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,0,pz);
 
      glVertex3d(npx,height,npz);
 
      glVertex3d(px,height,pz);
 
   }
 
   glEnd();
 
 
   //UNIR PONTOS DAS BASES
 
   /*glBegin(GL_LINE_STRIP);
 
   for(int i=0; i<=slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,0,pz);
 
   }
 
   glEnd();
 
   glBegin(GL_LINE_STRIP);
 
   for(int i=0; i<=slices; i++){
 
      float angle = (2*M_PI)/slices;
 
      float px = radius * sin(i*angle);
 
      float pz = radius * cos(i*angle);
 
      glColor3f(0.0,1.0,0.0);
 
      glVertex3d(px,height,pz);
 
   }
 
   glEnd();
 
*/
 
}
 
 
 
void renderScene(void) {
 
 
   // clear buffers
 
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
 
 
 
   // set the camera
 
   glLoadIdentity();
 
   gluLookAt(px, py, pz,
 
              dx, dy, dz,
 
              0.0f, 1.0f, 0.0f);
 
   glRotatef(ang,0.0f,1.0f,0.0f);
 
   glTranslatef(tx, ty, tz);
 
   drawCylinder(1,2,25);
 
 
   // End of frame
 
   glutSwapBuffers();
 
}
 
 
 
void processKeys(unsigned char key, int xx, int yy) {
 
 
   switch (key) {
 
        case 'w':
 
            dy += 0.2;
 
            break;
 
        case 'a':
 
            dx -= 0.2;
 
            break;
 
        case 's':
 
            dy -= 0.2;
 
            break;
 
        case 'd':
 
            dx += 0.2;
 
            break;
 
        case 'z':
 
            ty += 0.2;
 
            break;
 
        case 'x':
 
            ty -= 0.2;
 
            break;
 
        case 'm':
 
           ang += 5.0;
 
    }
 
    glutPostRedisplay();
 
 
}
 
 
 
void processSpecialKeys(int key, int xx, int yy) {
 
 
}
 
 
 
int main(int argc, char **argv) {
 
 
// init GLUT and the window
 
   glutInit(&argc, argv);
 
   glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
 
   glutInitWindowPosition(100,100);
 
   glutInitWindowSize(800,800);
 
   glutCreateWindow("CG@DI-UM");
 
      
 
// Required callback registry 
 
   glutDisplayFunc(renderScene);
 
   glutReshapeFunc(changeSize);
 
   
 
// Callback registration for keyboard processing
 
   glutKeyboardFunc(processKeys);
 
   glutSpecialFunc(processSpecialKeys);
 
 
//  OpenGL settings
 
   glEnable(GL_DEPTH_TEST);
 
   glEnable(GL_CULL_FACE);
 
   
 
// enter GLUT's main cycle
 
   glutMainLoop();
 
   
 
   return 1;
 
}
