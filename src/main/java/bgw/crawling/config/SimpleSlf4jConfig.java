package bgw.crawling.config;

import lombok.Getter;
import org.slf4j.simple.SimpleLogger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleSlf4jConfig {

    private static boolean init  = false;
    private static String logLevel = "info";

    private SimpleSlf4jConfig(){

    }

    public static void init() {
        if (!init) {
            String level = "INFO";
            System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY,level);
            //System.setProperty(SimpleLogger.LOG_FILE_KEY,"system.out");
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
            String formattedDate = today.format(formatter);
            String fileName = "logs/"+formattedDate+"Log.log";

            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            System.setProperty(SimpleLogger.LOG_FILE_KEY, "logs/"+formattedDate+"log.log");
            System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
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
