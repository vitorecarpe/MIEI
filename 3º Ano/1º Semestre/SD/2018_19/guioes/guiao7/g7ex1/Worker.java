import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class Worker implements Runnable{
    private Banco banco;
    private Socket clientSocket;
    private int id;

    public Worker(Banco banco, Socket clientSocket, int id){
        this.banco = banco;
        this.clientSocket = clientSocket;
        this.id = id;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
    
            System.out.println("Worker"+id+"> Processando conexao");
            String msg;
            while ((msg = in.readLine()) != null && !msg.equals("exit")) {
                System.out.println(" Cliente"+id+"> " + msg);

                String[] parts = msg.split(" ");
                switch (parts[0]) {
                    case "1": // criar conta
                    case "new":
                        int idConta = this.banco.criarConta(Integer.parseInt(parts[1]));
                        out.println("Worker"+id+"> Conta criada com sucesso !!! ID="+idConta);
                        break;
                    case "2": // fechar conta
                    case "close":
                        try {
                            double saldo = this.banco.fecharConta(Integer.parseInt(parts[1]));
                            out.println("Worker"+id+"> Conta fechada com sucesso. Saldo="+saldo);
                        } catch (ContaInvalida e) {
                            out.println("Worker"+id+"> ERRO: Conta nao existe !!!");
                        }
                        break;
                    case "3": // consultar conta
                    case "check":
                        try {
                            double saldo = this.banco.consultar(Integer.parseInt(parts[1]));
                            out.println("Worker"+id+"> Saldo da conta: "+saldo+"€");
                        } catch (ContaInvalida e) {
                            out.println("Worker" + id + "> ERRO: Conta nao existe !!!");
                        }
                        break;
                    case "4": // levantar dinheiro
                    case "take":
                        try {
                            this.banco.levantar(Integer.parseInt(parts[1]),Double.parseDouble(parts[2]));
                            out.println("Worker"+id+"> Levantamento foi processado com sucesso");
                        } 
                        catch (ContaInvalida e) {
                            out.println("Worker"+id+"> ERRO: Conta nao existe !!!");
                        }
                        catch(SaldoInsuficiente e) {
                            out.println("Worker"+id+"> ERRO: Nao ha saldo suficiente na conta !!!");
                        }
                        break;
                    case "5": // depositar dinheiro
                    case "put":
                        try {
                            this.banco.depositar(Integer.parseInt(parts[1]),Double.parseDouble(parts[2]));
                            out.println("Worker"+id+"> Deposito foi processado com sucesso");
                        } catch (ContaInvalida e) {
                            out.println("Worker" + id + "> ERRO: Conta nao existe !!!");
                        }
                        break;
                    case "6": // tranferir dinheiro
                    case "tranfer":
                        try {
                            this.banco.transferir(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),
                                                    Double.parseDouble(parts[3]));
                            out.println("Worker" + id + "> Tranferencia foi processada com sucesso");
                        } 
                        catch (ContaInvalida e) {
                            out.println("Worker"+id+"> ERRO: Conta nao existe !!!");
                        }
                        catch(SaldoInsuficiente e) {
                            out.println("Worker"+id+"> ERRO: Nao ha saldo suficiente na conta !!!");
                        }
                        break;
                    default:
                        out.println("Worker"+id+"> Pedido incorreto. Tente novamente.");
                        break;
                }
                out.flush();
            }
            
            out.close();
            in.close();
            clientSocket.close();
            System.out.println("Worker"+id+"> Terminando conexao");
        }
        catch(IOException e){}
    }
}