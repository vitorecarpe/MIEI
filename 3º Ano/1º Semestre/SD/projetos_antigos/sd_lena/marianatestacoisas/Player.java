import java.util.*;
import java.lang.*;

public class Player{
	private int ranking;
	private String username;
	private String password;
	private int numMatches;
	private int wonMatches;
	private boolean loggedIn;

	public Player (String user, String pass){
		this.ranking = 0;
		this.username = user;
		this.password = pass;
		this.numMatches = 0;
		this.wonMatches = 0;
		this.loggedIn = true;
	}

	// construtor utilizado unicamente para casos de teste
	public Player (String user, String pass, int rank){
		this.ranking = rank;
		this.username = user;
		this.password = pass;
		this.wonMatches = rank;
		this.numMatches = 9;
		this.loggedIn = false;
	}

	public String getUsername (){
		return this.username;
	}

	public int getRanking (){
		return this.ranking;
	}


	
	public int getNumMatches(){
		return this.numMatches;
	}

	public int getWonMatches(){
		return this.wonMatches;
	}

	public boolean isLoggedIn (){
		return this.loggedIn;
	}

	public void login (String pass) throws AlreadyLoggedInException{
		if (!this.password.equals(pass) || (this.loggedIn == true)) throw new AlreadyLoggedInException("Já tem sessão aberta noutra máquina!");
		this.loggedIn = true;
	}

	public void logout (){
		this.loggedIn = false;
	}

	public void updateRanking (boolean matchResult){
		this.numMatches++;
		if (matchResult == true) this.wonMatches++;
		this.ranking = calcRanking(numMatches, wonMatches);
	}

	public int calcRanking(int total, int won){
		if (total == 0) return 0;
		return (won/total)*9;
	}
}