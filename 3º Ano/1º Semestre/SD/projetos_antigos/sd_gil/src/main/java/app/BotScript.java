package app;


import com.github.javafaker.Faker;

/**
 * Starts multiple bots
 */
public class BotScript {

    /**
     * Main
     * @param args Arguments
     */
    public static void main(String[] args) {

        Faker faker = new Faker();

        int nBots = 100;

        for (int i=0; i<nBots; i++){
            String[] bot = new String[3];
            String name = faker.name().firstName().split(" ")[0];
            bot[0] = name;
            bot[1] = name;
            bot[2] = name;
            StartBot.main(bot);
        }
    }
}
