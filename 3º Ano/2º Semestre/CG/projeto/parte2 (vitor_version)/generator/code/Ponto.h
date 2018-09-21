using namespace std;

class Ponto {
private:
	float x, y, z;
public:
	Ponto() {
		x = 0;
		y = 0;
		z = 0;
	};
	Ponto(float a, float b, float c) {
		x = a;
		y = b;
		z = c;
	}
	float getX() {
		return x;
	}
	float getY() {
		return y;
	}
	float getZ() {
		return z;
	}
};