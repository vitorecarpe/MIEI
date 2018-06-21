package engine;

import li3.Interface;

import engine.Load;
import engine.ArticleStructure;
import engine.ContributorStructure;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.*;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;

public class QueryEngineImpl implements Interface {

    private HashMap<Long,ArrayList<ArticleStructure>> artHash;
    private HashMap<Long,ArrayList<ContributorStructure>> contHash;
    private Load loaded;

    public void init() {
        artHash = new HashMap<Long,ArrayList<ArticleStructure>>();
        contHash = new HashMap<Long,ArrayList<ContributorStructure>>();
    }

    public void load(int nsnaps, ArrayList<String> snaps_paths) {
        loaded = new Load(nsnaps,snaps_paths);
        artHash = loaded.getArtHash();
        contHash = loaded.getContHash();
    }

    //Q1
    public long all_articles() {
        //recebe o número de artigos do Load.java
        long allArt = (long) loaded.getArtCount();
        return allArt;
    }

    //Q2
    public long unique_articles() {
        //tamanho da hash será o nº artigos sem repetições
    	long unArt = artHash.size();
        return unArt;
    }

    //Q3
    public long all_revisions() {
    	long count = 0;

    	Set<Long> keys = artHash.keySet();
    	for(Long key : keys){
            //Array com ID's de revisão únicos para este artigo.
    		ArrayList<ArticleStructure> values = artHash.get(key);
    		ArrayList<Long> revIDs = new ArrayList<Long>();
    		for(ArticleStructure value : values){
    			if(!revIDs.contains(value.getRevID())){
    				revIDs.add(value.getRevID());
    			}
    		}
            //Tamanho é o número de revisões diferentes do artigo.
    		count += revIDs.size();
    	}
	    return count;
    }

    //Q4
    public ArrayList<Long> top_10_contributors() {
        //Hashmap que contem <K = ID do Contribuidor, V = Nº de contribuições>
        HashMap<Long,Long> contNcont = new HashMap<Long,Long>();
        long nCont=0;

        Set<Long> keys = contHash.keySet();
        for(Long key : keys){
            //Set de ID's de revisão únicos por contribuidor
            HashSet<Long> unRev = new HashSet<Long>();
            ArrayList<ContributorStructure> values = contHash.get(key);
            for(ContributorStructure value : values){
                unRev.add(value.getRevID());
            }
            //Conta nº de revisões únicas por contribuidor.
            nCont = unRev.size();
            contNcont.put(key,nCont);
        }

        //Ordena por numero de contribuições e limita a 10.
        HashMap<Long,Long> cont = contNcont.entrySet()
            .stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed()) 
            .limit(10)
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e2,
                LinkedHashMap::new));
        //Passa as keys da hash para um array.
        ArrayList<Long> ids = new ArrayList<Long>(cont.keySet());

        return ids;
    }

    //Q5
    public String contributor_name(long contributor_id) {
        return contHash.get(contributor_id).get(0).getContributor();
    }

    //Q6
    public ArrayList<Long> top_20_largest_articles() {
        //Hashmap que contem <K = ID do artigo, V = Tamanho do artigo>
        HashMap<Long,Long> artNsize = new HashMap<Long,Long>();
        
        //Ocupa o hashmap com os ID's e tamanhos dos artigos.
        Set<Long> keys = artHash.keySet();
        for(Long key : keys){
            ArrayList<ArticleStructure> values = artHash.get(key);
            for(ArticleStructure value : values){
                //Dependendo da revisão, insere a revisão do mesmo artigo que tem maior tamanho.
                if(!(artNsize.containsKey(value.getID())) || value.getSize() > artNsize.get(value.getID())) {
                    artNsize.put(value.getID(),value.getSize());
                }
            }
        }

        //Ordena pelo tamanho do artigo e limitar a 20.
        HashMap<Long,Long> size = artNsize.entrySet()
            .stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed()) 
            .limit(20)
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e2,
                LinkedHashMap::new));
        //Passa as keys da hash para um array.
        ArrayList<Long> ids = new ArrayList<Long>(size.keySet());

        return ids;

    }

    //Q7
    public String article_title(long article_id) {
        //Devolve o título mais recente do ID do artigo.
    	int size = artHash.get(article_id).size();
    	return artHash.get(article_id).get(size-1).getTitle();
    }

    //Q8
    public ArrayList<Long> top_N_articles_with_more_words(int n) {
        //Hashmap que contem <K = ID do artigo, V = Palavras do artigo>
    	HashMap<Long,Long> artNwords = new HashMap<Long,Long>();
    	Set<Long> keys = artHash.keySet();

        //Ocupa o hashmap com os ID's e nº de palavras dos artigos.
    	for(Long key : keys){
    		ArrayList<ArticleStructure> values = artHash.get(key);
    		for(ArticleStructure value : values){
                //Dependendo da revisão, insere a revisão do mesmo artigo que tem mais palavras.
    			if(!(artNwords.containsKey(value.getID())) || value.getWords() > artNwords.get(value.getID())) {
					artNwords.put(value.getID(),value.getWords());
				}
    		}
    	}

        //Ordena pelo nº de palavras do artigo e limitar a n.
		HashMap<Long,Long> words = artNwords.entrySet()
   			.stream()
   			.sorted(Map.Entry.<Long, Long>comparingByValue().reversed()) 
   			.limit(n)
			.collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e2,
                LinkedHashMap::new));
        //Passa as keys da hash para um array.
   		ArrayList<Long> ids = new ArrayList<Long>(words.keySet());

        return ids;

	}

    //Q9
    public ArrayList<String> titles_with_prefix(String prefix) {
        //Array que vai guardar os titulos dos artigos que contêm o prefixo.
    	ArrayList<String> prefixes = new ArrayList<String>();
    	Set<Long> keys = artHash.keySet();
    	
    	for(Long key : keys){
    		ArrayList<ArticleStructure> values = artHash.get(key);
    		for(ArticleStructure value : values){
    			if(value.getTitle().startsWith(prefix) && !(prefixes.contains(value.getTitle()))) {
    				prefixes.add(value.getTitle());
    			}
    		}
    	}
        //Ordenar alfabeticamente.
    	Collections.sort(prefixes);
	    return prefixes;
    }
    
    //Q10
    public String article_timestamp(long article_id, long revision_id) {
    	int size = artHash.get(article_id).size();
    	String time = " ";
    	for(int i=0; i<size; i++){
    		if(artHash.get(article_id).get(i).getRevID() == revision_id){
    			time = artHash.get(article_id).get(i).getTimestamp();
    		}
    	}
    	return time;
    }

    public void clean() {
        artHash.clear();
        contHash.clear();
    }
}
