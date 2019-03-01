import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;


public class Controlador implements ControladorInterface {

	private Map<Integer, Questao> questoes;
	private int nrQuestoes;

	public Controlador() {
		this.questoes = new HashMap<Integer, Questao>();
		this.nrQuestoes = 0;
	}

	public int getNrQuestoes() {
		return this.nrQuestoes;
	}


	/*
	A operação adiciona introduz um novo par pergunta-resposta, criando um objecto
	Questao, etiquetado por um id numérico crescente (1, 2, 3, . . . ).
	 */
	public synchronized void adiciona(String pergunta, String resposta) {
		this.questoes.put(nrQuestoes, new Questao(nrQuestoes, pergunta, resposta));
		nrQuestoes++;

		this.notifyAll();
	}

	/*
	A operação obtem devolve um objecto que representa uma questão que tiver
	sido previamente adicionada, com id maior do que o argumento, e que se encontre
	ainda disponı́vel: não tenha ainda sido respondida correctamente nem tenha sido sujeita
	a mais de 10 tentativas de resposta. Caso não exista nenhuma, deverá bloquear
	até tal ser possı́vel.
	 */
	public synchronized Questao obtem(int id) {

		Questao q = null;

		int i = id;

		for(; i < nrQuestoes; i++) {
			q = this.questoes.get(i);
			if (q != null && q.disponivel()) return q;
			if (q != null && !q.disponivel()) this.questoes.remove(id);
		}
		try {
			while((q = this.questoes.get(nrQuestoes - 1)) == null
				|| !q.disponivel()) {
				wait();
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		return q;
	}
}