package bgw.crawling;

import bgw.crawling.chzzk.Chzzk;
import bgw.crawling.config.SimpleSlf4jConfig;
import bgw.crawling.connetion.ConnectionRepository;
import bgw.crawling.connetion.MysqlConnection;
import bgw.crawling.connetion.ThreadLocalConnectionRepository;
import bgw.crawling.service.Service;
import bgw.crawling.service.ServiceProxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLNonTransientConnectionException;
import java.util.Optional;
import java.util.Scanner;



public class Main {


    private static volatile boolean running = true;
    public static void main(String[] args)  {

        SimpleSlf4jConfig.init();

        Service service = ServiceProxy.getInstance();

        MysqlConnection.init();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chrome");


        ChromeOptions options = ChromeOption.options;

        options.addArguments("--headless");  // Headless 모드 활성화
        options.addArguments("--window-size=1928,1080");  // 창 크기 설정


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
                startProcess(log, service);
            });
        }
        MysqlConnection.connectClose();
    }

    private static void startProcess(Logger log, Service service) {
        ThreadLocalConnectionRepository tLCR = ThreadLocalConnectionRepository.instance;
        try {
            Optional<ConnectionRepository> rentalConnectionRepository = MysqlConnection.rentalConnectionRepository(); // ConnectionRepository 렌탈
            //커넥션 빌림
            if(rentalConnectionRepository.isEmpty()){
                log.info("남는 connection 없어요");
                return;
            }
            tLCR.putRentalConnectionRepository(rentalConnectionRepository.get()); // 쓰레드 로컬에 삽입
            service.saveCrawlingData();
        }catch (SQLNonTransientConnectionException e){
            log.error("error ", e);
            try {
                MysqlConnection.reConnection(); // 복구처리
            } catch (Exception ex) {
                log.error("reConnect  fail", ex);
            }
        }
        catch (Exception e) {
            log.error("error ", e);
        }finally {
            try {
                tLCR.getRentalConnectionRepository().finishRental(); // 반납
                tLCR.removeRentalConnectionRepository(); // 쓰레드 로컬 삭제
            }catch (Exception e){
                log.error("반납 error" , e);
            }

        }
    }


}