package bgw.crawling;

import bgw.crawling.africatv.AfricaTV;
import bgw.crawling.mariadb.MariaDBConnection;
import bgw.crawling.twitch.Twitch;
import bgw.crawling.twitch.TwitchVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {

        List<Crawling> crawlingList = new ArrayList<>();
        List<CrawlingVO> crawlingVOList = new ArrayList<>();
        Crawling africaTV = new AfricaTV();
        Crawling twitch = new Twitch();
        crawlingList.add(africaTV);
        crawlingList.add(twitch);

        crawlingList.parallelStream().forEach(crawling -> crawlingVOList.addAll(crawling.crawling()));



        crawlingVOList.forEach(crawlingVO -> System.out.println(crawlingVO.toString()));


    }
}