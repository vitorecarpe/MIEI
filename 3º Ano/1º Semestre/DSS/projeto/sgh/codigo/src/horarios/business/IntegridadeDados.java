package horarios.business;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Funções de integridade de dados que restringe as opções disponíveis para determinados campos.
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @author Vitor Peixoto
 */
public class IntegridadeDados {
    //FUNÇÕES DE INTEGRIDADE DE DADOS
    public void diaList(String text){
        if(!text.equals("Segunda") && !text.equals("Terça") && !text.equals("Quarta") &&
           !text.equals("Quinta") && !text.equals("Sexta") && !text.equals("")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Dado inválido para Dia! \nDia deve ser: Segunda, Terça, Quarta, Quinta ou Sexta.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void horaList(String hora){
        if(!hora.equals("9") && !hora.equals("11") && !hora.equals("14") && !hora.equals("16") && !hora.equals("")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Dado inválido para Hora! \nAs horas de aulas são: 9, 11, 14 e 16.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void tipoList(String tipo){
        if(!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3") && !tipo.equals("")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Dado inválido para Tipo! \nOs tipos são: 1 (Teórica), 2 (Teórico-Prática) e 3 (Prática).", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void anoList(String tipo){
        if(!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3") && !tipo.equals("4") && !tipo.equals("5") && !tipo.equals("6") && !tipo.equals("")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Dado inválido para Ano! \nO número máximo de anos possíveis num curso são 6.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
    public void semestreList(String tipo){
        if(!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("")){
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/warning.png"));
            JOptionPane.showMessageDialog(null, "Dado inválido para Semestre! \nO número máximo de semestres possíveis são 2.", "Aviso", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
}