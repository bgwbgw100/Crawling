package bgw.crawling.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MariaDBConnection {

    private static Logger logger = Logger.getLogger("MariaDBConnection");
    private static final String MARIADB_PASSWORD = System.getenv("MARIADB_PASSWORD");
    private static final String MARIADB_USERNAME = System.getenv("MARIADB_USERNAME");

    private MariaDBConnection(){}

    public static Optional<Connection> getDBConnection(){
        Connection conn = null;
        String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localHost:3306/crawling";
        String DB_USERNAME = System.getenv("MARIADB_USERNAME");
        String DB_PASSWORD = System.getenv("MARIADB_PASSWORD");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
            Class.forName(DB_DRIVER_CLASS);
            conn = connection;
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "드라이버 로딩 실패" );
            e.printStackTrace();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "DB 접속 실패" );
            e.printStackTrace();
        }

        return Optional.ofNullable(conn);
    }
}
