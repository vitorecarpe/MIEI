package data;

import business.Apostador;
import business.Equipa;
import business.Evento;
import business.Odds;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class BetESS implements Serializable {
    private ArrayList<Apostador> apostadores;
    private ArrayList<Evento> eventos;
    private ArrayList<Equipa> equipas; 
    
    public BetESS() {
        this.apostadores = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.equipas = new ArrayList<>();
    }
    public BetESS(BetESS b) {
        this.apostadores = b.getApostadores();
        this.eventos = b.getEventos();
        this.equipas = b.getEquipas();
    }
    
    public boolean criarEvento(String c, String f, Odds odds){
        if(c.equals(f))
            popupWindow(3, "As equipas selecionadas são a mesma. Por favor selecione outra.", "Aviso");
        else{
            Evento evento = new Evento(getEventosSize()+1, odds, true, "", getEquipa(c), getEquipa(f)); 
            eventos.add(evento);
            popupWindow(1, "Evento criado e disponível.", "Sucesso");
            return true;
        }
        return false;
    }
    public void finalizarEvento(String jogo, String res){
        String[] jogoEquipas = jogo.split(" X ");
        for(Evento e : getEventosAtivos()){
            if(e.getEquipaCasaNome().equals(jogoEquipas[0]) && e.getEquipaForaNome().equals(jogoEquipas[1])){
                e.setEstado(false);
                e.setResultado(res);
                e.distribuirPremios(getApostadores());
                popupWindow(1, "Evento encerrado e prémios distribuídos.", "Sucesso");
            }
        }
    }
    
    public ArrayList<Apostador> getApostadores(){
        return this.apostadores;
    }
    public ArrayList<Equipa> getEquipas(){
        return this.equipas;
    }
    public ArrayList<Evento> getEventos(){
        return this.eventos;
    }
    public int getApostadoresSize(){
        return apostadores.size();
    }
    public int getEventosSize(){
        return eventos.size();
    }

    public ArrayList<Evento> getEventosAtivos(){
        return eventos.stream().filter(e -> e.getEstado()).collect(Collectors.toCollection(ArrayList::new));
    }
    public int getEventosAtivosSize(){
        return eventos.stream().filter(e -> e.getEstado()).collect(Collectors.toCollection(ArrayList::new)).size();
    }
    public Evento getEventoAtivo(int i){
        return eventos.stream().filter(e -> e.getEstado()).collect(Collectors.toCollection(ArrayList::new)).get(i);
    }
    public Equipa getEquipa(String nome){
        for(Equipa e : equipas)
            if(e.getNome().equals(nome))
                return e;
        return null;
    }
    
    public void popupWindow(int tipo, String texto, String titulo){
        ImageIcon icon = new ImageIcon();
        if(tipo==1)      icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/check.png"));
        else if(tipo==2) icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/warning.png"));
        else if(tipo==3) icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/forbidden.png"));
        else if(tipo==4) icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/ball.png"));
        JOptionPane.showMessageDialog(null, texto, titulo, JOptionPane.INFORMATION_MESSAGE, icon);
    }
    
    public Apostador login(String email, String pass){
        if(email.equals("admin") && pass.equals("admin"))
            return new Apostador();
        else
            for (Apostador a : getApostadores())
                if (a.getEmail().compareTo(email)==0 && a.getPassword().compareTo(pass)==0) 
                    return a;
        popupWindow(3, "Dados incorretos!", "Aviso");
        return null;
    }
    public boolean registarApostador(Apostador a, boolean aut){
        for(Apostador ap : getApostadores())
            if(ap.checkEmail(a.getEmail())){
                popupWindow(3, "Já foi registado um apostador com esse email. Por favor tente outro.", "Aviso");
                return false;
            }
        if(aut){
            apostadores.add(a);
            popupWindow(1, "Registado com sucesso!", "Sucesso");
            return true;
        } else popupWindow(2, "A autorização é necessária para o registo.", "Aviso");
        return false;
    }
    
    public BetESS load() throws IOException {
        BetESS b = new BetESS();
        try{
            FileInputStream fileIn = new FileInputStream("betess.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            b = (BetESS) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Data loaded\n");
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found\n");
        }
        return b;
    }
    public void save() {
        ObjectOutputStream out = null;
        try {
            try (FileOutputStream fileOut = new FileOutputStream("betess.obj")) {
                out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.flush();
                out.close();
            }
            System.out.printf("Data saved\n");
        } catch (IOException ex) {
            Logger.getLogger(BetESS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(BetESS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public BetESS povoar(){
        this.povoarEquipas();
        this.povoarApostadores();
        this.povoarEventos();
        System.out.println("New data reloaded!\n");
        return this;
    }
    public void povoarEquipas(){
        Equipa belenenses   = new Equipa(0, "Belenenses SAD", true, "resources/equipas/belenenses.png");
        Equipa boavista     = new Equipa(1, "Boavista FC", true, "resources/equipas/boavista.png");
        Equipa tondela      = new Equipa(2, "CD Tondela", true, "resources/equipas/tondela.png");
        Equipa aves         = new Equipa(3, "CD Aves", true, "resources/equipas/aves.png");
        Equipa feirense     = new Equipa(4, "CD Feirense", true, "resources/equipas/feirense.png");
        Equipa nacional     = new Equipa(5, "CD Nacional", true, "resources/equipas/nacional.png");
        Equipa maritimo     = new Equipa(6, "CS Marítimo", true, "resources/equipas/maritimo.png");
        Equipa porto        = new Equipa(7, "FC Porto", true, "resources/equipas/porto.png");
        Equipa chaves       = new Equipa(8, "GD Chaves", true, "resources/equipas/chaves.png");
        Equipa moreirense   = new Equipa(9, "Moreirense FC", true, "resources/equipas/moreirense.png");
        Equipa portimonense = new Equipa(10, "Portimonense SC", true, "resources/equipas/portimonense.png");
        Equipa rioave       = new Equipa(11, "Rio Ave FC", true, "resources/equipas/rioave.png");
        Equipa santaclara   = new Equipa(12, "Santa Clara", true, "resources/equipas/santaclara.png");
        Equipa benfica      = new Equipa(13, "SL Benfica", true, "resources/equipas/benfica.png");
        Equipa braga        = new Equipa(14, "SC Braga", true, "resources/equipas/braga.png");
        Equipa sporting     = new Equipa(15, "Sporting CP", true, "resources/equipas/sporting.png");
        Equipa setubal      = new Equipa(16, "Vitória FC", true, "resources/equipas/setubal.png");
        Equipa guimaraes    = new Equipa(17, "Vitória SC", true, "resources/equipas/guimaraes.png");
       
        equipas.add(belenenses);
        equipas.add(boavista);
        equipas.add(tondela);
        equipas.add(aves);
        equipas.add(feirense);
        equipas.add(nacional);
        equipas.add(maritimo);
        equipas.add(porto);
        equipas.add(chaves);
        equipas.add(moreirense);
        equipas.add(portimonense);
        equipas.add(rioave);
        equipas.add(santaclara);
        equipas.add(benfica);
        equipas.add(braga);
        equipas.add(sporting);
        equipas.add(setubal);
        equipas.add(guimaraes);
    }
    public void povoarApostadores(){
        Apostador a = new Apostador(0, "joaonunes@gmail.com", "joaonunes", "João Nunes", 25.00, new ArrayList<>());
        Apostador b = new Apostador(1, "saramoreno@gmail.com", "saramoreno", "Sara Moreno", 5.00, new ArrayList<>());
        Apostador c = new Apostador(2, "pauloprazeres@gmail.com", "pauloprazeres", "Paulo Prazeres", 2.92, new ArrayList<>());
        Apostador d = new Apostador(3, "albanojeronimo@gmail.com", "albanojeronimo", "Albano Jerónimo", 89.20, new ArrayList<>());
        Apostador e = new Apostador(4, "nunolopes@gmail.com", "nunolopes", "Nuno Lopes", 102.36, new ArrayList<>());
        Apostador f = new Apostador(5, "marcomartins@gmail.com", "marcomartins", "Marco Martins", 19.76, new ArrayList<>());
        Apostador g = new Apostador(6, "miguelguilherme@gmail.com", "miguelguilherme", "Miguel Guilherme", 15.58, new ArrayList<>());
        Apostador h = new Apostador(7, "beatrizbatarda@gmail.com", "beatrizbatarda", "Beatriz Batarda", 5.01, new ArrayList<>());
        
        this.apostadores.add(a);
        this.apostadores.add(b);
        this.apostadores.add(c);
        this.apostadores.add(d);
        this.apostadores.add(e);
        this.apostadores.add(f);
        this.apostadores.add(g);
        this.apostadores.add(h);
    }
    public void povoarEventos(){
        Evento j1j1 = new Evento(0, new Odds(1.11, 3.94, 8.71), true, "", equipas.get(7), equipas.get(9));
        Evento j1j2 = new Evento(1, new Odds(2.10, 2.81, 4.50), true, "", equipas.get(12), equipas.get(16));
        Evento j1j3 = new Evento(2, new Odds(1.72, 3.81, 7.21), true, "", equipas.get(17), equipas.get(10));
        Evento j1j4 = new Evento(3, new Odds(1.68, 3.54, 7.02), true, "", equipas.get(0), equipas.get(1));
        Evento j1j5 = new Evento(9, new Odds(2.13, 2.81, 2.50), true, "", equipas.get(14), equipas.get(13));
        Evento j1j6 = new Evento(5, new Odds(1.42, 3.36, 3.47), true, "", equipas.get(8), equipas.get(6));
        Evento j1j7 = new Evento(6, new Odds(1.09, 3.81, 8.98), true, "", equipas.get(15), equipas.get(5));
        Evento j1j8 = new Evento(7, new Odds(1.87, 2.98, 3.49), true, "", equipas.get(3), equipas.get(2));
        Evento j1j9 = new Evento(8, new Odds(1.42, 3.25, 3.99), true, "", equipas.get(11), equipas.get(4));
        
        this.eventos.add(j1j1);
        this.eventos.add(j1j2);
        this.eventos.add(j1j3);
        this.eventos.add(j1j4);
        this.eventos.add(j1j5);
        this.eventos.add(j1j6);
        this.eventos.add(j1j7);
        this.eventos.add(j1j8);
        this.eventos.add(j1j9);
    }
}