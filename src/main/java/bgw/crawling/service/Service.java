package bgw.crawling.service;

import java.sql.Connection;

public interface Service {
    public void saveCrawlingData(Connection  connection) throws Exception;

}
