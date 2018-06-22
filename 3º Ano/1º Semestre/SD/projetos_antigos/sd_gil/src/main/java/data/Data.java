package data;
import java.io.*;

/**
 * Class that will be in charge of loading
 * and saving data to a binary file
 */
public class Data {

    /**
     * Saves the data from an object
     * @param o         Object to save
     * @param filename  Filename
     * @throws FileNotFoundException    File not found
     * @throws IOException              IO Error
     */
    public static void save(Object o, String filename) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.flush();
        oos.close();
    }

    /**
     * Loads the data from a binary file
     * @param filename Filename
     * @return Object
     * @throws FileNotFoundException    File not found
     * @throws IOException              IO Error
     * @throws ClassNotFoundException   Class not found
     */
    public static Object load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object o = ois.readObject();
        ois.close();
        return o;
    }
}