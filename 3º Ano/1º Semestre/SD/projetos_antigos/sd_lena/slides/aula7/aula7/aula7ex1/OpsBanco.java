package aula7ex1;

public enum OpsBanco {
	//operações sobre o banco
	CRIAR_CONTA,
	FECHAR_CONTA,
	CONSULTAR,
	CONSULTAR_TOTAL,
	LEVANTAR,
	DEPOSITAR,
	TRANSFERIR,
	
	//estado da operação
	OK,
	OP_INVALIDA,
	SALDO_INSUFICIENTE,
	CONTA_INVALIDA;
}
