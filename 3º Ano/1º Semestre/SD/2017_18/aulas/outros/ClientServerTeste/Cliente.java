package ClientServerTeste;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {
    public static void main(String args[]) throws IOException {
        Socket s = new Socket("localhost",50000);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        Thread input = new Thread(new ClientInputHandler(dis));
        Thread output = new Thread(new ClientOutputHandler(dos));
        input.start();
        output.start();




    }
}
