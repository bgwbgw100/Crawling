import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.logging.Logger;

@Slf4j
public class DbConnection {

    private static Logger logger = Logger.getLogger("DbConnection");
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
                e.printStackTrace();
        }
    };
}



