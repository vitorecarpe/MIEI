package agenteUDP;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class AgenteProbeListenerThread extends Thread{
    private AtomicBoolean running = new AtomicBoolean(true);
    
    private int portToSend = 8888;
    private int packetSize = 1024;
    private int port;
    private InetAddress group;
    
    public AgenteProbeListenerThread(InetAddress address, int port){
        this.port = port;
        this.group = address;
    }
    
    // Interrompe o Agente
    public void stopAgenteProbe(){
        this.running.set(false);
        this.interrupt();
        System.out.println("\n [AgenteProbe] AgenteProbe terminado !!!");
    }
        
    public void run(){
        try (MulticastSocket mcSocket = new MulticastSocket(port)) {
            mcSocket.setReuseAddress(true);
            mcSocket.joinGroup(group);
            while(running.get()){
                System.out.println(" [AgenteProbeListener] Multicast Receiver running at: " + mcSocket.getLocalSocketAddress());
                DatagramPacket packetRecebido = new DatagramPacket(new byte[packetSize], packetSize);
                mcSocket.receive(packetRecebido);
                // desencriptar
                
                InetAddress destino = packetRecebido.getAddress();
                String msg = new String(packetRecebido.getData(), packetRecebido.getOffset(), packetRecebido.getLength());
                System.out.println(" [AgenteProbeListener] Received from: " + destino.toString() + ":" + packetRecebido.getPort());
                System.out.println(" [AgenteProbeListener] Message: " + msg);

                if("PROBING".equals(msg.toString())){ // resposta ao ProbRequest
                    Random rand = new Random();
                    Thread.sleep(rand.nextInt(10)); // Random delay entre 0 a 10ms
                    
                    com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    double cpuLoad = os.getSystemCpuLoad();
                    long freePhysicalMemory = os.getFreePhysicalMemorySize();
                    System.out.println("  [AgenteProbeAnswer] CPU load = " + cpuLoad);
                    System.out.println("                      RAM free = " + freePhysicalMemory);

                    try (DatagramSocket udpSocket = new DatagramSocket()) {
                        byte[] answer = ("STATUS\nCPU=" + cpuLoad + "\nRAM=" + freePhysicalMemory).getBytes();
                        // encriptar
                        
                        DatagramPacket packetAnswer = new DatagramPacket(answer, answer.length, destino, portToSend);
                        udpSocket.send(packetAnswer);
                        System.out.println("  [AgenteProbeAnswer] Status sent.");
//                        udpSocket.close();
                    }
                    catch(IOException e){ System.out.println("Porta (udpSocket) indisponivel\n" + e); }
                }
            }
            mcSocket.leaveGroup(group);
//            mcSocket.close();
        }
        catch (IOException e)          { System.out.println("Porta (mcSocket) indisponivel\n" + e); }
        catch (InterruptedException e) { System.out.println("\n  Interrompido !!!" + e); }
        //catch (Exception e) { System.out.println(e); }
    }
}