package ClientServerTeste;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nelson
 * Date: Jan 19, 2009
 * Time: 10:23:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientOutputHandler implements Runnable{
    private DataOutputStream output;

    public ClientOutputHandler(DataOutputStream saida){
        this.output= saida;
    }

    public void run() {
        BufferedReader leitura = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try {
                String lido = leitura.readLine();
                output.writeUTF(lido);
            } catch (IOException e) {
                //
            }
        }
    }
}
