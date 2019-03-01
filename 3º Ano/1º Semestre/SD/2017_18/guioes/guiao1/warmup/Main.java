package warmup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String args[]) {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			System.out.print("> ");
			while((line=input.readLine())!=null){
				System.out.println("Echo: " + line);
				System.out.print("> ");
			}
		} catch (IOException e) {
			System.err.println("Error reading line from System.in!");
			e.printStackTrace();
		}
	}
}
