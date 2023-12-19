package bgw.crawling.mariadb;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MariaDBConnection {

    private static final String MARIADB_PASSWORD = System.getenv("MARIADB_PASSWORD");
    private static final String MARIADB_USERNAME = System.getenv("MARIADB_USERNAME");

    private static final List<Connection> dbConnectList = new ArrayList<>();

    private MariaDBConnection(){}

    public static Connection getDBConnection(){
        Connection conn = null;
        String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localHost:3306/crawling";


        try {
            conn = DriverManager.getConnection(DB_URL, MARIADB_USERNAME, MARIADB_PASSWORD);
            Class.forName(DB_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            log.error("드라이버 로딩 실패",e);

        } catch (SQLException e) {
            log.error("DB접근 실패",e);
        }
        dbConnectList.add(conn);
        return conn;
    }

    public static void connectListClose(){
        int index = 0;
        for (Connection connection : dbConnectList) {

            try (connection){
                log.info("closeConnection"+index++);
            } catch (SQLException e) {
                log.error("DBCloseError",e);
            }
        }
    }
}
