
/**
 * Write a description of class ParqueComRecusados here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParqueComRecusados extends Parque implements IParque
{
    public String interfaces() {
        StringBuilder sb = new StringBuilder();
        Class[] list = this.getClass().getInterfaces();
        for(Class c : list)
            sb.append(c.getName()).append("\n");
        return sb.toString();
        }
}
