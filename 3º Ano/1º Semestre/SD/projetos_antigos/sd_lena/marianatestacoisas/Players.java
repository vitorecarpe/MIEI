import java.util.*;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Exception;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class Players{
	Map<String,Player> players;
	ReentrantLock lock = new ReentrantLock ();;

	public Players (){
		this.players = new HashMap<String,Player>();
	}

	public Player signUp (String user, String pass) throws ExistentUserException{
		
		lock.lock();
		Player p;
		if (players.containsKey(user)) throw new ExistentUserException("utilizador já existente!!");
		else{
			p = new Player(user, pass);
			players.put(user,p);
		}
		lock.unlock();
		return p;
	}

	public Player login (String user, String pass) throws InexistentUserException, AlreadyLoggedInException{
		lock.lock();
		Player p = players.get(user);
		lock.unlock();
		if (p == null) throw new InexistentUserException("utilizador inexistente");
		p.login(pass);
		return p;
	}

	public void logout (String user){
		lock.lock();
		Player p = players.get(user);
		if (p != null) p.logout();
		lock.unlock();
	}

}