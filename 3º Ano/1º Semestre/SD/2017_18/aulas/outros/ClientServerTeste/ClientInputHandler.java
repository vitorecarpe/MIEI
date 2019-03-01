package ClientServerTeste;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Jan 19, 2009
 * Time: 9:18:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientInputHandler implements Runnable{
    private DataInputStream entrada;

    public ClientInputHandler(DataInputStream ent){
        this.entrada = ent;
    }

    public void run() {

        while(true){
            try {
                String lido = entrada.readUTF();
                System.out.println(lido);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

    }
}
