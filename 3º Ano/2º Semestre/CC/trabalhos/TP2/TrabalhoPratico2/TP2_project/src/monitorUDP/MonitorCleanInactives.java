package monitorUDP;

import java.util.concurrent.atomic.AtomicBoolean;

public class MonitorCleanInactives extends Thread{
    private AtomicBoolean running = new AtomicBoolean(true);
    private Tabela tabela;
    
    public MonitorCleanInactives(Tabela tabela) {
        this.tabela = tabela;
    }
    
    public void stopMonitorCleaner(){
        this.running.set(false);
        this.interrupt();
        System.out.println("\n [MonitorCleaner] MonitorCleaner terminado!!!");
    }
    
    public void run(){
        while(running.get()){
            try {
                Thread.sleep(20000); // corre a cada 20segundos
                tabela.removeInativos();
                System.out.println("  [MonitorCleaner] Inactives have been removed!!!");
            } 
            catch (InterruptedException e) { System.out.println("  [MonitorCleaner] ERROR..."); }
        }
    }
    
}
