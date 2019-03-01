import java.io.*;
import java.net.*;
import java.util.*;

interface ControladorInterface {
	void adiciona(String pergunta, String resposta);
	Questao obtem(int id);
}