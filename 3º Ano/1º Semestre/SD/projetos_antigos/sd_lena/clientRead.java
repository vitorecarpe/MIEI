import java.net.*;
import java.io.*;
import java.util.*;

public class clientRead implements Runnable{

	private Socket cs;
	private BufferedReader in;

	public clientRead(Socket cs){
		this.cs = cs;
		try {
			this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}