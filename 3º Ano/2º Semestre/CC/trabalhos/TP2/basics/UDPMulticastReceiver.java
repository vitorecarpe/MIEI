import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMulticastReceiver {
    public static void main(String[] args) throws Exception {
        int mcPort = 8888;
        String mcIPStr = "239.8.8.8";
        // InetAddress group = InetAddress.getByName(mcIPStr);
        MulticastSocket mcSocket = null;

        mcSocket = new MulticastSocket(mcPort);
        mcSocket.joinGroup(InetAddress.getByName(mcIPStr));
        System.out.println("Multicast Receiver running at:" + mcSocket.getLocalSocketAddress());
        System.out.println("Waiting for a  multicast message...");

        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        mcSocket.receive(packet);
        String msg = new String(packet.getData(), 
                                packet.getOffset(), 
                                packet.getLength());
        System.out.println("[Multicast  Receiver] Received:" + msg);

        try { mcSocket.leaveGroup(InetAddress.getByName(mcIPStr)); } 
        catch (Exception e) { System.out.println(e); }
        mcSocket.close();
    }
}