/**
 * Read datasets and load them to an hashmap.
 *
 * @author Vitor Peixoto
 * @author Francisco Oliveira
 * @author Raul Vilas Boas
 * @version May 13
 */

package engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.lang.NullPointerException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Load {

    private HashMap<Long,ArrayList<ArticleStructure>> artHash;
    private HashMap<Long,ArrayList<ContributorStructure>> contHash;
    private int artCount = 0;

    public HashMap<Long,ArrayList<ArticleStructure>> getArtHash(){
        return artHash;
    }
    public HashMap<Long,ArrayList<ContributorStructure>> getContHash(){
        return contHash;
    }
    public int getArtCount(){
        return artCount;
    }

    public long wordCount (String str){
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char s : str.toCharArray()) {
            chars.add(s);
        }
        int count, i;
        boolean x, flag=false;
        for (count=0, i=0; i<chars.size(); i++){
            x=(chars.get(i).equals(' ') || chars.get(i).equals('\n') || chars.get(i).equals('\t'));
            if (!x && !flag) {
                count++; 
                flag=true;
            }
            else if (x) flag=false;
        }
        return count;
    }



    public Load(int nsnaps, ArrayList<String> snaps_paths) {
        artHash = new HashMap<Long,ArrayList<ArticleStructure>>();
        contHash = new HashMap<Long,ArrayList<ContributorStructure>>();
        ArticleStructure art;
        ContributorStructure cont=new ContributorStructure();

        for(int i=0; i<nsnaps; i++){
            try{
                XMLInputFactory factory = XMLInputFactory.newInstance();
                factory.setProperty(XMLInputFactory.IS_COALESCING, true);
                XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(snaps_paths.get(i)));
                while (reader.hasNext()) {
                    int event = reader.next();
                    if (event==XMLStreamConstants.START_ELEMENT){
                        if (reader.getLocalName().equals("page")) {
                            artCount++;
                            art = buildArt(reader,event);
                            artHash = insertArt(artHash,art);

                            if(art.getContID() != 0){
                                cont = new ContributorStructure(art.getID(),
                                                            art.getRevID(),
                                                            art.getContributor(),
                                                            art.getContID());
                                contHash = insertCont(contHash,cont);

                            }
                        }
                    }
                }
            }
            catch (XMLStreamException e) {
                System.out.println("EXCEPTION: Unexpected processing error!");
                e.printStackTrace();
            }
            catch (FileNotFoundException e){
                System.out.println("EXCEPTION: File expected path does not exist!");
            }
            catch (NullPointerException e){
                System.out.println("EXCEPTION: Attempting to use Null when a object is required!");
            }
        }
    }

    public ArticleStructure buildArt(XMLStreamReader reader, int eventy) throws XMLStreamException{
        ArticleStructure art = new ArticleStructure();
        ContributorStructure cont = new ContributorStructure();

        boolean bTitle = false;
        boolean bID = false;
        boolean bRevID = false;
        boolean bTime = false;
        boolean bName = false;
        boolean bContID = false;
        boolean bTextSize = false;
        boolean bTextWords = false;

        boolean writeFlag = false;
        boolean revFlag = false;
        boolean contFlag = false;

        String title = "n/a";
        long id = 0;
        long revid = 0;
        String time = "n/a";
        String name = "n/a";
        long contid = 0;
        long size = 0;
        long words = 0;

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String qName = reader.getLocalName();

                    if (reader.getLocalName().equals("page")) {
                        artCount++;
                        revFlag = false;
                        contFlag = false;
                    }
                    else if(qName.equalsIgnoreCase("title")){
                        bTitle = true;
                    }
                    else if(qName.equalsIgnoreCase("id") && revFlag==false){
                        bID = true;
                    }
                    else if(reader.getLocalName().trim().equals("revision")){
                        revFlag = true;
                        contFlag = false;
                    }
                    else if(qName.equalsIgnoreCase("id") && revFlag==true && contFlag==false){
                    bRevID = true;
                    }
                    else if(qName.equalsIgnoreCase("timestamp")){
                        bTime = true;
                    }
                    else if(reader.getLocalName().trim().equals("contributor")){
                        contFlag = true;
                    }
                    else if(qName.equalsIgnoreCase("username") && revFlag==true && contFlag==true){
                    bName = true;
                    }
                    else if(qName.equalsIgnoreCase("id") && revFlag==true && contFlag==true){
                        bContID = true;
                    }
                    else if(qName.equalsIgnoreCase("text") ){
                        bTextSize = true;
                        bTextWords = true;
                        writeFlag = true;
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    if(bTitle){
                        title = reader.getText();
                        bTitle = false;
                    }
                    if(bID){
                        id = Long.valueOf(reader.getText()).longValue();
                        bID = false;
                    }
                    if(bRevID){
                        revid = Long.valueOf(reader.getText()).longValue();
                        bRevID = false;
                    }
                    if(bTime){
                        time = reader.getText();
                    bTime = false;
                    }
                    if(bName){
                        name = reader.getText();
                        bName = false;
                    }
                    if(bContID){
                        contid = Long.valueOf(reader.getText()).longValue();
                        bContID = false;
                    }
                    if(bTextSize){
                        size = reader.getText().getBytes().length;
                        bTextSize = false;
                    }
                    if(bTextWords){
                        words = wordCount(reader.getText());
                        bTextWords = false;
                    }
                    if(writeFlag){
                        art = new ArticleStructure(title,id,revid,time,name,contid,size,words);
                        return art;
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String qNames = reader.getLocalName();

                    if(qNames.equalsIgnoreCase("text") ){
                        writeFlag = false;
                    }
                    break;
            }
        }

        return art;
    }
    public HashMap<Long,ArrayList<ArticleStructure>> insertArt(HashMap<Long,ArrayList<ArticleStructure>> artHash, ArticleStructure artNew) throws NullPointerException {
        ArrayList<ArticleStructure> value = new ArrayList<ArticleStructure>();
        if(artHash.containsKey(artNew.getID())){
            value = artHash.get(artNew.getID());
            value.add(artNew);
            artHash.replace(artNew.getID(),value);
        }
        else{
            value.add(artNew);
            artHash.put(artNew.getID(),value);
        }
        return artHash;
    }

    public HashMap<Long,ArrayList<ContributorStructure>> insertCont(HashMap<Long,ArrayList<ContributorStructure>> contHash, ContributorStructure contNew) throws NullPointerException {
        ArrayList<ContributorStructure> value = new ArrayList<ContributorStructure>();
        if(contHash.containsKey(contNew.getContID())){
            value = contHash.get(contNew.getContID());
            value.add(contNew);
            contHash.replace(contNew.getContID(),value);
        }
        else{
            value.add(contNew);
            contHash.put(contNew.getContID(),value);
        }
        return contHash;
    }
}
