package bgw.crawling;

import bgw.crawling.africatv.AfricaTV;
import bgw.crawling.africatv.AfricaVO;
import bgw.crawling.dao.CrawlingDAO;
import bgw.crawling.twitch.Twitch;
import bgw.crawling.twitch.TwitchVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Service {

    @Getter
    private static Service instance = new Service();

    private Service(){}

    private final CrawlingDAO crawlingDAO = CrawlingDAO.getInstance();

    private final Connection connection = CrawlingDAO.getConnection();
    public void saveCrawlingData(){
        // 크롤링
        List<Crawling> crawlingList = new ArrayList<>();
        List<CrawlingVO> crawlingVOList = new ArrayList<>();
        Crawling africaTV = new AfricaTV();
        Crawling twitch = new Twitch();
        Set<String> set = new HashSet<>();

        crawlingList.add(africaTV);
        crawlingList.add(twitch);
        crawlingList.parallelStream().forEach(crawling -> crawlingVOList.addAll(crawling.crawling()));

        List<CrawlingVO> paramList = crawlingVOList.stream().filter(crawlingVO -> {

            String platForm = "non";
            platForm = crawlingVO instanceof AfricaVO ? "afreeca" : platForm;
            platForm = crawlingVO instanceof TwitchVO ? "twitch" : platForm;

            String key = String.format("%s-%s",platForm,crawlingVO.getUserId());

            if(set.add(key)){
                return true;
            };
            return !"ALL".equals(crawlingVO.getTag());
        }).collect(Collectors.toList());

        // 크롤링 데이터
        StringBuilder sqlQuery = new StringBuilder();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("autoCommit false fail" , e);
        }
        try {
            crawlingDAO.delete(sqlQuery);
            crawlingDAO.insert(paramList,sqlQuery);
        } catch (SQLException e) {
            log.error(sqlQuery.toString());
            log.error("sql Error" , e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("sql rollback error" , e);
            }
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("sql commit error" , e);
        }

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("autoCommit true fail" , e);
        }


    }

}
