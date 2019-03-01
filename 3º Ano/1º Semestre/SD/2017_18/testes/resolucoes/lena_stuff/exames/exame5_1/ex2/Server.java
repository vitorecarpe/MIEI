import java.io.*;
import java.net.*;
import java.util.*;

/*
Escreva um programa servidor que usando threads, sockets TCP e a biblioteca acima, permita
que clientes remotos tentem responder a perguntas. Cada cliente ligado, até fechar a
ligação, poderá em ciclo: enviar ”Pergunta”, esperar pelo enunciado de uma pergunta, enviar
a resposta e esperar pelo resultado, que deverá ser ”Respondida”, ”Certa”, ou ”Errada”.
O servidor não deve devolver ao mesmo cliente perguntas repetidas. O servidor deverá
adicionar uma nova pergunta por minuto, utilizando um método Util.novaPergunta(), que
devolve um array com duas strings: pergunta e resposta correspondente.
 */


public class Server {
	public static void main(String[] args) throws IOException {
		Controlador c = new Controlador();

		ServerSocket ss = new ServerSocket(12345);
		Socket s = null;

		Thread qMaker = new Thread(new QuestaoMaker(c));

		qMaker.start();

		while(true) {
			s = ss.accept();

			Thread client = new Thread(new TreatClient(c, s));

			client.start();
		}
	}
}