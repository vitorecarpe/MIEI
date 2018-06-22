package app;

import business.Bot;

/**
 * Start a bot instance
 */
public class StartBot{

    /**
     * Starts bot
     * @param args Arguments
     */
    public static void main(String[] args){
        String name = null;
        String email = null;
        String password = null;

        if (args.length == 2){
            name = args[0];
            password = args[1];
        }
        else if (args.length == 3){
            name = args[0];
            email = args[1];
            password = args[2];
        }
        else{
            System.err.println("Wrong number of arguments. Start with [username] [password] to login" +
                    " or [username] [email] [password] to register");
            System.exit(-2);
        }

        (new Thread(new Bot(name, email, password))).start();
    }
}