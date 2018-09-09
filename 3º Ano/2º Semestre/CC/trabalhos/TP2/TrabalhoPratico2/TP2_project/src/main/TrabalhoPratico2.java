package main;
import agenteUDP.AgenteUDP;
import reverseProxy.ReverseProxy;

import java.net.UnknownHostException;
import java.util.Scanner;

// Main capaz de iniciar um AgenteUDP ou ReverseProxy
public class TrabalhoPratico2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1 - AgenteUDP\n2 - ReverseProxy\n0 - Sair");
        int in = input.nextInt();
        try{
            switch(in){
                case 1:
                    AgenteUDP agente = new AgenteUDP();
                    agente.run();
                    break;
                case 2:
                    ReverseProxy reverse = new ReverseProxy();
                    reverse.run();
                    break;
                default:
                    System.exit(0);
                    break;
            }
        } catch (UnknownHostException e) {
            System.out.println("<MAIN> Host Address indisponivel");
            System.out.println(e);
        }
    }
}
