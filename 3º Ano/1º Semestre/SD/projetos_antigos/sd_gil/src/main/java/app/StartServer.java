package app;

import business.MainServer;
import data.Data;

/**
 * Start a server instance
 */
public class StartServer{

    /**
     * Loads stored data (if exists)
     * @return
     */
    public static MainServer loadData(){
        try {
            MainServer main = (MainServer) Data.load("data.dat");
            main.clearLastSessionData();
            return main;
        }
        catch (Exception e){
            System.out.println("No data found.");
            return null;
        }
    }

    /**
     * Starts server
     * @param args Arguments
     */
    public static void main(String[] args) {

        MainServer server = loadData();
        if (server == null) server = new MainServer();
        server.start();
    }
}