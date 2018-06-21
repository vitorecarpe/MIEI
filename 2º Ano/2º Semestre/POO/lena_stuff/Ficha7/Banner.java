
public class Banner {
    public static void main (String[] args) {
        int argc = args.length, i;
        final String[] digits = {
        " ___ |   || | ||___|",
        " ___ /_  | |  | |__|",
        " ___ |_  ||  _||___|", 
        " ___ |_  ||_  ||___|",
        " _ _ | | ||_  |  |_|",
        " ___ |  _||_  ||___|", 
        " ___ |  _|| . ||___|",
        " ___ |__ |  / | /__|",
        " ___ | . || . ||___|",
        " ___ | . ||_  ||___|"};
        for (i = 0; i < argc; i++) {
            String p = args[i];
            int l = p.length(), n = 0;
            
            while (n < 4) {
                for (int j = 0; j < l; j++) { // para cada elemento da string
                    int c = (int) p.charAt(j) - 48;
                    for (int k = n*5; k < (n+1)*5; k++) {
                        System.out.print(digits[c].charAt(k));
                    }
                }
                System.out.println(""); //muda de linha no fim.
                n++; // significa mais uma linha feita yay
            }
        }
        System.out.println("");
    }
}
