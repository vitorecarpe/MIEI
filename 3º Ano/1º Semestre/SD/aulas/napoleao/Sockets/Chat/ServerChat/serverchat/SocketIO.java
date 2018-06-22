package serverchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketIO {

	private static ObjectInputStream objInput;
	private static ObjectOutputStream objOutput;

	public synchronized static Object readObject(Socket skt) throws IOException, ClassNotFoundException {
		synchronized (skt) {
			objInput = new ObjectInputStream(skt.getInputStream());
			return objInput.readObject();
		}
	}

	public static void writeObject(Socket skt, Object obj) throws IOException {
		System.out.println("trying to sync skt");
		//synchronized (skt) {
				System.out.println("sending object " + obj.toString());
				objOutput = new ObjectOutputStream(skt.getOutputStream());
				objOutput.writeObject(obj);
				objOutput.flush();
				System.out.println("Object sent");
		//}
		System.out.println("exited writeObject");
	}
}
