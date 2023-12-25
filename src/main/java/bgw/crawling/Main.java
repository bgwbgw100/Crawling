package bgw.crawling;

import bgw.crawling.africatv.AfricaTV;
import bgw.crawling.config.SimpleSlf4jConfig;
import bgw.crawling.dao.CrawlingDAO;
import bgw.crawling.mariadb.MariaDBConnection;
import bgw.crawling.mariadb.MysqlConnection;
import bgw.crawling.twitch.Twitch;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args)  {

        SimpleSlf4jConfig.init();

        List<Crawling> crawlingList = new ArrayList<>();
        List<CrawlingVO> crawlingVOList = new ArrayList<>();
        Crawling africaTV = new AfricaTV();
        Crawling twitch = new Twitch();
        crawlingList.add(africaTV);
        crawlingList.add(twitch);

        crawlingList.parallelStream().forEach(crawling -> crawlingVOList.addAll(crawling.crawling()));

        CrawlingDAO.getInstance().insert(crawlingVOList);

        MysqlConnection.connectListClose();

    }
}