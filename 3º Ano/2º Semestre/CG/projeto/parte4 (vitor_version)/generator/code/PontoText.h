using namespace std;

class PontoText {
private:
	float x, y;
public:
	PontoText() {
		x = 0;
		y = 0;
	};
	PontoText(float a, float b) {
		x = a;
		y = b;
	}
	float getX() {
		return x;
	}
	float getY() {
		return y;
	}
};