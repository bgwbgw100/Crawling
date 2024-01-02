package bgw.crawling.mariadb;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MysqlConnection {

    private static final String CRAWLING_PASSWORD = System.getenv("CRAWLING_PASSWORD");
    private static final String CRAWLING_NAME = System.getenv("CRAWLING_NAME");

    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://my8002.gabiadb.com:3306/crawling";


    private static List<ConnectionRepository> connectionRepositoryList = Collections.synchronizedList(new ArrayList<ConnectionRepository>());

    private MysqlConnection(){}

    public static void init(){
        getDBConnection();
        getDBConnection();
    }


    private static Connection getDBConnection(){
        Connection conn = null;


        try {
            Class.forName(DB_DRIVER_CLASS);
            conn = DriverManager.getConnection(DB_URL, CRAWLING_NAME, CRAWLING_PASSWORD);

        } catch (ClassNotFoundException e) {
            log.error("드라이버 로딩 실패",e);

        } catch (SQLException e) {
            log.error("DB접근 실패",e);
        }

        initSql(conn);
        connectionRepositoryList.add(new ConnectionRepository(conn));
        return conn;


    }

    public static void connectClose(){
        for (ConnectionRepository connectionRepository : connectionRepositoryList) {
            Connection connection = connectionRepository.getConnection();
            try {
                if(!connection.isClosed()){
                    connection.close();
                };
                log.info("closeConnection");
            } catch (SQLException e) {
                log.error("DBCloseError",e);
            }
        }


    }

    public static void reConnection() throws SQLException {

        for (ConnectionRepository connectionRepository : connectionRepositoryList) {
            Connection connection = connectionRepository.getConnection();
            if(connection.isValid(3)) continue;//연결 유효한지 체크
            
            try {
                connection = DriverManager.getConnection(DB_URL, CRAWLING_NAME, CRAWLING_PASSWORD);
                Class.forName(DB_DRIVER_CLASS);
            } catch (ClassNotFoundException e) {
                log.error("드라이버 로딩 실패",e);

            } catch (SQLException e) {
                log.error("DB접근 실패",e);
            }
            connectionRepository.setConnection(connection);
            initSql(connection);
            log.info("reconnection");


        }

    }


    private static void initSql(Connection conn){
        try (PreparedStatement preparedStatement = conn.prepareStatement("SET SESSION wait_timeout = 600")){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(" session wait_timeout Set fail " , e);
        }
    }

    public static synchronized  Optional<ConnectionRepository> rentalConnectionRepository() {
        Optional<ConnectionRepository> rental = Optional.ofNullable(null);
        synchronized (connectionRepositoryList){
            for (ConnectionRepository connectionRepository : connectionRepositoryList) {
                rental = connectionRepository.rental();
                if(!rental.isEmpty()) break;
            }
        }
        return rental;
    }
}
