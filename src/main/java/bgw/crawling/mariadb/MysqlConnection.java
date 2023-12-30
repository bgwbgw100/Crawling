package bgw.crawling.mariadb;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MysqlConnection {

    private static final String CRAWLING_PASSWORD = System.getenv("CRAWLING_PASSWORD");
    private static final String CRAWLING_NAME = System.getenv("CRAWLING_NAME");
    @Getter
    private static Connection connection = getDBConnection();


    private MysqlConnection(){}

    private static Connection getDBConnection(){
        Connection conn = null;
        String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://my8002.gabiadb.com:3306/crawling";

        try {
            Class.forName(DB_DRIVER_CLASS);
            conn = DriverManager.getConnection(DB_URL, CRAWLING_NAME, CRAWLING_PASSWORD);

        } catch (ClassNotFoundException e) {
            log.error("드라이버 로딩 실패",e);

        } catch (SQLException e) {
            log.error("DB접근 실패",e);
        }

        initSql(conn);
        return conn;
    }

    public static void connectClose(){
        int index = 0;
        try {
            if(!connection.isClosed()){
                connection.close();
            };
            log.info("closeConnection");
        } catch (SQLException e) {
            log.error("DBCloseError",e);
        }
    }

    public static void reConnection() throws SQLException {
        if(connection.isClosed()){
            return;
        }
        String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://my8002.gabiadb.com:3306/crawling?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(DB_URL, CRAWLING_NAME, CRAWLING_PASSWORD);
            Class.forName(DB_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            log.error("드라이버 로딩 실패",e);

        } catch (SQLException e) {
            log.error("DB접근 실패",e);
        }
        log.info("reconnection");
        initSql(connection);
    }


    private static void initSql(Connection conn){
        try (PreparedStatement preparedStatement = conn.prepareStatement("SET SESSION wait_timeout = 600")){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(" session wait_timeout Set fail " , e);
        }
    }

}
