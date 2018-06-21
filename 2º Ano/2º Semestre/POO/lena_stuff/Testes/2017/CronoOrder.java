import java.util.Comparator;
import java.time.LocalDateTime;

/* 1 d). Ordenar as faixas por ordem cronológica de última vez que foram tocadas.*/
public class CronoOrder implements Comparator <Faixa> {
    public int compare(Faixa f1, Faixa f2) {
        LocalDateTime u1 = f1.getUltimaVez();
        LocalDateTime u2 = f2.getUltimaVez();
        if (u1.isBefore(u2)) return -1;
        if (u2.isBefore(u1)) return 1;
        else return 0;
    }
}
