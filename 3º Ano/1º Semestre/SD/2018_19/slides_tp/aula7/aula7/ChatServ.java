import java.io.*;
import java.net.*;
import java.util.*;



//cada vez que ele escreve no log está a notificar toda a gente.
class ChatLog
{
   Vector<String> log = new Vector<String>(); // We do sync but Vector is thread safe

  public synchronized void add(String s)
  {
    log.add(s);
    //System.out.println("get "+log.elementAt(log.size()-1) );

    notifyAll();
  }



  //está a ler do log e a escrever no socket; enquanto o tamanho do log for maior que o numero de coisas
  //escritas no socket escreve continuamente nele.Esta função é chamada pelo TreatClientWrite,
  public void writeloop(PrintWriter pw) 
  { 
    int i=0; 
    String s;
    try 
    { 
      while (true) 
      {
        synchronized (this) 
        { 
          while (i >= log.size()) wait(); 
          s=log.elementAt(i);
        }

        pw.println(s); //out.println(s);
        i++; 
      }	
    } catch (InterruptedException e) {} 
//    System.out.println("Write Loop Ended");
  } 
}



//aqui está ler do socket e e a escrever no log;
class TreatClientRead implements Runnable
{
  Socket cs;
  ChatLog l;
    
  TreatClientRead (Socket cs, ChatLog l) 
  {
    this.cs = cs; this.l=l;
  }
    
  public void run() 
  {
        
    try 
    {
            
      BufferedReader in = new BufferedReader( new InputStreamReader( cs.getInputStream() ) );
	        
      String current;
	        
      System.out.println("New Connection Received");
      while ((current = in.readLine()) != null)
      {
        l.add(current);
        System.out.println("echo: " + current);
      }

      in.close();
      cs.close();
      System.out.println("Connection Closed");
	        
    } catch ( IOException e) { e.printStackTrace(); }
        
  }
}


//aqui está a a ler do log  e a escrever no socket (tal é feito no writeloop)
class TreatClientWrite implements Runnable
{
  Socket cs;
  ChatLog l;
    
  TreatClientWrite (Socket cs, ChatLog l) 
  {
    this.cs = cs; this.l=l;
  }
    
  public void run() 
  {
    try 
    {
      PrintWriter out = new PrintWriter( cs.getOutputStream(), true );
	        
      l.writeloop(out);
	        
    } catch ( IOException e) { e.printStackTrace(); }
  }
}


public class ChatServ
{
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(9999);
        Socket cs;

       ChatLog l = new ChatLog();

        
        while (true)
        {
            cs = ss.accept();
                                    
            Thread tr = new Thread( new TreatClientRead(cs,l) );
            Thread tw = new Thread( new TreatClientWrite(cs,l) );
            tr.start();
            tw.start();
        }

        //ss.close();
    }
}
