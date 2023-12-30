package bgw.crawling.service;

import bgw.crawling.dao.CrawlingDAO;
import bgw.crawling.mariadb.MysqlConnection;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class ServiceProxy implements InvocationHandler {

    private Connection connection = MysqlConnection.getConnection();

    private final Service realService = new ServiceImpl(connection);

    private static final Service proxy = (Service) Proxy.newProxyInstance( Service.class.getClassLoader(),
            new Class<?>[] {Service.class},
            new ServiceProxy()
    );

    private ServiceProxy(){}


    public static Service getInstance() {
        return proxy;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Object result ;
        try {
            connection.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            result = method.invoke(realService, args);
            log.info("{}",System.currentTimeMillis()- startTime);
        } catch (CommunicationsException e){
            MysqlConnection.reConnection();
            log.error("",e);
            throw e;
        } catch (Exception e) {
            connection.rollback();
            log.error("",e);
            throw e;
        }finally {
            connection.commit();
            connection.setAutoCommit(true);
        }
        return result;
    }
}
