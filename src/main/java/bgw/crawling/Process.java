package bgw.crawling;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Process {

    private static final long TIME = 90000;
    private Process(){};
    @SneakyThrows
    public static void sleepProcess(ProcessInterface process){
        long starTime = System.currentTimeMillis();
        process.process();
        long endTime = System.currentTimeMillis() - starTime;
        log.info("endTime : {}" , endTime);
        if(TIME - endTime >0){
            Thread.sleep(TIME-endTime);
        }else {
            long garbageTime = endTime % TIME;
            Thread.sleep(TIME-garbageTime);
        }
    }
}
interface ProcessInterface{
    void process();
}
