/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import business.Admin;
import business.Aposta;
import business.Apostador;
import business.Bookie;
import business.Equipa;
import business.Evento;
import business.EventoFutebol;
import business.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author danie
 */
public class Data implements Serializable{
    private HashMap<String,User> utilizadores;
    private HashMap<Integer,Evento> eventos;
    private HashMap<Integer,Equipa> equipas;
    private HashMap<Integer,Aposta> apostas;
    
    public Data(){
        this.utilizadores = new HashMap<>();
        this.eventos = new HashMap<>();
        this.equipas = new HashMap<>();
        this.apostas = new HashMap<>();
    }
    
    public Data(HashMap<String,User> a, HashMap<Integer,Evento> ev, HashMap<Integer,Equipa> eq, HashMap<Integer,Aposta> ap){
        this.utilizadores = a;
        this.eventos = ev;
        this.equipas = eq;
        this.apostas = ap;
    }
    
    public Data(Data d){
        this.utilizadores = d.getUtilizadores();
        this.eventos = d.getEventos();
        this.equipas = d.getEquipas();
        this.apostas = d.getApostas();
    }
    
    public HashMap<String,User> getUtilizadores(){
        return this.utilizadores;
    }
    public HashMap<Integer,Evento> getEventos(){
        return this.eventos;
    }
    public HashMap<Integer,Equipa> getEquipas(){
        return this.equipas;
    }
    public HashMap<Integer,Aposta> getApostas(){
        return this.apostas;
    }
    public HashMap<Integer,EventoFutebol> getEventosFutebol(){
        HashMap<Integer,EventoFutebol> efs = new HashMap<>();
        for(Evento e : this.getEventos().values()){
            EventoFutebol ef = (EventoFutebol) e;
            efs.put(ef.getID(), ef);
        }
        return efs;
    }
    
    public void addEvento(Evento e){
        EventoFutebol ef = (EventoFutebol) e;
        this.eventos.put(ef.getID(), e);
    }
    
    public void endEvento(Evento e, int res){
        EventoFutebol ef = (EventoFutebol) e;
        this.getEventosFutebol().get(ef.getID()).finalizar(res);
    }
    
    public void newApostador(Apostador a){
        this.utilizadores.put(a.getEmail(),a);
    }
    
    public void addEquipa(Equipa e){
        this.equipas.put(e.getID(),e);
    }
    
    public void removeEquipa(Equipa e){
        if(equipas.containsKey(e.getID()))
            equipas.get(e.getID()).setEstado(false);
    }
    
    public void addAposta(Aposta a) {
        Apostador ap = a.getApostador();
        ap.levantarESSCoins(a.getValor());
        this.apostas.put(a.getID(), a);
        a.getApostador().registarAposta(a);
        EventoFutebol ef = (EventoFutebol) a.getEvento();
        this.getEventosFutebol().get(ef.getID()).addUser(ap);
    }
    
    public void removeAposta(Aposta a){        
        Apostador ap = a.getApostador();
        ap.adicionarESSCoins(a.getValor());
        this.apostas.remove(a.getID());
        EventoFutebol ef = (EventoFutebol) a.getEvento();
        this.getEventosFutebol().get(ef.getID()).removeUser(ap);
    }
    
    public void save(){
        try {
            FileOutputStream fileOut = new FileOutputStream("betessData.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.flush();
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in betessData.obj\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public void povoar(){
        this.povoarEquipas();
        this.povoarAdmins();
        this.povoarBookies();
        this.povoarApostadores();
        this.povoarEventos();
        System.out.println("Dados inseridos!\n");
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
        Equipa famalicao    = new Equipa(18, "FC Famalicão", false, "resources/equipas/famalicao.png");
        Equipa pacos        = new Equipa(19, "FC Paços de Ferreira", false, "resources/equipas/pacos.png");
        
        equipas.put(0,belenenses);
        equipas.put(1,boavista);
        equipas.put(2,tondela);
        equipas.put(3,aves);
        equipas.put(4,feirense);
        equipas.put(5,nacional);
        equipas.put(6,maritimo);
        equipas.put(7,porto);
        equipas.put(8,chaves);
        equipas.put(9,moreirense);
        equipas.put(10,portimonense);
        equipas.put(11,rioave);
        equipas.put(12,santaclara);
        equipas.put(13,benfica);
        equipas.put(14,braga);
        equipas.put(15,sporting);
        equipas.put(16,setubal);
        equipas.put(17,guimaraes);
        //equipas.put(18,famalicao);
        //equipas.put(19,pacos);
    }  
    public void povoarAdmins(){
        Admin a = new Admin("admin", "admin", "Administrador");
        
        utilizadores.put(a.getEmail(), a);
    }
    public void povoarBookies(){
        Bookie a = new Bookie("adao@betess.com", "adao", "Adão");
        Bookie b = new Bookie("eva@betess.com", "eva", "Eva");
        
        utilizadores.put(a.getEmail(), a);
        utilizadores.put(b.getEmail(), b);
    }
    public void povoarApostadores(){
        Apostador a = new Apostador("joaonunes@gmail.com", "joaonunes", "João Nunes", 25.00);
        Apostador b = new Apostador("saramoreno@gmail.com", "saramoreno", "Sara Moreno", 5.00);
        Apostador c = new Apostador("pauloprazeres@gmail.com", "pauloprazeres", "Paulo Prazeres", 2.92);
        Apostador d = new Apostador("albanojeronimo@gmail.com", "albanojeronimo", "Albano Jerónimo", 89.20);
        Apostador e = new Apostador("nunolopes@gmail.com", "nunolopes", "Nuno Lopes", 102.36);
        Apostador f = new Apostador("marcomartins@gmail.com", "marcomartins", "Marco Martins", 19.76);
        Apostador g = new Apostador("miguelguilherme@gmail.com", "miguelguilherme", "Miguel Guilherme", 15.58);
        Apostador h = new Apostador("beatrizbatarda@gmail.com", "beatrizbatarda", "Beatriz Batarda", 5.01);
        
        this.utilizadores.put(a.getEmail(),a);
        this.utilizadores.put(b.getEmail(),b);
        this.utilizadores.put(c.getEmail(),c);
        this.utilizadores.put(d.getEmail(),d);
        this.utilizadores.put(e.getEmail(),e);
        this.utilizadores.put(f.getEmail(),f);
        this.utilizadores.put(g.getEmail(),g);
        this.utilizadores.put(h.getEmail(),h);
    }
    public void povoarEventos(){
        EventoFutebol j1j1 = new EventoFutebol(0, 1.11d, 3.94d, 8.71d, true, 0, equipas.get(7), equipas.get(9), null);
        EventoFutebol j1j2 = new EventoFutebol(1, 2.10, 2.81, 4.50, true, 0, equipas.get(12), equipas.get(16), null);
        EventoFutebol j1j3 = new EventoFutebol(2, 1.72, 3.81, 7.21, true, 0, equipas.get(17), equipas.get(10), null);
        EventoFutebol j1j4 = new EventoFutebol(3, 1.68, 3.54, 7.02, true, 0, equipas.get(0), equipas.get(1), null);
        EventoFutebol j1j5 = new EventoFutebol(9, 2.13, 2.81, 2.50, true, 0, equipas.get(14), equipas.get(13), null);
        EventoFutebol j1j6 = new EventoFutebol(5, 1.42, 3.36, 3.47, true, 0, equipas.get(8), equipas.get(6), null);
        EventoFutebol j1j7 = new EventoFutebol(6, 1.09, 3.81, 8.98, true, 0, equipas.get(15), equipas.get(5), null);
        EventoFutebol j1j8 = new EventoFutebol(7, 1.87, 2.98, 3.49, true, 0, equipas.get(3), equipas.get(2), null);
        EventoFutebol j1j9 = new EventoFutebol(8, 1.42, 3.25, 3.99, true, 0, equipas.get(11), equipas.get(4), null);
        
        this.eventos.put(j1j1.getID(),j1j1);
        this.eventos.put(j1j2.getID(),j1j2);
        this.eventos.put(j1j3.getID(),j1j3);
        this.eventos.put(j1j4.getID(),j1j4);
        this.eventos.put(j1j5.getID(),j1j5);
        this.eventos.put(j1j6.getID(),j1j6);
        this.eventos.put(j1j7.getID(),j1j7);
        this.eventos.put(j1j8.getID(),j1j8);
        this.eventos.put(j1j9.getID(),j1j9);

    }
    
    public static Data load() throws FileNotFoundException, IOException{
        Data d = new Data();
        try{
            FileInputStream fileIn = new FileInputStream("betessData.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            d = (Data) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Data loaded!\n");
        } catch (Exception e) {
            System.out.println("Dados preexistentes não encontrados. Gerando dados modelo...");
            d.povoar();
            d.save();
        }
        return d;
    }
}
