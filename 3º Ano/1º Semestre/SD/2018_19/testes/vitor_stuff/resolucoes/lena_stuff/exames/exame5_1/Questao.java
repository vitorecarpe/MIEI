import java.io.*;
import java.net.*;
import java.util.*;

public class Questao implements QuestaoInterface {
	private int id;
	private String pergunta;
	private String resposta;
	private int tentativas;
	private boolean jaAcertaram;

	/*
	A operação obtem devolve um objecto que representa uma questão que tiver sido
	previamente adicionada, com id maior do que o argumento, e que se encontre ainda
	disponı́vel: não tenha ainda sido respondida correctamente nem tenha sido sujeita a
	mais de 10 tentativas de resposta.
	 */

	public Questao(int id, String pergunta, String resposta) {
		this.id = id;
		this.pergunta = pergunta;
		this.resposta = resposta;
		this.tentativas = 10;
		this.jaAcertaram = false;
	}

	public String getPergunta() {
		return this.pergunta;
	}

	boolean disponivel() {
		return (!this.jaAcertaram) && (this.tentativas > 0);
	}

	public String responde(String resposta) {
		/*  O método responde deverá devolver ”R”, ”C”, ou ”E”, conforme a questão
			já tiver sido previamente respondida correctamente, a resposta esteja certa,
			ou a resposta esteja errada, respectivamente.
		 */
		this.tentativas--;
		if(this.resposta.equals(resposta) && this.jaAcertaram)
			return "R";
		else if (!this.resposta.equals(resposta))
			return "E";
		else {
			this.jaAcertaram = true;
			return "C";
		}
	}

	public int id() {
		return this.id;
	}
}