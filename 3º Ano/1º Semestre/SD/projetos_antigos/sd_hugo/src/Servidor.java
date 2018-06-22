import java.util.concurrent.locks.*;
import java.util.*;
import java.net.*;
import java.io.*;
import Exceptions.*;

class Utilizador implements Comparable<Utilizador>{
    String nome, password;
    private boolean logado;
    private List <String> enviar; /* Lista de respostas a enviar pro cliente */
    private Lock lock;
    private Condition temMsgsNovas;

    /* Verifica se pode ou n fazer login, se puder marca logado a true */
    public boolean mayLogIn(String pass) {
        lock.lock();
        try {
            if(pass.equals(password) && logado == false){
                logado = true;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Utilizador(String nome, String password){
        this.nome=nome;
        this.logado = false;
        this.password=password;
        this.enviar = new ArrayList<>();
        lock = new ReentrantLock();
        temMsgsNovas  = lock.newCondition(); 
    }

    public void inserirResposta(String resposta){
        lock.lock();
        try {
            this.enviar.add(resposta);
            temMsgsNovas.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String lerResposta(ClienteSender cs) throws InterruptedException{
        lock.lock();
        try {
            
            String resposta;
            
            while(enviar.isEmpty() && cs.ePrecisoParar() == false){
                temMsgsNovas.await(); /* Espera por respostas novas a enviar */
            }
            
            if(cs.ePrecisoParar() == true){ /* O cliente fez logout */
                logado = false;
                return null;
            }
            
            resposta = enviar.get(0); /* Elemento mais antigo */
            enviar.remove(0); /* Remove o elemento da lista */
            
            
            return resposta;
        } finally {
            lock.unlock();
        }
    }

    public void signal(){
        lock.lock();
        temMsgsNovas.signalAll();
        lock.unlock();
    }
    @Override
    public int compareTo(Utilizador o) {
        return this.nome.compareTo(o.nome);
    }
}

class Leilao {
    int idLeilao;
    String descricao;
    private boolean terminado;
    private float valorItem;
    private final Utilizador donoLeilao;
    private Utilizador licitadorMaior;
    private TreeMap <Utilizador, Float> tentativas;

    public Leilao(Utilizador donoLeilao, String descricao, int idLeilao){
            this.descricao = descricao; this.terminado = false; this.valorItem = 0;
            this.licitadorMaior = null; this.donoLeilao = donoLeilao;this.idLeilao = idLeilao;
            this.tentativas = new TreeMap<>();
    }

    public void leiloar(Utilizador licitador, float quantia){
        if(!(tentativas.containsKey(licitador))){ /* Este licitador nunca apostou neste leilao */
           tentativas.put(licitador,quantia);
        }

        else{
            Float valorAntigo = tentativas.get(licitador);
            if(valorAntigo < quantia){ /* So atualiza o valor se o valor antigo for menor */
                valorAntigo = quantia;
            }
        }

        if(quantia > valorItem){ /* Atualiza se tivermos um novo licitador com maior valor */
            licitadorMaior = licitador;
            valorItem = quantia;
        }
    }

    public void terminarLeilao(){
        this.terminado = true;
    }

    public Set <Utilizador> envolvidos(){
        return tentativas.keySet();
    }

    public boolean isTerminado() {
        return terminado;
    }

    public float getValorItem() {
        return valorItem;
    }

    public Utilizador getLicitadorMaior() {
        return licitadorMaior;
    }

    public Utilizador getDonoLeilao() {
        return donoLeilao;
    }
          
}

class SistemaServidor implements Sistema{
    TreeMap <String, Utilizador> utilizadores;
    TreeMap <Integer, Leilao> leiloes;
    Lock lock = new ReentrantLock();

    public SistemaServidor(){
        this.utilizadores = new TreeMap<>(); 
        this.leiloes = new TreeMap<>(); 
    }

    @Override
    public void registarUtilizador(String nome, String password) throws UtilizadorJaExisteException{
        lock.lock();
        try { 
            if(utilizadores.containsKey(nome))  throw new UtilizadorJaExisteException();
            else{
                Utilizador utilizador = new Utilizador(nome,password);
                utilizadores.put(nome, utilizador);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Utilizador iniciar(String nome, String password) throws UtilizadorNExisteException{
        lock.lock();
        try {
            Utilizador existeNome = utilizadores.get(nome);
            if(existeNome == null) /* Existe */
                throw new UtilizadorNExisteException();
            if(!existeNome.mayLogIn(password))
                throw new UtilizadorNExisteException();
            
            return existeNome;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int iniciarLeilao(String nome, String descricao){
        lock.lock();
        try {
            Utilizador donoLeilao = this.utilizadores.get(nome);
            if(donoLeilao==null){ /* Nao existe o dono */
                return -1;
            }
            int idLeilao = this.leiloes.size() + 1;
            Leilao novoLeilao = new Leilao(donoLeilao, descricao, idLeilao);
            this.leiloes.put(idLeilao, novoLeilao);
            return idLeilao;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List <String> listarLeiloes(String nome){
        lock.lock();
        try {
            List <String> resposta = new ArrayList <>();
            Iterator <Leilao> todosLeiloes = this.leiloes.values().iterator();
            
            if(todosLeiloes.hasNext() == false){
                resposta.add("Nao existem leilões ativos.");
                return resposta;
            }
            
            resposta.add("\tID\t Descriçãot\t Valor €\t Maior licitador |");
            while(todosLeiloes.hasNext()){ /* Percorrer todos os leiloes */
                Leilao este = todosLeiloes.next();
                
                if(este.isTerminado() != true){ /* So interessa os terminados */
                    
                    String stringLeilao;
                    Utilizador donoLeilao = este.getDonoLeilao(), licitadorMaior = este.getLicitadorMaior();
                    int idLeilao = este.idLeilao;
                    String descricao = este.descricao;
                    float valorItem = este.getValorItem();
                    
                    if(donoLeilao.nome.equals(nome)){ /* É o dono do leiao (*) */
                        if(licitadorMaior != null) /* Ja existe um licitador maior */
                            stringLeilao = "*\t" + idLeilao + '\t' + descricao + '\t'+ valorItem + "\t\t" + licitadorMaior.nome;
                        else /* Ainda nao existe nenhum licitador */
                            stringLeilao = "*\t" + idLeilao + '\t' + descricao + '\t'+ valorItem;
                    }
                    else if(licitadorMaior != null && licitadorMaior.nome.equals(nome)){ /* + */
                        stringLeilao = "+\t" + este.idLeilao + '\t' + este.descricao + '\t'
                                + valorItem + '\t' + licitadorMaior.nome;
                    }
                    else{
                        if(licitadorMaior != null)
                            stringLeilao = "\t" + idLeilao + '\t' + descricao + '\t'+ valorItem + "\t\t" + licitadorMaior.nome;
                        else
                            stringLeilao = "\t" + idLeilao + '\t' + descricao + '\t'+ valorItem;
                    }
                    resposta.add(stringLeilao + " |");
                }
                
            }
            
            return resposta;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void licitar(int nrLeilao, String nome, float valor) throws LeilaoNExisteException, UtilizadorPossuiLeilaoException, ValorBaixoException{
        lock.lock();
        try {
            Leilao leilao = leiloes.get(nrLeilao);
            Utilizador utilizador = utilizadores.get(nome);
            /*Excecoes*/
            if(leilao == null || leilao.isTerminado() == true) throw new LeilaoNExisteException();
            if(leilao.getDonoLeilao().nome.equals(nome)){
                throw new UtilizadorPossuiLeilaoException();
            }
            if(leilao.getValorItem() >= valor){
                throw new ValorBaixoException();
            }
            
            leilao.leiloar(utilizador, valor);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void terminarLeilao(int nrLeilao, String nome) throws UtilizadorNaoPossuiLeilaoException, LeilaoNExisteException{
        lock.lock();
        try {
            Leilao leilao = this.leiloes.get(nrLeilao);
            
            if(leilao == null || leilao.isTerminado() == true){ /* Leilao nao existe ou ja terminou */
                throw new LeilaoNExisteException();
            }
            
            if(!(leilao.getDonoLeilao().nome.equals(nome))){ /* O utilizador nao possui o leilao */
                throw new UtilizadorNaoPossuiLeilaoException();
            }
            
            leilao.terminarLeilao();
        } finally {
            lock.unlock();
        }
    }

    public Set <Utilizador> envolvidosNumLeilao(int nrLeilao) throws LeilaoNExisteException{
        lock.lock();
        try {
            Leilao leilao = this.leiloes.get(nrLeilao);
            
            if(leilao == null){ /* Leilao nao existe */
                throw new LeilaoNExisteException();
            }
            
            return leilao.envolvidos();
        } finally {
            lock.unlock();
        }
    }
    
    public String vencedorEQuantiaLeilao(int idLeilao){
        lock.lock();
        try {
            String resposta = null;
            Leilao leilao = leiloes.get(idLeilao);
            
            if(leilao.isTerminado() == true){ /* Leilao ja terminou */
                Utilizador licitadorMaior = leilao.getLicitadorMaior();
                if(licitadorMaior != null){ /* Existe vencedor */
                    resposta = licitadorMaior.nome + " " + leilao.getValorItem();
                }
            }
            return resposta;
        } finally {
            lock.unlock();
        }
    }
}

class ClienteSender extends Thread{
    Socket socket;
    PrintWriter out;
    SistemaServidor sistemaLeiloes;
    Utilizador utilizador;
    boolean temQTerminar;

    public ClienteSender(Socket socket, PrintWriter out, SistemaServidor sistemaLeiloes, Utilizador utilizador)
    throws IOException{
        this.socket = socket;
        this.out = out;
        this.sistemaLeiloes = sistemaLeiloes;
        this.utilizador = utilizador;
        this.temQTerminar = false;
    }

    @Override
    public void run(){
        String mensagem;
        while(true){
            try{
                mensagem = utilizador.lerResposta(this);
                
                if(mensagem == null){ /* O cliente saiu */
                    socket.shutdownInput();
                    socket.close();
                    return;
                }
                
                out.println(mensagem);
                out.flush();
            } catch (InterruptedException | IOException e){}
        }
    }
    
    public synchronized void temQParar(){
        temQTerminar = true; 
        /* Temos que acordar a thread q pode estar a dormir*/
        utilizador.signal();
    }
    
    public synchronized boolean ePrecisoParar(){
        return this.temQTerminar;
    }
}

class ClienteReciever extends Thread{
    BufferedReader in;
    PrintWriter out;
    SistemaServidor sistemaLeiloes;
    Socket socket;
    Utilizador utilizador;

    public ClienteReciever(Socket socket, SistemaServidor sistemaLeiloes) 
    throws IOException{
            this.socket = socket;
            this.utilizador = null;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
            this.sistemaLeiloes = sistemaLeiloes;
    }

    private void analisarPedido(StringTokenizer token){
        String nomeFuncao = token.nextToken();

        switch (nomeFuncao) {
            case "iniciarLeilao":
                {
                    if(token.countTokens() != 1){ utilizador.inserirResposta("erro"); break;}
                    int idLeilao = this.sistemaLeiloes.iniciarLeilao(utilizador.nome, token.nextToken(""));
                    utilizador.inserirResposta("iniciarLeilao " + idLeilao);
                    break;
                }
            case "listarLeiloes":
                List <String> listaLeiloes = sistemaLeiloes.listarLeiloes(utilizador.nome);
                StringBuilder respostaBuilder = new StringBuilder();
                Iterator <String> iterador = listaLeiloes.iterator();
                while(iterador.hasNext()) respostaBuilder.append(" ").append(iterador.next());
                utilizador.inserirResposta("listarLeiloes" + respostaBuilder.toString());
                break;
            case "licitar":
                {
                    if(token.countTokens() != 2){ utilizador.inserirResposta("erro"); break;}
                    String idLeilao = token.nextToken();
                    String valor = token.nextToken();
                    int id;
                    float valorLicitacao;
                    try{
                        id = Integer.parseInt(idLeilao);
                        valorLicitacao = Float.parseFloat(valor);
                        sistemaLeiloes.licitar(id,utilizador.nome,valorLicitacao);
                        utilizador.inserirResposta("licitar sucesso");
                    }
                    catch(ValorBaixoException e){utilizador.inserirResposta("licitar valorBaixo");}
                    catch(NumberFormatException | LeilaoNExisteException | UtilizadorPossuiLeilaoException e){
                        utilizador.inserirResposta("licitar erro");
                    }
                    break;
                }
            case "terminarLeilao":
            {
                if(token.countTokens() != 1){ utilizador.inserirResposta("erro"); break;}
                String idLeilao = token.nextToken();
                int id;
                try{
                    String vencedor, quantia;
                    id = Integer.parseInt(idLeilao);
                    sistemaLeiloes.terminarLeilao(id,utilizador.nome);
                    String vencedorQuantia = sistemaLeiloes.vencedorEQuantiaLeilao(id);
                    if(vencedorQuantia == null){
                        vencedor = "(ninguem)";
                        quantia = "0";
                    }
                    else{
                        StringTokenizer st = new StringTokenizer(vencedorQuantia);
                        vencedor = st.nextToken(); 
                        quantia = st.nextToken();
                    }
                    /* Avisar o dono*/
                    utilizador.inserirResposta("terminarLeilao sucesso " + id + " " + vencedor + " " + quantia);
                    /* Avisar envolvidos */ 
                    for(Utilizador u : sistemaLeiloes.envolvidosNumLeilao(id)){
                        u.inserirResposta("terminarLeilao sucesso " + id + " " + vencedor + " " + quantia);
                    }
                }
                catch(NumberFormatException | UtilizadorNaoPossuiLeilaoException | LeilaoNExisteException e){
                    utilizador.inserirResposta("terminarLeilao erro");}
                    break;
            }
            default:
            {
                utilizador.inserirResposta("erro");
            }
        }
    }

    /* Esta thread vai comunicar (receber e enviar) com o cliente enquanto ele nao inicia sessao
            Depois do cliente iniciar sessao esta thread apenas recebe do cliente e é criada outra thread que envia pro cliente */
    private void antesIniciarSessao() throws IOException{ 
        String pedido = null;
        while(true){
            try{
                pedido = in.readLine();
            } catch (IOException io){}
            if(pedido == null){
                socket.shutdownInput();
                socket.close();
                return;
            }
            StringTokenizer token = new StringTokenizer(pedido);
            String nomeFuncao;
            if(token.countTokens() == 0)    nomeFuncao = "erro";
            else    nomeFuncao = token.nextToken();

            switch (nomeFuncao) {
                case "registarUtilizador":
                    {
                        try{
                            if(token.countTokens() != 2) throw new UtilizadorJaExisteException(); /* Serve para enviar erro */
                            String nome = token.nextToken();
                            String pass = token.nextToken();
                            sistemaLeiloes.registarUtilizador(nome,pass);
                            out.println("registarUtilizador sucesso");
                            out.flush();
                        }
                        catch(UtilizadorJaExisteException e){
                            out.println("registarUtilizador erro");
                            out.flush();
                        }   break;
                    }
                case "iniciar":
                {
                    try{
                        if(token.countTokens() != 2)    throw new UtilizadorNExisteException();
                        String nome = token.nextToken();
                        String pass = token.nextToken();
                        utilizador = sistemaLeiloes.iniciar(nome,pass);
                        utilizador.inserirResposta("iniciar sucesso");
                        ClienteSender cs = new ClienteSender(socket, this.out, this.sistemaLeiloes, utilizador);
                        cs.start();
                        depoisIniciarSessao(cs);
                        return;
                    }
                    catch(UtilizadorNExisteException e){
                        out.println("iniciar erro");
                        out.flush();
                    }
                }
                default:
                {
                    out.println("erro");
                    out.flush();
                }
            }
        }
    }

    private void depoisIniciarSessao(ClienteSender sender) throws IOException{
        String pedido;
        while(true){
                pedido = in.readLine();
                if(pedido == null){
                    sender.temQParar();
                    return;
                }
                StringTokenizer token = new StringTokenizer(pedido);
                analisarPedido(token);
        }
    }

    @Override
    public void run(){
		try{
			antesIniciarSessao();
		}catch(IOException e){}
	}
}

public class Servidor{
	public static void main(String [] args) throws IOException{
		SistemaServidor sistemaLeiloes = new SistemaServidor();
		ServerSocket server = new ServerSocket(12345);

		while(true){
			try{
				Socket socket = server.accept();
				ClienteReciever cr = new ClienteReciever(socket, sistemaLeiloes);
				cr.start();
			}
			catch(IOException io){}
		}
	}
}