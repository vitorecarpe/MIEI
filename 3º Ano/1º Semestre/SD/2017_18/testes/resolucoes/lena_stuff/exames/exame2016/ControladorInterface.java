interface ControladorInterface {

	reserva(int testeId, int[] salaIds);	// docente reserva conjunto de salas
	boolean presenca(int testeId);			// aluno regista presença no teste
	entrega(int testeId);					// aluno entrega o seu teste
	int comecar_limpeza();					// obter sala para limpeza
	terminar_limpeza(int salaId);			// fim de limpeza da sala
}