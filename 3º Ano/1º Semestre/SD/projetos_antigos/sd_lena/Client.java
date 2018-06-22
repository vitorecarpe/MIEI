import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	this.cs = new Socket("127.0.0.1", 9999);
		
	Thread tr = new Thread(new clientRead(cs));
	Thread tw = new Thread(new clientWrite(cs));
	tr.start();
	tw.start();
	//cs.shutdownOutput();
	//in.close();
	//out.close();
	//cs.close();
}