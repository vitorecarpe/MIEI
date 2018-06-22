import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
	private int[N] buff;

	public void put(int x){
		int c=0;

		lock(buff);
		while( !(c>N) ){
			wait();
		}
		unlock(buff);

		buff[c] = v;
		lock();
	}
	public int get(){

	}

}

