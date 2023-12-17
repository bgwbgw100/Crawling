package bgw.crawling;

import bgw.crawling.twitch.Twitch;
import bgw.crawling.twitch.TwitchVO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Connection connection;


        /*Optional<Connection> optional = MariaDBConnection.getDBConnection();
        while (optional.isEmpty()){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            optional = MariaDBConnection.getDBConnection();
        }

        connection = optional.get();*/
/*

        Crawling<List<AfricaVO>> africaTV = new AfricaTV<>();

        List<AfricaVO> africaVOList = africaTV.crawling();
        africaVOList.forEach(africaVO -> sb.append(africaVO.toString()).append("\n"));
*/
        StringBuilder sb = new StringBuilder();
        Crawling twitch = new Twitch();
        List<CrawlingVO> crawling = twitch.crawling();
        crawling.forEach(africaVO -> sb.append(africaVO.toString()).append("\n"));



        System.out.println(sb);


    }
}