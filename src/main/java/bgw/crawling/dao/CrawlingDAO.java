package bgw.crawling.dao;

import bgw.crawling.CrawlingVO;
import bgw.crawling.africatv.AfricaVO;
import bgw.crawling.mariadb.MariaDBConnection;
import bgw.crawling.twitch.TwitchVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CrawlingDAO {

    private static final Connection connection = MariaDBConnection.getDBConnection();

    @Getter
    private static final CrawlingDAO instance = new CrawlingDAO();

    private CrawlingDAO(){}

    public void insert(List<CrawlingVO> dataList){
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlQuery = new StringBuilder();
        sql.append("INSERT INTO LIST").append("\n")
                .append("(PLAT_FORM,USER_ID,NAME,TITLE,VIEWS,UPDATE_DT)").append("\n")
                .append("SELECT '' AS PLAT_FORM").append("\n")
                .append("       ,'' AS USER_ID").append("\n")
                .append("       ,'' AS NAME").append("\n")
                .append("       ,'' AS TITLE").append("\n")
                .append("       ,0  AS VIEWS").append("\n")
                .append("       ,NOW() AS UPDATE_DT").append("\n")
                .append("FROM DUAL").append("\n")
                .append("WHERE '' != ''").append("\n");
        sqlQuery.append(sql);
        for (CrawlingVO crawlingVO : dataList) {
            String platForm = "non";
            platForm = crawlingVO instanceof AfricaVO ? "africa" : platForm;
            platForm = crawlingVO instanceof TwitchVO ? "twitch" : platForm;
            sql.append("UNION ALL").append("\n")
                    .append("SELECT ? AS PLAT_FORM").append("\n")
                    .append("       ,? AS USER_ID").append("\n")
                    .append("       ,? AS NAME").append("\n")
                    .append("       ,? AS TITLE").append("\n")
                    .append("       ,? AS VIEWS").append("\n")
                    .append("       ,NOW() AS UPDATE_DT").append("\n")
                    .append("FROM DUAL").append("\n");
            sqlQuery.append("UNION ALL").append("\n")
                    .append("SELECT '").append(platForm).append("' AS PLAT_FORM ").append("\n")
                    .append("       ,'").append(crawlingVO.getUserId()).append("' AS USER_ID ").append("\n")
                    .append("       ,'").append(crawlingVO.getName()).append("' AS NAME").append("\n")
                    .append("       ,'").append(crawlingVO.getTitle()).append("' AS TITLE").append("\n")
                    .append("       ,").append(crawlingVO.getViews()).append(" AS VIEWS").append("\n")
                    .append("       ,NOW() AS UPDATE_DT").append("\n")
                    .append("FROM DUAL").append("\n");
        }
        sql.append("ON DUPLICATE KEY UPDATE NAME = VALUES(NAME), TITLE = VALUES(TITLE), VIEWS = VALUES(VIEWS)");
        sqlQuery.append("ON DUPLICATE KEY UPDATE NAME = VALUES(NAME), TITLE = VALUES(TITLE), VIEWS = VALUES(VIEWS)");


        try (PreparedStatement statement = connection.prepareStatement(sql.toString())){

            int index = 1;
            for (CrawlingVO crawlingVO : dataList) {
                String platForm = "non";
                platForm = crawlingVO instanceof AfricaVO ? "africa" : platForm;
                platForm = crawlingVO instanceof TwitchVO ? "twitch" : platForm;
                statement.setString(index++,platForm);
                statement.setString(index++,crawlingVO.getUserId());
                statement.setString(index++,crawlingVO.getName());
                statement.setString(index++,crawlingVO.getTitle());
                statement.setInt(index++,crawlingVO.getViews());
            }

            //log.info(sqlQuery.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(sqlQuery.toString());
            log.error("createStatement Fail", e);
        }
    }





}
