import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

/*
Considere um sistema cliente/servidor de uma variante do jogo de ”Batalha Naval”.
O servidor faz a gestão de uma grande matriz de n x m de submarinos; cada posição da
matriz vale 1 se estiver ocupada ou 0 se estiver livre. Suponha que no inı́cio do
jogo o conjunto de jogadores é conhecido e a matriz é inicializada de forma aleatória
por uma função iniciar, já existente. O servidor aceita pedidos do tipo disparo c i j
cujos argumentos identificam o cliente e as coordenadas do disparo (por exemplo,
”joao 13 245”). A pontuação de cada disparo é calculada somando todos os submarinos do
segmento da linha i entre j − 4 e j + 4, que são afundados, não voltando a contar para
outro disparo. Cada jogador pode efectuar 3 disparos. No terceiro disparo, o cliente
é bloqueado até ao final do jogo, momento em que recebe como resposta o identificador
do jogador vencedor.
O jogo termina quando todos os jogadores usarem os seus disparos. Escreva em Java o
código do servidor de forma a que atenda eficientemente pedidos concorrentes.
 */

public class Matrix {
	int[][] submarinos;
	Map<String, Integer> jogadores;
	int nrAcabados;
	ReentrantLock lock;

	public Matrix(int m, int n, Map<String, Integer> jogadores) {
		this.submarinos = new int[m][n];
		this.jogadores = jogadores;
		this.nrAcabados = 0;
		this.lock = new ReentrantLock();
	}

	public void iniciar() {
		return;
	}

	boolean jogoAcabou() {
		System.out.println(nrAcabados);
		System.out.println(jogadores.size());
		return nrAcabados == jogadores.size();
	}

	public String getVencedor() {
		int max = 0;
		String jogador = null;

		for(Map.Entry<String, Integer> entry : this.jogadores.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				jogador = entry.getKey();
			}
		}
		return jogador;
	}

	void pedido(String jogador, int coordenadaX, int coordenadaY, int nrPedido) {
		int j = coordenadaY - 4;
		int n = coordenadaY + 4;

		this.lock.lock();
		try {
			int pontuacao = this.jogadores.get(jogador);
	
			for(; j <= n; j++) {
				if(submarinos[coordenadaX][j] == 1) {
					pontuacao++;
					submarinos[coordenadaX][j] = 0;
				}
			}
	
			this.jogadores.put(jogador, pontuacao);
					synchronized (this) {
			System.out.println(nrPedido == 3);
			if(nrPedido == 3) {
				this.nrAcabados++;
				this.notifyAll();
			}
		}
		}
		finally {
			this.lock.unlock();
		}	

		System.out.println("pedido processado");
	}
}