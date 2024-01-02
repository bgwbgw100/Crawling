package bgw.crawling.mariadb;



import lombok.Getter;
import lombok.Setter;
import org.slf4j.helpers.ThreadLocalMapOfStacks;

import java.sql.Connection;
import java.util.Optional;

public class  ConnectionRepository  {

    private boolean rental = false;

    @Getter
    private Connection connection;



    protected ConnectionRepository(Connection connection){
        this.connection = connection;
    };

    public synchronized void  setConnection(Connection connection) {
        this.connection = connection;
    }

    public synchronized Optional<ConnectionRepository> rental(){

        if(rental){
            return  Optional.ofNullable(null);
        }
        rental = true;
        return Optional.of(this);
    }

    public  synchronized void finishRental(){

        rental = false;

    }




}
