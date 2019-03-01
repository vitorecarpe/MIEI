import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.time.LocalDateTime;

public class Presenca {
	private String user;
	private int espaco_id;
	private LocalDateTime entrada;
	private LocalDateTime saida;

	Presenca(String user, int espaco_id) {
		this.user = user;
		this.espaco_id = espaco_id;
		this.entrada = LocalDateTime.now();
		this.saida = null;
	}

	public String getUser() {
		return this.user;
	}

	public int getEspacoId() {
		return this.espaco_id;
	}

	public String getEntrada() {
		return this.entrada.toString();
	}

	void sair() {
		this.saida = LocalDateTime.now();
	}
}