package bgw.crawling.dao;

import bgw.crawling.CrawlingVO;
import bgw.crawling.mariadb.ConnectionRepository;
import bgw.crawling.mariadb.ThreadLocalConnectionRepository;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CrawlingDAOProxy {


    private final CrawlingDAO crawlingDAO = CrawlingDAO.getInstance();


    @Getter
    private static final CrawlingDAOProxy instance = new CrawlingDAOProxy();

    private CrawlingDAOProxy(){
    };

    public void insert(List<CrawlingVO> dataList, StringBuilder sqlQuery) throws SQLException {

        crawlingDAO.setConnection(ThreadLocalConnectionRepository.instance.getRentalConnectionRepository().getConnection());
        crawlingDAO.insert(dataList, sqlQuery);
        crawlingDAO.setConnection(null);
    }

    public void delete(StringBuilder sqlQuery) throws SQLException {
        crawlingDAO.setConnection(ThreadLocalConnectionRepository.instance.getRentalConnectionRepository().getConnection());
        crawlingDAO.delete(sqlQuery);
        crawlingDAO.setConnection(null);
    }
}
