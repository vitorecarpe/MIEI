import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

interface Bank{
	int createAccount(float initialBalance);
	float closeAccount(int id) throws InvalidAccount;
	void transfer(int from,int to,float amount) 
		throws InvalidAccount, NotEnoughFunds;
	float totalBalance(int accounts[])
		throws InvalidAccount;
}

class InvalidAccount extends Exception{}
class NotenoughFunds extends Exception{}

class Account {
	private float bal;
	private final ReentrantLock l = new ReentrantLock();
	boolean closed = false;

	public Account(float bal) {this.bal=bal;}

	public void lock() {l.lock();}
	public void unlock() {l.unlock();}

	public balance() {return bal;}
	public withdraw(float amount) {bal-=amount;}
	public deposit(float amount) {bal+=amount;}
}

class BankImpl implements Bank {
	private final Map<Integer,Account> map = new HashMap<Integer,Account>();
	int nextId = 0;

	public synchronized int createAccount(float initialBalance){
		int id = nextId;
		nextId++;
		map.put(id, new Account(initialBalance));
		return id;
	}

	public float closeAccount(int id) throws InvalidAccount {
		Account ac = null;
		float bal = 0.0f;
		synchronized(this){
			ac = map.get(id);
			if(ac==null) throw new InvalidAccount(id);
			map.remove(id);
		}
		ac.lock();
		ac.closed() = true;
		bal = ac.unlock();
		return bal;
	}
	/*VITOR STUFF (aula 4)
	public int closeAccount(int id) throws InvalidAccount{
		l.lock();
		try{
			Conta c = contas.get(id);
			if (c==null) throw new InvalidAccount(id);
			contas.remove(id);
			c.l.lock();
		} finally {
			l.unlock();
		}
		try {
			return c.saldo();
		} finally{
			c.l.unlock();
		}
	}
	*/

	public void transfer(int from, int to, float amount) throws InvalidAccount, NotEnoughFunds{
		Account f = null;
		Account t = null;

		synchronized(this){
			f = map.get(from);
			if(f==null) throw new InvalidAccount(from);
			t = map.get(to);
			if(t==null) throw new InvalidAccount(to);
		}
		if(from<to){
			f.lock();
			t.lock();
		} else{ // why trocar ordem?
			t.lock()
			f.lock()
		}

		if(f.closed || t.closed){
			f.unlock();
			t.unlock();
			throw new NotEnoughFunds();
		}
		f.withdraw(amount);
		f.unlock();
		t.deposit(amount);
		t.unlock();
	}
	/*
	public void transferencia(int i, int j, int m) throws InvalidAccount{
		Conta c1, c2;
		l.lock();
		try{
			c1=contas.get(i);
			if(c1==null) throw new InvalidAccount(i)
			c2=contas.get(j);
			if(c2==null) throw new InvalidAccount(j);
			if(i<j){
				c1=contas[i];
				c2=contas[j];
			}
			else{
				c1=contas[j];
				c2=contas[i];
			}
		} finally {
			l.unlock();
		}
		if(c1.saldo()<m){
			c1.l.unlock();
			c2.l.unlock();
			throw new NotEnoughFunds();
		}
		c1.debito(m);
		c1.l.unlock();
		c2.credito(m);
		c2.l.unlock();
	}
	*/

	public float totalBalance(int accounts[]) throws InvalidAccount{
		int[] id = accounts.clone();
		Arrays.sort(id);
		Account[] ac = new Account[id.length];

		synchronized(this){
			for(int i=0; i<id.length; i++){
				ac[i] = map.get(id[i]);
				if(ac==null) throw new InvalidAccount(id[i]);
			}
		}
		for(int=0; i<id.length; i++){
			ac[i].lock();
			if(ac[i].closed){
				for(int j=0; j<=i; j++)
					ac[j].unlock();
				throw new InvalidAccount(id[i]);
			}
		}
		float sum = 0.0f;
		for(int=0; i<id.length; i++){
			sum += ac[i].balance();
			ac[i].unlock();
		}

		return sum;
	}
}