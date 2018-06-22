package aula7ex1;

public interface InterfaceBanco {
	int criarConta(double saldo);
	double fecharConta(int id) throws ContaInvalida;
	double consultar(int id) throws ContaInvalida;
	double consultarTotal(int[] contasInput);
	void levantar(int id, double valor) throws SaldoInsuficiente, ContaInvalida;
	void depositar(int id, double valor) throws ContaInvalida;
	void transferir(int conta_origem, int conta_destino, double valor) throws ContaInvalida, SaldoInsuficiente;
}
