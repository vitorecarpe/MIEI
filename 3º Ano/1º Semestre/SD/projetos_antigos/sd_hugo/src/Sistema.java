import java.util.List;
import Exceptions.*;

interface Sistema{
	public void registarUtilizador(String nome, String password) throws UtilizadorJaExisteException;
	public Utilizador iniciar(String nome, String password) throws UtilizadorNExisteException;
	public int iniciarLeilao(String nome, String descricao);
	public List <String> listarLeiloes(String nome);
	public void licitar(int nrLeilao, String nome, float valor) throws LeilaoNExisteException, UtilizadorPossuiLeilaoException, ValorBaixoException;
	public void terminarLeilao(int nrLeilao, String nome) throws UtilizadorNaoPossuiLeilaoException,LeilaoNExisteException;
	
}