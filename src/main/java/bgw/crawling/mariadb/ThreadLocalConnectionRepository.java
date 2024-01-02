package bgw.crawling.mariadb;

public class ThreadLocalConnectionRepository {
    public static final ThreadLocalConnectionRepository instance = new ThreadLocalConnectionRepository();

    private final ThreadLocal<ConnectionRepository> rentalConnectionRepository = new ThreadLocal<>();

    private ThreadLocalConnectionRepository(){};

    public void putRentalConnectionRepository(ConnectionRepository connection) {
        this.rentalConnectionRepository.set(connection);
    }

    public  ConnectionRepository getRentalConnectionRepository(){
        return this.rentalConnectionRepository.get();
    }

    public  void removeRentalConnectionRepository(){
        this.rentalConnectionRepository.remove();
    }
}
