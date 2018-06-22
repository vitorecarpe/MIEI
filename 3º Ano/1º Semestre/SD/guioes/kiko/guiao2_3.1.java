//SD
//Guiao 2


//////// Synchronized Counter  
//////// 3.1)

//// Method Sync
class Contador {
	long i;
	public synchronized long get() {
		return i;
	}
	public synchronized void inc() {
		i++;
	}
}
class Incrementer implements Runnable {
	Contador c;
	Incrementer(Contador c1) {
		c = c1;
	}
	public void run() {
		Integer i = new Integer(0);
		for (long i=0; i<1000000; i++)
			c.inc();
	}
}

//// Block Sync
class Contador {
	long i;
	public long get() {
		return i;
	}
	public void inc() {
		i++;
	}
}
class Incrementer implements Runnable {
	Contador c;
	Incrementer(Contador c1) {
		c = c1;
	}
	public void run() {
		Integer i = new Integer(0);
		for (long i=0; i<1000000; i++)
			synchronized(c) { c.inc(); }
	}
}

