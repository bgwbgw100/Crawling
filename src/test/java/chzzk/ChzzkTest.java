package chzzk;

import bgw.crawling.ChromeOption;
import bgw.crawling.CrawlingVO;
import bgw.crawling.chzzk.ChzzkVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class ChzzkTest {


    public List<CrawlingVO> crawling() throws Exception {

        WebDriver driver = null;

        driver = new ChromeDriver(ChromeOption.options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        driver.get("https://chzzk.naver.com/lives") ;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 20; i++) {
            Thread.sleep(30);
            js.executeScript("window.scrollBy(0, 200);");
        }

        List<WebElement> lis = driver.findElements(By.tagName("li"));
        List<CrawlingVO> crawlingVOList = new ArrayList<>();

        for (WebElement li: lis) {
            ChzzkVO chzzkVO = new ChzzkVO();

            chzzkVO.setTag("all");

            String text = li.getText();

            WebElement a = li.findElement(By.tagName("a"));

            String url = a.getAttribute("href");
            String[] splitText = text.split("\n");

            chzzkVO.setUserId(url.substring(url.lastIndexOf("live/") + 5));
            if(splitText[0].equals("LIVE")){
                chzzkVO.setStringConverterViews(splitText[1]);
                chzzkVO.setTitle(splitText[4]);
                chzzkVO.setName(splitText[6]);
            }else{
                chzzkVO.setStringConverterViews(splitText[2]);
                chzzkVO.setTitle(splitText[5]);
                chzzkVO.setName(splitText[7]);
            }
            crawlingVOList.add(chzzkVO);
        }
        driver.close();
        return crawlingVOList;
    }

    @Test
    public void test() throws  Exception{
        crawling();
    }

}
