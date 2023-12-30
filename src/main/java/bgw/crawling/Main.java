package bgw.crawling;

import bgw.crawling.config.SimpleSlf4jConfig;
import bgw.crawling.mariadb.MysqlConnection;
import bgw.crawling.service.Service;
import bgw.crawling.service.ServiceImpl;
import bgw.crawling.service.ServiceProxy;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;



public class Main {


    private static volatile boolean running = true;
    public static void main(String[] args)  {

        SimpleSlf4jConfig.init();

        Service service = ServiceProxy.getInstance();

        Logger log = LoggerFactory.getLogger(Main.class);

        //while 문에서 q 입력받을시  반복중단 하고 종료
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (running) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if ("q".equals(input)) {
                        running = false; // 'q' 입력 시 루프 종료
                    }
                }
            }
            scanner.close();
        });

        inputThread.start();

        while (running){
            Process.sleepProcess(() -> {
                try {
                    service.saveCrawlingData();
                }catch (CommunicationsException e) {
                        log.info("reconnect");
                    try {
                        MysqlConnection.reConnection();
                    } catch (SQLException ex) {
                        log.error("reConnect  fail", ex);
                    }
                }catch (Exception e) {
                    log.error("error ", e);
                }
            });
        }

        MysqlConnection.connectClose();

    }
}