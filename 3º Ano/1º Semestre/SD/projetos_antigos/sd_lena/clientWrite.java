import java.net.*;
import java.io.*;
import java.util.*;

public class clientWrite implements Runnable{

	private Socket cs;
	private PrintWriter out;
	private BufferedReader sin;

	public clientWrite(Socket cs){
		this.cs=cs;
		try {
			this.out = new PrintWriter(cs.getOutputStream(), true);
			this.sin = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}