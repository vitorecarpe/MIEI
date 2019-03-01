import java.io.*;
import java.net.*;
import java.util.*;

public class TreatClient implements Runnable {
	private Controlador c;
	private Socket s;
	private ArrayList<Integer> respondidas;

	public TreatClient(Controlador c, Socket s) {
		this.c = c;
		this.s = s;
		this.respondidas = new ArrayList<>();
	}

	public void veredito(String veredito, PrintWriter out) {
		switch(veredito) {
			case "R": out.println("Respondida");
					  break;
			case "C": out.println("Certa");
					  break;
			case "E": out.println("Errada");
					  break;
		}
	}

	/*
	Cada cliente ligado, até fechar a ligação, poderá em ciclo: enviar ”Pergunta”,
	esperar pelo enunciado de uma pergunta, enviar a resposta e esperar pelo resultado,
	que deverá ser ”Respondida”, ”Certa”, ou ”Errada”
	 */

	@Override 
	public void run() {

		String line = null, resposta = null, veredito = null;
		int id = -1;

		try (Socket s = this.s;
			 PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
			
			while((line = in.readLine()) != null) {

				if (!line.equals("Pergunta"))
					continue;

				Questao q = c.obtem(id);

				out.println(q.getPergunta());	 	// mostra pergunta ao cliente
				resposta = in.readLine();			// recebe resposta
				veredito = q.responde(resposta);	// verifica resposta

				if(!veredito.equals("E")) id = q.id();
				veredito(veredito, out);
	
				respondidas.add(q.id());
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}