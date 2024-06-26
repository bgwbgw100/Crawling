
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Slf4j
public class DbConnection {





    @Test
    public void mySqlConnection() throws Exception{
        Connection conn = null;
        String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://my8002.gabiadb.com:3306/crawling?autoReconnect=true";

        conn = DriverManager.getConnection(DB_URL,  System.getenv("CRAWLING_NAME"), System.getenv("CRAWLING_PASSWORD"));
        Class.forName(DB_DRIVER_CLASS);

        conn.close();

    }

    @Test
    public void mariaDBConnect(){
        
        String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localHost:3306/crawling";
        String DB_USERNAME = System.getenv("MARIADB_USERNAME");
        String DB_PASSWORD = System.getenv("MARIADB_PASSWORD");
        Connection conn = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
            Class.forName(DB_DRIVER_CLASS);
            conn =connection;
        } catch (ClassNotFoundException e) {

            Assertions.fail("mariaDb jdbc 클래스를 찾을수 없습니다");

        } catch (SQLException e) {
            Assertions.fail("마리아DB 연결에 실패 하였습니다.");
            
        }
        try{
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("close fail",e);

        }
    }
}



