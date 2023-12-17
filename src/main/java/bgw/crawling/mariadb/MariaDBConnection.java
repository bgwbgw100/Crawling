package bgw.crawling.mariadb;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
@Slf4j
public class MariaDBConnection {

    private static final Logger logger = Logger.getLogger("MariaDBConnection");
    private static final String MARIADB_PASSWORD = System.getenv("MARIADB_PASSWORD");
    private static final String MARIADB_USERNAME = System.getenv("MARIADB_USERNAME");

    private static final List<Connection> dbConnectList = new ArrayList<>();

    private MariaDBConnection(){}

    public static Connection getDBConnection(){
        Connection conn = null;
        String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localHost:3306/crawling";
        String DB_USERNAME = System.getenv("MARIADB_USERNAME");
        String DB_PASSWORD = System.getenv("MARIADB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
            Class.forName(DB_DRIVER_CLASS);
            conn = connection;
        } catch (ClassNotFoundException e) {
            log.error("드라이버 로딩 실패",e);

        } catch (SQLException e) {
            log.error("DB접근 실패",e);
        }
        dbConnectList.add(conn);
        return conn;
    }

    public static void connectListClose(){
        for (Connection connection : dbConnectList) {
            try {
                if(!connection.isClosed())connection.close();
            } catch (SQLException e) {
                log.error("DBCloseError",e);

            }
        }

    }
}
