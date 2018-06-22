import java.net.*;
import java.io.*;
import java.util.*;

class TreatClient implements Runnable
{
    Socket cs;
    Players players;
    Players wantToPlay;
    Player player;
    PrintWriter out;
    BufferedReader in;

    TreatClient (Socket cs, Players players, Players wantToPlay) {
        this.cs = cs;
        this.players = players;
        this.wantToPlay = wantToPlay;
    }


/* 
    public Player inicial(PrintWriter out, BufferedReader in) {
            try {

            player = null;
            String username = in.readLine();
            String password = in.readLine();
            int loginSignUp = Integer.parseInt(in.readLine());
           
            if (loginSignUp == 0) player = players.login(username, password); //se é 0 faz login
            else players.signUp(username, password); //se é 1 faz signUp
             }
            catch ( InexistentUserException e) { 
                System.out.println(e.getMessage());
                inicial(out,in);
            }catch ( AlreadyLoggedInException e){
                System.out.println(e.getMessage());
                inicial(out,in);
            }catch ( ExistentUserException e){
                System.out.println(e.getMessage());
                inicial(out,in);
            }
            catch ( IOException e) { 
                e.printStackTrace();
                inicial(out,in);
            }
            return player;
            
    }
*/
    public Player inicial(PrintWriter out, BufferedReader in) {
        boolean flag = false;    
        while(!flag){
            try {

            player = null;
            String username = in.readLine();
            String password = in.readLine();
            int loginSignUp = Integer.parseInt(in.readLine());
            System.out.println("oi");
            if (loginSignUp == 0) player = players.login(username, password); //se é 0 faz login
            else player = players.signUp(username, password); //se é 1 faz signUp
            flag = true;
             }
            catch ( InexistentUserException e) { 
                System.out.println(e.getMessage());
                //out.println("autentica");
            }catch ( AlreadyLoggedInException e){
                System.out.println(e.getMessage());
                //out.println("shutdown");
            }catch ( ExistentUserException e){
                System.out.println(e.getMessage());
                //out.println("autentica");
            }
            catch ( IOException e) { 
                e.printStackTrace();
                //out.println("autentica");-
            }
        }
        return player;
            
}



    public void mandaInformacao() {
            out.println(player.getRanking());
            out.println(player.getNumMatches());
            out.println(player.getWonMatches());
    }


    public void run() {
        
        try {
        	this.out = new PrintWriter( cs.getOutputStream(), true); 
	        this.in = new BufferedReader( new InputStreamReader( cs.getInputStream() ) );
	        
           // out.println("autentica");

            System.out.println("oi");
            Player player = inicial(out, in);
	        String username=player.getUsername();
			String current;	       
	        System.out.println(username + " is on!");
	        
            mandaInformacao();
            while ((current = in.readLine()) != null)
	        {
	            System.out.println(username + ": " + current);
	        }
	        in.close();
	        out.println(username + " is off!");
	        out.close();
	        cs.close();
	        System.out.println("Connection Closed");
        	}
            catch ( IOException e) { 
            	e.printStackTrace();
            }
        
    }
}




public class serverSD{
    public static void main(String[] args) throws IOException {
        
        ServerSocket ss = new ServerSocket(9999);     
        Socket cs = null;
        Players players = new Players ();
        Players wantToPlay = new Players();
        while (true)
        {
            cs = ss.accept();                     
            Thread t = new Thread( new TreatClient(cs, players, wantToPlay) );

            t.start();
        }

        //ss.close();
    }
}
