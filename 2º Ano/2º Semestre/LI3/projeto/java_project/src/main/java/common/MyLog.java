package common;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;

public class MyLog {

    private Logger log;

    public MyLog(String name){
        /*
            LOG CONFIGURATION
         */
        this.log = Logger.getLogger(name);
        this.log.setAdditivity(false);
        FileAppender capp = null;
        try {
            capp = new FileAppender(new PatternLayout("%m\n"),name+".txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        capp.setName(name);
        this.log.removeAllAppenders();
        this.log.addAppender(capp);
    }

    public void writeLog(String message){
        this.log.info(message);
    }

}
