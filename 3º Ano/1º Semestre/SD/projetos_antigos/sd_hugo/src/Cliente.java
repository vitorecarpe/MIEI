import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Util{
    private static boolean logado;

    synchronized static boolean estaLogado(){
            return Util.logado;
    }

    synchronized static void setLogado(boolean logado){
		Util.logado = logado;
	}
}

class ServidorSender extends Thread {
    PrintWriter out;
    Socket socket;

    public ServidorSender(Socket socket) throws IOException{
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream());
    }

    private void analisarEscolha(int escolha){
        if((Util.estaLogado() == true && (escolha < 3 || escolha > 6)) || 
                (Util.estaLogado() == false && escolha > 2)){
                System.out.println("Opção inválida.");return;
        }

        Scanner scanner = new Scanner(System.in);
        String nome, pass, descricao, id, valor;

        switch(escolha){
            case 1: /* Registar Utilizador*/
                System.out.println("\nNome:");nome = scanner.nextLine();
                System.out.println("\nPass:");pass = scanner.nextLine();
                out.println("registarUtilizador " + nome + " " +  pass);
                out.flush();
                break;
            case 2:	/* Iniciar sessao */
                System.out.println("\nNome:");nome = scanner.nextLine();
                System.out.println("\nPass:");pass = scanner.nextLine();
                out.println("iniciar " + nome + " " +  pass);
                out.flush();
                break;
            case 3: /* Iniciar leilao */
                System.out.println("\nDescricao do leilao:");descricao = scanner.nextLine();
                out.println("iniciarLeilao " + descricao);
                out.flush();
                break;
            case 4: /* Listar leiloes ativos */
                out.println("listarLeiloes");
                out.flush();
                break;
            case 5: /* Licitar */
                System.out.println("ID do leilão: ");id = scanner.nextLine();
                System.out.println("Valor que pretende licitar: "); valor = scanner.nextLine();
                out.println("licitar " + id + ' ' + valor);
                out.flush();
                break;
            case 6: /* Terminar leilao */
                System.out.println("ID do leilão: ");id = scanner.nextLine();
                out.println("terminarLeilao " + id);
                out.flush();
                break;
        }
    }

    @Override
    public void run(){
        String escolhaUtilizador;
        int escolha;
        Scanner scanner = new Scanner(System.in);
        while(true){
            escolhaUtilizador = scanner.nextLine();

            if(escolhaUtilizador == null || escolhaUtilizador.equals("0") || socket.isClosed()){ /* Caso EoF ou escolheu 0*/
                try{
                    if(socket.isClosed() == true){
                        System.out.println("O servidor foi fechado.");
                    }
                    else{
                        socket.shutdownInput();
                        System.out.println("Até à proxima.");
                    }
                    return;
                } catch(IOException e){}
            }

            try{
                escolha = Integer.parseInt(escolhaUtilizador);
                analisarEscolha(escolha);
            }catch(NumberFormatException e){System.out.println("Insira um número.");}
        }
    }
}

class ServidorReceiver extends Thread{
    Socket socket;
    BufferedReader in;

    public ServidorReceiver(Socket socket) throws IOException{
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void mostrarMenu(){
        System.out.println("\n-------------------------------");
        System.out.println("1 - Registar utilizador");
        System.out.println("2 - Iniciar sessão");
        System.out.println("0 - Sair");
        System.out.println("-------------------------------\n");
    }

    private void mostrarSegMenu(){
        System.out.println("\n-------------------------------");
        System.out.println("3 - Iniciar um leilao");
        System.out.println("4 - Listar leilões ativos");
        System.out.println("5 - Licitar");
        System.out.println("6 - Terminar leilão");
        System.out.println("0 - Sair");
        System.out.println("-------------------------------\n");
    }

    private void analisarResposta(StringTokenizer token){
        String nomeFuncao = token.nextToken(), resposta;

        switch (nomeFuncao) {
            case "registarUtilizador":
                resposta = token.nextToken();
                switch (resposta) {
                    case "sucesso":
                        System.out.println("\nRegistado com sucesso.");
                        break;
                    case "erro":
                        System.out.println("\nErro ao registar utilizador.");
                        break;
                }
                break;
            case "iniciar":
                resposta = token.nextToken();
                switch (resposta) {
                    case "sucesso":
                        Util.setLogado(true);
                        System.out.println("\n Bem-vindo.");
                        break;
                    case "erro":
                        System.out.println("\nErro ao iniciar sessao.");
                        break;
                }
                break;
            case "iniciarLeilao":
                resposta = token.nextToken();
                if(resposta.equals("-1"))	System.out.println("\nErro.");
                else	System.out.println("\nLeilão criado com sucesso (ID: " + resposta + ')');
                break;
            case "listarLeiloes":
                while(token.hasMoreTokens()) System.out.println(token.nextToken("|"));
                break;
            case "licitar":
                resposta = token.nextToken();
                switch (resposta) {
                    case "sucesso":
                        System.out.println("Licitado com sucesso.");
                        break;
                    case "valorBaixo":
                        System.out.println("Valor muito baixo.");
                        break;
                    case "erro":
                        System.out.println("Erro");
                        break;
                }
                break;
            case "terminarLeilao":
                resposta = token.nextToken();
                if(resposta.equals("erro")) System.out.println("Nao foi possivel terminar o leilao.");
                else{
                    String idLeilaoTerminado = token.nextToken(), vencedor = token.nextToken(), quantia = token.nextToken();
                    System.out.println("O leilao " + idLeilaoTerminado + " terminou.\nO vencedor foi o/a " +
                            vencedor + " com a quantia de " + quantia + '.');
                }
            break;
            case "erro":
                System.out.println("\nErro!");
                break;
        }
    }

    @Override
    public void run(){
		String mensagemRecebida = null;
		while(true){
			if(Util.estaLogado() == false) mostrarMenu();
			else mostrarSegMenu();
			try{mensagemRecebida = in.readLine();} catch (IOException io){}
			if(mensagemRecebida == null){
                            try {
                                socket.close();
                            } catch (IOException ex) {}
                            return;
                        }
			StringTokenizer token = new StringTokenizer(mensagemRecebida);
			analisarResposta(token);
		}
	}
}

public class Cliente{
	public static void main(String [] args) throws IOException{
		Socket server = new Socket("localhost",12345);
		ServidorReceiver sr = new ServidorReceiver(server);
		ServidorSender ss = new ServidorSender(server);

		sr.start();ss.start();
	}
}