package bgw.crawling.config;

import lombok.Getter;
import org.slf4j.simple.SimpleLogger;

public class SimpleSlf4jConfig {

    private static boolean init  = false;
    private static String logLevel = "info";

    private SimpleSlf4jConfig(){

    }

    public static void init() {
        if (!init) {
            String level = "DEBUG";
            System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY,level);
            System.setProperty(SimpleLogger.LOG_FILE_KEY,"system.out");
            logLevel =level;
            init =true;

        }
    }

    public static boolean getInitState(){
        return init;
    }

    public static String getLogLevel() {
        return logLevel;
    }
}
