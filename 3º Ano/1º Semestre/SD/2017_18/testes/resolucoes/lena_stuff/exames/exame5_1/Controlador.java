import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;


public class Controlador implements ControladorInterface {

	private Map<Integer, Questao> questoes;
	private int nrQuestoes;
	private ReentrantLock lock;
	private Condition disponivel;

	public Controlador() {
		this.questoes = new HashMap<Integer, Questao>();
		this.nrQuestoes = 0;
		this.lock = new ReentrantLock();
		this.disponivel = lock.newCondition();
	}

	public int getNrQuestoes() {
		return this.nrQuestoes;
	}


	/*
	A operação adiciona introduz um novo par pergunta-resposta, criando um objecto
	Questao, etiquetado por um id numérico crescente (1, 2, 3, . . . ).
	 */
	public void adiciona(String pergunta, String resposta) {
		this.lock.lock();

		this.questoes.put(nrQuestoes, new Questao(nrQuestoes, pergunta, resposta));
		System.out.println(nrQuestoes);
		nrQuestoes++;

		disponivel.signal();

		this.lock.unlock();
	}

	/*
	A operação obtem devolve um objecto que representa uma questão que tiver
	sido previamente adicionada, com id maior do que o argumento, e que se encontre
	ainda disponı́vel: não tenha ainda sido respondida correctamente nem tenha sido sujeita
	a mais de 10 tentativas de resposta. Caso não exista nenhuma, deverá bloquear
	até tal ser possı́vel.
	 */
	public Questao obtem(int id) {
		this.lock.lock();

		Questao q = null;

		System.out.println("Pergunta " + this.questoes.get(id));

		if(!this.questoes.get(id).disponivel()) {
			this.questoes.remove(id);
			// mandar uma exceção aqui?
			return null;
		}

		try {
			while(id > nrQuestoes) disponivel.wait();
			q = this.questoes.get(id);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			this.lock.unlock();
		}
		return q;
	}
}