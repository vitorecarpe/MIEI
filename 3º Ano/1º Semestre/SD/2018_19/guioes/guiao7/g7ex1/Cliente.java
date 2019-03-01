import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class Cliente{
    public static void main(String[] args) throws IOException{
        final Socket socket = new Socket("127.0.0.1", 1234);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        String msg;
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while( (msg=systemIn.readLine())!=null && !msg.equals("exit") ){
            out.println(msg);
            out.flush();
            System.out.println("Server Banco> " + in.readLine());
        }

        out.close();
        in.close();
        socket.close();
    }

}