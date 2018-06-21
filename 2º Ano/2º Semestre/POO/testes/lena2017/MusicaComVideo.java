import java.util.ArrayList;
import java.time.LocalDateTime;

public class MusicaComVideo extends Faixa { // não é necessário implementar Playable pois já é implementado em Faixa.
    /* 4 a) variáveis de instância */
    private String video;
    
    public MusicaComVideo(String nome, String autor, double duracao, int classificacao, ArrayList<String> letra, int numeroVezesTocada, LocalDateTime ultimaVez,String video) {
        super(nome, autor, duracao, classificacao, letra, numeroVezesTocada, ultimaVez);
        this.video = video;
    }
    
    @Override
    public void play() {
        super.play();
        System.out.println(video);
    }
}
