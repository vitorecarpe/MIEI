import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.*;

import java.io.*;
import java.util.Date;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Interface {

    private static Intel intel;
    private Menu menu;
    private int menuInt;
    private int op;
    
    /** Faz o login de um cliente */
    public int loginCliente(String email, String password) {
        for(int i=0;i<intel.getClientes().size();i++){
            Cliente cliente = intel.getClientes().get(i);
            if( cliente.getEmail().equals(email) && cliente.getPassword().equals(password) ){
                intel.setUser(cliente);
                System.out.println("\nLogin bem sucedido!\nBem vindo "+
                                    intel.getUser().getNome());
                return 0;
            }
        }
        return 1;
    }
    /** Faz o login de um motorista */
    public int loginMotorista(String email, String password) {
        for(int i=0;i<intel.getMotoristas().size();i++){
            Motorista motorista = intel.getMotoristas().get(i);
            if( motorista.getEmail().equals(email) && motorista.getPassword().equals(password) ){
                intel.setUserM(motorista);
                System.out.println("\nLogin bem sucedido!\nBem vindo "+
                                    intel.getUserM().getNome());
                return 0;
            }
        }
        return 1;
    }
    
    /** Faz a lista com os 10 clientes que mais gastam */
    public void clientesMaisGastam(){
        HashMap<String,Double> res = new HashMap<>();
        for(Cliente c: intel.getClientes()){
            res.put(c.getNome(),round(c.getGastos(),2));
        }
        
        System.out.println("\nCLIENTES QUE MAIS GASTAM:");
        //Ordena pelos que mais gastam e limita a 10.
        res.entrySet().stream()
                      .sorted(Map.Entry.<String,Double>comparingByValue().reversed()) 
                      .limit(10)
                      .forEach(System.out::println);
    }
    /** Faz a lista com os 5 motoristas com mais desvios no preço */
    public void motoristasMaisDesvios(){
        HashMap<String,Integer> res = new HashMap<>();
        for(Motorista m: intel.getMotoristas()){
            res.put(m.getNome(),m.getDesvios());
        }
        
        System.out.println("\nMOTORISTAS COM MAIS DESVIOS NO PREÇO:");
        //Ordena pelos que mais gastam e limita a 10.
        res.entrySet().stream()
                      .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()) 
                      .limit(5)
                      .forEach(System.out::println);
    }
    /** Adiciona um novo cliente ao ArrayList */
    public void addCliente() {
        Scanner is = new Scanner(System.in);
        String email,nome,password,morada,nascimento;
        int x, y;
        try{
            System.out.print("Email: ");      email = is.nextLine();
            System.out.print("Nome: ");       nome = is.nextLine();
            System.out.print("Password: ");   password = is.nextLine();
            System.out.print("Morada: ");     morada = is.nextLine();
            System.out.print("Nascimento (AAAA/MM/DD): "); nascimento = is.nextLine();
            DataValida data = new DataValida(nascimento);
            if( !data.isValid() ) throw new InputMismatchException();
            System.out.print("X: ");    x = is.nextInt();
            System.out.print("Y: ");    y = is.nextInt();
            Cliente c = new Cliente(email,nome,password,morada,data.convertToInt(),x,y,0);
            for(Cliente cliente : intel.getClientes()){
                if(cliente.equals(c)) {
                    System.out.println("Cliente ja existe!");
                    return;
                }
            }
            intel.getClientes().add(c);
            System.out.println("Cliente criado com sucesso!");
        }
        catch (InputMismatchException e) {
            System.out.println("Cliente nao criado! Erros na introduçao de dados");
            System.out.println(e);
        }
    }
    /** Adiciona um novo motorista ao ArrayList */
    public void addMotorista() {
        Scanner is = new Scanner(System.in);
        String email,nome,password,morada,nascimento;
        try{
            System.out.print("Email: ");      email = is.nextLine();
            System.out.print("Nome: ");       nome = is.nextLine();
            System.out.print("Password: ");   password = is.nextLine();
            System.out.print("Morada: ");     morada = is.nextLine();
            System.out.print("Nascimento (AAAA/MM/DD): "); nascimento = is.nextLine();
            DataValida data = new DataValida(nascimento);
            if( !data.isValid() ) throw new InputMismatchException();

            Motorista m = new Motorista(email,nome,password,morada,data.convertToInt(),0,0,0,true,0);
            for(Motorista motorista : intel.getMotoristas()){
                if(motorista.equals(m)) {
                    System.out.println("Motorista ja existe!");
                    return;
                }
            }
            intel.getMotoristas().add(m);
            System.out.println("Motorista criado com sucesso!");
        }
        catch (InputMismatchException e) {
            System.out.println("Cliente nao criado! Erros na introduçao de dados");
            System.out.println(e);
        }
    }
    /** Adiciona uma nova viatura ao ArrayList */
    public void addViatura() {
        Scanner is = new Scanner(System.in);
        String matricula,tipo;
        double preco;
        int x, y;
        try{
            System.out.print("Tipo de viatura \n (c - carro)\n (m - moto)\n (r - carrinha):\n"); tipo = is.nextLine();
            System.out.print("Matricula: ");          matricula = is.nextLine();
            MatriculaValida matri = new MatriculaValida(matricula);
            if( !matri.isValid() ) throw new InputMismatchException();
            System.out.print("Preço base por Km: ");  preco = is.nextDouble();
            System.out.print("X: ");            x = is.nextInt();
            System.out.print("Y: ");            y = is.nextInt();
            if(tipo.equalsIgnoreCase("c")){
                Carro c = new Carro(matricula,0,preco,100,null,x,y,null,0,0,0);
                for(Carro carro : intel.getCarros()){
                    if(carro.equals(c)) {
                        System.out.println("Carro ja existe!");
                        return;
                    }
                }
                intel.getCarros().add(c);
                System.out.println("Carro criado com sucesso!");
            }
            else if(tipo.equalsIgnoreCase("m")){
                Moto m = new Moto(matricula,0,preco,100,null,x,y,null,0,0,0);
                for(Moto moto : intel.getMotos()){
                    if(moto.equals(m)) {
                        System.out.println("Moto ja existe!");
                        return;
                    }
                }
                intel.getMotos().add(m);
                System.out.println("Moto criada com sucesso!");
            }
            else if(tipo.equalsIgnoreCase("r")){
                Carrinha r = new Carrinha(matricula,0,preco,100,null,x,y,null,0,0,0);
                for(Carrinha carrinha : intel.getCarrinhas()){
                    if(carrinha.equals(r)) {
                        System.out.println("Carrinha ja existe!");
                        return;
                    }
                }
                intel.getCarrinhas().add(r);
                System.out.println("Carrinha criada com sucesso!");
            }
            else System.out.println("Dados invalidos introduzidos");
            
        }
        catch (InputMismatchException e) {
            System.out.println("Viatura nao criada! Erros na introduçao de dados");
            System.out.println(e);
        }
    }
    
    /** Mostra a lista de viagens do User */
    public void mostraViagens(int start, int end) {
        ArrayList<Viagem> viagens;
        if( intel.getUser() != null ) viagens = intel.getUser().getViagens();
        else                          viagens = intel.getUserM().getViagens();
        for(Viagem viagem : viagens)  if( (start<viagem.getData())&&(viagem.getData()<end) ) System.out.println(viagem);
    }
    
    /** Devolve lista de carros disponiveis */
    public ArrayList<Carro> carrosDisponiveis(ArrayList<Carro> carros) {
        ArrayList<Carro> carrosDisp = new ArrayList<Carro>();
        for(Carro carro : carros) {
            if(carro.getMotorista().getDisponivel()) carrosDisp.add(carro);
        }
        return carrosDisp;
    }
    /** Devolve lista de motos disponiveis */
    public ArrayList<Moto> motosDisponiveis(ArrayList<Moto> motos) {
        ArrayList<Moto> motosDisp = new ArrayList<Moto>();
        for(Moto moto : motos) {
            if(moto.getMotorista().getDisponivel()) motosDisp.add(moto);
        }
        return motosDisp;
    }
    /** Devolve lista de carrinhas disponiveis */
    public ArrayList<Carrinha> carrinhasDisponiveis(ArrayList<Carrinha> carrinhas) {
        ArrayList<Carrinha> carrinhasDisp = new ArrayList<Carrinha>();
        for(Carrinha carrinha:carrinhas) {
            if(carrinha.getMotorista().getDisponivel()) carrinhasDisp.add(carrinha);
        }
        return carrinhasDisp;
    }
    /** Diz qual o carro mais proximo do cliente c */
    public Carro carroMaisProx(Cliente c, ArrayList<Carro> carros) {
        if(carros.isEmpty()) return null;
        else{
            Carro elCoche=carros.get(0);
            double distMin=c.getCoords().distance(carros.get(0).getCoords()), distTemp;
        
            for(Carro carro : carros){
                distTemp = c.getCoords().distance(carro.getCoords());
                if( distTemp < distMin ) {elCoche=carro;distMin=distTemp;}
            }
            return elCoche;
        }
    }
    /** Diz qual a moto mais proximo do cliente c */
    public Moto motoMaisProx(Cliente c, ArrayList<Moto> motos) {
        if(motos.isEmpty()) return null;
        else{
            Moto laMoto = motos.get(0);
            double distMin=c.getCoords().distance(motos.get(0).getCoords()), distTemp;
        
            for(Moto moto : motos){
                distTemp = c.getCoords().distance(moto.getCoords());
                if( distTemp < distMin ) {laMoto=moto;distMin=distTemp;}
            }
            return laMoto;
        }
    }
    /** Diz qual a carrinha mais proxima do cliente c */
    public Carrinha carrinhaMaisProx(Cliente c, ArrayList<Carrinha> carrinhas) {
        if(carrinhas.isEmpty()) return null;
        else{
            Carrinha laCarrinha = carrinhas.get(0);
            double distMin=c.getCoords().distance(carrinhas.get(0).getCoords()), distTemp;
        
            for(Carrinha carrinha : carrinhas){
                distTemp = c.getCoords().distance(carrinha.getCoords());
                if( distTemp < distMin ) {laCarrinha=carrinha;distMin=distTemp;}
            }
            return laCarrinha;
        }
    }
    /** Diz qual a viatura mais proxima do cliente c */
    public Viatura viaturaMaisProx(Cliente c, ArrayList<Carrinha> carrinhas, ArrayList<Carro> carros, ArrayList<Moto> motos) {
        if(carrinhas.isEmpty() || carros.isEmpty() || motos.isEmpty()) return null;
        else{
            Carrinha laCarrinha = carrinhaMaisProx(c,carrinhas);
            Moto laMoto = motoMaisProx(c,motos);
            Carro elCoche = carroMaisProx(c,carros);
            
            double distCarrinha = c.getCoords().distance(laCarrinha.getCoords());
            double distCarro = c.getCoords().distance(elCoche.getCoords());
            double distMoto = c.getCoords().distance(laMoto.getCoords());
            if(distCarrinha<=distCarro && distCarrinha<=distMoto) return laCarrinha;
            if(distCarro<=distCarrinha && distCarro<=distMoto) return elCoche;
            if(distMoto<=distCarro && distMoto<=distCarrinha) return laMoto;
            return null;
        }
    }
    
    /** Faz uma viagem com o Carro mais proximo para o destino pedido */
    public void viagemcomCarroMaisProx(Cliente c, ArrayList<Carro> carros, Par destino, int classif) {
        if(c==null) System.out.println("Tem de fazer login!");
        else {
            ArrayList<Carro> carrosDisp = carrosDisponiveis(carros);
            Carro carroProx = carroMaisProx(c,carrosDisp);
            double distancia = carroProx.getCoords().distance(intel.getUser().getCoords()) +
                                            destino.distance(this.intel.getUser().getCoords());
            //carroProx.setCliente(c);
            carroProx.setCoords(destino);
            intel.getUser().setCoords(destino);
            //fator aleatorio do tempo real da viagem
            Random r = new Random();
            int Low = 95; 
            int High = 122;
            //fator fiabilidade de acordo com a viatura
            double fatorFiabilidade = ((1/carroProx.getFiabilidade())*3)+1;
            //tempo e preço estimados e reais.
            double tempoEst = round(2.5*distancia,2);
            double precoEst = round((carroProx.getPreco())*distancia,2); 
            double tempoReal = tempoEst*fatorFiabilidade*(r.nextInt(High-Low) + Low)/100; 
            double precoReal = (precoEst*tempoReal)/tempoEst; 
            double preco;   
            //o motorista demorou muito tempo? se sim o cliente nao paga mais do que o preço estimado inicialmente
            if(tempoReal-tempoEst > 0.25*tempoEst){preco=precoEst;carroProx.diminuiFiabilidade();carroProx.getMotorista().setDesvios(carroProx.getMotorista().getDesvios()+1);}
            else {preco=precoReal;carroProx.aumentaFiabilidade();}
            carroProx.getMotorista().setNKMS(carroProx.getMotorista().getNKMS()+round(distancia,2));
            //aumenta o nº de gastos deste user.
            intel.getUser().setGastos(intel.getUser().getGastos()+round(preco,2));
            //acrescenta o preço na faturaçao da viatura.
            carroProx.setFaturacao(carroProx.getFaturacao()+round(preco,2));
            
            Viagem viagem = new Viagem(round(distancia, 2),round(tempoReal, 2),round(preco, 2),intel.getData(),intel.getUser().getNome(),carroProx.getMotorista().getNome(), classif);
            carroProx.getMotorista().addViagem(viagem);
            intel.getUser().addViagem(viagem);
        }
    }
    /** Faz uma viagem com Moto mais proxima para o destino pedido */
    public void viagemcomMotoMaisProx(Cliente c, ArrayList<Moto> motos, Par destino, int classif) {
        if(c==null) System.out.println("Tem de fazer login!");
        else {
            ArrayList<Moto> motosDisp = motosDisponiveis(motos);
            Moto motoProx = motoMaisProx(c,motosDisp);
            double distancia = motoProx.getCoords().distance(intel.getUser().getCoords()) +
                                            destino.distance(this.intel.getUser().getCoords());
            //motoProx.setCliente(c);
            motoProx.setCoords(destino);
            intel.getUser().setCoords(destino);
            //fator aleatorio do tempo real da viagem
            Random r = new Random();
            int Low = 95; 
            int High = 120;
            //fator fiabilidade de acordo com a viatura
            double fatorFiabilidade = ((1/motoProx.getFiabilidade())*3)+1;
            //tempo e preço estimados e reais.
            double tempoEst = round(2.5*distancia,2);
            double precoEst = round((motoProx.getPreco())*distancia,2); 
            double tempoReal = tempoEst*fatorFiabilidade*(r.nextInt(High-Low) + Low)/100; 
            double precoReal = (precoEst*tempoReal)/tempoEst; 
            double preco;   
            //o motorista demorou muito tempo? se sim o cliente nao paga mais do que o preço estimado inicialmente
            if(tempoReal-tempoEst > 0.25*tempoEst){preco=precoEst; motoProx.diminuiFiabilidade();motoProx.getMotorista().setDesvios(motoProx.getMotorista().getDesvios()+1);}
            else {preco=precoReal; motoProx.aumentaFiabilidade();}
            motoProx.getMotorista().setNKMS(motoProx.getMotorista().getNKMS()+round(distancia,2));
            //aumenta o nº de gastos deste user.
            intel.getUser().setGastos(intel.getUser().getGastos()+round(preco,2));   
            //acrescenta o preço na faturaçao da viatura.
            motoProx.setFaturacao(motoProx.getFaturacao()+round(preco,2));
            
            Viagem viagem = new Viagem(round(distancia, 2),round(tempoReal, 2),round(preco, 2),intel.getData(),intel.getUser().getNome(),motoProx.getMotorista().getNome(),classif);
            motoProx.getMotorista().addViagem(viagem);
            intel.getUser().addViagem(viagem);
        }
    }
    /** Faz uma viagem com a Carrinha mais proxima para o destino pedido */
    public void viagemcomCarrinhaMaisProx(Cliente c, ArrayList<Carrinha> carrinhas, Par destino, int classif) {
        if(c==null) System.out.println("Tem de fazer login!");
        else {
            ArrayList<Carrinha> carrinhasDisp = carrinhasDisponiveis(carrinhas);
            Carrinha carrinhaProx = carrinhaMaisProx(c,carrinhasDisp);
            double distancia = carrinhaProx.getCoords().distance(intel.getUser().getCoords()) +
                                            destino.distance(this.intel.getUser().getCoords());
            //carrinhaProx.setCliente(c);
            carrinhaProx.setCoords(destino);
            intel.getUser().setCoords(destino);
            //fator aleatorio do tempo real da viagem
            Random r = new Random();
            int Low = 95; 
            int High = 120;
            //fator fiabilidade de acordo com a viatura
            double fatorFiabilidade = ((1/carrinhaProx.getFiabilidade())*3)+1;
            //tempo e preço estimados e reais.
            double tempoEst = round(2.5*distancia,2);
            double precoEst = round((carrinhaProx.getPreco())*distancia,2); 
            double tempoReal = tempoEst*fatorFiabilidade*(r.nextInt(High-Low) + Low)/100; 
            double precoReal = (precoEst*tempoReal)/tempoEst; 
            double preco;   
            //o motorista demorou muito tempo? se sim o cliente nao paga mais do que o preço estimado inicialmente
            if(tempoReal-tempoEst > 0.25*tempoEst){preco=precoEst; carrinhaProx.diminuiFiabilidade();carrinhaProx.getMotorista().setDesvios(carrinhaProx.getMotorista().getDesvios()+1);}
            else {preco=precoReal; carrinhaProx.aumentaFiabilidade();}
            carrinhaProx.getMotorista().setNKMS(carrinhaProx.getMotorista().getNKMS()+round(distancia,2));
            //aumenta o nº de gastos deste user.
            intel.getUser().setGastos(intel.getUser().getGastos()+round(preco,2)); 
            //acrescenta o preço na faturaçao da viatura.
            carrinhaProx.setFaturacao(carrinhaProx.getFaturacao()+round(preco,2));
            
            Viagem viagem = new Viagem(round(distancia, 2),round(tempoReal, 2),round(preco, 2),intel.getData(),intel.getUser().getNome(),carrinhaProx.getMotorista().getNome(),classif);
            carrinhaProx.getMotorista().addViagem(viagem);
            intel.getUser().addViagem(viagem);
        }
    }
    /** Faz uma viagem com qualquer Viatura mais proxima para o destino pedido */
    public void viagemcomViaturaMaisProx(Cliente c, ArrayList<Carro> carros, ArrayList<Moto> motos, ArrayList<Carrinha> carrinhas, Par destino, int classif) {
        if(c==null) System.out.println("Tem de fazer login!");
        else {
            ArrayList<Carro> carrosDisp = carrosDisponiveis(carros);
            ArrayList<Moto> motosDisp = motosDisponiveis(motos);
            ArrayList<Carrinha> carrinhasDisp = carrinhasDisponiveis(carrinhas);
            Viatura viatura = viaturaMaisProx(c, carrinhasDisp, carrosDisp, motosDisp);
            //viagem(c,viatura,destino);
            double distancia = viatura.getCoords().distance(intel.getUser().getCoords()) +
                                           destino.distance(this.intel.getUser().getCoords());
            //carrinhaProx.setCliente(c);
            viatura.setCoords(destino);
            intel.getUser().setCoords(destino);
            //fator aleatorio do tempo real da viagem
            Random r = new Random();
            int Low = 95; 
            int High = 118;
            //fator fiabilidade de acordo com a viatura
            double fatorFiabilidade = ((1/viatura.getFiabilidade())*3)+1;
            //tempo e preço estimados e reais.
            double tempoEst = round(2.5*distancia,2);
            double precoEst = round((viatura.getPreco())*distancia,2); 
            double tempoReal = tempoEst*fatorFiabilidade*(r.nextInt(High-Low) + Low)/100; 
            double precoReal = (precoEst*tempoReal)/tempoEst; 
            double preco;   
            //o motorista demorou muito tempo? se sim o cliente nao paga mais do que o preço estimado inicialmente
            if(tempoReal-tempoEst > 0.25*tempoEst){preco=precoEst; viatura.diminuiFiabilidade();viatura.getMotorista().setDesvios(viatura.getMotorista().getDesvios()+1);}
            else {preco=precoReal; viatura.aumentaFiabilidade();}
            viatura.getMotorista().setNKMS(viatura.getMotorista().getNKMS()+round(distancia,2));
            //aumenta o nº de gastos deste user.
            intel.getUser().setGastos(intel.getUser().getGastos()+round(preco,2));
            //acrescenta o preço na faturaçao da viatura.
            viatura.setFaturacao(viatura.getFaturacao()+round(preco,2));
      
            Viagem viagem = new Viagem(round(distancia, 2),round(tempoReal, 2),round(preco, 2),intel.getData(),intel.getUser().getNome(),viatura.getMotorista().getNome(),classif);
            viatura.getMotorista().addViagem(viagem);
            intel.getUser().addViagem(viagem);
        }
    }
    /** Faz uma viagem com uma viatura pedida (atraves da matricula) */
    public void viagem(Cliente c, Viatura v, Par destino, int classif){
        double distancia = v.getCoords().distance(c.getCoords()) +
                                 destino.distance(c.getCoords());
        //carrinhaProx.setCliente(c);
        v.setCoords(destino);
        c.setCoords(destino);
        //fator aleatorio do tempo real da viagem
        Random r = new Random();
        int Low = 95; 
        int High = 120;
        //fator fiabilidade de acordo com a viatura
        double fatorFiabilidade = ((1/v.getFiabilidade())*3)+1;
        //tempo e preço estimados e reais.
        double tempoEst = round(2.5*distancia,2);
        double precoEst = round((v.getPreco())*distancia,2); 
        double tempoReal = tempoEst*fatorFiabilidade*(r.nextInt(High-Low) + Low)/100; 
        double precoReal = (precoEst*tempoReal)/tempoEst; 
        double preco;
        //o motorista demorou muito tempo? 
        //se sim o cliente nao paga mais do que o preço estimado inicialmente e a viatura perde fiabilidade.
        //se nao o cliente paga o valor dependendo do tempo e a viatura ganha fiabilidade.
        if(tempoReal-tempoEst > 0.25*tempoEst){preco=precoEst; v.diminuiFiabilidade();v.getMotorista().setDesvios(v.getMotorista().getDesvios()+1);}
        else {preco=precoReal; v.aumentaFiabilidade();}
        v.getMotorista().setNKMS(v.getMotorista().getNKMS()+round(distancia,2));
        //aumenta o nº de gastos deste user.
        c.setGastos(c.getGastos()+round(preco,2));
        //acrescenta o preço na faturaçao da viatura.
        v.setFaturacao(v.getFaturacao()+round(preco,2));
        
        Viagem viagem = new Viagem(round(distancia, 2),round(tempoReal, 2),round(preco, 2),intel.getData(),c.getNome(),v.getMotorista().getNome(), classif);
        v.getMotorista().addViagem(viagem);
        c.addViagem(viagem);
    }
    
    /** Funçao que arrendonda um double */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
   
    
    /** Main */
    public static void main(String[] args) {
        intel = new Intel();
        try{intel = intel.load("umer.obj");
            System.out.println("\nLoad bem sucedido");
            new Interface(intel).run();
        }
        catch(IOException e)           {System.out.println("\nErro a aceder a ficheiro!");
                                        new Interface().run();}
        catch(ClassNotFoundException e){System.out.println("\nOOPS!");} 
        catch(NullPointerException e)  {System.out.println("Load falhou por falta de ficheiro"); 
                                        new Interface().run();}     
        try{intel.save("umer.obj");}
        catch(IOException e)           {System.out.println("\nErro a aceder a ficheiro!");}
    }
    
    /** Trata de criar alguns elementos base e inicializacao */
    public Interface() {             
        intel = new Intel();
        menuInt = 1;
    }
    /** Trata de criar alguns elementos base e inicializacao */
    public Interface(Intel intel) {             
        intel = new Intel(intel);
        menuInt = 1;
    }
    /** Executa os menus principal e invoca o método correspondente à opção seleccionada */
    public void run() {
        do {
            switch (menuInt) {
                case 1: menuPrincipal();
                        break;
                case 2: menuCliente();
                        break;
                case 3: menuMotorista();
                        break;
                case 4: menuDebug();
                        break;
            }
        } while ( menuInt != 0 );
        System.out.println("\nAté breve!");
    }
    
    /** Executa o menu principal */
    private int menuPrincipal() {
        String[] opcoesMenuPrincipal = {"Login Cliente",
                                        "Login Motorista",
                                        "Registar Cliente",
                                        "Registar Motorista",
                                        "Adicionar Viatura",
                                        "Clientes que mais gastam",
                                        "Motoristas com mais desvios no preço"};
        menu = new Menu(opcoesMenuPrincipal);
        Scanner scan = new Scanner(System.in);
        String email,password;
        
        menu.executa();
        switch (menu.getOpcao()) {
            case -1:op=-1;
                    break;
            case 0: menuInt = 0;
                    break;
            case 1: System.out.print("Email: ");    email=scan.next();
                    System.out.print("Password: "); password=scan.next();
                    if( 0 == loginCliente(email,password) ) menuInt = 2;
                    else System.out.println("Cliente nao existe!");
                    break;
            case 2: System.out.print("Email: ");    email=scan.next();
                    System.out.print("Password: "); password=scan.next();
                    if( 0 == loginMotorista(email,password) ) menuInt = 3;
                    else System.out.println("Motorista nao existe!");
                    break;
            case 3: addCliente();
                    break;
            case 4: addMotorista();
                    break;
            case 5: addViatura();
                    break;
            case 6: clientesMaisGastam();
                    break;
            case 7: motoristasMaisDesvios();
                    break;
            case 8: menuInt = 4; //MENU DE DEBUG
                    break;
            default:System.out.println("uhm not good");
        }
        
        scan.close();
        return menu.getOpcao();
    }
    /** Executa o menu de cliente */
    private int menuCliente() {
        String[] opcoesMenuCliente   = {"Sobre mim",
                                        "Visualizar relatorio de viagens",
                                        "Pedir transporte ao carro mais proximo",
                                        "Pedir transporte a moto mais proxima",
                                        "Pedir transporte a carrinha mais proxima",
                                        "Pedir transporte a viatura mais proxima",
                                        "Pedir transporte a viatura especifica",

                                        "Logout"};
        menu = new Menu(opcoesMenuCliente);
        Scanner scan = new Scanner(System.in);
        int x,y,classif;
        
        menu.executa();
        switch (menu.getOpcao()) {
            case -1:op=-1;
                    break;
            case 0: menuInt = 0;
                    break;
            case 1: System.out.println("Email: "+intel.getUser().getEmail());
                    System.out.println("Nome: " +intel.getUser().getNome());
                    System.out.println("Morada: "+intel.getUser().getMorada());
                    System.out.println("Nascimento: "+intel.getUser().getNascimento());
                    System.out.println(intel.getUser().getCoords().toString());
                    System.out.println("Gastos: "+round(intel.getUser().getGastos(),2));
                    break;
            case 2: System.out.print("Data inicial (AAAA/MM/DD): ");
                    String start = scan.next();
                    System.out.print("Data final (AAAA/MM/DD): ");
                    String end = scan.next();
                    DataValida startt = new DataValida(start);
                    DataValida endd = new DataValida(end);
                    if ( startt.isValid() && endd.isValid() ) {
                        mostraViagens( startt.convertToInt(), endd.convertToInt() );
                    }
                    break;
            case 3: System.out.print("Insira as coordenadas para onde pretende ir\nX: "); x=scan.nextInt();
                    System.out.print("Y: "); y=scan.nextInt();
                    Par destinoCar = new Par(x,y);
                    System.out.print("Classificaçao do Motorista (0-5):"); classif=scan.nextInt();
                    try{ viagemcomCarroMaisProx(intel.getUser(), intel.getCarros(), destinoCar, classif); }
                    catch(NullPointerException e) {System.out.println("Nenhum veiculo disponivel");}
                    break;
            case 4: System.out.print("Insira as coordenadas para onde pretende ir\nX: "); x=scan.nextInt();
                    System.out.print("Y: "); y=scan.nextInt();
                    Par destinoMoto = new Par(x,y);
                    System.out.print("Classificaçao do Motorista (0-5):"); classif=scan.nextInt();
                    try{ viagemcomMotoMaisProx(intel.getUser(), intel.getMotos(), destinoMoto, classif); }
                    catch(NullPointerException e) {System.out.println("Nenhum veiculo disponivel");}
                    break;
            case 5: System.out.print("Insira as coordenadas para onde pretende ir\nX: "); x=scan.nextInt();
                    System.out.print("Y: "); y=scan.nextInt();
                    Par destinoCarrinha = new Par(x,y);
                    System.out.print("Classificaçao do Motorista (0-5):"); classif=scan.nextInt();
                    try{ viagemcomCarrinhaMaisProx(intel.getUser(), intel.getCarrinhas(), destinoCarrinha, classif); }
                    catch(NullPointerException e) {System.out.println("Nenhum veiculo disponivel");}
                    break;
            case 6: System.out.print("Insira as coordenadas para onde pretende ir\nX: "); x=scan.nextInt();
                    System.out.print("Y: "); y=scan.nextInt();
                    Par destino = new Par(x,y);
                    System.out.print("Classificaçao do Motorista (0-5):"); classif=scan.nextInt();
                    try{ viagemcomViaturaMaisProx(intel.getUser(), intel.getCarros(), intel.getMotos(), intel.getCarrinhas(), destino, classif); }
                    catch(NullPointerException e) {System.out.println("Nenhum veiculo disponivel");}
                    break;
            case 7: System.out.print("Matricula do veiculo: "); String mat=scan.next();
                    MatriculaValida matri = new MatriculaValida(mat);
                    if( matri.isValid() ) {
                        Viatura v=null;
                        for(Carro c : intel.getCarros() ) {
                            if( c.getMatricula().equalsIgnoreCase(mat) ) v=c;
                        }
                        for(Moto m : intel.getMotos() ) {
                            if( m.getMatricula().equalsIgnoreCase(mat) ) v=m;
                        }
                        for(Carrinha ca : intel.getCarrinhas() ) {
                            if( ca.getMatricula().equalsIgnoreCase(mat) ) v=ca;
                        }
                        System.out.print("Insira as coordenadas para onde pretende ir\nX: "); x=scan.nextInt();
                        System.out.print("Y: "); y=scan.nextInt();
                        Par destino1 = new Par(x,y);
                        System.out.print("Classificaçao do Motorista (0-5):"); classif=scan.nextInt();
                        if( v!=null ) viagem(intel.getUser(),v,destino1,classif);
                        else System.out.println("Veiculo nao existe");
                    }
                    break;
            case 8: menuInt = 1;
                    intel.setUser(null);
                    break;
            default:System.out.println("uhm not good");
        }
        
        scan.close();
        return menu.getOpcao();
    }
    /** Executa o menu de motorista */
    private int menuMotorista() {
        String[] opcoesMenuMotorista = {"Sobre mim",
                                        "Visualizar relatorio de viagens",
                                        "Alterar Disponibilidade",
                                        "Mudar de viatura",
                                        "Total faturado pela minha viatura",
                                        "Logout"};
        menu = new Menu(opcoesMenuMotorista);
        Scanner scan = new Scanner(System.in);
        
        menu.executa();
        switch (menu.getOpcao()) {
            case -1:op=-1;
                    break;
            case 0: menuInt = 0;
                    break;
            case 1: System.out.println("Email: "+intel.getUserM().getEmail());
                    System.out.println("Nome: " +intel.getUserM().getNome());
                    System.out.println("Morada: "+intel.getUserM().getMorada());
                    System.out.println("Nascimento: "+intel.getUserM().getNascimento());
                    System.out.println("Nº kms: "+round(intel.getUserM().getNKMS(),2));
                    System.out.println("Desvios: "+intel.getUserM().getDesvios());
                    //System.out.println("Viatura: "+matriculaDoMotorista(intel.getUserM()));
                    //System.out.println(""+posicaoDoMotorista(intel.getUserM()));
                    break;
            case 2: System.out.print("Data inicial: ");
                    String start = scan.next();
                    System.out.print("Data final: ");
                    String end = scan.next();
                    DataValida startt = new DataValida(start);
                    DataValida endd = new DataValida(end);
                    if ( startt.isValid() && endd.isValid() ) {
                        mostraViagens( startt.convertToInt(), endd.convertToInt() );
                    }
                    break;
            case 3: if( intel.getUserM().getDisponivel() == true )
                        {intel.getUserM().setDisponivel(false); System.out.print("Esta agora indisponivel!");}
                    else {intel.getUserM().setDisponivel(true); System.out.print("Esta agora disponivel!");}
                    
                    break;
            case 4: System.out.print("Matricula do novo veiculo: "); String mat=scan.next();
                    MatriculaValida matri = new MatriculaValida(mat);
                    if( matri.isValid() ) {
                        for(Carro c : intel.getCarros() ) {
                            if( c.getMatricula().equalsIgnoreCase(mat) ){
                                if( c.getMotorista()==null ) c.setMotorista(intel.getUserM());
                                else System.out.println("Carro ja em utilizacao");
                                break;
                            }
                        }
                        for(Moto m : intel.getMotos() ) {
                            if( m.getMatricula().equalsIgnoreCase(mat) ){
                                if( m.getMotorista()==null ) m.setMotorista(intel.getUserM());
                                else System.out.println("Moto ja em utilizacao");
                                break;
                            }
                        }
                        for(Carrinha ca : intel.getCarrinhas() ) {
                            if( ca.getMatricula().equalsIgnoreCase(mat) ){
                                if( ca.getMotorista()==null ) ca.setMotorista(intel.getUserM());
                                else System.out.println("Carrinha ja em utilizacao");
                                break;
                            }
                        }
                    }
                    break;
            case 5: for(Carro c : intel.getCarros()){
                         if(c.getMotorista().equals(intel.getUserM())){
                             System.out.print("Viatura em uso: "+c.getMatricula()+"\n");
                             System.out.print("Total faturado pela viatura: "+round(c.getFaturacao(),2)+"€\n");}
                    }
                    for(Moto m : intel.getMotos()){
                         if(m.getMotorista().equals(intel.getUserM())){
                             System.out.print("Viatura em uso: "+m.getMatricula()+"\n");
                             System.out.print("Total faturado pela viatura: "+round(m.getFaturacao(),2)+"€\n");}
                    }
                    for(Carrinha ca : intel.getCarrinhas()){
                         if(ca.getMotorista().equals(intel.getUserM())){
                             System.out.print("Viatura em uso: "+ca.getMatricula()+"\n");
                             System.out.print("Total faturado pela viatura: "+round(ca.getFaturacao(),2)+"€\n");}
                    }
                    break;
            case 6: menuInt = 1;
                    intel.setUserM(null);
                    break;
            default:System.out.println("uhm not good");
        }
        
        scan.close();
        return menu.getOpcao();
    }
    /** Executa o menu debug */
    private int menuDebug() {
        String[] opcoesMenuDebug = {"DEBUG-cliente na posiçao?",
                                    "DEBUG-alterar data",
                                    "Leave Debug"};
        menu = new Menu(opcoesMenuDebug);
        Scanner scan = new Scanner(System.in);
        String email,password;
        
        menu.executa();
        switch (menu.getOpcao()) {
            case -1:op=-1;
                    break;
            case 0: menuInt = 0;
                    break;
            case 1: System.out.print("posicao do cliente? ");
                    int n = scan.nextInt();
                    if ( n>=0 && n<intel.getClientes().size() ) 
                        System.out.println(intel.getClientes().get(n).toString());
                    else System.out.println("Posiçao invalida!!!");
                    break;
            case 2: System.out.print("nova data? ");
                    String s = scan.next();
                    DataValida dv = new DataValida(s);
                    if ( dv.isValid() ) intel.setData(dv.convertToInt());
                    break;
            case 3: menuInt = 1;
                    break;
            default:System.out.println("uhm not good");
        }
        
        scan.close();
        return menu.getOpcao();
    }
}
