package bgw.crawling.africatv;

import bgw.crawling.Crawling;
import bgw.crawling.CrawlingVO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public  class AfricaTV implements Crawling {
    private final List<CrawlingVO>  africaVOS = new ArrayList<>();



    public List<CrawlingVO> crawling(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.afreecatv.com/?hash=all");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
        driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
        driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
        driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
        driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
        WebElement textBox = driver.findElement(By.id("broadlist_area"));

        List<WebElement> li = textBox.findElements(By.tagName("li"));

        for (WebElement element: li) {
            CrawlingVO africaVO = new AfricaVO();
            WebElement userElement = element.findElement(By.className("details")).findElement(By.tagName("a"));
            String userId =userElement.getAttribute("user_id");
            String name = userElement.getText();
            String views = element.findElement(By.className("views")).getText();
            String title = element.findElement(By.className("title")).getText();
            africaVO.setUserId(userId);
            africaVO.setName(name);
            africaVO.setStringConverterViews(views);
            africaVO.setTitle(title);
            this.africaVOS.add(africaVO);
        }


        driver.quit();
        return africaVOS;
    }



}
