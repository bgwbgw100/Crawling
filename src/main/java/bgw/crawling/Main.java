package bgw.crawling;

import bgw.crawling.config.SimpleSlf4jConfig;
import bgw.crawling.mariadb.MysqlConnection;
import bgw.crawling.service.Service;
import bgw.crawling.service.ServiceImpl;
import bgw.crawling.service.ServiceProxy;

import java.util.Scanner;


public class Main {
    private static volatile boolean running = true;
    public static void main(String[] args)  {

        SimpleSlf4jConfig.init();

        Service service = ServiceProxy.getInstance();

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
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        MysqlConnection.connectClose();

    }
}