package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Janela de Erro.
 * @author Daniel Fernandes
 * @author Maria Helena Poleri
 * @author Mariana Miranda
 * @author Pedro Fernandes
 */
public final class Erro {

	/**
	 * Decifra o significado do erro.
	 * @param msg	Erro
	 * @return		Significado do erro.
	 */
	public static String trataException(String msg) {
		String error_msg = null;
		switch(msg) {
			case "1": error_msg = "Exceção IO. Não me perguntes porque também não sei.";
					  break;
			case "2": error_msg = "O utilizador não existe!";
					  break;
			case "3": error_msg = "Já tem sessão iniciada!";
					  break;
			case "4": error_msg = "O utilizador já existe!";
					  break;
			case "5": error_msg = "Não preencheu o campo do username!";
					  break;
			case "6": error_msg = "Não preencheu o campo da password!";
					  break;
			case "7": error_msg = "Esse herói já foi escolhido!";
			  		  break;
			case "8": error_msg = "Excedeu o tempo de escolher equipas!";
					  break;
			case "9": error_msg = "A password usada na autenticação é errada.";
					  break;
			default: error_msg = "Erro desconhecido.";
					  break;
		}
		return error_msg;
	}
	
	/**
	 * Constroí uma janela de Erro.
	 * @param msg	Erro.
	 */
	public static void start(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText(trataException(msg));
		
		alert.showAndWait();
	}
}
