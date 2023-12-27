package bgw.crawling.dao;

import bgw.crawling.CrawlingVO;
import bgw.crawling.africatv.AfricaVO;
import bgw.crawling.config.SimpleSlf4jConfig;
import bgw.crawling.mariadb.MariaDBConnection;
import bgw.crawling.mariadb.MysqlConnection;
import bgw.crawling.twitch.TwitchVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.simple.SimpleLoggerConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrawlingDAO {

    @Getter
    private static final Connection connection = MysqlConnection.getDBConnection();

    @Getter
    private static final CrawlingDAO instance = new CrawlingDAO();

    private CrawlingDAO(){}

    public void insert(List<CrawlingVO> dataList, StringBuilder sqlQuery) throws SQLException {
        ArrayList<Object> paramList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sqlQuery = new StringBuilder();

        sql.append("INSERT INTO LIST").append("\n")
                .append("(PLAT_FORM,USER_ID,NAME,TITLE,VIEWS,UPDATE_DT,TAG)").append("\n")
                .append("SELECT '' AS PLAT_FORM").append("\n")
                .append("       ,'' AS USER_ID").append("\n")
                .append("       ,'' AS NAME").append("\n")
                .append("       ,'' AS TITLE").append("\n")
                .append("       ,0  AS VIEWS").append("\n")
                .append("       ,NOW() AS UPDATE_DT").append("\n")
                .append("       ,'' AS TAG").append("\n")
                .append("FROM DUAL").append("\n")
                .append("WHERE '' != ''").append("\n");

        for (CrawlingVO crawlingVO : dataList) {

            sql.append("UNION ALL").append("\n")
                    .append("SELECT ? AS PLAT_FORM").append("\n")
                    .append("       ,? AS USER_ID").append("\n")
                    .append("       ,? AS NAME").append("\n")
                    .append("       ,? AS TITLE").append("\n")
                    .append("       ,? AS VIEWS").append("\n")
                    .append("       ,NOW() AS UPDATE_DT").append("\n")
                    .append("       ,? AS TAG").append("\n")
                    .append("FROM DUAL").append("\n");
        }
        sql.append("ON DUPLICATE KEY UPDATE NAME = VALUES(NAME), TITLE = VALUES(TITLE), VIEWS = VALUES(VIEWS), TAG = VALUES(TAG)");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())){

            int index = 1;
            for (CrawlingVO crawlingVO : dataList) {
                String platForm = "non";
                platForm = crawlingVO instanceof AfricaVO ? "afreeca" : platForm;
                platForm = crawlingVO instanceof TwitchVO ? "twitch" : platForm;
                statement.setString(index++,platForm);
                statement.setString(index++,crawlingVO.getUserId());
                statement.setString(index++,crawlingVO.getName());
                statement.setString(index++,crawlingVO.getTitle());
                statement.setInt(index++,crawlingVO.getViews());
                statement.setString(index++,crawlingVO.getTag());
                paramList.add(platForm);
                paramList.add(crawlingVO.getUserId());
                paramList.add(crawlingVO.getName());
                paramList.add(crawlingVO.getTitle());
                paramList.add(crawlingVO.getViews());
                paramList.add(crawlingVO.getTag());
            }

            sqlQuery.append(" query execute :  ").append("\n").append(sql.toString().replaceAll("\\?","'{}'"));
            if(SimpleSlf4jConfig.getInitState()){
                if("DEBUG".equals(SimpleSlf4jConfig.getLogLevel())){
                    log.debug(sqlQuery.toString(),paramList.toArray());
                };
            }
            statement.executeUpdate();
        }


    }

    public void delete(StringBuilder sqlQuery) throws SQLException {
        sqlQuery = new StringBuilder();
        sqlQuery.append("DELETE FROM LIST");
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());){
            statement.executeUpdate();
        }



    }





}
