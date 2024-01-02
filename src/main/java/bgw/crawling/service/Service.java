package bgw.crawling.service;

import bgw.crawling.Transactional;

import java.sql.Connection;

public interface Service {
    @Transactional
    public void saveCrawlingData() throws Exception;

}
