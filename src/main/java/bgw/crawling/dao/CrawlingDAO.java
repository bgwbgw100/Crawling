package bgw.crawling.dao;

import bgw.crawling.mariadb.MariaDBConnection;
import lombok.Getter;

import java.sql.Connection;

public class CrawlingDAO {

    private static final Connection connection = MariaDBConnection.getDBConnection();

    @Getter
    private static final CrawlingDAO instance = new CrawlingDAO();

    private CrawlingDAO(){}

    public void insert(){
        
    }



}
