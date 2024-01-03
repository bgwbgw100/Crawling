package bgw.crawling.service;

import bgw.crawling.Transactional;
import bgw.crawling.connetion.ConnectionRepository;
import bgw.crawling.connetion.ThreadLocalConnectionRepository;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

@Slf4j
public class ServiceProxy implements InvocationHandler {


    private final Service realService = new ServiceImpl();

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
        boolean transactional = method.getAnnotation(Transactional.class) != null;
        ConnectionRepository connectionRepository = ThreadLocalConnectionRepository.instance.getRentalConnectionRepository(); // 쓰레드 로컬에서 커넥션 repository 반환

        Connection connection = connectionRepository.getConnection();
        connection.setAutoCommit(true);
        if(transactional){
            connection.setAutoCommit(false);
        }
        try {
            result = method.invoke(realService, args);
        } catch (Exception e) {
            if(transactional){
                connection.rollback();
                log.error("rollback",e);
            }
            throw e;
        }finally {
            if(transactional){
                connection.commit();
            }
        }
        return result;
    }



}
