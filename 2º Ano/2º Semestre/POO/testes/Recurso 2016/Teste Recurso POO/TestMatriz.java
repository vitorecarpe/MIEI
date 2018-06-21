import java.io.IOException;
/**
 * Write a description of class TestMatriz here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestMatriz
{
    public static void main(String args[]) throws IOException{
        try{
            Matriz m = new Matriz(4,4);
            for(int i = 0;i<4;i++)
                for(int j = 0;j<4;j++){
                    m.add(i,j,i+j);
                }
        
            Matriz c = m;   
        
            System.out.println(m.toString());
        
            System.out.println("Size: "+m.size()+"\n");
        
            System.out.println("Count: "+m.count(4)+"\n");
        
            System.out.println("Equals: "+m.equals(c)+"\n");
        
            m.save("save.txt");
            
            Matriz tri = new Matriz(4);
            
            for(int i = 0;i<4;i++)
                for(int j = 0;j<=i;j++){
                    tri.add(i,j,i+j);
                }
            
            System.out.println("M é triangular: "+m.tri()+"\n");
            
            System.out.println("Tri é triangular: "+tri.tri()+"\n");
        }
        catch(IOException e){
            throw new IOException(e.toString());
        }
    }
}
