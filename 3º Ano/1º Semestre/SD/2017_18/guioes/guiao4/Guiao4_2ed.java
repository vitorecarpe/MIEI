//Aula 4

public int createAccount(int saldo){
	int id;
	Conta c = new Conta();
	c.credito(saldo);
	l.lock();
	contas.put(nextId, c);
	id = nextId;
	nextId += 1;
	l.unlock();
	return id;
}
/*
public int closeAccount(int id){
	Conta c = new Conta();
	c.credito(saldo);
	l.lock();
	contas.put(nextId,c);
	id = nextId;
	nextId += 1;
	l.unlock();
	return id;
}
*/
public int saldo(int i) throws InvalidAccount{
	Conta c = null;
	l.lock();
	try{
		c = contas.get(i);
		if(c==null) throw new InvalidAccount();
		c.l.lock();
	} finally {
		l.unlock();
	}
	c.l.lock();
	try{
		return c.saldo();
	} finally {
		c.l.unlock();
	}
}