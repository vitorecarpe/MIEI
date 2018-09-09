import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMulticastSender {
    public static void main(String[] args) throws Exception {
        int mcPort = 8888;
        String mcIPStr = "239.8.8.8";
        // InetAddress group = InetAddress.getByName(mcIPStr);
        DatagramSocket udpSocket = new DatagramSocket();

        byte[] msg = "HELLO WORLD".getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName(mcIPStr),mcPort);
        // packet.setAddress(InetAddress.getByName(mcIPStr));
        // packet.setPort(mcPort);
        udpSocket.send(packet);

        System.out.println("[Multicast Sender] Sent a  multicast message.");
        System.out.println("Exiting application");
        udpSocket.close();
    }
}