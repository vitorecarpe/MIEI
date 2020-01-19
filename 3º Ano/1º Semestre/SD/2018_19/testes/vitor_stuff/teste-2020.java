import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import jdk.internal.org.jline.utils.InputStreamReader;

// TESTE 2020

public class Controlo implements CTA{
    private Condition cond;
    private HashMap<Integer,Boolean> ocup;
    private ReentrantLock l;

    public Controlo(int num){
        this.l = new ReentrantLock();
        this.cond = this.l.newCondition();
        for(int i = 1; i<=num; i++){
            this.ocup.put(i, false);
        }
    }

    public int pedirDescolar(){
        int pista = 0;
        this.l.lock();
        while(pista==0){
            for(Integer a : this.ocup.keySet())
                if(!this.ocup.get(a) && pista==0) pista = a;
            if(pista==0)
                this.cond.await();
        }

        this.ocup.put(pista, true);
        Thread.sleep(5*60);
        this.descolou(pista);
        return pista;
    }

    public int pedirAterrar(){
        int pista = 0;
        this.l.lock();
        while(pista==0){
            for(Integer a : this.ocup.keySet())
                if(!this.ocup.get(a) && pista==0) pista = a;
            if(pista==0)
                this.cond.await();
        }

        this.ocup.put(pista, true);
        Thread.sleep(5*60);
        this.aterrou(pista);
        this.l.unlock();
        return pista;
    }

    public void descolou(int pista){
        this.l.lock();
        this.ocup.put(pista, false);
        this.cond.signalAll();
        this.l.unlock();
    }

    public void aterrou(int pista){
        this.l.lock();
        this.ocup.put(pista, false);
        this.cond.signalAll();
        this.l.unlock();
    }
}


// VALORIZAÇÃO 1 - adicionar uma variável flip (sim, pode criar
// deadlocks pq muitas aterragens podem ser bloqueadas por
// esperarem por apenas uma descolagem, por exemplo, mas a
// verdade é que esta solução já respeita o requisito i.)
public class Controlador implements CTA{
    private int flipper; //let 0 = vez aterrar, 1 = vez descolar
    // restantes variaveis here...

    public Controlador(int num){
        flipper = 2; //aceita o que chegar primeiro
        // ...
    }

    public int pedirDescolar(){
        int pista = 0;
        this.l.lock();
        while(pista==0){
            for(Integer a : this.ocup.keySet())
                if(!this.ocup.get(a) && pista==0) pista = a;
            if(pista==0 || this.flipper != 1) //ADD THIS 
                this.cond.await();
        }

        this.ocup.put(pista, true);
        this.flipper = 0; // ADD THIS
        Thread.sleep(5*60);
        this.descolou(pista);
        return pista;
    }

    // REPETIR O MESMO PARA ATERRAR (EXCETO VALOR DO FLIPPER)
    // METODOS DESCOLOU E ATERROU NAO SAO INFLUENCIADOS
}

// VALORIZAÇÃO 2 - Usar LinkedList (funciona como um FIFO)
public class Controlador implements CTA{
    private LinkedList<Condition> condDescolar;
    private LinkedList<Condition> condAterrar;
    // restantes variáveis here...

    public Controlador(int num){
        this.condAterrar = new LinkedList<>(this.l.newCondition());
        this.condDescolar = new LinkedList<>(this.l.newCondition());
        // ...
    }

    public int pedirDescolar(){
        int pista = 0;
        Condition cond = this.l.newCondition();
        this.l.lock();
        while(pista==0){
            for(Integer a : this.ocup.keySet())
                if(!this.ocup.get(a) && pista==0) pista = a;
            if(pista==0)
                this.condDescolar.add(cond.await());
        }
        this.ocup.put(pista, true);
        Thread.sleep(5*60);
        this.descolou(pista);
        return pista;
    }
    public int pedirAterrar(){
        int pista = 0;
        Condition cond = this.l.newCondition();
        this.l.lock();
        while(pista==0){
            for(Integer a : this.ocup.keySet())
                if(!this.ocup.get(a) && pista==0) pista = a;
            if(pista==0)
                this.condAterrar.add(cond.await());
        }
        this.ocup.put(pista, true);
        Thread.sleep(5*60);
        this.aterrou(pista);
        return pista;
    }

    public void descolou(int pista){
        this.l.lock();
        this.ocup.put(pista, false);
        this.condAterrar.removeFirst().signalAll(); // isto torna desnecessário o uso do Flipper
        this.l.unlock();
    }
    public void aterrou(int pista){
        this.l.lock();
        this.ocup.put(pista, false);
        this.condDescolar.removeFirst().signalAll(); // isto torna desnecessário o uso do Flipper
        this.l.unlock();
    }
}

// VALORIZAÇÃO 3 - Se é para intercalar, então já não passa
// o max. numero de aterragens, mas se cagassemos nisso, a
// solução seria um counter que dava reset qd houvesse uma
// aterragem e incrementava a cada descolagem. Aos 10, dava
// signal a uma aterragem (e novamente counter = 0).

public class WorkerPlane implements Runnable{
    private Controlador ctl;
    private Socket s;

    public WorkerPlane(Controlador ctl){
        this.ctl=ctl;
        this.s = new Socket("localhost", 5000);
    }
    public void run(){
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());

        out.println("Pretende aterrar ou descolar?");
        out.flush();
        String resposta = in.readLine();
        
        if(resposta.equals("aterrar")){
            ctl.pedirAterrar();
        } 
        else if (resposta.equals("descolar")){
            ctl.pedirDescolar();
        }

        out.println("Obrigado pela preferência ;)")

        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
}

public class Servidor {
	public static void main(String[] args){
		ServerSocket ss = new ServerSocket(5000);
		Controlador ctl = new Controlador(10);

		while(true){
			Socket sc = ss.accept();
			new Thread(new WorkerPlane(sc, ctl)).start();
		}
	}
}